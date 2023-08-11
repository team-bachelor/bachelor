/*
 * @(#)GlobalEnum.java	May 14, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.gv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 *  
 * 
 * @author Team Bachelor
 *
 */
@Entity
@Table(name="T_bchlr_GV_ENUM")
public class GlobalEnum {
	 
	@Id
	@GenericGenerator(name="uuidGen", strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	 
	@Column(name="ENUM_DESC")
	private String enumDesc;
	 
	@Column(name="FIELD_DESC")
	private String fieldDesc;
	 
	@Column(name="FIELD_VALUE")
	private String fieldValue;
	 
	@Column(name="FIELD_COMMET")
	private String fieldComment;
	
	@Column(name = "ENUM_NAME")
	private String enumName;
	
	private String flag;

	public String getEnumDesc() {
		return enumDesc;
	}

	public void setEnumDesc(String enumDesc) {
		this.enumDesc = enumDesc;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
 
	public String getId() {
		return id;
	}
 
	public void setId(String id) {
		this.id = id;
	}
 
	public String getEnumName() {
		return enumName;
	}

	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
  
	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public String getFieldComment() {
		return fieldComment;
	}
 
	public void setFieldComment(String fieldComment) {
		this.fieldComment = fieldComment;
	}
}
