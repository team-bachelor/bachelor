/*
 * @(#)OrgDaoImpl.java	Mar 15, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.org.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import org.bachelor.dao.impl.GenericDaoImpl;
import org.bachelor.ext.org.domain.OrgExt;
import org.bachelor.org.dao.IOrgDao;
import org.bachelor.org.domain.Org;
import org.bachelor.org.vo.OrgVo;

/**
 * @author Team Bachelor
 * 
 */
@Repository
@SuppressWarnings("unchecked")
public class OrgDaoImpl extends GenericDaoImpl<Org, String> implements IOrgDao {

	/**
	 * 根据组织Id查询组织信息。 如果要查询北京电力公司，请传入null.
	 * 
	 * @param id
	 *            组织Id
	 * @return 组织信息实体类
	 */
	public Org findById(String id) {
		if (id == null || id.trim().equals("")) {
			id = "0600000000";
		}
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("select t.id,t.shortname,t.show_order,t.flag,t.parentid,t.name,");
		qSQL.append("t.create_time,t.update_time,t.sync_time,t.category,t.type,t.status_flag,");
		qSQL.append("v.bm idpath,v.nm namepath");
		qSQL.append(" from t_bchlr_org t, org_vw v where v.id = t.id and t.status_Flag='1' ");// and
																							// t.flag='1'
		qSQL.append(" and t.id = '").append(id).append("'");
		qSQL.append(" order by show_order desc");
		Org org = getJdbcTemplate().query(qSQL.toString(),
				new ResultSetExtractor<Org>() {

					@Override
					public Org extractData(ResultSet rst) throws SQLException,
							DataAccessException {
						Org org = new Org();
						if (rst.next()) {
							achieveOrg(org, rst);
						}
						return org;
					}
				});
		return org;
	}

	/** 赋值ORG信息 **/
	public void achieveOrg(Org org, ResultSet rst) throws SQLException {
		org.setCategory(rst.getString("category"));
		org.setId(rst.getString("id"));
		org.setShortName(rst.getString("shortname"));
		org.setShowOrder(rst.getInt("show_order"));
		org.setFlag(rst.getString("flag"));
		org.setParentId(rst.getString("parentid"));
		org.setName(rst.getString("name"));
		org.setCreateTime(rst.getDate("create_time"));
		org.setUpdateTime(rst.getDate("update_time"));
		org.setSyncTime(rst.getDate("sync_time"));
		org.setType(rst.getString("type"));
		org.setStatusFlag(rst.getString("status_flag"));
		org.setIdPath(rst.getString("idpath"));
		org.setNamePath(rst.getString("namepath"));

		org.setCompanyId(org.getEachOrgId(0));
		org.setCompanyName(org.getEachOrgName(0));

		org.setDepartmentId(org.getEachOrgId(1));
		org.setDepartmentName(org.getEachOrgName(1));

		org.setParentName(org.getEachOrgName(2));

		org.setOrgExt(findOrgExtById(org.getId()));
	}

	/**
	 * 根据ID查询，显示或者不显示信息
	 * 
	 * @param id
	 * @return
	 */
	public Org findAllById(String id) {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("select t.id,t.shortname,t.show_order,t.flag,t.parentid,t.name,");
		qSQL.append("t.create_time,t.update_time,t.sync_time,t.category,t.type,t.status_flag,");
		qSQL.append("v.bm idpath,v.nm namepath");
		qSQL.append(" from t_bchlr_org t, org_vw v where v.id = t.id and t.status_Flag='1' ");

		qSQL.append(" and t.id = '").append(id).append("'");
		qSQL.append(" order by show_order desc");
		Org org = getJdbcTemplate().query(qSQL.toString(),
				new ResultSetExtractor<Org>() {

					@Override
					public Org extractData(ResultSet rs) throws SQLException,
							DataAccessException {
						Org org = new Org();
						if (rs.next()) {
							achieveOrg(org, rs);
						}
						return org;
					}
				});
		return org;
	}

	public OrgExt findOrgExtById(String id) {
		StringBuilder qSQL = new StringBuilder("from OrgExt o where o.id='")
				.append(id).append("'");
		Query query = getSession().createQuery(qSQL.toString());
		List<OrgExt> org_list = query.list();
		OrgExt oe = new OrgExt();
		if (org_list != null && org_list.size() > 0) {
			oe = org_list.get(0);
		}
		return oe;
	}

