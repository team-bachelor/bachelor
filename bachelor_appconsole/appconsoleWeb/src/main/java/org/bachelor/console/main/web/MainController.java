/*
 * @(#)MainController.java	Mar 5, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.console.main.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Team Bachelor
 *
 */
@Controller
public class MainController {
	
	@RequestMapping("/index.htm")
	public ModelAndView index(HttpServletRequest request){
		return new ModelAndView("login/login");
		/*
		String ie6 = "MSIE 6.0";
		String ie8 = "MSIE 8.0";
		String chrome = "Chrome";
		String firefox = "Firefox";
		
		String userAgent = request.getHeader("user-agent");
		if(userAgent.contains(ie6) || userAgent.contains(ie8)){
			return new ModelAndView("login/login");
		}else if(userAgent.contains(chrome) || userAgent.contains(firefox)){
			return new ModelAndView("redirect:http://10.156.210.18:7070/console5/login/index.htm");
		}else{
			return new ModelAndView("login/login");			
		}*/
		

		
	}

	@RequestMapping("/main.htm")
	public ModelAndView main(){
		return new ModelAndView("main");
		
	}
	
	@RequestMapping("/banner.htm")
	public ModelAndView banner(){
		return new ModelAndView("banner");
		
	}
	
	@RequestMapping("/error.htm")
	public ModelAndView error(){
		
		return new ModelAndView("error");
	}
}
