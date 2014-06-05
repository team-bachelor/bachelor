/*
 * @(#)ProjectModuleServiceImpl.java	Mar 11, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.ps.dao.IProjectModuleDao;
import org.bachelor.ps.domain.Function;
import org.bachelor.ps.domain.ProjectModule;
import org.bachelor.ps.service.IProjectModuleService;

@Service
public class ProjectModuleServiceImpl implements IProjectModuleService {

	@Autowired
	private IProjectModuleDao dao = null;
	
	/* (non-Javadoc)
	 * @see org.bachelor.ps.service.IProjectModuleService#save(org.bachelor.ps.domain.ProjectModule)
	 */
	@Override
	public void save(ProjectModule pm) {
		dao.save(pm);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.ps.service.IProjectModuleService#delete(org.bachelor.ps.domain.ProjectModule)
	 */
	@Override
	public void delete(ProjectModule pm) { 
		dao.delete(pm);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.ps.service.IProjectModuleService#update(org.bachelor.ps.domain.ProjectModule)
	 */
	@Override
	public void update(ProjectModule pm) {
		dao.update(pm);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.ps.service.IProjectModuleService#findByName(java.lang.String)
	 */
	@Override
	public ProjectModule findByName(String id) {
		return dao.findById(id);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.ps.service.IProjectModuleService#findAll(java.lang.String)
	 */
	@Override
	public List<ProjectModule> findChildren(String parentModuleName) {
		
		return dao.findModule(parentModuleName);
	}

	@Override
	public void saveOrUpdate(ProjectModule pm) {
		
		dao.saveOrUpdate(pm);
	}

	@Override
	public ProjectModule findByCode(String code) {
		List<ProjectModule> pms = dao.findByCode(code);
		ProjectModule pm = new ProjectModule();
		if(pms!=null && pms.size()>0){
			pm = pms.get(0);
		}
		return pm;
	}

	@Override
	public List<ProjectModule> findModuleByParentId(String pid) {

		return dao.findModuleByParentId(pid);
	}

	@Override
	public void deleteById(String moduleId) {

		dao.deleteById(moduleId);
	}

}