	/**
	 * 根据组织Id查询改组织所在的公司信息 id 是否北京电力公司 是返回null 否ID所属的公司实体 ID有可能是部门或科室等
	 * 
	 * @param id
	 *            组织Id
	 * @return 公司信息实体类
	 */
	public Org findCompany(String id) {
		if (id == null || id.equals("0600000000")) {
			return null;
		}

		return super.findById((id.substring(0, 4) + "000000"));// 先拼合公司ID编码，再进行查询
	}

	/**
	 * 根据组织Id查询改组织所在的部门信息
	 * 
	 * 参数ID 如果是部门及部门以下的ID值就返回当前实体 否则 返回NULL ID有可能是部门或科室等
	 * 
	 * @param id
	 *            部门Id
	 * @return 部门信息实体类
	 */
	public Org findDepartment(String id) {
		if (id.indexOf("0000") < 0) {
			return super.findById((id.substring(0, 6) + "0000"));// 先拼合公司ID编码，再进行查询
		} else {
			return null;
		}
	}

	/**
	 * 根据组织Id查询该组织的下级组织信息 如果组织Id为空，则返回北京电力公司的下级组织信息，也就是各公司。
	 * 如果没有下级组织信息，则返回长度为0的List。 排序规则是SHOW_ORDER字段升序排序
	 * 
	 * @param id
	 *            组织Id
	 * @return 该组织的下级组织信息列表
	 */
	public List<Org> findSubOrg(String id) {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("select t.id,t.parentid,t.name,t.shortname,t.show_order,");
		qSQL.append("t.flag,t.create_time,t.update_time,");
		qSQL.append("t.sync_time,t.category,t.type,t.status_flag from t_bchlr_org t");
		qSQL.append(" where t.parentid = '").append(id).append("'");
		qSQL.append(" and flag='1' and status_Flag='1' order by t.show_order asc");
		List<Org> org_list = getJdbcTemplate().query(qSQL.toString(),
				new RowMapper() {

					@Override
					public Org mapRow(ResultSet rst, int arg1)
							throws SQLException {
						Org org = new Org();
						org.setId(rst.getString("id"));
						org.setParentId(rst.getString("parentid"));
						org.setName(rst.getString("name"));
						org.setShortName(rst.getString("shortname"));
						org.setShowOrder(rst.getInt("show_order"));
						org.setFlag(rst.getString("flag"));
						org.setCreateTime(rst.getDate("Create_Time"));
						org.setUpdateTime(rst.getDate("UPDATE_TIME"));
						org.setSyncTime(rst.getDate("SYNC_TIME"));
						org.setCategory(rst.getString("category"));
						org.setType(rst.getString("type"));
						org.setStatusFlag(rst.getString("status_flag"));
						return org;
					}
				});
		// 如果为NULL时创建新集合返回，反之则返回当前集合
		return (org_list == null ? new ArrayList<Org>() : org_list);
	}

	/**
	 * 功能: 查询所有二级单位信息 作者 曾强 2013-5-22下午05:33:57
	 * 
	 * @param orgVo
	 * @return
	 */
	@Override
	public List<OrgVo> findAllSecondUnitInfo(OrgVo orgVo) {
		StringBuilder qSQL = new StringBuilder();
		List<OrgVo> vo_list = new ArrayList<OrgVo>();
		qSQL.append("select t.id,t.parentid,t.name,t.flag,t.shortname from t_bchlr_org t where flag='1' and statusFlag='1' ");
		if (orgVo != null) {
			if (orgVo.getId() != null && !orgVo.getId().equals("")) {
				qSQL.append(" and t.parentid = '").append(orgVo.getId())
						.append("'");
			}
		}
		qSQL.append(" order by t.id desc ");
		vo_list = getJdbcTemplate().query(qSQL.toString(), new RowMapper() {

			@Override
			public OrgVo mapRow(ResultSet rst, int arg1) throws SQLException {
				OrgVo vo = new OrgVo();
				vo.setFlag(rst.getString("FLAG"));
				vo.setId(rst.getString("ID"));
				vo.setName(rst.getString("NAME"));
				vo.setShortName(rst.getString("SHORTNAME"));
				vo.setParentId(rst.getString("parentid"));
				return vo;
			}
		});
		return vo_list;
	}

