package org.bachelor.facade.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import org.bachelor.context.service.IVLService;
import org.bachelor.dao.DaoConstant;
import org.bachelor.dao.vo.PageVo;

/**
 * OperamaskUI相关工具类
 * 
 * @author user
 *
 */
public class OperamaskUtil implements ApplicationContextAware{
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
	
	public static Map page(List list, String count){
		Map<String,Object> map = new HashMap<String, Object>();
	    map.put("total", count);
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
