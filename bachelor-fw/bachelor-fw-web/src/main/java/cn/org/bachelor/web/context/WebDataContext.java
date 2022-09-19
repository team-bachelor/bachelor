/*
 * @(#)VariableLifecycleServiceImpl.java	May 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package cn.org.bachelor.web.context;

import cn.org.bachelor.context.IDataContext;
import cn.org.bachelor.web.util.RequestUtil;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Team Bachelor
 */
@Component
public class WebDataContext implements IDataContext {

    public static final String WEB_CONTEXT_NAME = "bachelor_context_name";
    public static final String WEB_DOC_ROOT = "bachelor_context_doc_root";
    public static final String WEB_PAGE_INFO_KEY = "bachelor_page_info";

    private static final ThreadLocal<Map<String, Object>> tls = new ThreadLocal<>();


    @Override
    public void setApplicationAttribute(String key, Object value) {
        ServletContext sc = RequestUtil.getServletContext();
        if (sc != null) {
            sc.setAttribute(key, value);
        }

    }

    /* (non-Javadoc)
     * @see cn.org.bachelor.vl.service.IVariableLifecycleService#getGloableAttribute(java.lang.String)
     */
    @Override
    public Object getApplicationAttribute(String key) {
        ServletContext sc = RequestUtil.getServletContext();
        if (sc != null) {
            return sc.getAttribute(key);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see cn.org.bachelor.vl.service.IVariableLifecycleService#removeGloableAttribute(java.lang.String)
     */
    @Override
    public Object removeApplicationAttribute(String key) {
        ServletContext sc = RequestUtil.getServletContext();
        if (sc != null) {
            Object obj = sc.getAttribute(key);
            sc.removeAttribute(key);
            return obj;
        }
        return null;
    }

    /* (non-Javadoc)
     * @see cn.org.bachelor.vl.service.IVariableLifecycleService#setSessionAttribute(java.lang.String, java.lang.Object)
     */
    @Override
    public void setSessionAttribute(String key, Object value) {
        HttpServletRequest request = RequestUtil.getRequest();

        if (request == null) {
            return;
        }
        request.getSession(true).setAttribute(key, value);
    }

    /* (non-Javadoc)
     * @see cn.org.bachelor.vl.service.IVariableLifecycleService#getSessionAttribute(java.lang.String)
     */
    @Override
    public Object getSessionAttribute(String key) {
        HttpServletRequest request = RequestUtil.getRequest();
        if (request != null && request.getSession(false) != null && request.getSession().getAttribute(key) != null) {
            return request.getSession().getAttribute(key);
        } else {
            return getRequestAttribute(key);

        }

    }


    /* (non-Javadoc)
     * @see cn.org.bachelor.vl.service.IVariableLifecycleService#removeSessionAttribute(java.lang.String)
     */
    @Override
    public Object removeSessionAttribute(String key) {
        HttpServletRequest request = RequestUtil.getRequest();
        if (request != null) {
            Object obj = request.getSession().getAttribute(key);
            request.getSession().removeAttribute(key);
            return obj;
        }
        return null;
    }

    /* (non-Javadoc)
     * @see cn.org.bachelor.vl.service.IVariableLifecycleService#setRequestAttribute(java.lang.String, java.lang.Object)
     */
    @Override
    public void setRequestAttribute(String key, Object value) {
        HttpServletRequest request = RequestUtil.getRequest();
        if (request != null) {
            request.setAttribute(key, value);
        } else {
            if (tls.get() == null) {
                Map<String, Object> map = new HashMap<>();
                tls.set(map);
            }
            tls.get().put(key, value);
        }


    }

    /* (non-Javadoc)
     * @see cn.org.bachelor.vl.service.IVariableLifecycleService#getRequestAttribute(java.lang.String)
     */
    @Override
    public Object getRequestAttribute(String key) {
        HttpServletRequest request = RequestUtil.getRequest();
        if (request != null) {
            return request.getAttribute(key);
        } else {

            if (tls.get() == null) {
                return null;
            }
            return tls.get().get(key);
        }

    }

    /* (non-Javadoc)
     * @see cn.org.bachelor.vl.service.IVariableLifecycleService#removeRequestAttribute(java.lang.String)
     */
    @Override
    public Object removeRequestAttribute(String key) {
        HttpServletRequest request = RequestUtil.getRequest();
        if (request != null) {
            Object obj = request.getAttribute(key);
            request.removeAttribute(key);
            return obj;
        }
        return null;
    }

}
