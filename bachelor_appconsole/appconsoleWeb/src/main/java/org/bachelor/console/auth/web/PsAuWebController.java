/*
 * @(#)PsAuWebController.java	Mar 18, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.console.auth.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.auth.domain.Role;
import org.bachelor.auth.service.IRoleService;
import org.bachelor.core.exception.BusinessException;
import org.bachelor.gv.domain.GlobalVariable;

/**
 * 模块功能授权维护
 * 
 * @author Team Bachelor
 */
@Controller
@RequestMapping("/auth/psau/")
public class PsAuWebController {
	@Autowired
	private IRoleService roleService = null;

	@RequestMapping("index")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("auth/psau/index");
		return mav;
	}
	
	@RequestMapping("all.htm")
	public @ResponseBody Map<String,Object> queryAll(){
		List<Role> ruleList = roleService.findAll();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", (ruleList!=null?ruleList.size():0));
		map.put("rows", (ruleList!=null?ruleList:new ArrayList<GlobalVariable>()));
		return map;
		
	}
	
	@RequestMapping("update.htm")
	@ResponseBody
	public Map<String, String> update(@RequestBody Role role){
		Map<String, String> map = new HashMap<String,String>();
		if(role != null){
			roleService.saveOrUpdate(role);
			map.put("code", "0");
		}else{
			map.put("code", "-1");
		}
		
		return map;
	}
	
	@RequestMapping("delete.htm")
	@ResponseBody
	public Map<String, String> delete(@RequestBody Role role){
		Map<String, String> map = new HashMap<String,String>();
		if(role != null){
			roleService.delete(role);
			map.put("code", "0");
		}else{
			map.put("code", "-1");
		}
		
		return map;
	}	
}
