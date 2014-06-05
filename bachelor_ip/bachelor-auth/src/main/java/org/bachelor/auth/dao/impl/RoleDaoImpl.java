/*
 * @(#)RoleDaoImpl.java	Mar 18, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.auth.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import org.bachelor.auth.dao.IRoleDao;
import org.bachelor.auth.domain.Role;
import org.bachelor.dao.impl.GenericDaoImpl;

/**
 * @author
 *
 */
@Repository
public class RoleDaoImpl extends GenericDaoImpl<Role, String>  implements IRoleDao {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Role> findAllRoleInfo() {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("select t.ID,t.NAME,t.MEMO,t.DESCRIPTION,t.DEL_FLAG from T_UFP_AUTH_ROLE t order by t.memo ");
		List<Role> role_list = getJdbcTemplate().query(qSQL.toString(), new RowMapper(){

			@Override
			public Role mapRow(ResultSet rst, int arg1) throws SQLException {
				Role role = new Role();
				role.setId(rst.getString("ID"));
				role.setDescription(rst.getString("DESCRIPTION"));
				role.setName(rst.getString("NAME"));
				role.setMemo(rst.getString("MEMO"));
				role.setDelFlag(rst.getString("DEL_FLAG"));
				return role;
			}});
		return role_list;
	}

	@Override
	public void batchDelete(String[] delInfo) {
		
		getJdbcTemplate().batchUpdate(delInfo);
	}

	@Override
	public Role findByName(String roleName) {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("select t.ID,t.NAME,t.MEMO,t.DESCRIPTION,t.DEL_FLAG from T_UFP_AUTH_ROLE t");
		qSQL.append(" where name='").append(roleName).append("'");
		Role role = getJdbcTemplate().query(qSQL.toString(), new ResultSetExtractor<Role>(){

			@Override
			public Role extractData(ResultSet rst) throws SQLException,
					DataAccessException {
				Role role = new Role();
				if(rst.next()){
						role.setId(rst.getString("ID"));
						role.setDescription(rst.getString("DESCRIPTION"));
						role.setName(rst.getString("NAME"));
						role.setMemo(rst.getString("MEMO"));
						role.setDelFlag(rst.getString("DEL_FLAG"));
				}
				return role;
			}});
		return role;
	}

	@Override
	public Role findRoleEtyByName(String roleName) {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("from Role where name='").append(roleName).append("'");
		List<Role> roles = findByHQL(qSQL.toString());
		Role role = null;
		if(roles!=null && roles.size()>0){
			role = roles.get(0);
		}
		return role;
	}

}
