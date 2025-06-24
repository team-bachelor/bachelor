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
 *
 * @author 刘卓
 * @see org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager
 * @since 2015/8/25
 */
public class RedisCacheManager extends AbstractTransactionSupportingCacheManager {

    // 定义日志记录器，用于记录该类的相关日志信息
    private static final Logger logger = LoggerFactory
            .getLogger(RedisCacheManager.class);

    // 缓存客户端包装器，用于与Redis进行交互
    private RedisClientWrapper cacheClient;

    // 缓存名称的集合，用于存储需要管理的缓存名称
    private Set<String> cacheNames;

    /**
     * 获取缓存对象.
     *
     * @param name 要获取的缓存名称.
     * @return 缓存对象
     */
    @Override
    public Cache getCache(String name) {
        // 记录调试日志，表明正在获取指定名称的RedisCache实例
        logger.debug("获取名称为: " + name + " 的RedisCache实例");
        // 调用父类的getCache方法获取缓存对象
        return super.getCache(name);
    }

    /**
     * 当缓存不存在时，初始化并创建一个新的缓存实例.
     *
     * @param name 缓存名称
     * @return 新创建的缓存实例
     */
    @Override
    protected Cache getMissingCache(String name) {
        // 初始化Redis管理器实例，为指定名称的缓存进行初始化操作
        cacheClient.init(name);
        // 创建一个新的SpringRedisCache实例，并返回
        return new SpringRedisCache(cacheClient, name);
    }

    /**
     * 获取缓存封装对象.
     *
     * @return 缓存封装对象
     */
    public RedisClientWrapper getCacheClient() {
        // 返回缓存客户端包装器实例
        return cacheClient;
    }

    /**
     * 设置缓存封装对象.
     *
     * @param cacheClient 缓存封装对象
     */
    public void setCacheClient(RedisClientWrapper cacheClient) {
        // 将传入的缓存客户端包装器实例赋值给当前类的成员变量
        this.cacheClient = cacheClient;
    }

    /**
     * 获取所有缓存实例
     *
     * @return 缓存实例的集合
     */
    protected Collection<Cache> loadCaches() {
        // 断言缓存客户端包装器不为空，若为空则抛出异常
        Assert.notNull(this.cacheClient, "A backing Redis CacheManager is required");
        // 获取缓存名称的集合
        Collection<String> names = this.cacheNames;
        // 创建一个LinkedHashSet集合，用于存储缓存实例，初始容量为缓存名称集合的大小
        LinkedHashSet<Cache> caches = new LinkedHashSet<>(names.size());
        // 遍历缓存名称集合
        for (String name : names) {
            // 将获取到的缓存实例添加到caches集合中
            caches.add(this.getCache(name));
        }
        // 返回缓存实例的集合
        return caches;
    }

    /**
     * 设置缓存名称的集合
     *
     * @param cacheNames 缓存名称的集合
     */
    public void setCacheNames(Set<String> cacheNames) {
        // 将传入的缓存名称集合赋值给当前类的成员变量
        this.cacheNames = cacheNames;
    }
}