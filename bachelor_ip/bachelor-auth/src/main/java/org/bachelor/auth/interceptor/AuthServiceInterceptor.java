/*
 * @(#)AuthServiceInterceptor.java	May 9, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.auth.interceptor;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.auth.common.AuthConstant;
import org.bachelor.auth.domain.AuthFunction;
import org.bachelor.auth.domain.LoginResult;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.service.IAuthRoleUserService;
import org.bachelor.auth.service.ILoginService;
import org.bachelor.context.interceptor.AllManagedIntercepter;
import org.bachelor.org.domain.User;
import org.bachelor.ps.domain.ProjectProperty;
import org.bachelor.ps.service.IProjectPropertyService;

/**
 * @author Team Bachelor
 *
 */
@Service
public class AuthServiceInterceptor extends AllManagedIntercepter{

	private Log log = LogFactory.getLog(this.getClass());
	@Autowired
	private ILoginService loginService;
	
	@Autowired
	private IProjectPropertyService ppService;
	
	@Autowired
	private IAuthRoleUserService authRoleUserService;
	
	private List<String> excludeUrlList = null;
	 
	private void redirectToLogin(HttpServletResponse response){
		ProjectProperty pp = ppService.get();
		String loginUrl = pp.getProjectLoginShowPath(true);
		if(!StringUtils.isEmpty(loginUrl)){
			try {
				response.sendRedirect(loginUrl);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}else{
			log.error("没有配置LoginPath!");
		}
		
	}

	/**
	 * @return the excludeUrlList
	 */
	public List<String> getExcludeUrlList() {
		return excludeUrlList;
	}

	/**
	 * @param excludeUrlList the excludeUrlList to set
	 */
	public void setExcludeUrlList(List<String> excludeUrlList) {
		this.excludeUrlList = excludeUrlList;
	}

	@Override
	protected void doAfterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception e) {
		 
	}

	@Override
	protected void doPostHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav) {
		 
	}

	@Override
	protected boolean doPreHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) {
		log.info("判断当前资源是否需要验证");
		String servletPath = request.getServletPath();
		//如果是登陆页面，不需要验证
		ProjectProperty pp = ppService.get();
		if(servletPath.equals(pp.getErrorPath()) || 
				servletPath.equals(pp.getInfoPath()) || 
				servletPath.equals(pp.getLoginAuthPath()) ||
				servletPath.equals(pp.getLoginShowPath()) ){
			log.info(servletPath + "不需要进行 登录验证。");
			return true;
		}
		if(excludeUrlList != null && excludeUrlList.size() > 0){
			for(String excludeUrl: excludeUrlList){
				if(servletPath.equals(excludeUrl)){
					log.info(servletPath + "不需要进行 登录验证。");
					return true;
				}
			}			
		}
		
		//处理不需要登录授权就可以使用的功能
		String userName = request.getHeader(AuthConstant.AUTH_NAME);
		if(StringUtils.isBlank(userName) && 
				(request.getSession(false) == null ||
				request.getSession(false).getAttribute(AuthConstant.LOGIN_USER) == null)){
			//没有单点登录信息，也没有登录信息
			
			//检查访客用户是否有授权功能
			List<Role> roles = authRoleUserService.findRolesByUserId("guest");
			if(roles != null && roles.size() > 0){
				for(Role role : roles){
					if(role != null && role.getAuthFunctions() != null){
						for(AuthFunction func : role.getAuthFunctions()){
							if(func.getFunc().getEntryPath().equalsIgnoreCase(servletPath)){
								//当前访问路径没有权限认证
								//模仿guest用户登录并将用户录信息保存在session中。
								//用户角色是guest角色
								LoginResult result = loginService.login("guest", "123456", true);
								
								break;
							}
						}
					}					
				}
			}
		}


		log.info(servletPath + " 需要进行 登录验证。");
		
		
		log.info("auth_name:" + userName);
		//判断请求是否来自信息中心
		if(userName != null){
			log.info("请求来自信息中心，进行单点登录验证");
			if(userName.equals(AuthConstant.AUTH_FAILED_VALUE)){
				log.info("用户(" + userName + ")验证失败");
				log.info("转发到登录页面。");
				redirectToLogin(response);
				return false;
			}else{
				log.info("认证用户");
				LoginResult loginResult = loginService.login(userName, null, true);
				if(loginResult.getResult() == AuthConstant.RESULT_SUCCESS){
					return true;
				}else{
					log.error("单点用户(" + userName + ")在本地库不存在。");
					redirectToLogin(response);
					return false;
				}
				
			}
		}else{
			log.info("请求不是来自信息中心。");
			log.info("判断用户是否已经登录。如果没有登录则跳转到登录页面。");
			if(request.getSession(false) == null ){
				log.info("用户没有登录或者session超时。重新登录");
				redirectToLogin(response);
				return false;
			}
			Object attributeObj = request.getSession(false).getAttribute(AuthConstant.LOGIN_USER);
			if(attributeObj != null && attributeObj instanceof User){
				User user = (User)attributeObj;
				if(user.getId() != null && !user.getId().trim().equals("")){
					//用户已经登录
					log.info("用户(" + user.getId() + ")已经登录");
					return true;
				}else{
					log.info("用户没有登录或者session超时。");
					redirectToLogin(response);
					return false;
				}
			}else{
				redirectToLogin(response);
				return false;
			}
		}
	}

	@Override
	protected int initOrder() {
		return 0;
	}
}
