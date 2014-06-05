/*
 * @(#)ProjectProperty.java	Mar 7, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;

import org.hibernate.annotations.GenericGenerator;
 
/**
 * 项目属性类
 * 
 * @author Team Bachelor
 *
 */
@Entity
@Table(name="T_UFP_PS_PROPERTY")
public class ProjectProperty {
	
	/** 项目ID **/
	@Id
	@GenericGenerator(name="uuidGen",strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	
	/**项目名称**/
	private String name;
	
	/**项目标题**/
	private String title;
	/**项目子标题**/
	private String subTitle;
	/**项目当前版本**/
	private String version;
	/**项目版权信息**/
	private String copyright;
	/**项目LOGO路径**/
	@Column(name="LOGO_URL")
	private String logoUrl;
	/** 项目入口IP **/
	@Column(name="ACCESS_IP")
	private String aaccessIp;
	/** 项目入口DNS **/
	@Column(name="ACCESS_DNS")
	private String aaccessDns;
	/** 项目端口 **/
	private String port;
	/** 项目的context **/
	private String context;
	/** 项目首页路径 **/
	@Column(name="INDEX_PATH")
	private String indexPath;
	/** 项目登录页面路径 **/
	@Column(name="LOGIN_SHOW_PATH")
	private String loginShowPath;
	/** 项目错误页面路径 **/
	@Column(name="ERROR_PATH")
	private String errorPath;
	/** 项目错误页面显示的信息 **/
	@Column(name="ERROR_MSG")
	private String errorMsg;
	/** 项目通知页面路径 **/
	@Column(name="INFO_PATH")
	private String infoPath;
	/** 项目通知页面显示的信息**/
	@Column(name="INFO_MSG")
	private String infoMsg;
	@Column(name="LOGIN_AUTH_PATH")
	private String loginAuthPath;

	public void setLoginAuthPath(String loginAuthPath) {
		this.loginAuthPath = loginAuthPath;
	}

	/** 业务域 **/
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "BUSINESS_DOMAIN_ID")
	private BusinessDomain ppbd;

	
	public String getIndexPath() {
		return indexPath;
	}

	public String getLoginShowPath() {
		return loginShowPath;
	}

	public String getErrorPath() {
		return errorPath;
	}

	public String getLoginAuthPath() {
		return loginAuthPath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BusinessDomain getPpbd() {
		return ppbd;
	}

	public void setPpbd(BusinessDomain ppbd) {
		this.ppbd = ppbd;
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
	public void setName(String id) {
		this.name = id;
	}

	/**
	 * 取得项目子标题
	 * 
	 * @return 项目标题
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 设置项目标题
	 * 
	 * @param title 项目标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 取得项目子标题
	 * 
	 * @return the 项目子标题
	 */
	public String getSubTitle() {
		return subTitle;
	}
	
	/**
	 * 设置项目子标题
	 * 
	 * @param subTitle 项目子标题
	 */
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	
	/**
	 * 取得项目当前版本
	 * 
	 * @return 项目当前版本
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * 设置项目当前版本
	 * @param version 项目当前版本
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * 取得项目版权信息
	 * 
	 * @return 项目版权信息
	 */
	public String getCopyright() {
		return copyright;
	}
	
	
	/**
	 * 设置项目版权信息
	 * 
	 * @param copyright 项目版权信息
	 */
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	
	/**
	 * 取得logoUrl
	 * 
	 * @return logoUrl
	 */
	public String getLogoUrl() {
		return logoUrl;
	}
	
	
	/**
	 * 设置logoUrl
	 * 
	 * @param logoUrl logoUrl
	 */
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	/**
	 * @return the aaccessIp
	 */
	public String getAaccessIp() {
		return aaccessIp;
	}

	/**
	 * @param aaccessIp the aaccessIp to set
	 */
	public void setAaccessIp(String entryIp) {
		this.aaccessIp = entryIp;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}


	/**
	 * @param indexPath the indexPath to set
	 */
	public void setIndexPath(String indexUrl) {
		this.indexPath = indexUrl;
	}

	public void setLoginShowPath(String loginShowPath) {
		this.loginShowPath = loginShowPath;
	}

	/**
	 * @param errorPath the errorPath to set
	 */
	public void setErrorPath(String errorPath) {
		this.errorPath = errorPath;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * @return the infoPath
	 */
	public String getInfoPath() {
		return infoPath;
	}

	/**
	 * @param infoPath the infoPath to set
	 */
	public void setInfoPath(String infoPath) {
		this.infoPath = infoPath;
	}

	/**
	 * @return the infoMsg
	 */
	public String getInfoMsg() {
		return infoMsg;
	}

	/**
	 * @param infoMsg the infoMsg to set
	 */
	public void setInfoMsg(String infoMsg) {
		this.infoMsg = infoMsg;
	}

	/**
	 * @return the aaccessDns
	 */
	public String getAaccessDns() {
		return aaccessDns;
	}

	/**
	 * @param aaccessDns the aaccessDns to set
	 */
	public void setAaccessDns(String aaccessDns) {
		this.aaccessDns = aaccessDns;
	}
	

	/**
	 * @return the indexPath
	 */
	public String getProjectIndexPath(boolean includeContext) {
		
		return groupPath(includeContext,indexPath);
	}

	/**
	 * @return the errorPath
	 */
	public String getProjectErrorPath(boolean includeContext) {
		
		return groupPath(includeContext,errorPath);
	}
	
	/**
	 * @return the loginShowPath
	 */
	public String getProjectLoginShowPath(boolean includeContext) {
		
		return groupPath(includeContext,loginShowPath);
	}
	
	/**
	 * @return the loginAuthPath
	 */
	public String getProjectLoginAuthPath(boolean includeContext) {
		
		return groupPath(includeContext,loginAuthPath);
	}
	
	/**
	 * 组合路径
	 * @param includeContext
	 * @param path
	 * @return
	 */
	public String groupPath(boolean includeContext,String path){
		if(includeContext == false){
			
			return path;
		}
		return ("/"+this.context+path);
	}
}
