/*
 * @(#)RoleDaoImpl.java	Mar 18, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.auth.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.bachelor.auth.dao.IRoleGroupDao;
import org.bachelor.auth.domain.RoleGroup;
import org.bachelor.dao.impl.GenericDaoImpl;

/**
 * @author
 *
 */
@Repository
public class RoleGroupDaoImpl extends GenericDaoImpl<RoleGroup, String>  implements IRoleGroupDao {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<RoleGroup> findAllRoleInfo() {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("select t.ID,t.NAME,t.DESCRIPTION,t.DEL_FLAG,t.PARENT_ID from T_bchlr_AUTH_ROLE t");
		List<RoleGroup> role_list = getJdbcTemplate().query(qSQL.toString(), new RowMapper(){

			@Override
			public RoleGroup mapRow(ResultSet rst, int arg1) throws SQLException {
				RoleGroup roleGroup = new RoleGroup();
				roleGroup.setId(rst.getString("ID"));
				roleGroup.setDescription(rst.getString("DESCRIPTION"));
				roleGroup.setName(rst.getString("NAME"));
				roleGroup.setFlag(rst.getString("DEL_FLAG"));
				RoleGroup parentRoleGroup = new RoleGroup();
				parentRoleGroup.setId(rst.getString("PARENT_ID"));
				roleGroup.setParentRoleGroup(parentRoleGroup);
				return roleGroup;
			}});
		return role_list;
	}
 

	@Override
	public RoleGroup findByName(String roleName) {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("from RoleGroup where name ='").append(roleName).append("'");
		Query query = getSession().createQuery(qSQL.toString());
		List<RoleGroup> role_list = query.list();
		return (role_list!=null?(role_list.size()>0?role_list.get(0):null):null);
	}


	@Override
	public List<RoleGroup> findChildren(String parentModuleName) {
		String hql = "from RoleGroup";
		if(StringUtils.isEmpty(parentModuleName)){
			hql += " where parentRoleGroup is null";
		}else{
			hql += " where parentRoleGroup.name ='" + parentModuleName + "'";
		}
		return super.findByHQL(hql);
	}

}
