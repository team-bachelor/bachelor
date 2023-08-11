/*
 * @(#)IMenuDao.java	Mar 22, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.menu.dao;

import java.util.List;

import org.bachelor.dao.IGenericDao;
import org.bachelor.menu.domain.Menu;

/**
 * 菜单实体Dao类
 * 
 * @author Team Bachelor
 *
 */
public interface IMenuDao extends IGenericDao<Menu, String>{

	/**
	 * 查询顶级菜单
	 * 
	 * @return 顶级菜单集合
	 */
	public List<Menu> findAllMenus(String roleIds);
	
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
