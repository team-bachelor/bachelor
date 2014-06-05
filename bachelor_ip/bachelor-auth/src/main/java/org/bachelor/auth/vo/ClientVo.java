/*
 * @(#)ClientVo.java	May 27, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.auth.vo;

import java.util.Date;

/**
 * 登录客户端信息
 * @author Team Bachelor
 *
 */
public class ClientVo {

	/** 客户端IP **/
	private String ip;
	/** 客户端浏览器类型 **/
	private String clientType;
	/** 客户端浏览器版本**/
	private String clientVer;
	/** 客户端登录时间 **/
	private Date loginTime = new Date();
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the clientType
	 */
	public String getClientType() {
		return clientType;
	}
	/**
	 * @param clientType the clientType to set
	 */
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	/**
	 * @return the loginTime
	 */
	public Date getLoginTime() {
		return loginTime;
	}
	/**
	 * @param loginTime the loginTime to set
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	/**
	 * @return the clientVer
	 */
	public String getClientVer() {
		return clientVer;
	}
	/**
	 * @param clientVer the clientVer to set
	 */
	public void setClientVer(String clientVer) {
		this.clientVer = clientVer;
	}
	
	
}
