/*
 * @(#)IBusinessDomainDao.java	Mar 26, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.dao;

import java.util.List;

import org.bachelor.dao.IGenericDao;
import org.bachelor.ps.domain.BusinessDomain;

/**
 * @author user
 *
 */
public interface IBusinessDomainDao extends IGenericDao<BusinessDomain, String>{


	/**
	 * @param parentId
	 */
	public List<BusinessDomain> findChildren(String parentId) ;
}
