/*
 * @(#)LoginWebController.java	May 22, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.console.login.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.auth.common.AuthConstant;
import org.bachelor.auth.domain.LoginResult;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.service.ILoginCallback;
import org.bachelor.auth.service.ILoginService;
import org.bachelor.context.common.ContextConstant;
import org.bachelor.context.service.IVLService;
import org.bachelor.facade.service.IContextService;

/**
 * @author Team Bachelor
 *
 */
@Controller
@RequestMapping("login/")
public class LoginWebController {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private ILoginService loginService;
	
	@Autowired
	private IVLService vlService;
	
	@Autowired
	private IContextService ctxService;
	
	@RequestMapping("index")
	public ModelAndView index(){
		return new ModelAndView("login/login");
	}
	
	@RequestMapping("login")
	public ModelAndView login(String userName, String password){
		LoginResult result = loginService.login(userName, password, new ILoginCallback() {
			
			@Override
			public boolean onSuccess(LoginResult loginResult) {
				List<Role> roles = loginResult.getRoles();
				boolean flag = false;
				if(roles!=null && roles.size()>0){
					for(Role role : roles){
						//超级管理员ID
						if(role.getId().trim().equals(AuthConstant.SUPERMANAGE_ID)){
							flag = true;
							break;
						}
					}
				} 
				if(!flag) {
					loginResult.setMsg("没有登录权限");
				}
				return flag;
			}
		});
		if(result.isLogin()){
			return new ModelAndView("redirect:/main.htm", "model", vlService.getSessionAttribute(AuthConstant.LOGIN_USER));
		}else{
			Map<String,String> login_info = new HashMap<String,String>();
			login_info.put("errorInfo", result.getMsg());
			return new ModelAndView("login/login", "model", login_info);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("loginoff")
	public ModelAndView loginoff(){
		ctxService.removeSessionAttribute(AuthConstant.LOGIN_USER);
		ctxService.removeSessionAttribute(AuthConstant.LOGIN_USER_CLIENT);
		ctxService.removeSessionAttribute(AuthConstant.LOGIN_USER_ROLE);
		List<String> urls = (List<String>) vlService.getSessionAttribute(AuthConstant.NO_LOGIN_AUTH_LIST);
		String loginPath = "/index.htm";
		if(urls!=null && urls.size()>0){
			loginPath = urls.get(1);
		}
		return new ModelAndView("redirect:" + loginPath);
	}
}
