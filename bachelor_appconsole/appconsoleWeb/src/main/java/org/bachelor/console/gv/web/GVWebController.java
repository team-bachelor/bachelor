/*
 * @(#)MenuWebController.java	Mar 7, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.console.gv.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.gv.domain.GlobalVariable;
import org.bachelor.gv.service.IGVService;

/**
 * @author Team Bachelor
 *
 */
@Controller
@RequestMapping("gv/variable")
public class GVWebController {
	
	@Autowired
	private IGVService gvService = null;

	@RequestMapping("index")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("gv/variable/index");
		return mav;
	}
	
	@RequestMapping("queryAll.htm")
	public @ResponseBody Map<String,Object> queryAll(GlobalVariable gv){
		List<GlobalVariable> gvList = gvService.queryAll(gv);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", (gvList!=null?gvList.size():0));
		map.put("rows", (gvList!=null?gvList:new ArrayList<GlobalVariable>()));
		return map;
		
	}
	
	@RequestMapping("index_5")
	public ModelAndView index_5(){
		/*List<GlobalVariable> gvList = gvService.queryAll();
		
		ModelAndView mav = new ModelAndView("gv/variable/index", "model", gvList);*/
		return new ModelAndView("gv/variable/index");
	}
	
	@RequestMapping("update.htm")
	@ResponseBody
	public Map<String, String> update(GlobalVariable gv){
		Map<String, String> map = new HashMap<String,String>();
		if(gv != null){
			GlobalVariable addGv = gvService.findByName(gv.getName());
			if(gv.getId()==null || "".equals(gv.getId())){
				if(addGv!=null){
					map.put("ResultCode", "2");
				} else {
					gvService.saveOrUpdate(gv);
					map.put("ResultCode", "0");
				}
			} else {
				if(addGv!=null){
					addGv.setName(gv.getName());
					addGv.setDescription(gv.getDescription());
					addGv.setFlag(gv.getFlag());
					addGv.setValue(gv.getValue());
					if(addGv.getId().equals(gv.getId())){
						gvService.saveOrUpdate(addGv);
						map.put("ResultCode", "0");
					} else {
						map.put("ResultCode", "2");
					}
				} else {
					gvService.saveOrUpdate(addGv);
					map.put("ResultCode", "0");
				}
			}
		}else{
			map.put("ResultCode", "1");
		}
		
		return map;
	}
	
	@RequestMapping("delete.htm")
	@ResponseBody
	public Map<String, String> delete(@RequestParam(value = "delInfo") String delInfo){
		Map<String, String> map = new HashMap<String,String>();
		if(delInfo!=null && !"".equals(delInfo)){
			gvService.batchDeleteById(delInfo);
			map.put("ResultCode", "0");
		} else{
			map.put("ResultCode", "1");
		}
		return map;
	}	
}
