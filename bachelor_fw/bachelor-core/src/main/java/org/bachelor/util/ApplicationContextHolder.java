package org.bachelor.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class ApplicationContextHolder implements ApplicationContextAware{
	private static ApplicationContext applicationContext;
	public ApplicationContextHolder(){}
	@Override
	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		applicationContext = ac;
	}
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	
	public static Object getBean(String name){
		return applicationContext.getBean(name);
	}
	
	public static <T>T getBean(String name, Class<T> clazz){
		return applicationContext.getBean(name, clazz);
	}

}
