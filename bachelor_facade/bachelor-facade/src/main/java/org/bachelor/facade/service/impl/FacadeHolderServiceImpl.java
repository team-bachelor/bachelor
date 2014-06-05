package org.bachelor.facade.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import org.bachelor.facade.service.IBpmContextService;
import org.bachelor.facade.service.IContextService;


@Service
public class FacadeHolderServiceImpl implements ApplicationContextAware{
	
	private static ApplicationContext appCtx = null;

	public static ApplicationContext getApplicationContext(){
		return appCtx;
	}
	
//	public static IBpmContextService getBpmContextService(){
//		return appCtx.getBean(IBpmContextService.class);
//	}
	
	public static IContextService getContextService(){
		return appCtx.getBean(IContextService.class);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		appCtx = applicationContext;
		
	}
}
