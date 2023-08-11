/*
 * @(#)IBusinessDomainService.java	Mar 26, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.service;

import java.util.List;
import org.bachelor.ps.domain.BusinessDomain;

/**
 * @author user
 *
 */
public interface IBusinessDomainService   {

	public List<BusinessDomain> findChildren(String parentId);

	public List<BusinessDomain> findAll();

	public void save(BusinessDomain bd);

	public void deleteById(String id);
}
