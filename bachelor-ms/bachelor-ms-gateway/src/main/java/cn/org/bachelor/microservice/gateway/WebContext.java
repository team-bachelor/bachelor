/*
 * @(#)VariableLifecycleServiceImpl.java	May 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package cn.org.bachelor.microservice.gateway;

import cn.org.bachelor.context.IContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Team Bachelor
 */
@Component
@Primary
public class WebContext implements IContext {

    private static final ThreadLocal<Map<String, Object>> tls = new ThreadLocal<>();

    @Override
    public void setApplicationAttribute(String key, Object value) {
        ServletContext sc = RequestUtils.getServletContext();
        if (sc != null) {
            sc.setAttribute(key, value);
        }

    }

    @Override
    public Object getApplicationAttribute(String key) {
        ServletContext sc = RequestUtils.getServletContext();
        if (sc != null) {
            return sc.getAttribute(key);
        }
        return null;
    }

    @Override
    public Object removeApplicationAttribute(String key) {
        ServletContext sc = RequestUtils.getServletContext();
        if (sc != null) {
            Object obj = sc.getAttribute(key);
            sc.removeAttribute(key);
            return obj;
        }
        return null;
    }

    @Override
    public void setSessionAttribute(String key, Object value) {
        HttpServletRequest request = RequestUtils.getRequest();

        if (request == null) {
            return;
        }
        request.getSession(true).setAttribute(key, value);
    }


    @Override
    public Object getSessionAttribute(String key) {
        HttpServletRequest request = RequestUtils.getRequest();
        if (request != null && request.getSession(false) != null && request.getSession().getAttribute(key) != null) {
            return request.getSession().getAttribute(key);
        } else {
            return getRequestAttribute(key);

        }

    }

    @Override
    public Object removeSessionAttribute(String key) {
        HttpServletRequest request = RequestUtils.getRequest();
        if (request != null) {
            Object obj = request.getSession().getAttribute(key);
            request.getSession().removeAttribute(key);
            return obj;
        }
        return null;
    }

    @Override
    public void setRequestAttribute(String key, Object value) {
        HttpServletRequest request = RequestUtils.getRequest();
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

    @Override
    public Object getRequestAttribute(String key) {
        HttpServletRequest request = RequestUtils.getRequest();
        if (request != null) {
            return request.getAttribute(key);
        } else {

            if (tls.get() == null) {
                return null;
            }
            return tls.get().get(key);
        }

    }

    @Override
    public Object removeRequestAttribute(String key) {
        HttpServletRequest request = RequestUtils.getRequest();
        if (request != null) {
            Object obj = request.getAttribute(key);
            request.removeAttribute(key);
            return obj;
        }
        return null;
    }

}
