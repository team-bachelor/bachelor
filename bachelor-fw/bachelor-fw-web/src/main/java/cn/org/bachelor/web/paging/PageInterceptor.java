/*
 * @(#)PageInceptorServiceImpl.java	May 27, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package cn.org.bachelor.web.paging;

import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 分页拦截器
 *
 * @author Team Bachelor
 */
public class PageInterceptor extends HandlerInterceptorAdapter {

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
        try {
            PageHelper.clearPage();
            String pageNum = request.getParameter("pageNum");
            String pageSize = request.getParameter("pageSize");
            if (StringUtils.isEmpty(pageNum) && StringUtils.isEmpty(pageSize)) {
                log.debug("找不到分页标志，不开始分页处理。");
                return true;
            }

            Integer pageNumInt = Integer.parseInt(pageNum);
            Integer pageSizeInt = Integer.parseInt(pageSize);

            log.debug("找到分页标志，开始分页处理。");
            PageHelper.startPage(pageNumInt, pageSizeInt);
        } catch (Exception e) {
            log.error(e);
        }
        return true;
    }

}
