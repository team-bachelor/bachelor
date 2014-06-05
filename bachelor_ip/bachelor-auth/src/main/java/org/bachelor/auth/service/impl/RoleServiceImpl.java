/*
 * @(#)RoleServiceImpl.java	Mar 18, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.auth.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.auth.dao.IRoleDao;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.service.IRoleService;

/**
 * @author
 *
 */
@Service(value="roleService")
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleDao dao;
	
	/* (non-Javadoc)
	 * @see org.bachelor.auth.service.IRoleService#save(org.bachelor.auth.domain.Role)
	 */
	@Override
	public void save(Role role) {
		dao.save(role);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.auth.service.IRoleService#deleRolee(org.bachelor.auth.domain.Role)
	 */
	@Override
	public void delete(Role role) {
		dao.delete(role);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.auth.service.IRoleService#saveOrUpdaRolee(org.bachelor.auth.domain.Role)
	 */
	@Override
	public void saveOrUpdate(Role role) {
		if(StringUtils.isEmpty(role.getId())){
			role.setId(null);
		}
		dao.saveOrUpdate(role);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.auth.service.IRoleService#updaRolee(org.bachelor.auth.domain.Role)
	 */
	@Override
	public void update(Role role) {
		dao.update(role);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.auth.service.IRoleService#findById(com.sun.xml.internal.bind.v2.model.core.ID)
	 */
	@Override
	public Role findById(String id) {
		return dao.findById(id);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.auth.service.IRoleService#findAll()
	 */
	@Override
	public List<Role> findAll() {
		return dao.findAll();
	}

	@Override
	public List<Role> findAllRoleInfo() {
		
		return dao.findAllRoleInfo();
	}

	@Override
	public void batchDelete(String delInfo) {
		// TODO Auto-generated method stub
		String delArray[] = delInfo.split(",");
		String delSQL[] = new String[delArray.length];
		int index = 0;
		for(String info:delArray){
			delSQL[index] = "delete from T_UFP_AUTH_ROLE where id='"+info+"'";
			index++;
		}
		dao.batchDelete(delSQL);
	}

	@Override
	public Role findByName(String roleName) {
		 
		return dao.findByName(roleName);
	}

	@Override
	public void deleteById(String id) {
		Role role = dao.findById(id);
		if(role!=null && !StringUtils.isEmpty(role.getId())){
			dao.delete(role);
		}
	}

	@Override
	public Role findRoleEtyByName(String roleName) {
		 
		return dao.findRoleEtyByName(roleName);
	}
}
