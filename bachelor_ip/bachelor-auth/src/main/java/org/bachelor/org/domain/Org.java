/*
 * @(#)Org.java	Mar 15, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.org.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OrderBy;

import org.bachelor.ext.org.domain.OrgExt;

/**
 * @author Team Bachelor
 *
 */
@Entity
@Table(name="T_bchlr_ORG")
public class Org implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	/** 主键, 10位编码ID **/
	private String id;
	@Column(name = "NAME")
	/** 组织的中文名称 **/
	private String name;
	@Column(name = "SHORTNAME")
	/** 组织的简称 **/
	private String shortName;
	@Column(name = "FLAG")
	/** 是否需要前台展示(0:显示1不显示) **/
	private String flag;
	
	@Column(name = "PARENTID")
	/** 上级组织ID **/
	private String parentId;
	
	/** 子模块 **/
	@OneToMany(mappedBy = "parentId", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Org.class)
	@OrderBy(clause="showOrder asc")
	public Set<Org> childModules;
	
	
	@Column(name = "SHOW_ORDER")
	/** 显示顺序 **/
	private int showOrder = 0;
	
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
	
	/**公司分类**/
	@Column(name = "CATEGORY")
	private String  category;
	
	/**组织类型。1：同步，2：自定义**/
	@Column(name = "TYPE")
	private String type;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	/** 组织扩展信息**/
	private OrgExt orgExt;
	 
	@Transient
	/** 上级组织名称 **/
	private String parentName;
	
	@Transient
	/** 所属公司ID **/
	private String companyId;
	
	@Transient
	/** 所属公司名称 **/
	private String companyName;
	
	@Transient
	/** 所属公司简称 **/
	private String companyShortName;
	
	@Transient
	/** 所属部门ID **/
	private String departmentId;
	
	@Transient
	/** 所属部门名称  **/
	private String departmentName;
	
	@Transient
	/** 从公司一级到本对象级别的各组织ID路径,以"/"隔开 **/
	private String idPath;
	
	@Transient
	/** 从公司一级到本对象级别的各组织名称路径,以"/"隔开 **/
	private String namePath;
	
	@Transient
	/** 是否有子级 **/
	private Integer isChildren;
	 
	public Integer getIsChildren() {
		return isChildren;
	}

	public void setIsChildren(Integer isChildren) {
		this.isChildren = isChildren;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
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

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}



	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the companyShortName
	 */
	public String getCompanyShortName() {
		return companyShortName;
	}

	/**
	 * @param companyShortName the companyShortName to set
	 */
	public void setCompanyShortName(String companyShortName) {
		this.companyShortName = companyShortName;
	}

	/**
	 * @return the departmentId
	 */
	public String getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	
	/**
	 * @return the idPath
	 */
	public String getIdPath() {
		return idPath;
	}

	/**
	 * @param idPath the idPath to set
	 */
	public void setIdPath(String idPath) {
		this.idPath = idPath;
	}

	/**
	 * @return the namePath
	 */
	public String getNamePath() {
		return namePath;
	}

	/**
	 * @param namePath the namePath to set
	 */
	public void setNamePath(String namePath) {
		this.namePath = namePath;
	}

	/**
	 * @return the statusFlag
	 */
	public String getStatusFlag() {
		return statusFlag;
	}

	/**
	 * @param statusFlag the statusFlag to set
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	/**
	 * @return the orgExt
	 */
	public OrgExt getOrgExt() {
		return orgExt;
	}

	/**
	 * @param orgExt the orgExt to set
	 */
	public void setOrgExt(OrgExt orgExt) {
		this.orgExt = orgExt;
	}
	//0公司1部门 2上一级
	public String getEachOrgName(int type){
		String name = getNamePath();
		if(name!=null && name.length()>0){
			String names[] = name.split("/");
			return commonOrgInfo(names,type);
		}
		return "";
	}
	//0公司1部门 2上一级
	public String getEachOrgId(int type){
		String id = getIdPath();
		if(id!=null && id.length()>0){
			String ids[] = id.split("/");
			return commonOrgInfo(ids,type);
		}
		return "";
	}
	
	/**
	 * 能用ORG部分属性赋值
	 * @param names
	 * @param type
	 * @return
	 */
	public String commonOrgInfo(String names[],int type){
		//二级单位
		if(type==0 && names.length>0){
			
			return names[type];
		}
		//部门
		if(type==1 && names.length>1){
			
			return names[type];
		}
		//本部部门
		if(type==2){
			
			return names[(names.length-1)];
		}
		/*
		if(names.length==1){
			if(type == 0){
				return names[type];
			}  
		} 
		if(names.length == 2){
			if(type == 1){
				return names[type];
			}
		}
		if(names.length >= 3){
			if(type!=2){
				return names[type];
			} else {
				if(names.length-2>=0){
					return names[names.length-2];
				} else {
					return "";
				}
			}
		}*/ 
		return "";
	}
}
