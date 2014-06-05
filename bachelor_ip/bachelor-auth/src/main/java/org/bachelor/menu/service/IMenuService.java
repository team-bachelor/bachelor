/*
 * @(#)IMenuService.java	Mar 22, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.menu.service;

import java.util.List;

import org.bachelor.menu.domain.Menu;

/**
 * @author user
 *
 */
public interface IMenuService {


	public void deleteById(String name);

	/**
	 * 查询菜单项。
	 * 根据当前用户所属角色集合，返回角色有权限的所有菜单。
	 * 
	 * @return 菜单集合
	 */
	public List<Menu> findAllMenus(boolean isCompress) throws Exception;

	/**
	 * 功能:查询所有菜单项
	 * 查询维护菜单信息
	 * 作者 曾强 2013-5-28下午03:43:52
	 * @return
	 */
	public List<Menu> findManatinMenus();

	public Menu findById(String id);

	public void update(Menu menu);

	public void save(Menu menu);
	
	/**
	 * 
	 * @param menu
	 */
	public void saveMenu(Menu menu);
	
	/**
	 *查询菜单信息,根据功能ID
	 * @param funcId
	 * @return
	 */
	public List<Menu> findByFuncId(String funcId);
}
