package cn.org.bachelor.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Redis缓存管理器.
 * @see org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager
 * @author 刘卓
 * @since 2015/8/25
 */
public class RedisCacheManager extends AbstractTransactionSupportingCacheManager {

	private static final Logger logger = LoggerFactory
			.getLogger(RedisCacheManager.class);

	private RedisClientWrapper cacheClient;

	private Set<String> cacheNames;

	/**
	 * 获取缓存对象.
	 * @param name 要获取的缓存名称.
	 * @return 缓存对象
	 */
	@Override
	public Cache getCache(String name)  {
		logger.debug("获取名称为: " + name + " 的RedisCache实例");
		
		Cache c = super.getCache(name);

		if (c == null) {

			// initialize the Redis manager instance
			cacheClient.init(name);
			// create a new cache instance
			c = new SpringRedisCache(cacheClient, name);

			// add it to the cache collection
			this.addCache(c);
		}
		return c;
	}

	/**
	 * 获取缓存封装对象.
	 * @return 缓存封装对象
	 */
	public RedisClientWrapper getCacheClient() {
		return cacheClient;
	}

	/**
	 * 设置缓存封装对象.
	 * @param cacheClient  缓存封装对象
	 */
	public void setCacheClient(RedisClientWrapper cacheClient) {
		this.cacheClient = cacheClient;
	}

	/**
	 * 获取所有缓存实例
	 * @return 缓存实例的集合
	 */
	protected Collection<Cache> loadCaches() {
		Assert.notNull(this.cacheClient, "A backing Redis CacheManager is required");
		Collection<String> names = this.cacheNames;
		LinkedHashSet caches = new LinkedHashSet(names.size());
		for(String name : names) {
			caches.add(this.getCache(name));
		}
		return caches;
	}

	/**
	 * 设置缓存名称的集合
	 * @param cacheNames 缓存名称的集合
	 */
	public void setCacheNames(Set<String> cacheNames) {
		this.cacheNames = cacheNames;
	}
}
