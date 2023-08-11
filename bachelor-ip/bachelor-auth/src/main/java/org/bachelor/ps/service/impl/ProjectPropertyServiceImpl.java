/*
 * @(#)ProjectPropertyServiceImpl.java	Mar 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.ps.dao.IProjectPropertyDao;
import org.bachelor.ps.domain.ProjectProperty;
import org.bachelor.ps.service.IProjectPropertyService;

@Service(value="ppService")
public class ProjectPropertyServiceImpl implements IProjectPropertyService {

	@Autowired
	private IProjectPropertyDao dao = null;
	
	
	/* (non-Javadoc)
	 * @see org.bachelor.ps.service.IProjectPropertyService#update(org.bachelor.ps.domain.ProjectProperty)
	 */
	@Override
	public void update(ProjectProperty pp) {
		//dao.update(pp);
		dao.merge(pp);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.ps.service.IProjectPropertyService#findByPk(java.lang.String)
	 */
	@Override
	public ProjectProperty get() {
		List<ProjectProperty> ppList = dao.findAll();
		if(ppList.size() == 0) return null; 
		
		return ppList.get(0);
	}

	@Override
	public void delete(ProjectProperty pp) {
		 
		dao.delete(pp);
	}
	

}
