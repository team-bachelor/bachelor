/*
 * @(#)GlobalEnumServiceImpl.java	May 14, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.gv.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.bachelor.gv.dao.IGlobalEnumDao;
import org.bachelor.gv.domain.GlobalEnum;
import org.bachelor.gv.service.IGlobalEnumService;
import org.bachelor.gv.vo.GlobalEnumTvVo;

/**
 * 全局变量服务实现类
 * 
 * @author Team Bachelor
 *
 */
@Service
@RequestMapping("/gv/enum/")
public class GlobalEnumServiceImpl implements IGlobalEnumService {

	@Autowired
	private IGlobalEnumDao globalEnumdao;
	
	/**
	 * 新建全局枚举实体
	 * 
	 * @param globalEnum 全局枚举实体
	 */
	@Override
	public void save(GlobalEnum globalEnum) {
		globalEnumdao.save(globalEnum);
	}

	/**
	 * 更新全局枚举实体
	 * 
	 * @param globalEnum 全局枚举实体
	 */
	@Override
	public void update(GlobalEnum globalEnum) {
		globalEnumdao.update(globalEnum);
	}
	
	/**
	 * 新建或者更新全局枚举实体
	 * id为null或者""时，新建实体。否则更新实体。
	 * @param globalEnum 全局枚举实体
	 */
	public void saveOrUpdate(GlobalEnum globalEnum){
		if(StringUtils.isEmpty(globalEnum.getId())){
			globalEnumdao.save(globalEnum);
		}else{
			globalEnumdao.update(globalEnum);
		}
	}

	/**
	 * 删除全局枚举实体
	 * @param globalEnum 全局枚举实体
	 */
	@Override
	public void delete(GlobalEnum globalEnum) {
		globalEnumdao.delete(globalEnum);
	}

	/**
	 * 删除全局枚举实体
	 * @param id 主键
	 */
	@Override
	public void deleteById(String id) {
		GlobalEnum globalEnum = globalEnumdao.findById(id);
		if(globalEnum != null){
			globalEnumdao.delete(globalEnum);
		}
	}

	/**
	 * 根据主键查找全局枚举实体
	 * @param id 主键
	 * @return 全局枚举实体
	 */
	@Override
	@RequestMapping("findById")
	@ResponseBody
	public GlobalEnum findById(String id) {
		return globalEnumdao.findById(id);
	}

	/**
	 * 查找所有全局枚举实体
	 * @return 所有全局枚举实体
	 */
	@Override
	@RequestMapping("all")
	@ResponseBody
	public List<GlobalEnum> findAll() {
		return globalEnumdao.findAll();
	}
	
	/**
	 * 根据枚举名称查找枚举实体
	 * 
	 * @param enumName 字段名称
	 * @return 枚举实体
	 */
	@Override
	@RequestMapping("findByEnumName")
	@ResponseBody
	public List<GlobalEnum> findByEnumName(String enumName){
		
		return globalEnumdao.findByEnumName(enumName);
	}

	/**
	 * 根据枚举ID查找枚举实体
	 * 
	 * @param enumName 字段名称
	 * @return 枚举实体
	 */
	@RequestMapping("findByEnumNameTV")
	@ResponseBody
	public  List<GlobalEnumTvVo> findByEnumNameTV(String enumName){
		
		return globalEnumdao.findByEnumNameTV(enumName);
	}
	
	/**
	 * 取得全局枚举Dao
	 * @return 全局枚举Dao
	 */
	public IGlobalEnumDao getGlobalEnumdao() {
		return globalEnumdao;
	}

	/**
	 * 设置全局枚举Dao
	 * @param globalEnumdao 全局枚举Dao
	 */
	public void setGlobalEnumdao(IGlobalEnumDao globalEnumdao) {
		this.globalEnumdao = globalEnumdao;
	}

	@Override
	public List<GlobalEnum> findByExample(GlobalEnum ge) {
		
		return globalEnumdao.findAllEnumInfo(ge);
	}

	@Override
	public void batchDelete(String info) {
		String dInfo[] = info.split(",");
		String dSQL[] = new String[dInfo.length];
		int index = 0;
		for(String tempInfo:dInfo){
			dSQL[index] = "delete from T_UFP_GV_ENUM where id='"+tempInfo+"'";
			index++;
		}
		globalEnumdao.batchDelete(dSQL);
	}

	
}
