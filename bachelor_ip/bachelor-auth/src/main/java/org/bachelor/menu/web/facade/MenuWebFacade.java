/*
 * @(#)MenuWebFacade.java	Apr 24, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.menu.web.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import org.bachelor.menu.domain.Menu;
import org.bachelor.menu.service.IMenuService;
import org.bachelor.menu.vo.MenuInfo;

/**
 * @author user
 *
 */
@Controller
@RequestMapping("/menu/")
public class MenuWebFacade {

	private Log log = LogFactory.getLog(MenuWebFacade.class);
	
	@Autowired
	private IMenuService menuService;
	
	@RequestMapping("all")
	@ResponseBody
	public List<MenuInfo> findAllMenus(@RequestParam(value="isCompress") String isCompress) {
		List<MenuInfo> infoList = new ArrayList<MenuInfo>();
		try{
			List<Menu> menus = menuService.findAllMenus(Boolean.valueOf(isCompress));
			
			//List<Menu> treeMenus = buildTree(menu_list);
			/** 把菜单树Menu对象转换成MenuInfo对象 **/
		   infoList = convert(menus);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		return infoList;
	}
	
	@SuppressWarnings("unused")
	private List<Menu> buildTree(List<Menu> menuNodeList){
		List<Menu> treeMenu = new ArrayList<Menu>();
		
		//构造子节点菜单路径
		List<List<Menu>> menuPathList = new ArrayList<List<Menu>>();
		for(Menu menu : menuNodeList){
			List<Menu> menuPath = buildMenuPath(menu);
			menuPathList.add(menuPath);
		}
		
		//根据子节点菜单路径构造菜单树
		for(List<Menu> menuPath: menuPathList){
			addTree(menuPath, treeMenu);
		}
		
		return treeMenu;
	}
	
	
	private void addTree(List<Menu> menuPath, List<Menu> treeMenu) {
		for(int i=0 ;i<menuPath.size() ;i++){
			Menu parentMenu = null;
			if(treeMenu.size() == 0){
				treeMenu.add(menuPath.get(0));
				break;
			}else{
				if(menuPath.get(i).getId() == treeMenu.get(i).getId()){
					parentMenu =treeMenu.get(i);
					continue;
				}else{
					if(parentMenu == null){
						for(int j=menuPath.size()-1;j>i;j--){
							menuPath.get(j-1).getChildMenus().clear();
							menuPath.get(j-1).getChildMenus().add(menuPath.get(j));
						}
						treeMenu.add(menuPath.get(i));
						break;
					}else{
						for(int j=menuPath.size()-1;j>i;j--){
							menuPath.get(j-1).getChildMenus().clear();
							menuPath.get(j-1).getChildMenus().add(menuPath.get(j));
						}
						parentMenu.getChildMenus().add(menuPath.get(i));
						break;
					}
					
				}
			}
		}
	}

	private List<Menu> buildMenuPath(Menu menu) {
		List<Menu> menuPath = new ArrayList<Menu>(4);
		menuPath.add(menu);
		while(menu.getParentMenu() != null){
			menuPath.add(menu.getParentMenu());
			menu = menu.getParentMenu();
		}
		
		Collections.reverse(menuPath);
		
		return menuPath;
		
	}

	private List<MenuInfo> convert(List<Menu> menuList){
		List<MenuInfo> infoList = new ArrayList<MenuInfo>();
		for(Menu menu : menuList){
			MenuInfo info = new MenuInfo();
			infoList.add(info);
			copy(info, menu);
			if(menu.getChildMenus() != null && menu.getChildMenus().size() > 0){
				List<Menu> childMenus = new ArrayList<Menu>(menu.getChildMenus());
				List<MenuInfo> childInfoList = convert(childMenus);
				info.getChildren().addAll(childInfoList);
			}
		}
		
		return infoList;
	}
	
	private void copy(MenuInfo info, Menu menu){
		info.setId(menu.getId());
		info.setDescription(menu.getDescription());
		if(menu.getFunc() != null){
			info.setFuncId(menu.getFunc().getId());
			info.setFuncUrl(menu.getFunc().getEntryPath());			
		}
		info.setIconUrl(menu.getIconUrl());
		info.setImageUrl(menu.getImageUrl());
		info.setName(menu.getName());
		info.setShowOrder(menu.getShowOrder());
		info.setExecutable(menu.isUsage());
	}
}