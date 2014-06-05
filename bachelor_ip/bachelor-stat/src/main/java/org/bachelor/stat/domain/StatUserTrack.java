package org.bachelor.stat.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_ufp_stat_user_track")
public class StatUserTrack implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7608323069005884056L;

	@Id
	@GenericGenerator(name="uuidGen" ,strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	
	@Column(name="FUNC_ID")
	private String funcId;
	
	@Column(name="MENU_ID")
	private String menuId;
	
	@Column(name="CLASS_NAME")
	private String className;
	
	private String method;
	
	@Column(name="SERVER_IP")
	private String serverIp;
	
	@Column(name="CLIENT_IP")
	private String clientIp;
	
	@Column(name="ACCESS_URI")
	private String accessUri;
	
	@Column(name="ACCESS_TIME")
	private Date accessTime;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="USER_NAME")
	private String userName;
	
	private String type;
	
	@Column(name="ORG_ID")
	private String orgId;
	
	@Column(name="ORG_NAME")
	private String orgName;
	
	@Column(name="ACCESS_URL")
	private String accessUrl;
	
	@Column(name="ACCESS_PARAM")
	private String accessParam;

	public String getAccessUrl() {
		return accessUrl;
	}

	public void setAccessUrl(String accessUrl) {
		this.accessUrl = accessUrl;
	}

	public String getAccessParam() {
		return accessParam;
	}

	public void setAccessParam(String accessParam) {
		this.accessParam = accessParam;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
 
	public String getAccessUri() {
		return accessUri;
	}

	public void setAccessUri(String accessUri) {
		this.accessUri = accessUri;
	}
 
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
