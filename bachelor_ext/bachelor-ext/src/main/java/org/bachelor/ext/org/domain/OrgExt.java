/*
 * @(#)UserExt.java	May 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ext.org.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author user
 *
 */
@Entity
@Table(name="T_UFP_EXT_ORG")
public class OrgExt implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3552457555586585695L;
	
	@Id 
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
 
}
