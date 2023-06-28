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

    public IContext getBaseContext() {
        return baseContext;
    }

    @Deprecated
    public void setCurrentUser(UserVo user) {
        setUser(user);
    }

    @Deprecated
    public UserVo getCurrentUser() {
        return getUser();
    }

    public boolean isUserLogon() {
        UserVo user = getUser();
        return user == null ? false : StringUtils.isEmpty(user.getAccessToken()) ? false : true;
    }

    public void setUser(UserVo user) {
        baseContext.setRequestAttribute(IamConstant.USER_KEY, user);
    }

    public UserVo getUser() {
        Object uo = baseContext.getRequestAttribute(IamConstant.USER_KEY);
        if (uo != null) {
            return (UserVo) uo;
        }
        return null;
    }

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

    public String getUserOrgId() {
        UserVo user = getUser();
        if (Objects.isNull(user)) {
            return null;
        }
        String orgId = user.getOrgId();
        return orgId;
    }

    public String getRemoteIP() {
        return remoteIP;
    }

    public void setRemoteIP(String remoteIP) {
        this.remoteIP = remoteIP;
    }

    public void setBaseContext(IContext baseContext) {
        this.baseContext = baseContext;
    }
}
