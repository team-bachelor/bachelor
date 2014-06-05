/*
 * @(#)Role.java	Mar 18, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.auth.domain;

import java.io.Serializable;
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
import org.hibernate.annotations.OrderBy;


/**
 * 角色类
 * 
 * @author Team Bachelor
 *
 */
@Entity
@Table(name="T_UFP_AUTH_ROLE_GROUP")
public class RoleGroup implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3318067031789219527L;

	@Id
	@GenericGenerator(name="uuidGen", strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	 
	private String name;
	
	private String description;
	
	private String type;
	
	private String flag;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "PARENT_ID")
	private  RoleGroup  parentRoleGroup ;
	
	@OneToMany(mappedBy = "parentRoleGroup", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, targetEntity = RoleGroup.class)
	@OrderBy(clause="name desc")
	private Set<RoleGroup> childrenRoleGroups;

	/** 角色集合 **/
	@OneToMany(mappedBy = "group", cascade = CascadeType.MERGE, fetch = FetchType.EAGER, targetEntity = Role.class)
	@OrderBy(clause="showOrder asc")
	private Set<Role> roles;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public RoleGroup getParentRoleGroup() {
		return parentRoleGroup;
	}

	public void setParentRoleGroup(RoleGroup parentRoleGroup) {
		this.parentRoleGroup = parentRoleGroup;
	}

	public Set<RoleGroup> getChildrenRoleGroups() {
		return childrenRoleGroups;
	}

	public void setChildrenRoleGroups(Set<RoleGroup> childrenRoleGroups) {
		this.childrenRoleGroups = childrenRoleGroups;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}	
}
