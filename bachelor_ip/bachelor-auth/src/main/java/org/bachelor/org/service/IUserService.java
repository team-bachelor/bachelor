/*
 * @(#)IUserService.java	Mar 15, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.org.service;

import java.util.List;

import org.bachelor.org.domain.Org;
import org.bachelor.org.domain.User;

/**
 * 用户信息服务接口
 * 
 * @author Team Bachelor
 *
 */
public interface IUserService {

	/**
	 * 根据用户Id查询用户信息。
	 * 返回的用户信息包括：用户基本信息、用户扩展信息、用户所属组织信息。
	 * 
	 * @param id 用户Id
	 * @return 用户实体类
	 */
	public User findById(String id);
	
	/**
	 * 查询指定组织下的直属用户。
	 * 返回的用户信息包括：用户基本信息，用户扩展信息。不包括用户所属组织信息。
	 * 
	 * @param id 组织ID
	 * @return 指定组织下的直属用户信息列表。
	 */
	public List<User> findByOrgId(String id);
	
	/**
	 * 创建用户实体类。用户实体类只需包含基本信息。 
	 * 
	 * @param user 用户实体类
	 * @param ownerOrg 用户所属的组织实体类
	 */
	public void create(User user ,Org ownerOrg);
	
	/**
	 * 创建用户实体类。用户实体类只需包含基本信息。 
	 * 
	 * @param user 用户实体类
	 * @param ownerOrgId 用户所属的组织实体类Id
	 */
	public void create(User user ,String ownerOrgId);
	
	/**
	 * 删除用户
	 * 
	 * @param user 要删除的用户实体类
	 */
	public void delete(User user);
	
	/**
	 * 删除用户
	 * @param id 要删除的用户Id
	 */
	public void deleteByid(String id);
	
	/**
	 * 更新用户基本信息
	 * @param user 用户实体类
	 */
	public void update(User user);
	
	/**
	 * 改变用户所在组织
	 * 
	 * @param user 用户实体类
	 * @param distOrg 目标组织的实体类
	 */
	public void changeOrg(User user, Org distOrg);
	
	/**
	 * 新增或更新用户
	 * @param user
	 */
	public void saveOrUpdate(User user);
	

}