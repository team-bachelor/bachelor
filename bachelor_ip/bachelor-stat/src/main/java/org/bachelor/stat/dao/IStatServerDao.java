package org.bachelor.stat.dao;

import java.util.List;

import org.bachelor.dao.IGenericDao;
import org.bachelor.stat.domain.StatServer;


public interface IStatServerDao  extends IGenericDao<StatServer,String> {
	
	public List<StatServer> findByExample(StatServer ss);
}
