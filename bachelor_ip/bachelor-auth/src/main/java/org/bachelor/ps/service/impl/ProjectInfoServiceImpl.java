/*
 * @(#)ProjectInfoServiceImpl.java	Mar 11, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.ps.domain.ProjectInfo;
import org.bachelor.ps.domain.ProjectModule;
import org.bachelor.ps.domain.ProjectProperty;
import org.bachelor.ps.service.IProjectInfoService;
import org.bachelor.ps.service.IProjectModuleService;
import org.bachelor.ps.service.IProjectPropertyService;

@Service
public class ProjectInfoServiceImpl implements IProjectInfoService {
	
	@Autowired
	private IProjectPropertyService ppService;
	
	
	@Autowired
	private IProjectModuleService pmService;	

	/* (non-Javadoc)
	 * @see org.bachelor.ps.service.IProjectInfoService#getPM()
	 */
	@Override
	public ProjectInfo findPI() {
		ProjectInfo pi = new ProjectInfo();
		ProjectProperty pp = ppService.get();
		pi.setPp(pp);
		
//		List<ProjectModule> pmList = pmService.findAll(null);
//		pi.setPmList(pmList);
		return pi;
	}

}
