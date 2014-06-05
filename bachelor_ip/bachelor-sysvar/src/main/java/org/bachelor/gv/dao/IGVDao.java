/*
 * @(#)IGvDao.java	2013-3-4
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.gv.dao;

import java.util.List;

import org.bachelor.dao.IGenericDao;
import org.bachelor.gv.domain.GlobalVariable;

/**
 * @author Team Bachelor
 *
 */
public interface IGVDao extends IGenericDao<GlobalVariable, String>{
	
	public void batchDeleteById(String dSQL[]);
	
	public List<GlobalVariable> queryAll(GlobalVariable gv) ;
}
