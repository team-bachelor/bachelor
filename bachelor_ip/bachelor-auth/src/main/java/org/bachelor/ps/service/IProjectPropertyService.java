/*
 * @(#)IProjectPropertyService.java	Mar 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.service;

import org.bachelor.ps.domain.ProjectProperty;

public interface IProjectPropertyService {
	
	public void update(ProjectProperty pp);
	
	public void delete(ProjectProperty pp);
	
	public ProjectProperty get();
}
