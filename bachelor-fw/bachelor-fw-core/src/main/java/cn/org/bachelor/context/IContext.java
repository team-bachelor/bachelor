/*
 * @(#)IVariableLifecycleService.java	May 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package cn.org.bachelor.context;

/**
 * 用于管理不同级别的公共变量
 *
 * @author Team Bachelor
 */
public interface IContext {


    /**
     * 设置应用级变量
     * @param key 变量key
     * @param value 变量值
     */
    void setApplicationAttribute(String key, Object value);

    /**
     * 获取应用级变量
     * @param key 变量key
     */
    Object getApplicationAttribute(String key);

    /**
     * 删除应用级变量
     * @param key 变量key
     */
    Object removeApplicationAttribute(String key);

    /**
     * 设置会话级变量
     * @param key 变量key
     * @param value 变量值
     */
    void setSessionAttribute(String key, Object value);

    /**
     * 设置会话级变量
     * @param key 变量key
     */
    Object getSessionAttribute(String key);

    /**
     * 删除会话级变量
     * @param key 变量key
     */
    Object removeSessionAttribute(String key);

    /**
     * 设置请求级变量
     * @param key 变量key
     * @param value 变量值
     */
    void setRequestAttribute(String key, Object value);

    /**
     * 获取请求级变量
     * @param key 变量key
     */
    Object getRequestAttribute(String key);

    /**
     * 删除请求级变量
     * @param key 变量key
     */
    Object removeRequestAttribute(String key);


}
