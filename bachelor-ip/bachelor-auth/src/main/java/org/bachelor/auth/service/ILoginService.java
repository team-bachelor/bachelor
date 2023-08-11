/*
 * @(#)ILoginService.java	Apr 24, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.auth.service;

import org.bachelor.auth.domain.LoginResult;

/**
 * 用户认证服务接口
 * 
 * @author Team Bachelor
 *
 */
public interface ILoginService {

	/**
	 * 根据传入的用户名和密码进行用户身份认证
	 * 
	 * @param userId 用户id
	 * @param pwd 用户密码
	 * @param authed 是否已经通过认证
	 * @return 登陆结果信息
	 */
	public LoginResult login(String userId, String pwd, boolean authed);
	
	/**
	 * 根据传入的用户名和密码进行用户身份认证
	 * 
	 * @param userId 用户id
	 * @param pwd 用户密码
	 * @return 登陆结果信息
	 */
	public LoginResult login(String userId, String pwd);
	
	/**
	 * 根据传入的用户名和密码进行用户身份认证
	 * 
	 * @param userId 用户id
	 * @param pwd 用户密码
	 * @param callback 登陆回掉接口
	 * @return 登陆结果信息
	 */
	public LoginResult login(String userId, String pwd, ILoginCallback callback);

	
}
