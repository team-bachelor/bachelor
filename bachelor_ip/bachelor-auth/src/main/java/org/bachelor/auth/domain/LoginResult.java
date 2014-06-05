/*
 * @(#)LoginResult.java	Apr 24, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.auth.domain;

import java.util.ArrayList;
import java.util.List;

import org.bachelor.org.domain.User;

/**
 *
 * 
 * @author
 *
 */
public class LoginResult {

	
	private int result;
	private String msg;
	private User user;
	private boolean login = false;
	private List<Role> roles = new ArrayList<Role>();
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	/**
	 * @return the result
	 */
	public int getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(int result) {
		this.result = result;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the login
	 */
	public boolean isLogin() {
		return login;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(boolean login) {
		this.login = login;
	}
	
	
}
