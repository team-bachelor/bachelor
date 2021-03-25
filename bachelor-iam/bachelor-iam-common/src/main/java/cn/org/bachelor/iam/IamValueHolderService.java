/*
 * @(#)VariableLifecycleServiceImpl.java	May 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package cn.org.bachelor.iam;

import cn.org.bachelor.iam.vo.UserVo;
import cn.org.bachelor.context.IVLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Team Bachelor
 */
@Service
public class IamValueHolderService {

    @Autowired
    private IVLService valueHolderService;

    private String remoteIP;

    public IVLService getValueHolderService() {
        return valueHolderService;
    }

    public void setCurrentUser(UserVo user) {
        valueHolderService.setRequestAttribute(IamConstant.USER_KEY, user);
    }

    public UserVo getCurrentUser() {
        Object uo = valueHolderService.getRequestAttribute(IamConstant.USER_KEY);
        UserVo user = null;
        if (uo != null) {
            user = (UserVo) uo;
            return user;
        }
        return user;
    }

    public String getCurrentUserCode(){
        UserVo user = getCurrentUser();
        String name = "user_unknow";
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

    public void setValueHolderService(IVLService valueHolderService) {
        this.valueHolderService = valueHolderService;
    }
}
