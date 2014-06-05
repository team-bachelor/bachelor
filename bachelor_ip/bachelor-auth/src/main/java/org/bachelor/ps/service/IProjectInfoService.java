/*
 * @(#)IProjectInfoService.java	Mar 11, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.service;

import org.bachelor.ps.domain.ProjectInfo;

/**
 * @author Team Bachelor
 *
 */
public interface IProjectInfoService {

	/**
	 * 查找项目的属性信息和模块结构
	 * 
	 * @return 项目的属性信息和模块结构
	 */
	public ProjectInfo findPI();
}
