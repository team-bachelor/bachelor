/*
 * @(#)LoginServiceImpl.java	Apr 24, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.auth.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import org.bachelor.auth.common.AuthConstant;
import org.bachelor.auth.domain.LoginResult;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.event.LoginFailedEvent;
import org.bachelor.auth.event.LoginSuccessEvent;
import org.bachelor.auth.service.IAuthRoleUserService;
import org.bachelor.auth.service.ILoginCallback;
import org.bachelor.auth.service.ILoginService;
import org.bachelor.auth.service.IRoleService;
import org.bachelor.context.service.IVLService;
import org.bachelor.org.dao.IUserDao;
import org.bachelor.org.domain.User;
import org.bachelor.org.service.IOrgService;

/**
 * @author Team Bachelor
 *
 */
@Service
public class LoginServiceImpl implements ILoginService,ApplicationEventPublisherAware{
	private Log log = LogFactory.getLog(this.getClass());
	@Autowired
	private ApplicationEventPublisher publisher;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IVLService vlService;

	@Autowired
	private IOrgService orgService;
	
	@Autowired
	private IAuthRoleUserService authRoleUserService;
	
	@Autowired
	private IRoleService roleService;
	
	/* (non-Javadoc)
	 * @see org.bachelor.auth.service.ILoginService#auth(java.lang.String, java.lang.String)
	 */
	@Override
	public LoginResult login(String userId, String pwd, boolean authed) {
		return login(userId,pwd,authed,null);
	}
	
	protected LoginResult doLogin(String userId, String pwd, boolean authed){
		LoginResult result = new LoginResult();
		if(authed){
			User user = userDao.findById(userId);
			if(user != null){
				//没有登录权限
				if(user.getLoginFlag().equals("2")){
					result.setResult(AuthConstant.RESULT_NO_LOGINAUTH);
					result.setMsg("没有登录权限。");
				} else {
					result.setUser(user);
					result.setMsg("登录成功。");
					result.setLogin(true);
					result.setResult(AuthConstant.RESULT_SUCCESS);	
				}
			}else{
				result.setResult(AuthConstant.RESULT_NO_USER);
				result.setMsg("没有此用户。");
			}
		}else{
			User user = userDao.findById(userId);
			if(user == null){
				result.setResult(AuthConstant.RESULT_NO_USER);
				result.setMsg("没有此用户。");
			}else{
				if(!user.getPwd().equals(pwd)){
					result.setResult(AuthConstant.RESULT_PWD_WRONG);
					result.setMsg("密码错误。");
				}else{
					//没有登录权限
					if(user.getLoginFlag().equals("2")){
						result.setResult(AuthConstant.RESULT_NO_LOGINAUTH);
						result.setMsg("没有登录权限。");
					} else {
						result.setResult(AuthConstant.RESULT_SUCCESS);
						result.setMsg("登录成功。");
						result.setLogin(true);
						result.setUser(user);
					}
				}
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationEventPublisherAware#setApplicationEventPublisher(org.springframework.context.ApplicationEventPublisher)
	 */
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	@Override
	public LoginResult login(String userId, String pwd) {
		return login(userId,pwd,false,null);
	}

	@Override
	public LoginResult login(String userId, String pwd, ILoginCallback callback) {
		return login(userId,pwd,false,callback);
	}
	
	private LoginResult login(String userId, String pwd, boolean authed, ILoginCallback callback) {
		//进行用户认证
		log.info("进行用户认证。用户Id：" + userId + ", 是否已经认证通过：" + authed);
		if(userId!=null && !"".equals(userId)){
				LoginResult result = doLogin(userId, pwd, authed);
				//登录成功 
				if(result!=null && result.isLogin()){
						//加载组织机构
						result.getUser().setOwnerOrg(orgService.findById(result.getUser().getOwnerOrgId()));
						if(userId.equals("guest")){
							//特殊处理：访客用户，未登录角色
							List<Role> roles = authRoleUserService.findRolesByUserId(userId);
							result.getRoles().addAll(roles);
						}else{
							//加载角色集合
							List<Role> roles = authRoleUserService.findRolesByUserId(result.getUser().getId());
							if(roles != null){
								result.getRoles().addAll(roles);
							}
							
							//登录用户默认添加添加角色：已登录，已授权
							//给登录用户添加角色：已登录
							Role role = roleService.findRoleEtyByName("logined");
							if(role != null && !result.getRoles().contains(role)){
								result.getRoles().add(role);
							}
							if(result.getRoles().size() > 1){
								//包含一个以上角色说明此用户是授权用户,给登录用户添加角色：已授权
								role = roleService.findRoleEtyByName("authed");
								if(role != null && !result.getRoles().contains(role)){
									result.getRoles().add(role);
								}
							}
						}

						
						if(result.getResult() == AuthConstant.RESULT_SUCCESS){
							boolean callbackResult = true;
							if(callback != null){
								try{
									callbackResult = callback.onSuccess(result);
								}catch(Exception e){
									callbackResult = false;
								}
							}
							if(!callbackResult){
								result.setLogin(false);
								log.info("认证失败。发布认证失败事件。");
								LoginFailedEvent loginEvent = new LoginFailedEvent(result);
								this.publisher.publishEvent(loginEvent);
								return result;
							}
							log.info("认证成功");
							log.debug("将用户信息存放到session中"); 
							
							vlService.setSessionAttribute(AuthConstant.LOGIN_USER, result.getUser());
							vlService.setSessionAttribute(AuthConstant.LOGIN_USER_ROLE, result.getRoles());
							
							log.info("发布认证成功事件。");
							LoginSuccessEvent loginEvent = new LoginSuccessEvent(result);
							this.publisher.publishEvent(loginEvent);			
						}else{
							log.info("认证失败。发布认证失败事件。");
							LoginFailedEvent loginEvent = new LoginFailedEvent(result);
							this.publisher.publishEvent(loginEvent);						
						} 
						return result;
				} else {
					log.info("登录失败");
					log.info("重新登录时，Session中User实体对象已过期。");
					return result;
				}
		} else {
			log.info("登录失败，用户ID为空或者null。");
			LoginResult result = new LoginResult();
			result.setResult(AuthConstant.RESULT_NO_USER);
			result.setMsg("登录失败，用户ID为空或者null。");
			return result;
		}
	}


}
