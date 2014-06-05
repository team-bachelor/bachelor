/*
 * @(#)ExMsg.java	Apr 23, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ex.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 异常信息实体类
 * 
 * @author Team Bachelor
 *
 */
@Entity
@Table(name="T_UFP_EXCEPTION_MSG")
public class ExMsg {

	@Id
	@GenericGenerator(name="uuidGen", strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	
	@Column(name="MSG")
	private String msg;
	
	@Column(name="TRACE_INFO")
	private String trace;
	
	@Column(name="MODULE_ID")
	private String moduleId;
	
	@Column(name="LAYER_TYPE")
	private String layer;
	
	@Column(name="CAUSE")
	private String cause;
	
	@Column(name="CLASS_NAME")
	private String className;
	
	@Column(name="OCCUR_TIME",columnDefinition="Date")
	private Date occurTime;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="ENTRY_INFO")
	private String entryInfo;

	@Transient
	private String userName;
	
	@Transient
	private String moduleName;
	
	@Transient
	private String exStartTime;
	
	@Transient
	private String exEndTime;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getExStartTime() {
		return exStartTime;
	}

	public void setExStartTime(String exStartTime) {
		this.exStartTime = exStartTime;
	}

	public String getExEndTime() {
		return exEndTime;
	}

	public void setExEndTime(String exEndTime) {
		this.exEndTime = exEndTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
 
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEntryInfo() {
		return entryInfo;
	}

	public void setEntryInfo(String entryInfo) {
		this.entryInfo = entryInfo;
	}

	public String getOccurTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return (this.occurTime!=null?sdf.format(this.occurTime):"");
	}

	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}
}
