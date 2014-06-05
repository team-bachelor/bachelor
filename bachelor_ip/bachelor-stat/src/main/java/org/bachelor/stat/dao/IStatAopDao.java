package org.bachelor.stat.dao;

import org.bachelor.dao.IGenericDao;
import org.bachelor.stat.domain.StatAop;

public interface IStatAopDao extends IGenericDao<StatAop,String>{
	
	public void batchDelete(String delInfo);
}
