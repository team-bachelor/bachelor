/*
 * @(#)OrgServiceImpl.java	Mar 15, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.org.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.bachelor.org.dao.IOrgDao;
import org.bachelor.org.domain.Org;
import org.bachelor.org.service.IOrgService;
import org.bachelor.org.vo.OrgVo;

/**
 * 
 * 组织信息服务接口
 * @author Team Bachelor
 *
 */
@RequestMapping("org/")
@Service
public class OrgServiceImpl implements IOrgService{

	@Autowired
	private IOrgDao orgDao = null;
	
	/* (non-Javadoc)
	 * @see org.bachelor.org.service.IOrgService#findById(java.lang.String)
	 */
	@Override
	@RequestMapping("findById")
	public Org findById(String id) {
		
		return orgDao.findById(id);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.org.service.IOrgService#findCompany(java.lang.String)
	 */
	@Override
	public Org findCompany(String id) {
		// TODO Auto-generated method stub
		return orgDao.findCompany(id);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.org.service.IOrgService#findDepartment(java.lang.String)
	 */
	@Override
	public Org findDepartment(String id) {
		// TODO Auto-generated method stub
		return orgDao.findDepartment(id);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.org.service.IOrgService#findSubOrg(java.lang.String)
	 */
	@Override
	@RequestMapping("findSubOrg")
	@ResponseBody
	public List<Org> findSubOrg(String id) {
		// TODO Auto-generated method stub
		return orgDao.findSubOrg(id);
	}

	/**
	 * 功能: 
	 * 作者 曾强 2013-5-23下午04:24:17
	 * @param org
	 * @param visible
	 */
	@Override
	public void changeVisible(List<String> orgIds, boolean visible) {
		// TODO Auto-generated method stub
		if(orgIds!=null && orgIds.size()>0){
			orgDao.changeVisible(orgIds, visible);
		}
	}

	@Override
	public void logicDelete(List<String> orgIds) {
		// TODO Auto-generated method stub
		if(orgIds!=null && orgIds.size()>0){
			orgDao.logicDelete(orgIds);
		}
	}

	@Override
	public List<Org> findOrgTreeInfo(Org org) {

		return orgDao.findOrgTreeInfo(org);
	}

	
	/**
	 * 查询所有二级单位
	 */
	@Override
	public List<Org> findAllCompany() {
		
		return orgDao.findSubOrg("0600000000");
	}
	/**
	 * 更新组织机构信息
	 * @param org
	 */
	@Override
	public void update(Org org) {
		
		orgDao.update(org);
	}

	@Override
	public Org findAllById(String id) {
		// TODO Auto-generated method stub
		return orgDao.findAllById(id);
	}

	@Override
	public Org findOrgByUserId(String userId) {
		 
		return orgDao.findOrgByUserId(userId);
	}

	@Override
	public List<Org> findShowOrgTreeInfo(String pid) {
		
		return orgDao.findShowOrgTreeInfo(pid);
	}

	@Override
	public List<Org> findOrgsById(String id,String flag) {
		 
		return orgDao.findOrgsById(id,flag);
	}

	@Override
	public Boolean hasChildenOrg(String orgId) {
		return orgDao.hasChildenOrg(orgId);
	}
}
