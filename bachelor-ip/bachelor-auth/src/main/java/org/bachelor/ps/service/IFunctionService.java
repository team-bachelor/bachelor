/*
 * @(#)IBusinessDomainService.java	Mar 26, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.service;

import java.util.List;

import org.bachelor.ps.domain.Function; 

/**
 * @author user
 *
 */
public interface IFunctionService{
	
	public void saveOrUpdate(Function func);

	public Function findById(String id);

	public void delete(Function fnc);
	
	/**
	 * 根据ID删除功能信息
	 * @param id
	 */
	public void deleteById(String id);
	
	public Function findByUrl(String url);
	
	/**
	 * 模糊查询URL
	 * @param url
	 * @return
	 */
	public List<Function> findByLikeUrl(String url);
	
	public Function findByCode(String code);
}
