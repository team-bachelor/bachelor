/*
 * @(#)Role.java	Mar 18, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.auth.domain;

import java.io.Serializable;
import java.util.List;

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

/**
 * 角色类
 * 
 * @author Team Bachelor
 *
 */
@Entity
@Table(name="T_UFP_AUTH_ROLE")
public class Role implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3318067031789219527L;

	@Id
	@GenericGenerator(name="uuidGen", strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	
	/** 角色名称 **/
	private String name;
	/** 角色描述 **/
	private String description;
	/** 备注 **/
	@OrderBy(clause="memo desc")
	private String memo;
	/**删除标识**/
	@Column(name="DEL_FLAG")
	private String delFlag;
	
	private String type;
	
	@OneToMany(mappedBy = "role", cascade = CascadeType.MERGE, fetch = FetchType.EAGER, targetEntity = AuthFunction.class)
	private List<AuthFunction> authFunctions;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="GROUP_ID")
	private RoleGroup group;
	
	@Column(name="SHOW_ORDER")
	private String showOrder;
	
	/*@Transient
	private  List<AuthorizedResource> authMenu;*/
	
	/*@OneToMany(mappedBy = "role", cascade = CascadeType.MERGE, fetch = FetchType.EAGER, targetEntity = AuthRoleUser.class)
	private List<AuthRoleUser> authRoleUsers;
 */ 
 
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public String getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(String showOrder) {
		this.showOrder = showOrder;
	}

	public RoleGroup getGroup() {
		return group;
	}

	public void setGroup(RoleGroup group) {
		this.group = group;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public List<AuthFunction> getAuthFunctions() {
		return authFunctions;
	}

	public void setAuthFunctions(List<AuthFunction> authFunctions) {
		this.authFunctions = authFunctions;
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
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
}
