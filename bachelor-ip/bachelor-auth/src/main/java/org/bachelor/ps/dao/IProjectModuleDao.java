/*
 * @(#)IProjectModuleDao.java	Mar 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.dao;

import java.util.List;

import org.bachelor.dao.IGenericDao;
import org.bachelor.ps.domain.ProjectModule;

/**
 * @author 
 *
 */
public interface IProjectModuleDao  extends IGenericDao<ProjectModule, String>{

	public List<ProjectModule> findModule(String parentModuleName);
	
	public List<ProjectModule> findByCode(String code);
	
	public List<ProjectModule> findModuleByParentId(String pid);
	
	/**
	 * 删除模块
	 * @param moduleId
	 */
	public void deleteById(String moduleId);
}
