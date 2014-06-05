/*
 * @(#)UserServiceImpl.java	Mar 15, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.org.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.bachelor.org.dao.IUserDao;
import org.bachelor.org.domain.Org;
import org.bachelor.org.domain.User;
import org.bachelor.org.service.IOrgService;
import org.bachelor.org.service.IUserService;


/**
 * 用户信息服务类
 * 
 * @author Team Bachelor
 *
 */
@Service
@RequestMapping("org/user/")
public class UserServiceImpl implements IUserService{

	@Autowired
	private IUserDao userDao = null;
	@Autowired
	private IOrgService orgService = null;
	/* (non-Javadoc)
	 * @see org.bachelor.org.service.IUserService#findById(java.lang.String)
	 */
	@Override
	@RequestMapping("findById")
	//@Cacheable(value="user")
	public User findById(String id) {
		User user = userDao.findById(id);
		if(user==null){
			return null;
		}
		Org org = orgService.findById(user.getOwnerOrgId());
		user.setOwnerOrg(org);
		return user;
	}

	/* (non-Javadoc)
	 * @see org.bachelor.org.service.IUserService#create(org.bachelor.org.domain.User, org.bachelor.org.domain.Org)
	 */
	@Override
	public void create(User user, Org ownerOrg) {
		// TODO Auto-generated method stub
		userDao.create(user, ownerOrg);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.org.service.IUserService#create(org.bachelor.org.domain.User, java.lang.String)
	 */
	@Override
	public void create(User user, String ownerOrgId) {
		// TODO Auto-generated method stub
		userDao.create(user,ownerOrgId);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.org.service.IUserService#delete(org.bachelor.org.domain.User)
	 */
	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
		userDao.delete(user);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.org.service.IUserService#deleteByid(java.lang.String)
	 */
	@Override
	public void deleteByid(String id) {
		// TODO Auto-generated method stub
		userDao.deleteByid(id);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.org.service.IUserService#update(org.bachelor.org.domain.User)
	 */
	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		userDao.update(user);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.org.service.IUserService#changeOrg(org.bachelor.org.domain.User, org.bachelor.org.domain.Org)
	 */
	@Override
	public void changeOrg(User user, Org distOrg) {
		// TODO Auto-generated method stub
		userDao.changeOrg(user, distOrg);
	}

	//保存或更新
	@Override
	public void saveOrUpdate(User user) {
		// TODO Auto-generated method stub
		userDao.saveOrUpdate(user);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.org.service.IUserService#findByOrgId(java.lang.String)
	 */
	@Override
	@RequestMapping("findByOrgId")
	@ResponseBody
	public List<User> findByOrgId(String id) {

		return userDao.findByOrgId(id);
	}

}
