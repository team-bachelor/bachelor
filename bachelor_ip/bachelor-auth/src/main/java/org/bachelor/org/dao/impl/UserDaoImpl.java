/*
 * @(#)UserDaoImpl.java	Mar 15, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.org.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import org.bachelor.dao.impl.GenericDaoImpl;
import org.bachelor.org.dao.IUserDao;
import org.bachelor.org.domain.Org;
import org.bachelor.org.domain.User;

/**
 * @author Team Bachelor
 *
 */
@Repository
public class UserDaoImpl  extends GenericDaoImpl<User, String> implements IUserDao {

	/**
	 * 改变用户所在组织
	 * 
	 * @param user 用户实体类
	 * @param distOrg 目标组织的实体类
	 */
	@Override
	public void changeOrg(User user, Org distOrg) {
		
		user = super.findById(user.getId());
		user.setOwnerOrg(distOrg);
		this.update(user);
	}

	/**
	 * 创建用户实体类。用户实体类只需包含基本信息。 
	 * 
	 * @param user 用户实体类
	 * @param ownerOrg 用户所属的组织实体类
	 */
	@Override
	public void create(User user, Org ownerOrg) {
		
		user.setOwnerOrg(ownerOrg);
		this.saveOrUpdate(user);
	}

	/**
	 * 创建用户实体类。用户实体类只需包含基本信息。 
	 * 
	 * @param user 用户实体类
	 * @param ownerOrgId 用户所属的组织实体类Id
	 */
	@Override
	public void create(User user, String ownerOrgId) {
		
		Org org = new Org();
		org.setId(ownerOrgId);
		user.setOwnerOrg(org);
		this.save(user);
	}

	@Override
	public void deleteByid(String id) {

		this.deleteByid(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByOrgId(String orgId) { 
		StringBuilder hSQL = new StringBuilder();
		hSQL.append("from User u where u.statusFlag = '1' and u.ownerOrgId ='" + orgId + "'");
		Query query = getSession().createQuery(hSQL.toString());
		
		return query.list();
	}

}
