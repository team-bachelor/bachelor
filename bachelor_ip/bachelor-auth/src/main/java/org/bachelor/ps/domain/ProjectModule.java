/*
 * @(#)ProjectModule.java	Mar 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OrderBy;
import org.springframework.core.annotation.Order;


/**
 * @author Team Bachelor
 *
 */
@Entity
@Table(name="T_bchlr_PS_MODULE")
public class ProjectModule implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7943757136144644255L;

	@Id
	@GenericGenerator(name="uuidGen", strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	
	/** 模块名称 **/
	private String name;
	/** 模块描述 **/
	private String description;
	 
	/** 模块显示顺序 **/
	@Column(name = "ORDER_ID")
	@Order(value = 1)
	private String orderId;
	
	/** 模块类型 1:菜单,2:服务,3:接口**/
	private String type; 
	
	/**   模块入口功能ID**/
	@Column(name = "ENTRY_FUNCTION_ID")
	private String entryFunctionId;
	
	/** 父模块 **/
	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name = "PARENT_ID")
	private ProjectModule parentModule;
	
	/** 子模块 **/
	@OneToMany(mappedBy = "parentModule", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ProjectModule.class)
	private Set<ProjectModule> childModules;
	
	/** 功能集合 **/
	@OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Function.class)
	@OrderBy(clause="id asc")
	private Set<Function> functions;
	
	/** 业务域 **/
	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name = "BUSINESS_DOMAIN_ID")
	private BusinessDomain pmbd;
	
	private String  code;
	/**  扩展信息集合  **/
	/*@OneToMany(mappedBy = "extModule", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ProjectModuleExt.class)
	private Set<ProjectModuleExt> pmes = new HashSet<ProjectModuleExt>();
	
	public Set<ProjectModuleExt> getPmes() {
		return pmes;
	}
	public void setPmes(Set<ProjectModuleExt> pmes) {
		this.pmes = pmes;
	}*/
	public String getOrderId() {
		return orderId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public ProjectModule getParentModule() {
		return parentModule;
	}
	public void setParentModule(ProjectModule parentModule) {
		this.parentModule = parentModule;
	}
	
	public Set<ProjectModule> getChildModules() {
		return childModules;
	}
	public void setChildModules(Set<ProjectModule> childModules) {
		this.childModules = childModules;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BusinessDomain getPmbd() {
		return pmbd;
	}
	public void setPmbd(BusinessDomain pmbd) {
		this.pmbd = pmbd;
	}
	public String getEntryFunctionId() {
		return entryFunctionId;
	}
	public void setEntryFunctionId(String entryFunctionId) {
		this.entryFunctionId = entryFunctionId;
	}
	 
	public Set<Function> getFunctions() {
		return functions;
	}
	public void setFunctions(Set<Function> functions) {
		this.functions = functions;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	 
}
