/*
 * @(#)GVDaoImpl.java	2013-3-4
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.gv.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import org.bachelor.dao.impl.GenericDaoImpl;
import org.bachelor.gv.dao.IGVDao;
import org.bachelor.gv.domain.GlobalVariable;

/**
 * @author Team Bachelor
 *
 */
@Repository
public class GVDaoImpl extends GenericDaoImpl<GlobalVariable, String>  implements IGVDao {

	@Override
	public void batchDeleteById(String dSQL[]) {

		getJdbcTemplate().batchUpdate(dSQL);
	}

	@Override
	public List<GlobalVariable> queryAll(GlobalVariable gv) {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("from GlobalVariable where 1=1");
		if(gv!=null){
			if(gv.getName()!=null && !"".equals(gv.getName())){
				qSQL.append(" and name like '%").append(gv.getName()).append("%'");
			}
			if(gv.getDescription()!=null && !"".equals(gv.getDescription())){
				qSQL.append(" and description like '%").append(gv.getDescription()).append("%'");
			}
			if(gv.getFlag()!=null && !"".equals(gv.getFlag())){
				qSQL.append(" and flag='").append(gv.getFlag()).append("'");
			}
		}
		return findByHQL(qSQL.toString());
	}
	


}
