/*
 * @(#)IProjectModuleService.java	Mar 11, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.service;

import java.util.List;

import org.bachelor.ps.domain.ProjectModule;

/**
 * @author ���
 *
 */
public interface IProjectModuleService {
	
	public void save(ProjectModule pm);
	
	public void delete(ProjectModule pm);
	
	public void update(ProjectModule pm);
	
	public ProjectModule findByName(String id);
	
	public List<ProjectModule> findChildren(String parentModuleName);
	
	public void saveOrUpdate(ProjectModule pm);
	
	public	ProjectModule findByCode(String code);
	
	public List<ProjectModule> findModuleByParentId(String pid);
	
	/**
	 * 删除模块
	 * @param moduleId
	 */
	public void deleteById(String moduleId);
}
