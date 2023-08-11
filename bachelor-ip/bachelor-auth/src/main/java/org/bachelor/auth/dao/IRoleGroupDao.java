/*
 * @(#)IRoleDao.java	Mar 18, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.auth.dao;

import java.util.List;

import org.bachelor.auth.domain.RoleGroup;
import org.bachelor.dao.IGenericDao;


public interface IRoleGroupDao extends IGenericDao<RoleGroup, String>{
	
	public List<RoleGroup> findAllRoleInfo();
	
	public RoleGroup findByName(String roleName);
	
	public List<RoleGroup> findChildren(String parentModuleName) ;
}
