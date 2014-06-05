/*
 * @(#)BusinessDomain.java	Mar 26, 2013
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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 业务域对象
 * 
 * @author user
 *
 */
@Entity
@Table(name="T_UFP_PS_BUSINESS_DOMAIN")
public class BusinessDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2403803291938374667L;

	/** 业务域ID **/
	@Id
	@GenericGenerator(name="uuidGen", strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	
	/** 业务域名称**/
	private String name;
	
	/** 业务域描述 **/
	private String description;
	
	/** 父业务域对象 **/
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "PARENT_ID")
	private BusinessDomain parent;
	
	/** 子业务域对象集合 **/
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = BusinessDomain.class)
	private Set<BusinessDomain> children;
	
/*	@OneToMany(mappedBy="parentBd", fetch = FetchType.EAGER)
	@OrderBy("name")
	private Set<Function> funcList = new HashSet<Function>();*/

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	public Set<BusinessDomain> getChildren() {
		return children;
	}

	public void setChildren(Set<BusinessDomain> children) {
		this.children = children;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the parent
	 */
	public BusinessDomain getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(BusinessDomain parent) {
		this.parent = parent;
	} 
}
