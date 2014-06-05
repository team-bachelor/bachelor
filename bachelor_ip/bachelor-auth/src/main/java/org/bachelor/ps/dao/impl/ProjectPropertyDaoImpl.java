/*
 * @(#)ProjectPropertyDaoImpl.java	Mar 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.dao.impl;

import org.springframework.stereotype.Repository;

import org.bachelor.dao.impl.GenericDaoImpl;
import org.bachelor.ps.dao.IProjectPropertyDao;
import org.bachelor.ps.domain.ProjectProperty;

@Repository
public class ProjectPropertyDaoImpl extends GenericDaoImpl<ProjectProperty, String>  implements IProjectPropertyDao{

}
