package org.bachelor.stat.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import org.bachelor.dao.impl.GenericDaoImpl;
import org.bachelor.stat.dao.IStatServerDao;
import org.bachelor.stat.domain.StatServer;

@Repository
public class StatServerDaoImpl  extends GenericDaoImpl<StatServer,String> implements IStatServerDao{

	@Override
	public List<StatServer> findByExample(StatServer ss) {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append(" from StatServer where 1=1 ");
		if(ss!=null){
			if(ss.getIpAddr()!=null && !"".equals(ss.getIpAddr())){
				qSQL.append(" and ipAddr='").append(ss.getIpAddr()).append("'");
			}
			if(ss.getSearchType()!=null && ss.getSearchType().trim().equals("search")){
				qSQL.append(" and shutdownTime is null ");
			}
		}
		qSQL.append(" order by startTime desc ");
		Query query  = getSession().createQuery(qSQL.toString());
		return query.list();
	}

}