	@Override
	public void changeVisible(List<String> orgIds, boolean visible) {
		String dSQL[] = new String[orgIds.size()];
		int index = 0;
		for (String orgId : orgIds) {
			if (orgId != null && !orgId.equals("")) {
				String sql = "update T_bchlr_ORG set FLAG='2' where id='" + orgId
						+ "'";
				dSQL[index] = sql;
				index++;
			}
		}
		// 批量更新
		getJdbcTemplate().batchUpdate(dSQL);
	}

	@Override
	public void logicDelete(List<String> orgIds) {
		String dSQL[] = new String[orgIds.size()];
		int index = 0;
		for (String orgId : orgIds) {
			if (orgId != null && !orgId.equals("")) {
				String sql = "update T_bchlr_ORG set STATUS_FLAG='2' where id='"
						+ orgId + "'";
				dSQL[index] = sql;
				index++;
			}
		}
		getJdbcTemplate().batchUpdate(dSQL);// 批量删除
	}

	@Override
	public List<Org> findOrgTreeInfo(Org org) {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("from Org where parentId = '").append(org.getId())
				.append("'");
		if (org.getFlag() != null && !"".equals(org.getFlag())) {
			qSQL.append(" and flag='").append(org.getFlag()).append("'");
		}
		qSQL.append(" and statusFlag='1' order by showOrder asc");
		Query query = getSession().createQuery(qSQL.toString());
		return query.list();
	}

	@Override
	public Org findOrgByUserId(String userId) {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("select * from org_user_vw where id='").append(userId)
				.append("' ");
		Org org = getJdbcTemplate().query(qSQL.toString(),
				new ResultSetExtractor<Org>() {

					@Override
					public Org extractData(ResultSet rs) throws SQLException,
							DataAccessException {
						Org org = new Org();
						if (rs.next()) {
							org.setCompanyName(rs.getString("EJDW"));
							org.setCompanyId(rs.getString("ORGID"));
						}
						return org;
					}
				});
		return org;
	}

	@Override
	public List<Org> findShowOrgTreeInfo(String pid) {
		StringBuilder qSQL = new StringBuilder();
		String parentId = pid;
		if (StringUtils.isEmpty(pid)) {

			parentId = "0600000000";
		}
		qSQL.append("from Org where parentId = '" + parentId
				+ "' and statusFlag='1' and flag='1' order by showOrder asc");
		Query query = getSession().createQuery(qSQL.toString());
		return query.list();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Org> findOrgsById(String id, String flag) {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("select o.id,o.parentid,o.name,(");
		/** 统计是否有子项 **/
		qSQL.append("select count(*) from (");
		qSQL.append("select f.id parent_id,f.name parent_name,o.id my_id,o.name my_name,c.id child_id,c.name child_name ");
		qSQL.append("from t_bchlr_org f,t_bchlr_org o,t_bchlr_org c where o.parentid = f.id (+) and o.id = c.parentid (+)  ");
		if (!StringUtils.isEmpty(flag) && "1".equals(flag)) {
			qSQL.append(" and o.flag = '1'");
		}
		qSQL.append(" ) org where org.parent_id = o.id) is_children ");
		/** 统计是否有子项 **/
		qSQL.append("from t_bchlr_org o where o.parentid = '").append(id)
				.append("'");
		if (!StringUtils.isEmpty(flag) && "1".equals(flag)) {
			qSQL.append(" and o.flag = '1'");
		}
		List<Org> orgs = getJdbcTemplate().query(qSQL.toString(),
				new RowMapper() {

					@Override
					public Org mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Org org = new Org();
						org.setId(rs.getString("id"));
						org.setParentId(rs.getString("parentid"));
						org.setName(rs.getString("name"));
						org.setIsChildren(rs.getInt("is_children"));
						return org;
					}
				});
		return orgs;
	}

	@Override
	public Boolean hasChildenOrg(String orgId) {
		List<Org> list = this.findShowOrgTreeInfo(orgId);
		if (list != null && list.size() > 0) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}
