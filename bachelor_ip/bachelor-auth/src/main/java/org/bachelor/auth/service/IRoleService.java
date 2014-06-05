/*
 * @(#)IRoleService.java	Mar 18, 2013
 *
 * CopyrighRole (c) 2013, Team Bachelor. All righRoles reserved.
 */
package org.bachelor.auth.service;

import java.util.List;

import org.bachelor.auth.domain.Role;



/**
 * @author
 *
 */
public interface IRoleService {


	public void save(Role role);
	
	public void delete(Role role);
	
	public void saveOrUpdate(Role role);
	
	public void deleteById(String id);
	
	public void update(Role role);
	
	public Role findById(String id);
	
	public List<Role> findAll();
	
	public List<Role> findAllRoleInfo();
	
	public void batchDelete(String delInfo);
	
	public Role findByName(String roleName);
	
	public Role findRoleEtyByName(String roleName);
}
