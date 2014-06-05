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

import org.bachelor.auth.dao.IRoleGroupDao;
import org.bachelor.auth.domain.RoleGroup;
import org.bachelor.auth.service.IRoleGroupService;

/**
 * @author
 *
 */
@Service
public class RoleGroupServiceImpl implements IRoleGroupService {

	@Autowired
	private IRoleGroupDao dao;
	
	/* (non-Javadoc)
	 * @see org.bachelor.auth.service.IRoleService#save(org.bachelor.auth.domain.Role)
	 */
	@Override
	public void save(RoleGroup roleGroup) {
		dao.save(roleGroup);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.auth.service.IRoleService#deleRolee(org.bachelor.auth.domain.Role)
	 */
	@Override
	public void delete(RoleGroup roleGroup) {
		dao.delete(roleGroup);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.auth.service.IRoleService#saveOrUpdaRolee(org.bachelor.auth.domain.Role)
	 */
	@Override
	public void saveOrUpdate(RoleGroup roleGroup) {
		if(StringUtils.isEmpty(roleGroup.getId())){
			roleGroup.setId(null);
		}
		dao.saveOrUpdate(roleGroup);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.auth.service.IRoleService#updaRolee(org.bachelor.auth.domain.Role)
	 */
	@Override
	public void update(RoleGroup roleGroup) {
		dao.update(roleGroup);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.auth.service.IRoleService#findById(com.sun.xml.internal.bind.v2.model.core.ID)
	 */
	@Override
	public RoleGroup findById(String id) {
		return dao.findById(id);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.auth.service.IRoleService#findAll()
	 */
	@Override
	public List<RoleGroup> findAll() {
		return dao.findAll();
	}

	@Override
	public List<RoleGroup> findAllRoleInfo() {
		
		return dao.findAllRoleInfo();
	}
 
	@Override
	public RoleGroup findByName(String roleName) {
		 
		return dao.findByName(roleName);
	}

	@Override
	public void deleteById(String id) {
		RoleGroup roleGroup = dao.findById(id);
		if(roleGroup!=null && !StringUtils.isEmpty(roleGroup.getId())){
			dao.evict(roleGroup);
			dao.delete(roleGroup);
		}
	}
 
	@Override
	public List<RoleGroup> findChildren(String parentModuleName) {
		 
		return dao.findChildren(parentModuleName);
	}
}
