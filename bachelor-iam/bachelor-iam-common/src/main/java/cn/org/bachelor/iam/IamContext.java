/*
 * @(#)VariableLifecycleServiceImpl.java	May 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package cn.org.bachelor.iam;

import cn.org.bachelor.context.IContext;
import cn.org.bachelor.context.IUserContext;
import cn.org.bachelor.iam.utils.StringUtils;
import cn.org.bachelor.iam.vo.UserVo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author Team Bachelor
 */
@Component
public class IamContext implements IUserContext {

    @Resource
    private IContext baseContext;

    private String remoteIP;

    /**
     * 获取基础上下文
     * @return IContext 上下文
     */
    public IContext getBaseContext() {
        return baseContext;
    }

    @Deprecated
    /**
     * 设置当前登录用户
     */
    public void setCurrentUser(UserVo user) {
        setUser(user);
    }

    /**
     * 获取当前登录用户
     * @return UserVo 当前登录用户
     */
    @Deprecated
    public UserVo getCurrentUser() {
        return getUser();
    }

    /**
     * 获取当前用户是否登录
     * @return 当前用户是否已登录
     */
    public boolean isUserLogon() {
        UserVo user = getUser();
        return user == null ? false : StringUtils.isEmpty(user.getAccessToken()) ? false : true;
    }

    /**
     * 设置当前登录用户
     * @param user 当前登录用户
     */
    public void setUser(UserVo user) {
        baseContext.setRequestAttribute(IamConstant.USER_KEY, user);
    }

    /**
     * 获取当前登录用户
     * @return 当前登录用户
     */
    public UserVo getUser() {
        Object uo = baseContext.getRequestAttribute(IamConstant.USER_KEY);
        if (uo != null) {
            return (UserVo) uo;
        }
        return null;
    }

    /**
     * 获取当前登录用户的编码
     * @return 当前登录用户的编码
     */
    public String getUserCode() {
        UserVo user = getUser();
        String name = "user_unknown";
        if (user != null && user.getCode() != null) {
            name = user.getCode();
        } else if (getRemoteIP() != null) {
            name = getRemoteIP();
        }
        return name;
    }

    /**
     * 获取当前登录用户的组织机构编码
     * @return 当前登录用户的组织机构编码
     */
    public String getUserOrgId() {
        UserVo user = getUser();
        if (Objects.isNull(user)) {
            return null;
        }
        String orgId = user.getOrgId();
        return orgId;
    }

    /**
     * 获取远程访问的IP
     * @return 远程访问的IP
     */
    public String getRemoteIP() {
        return remoteIP;
    }

    /**
     * 设置远程访问的IP
     * @param remoteIP 远程访问的IP
     */
    public void setRemoteIP(String remoteIP) {
        this.remoteIP = remoteIP;
    }

    /**
     * 设置基础上下文
     * @param baseContext 基础上下文
     */
    public void setBaseContext(IContext baseContext) {
        this.baseContext = baseContext;
    }
}
