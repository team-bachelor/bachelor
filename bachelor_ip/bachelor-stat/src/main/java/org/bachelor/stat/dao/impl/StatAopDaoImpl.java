package org.bachelor.stat.dao.impl;

import org.springframework.stereotype.Repository;

import org.bachelor.dao.impl.GenericDaoImpl;
import org.bachelor.stat.dao.IStatAopDao;
import org.bachelor.stat.domain.StatAop;

@Repository
public class StatAopDaoImpl extends GenericDaoImpl<StatAop,String> implements IStatAopDao {

	@Override
	public void batchDelete(String info) {
		if(info!=null && !"".equals(info)){
			String delInfo[] = info.split(",");
			String dSQL[] = new String[delInfo.length];
			int index = 0;
			for(String dInfo:delInfo){
				if(dInfo!=null && !"".equals(dInfo)){
					dSQL[index] = "delete from T_bchlr_STAT_AOP where class_name='"+dInfo+"'";
					index++;
				}
			}
			getJdbcTemplate().batchUpdate(dSQL);
		}
	}
}
