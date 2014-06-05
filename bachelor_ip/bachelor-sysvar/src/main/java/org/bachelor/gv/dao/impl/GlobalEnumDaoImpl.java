/*
 * @(#)GlobalEnumDaoImpl.java	May 14, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.gv.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import org.bachelor.dao.impl.GenericDaoImpl;
import org.bachelor.gv.dao.IGlobalEnumDao;
import org.bachelor.gv.domain.GlobalEnum;
import org.bachelor.gv.vo.GlobalEnumTvVo;

/**
 * 全局枚举Dao实现类
 * 
 * @author Team Bachelor
 *
 */
@Repository
public class GlobalEnumDaoImpl extends GenericDaoImpl<GlobalEnum, String>  implements IGlobalEnumDao {

	@Override
	public List<GlobalEnum> findAll() {
		DetachedCriteria  dc = getDetachedCriteria();
		dc.addOrder(Order.asc("enumName")).addOrder(Order.asc("fieldDesc"));
		return super.findByCriteria(dc);
	}

	@Override
	public List<GlobalEnum> findByEnumName(String enumId) {
		DetachedCriteria  dc = getDetachedCriteria();
		
		dc.add(Restrictions.eq("enumName", enumId));
		 
		return super.findByCriteria(dc);
	}

	@Override
	public List<GlobalEnum> findAllEnumInfo(GlobalEnum ge) {
		DetachedCriteria  dc = getDetachedCriteria();
		if(ge!=null){
			if(ge.getEnumDesc()!=null && !ge.getEnumDesc().trim().equals("")){
				dc.add(Restrictions.like("enumDesc", ("%"+ge.getEnumDesc()+"%")));
			}
			if(ge.getEnumName()!=null && !ge.getEnumName().trim().equals("")){
				dc.add(Restrictions.like("enumName", ("%"+ge.getEnumName()+"%")));
			}
			if(ge.getFieldValue()!=null && !ge.getFieldValue().trim().equals("")){
				dc.add(Restrictions.like("fieldValue", ("%"+ge.getFieldValue()+"%")));
			}
		}
		return super.findByCriteria(dc);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalEnumTvVo> findByEnumNameTV(String enumName) {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("select field_desc as text , field_value as value from t_ufp_gv_enum");
		qSQL.append(" where ENUM_NAME='").append(enumName).append("'");
		List<GlobalEnumTvVo> tv_list = getJdbcTemplate().query(qSQL.toString(), new RowMapper(){

			@Override
			public Object mapRow(ResultSet rst, int arg1) throws SQLException {
				GlobalEnumTvVo tv = new GlobalEnumTvVo();
				tv.setText(rst.getString("text"));
				tv.setValue(rst.getString("value"));
				return tv;
			}});
		return tv_list;
	}

	@Override
	public void batchDelete(String[] dSQL) {
		// TODO Auto-generated method stub
		getJdbcTemplate().batchUpdate(dSQL);
	}
}
