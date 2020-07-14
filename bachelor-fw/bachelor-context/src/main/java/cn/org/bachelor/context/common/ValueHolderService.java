/*
 * @(#)VariableLifecycleServiceImpl.java	May 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package cn.org.bachelor.context.common;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Team Bachelor
 */
@Service
public class ValueHolderService {

    private static ThreadLocal<Map<String, Object>> tls = new ThreadLocal<Map<String, Object>>();



    /* (non-Javadoc)
     * @see cn.org.bachelor.vl.service.IVariableLifecycleService#setRequestAttribute(java.lang.String, java.lang.Object)
     */
    public void setRequestAttribute(String key, Object value) {

        if (tls.get() == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            tls.set(map);
        }
        tls.get().put(key, value);


    }

    /* (non-Javadoc)
     * @see cn.org.bachelor.vl.service.IVariableLifecycleService#getRequestAttribute(java.lang.String)
     */
    public Object getRequestAttribute(String key) {

        if (tls.get() == null) {
            return null;
        }
        return tls.get().get(key);

    }

    /* (non-Javadoc)
     * @see cn.org.bachelor.vl.service.IVariableLifecycleService#removeRequestAttribute(java.lang.String)
     */
    public Object removeRequestAttribute(String key) {
        Object obj = tls.get().remove(key);
        return obj;
    }

}
