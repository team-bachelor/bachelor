package org.bachelor.demo.main.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.auth.common.AuthConstant;
import org.bachelor.auth.domain.Role;
import org.bachelor.facade.service.IContextService;

@Controller
public class MainController {

	@Autowired
	private IContextService contextService;
	
	@RequestMapping("index")
	public ModelAndView index(){
		
		return new ModelAndView("main");
	}
	
	/**
	 * 显示用户信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("persionalInfo")
	public ModelAndView persionalInfo(){
		List<Role> roles = (List<Role>)contextService.getSessionAttribute(AuthConstant.LOGIN_USER_ROLE);
		return new ModelAndView("userInfo","model",roles);
	}
}
