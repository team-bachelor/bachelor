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
import org.bachelor.context.service.IVLService;
import org.bachelor.dao.DaoConstant;
import org.bachelor.dao.vo.PageVo;
import org.bachelor.web.service.IPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * 分页拦截器
 * @author Team Bachelor
 *
 */
@Component
public class PageInterceptor extends AllManagedIntercepter{
	@Autowired
	private IVLService vlService;
	
	@Autowired(required=false)
	private IPageService pService;
	
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

	/* (non-Javadoc)
	 * @see org.bachelor.core.interceptor.ControllerInterceptor#doPreHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	protected boolean doPreHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) {
		try{
			String startIndexStr = request.getParameter("start");
			String limitStr = request.getParameter("limit");
			String pageStr = request.getParameter("page");
			if(StringUtils.isEmpty(startIndexStr) && StringUtils.isEmpty(limitStr) && StringUtils.isEmpty(pageStr)){
				log.debug("找不到分页标志，不开始分页处理。");
				return true;
			}
			//取得每页记录数
			int pageNum = 10;
			if(pService != null){
				pageNum = pService.getPageRowNum();
			}
			int startIndex = 0;
			int endIndex = 0;
			int page = 0;
			if(!StringUtils.isEmpty(pageStr)){
				page = Integer.parseInt(StringUtils.isEmpty(pageStr)?"0":pageStr);
				startIndex = page*pageNum;
				endIndex = (page+1)*pageNum;
			}else{
				startIndex = Integer.parseInt(StringUtils.isEmpty(startIndexStr)?"0":startIndexStr);
				pageNum = Integer.parseInt(StringUtils.isEmpty(limitStr)?"0":limitStr);
				endIndex = startIndex + pageNum;
			}
			
			if(startIndex ==-1 || endIndex == -1){
				log.debug("分页参数不对，不做分页处理。");
				return true;
			}
			
			log.debug("找到分页标志，开始分页处理。");
			//创建分页信息，保存在Request中
			PageVo pageVo = new PageVo();
			pageVo.setStartIndex(startIndex);
			pageVo.setEndIndex(endIndex);
			pageVo.setPageRowNum(pageNum);
			if(page>=0){
				pageVo.setPage(page);
			}
			vlService.setRequestAttribute(DaoConstant.PAGE_INFO, pageVo);
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
