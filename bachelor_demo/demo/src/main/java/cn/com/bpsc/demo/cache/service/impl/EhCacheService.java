package org.bachelor.demo.cache.service.impl;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import org.bachelor.demo.cache.service.IEhCacheService;

@Service
public class EhCacheService implements IEhCacheService {
	
	@Autowired
	private CacheInterceptor cacheInterceptor;
	

	
	
	@Override
	public void enable() {
		Field field = ReflectionUtils.findField(cacheInterceptor.getClass(), "initialized");
		
		ReflectionUtils.makeAccessible(field);
		ReflectionUtils.setField(field, cacheInterceptor, true);
		
		
	}

	@Override
	public void disable() {
		Field field = ReflectionUtils.findField(cacheInterceptor.getClass(), "initialized");
		
		ReflectionUtils.makeAccessible(field);
		ReflectionUtils.setField(field, cacheInterceptor, false);
	}

}
