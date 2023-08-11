/*
 * @(#)ProjectInfo.java	Mar 11, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.domain;

import java.util.List;

/**
 * @author Team Bachelor
 *
 */
public class ProjectInfo {

	private ProjectProperty pp;
	private List<ProjectModule> pmList;
	/**
	 * @return the pp
	 */
	public ProjectProperty getPp() {
		return pp;
	}
	/**
	 * @param pp the pp to set
	 */
	public void setPp(ProjectProperty pp) {
		this.pp = pp;
	}
	/**
	 * @return the pmList
	 */
	public List<ProjectModule> getPmList() {
		return pmList;
	}
	/**
	 * @param pmList the pmList to set
	 */
	public void setPmList(List<ProjectModule> mmList) {
		this.pmList = mmList;
	}
	
	
}
