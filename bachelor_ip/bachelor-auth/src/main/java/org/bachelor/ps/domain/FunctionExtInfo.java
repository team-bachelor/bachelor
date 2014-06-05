/*
 * @(#)FunctionExtInfo.java	Mar 22, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.domain;

import java.util.List;

/**
 * 
 * 功能扩展信息 
 * @author 
 *
 */
public class FunctionExtInfo {

	private String javaPackage;
	
	private List<String> dbTables;
	
	private List<String> dbViews;
	
	private List<String> dbFuncs;
	
	private List<String> dbProcedures;
	
	private String jspFolder;
	
	private String flexFolder;

	/**
	 * @return the javaPackage
	 */
	public String getJavaPackage() {
		return javaPackage;
	}

	/**
	 * @param javaPackage the javaPackage to set
	 */
	public void setJavaPackage(String javaPackage) {
		this.javaPackage = javaPackage;
	}

	/**
	 * @return the dbTables
	 */
	public List<String> getDbTables() {
		return dbTables;
	}

	/**
	 * @param dbTables the dbTables to set
	 */
	public void setDbTables(List<String> dbTables) {
		this.dbTables = dbTables;
	}

	/**
	 * @return the dbViews
	 */
	public List<String> getDbViews() {
		return dbViews;
	}

	/**
	 * @param dbViews the dbViews to set
	 */
	public void setDbViews(List<String> dbViews) {
		this.dbViews = dbViews;
	}

	/**
	 * @return the dbFuncs
	 */
	public List<String> getDbFuncs() {
		return dbFuncs;
	}

	/**
	 * @param dbFuncs the dbFuncs to set
	 */
	public void setDbFuncs(List<String> dbFuncs) {
		this.dbFuncs = dbFuncs;
	}

	/**
	 * @return the dbProcedures
	 */
	public List<String> getDbProcedures() {
		return dbProcedures;
	}

	/**
	 * @param dbProcedures the dbProcedures to set
	 */
	public void setDbProcedures(List<String> dbProcedures) {
		this.dbProcedures = dbProcedures;
	}

	/**
	 * @return the jspFolder
	 */
	public String getJspFolder() {
		return jspFolder;
	}

	/**
	 * @param jspFolder the jspFolder to set
	 */
	public void setJspFolder(String jspFolder) {
		this.jspFolder = jspFolder;
	}

	/**
	 * @return the flexFolder
	 */
	public String getFlexFolder() {
		return flexFolder;
	}

	/**
	 * @param flexFolder the flexFolder to set
	 */
	public void setFlexFolder(String flexFolder) {
		this.flexFolder = flexFolder;
	}
	
	
}
