/*
 * @(#)IBusinessDomainDao.java	Mar 26, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.dao;

import java.util.List;

import org.bachelor.dao.IGenericDao;
import org.bachelor.ps.domain.Function;

/**
 * @author user
 *
 */
public interface IFunctionDao extends IGenericDao<Function, String>{

	public List<Function> findByUrl(String url);
	
	public List<Function> findByCode(String code) ;
	
	/**
	 * 根据ID删除功能信息
	 * @param id
	 */
	public void deleteById(String id);
	
	/**
	 * 模糊查询URL
	 * @param url
	 * @return
	 */
	public List<Function> findByLikeUrl(String url);
}
