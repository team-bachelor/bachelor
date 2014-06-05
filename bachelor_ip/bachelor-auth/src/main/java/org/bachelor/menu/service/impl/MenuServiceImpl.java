/*
 * @(#)MenuServiceImpl.java	Mar 22, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.menu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.auth.common.AuthConstant;
import org.bachelor.auth.domain.AuthFunction;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.service.IAuthFunctionService;
import org.bachelor.context.service.IVLService;
import org.bachelor.menu.dao.IMenuDao;
import org.bachelor.menu.domain.Menu;
import org.bachelor.menu.service.IMenuService;

/**
 * 菜单服务类 
 * 
 * @author Team Bachelor
 *
 */
@Service(value="menuService")
public class MenuServiceImpl implements IMenuService {
	
	@Autowired
	private IMenuDao dao = null;
	
	@Autowired
	private IVLService vlService = null;
	@Autowired
	private IAuthFunctionService sfService = null;
	
	public MenuServiceImpl(){
		
	}
	 
	public void save(Menu menu){
		if(menu.getParentMenu() == null || StringUtils.isEmpty(menu.getParentMenu().getId())){
			dao.save(menu);
		}else{
//			Menu parentMenu = dao.findById(menu.getParent().getId());
//			menu.setParent(parentMenu);
//			parentMenu.getChildren().add(menu);
			
			dao.save(menu);
		}
	}

	@Override
	public void deleteById(String id){
		if(id == null)
			return;
		Menu menu = dao.findById(id);
		if(menu == null)
			return;
		if(menu.getParentMenu() != null){
			menu.getParentMenu().getChildMenus().remove(menu);
			menu.setParentMenu(null);			
		}

		dao.delete(menu);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.menu.service.IMenuService#findTop()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> findAllMenus(boolean isCompress) throws Exception{
		ArrayList<Role> roles = (ArrayList<Role>)vlService.getSessionAttribute(AuthConstant.LOGIN_USER_ROLE);
		if(roles!=null && roles.size()>0){
			return findMenusByRole(roles,isCompress);
		} else {
			return new ArrayList<Menu>();
		}
	}
	/** 根据权限列表，查询菜单信息 **/
	public List<Menu> findMenusByRole(ArrayList<Role> role_list,boolean isCompress) throws Exception{
		if(role_list!=null && role_list.size()>0){
			Map<String,Object> funcIds = new HashMap<String,Object>();
			//所有菜单树数据
			List<Menu> menus = dao.findAllMenus(null);
			
			for(Role role : role_list){
				List<AuthFunction> afs = role.getAuthFunctions();
				if(afs!=null && afs.size()>0){
					for (AuthFunction af : afs){
						funcIds.put(af.getFunc().getId(),af.getFunc().getId());
					}
				}
			}
			/** 遍历原始树结构 **/
			loopTreeMenuData(menus,funcIds,isCompress);
			
			/** 清理没有子级的菜单 **/
			if(menus!=null && menus.size()>0){
				List<Menu> tempMenus = new ArrayList<Menu>();
				for(Menu menu : menus){
					if(menu.getChildMenus()!=null && menu.getChildMenus().size()>0){
						tempMenus.add(menu);
					}
				}
				menus = tempMenus;
			}
			//递归菜单集合，该菜单是否可用
			setMenuUsage(menus, role_list);
			return menus;
		} else {
			return new ArrayList<Menu>();
		}
	}
	 
	/** 遍历树结构，根据FuncId删除节点 **/
	public void loopTreeMenuData(List<Menu> menus,Map<String,Object> funcIds,boolean isCompress) throws Exception{
		for(int i=(menus.size()-1);i>-1;i--){
			Menu menu = menus.get(i);
			if(menu.getChildMenus()!=null && menu.getChildMenus().size()>0){
				LinkedList<Menu> tempMenus = new LinkedList<Menu>(menu.getChildMenus());
				loopTreeMenuData(tempMenus,funcIds,isCompress);
			} 
			/** 删除不是功能ID权限节点 **/
			if(menu.getChildMenus()==null || menu.getChildMenus().size()<=0){
				if(funcIds!=null && menu.getFunc()!=null 
						&& !StringUtils.isEmpty(menu.getFunc().getId())  && 
						!funcIds.containsValue(menu.getFunc().getId())){
					if(menu.getParentMenu()!=null && menu.getParentMenu().getChildMenus()!=null
							&& menu.getParentMenu().getChildMenus().size()>0){
						menu.getParentMenu().getChildMenus().remove(menu);
					}
					/** 如果是true时，只把菜单设置为不可用. 如果有false时，直接删除菜单**/
					if(isCompress){
						menu.setFlag("2");
					} else{
						menus.remove(menu);
					}
				}
			} 
		}
	}

	/**
	 * 设置菜单权限
	 * @param menu_list
	 * @param roleList
	 */
	private void setMenuUsage(List<Menu> menu_list, ArrayList<Role> roleList) throws Exception {
		for(Menu menu:menu_list){
			menu.setUsage(false);
			if(menu.getFlag().equals("0")){
				menu.setUsage(true);
			}
			//遍历roleList中的func，如果有一个Func是可用的，就将menu.usage设置为ture.
			for(Role role : roleList){
				if(role.getAuthFunctions() == null){
					continue;
				}
				for(AuthFunction func: role.getAuthFunctions()) {
						if(menu.getFunc()!=null && func.getFunc().getId().equals(menu.getFunc().getId())){
							if(func.getUsage().equals("1")){
								menu.setUsage(true);
								break;
							} 
						}
				}
			}
			if(menu.getChildMenus()!=null && menu.getChildMenus().size()>0){
				List<Menu> menuList = new ArrayList<Menu>(menu.getChildMenus());
				setMenuUsage(menuList,roleList);
			}
		}
	}

	@Override
	public List<Menu> findManatinMenus() {
		
		return dao.findAllMenus(null);
	}

	public void update(Menu menu){
		
		dao.update(menu);
	}

	@Override
	public Menu findById(String id) {
		 
		return dao.findById(id);
	}

	@Override
	public void saveMenu(Menu menu) {
		 
		dao.saveMenu(menu);
	}

	@Override
	public List<Menu> findByFuncId(String funcId) {
		 
		return dao.findByFuncId(funcId);
	}
}
