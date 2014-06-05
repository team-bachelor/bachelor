/*
 * @(#)IExMsgDao.java	Apr 23, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ex.dao;

import java.util.List;

import org.bachelor.dao.IGenericDao;
import org.bachelor.ex.domain.ExMsg;

/**
 * @author user
 *
 */
public interface IExMsgDao extends IGenericDao<ExMsg,String> {
	
	public List<ExMsg> findExMsgInfo(ExMsg ex);
}
