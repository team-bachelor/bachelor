package org.bachelor.cache.service;

import java.io.Serializable;

/**
 * 平台级缓存服务
 * 
 * @author 
 *
 */
public interface IIPCacheService {

	/**
	 * 添加或者更新缓存
	 * @param id 缓存对象ID
	 * @param value 缓存对象
	 */
	public void put(String id, Serializable value);

	/**
	 * 取得缓存对象
	 * @param id 缓存对象ID
	 * @return 缓存对象
	 */
	public Object get(String id);
	
	/**
	 * 移除缓存对象
	 * @param id 缓存对象ID
	 */	
	public void remove(String id);
	
	/**
	 * 清除缓存中所有对象
	 */
	public void clear();
	
	
	/**
	 * 停止使用平台缓存
	 */
	public void disable();
	
	/**
	 * 启用平台缓存
	 */
	public void enable();
}
