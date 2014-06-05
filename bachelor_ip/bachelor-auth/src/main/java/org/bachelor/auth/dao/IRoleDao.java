/*
 * @(#)IRoleDao.java	Mar 18, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.auth.dao;

import java.util.List;

import org.bachelor.auth.domain.Role;
import org.bachelor.dao.IGenericDao;


public interface IRoleDao extends IGenericDao<Role, String>{
	
	public List<Role> findAllRoleInfo();
	
	public void batchDelete(String[] delInfo); 
	
	public Role findByName(String roleName);
	
	public Role findRoleEtyByName(String roleName);
}
