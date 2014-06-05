package org.bachelor.auth.vo;

import java.io.Serializable;


public class AuthUiResourceVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7287001720939580363L;

	private String id;
	
	private String uiTypeId;
	
	private String uiResourceId;
	
	private String uiResourceDesc;
	
	private String uiResourcePermission;
	
	private String aurFuncId;
	
	private String funcName;
	
	private String roleId;
	
	private String roleName;
	
	private String uiTypeName;
	
	private String permissionName;
	
	private String flowId;
	
	private String flowVersion;
	
	private String joinId;
	
	private String joinName;
	
	private String uiTypeSelector;
	
	private String flowName;
	
	/** 查询类型 0必须要有funcId 1根据条件进行查询 **/
	private String searchType;
	
	private String roleMemo;
	
	public String getRoleMemo() {
		return roleMemo;
	}

	public void setRoleMemo(String roleMemo) {
		this.roleMemo = roleMemo;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

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

	public String getUiTypeName() {
		return uiTypeName;
	}

	public void setUiTypeName(String uiTypeName) {
		this.uiTypeName = uiTypeName;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
 
	public String getAurFuncId() {
		return aurFuncId;
	}

	public void setAurFuncId(String aurFuncId) {
		this.aurFuncId = aurFuncId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
