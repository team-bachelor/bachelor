/*
 * @(#)MenuWebController.java	Mar 7, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.console.menu.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.console.common.Constant;
import org.bachelor.menu.domain.Menu;
import org.bachelor.menu.service.IMenuService;
import org.bachelor.ps.domain.Function;
import org.bachelor.ps.domain.ProjectModule;
import org.bachelor.ps.domain.ProjectProperty;
import org.bachelor.ps.service.IFunctionService;
import org.bachelor.ps.service.IProjectModuleService;
import org.bachelor.ps.service.IProjectPropertyService;
import org.bachelor.web.ztree.ZTreeTreeNode;
import org.bachelor.web.ztree.ZTreeTreeUtil;

/**
 * 菜单管理的Web控制器
 * 
 * @author Team Bachelor
 *
 */
@Controller
@RequestMapping("/menu/")
public class MenuWebController {
	
	@Autowired
	private IMenuService menuService;
	
	@Autowired
	private IProjectPropertyService ppService;
	
	@Autowired
	private IProjectModuleService projectModuleService;

	@Autowired
	private IFunctionService functionService;
	
	@RequestMapping("index")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("menu/index");
		return mav;
	}
	
	@RequestMapping("tree")
	@ResponseBody 
	public List<ZTreeTreeNode> all(){
		ProjectProperty pp = ppService.get();
		//根节点:项目名称
		ZTreeTreeNode root = new ZTreeTreeNode();
		root.setId("");
		root.setName(pp.getTitle());
		root.setNodeType(Constant.ZTREE_NODE_TYPE_PROJECT);
		
		
		List<ZTreeTreeNode> nodeList = new ArrayList<ZTreeTreeNode>();
		nodeList.add(root);
		
		//子节点：各模块的层级信息
		List<Menu> allMenu = menuService.findManatinMenus();
		List<ZTreeTreeNode> moduleNodeList = ZTreeTreeUtil.toZTreeNode(allMenu,new MenuZTreeAdapter());
		root.setChildren(moduleNodeList);
		return nodeList;
	}
	
	@RequestMapping("detail")
	public ModelAndView detail(@RequestParam(value="id") String id){
		Menu menu = menuService.findById(id);

		ModelAndView mav = new ModelAndView("menu/detail", "model", menu);
		return mav;
	}

	@RequestMapping("add")
	public ModelAndView add(@RequestParam(value="parentId", required=false)String parentId){
		Menu menu = new Menu();
		Menu parentMenu = new Menu();
		parentMenu.setId(parentId);
		menu.setParentMenu(parentMenu);
		
		if(parentId != null){
			menu.getParentMenu().setId(parentId);
		}
		ModelAndView mav = new ModelAndView("menu/add", "model", menu);
		return mav;
	}
	
	@RequestMapping("doAdd")
	@ResponseBody
	public Map<String,Object> doAdd(Menu menu){
		Map<String,Object> result_map = new HashMap<String,Object>();
		try{
			menuService.save(menu);
			result_map.put("id", menu.getId());
			result_map.put("resultCode", "0");
		}catch(Exception e){
			result_map.put("resultCode", "1");
			e.printStackTrace();
		}
		return result_map;
	}
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String,Object> update(Menu menu){
		Map<String,Object> result_map = new HashMap<String,Object>();
		try{
			menuService.update(menu);
			result_map.put("resultCode", "0");
		}catch(Exception e){
			result_map.put("resultCode", "1");
			e.printStackTrace();
		}
		return result_map;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam(value="id")String id){
		Map<String,Object> result_map = new HashMap<String,Object>();
		try{
			menuService.deleteById(id);
			result_map.put("resultCode", "0");
		}catch(Exception e){
			result_map.put("resultCode", "1");
			e.printStackTrace();
		}
		return result_map;
	}
	
	@RequestMapping("move")
	@ResponseBody
	public  Map<String, Object> moveMentItem(Menu paraMenu){
		Map<String,Object> result_map = new HashMap<String,Object>();
		try{
			String parentId = paraMenu.getParentMenu().getId();
			Menu menu = menuService.findById(paraMenu.getId());
			Menu parentMenu = new Menu();
			parentMenu.setId(parentId);
			menu.setParentMenu(parentMenu);
			menuService.update(menu);
			result_map.put("resultCode", "0");
		}catch(Exception e){
			result_map.put("resultCode", "1");
			e.printStackTrace();
		}
		return result_map;
	}
	
	/**
	 * 通过没有关联的功能,自动生成菜单
	 * @return
	 */
	@RequestMapping("authCreate")
	@ResponseBody
	public Map<String,Object> authCreateMenuStructure(String nodeId,String targetId){
		Map<String,Object> result_map = new HashMap<String,Object>();
		try{
				List<ProjectModule> pms = projectModuleService.findModuleByParentId(nodeId);
				if(pms==null || pms.size()<=0){
					Function func = functionService.findById(nodeId);
					Set<Function> funcs = new HashSet<Function>();
					funcs.add(func);
					if(funcs!=null && funcs.size()>=0){
						
						saveMenuByFunc(funcs,targetId);
					} 
				} else {
					
					saveMenuByPm(pms,targetId);
				}
				result_map.put("resultCode", "1");
		}catch(Exception e){
			result_map.put("resultCode", "0");
			e.printStackTrace();
		}
		return result_map;
	}
	
	private Menu menu ;
	private Menu pMenu;
	
	public void saveMenuByPm(List<ProjectModule> pms,String parentId){
		for(ProjectModule pm : pms){
			String uuid = UUID.randomUUID().toString();
			menu = new Menu();
			pMenu = new Menu();
			if(StringUtils.isEmpty(parentId)){
				
				parentId = null;
			}
			pMenu.setId(parentId);
			menu.setParentMenu(pMenu);
			menu.setName(pm.getName());
			menu.setId(uuid);
			menu.setShowOrder((!StringUtils.isEmpty(pm.getOrderId())?Integer.valueOf(pm.getOrderId()):0));
			menu.setFlag("0");
			menu.setOpenType("1");
			menu.setDescription(pm.getName());
			menuService.saveMenu(menu);
			if(pm.getChildModules()!=null && pm.getChildModules().size()>0){
				
				List<ProjectModule> tempPms = new ArrayList<ProjectModule>(pm.getChildModules());
				saveMenuByPm(tempPms,uuid);
			}
			
			if(pm.getFunctions()!=null && pm.getFunctions().size()>0){
				
				saveMenuByFunc(pm.getFunctions(),uuid);
			}
		}
	}
	
	public void saveMenuByFunc(Set<Function> funcs,String parentId){
		for(Function func : funcs){
			String uuid = UUID.randomUUID().toString();
			menu = new Menu();
			pMenu = new Menu();
			if(StringUtils.isEmpty(parentId)){
				
				parentId = null;
			}
			pMenu.setId(parentId);
			menu.setParentMenu(pMenu);
			menu.setName(func.getName());
			menu.setFunc(func);
			menu.setFlag("0");
			menu.setOpenType("1");
			menu.setId(uuid);
			menu.setDescription(func.getName());
			menuService.saveMenu(menu);
		}
	}
	
	/**
	 * 跳转至生成菜单首页
	 * @return
	 */
	@RequestMapping("cmIndex")
	public ModelAndView createMenuIndex(){
		
		return new ModelAndView("/menu/createMenu");
	}
}
