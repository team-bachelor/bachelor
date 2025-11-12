/*
 * @(#)VariableLifecycleServiceImpl.java	May 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
// 声明该类所在的包路径
package cn.org.bachelor.web.context;

// 引入 IContext 接口，该类实现了此接口的功能

import cn.org.bachelor.context.IContext;
import cn.org.bachelor.web.util.RequestUtil;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * WebContext 类实现了 IContext 接口，用于管理 Web 应用中的不同作用域的属性，
 * 包括应用程序作用域、会话作用域和请求作用域。
 *
 * @author Team Bachelor
 */
@Component
public class WebContext implements IContext {

    // 定义一个常量，用于表示 Web 上下文的名称
    public static final String WEB_CONTEXT_NAME = "bachelor_context_name";
    // 定义一个常量，用于表示 Web 文档的根目录
    public static final String WEB_DOC_ROOT = "bachelor_context_doc_root";
    // 定义一个常量，用于表示 Web 页面信息的键
    public static final String WEB_PAGE_INFO_KEY = "bachelor_page_info";

    // 使用 ThreadLocal 存储每个线程的属性映射，保证线程安全
    private static final ThreadLocal<Map<String, Object>> tls = new ThreadLocal<>();

    /**
     * 设置应用程序作用域的属性。
     *
     * @param key   属性的键
     * @param value 属性的值
     */
    @Override
    public void setApplicationAttribute(String key, Object value) {
        // 通过 RequestUtil 获取 ServletContext
        ServletContext sc = RequestUtil.getServletContext();
        // 如果 ServletContext 不为空，则设置属性
        if (sc != null) {
            sc.setAttribute(key, value);
        }
    }

    /**
     * 获取应用程序作用域的属性。
     *
     * @param key 属性的键
     * @return 属性的值，如果不存在则返回 null
     */
    @Override
    public Object getApplicationAttribute(String key) {
        // 通过 RequestUtil 获取 ServletContext
        ServletContext sc = RequestUtil.getServletContext();
        // 如果 ServletContext 不为空，则获取属性
        if (sc != null) {
            return sc.getAttribute(key);
        }
        return null;
    }

    /**
     * 移除应用程序作用域的属性。
     *
     * @param key 属性的键
     * @return 被移除的属性的值，如果不存在则返回 null
     */
    @Override
    public Object removeApplicationAttribute(String key) {
        // 通过 RequestUtil 获取 ServletContext
        ServletContext sc = RequestUtil.getServletContext();
        // 如果 ServletContext 不为空，则移除属性
        if (sc != null) {
            Object obj = sc.getAttribute(key);
            sc.removeAttribute(key);
            return obj;
        }
        return null;
    }

    /**
     * 设置会话作用域的属性。
     *
     * @param key   属性的键
     * @param value 属性的值
     */
    @Override
    public void setSessionAttribute(String key, Object value) {
        // 通过 RequestUtil 获取 HttpServletRequest
        HttpServletRequest request = RequestUtil.getRequest();
        // 如果请求不为空，则设置会话属性
        if (request == null) {
            return;
        }
        request.getSession(true).setAttribute(key, value);
    }

    /**
     * 获取会话作用域的属性。
     * 若会话中不存在该属性，则尝试从请求作用域获取。
     *
     * @param key 属性的键
     * @return 属性的值，如果不存在则返回 null
     */
    @Override
    public Object getSessionAttribute(String key) {
        // 通过 RequestUtil 获取 HttpServletRequest
        HttpServletRequest request = RequestUtil.getRequest();
        // 如果请求不为空且会话存在且会话中属性不为空，则返回会话属性
        if (request != null && request.getSession(false) != null && request.getSession().getAttribute(key) != null) {
            return request.getSession().getAttribute(key);
        } else {
            // 否则尝试从请求作用域获取属性
            return getRequestAttribute(key);
        }
    }

    /**
     * 移除会话作用域的属性。
     *
     * @param key 属性的键
     * @return 被移除的属性的值，如果不存在则返回 null
     */
    @Override
    public Object removeSessionAttribute(String key) {
        // 通过 RequestUtil 获取 HttpServletRequest
        HttpServletRequest request = RequestUtil.getRequest();
        // 如果请求不为空，则移除会话属性
        if (request != null) {
            Object obj = request.getSession().getAttribute(key);
            request.getSession().removeAttribute(key);
            return obj;
        }
        return null;
    }

    /**
     * 设置请求作用域的属性。
     * 如果请求不存在，则将属性存储在 ThreadLocal 中。
     *
     * @param key   属性的键
     * @param value 属性的值
     */
    @Override
    public void setRequestAttribute(String key, Object value) {
        // 通过 RequestUtil 获取 HttpServletRequest
        HttpServletRequest request = RequestUtil.getRequest();
        // 如果请求不为空，则设置请求属性
        if (request != null) {
            request.setAttribute(key, value);
        } else {
            // 如果请求为空，检查 ThreadLocal 中是否有映射
            if (tls.get() == null) {
                // 若没有则创建一个新的 HashMap 并存储到 ThreadLocal 中
                Map<String, Object> map = new HashMap<>();
                tls.set(map);
            }
            // 将属性存储到 ThreadLocal 的映射中
            tls.get().put(key, value);
        }
    }

    /**
     * 获取请求作用域的属性。
     * 如果请求不存在，则从 ThreadLocal 中获取属性。
     *
     * @param key 属性的键
     * @return 属性的值，如果不存在则返回 null
     */
    @Override
    public Object getRequestAttribute(String key) {
        // 通过 RequestUtil 获取 HttpServletRequest
        HttpServletRequest request = RequestUtil.getRequest();
        // 如果请求不为空，则获取请求属性
        if (request != null) {
            return request.getAttribute(key);
        } else {
            // 如果请求为空，检查 ThreadLocal 中是否有映射
            if (tls.get() == null) {
                return null;
            }
            // 从 ThreadLocal 的映射中获取属性
            return tls.get().get(key);
        }
    }

    /**
     * 移除请求作用域的属性。
     *
     * @param key 属性的键
     * @return 被移除的属性的值，如果不存在则返回 null
     */
    @Override
    public Object removeRequestAttribute(String key) {
        // 通过 RequestUtil 获取 HttpServletRequest
        HttpServletRequest request = RequestUtil.getRequest();
        // 如果请求不为空，则移除请求属性
        if (request != null) {
            Object obj = request.getAttribute(key);
            request.removeAttribute(key);
            return obj;
        }
        return null;
    }
}