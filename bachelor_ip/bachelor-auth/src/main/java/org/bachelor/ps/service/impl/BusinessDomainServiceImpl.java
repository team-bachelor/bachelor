/*
 * @(#)BusinessDomainServiceImpl.java	Mar 26, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.ps.dao.IBusinessDomainDao;
import org.bachelor.ps.domain.BusinessDomain;
import org.bachelor.ps.service.IBusinessDomainService;

/**
 * @author user
 *
 */
@Service(value="businessDomainService")
public class BusinessDomainServiceImpl  implements IBusinessDomainService{
	
	@Autowired
	private IBusinessDomainDao dao;
	 
	@Override
	public List<BusinessDomain> findChildren(String parentId) {
		return dao.findChildren(parentId);
	}

	@Override
	public void deleteById(String id) {
		BusinessDomain bd = dao.findById(id);
		dao.delete(bd);
	}

	@Override
	public List<BusinessDomain> findAll() {
		return dao.findAll();
	}

	@Override
	public void save(BusinessDomain bd) {
		dao.save(bd);
	}

}
