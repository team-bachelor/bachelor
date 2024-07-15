/*
 * @(#)OpermaskPageUtil.java	Jun 4, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package cn.org.bachelor.dao.dao.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.org.bachelor.dao.dao.DaoConstant;
import cn.org.bachelor.dao.dao.vo.PageVo;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import cn.org.bachelor.context.context.service.IVLService;

/**
 * 
 * 此类所在的包名和工程不对，请使用org.bachelor.facade.util.OperamaskUtil类。
 * 此类不再维护
 * 
 * @author user
 * @see org.bachelor.facade.util.OperamaskUtil
 */
@Service
@Deprecated
public class OpermaskPageUtil implements ApplicationContextAware{
	private static IVLService vlService;
	
	public static Map page(List list){
		PageVo pageVo = (PageVo)vlService.getRequestAttribute(DaoConstant.PAGE_INFO);
		if(pageVo == null){
			pageVo = new PageVo();
			pageVo.setCount(list.size());
		}
		Map<String,Object> map = new HashMap<String, Object>();
	    map.put("total", pageVo.getCount());
		map.put("rows", (list!=null?list:new ArrayList()));
		
		return map;
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		vlService = ctx.getBean(IVLService.class);
		
	}
}
