/*
 * @(#)FunctionDaoImpl.java	Mar 26, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.bachelor.auth.domain.AuthFunction;
import org.bachelor.auth.service.IAuthFunctionService;
import org.bachelor.dao.impl.GenericDaoImpl;
import org.bachelor.menu.domain.Menu;
import org.bachelor.menu.service.IMenuService;
import org.bachelor.ps.dao.IFunctionDao;
import org.bachelor.ps.domain.Function;

/**
 * @author user
 *
 */
@Repository
public class FunctionDaoImpl extends GenericDaoImpl<Function, String>   implements IFunctionDao {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private IAuthFunctionService authFunctionService;
	
	@Autowired
	private IMenuService menuService;
	
	@Override
	public List<Function> findByUrl(String url) {
		List<Function> func_list = findByProperty("entryPath", url);
		return func_list;
	}

	@Override
	public List<Function> findByCode(String code) {
		List<Function> func_list = findByProperty("code", code);
		return func_list;
	}

	@Override
	public void deleteById(String id) {
		
		if(StringUtils.isEmpty(id)){
			
			return ;
		}
		
		log.info("---------------功能删除：要删除的功能ID是"+(id)+"---------------");

		List<AuthFunction> authFunctions = authFunctionService.findByFuncIds("'"+id+"'");
		log.info("---------------功能删除：查询出角色功能信息"+(authFunctions!=null?authFunctions.size():0)+"条---------------");
		
		if(authFunctions!=null && authFunctions.size()>0){
			String iSQL[] = new String[authFunctions.size()];
			int index = 0;
			for(AuthFunction authFunction : authFunctions){
				iSQL[index] = "delete from t_ufp_auth_function where FUNCTION_ID = '"+authFunction.getFunc().getId()+"'";
				index++;
			}
			getJdbcTemplate().batchUpdate(iSQL);
		}
		
		List<Menu> menus = menuService.findByFuncId("'"+id+"'");
		log.info("---------------功能删除：查询出菜单信息"+(menus!=null?menus.size():0)+"条---------------");
		
		/*if(menus!=null && menus.size()>0){
			String uSQL[] = new String[menus.size()];
			int index = 0;
			for(Menu menu : menus){
				uSQL[index] = "delete from T_UFP_MENU_INFO where FUNCTION_ID = '"+menu.getFunc().getId()+"'";
				index++;
			}
			getJdbcTemplate().batchUpdate(uSQL);
		}*/
		
		/** 如果通过功能ID找到相对的菜单,拿到菜单的父节点去做比较,
		 * 	如果父节点下只有一个子节点并等于传进来的功能ID时就记录菜单的父节点，并以删除**/
		if(menus!=null && menus.size()>0){
					/** 获取要删除的父级菜单ID**/
					List<String> delMenuIds = new ArrayList<String>();
					for(Menu menu : menus){
						Menu smenu = menu.getParentMenu();
						if(smenu.getChildMenus()!=null && smenu.getChildMenus().size()>0){
							List<Menu> lmenus = new ArrayList<Menu>(smenu.getChildMenus());
							/** 如果通过FuncId查询出的菜单数据只有一条，而且等于查询条件的funcId时，保存父级菜单ID**/
							if(smenu.getChildMenus().size()==1 && lmenus.get(0).getFunc().getId().equals(id)){
								
									delMenuIds.add(smenu.getId());
							} 
						}
						/**删除功能相关的菜单节点**/
						String dSQL = "delete from T_UFP_MENU_INFO where ID = '"+menu.getId()+"'";
						getJdbcTemplate().update(dSQL);
					}
					/** 删除菜单父节点 **/
					if(delMenuIds!=null && delMenuIds.size()>0){
						String dSQL[]  = new String[delMenuIds.size()];
						int index = 0;
						for(String delMenuId:delMenuIds){
								
								dSQL[index] = "delete from T_UFP_MENU_INFO where id = '"+delMenuId+"'";
								index++;
						}
						getJdbcTemplate().batchUpdate(dSQL);
					}
		}
		
		StringBuilder dSQL = new StringBuilder("delete from T_UFP_PS_FUNCTION where id='");
		dSQL.append(id).append("'");
		getJdbcTemplate().update(dSQL.toString());
	}

	@Override
	public List<Function> findByLikeUrl(String url) {
		StringBuilder qSQL = new StringBuilder("from Function where entryPath like '%").append(url.trim()).append("%'");
		return findByHQL(qSQL.toString());
	}

	
}
