/*
 * @(#)BusinessDomainDaoImpl.java	Mar 26, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import org.bachelor.dao.impl.GenericDaoImpl;
import org.bachelor.ps.dao.IBusinessDomainDao;
import org.bachelor.ps.domain.BusinessDomain;

/**
 * @author user
 *
 */
@Repository
public class BusinessDomainDaoImpl extends GenericDaoImpl<BusinessDomain, String>   implements IBusinessDomainDao {

	/* (non-Javadoc)
	 * @see org.bachelor.ps.dao.IBusinessDomainDao#findChildren(java.lang.String)
	 */
	@Override
	public List<BusinessDomain> findChildren(String parentId) {
		String hql = "from BusinessDomain ";
		if(StringUtils.isEmpty(parentId)){
			hql += " where parent is null";
		}else{
			hql += " where parent.id = '" + parentId + "'";
		}
		
		return super.findByHQL(hql);
	}

	

}
