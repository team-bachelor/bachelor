/*
 * @(#)IOrgService.java	Mar 15, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.org.service;

import java.util.List;

import org.bachelor.org.domain.Org;

/**
 * 组织信息服务接口
 * 
 * @author Team Bachelor
 *
 */
public interface IOrgService {

	/**
	 * 根据组织Id查询组织信息。
	 * 如果要查询北京电力公司，请传入null.
	 * 
	 * @param id 组织Id
	 * @return 组织信息实体类
	 */
	public Org findById(String id);
	
	/**
	 * 根据组织Id查询改组织所在的公司信息
	 * 
	 * @param id 组织Id
	 * @return 公司信息实体类
	 */
	public Org findCompany(String id);
	
	
	/**
	 * 根据组织Id查询改组织所在的部门信息
	 * @param id 部门Id
	 * @return 部门信息实体类
	 */
	public Org findDepartment(String id);
	
	/**
	 * 根据组织Id查询该组织的下级组织信息
	 * 如果组织Id为空，则返回北京电力公司的下级组织信息，也就是各公司。
	 * 如果没有下级组织信息，则返回长度为0的List。
	 * 排序规则是SHOW_ORDER字段升序排序
	 * 
	 * @param id 组织Id
	 * @return 该组织的下级组织信息列表
	 */
	public List<Org> findSubOrg(String id);
	
	/**
	 * 功能: 根据组织ID，查询ORG数据结构信息
	 * 作者 曾强 2013-5-31上午11:16:59
	 * @param id
	 * @return
	 */
	public List<Org> findOrgTreeInfo(Org org);
	
	/**
	 * 设置Org对象是否在前台显示
	 * 
	 * @param Org 组织对象
	 * @param visible 是否在前台显示。true：显示，false：不显示。
	 */
	public void changeVisible(List<String> orgIds, boolean visible);
	
	/**
	 * 逻辑删除Org对象
	 * 
	 * @param Org 组织对象
	 */
	public void logicDelete(List<String> orgIds);
	
	/**
	 * 查询所有二级单位信息
	 * @return
	 */
	public List<Org> findAllCompany();
	
	/**
	 * 更新组织机构信息
	 * @param org
	 */
	public void update(Org org);
	
	/**
	 * 根据ID查询，显示或者不显示信息
	 * @param id
	 * @return
	 */
	public Org findAllById(String id);
	
	/**
	 * 根据用户ID查询ORG信息
	 * @param userId
	 * @return
	 */
	public Org findOrgByUserId(String userId);
	
	/**
	 * 功能: 显示设置为在页面显示的树结构
	 * 作者 曾强 2013-5-31上午11:16:59
	 * @param id
	 * @return
	 */
	public List<Org> findShowOrgTreeInfo(String pid);
	
	/**
	 *  根据ID查询子菜单
	 * @param id
	 * @return
	 */
	public List<Org> findOrgsById(String id,String flag);
	
	/**
	 * 根据单位id，判断该单位是否存在子集机构
	 * @param orgId 单位id
	 * @return 指定单位存在子集返回true，不存在返回false
	 */
	public Boolean hasChildenOrg(String orgId);
	
	
}