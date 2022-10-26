/*
 * @(#)VariableLifecycleServiceImpl.java	May 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package cn.org.bachelor.iam;

import cn.org.bachelor.context.ITenantContext;
import cn.org.bachelor.iam.vo.UserVo;
import cn.org.bachelor.context.IContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Team Bachelor
 */
@Service
public class IamContext implements ITenantContext {

    @Resource
    private IContext baseContext;

    private String remoteIP;

    public IContext getBaseContext() {
        return baseContext;
    }

    public void setCurrentUser(UserVo user) {
        baseContext.setRequestAttribute(IamConstant.USER_KEY, user);
    }

    public UserVo getCurrentUser() {
        Object uo = baseContext.getRequestAttribute(IamConstant.USER_KEY);
        if (uo != null) {
            return (UserVo) uo;
        }
        return null;
    }

    public String getCurrentUserCode(){
        UserVo user = getCurrentUser();
        String name = "user_unknown";
        if (user != null && user.getCode() != null) {
            name = user.getCode();
        } else if (getRemoteIP() != null) {
            name = getRemoteIP();
        }
        return name;
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

    @Override
    public String getTenantId() {
        UserVo user = getCurrentUser();
        if(user != null){
            return user.getTenantId();
        }
        return null;
    }

    @Override
    public String getOrgId() {
        UserVo user = getCurrentUser();
        if(user != null){
            return user.getOrgId();
        }
        return null;
    }
}
