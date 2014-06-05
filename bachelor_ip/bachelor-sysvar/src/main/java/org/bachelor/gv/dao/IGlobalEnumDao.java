/*
 * @(#)IGlobalEnumDao.java	May 14, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.gv.dao;

import java.util.List;

import org.bachelor.dao.IGenericDao;
import org.bachelor.gv.domain.GlobalEnum;
import org.bachelor.gv.vo.GlobalEnumTvVo;

/**
 * 全局枚举Dao接口
 * 
 * @author Team Bachelor
 *
 */
public interface IGlobalEnumDao extends IGenericDao<GlobalEnum, String>{

	public List<GlobalEnum> findByEnumName(String enumId);
	
	public List<GlobalEnum> findAllEnumInfo(GlobalEnum ge);
	
	public  List<GlobalEnumTvVo> findByEnumNameTV(String enumName);
	
	public void batchDelete(String[] dSQL);
}
