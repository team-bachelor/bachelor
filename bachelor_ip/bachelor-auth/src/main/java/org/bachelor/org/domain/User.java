/*
 * @(#)User.java	Mar 15, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.org.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.bachelor.core.entity.IBaseEntity;
import org.bachelor.ext.org.domain.UserExt;


/**
 * 用户基本信息实体类
 * @author Team Bachelor
 *
 */
@Entity
@Table(name="T_UFP_USER")
public class User implements Serializable, IBaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	/** 主键, 用户英文名也就是OA账号 **/
	private String id;
	
	/** 用户中文名 **/
	private String username;
	
	/** 用户备注 **/
	private String memo;
	
	/** 用户类型。1：同步用户,2：自定义用户 **/
	private String type;
	
	/** 职务名称 **/
	private String duty;
	
	/** 密码 **/
	private String pwd;
	
	@Column(name = "IDENTIFY_CODE")
	/** 身份证号 **/
	private String identifyCode;
	
	/** 性别。1：男，2：女 **/
	private String gender;
	
	@Column(name = "LOGIN_FLAG")
	/** 登录许可。1：可登陆,2：不可登录 **/
	private String loginFlag;
	
	@Column(name = "STATUS_FLAG")
	/** 状态标志。1：正常,2：已删除 **/
	private String statusFlag;
	
	@Column(name = "CREATE_TIME")
	/** 创建时间 **/
	private Date createTime;
	
	@Column(name = "UPDATE_TIME")
	/** 更新时间 **/
	private Date updateTime;
	
	@Column(name = "SYNC_TIME")
	/** 同步时间 **/
	private Date syncTime;
 
	@Column(name = "OWNER_ORG_ID")
	/** 所属组织ID **/
	private String ownerOrgId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	@Transient
	/** 用户扩展信息**/
	private UserExt userExt;
	
	@Transient
	/** 所属组织信息**/
	private Org ownerOrg;
	
	@Transient
	/** 所属组织名称**/
	private String ownerOrgName;
	
	/*@OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.EAGER, targetEntity = AuthRoleUser.class)
	private List<Role> roles;*/
	@Transient
	private String processType;
	
	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public UserExt getUserExt() {
		return userExt;
	}

	public String getOwnerOrgName() {
		return ownerOrgName;
	}

	public void setOwnerOrgName(String ownerOrgName) {
		this.ownerOrgName = ownerOrgName;
	}

	public void setUserExt(UserExt userExt) {
		this.userExt = userExt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getIdentifyCode() {
		return identifyCode;
	}

	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}

	public Org getOwnerOrg() {
		return ownerOrg;
	}

	public void setOwnerOrg(Org ownerOrg) {
		this.ownerOrg = ownerOrg;
	}

	/**
	 * @return the ownerOrgId
	 */
	public String getOwnerOrgId() {
		return ownerOrgId;
	}

	/**
	 * @param ownerOrgId the ownerOrgId to set
	 */
	public void setOwnerOrgId(String ownerOrgId) {
		this.ownerOrgId = ownerOrgId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.username;
	}
	
	
}
