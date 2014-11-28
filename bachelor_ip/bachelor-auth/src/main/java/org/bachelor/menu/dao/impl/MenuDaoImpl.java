/*
 * @(#)MenuDaoImpl.java	Mar 22, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.menu.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import org.bachelor.dao.impl.GenericDaoImpl;
import org.bachelor.menu.dao.IMenuDao;
import org.bachelor.menu.domain.Menu;

/**
 * @author user
 *
 */
@Repository
public class MenuDaoImpl extends GenericDaoImpl<Menu, String> implements IMenuDao {

	/**
	 * 通过功能ID查询菜单信息
	 * 如果功能ID为空查询所有菜单信息
	 */
	public List<Menu> findAllMenus(String funcIds){
		
		String hql = "from Menu m where (m.flag != '2') ";	
		
		if(StringUtils.isEmpty(funcIds)==false){
			hql += "  and  m.func.id in ("+funcIds+") ";
		}  else {
			hql += " and m.parentMenu is null ";
		}
		
		hql += " order by  showOrder asc"; 
		
		 List<Menu> menus = super.findByHQL(hql);
		 return menus;
	}

	@Override
	public void saveMenu(Menu menu) {
		StringBuilder iSQL = new StringBuilder();
		iSQL.append("insert into T_bchlr_MENU_INFO(id,NAME,DESCRIPTION,SHOW_ORDER,FLAG,PARENT_ID,FUNCTION_ID,OPEN_TYPE) values(");
		iSQL.append("'").append(menu.getId()).append("',");
		iSQL.append("'").append(menu.getName()).append("',");
		iSQL.append("'").append(menu.getDescription()).append("',");
		iSQL.append("'").append(menu.getShowOrder()).append("',");
		iSQL.append("'").append(menu.getFlag()).append("',");
		if(!StringUtils.isEmpty(menu.getParentMenu().getId())){
			iSQL.append("'").append(menu.getParentMenu().getId()).append("',");
		} else {
			iSQL.append("null,");
		}
		if(!StringUtils.isEmpty(menu.getFunc().getId())){
			iSQL.append("'").append(menu.getFunc().getId()).append("',");
		} else {
			iSQL.append("null,");
		}
		iSQL.append("'").append(menu.getOpenType()).append("'");
		iSQL.append(")");
		getJdbcTemplate().update(iSQL.toString());
	}

	@Override
	public List<Menu> findByFuncId(String funcId) {
		
		if(StringUtils.isEmpty(funcId)){
			
			return null;
		}
		
		String hql = "from Menu m where (m.flag != '2') ";	
		
		List<Menu> menus = null;
		
		if(StringUtils.isEmpty(funcId)==false){
			hql += "  and  m.func.id in ("+funcId+") ";
		  
			hql += " order by  showOrder asc"; 
		
			menus = super.findByHQL(hql);
		}
	 	return menus;
	}
 
}
