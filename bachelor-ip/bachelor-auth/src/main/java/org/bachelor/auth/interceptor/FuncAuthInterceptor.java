/*
 * @(#)AuthServiceInterceptor.java	May 9, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.auth.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bachelor.auth.common.AuthConstant;
import org.bachelor.auth.domain.AuthFunction;
import org.bachelor.auth.domain.Role;
import org.bachelor.context.interceptor.AllManagedIntercepter;
import org.bachelor.context.service.IVLService;
import org.bachelor.org.domain.User;
import org.bachelor.ps.domain.Function;
import org.bachelor.ps.domain.ProjectProperty;
import org.bachelor.ps.service.IFunctionService;
import org.bachelor.ps.service.IProjectPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Team Bachelor
 *
 */
@Service
public class FuncAuthInterceptor extends AllManagedIntercepter{

	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private IVLService vlService;
	
	@Autowired
	private IProjectPropertyService ppService;
	
	@Autowired
	private IFunctionService funcService;
	
	private List<String> excludeRolelList = null;
	
	private List<String> excludeUrlList = null;
	
	public List<String> getExcludeUrlList() {
		return excludeUrlList;
	}

	public void setExcludeUrlList(List<String> excludeUrlList) {
		this.excludeUrlList = excludeUrlList;
	}

	public List<String> getExcludeRolelList() {
		return excludeRolelList;
	}

	public void setExcludeRolelList(List<String> excludeRolelList) {
		this.excludeRolelList = excludeRolelList;
	}

	@Override
	protected void doAfterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception e) {
		 
	}

	@Override
	protected void doPostHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav) {
		 
	}

	/**
	 * 1.从Vl中取得当前用户的角色
		2.检查角色是否有访问当前url的权限
		3.如果有则返回true
		4.如果没有，则重定向到错误页面
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected boolean doPreHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) {
		/** 项目信息实体 **/
		ProjectProperty pp = ppService.get();
		/** 得到当前登录的角色集合 **/
		List<Role> roles = (List<Role>) vlService.getSessionAttribute(AuthConstant.LOGIN_USER_ROLE);
		/** 得到当前登录的用户信息  **/
		User user = (User) vlService.getSessionAttribute(AuthConstant.LOGIN_USER);
		/** 得到请求的URL **/
		String requestUrl = request.getServletPath();
		
		/** 如果请求路径是登录就不做处理，直接返回 **/
		if(excludeUrlList != null && excludeUrlList.size() > 0){
			for(String excludeUrl: excludeUrlList){
				if(requestUrl.equals(excludeUrl)){
					log.info(requestUrl + "不需要进行 登录验证。");
					return true;
				}
			}			
		}
		
		/** 如果请求路径是登录就不做处理，直接返回 **/
		if(requestUrl.equals(pp.getErrorPath()) || 
				requestUrl.equals(pp.getIndexPath()) ||
				requestUrl.equals(pp.getLoginAuthPath()) ||
				requestUrl.equals(pp.getLoginShowPath()) ){
			return true;
		}
		
		/** 跳过配置的角色名称 **/
		if(validateLoginUser(roles)){
			
			return true;
		}
		/** 根据URL查询，URL是否已经配置的URL**/
		Function func = funcService.findByUrl(requestUrl);
		/** 如果请求的URL没有配置功能，直接跳过，不对其进行验证 **/
		if(func==null || func.getId()==null &&
				StringUtils.isEmpty(func.getId())){
			
			return true;
		}
		/** 判断请求URI是否存在于登录权限内**/
		if(roles!=null && roles.size()>0){
				for(Role role : roles){
						List<AuthFunction> afs = role.getAuthFunctions();
						if(afs!=null && afs.size()>0){
								for(AuthFunction af : afs){
										/** 角色存储的URL **/
										String entryPath = af.getFunc().getEntryPath();
										/** 请求路径在登录的角色中是否存有，如果有返回true, 否就继续执行 **/
										if(requestUrl.equals(entryPath)){
											return true;
										}
								}
						}
				}
		}
		//没有访问功能点权限，抛出异常
		FuncAuthFailedException e = new FuncAuthFailedException(user.getId()+"用户没有"+func.getName()+"功能点权限.");
		throw e;
//		/** 定向至错误页面 (带有项目上下文名称)**/
//		String errorPath =pp.getProjectErrorPath(true);
//		try {
//			response.sendRedirect(errorPath);
//		} catch (IOException e) {
//			log.error(e.getMessage(),e);
//		}
//		return false;
	}
	
	/**
	 * 验证登录用户的角色
	 * @return
	 */
	public boolean validateLoginUser(List<Role> roles){
		if(excludeRolelList!=null && excludeRolelList.size()>0){
			for(String roleName:excludeRolelList){
				for(Role role : roles){
					/** 只要找到包涵的角色名称就返回 **/
					if(roleName.equals(role.getName())){
						return true;
					}
				} 
			}
		}
		return false;
	}

	@Override
	protected int initOrder() {
		return 2;
	}
}
