/*
 * @(#)AuthConstant.java	May 9, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.auth.common;


/**
 * @author Team Bachelor
 *
 */
public class AuthConstant {

	/** 登录用户保存在Session的Key **/
	public static final String LOGIN_USER = "ufp_login_user";
	
	/** 登录用户角色保存在Session的Key **/
	public static final String LOGIN_USER_ROLE = "ufp_login_user_role";
	
	/** 登录客户端信息保存在Session的Key **/
	public static final String LOGIN_USER_CLIENT = "ufp_login_user_client";
	
	/** 登录用户浏览器类型_IE **/
	public static final String CLIENT_IE = "ie";
	
	
	/** 登录用户浏览器类型_firefox **/
	public static final String CLIENT_FIREFOX = "firefox";
	
	/** 登录用户浏览器类型_chrome **/
	public static final String CLIENT_CHROME = "chrome";

	public static final String AUTH_NAME = "iv-user";
	public static final String AUTH_FAILED_VALUE = "Unauthenticated";
	
	public static final int RESULT_SUCCESS = 0;
	public static final int RESULT_NO_USER = 1;
	public static final int RESULT_PWD_WRONG = 2;
	public static final int RESULT_NO_LOGINAUTH = 3;
	
	public static final String[] ANNOTATION_NAME = {"Aspect"};
	
	public static final String[] INTERCEPTOR_CLASS_NAME = {"JspViewChangeServiceImpl"};
	
	/** 默认密码 **/
	public static final String DEFAULT_PWD = "123456";
	
	/** 超级角色ID **/
	public static final String SUPERMANAGE_ID = "8a1c53cc3f5a637f013f5a67e4e0001e";
	
	public static final String FUNCTION_ICON="/img/func.png";
	
	public static final String MODULE_OPENICON="/img/glyphicons-halflings.png";
	
	public static final String MODULE_CLOSEICON="/img/close.png";
	
	public static final String USER_TRACK_FUNC_ENTITY="user_track_func_entity";
	
	/**  **/
	public static final String FUNCTION_ID = "function_id";
	
	/** 二级单位电网风险审核 角色ID **/  
	public static final String SECOND_DEPT_VERIFY = "8a1c5221409a618601409a707e0f001e";
	
	public static final String SECOND_CADRE_POWER = "fafa39f040b8e5ed0140b8ed38ce002e";
	
	public static final String SECOND_CADRE_DEV = "fafa39f040b8e5ed0140b8ed6f8a0031";
	
	public static final String SECOND_DEPT_RANK = "fafa39f040b8e5ed0140b8ecba0e0028";
	
	
	/** 不做登录权限过滤URL集合 **/
	public static final String NO_LOGIN_AUTH_LIST = "noLogAuthList";
}
