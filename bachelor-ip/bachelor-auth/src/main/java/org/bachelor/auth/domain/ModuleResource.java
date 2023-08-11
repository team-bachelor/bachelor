/*
 * @(#)ModuleResource.java	Mar 19, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.auth.domain;

/**
 * @author
 *
 */
public class ModuleResource extends AbsractResource {

	private String accessUrl;
	
	public ModuleResource(){
		type = "module";
	}
}
