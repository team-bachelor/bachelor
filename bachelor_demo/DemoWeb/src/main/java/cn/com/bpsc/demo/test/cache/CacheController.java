package org.bachelor.demo.test.cache;

import net.sf.ehcache.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.bachelor.demo.cache.service.IEhCacheService;
import org.bachelor.org.domain.User;
import org.bachelor.org.service.IUserService;


@Controller
@RequestMapping("cache")
public class CacheController {

	@Autowired
	private IUserService userService;
	
	
	@Autowired
	private IEhCacheService ehCacheService;
	
	@RequestMapping("user")
	@ResponseBody
	@Cacheable(value="appCache")
	public User user(String id){
		
		long startTime = System.currentTimeMillis();
		
		User user = userService.findById(id);
		
		long endTime = System.currentTimeMillis();
		long cost = endTime - startTime;
		System.out.println("查找用户耗时：" + cost);
		
		return user;
	}
	
	@RequestMapping("enable")
	@ResponseBody
	public String enable(){
		ehCacheService.enable();
		
		return "enabled";
	}
	
	@RequestMapping("disable")
	@ResponseBody
	public String disable(){
		ehCacheService.disable();
		return "disabled";
	}
	
}
