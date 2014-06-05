/*
 * @(#)LoginWebController.java	May 22, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.demo.login.web;

import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.auth.common.AuthConstant;
import org.bachelor.auth.domain.LoginResult;
import org.bachelor.auth.service.ILoginService;
import org.bachelor.bpm.common.Constant;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.facade.service.IContextService;
import org.bachelor.facade.service.impl.BpmContextService;

/**
 * @author
 *
 */
@Controller
@RequestMapping("login")
public class LoginWebController {

	@Autowired
	private ILoginService loginService;
	
	@Autowired
	private IContextService ctxService;
	
	@Autowired
	private HistoryService historyService;
	
	@RequestMapping("index")
	public ModelAndView index(){
		return new ModelAndView("login/login");
	}
	@RequestMapping("login")
	public ModelAndView login(String userId, String pwd){
		LoginResult result = loginService.login(userId, pwd);
		if(result.getResult() == AuthConstant.RESULT_SUCCESS){
			String index = ctxService.getProjectProperty().getIndexPath();
			/*ModelAndView mav = new ModelAndView("redirect:/report/index.htm");
			return mav;*/
			return new ModelAndView("redirect:" + index);
		}else{
			String loginPath = ctxService.getProjectProperty().getLoginShowPath();
			
			return new ModelAndView("forward:" + loginPath);
		}
	}
	
	@RequestMapping("logoff")
	public ModelAndView logoff(){
		ctxService.removeSessionAttribute(AuthConstant.LOGIN_USER);
		ctxService.removeSessionAttribute(AuthConstant.LOGIN_USER_CLIENT);
		ctxService.removeSessionAttribute(AuthConstant.LOGIN_USER_ROLE);
		String loginPath = ctxService.getProjectProperty().getLoginShowPath();
		return new ModelAndView("redirect:" + loginPath);
	}
}