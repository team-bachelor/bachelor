/*
 * @(#)PageInceptorServiceImpl.java	May 27, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.web.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bachelor.context.interceptor.AllManagedIntercepter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;

/**
 * 分页拦截器
 * @author Team Bachelor
 *
 */
@Component
public class PageInterceptor extends AllManagedIntercepter{
	
	private Log log = LogFactory.getLog(this.getClass());

	/* (non-Javadoc)
	 * @see org.springframework.core.Ordered#getOrder()
	 */
	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
	
	@Override
	protected int initOrder() {
		return 100;
	}

	/**
	 * 添加分页处理的拦截器
	 * 从请求中解析start, page 参数，如果这些参数有值，就设置PageHelper;没有值就跳过
	 * @param request
	 * @param response
	 * @param obj
	 * @return
	 */
	@Override
	protected boolean doPreHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) {
		try{

            String startIndexStr = request.getParameter("start");
            String pageStr = request.getParameter("page");
            if(StringUtils.isEmpty(startIndexStr) && StringUtils.isEmpty(pageStr)){
                log.debug("找不到分页标志，不开始分页处理。");
                return true;
            }

            Integer startIndex = Integer.parseInt(startIndexStr);
            Integer pageNum = Integer.parseInt(pageStr);
			
			log.debug("找到分页标志，开始分页处理。");

            PageHelper.startPage(startIndex, pageNum);
		}catch(Exception e){
			log.error(e);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.bachelor.core.interceptor.ControllerInterceptor#doPostHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	protected void doPostHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav) {

	}
	
	/* (non-Javadoc)
	 * @see org.bachelor.core.interceptor.ControllerInterceptor#doAfterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	protected void doAfterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception e) {

	}
}
