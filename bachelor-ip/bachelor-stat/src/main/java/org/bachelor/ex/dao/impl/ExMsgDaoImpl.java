/*
 * @(#)ExMsgDaoImpl.java	Apr 23, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ex.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import org.bachelor.dao.impl.GenericDaoImpl;
import org.bachelor.ex.dao.IExMsgDao;
import org.bachelor.ex.domain.ExMsg;

/**
 * @author user
 *
 */
@Repository
public class ExMsgDaoImpl extends GenericDaoImpl<ExMsg,String> implements IExMsgDao{

	@Override
	public List<ExMsg> findExMsgInfo(ExMsg ex) {
		StringBuffer exQuery = new StringBuffer();
		exQuery.append("from ExMsg where 1=1 ");
		if(ex!=null){
			if(ex.getModuleId()!=null && !ex.getModuleId().equals("")){
				exQuery.append(" and moduleId='").append(ex.getModuleId()).append("'");
			}
			if(ex.getLayer()!=null && !ex.getLayer().equals("")){
				exQuery.append(" and layer='").append(ex.getLayer()).append("'");
			}
			 
			if(ex.getExStartTime()!=null && !ex.getExStartTime().equals("")){
				exQuery.append(" and occurTime>=to_date('").append(ex.getExStartTime()).append("','yyyy-MM-dd HH24:mi:ss') ");
			}
			if(ex.getExEndTime()!=null && !ex.getExEndTime().equals("")){
				exQuery.append(" and occurTime<=to_date('").append(ex.getExEndTime()).append("','yyyy-MM-dd HH24:mi:ss') ");
			}
		}
		return super.findByHQL(exQuery.toString());
	}

}
