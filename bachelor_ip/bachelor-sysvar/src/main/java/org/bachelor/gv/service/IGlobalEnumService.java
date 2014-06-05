/*
 * @(#)IGlobalEnumService.java	May 14, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.gv.service;

import java.util.List;

import org.bachelor.gv.domain.GlobalEnum;

/**
 * 全局变量服务接口
 * 
 * @author Team Bachelor
 *
 */
public interface IGlobalEnumService {

	/**
	 * 新建全局枚举实体
	 * @param globalEnum 全局枚举实体
	 */
	public void save(GlobalEnum globalEnum);
	
	/**
	 * 更新全局枚举实体
	 * @param globalEnum 全局枚举实体
	 */
	public void update(GlobalEnum globalEnum);
	
	/**
	 * 新建或者更新全局枚举实体
	 * id为null或者""时，新建实体。否则更新实体。
	 * @param globalEnum 全局枚举实体
	 */
	public void saveOrUpdate(GlobalEnum globalEnum);
	
	/**
	 * 删除全局枚举实体
	 * @param globalEnum 全局枚举实体
	 */
	public void delete(GlobalEnum globalEnum);
	
	/**
	 * 删除全局枚举实体
	 * @param id 主键
	 */
	public void deleteById(String id);
	
	/**
	 * 根据主键查找全局枚举实体
	 * @param id 主键
	 * @return 全局枚举实体
	 */
	public GlobalEnum findById(String id);
	
	/**
	 * 根据枚举名称查找枚举实体
	 * @param enumName 枚举名称
	 * @return 枚举实体
	 */
	public List<GlobalEnum> findByEnumName(String enumName);
	
	/**
	 * 查找所有全局枚举实体
	 * @return 所有全局枚举实体
	 */
	public List<GlobalEnum> findAll();
	
	public List<GlobalEnum> findByExample(GlobalEnum ge);
	
	public void batchDelete(String info);
	
}
