/*
 * @(#)LoginSuccessEvent.java	May 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.auth.event;

import org.springframework.context.ApplicationEvent;

import org.bachelor.auth.domain.LoginResult;

/**
 * @author Team Bachelor
 *
 */
public class LoginFailedEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5062717669721900150L;
	
	/**
	 * @param source
	 */
	public LoginFailedEvent(LoginResult source) {
		super(source);
	}

}
