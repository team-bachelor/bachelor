/*
 * @(#)ProjectModuleDaoImpl.java	Mar 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bachelor.auth.domain.AuthFunction;
import org.bachelor.auth.service.IAuthFunctionService;
import org.bachelor.dao.impl.GenericDaoImpl;
import org.bachelor.menu.domain.Menu;
import org.bachelor.menu.service.IMenuService;
import org.bachelor.ps.dao.IProjectModuleDao;
import org.bachelor.ps.domain.Function;
import org.bachelor.ps.domain.ProjectModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectModuleDaoImpl extends GenericDaoImpl<ProjectModule, String>   implements IProjectModuleDao {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private IAuthFunctionService authFunctionService;
	
	@Autowired
	private IMenuService menuService;
	
	/* (non-Javadoc)
	 * @see org.bachelor.ps.dao.IProjectModuleDao#findModule(java.lang.String)
	 */
	@Override
	public List<ProjectModule> findModule(String parentModuleName) {
//		Criteria c = getSession().createCriteria(ProjectModule.class);
//		c.createAlias("parent", "parent");
//		if(StringUtils.isEmpty(parentModuleName)){
//			c.add(Restrictions.isNull("parent"));
//		}else{
//			c.add(Restrictions.eq("parent.name", parentModuleName));
//		}
//		return c.list();
		String hql = "from ProjectModule";
		
		if(StringUtils.isEmpty(parentModuleName)){
			hql += " where parentModule is null";
		}else{
			hql += " where parentModule.name ='" + parentModuleName + "'";
		}
		
		
		
		return super.findByHQL(hql);
	}

	@Override
	public List<ProjectModule> findByCode(String code) {
		 
		return this.findByProperty("code", code );
	}

	@Override
	public List<ProjectModule> findModuleByParentId(String pid) {
		String hql = "from ProjectModule";
		if(!StringUtils.isEmpty(pid)){
			hql += " where id ='"+pid+"'";
		}
		return super.findByHQL(hql);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public synchronized void processFuncsByProjectModules(List<ProjectModule> pms,
			Set<Function> funcs,LinkedList<Function> queueFuncs,List<String> moduleIds){
		for(ProjectModule pm:pms){
			
			moduleIds.add(pm.getId());
			
			if(pm.getFunctions()!=null && pm.getFunctions().size()>0){
				for(Function func:pm.getFunctions()){
					funcs.add(func);
					queueFuncs.add(func);
				}
			}
			if(pm.getChildModules()!=null && pm.getChildModules().size()>0){
				
				processFuncsByProjectModules(new ArrayList(pm.getChildModules()),funcs,queueFuncs,moduleIds);
			}
		}
	}

	@Override
	public void deleteById(String moduleId) {
		
		ProjectModule pm = findById(moduleId);
		
		/** 如果没有查询出模块数据就即时返回,中断操作**/
		if(pm==null){
			
			return ;
		}
		
		/** 得到所有模块下的功能 **/
		Set<Function> funcs = new HashSet<Function>();
		/** 获取所有模块ID值 **/
		List<String> moduleIds = new ArrayList<String>();
		/** 得到广度中功能ID **/
		LinkedList<Function> queueFuncs = new LinkedList<Function>();
		
		List<ProjectModule> pms = new ArrayList<ProjectModule>();
		pms.add(pm);
		processFuncsByProjectModules(pms,funcs,queueFuncs,moduleIds);
		
		/** 如果通过功能ID找到相对的菜单,拿到菜单的父节点去做比较,
		 * 	如果父节点下只有一个子节点并等于传进来的功能ID时就记录菜单的父节点，并以删除**/
		if(queueFuncs!=null && queueFuncs.size()>0){
				/** 获取要删除的父级菜单ID**/
				List<String> delMenuIds = new ArrayList<String>();
				for(int i=queueFuncs.size()-1;i>=0;i--){
					Function pfunc = queueFuncs.get(i);
					
					List<Menu> dmenus = menuService.findByFuncId("'"+pfunc.getId()+"'");
					for(Menu menu : dmenus){
						Menu smenu = menu.getParentMenu();
						if(smenu.getChildMenus()!=null && smenu.getChildMenus().size()>0){
							List<Menu> lmenus = new ArrayList<Menu>(smenu.getChildMenus());
							/** 如果通过FuncId查询出的菜单数据只有一条，而且等于查询条件的funcId时，保存父级菜单ID**/
							if(smenu.getChildMenus().size()==1 && lmenus.get(0).getFunc().getId().equals(pfunc.getId())){
								
									delMenuIds.add(smenu.getId());
							} 
						}
					}
					
					/** 删除功能关联的菜单信息 **/
					String dSQL = "delete from T_UFP_MENU_INFO where FUNCTION_ID = '"+pfunc.getId()+"'";
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
		 
		StringBuilder ids = new StringBuilder();
		/** 取出模块下面所有的功能ID**/
		int len  = 0;
		for(Function func:funcs){
			ids.append("'"+func.getId()+"'"); 
			if(len<(funcs.size()-1)){
				ids.append(",");
			}
			len++;
		}
		
		/** 如果没有子级,直接删除模块,跳出方法体 **/
		if(StringUtils.isEmpty(ids)){
			
			/** 删除模块 **/
			if(moduleIds.size()>0){
				deleteByModuleId(moduleIds);
			}
			return ;
		}
		
		log.info("---------------功能模块删除：要删除的功能ID是"+(ids)+"---------------");
		List<AuthFunction> authFunctions = authFunctionService.findByFuncIds(ids.toString());
		/** 查询要删除角色功能信息, 根据功能ID **/
		if(authFunctions!=null && authFunctions.size()>0){
			String iSQL[] = new String[authFunctions.size()];
			StringBuilder printSQL = new StringBuilder();
			int index = 0;
			for(AuthFunction authFunction : authFunctions){
				iSQL[index] = "delete from t_ufp_auth_function where FUNCTION_ID = '"+authFunction.getFunc().getId()+"'";
				printSQL.append(iSQL[index]+"\n");
				index++;
			}
			log.info("---------------角色功能删除：要删除的功能SQL是"+(printSQL.toString())+"---------------");
			getJdbcTemplate().batchUpdate(iSQL);
		}
		 
		/** 查询要删除菜单信息, 根据功能ID **/
		List<Menu> menus = menuService.findByFuncId(ids.toString());
		if(menus!=null && menus.size()>0){
			String uSQL[] = new String[menus.size()];
			StringBuilder printSQL = new StringBuilder();
			int index = 0;
			for(Menu menu : menus){
				uSQL[index] = "delete from T_UFP_MENU_INFO where FUNCTION_ID = '"+menu.getFunc().getId()+"'";
				printSQL.append(uSQL[index]+"\n");
				index++;
			}
			log.info("---------------菜单更新：要删除的功能SQL是"+(printSQL.toString())+"---------------");
			//getJdbcTemplate().batchUpdate(uSQL);
		}
		
		/** 删除功能 **/
		String dSQL[] = new String[funcs.size()];
		StringBuilder printSQL = new StringBuilder();
		int index = 0;
		for(Function func:funcs){
			dSQL[index] =  "delete from T_UFP_PS_FUNCTION where id='" + func.getId()+"'";
			printSQL.append(dSQL[index]+"\n");
			index++;
		}
		log.info("---------------功能删除：要删除的功能SQL是"+(dSQL.toString())+"---------------");
		getJdbcTemplate().batchUpdate(dSQL);
		/** 删除功能 **/
		
		if(moduleIds.size()>0){
			/** 删除模块 **/
			deleteByModuleId(moduleIds);
		}
	}
	/** JDBC删除模块数据 **/
	public void deleteByModuleId(List<String> moduleIds){
		String dSQL[] = new String[moduleIds.size()];
		int index = 0;
		for(String moduleId:moduleIds){
			dSQL[index] =  "delete from T_UFP_PS_MODULE where id='" + moduleId +"'";
			index++;
		}
		getJdbcTemplate().batchUpdate(dSQL);
	}
}
