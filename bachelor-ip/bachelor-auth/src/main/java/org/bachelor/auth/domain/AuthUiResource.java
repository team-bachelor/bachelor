package org.bachelor.auth.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import org.bachelor.ps.domain.Function;

@Entity
@Table(name = "T_bchlr_AUTH_UI_RESOURCE")
public class AuthUiResource {

	@Id
	@GenericGenerator(name="uuidGen", strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	
	@Column(name = "UI_TYPE_ID")
	private String uiTypeId;
	
	@Transient
	private String uiTypeName;
	
	@Column(name = "UI_RESOURCE_ID")
	private String uiResourceId;
	
	@Column(name = "UI_RESOURCE_DESC")
	private String uiResourceDesc;
	
	@Column(name = "UI_RESOURCE_PERMISSION")
	private String uiResourcePermission;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUNCTION_ID")
	private Function func;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	private Role role;
	
	@Column(name="FLOW_ID")
	private String flowId;
	
	@Column(name="VERSION_NAME")
	private String flowVersion;
	
	@Column(name="JOIN_ID")
	private String joinId;
	
	@Column(name="JOIN_NAME")
	private String joinName;
	
	@Column(name="UI_TYPE_SELECTOR")
	private String uiTypeSelector;
	
	@Column(name="FLOW_NAME")
	private String flowName;
	
	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public String getJoinName() {
		return joinName;
	}

	public void setJoinName(String joinName) {
		this.joinName = joinName;
	}

	public String getUiTypeSelector() {
		return uiTypeSelector;
	}

	public void setUiTypeSelector(String uiTypeSelector) {
		this.uiTypeSelector = uiTypeSelector;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getFlowVersion() {
		return flowVersion;
	}

	public void setFlowVersion(String flowVersion) {
		this.flowVersion = flowVersion;
	}

	public String getJoinId() {
		return joinId;
	}

	public void setJoinId(String joinId) {
		this.joinId = joinId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUiTypeId() {
		return uiTypeId;
	}

	public void setUiTypeId(String uiTypeId) {
		this.uiTypeId = uiTypeId;
	}

	public String getUiTypeName() {
		return uiTypeName;
	}

	public void setUiTypeName(String uiTypeName) {
		this.uiTypeName = uiTypeName;
	}

	public String getUiResourceId() {
		return uiResourceId;
	}

	public void setUiResourceId(String uiResourceId) {
		this.uiResourceId = uiResourceId;
	}

	public String getUiResourceDesc() {
		return uiResourceDesc;
	}

	public void setUiResourceDesc(String uiResourceDesc) {
		this.uiResourceDesc = uiResourceDesc;
	}

	public String getUiResourcePermission() {
		return uiResourcePermission;
	}

	public void setUiResourcePermission(String uiResourcePermission) {
		this.uiResourcePermission = uiResourcePermission;
	}

	public Function getFunc() {
		return func;
	}

	public void setFunc(Function func) {
		this.func = func;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
