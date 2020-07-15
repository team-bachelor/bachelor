/*
 * @(#)IVariableLifecycleService.java	May 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package cn.org.bachelor.context.service;

/**
 * 变量生命周期管理服务
 *
 * @author Team Bachelor
 */
public interface IVLService {


    void setGloableAttribute(String key, Object value);

    Object getGloableAttribute(String key);

    Object removeGloableAttribute(String key);

    void setSessionAttribute(String key, Object value);

    Object getSessionAttribute(String key);

    Object removeSessionAttribute(String key);

    void setRequestAttribute(String key, Object value);

    Object getRequestAttribute(String key);

    Object removeRequestAttribute(String key);


}
