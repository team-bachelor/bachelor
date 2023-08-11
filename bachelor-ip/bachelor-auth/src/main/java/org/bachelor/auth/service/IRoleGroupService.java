/*
 * @(#)IRoleService.java	Mar 18, 2013
 *
 * CopyrighRole (c) 2013, Team Bachelor. All righRoles reserved.
 */
package org.bachelor.auth.service;

import java.util.List;

import org.bachelor.auth.domain.RoleGroup;



/**
 * @author
 *
 */
public interface IRoleGroupService {


	public void save(RoleGroup roleGroup);
	
	public void delete(RoleGroup roleGroup);
	
	public void saveOrUpdate(RoleGroup roleGroup);
	
	public void update(RoleGroup roleGroup);
	
	public RoleGroup findById(String id);
	
	public List<RoleGroup> findAll();
	
	public List<RoleGroup> findAllRoleInfo();
	
	public RoleGroup findByName(String roleName);

	public void deleteById(String id);
	
	public List<RoleGroup> findChildren(String parentModuleName) ;
}
