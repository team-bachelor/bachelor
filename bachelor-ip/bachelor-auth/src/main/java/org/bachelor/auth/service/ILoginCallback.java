package org.bachelor.auth.service;

import org.bachelor.auth.domain.LoginResult;

/**
 * 用户登陆认证回调接口
 * 
 * @author user
 *
 */
public interface ILoginCallback {

	/**
	 * 认证成功后回调接口.
	 * 
	 * 
	 * @param loginResult 认证结果信息
	 * @return 认证通过返回：true，认证失败返回：false。
	 */
	public boolean onSuccess(LoginResult loginResult);
	
}
