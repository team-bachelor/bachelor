/*
 * @(#)PageInceptorServiceImpl.java	May 27, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package cn.org.bachelor.web.paging;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 分页拦截器
 *
 * @author Team Bachelor
 */
public class PageInterceptor extends HandlerInterceptorAdapter {

    public static final String PAGE_NUM = "pageNum";
    public static final String PAGE_SIZE = "pageSize";
    public static final String PAGE_FLAG = "page";
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 添加分页处理的拦截器
     * 从请求中解析start, page 参数，如果这些参数有值，就设置PageHelper;没有值就跳过
     *
     * @param request
     * @param response
     * @param obj
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object obj) {
        String flag = request.getParameter(PAGE_FLAG);
        if("n".equals(flag) || "N".equals(flag)){
            PageHelper.clearPage();
            return true;
        }
        try {
            String pageNum = null;
            String pageSize = null;
            if (request.getMethod().equals(HttpMethod.GET.name())) {
                pageNum = request.getParameter(PAGE_NUM);
                pageSize = request.getParameter(PAGE_SIZE);
            } else if (request.getMethod().equals(HttpMethod.POST.name())) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                StringBuilder body = new StringBuilder("");
                reader.lines().forEach(s -> {
                    body.append(s);
                });
                JSONObject o = JSONObject.parseObject(body.toString());
                pageNum = o.getString(PAGE_NUM);
                pageSize = o.getString(PAGE_SIZE);

            }
            if (StringUtils.isEmpty(pageNum) && StringUtils.isEmpty(pageSize)) {
                log.debug("找不到分页标志，不开始分页处理。");
                return true;
            }

            Integer pageNumInt = Integer.parseInt(pageNum);
            Integer pageSizeInt = Integer.parseInt(pageSize);

            log.debug("找到分页标志，开始分页处理。");
            PageHelper.clearPage();
            PageHelper.startPage(pageNumInt, pageSizeInt);
        } catch (Exception e) {
            log.error(e);
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        PageHelper.clearPage();
    }
}
