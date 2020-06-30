package org.bachelor.cache.redis;

import org.bachelor.cache.CacheException;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Spring Cache接口的Redis实现。
 * @see org.springframework.cache.Cache
 * @author 刘卓
 * @since 2015/8/25
 */
public class SpringRedisCache implements Cache {

    private final RedisClientWrapper cache;
    private final String name;

    /**
     * 初始化
     * @param cache 缓存实例
     * @param name 缓存名称
     */
    public SpringRedisCache(RedisClientWrapper cache, String name) {
        Assert.notNull(cache, "cache must not be null");
        this.cache = cache;
        this.name = name;
    }

    /**
     * 获取当前缓存对象的名称.
     * @return 缓存名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 获取原生缓存对象.
     * @return 原生缓存对象
     * @see RedisClientWrapper
     */
    public RedisClientWrapper getNativeCache() {
        return this.cache;
    }

    /**
     * 获取缓存中指定key的值.
     * @param key 指定的key
     * @return 缓存中与指定key对应的值,如果key不存在返回null.
     */
    public ValueWrapper get(Object key) {
        byte[] element = this.cache.get(getByteKey(key));
        return element != null ? new SimpleValueWrapper(SerializeUtils.deserialize(element)) : null;
    }

    /**
     * 获取缓存中指定key的值,并转换为指定类型.
     * 如果参数type为null或返回类型与type不匹配,则抛出异常.
     * @see IllegalStateException
     * @param key 指定的key.
     * @param type 指定的类型.
     * @return 缓存中指定key对应的值.
     */
    public <T> T get(Object key, Class<T> type) {
        byte[] data = this.cache.get(getByteKey(key));
        if(data == null) {
            return null;
        }

        Object element =  SerializeUtils.deserialize(data);
        if(type != null && !type.isInstance(element)) {
            throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + element);
        }
        else {
            return (T)element;
        }
    }

    public <T> T get(Object key, Callable<T> callable) {
        return null;
    }

    /**
     * 将value以指定key存入缓存.
     * @param key 指定的key.
     * @param value 要保存如缓存中的值.
     */
    public void put(Object key, Object value) {
        this.cache.set(getByteKey(key), SerializeUtils.serialize(value));
    }

    /**
     * 检查缓存中是否存在指定key,如果存在则返回缓存中的值,如果不存在则将参数value推入缓存.
     * @param key 指定的key
     * @param value 如果key不存在时要保存的value
     * @return key对应的缓存值
     */
    public ValueWrapper putIfAbsent(Object key, Object value) {
        ValueWrapper wrapper = this.get(key);
        if (wrapper == null) {
            this.put(key, value);
            wrapper = new SimpleValueWrapper(value);
        }
        return wrapper;
    }

    /**
     * 清除缓存中指定key的内容
     * @param key 要清除的key.
     */
    public void evict(Object key) {
        this.cache.del(getByteKey(key));
    }

    /**
     * 清除缓存的所有内容
     */
    public void clear() {
        try {
            Set<byte[]> keys = cache.keys(this.getName() + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                for (byte[] key : keys) {
                    cache.del(key);
                }
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    /**
     * 获得byte[]型的key
     * @param key 对象类型的key
     * @return 转换为byte[]后的key
     */
    private byte[] getByteKey(Object key){
        if(key instanceof String){
            String realKey = null;
            if(! StringUtils.isEmpty(this.getName())) {
                realKey = this.getName() + "_" + (String)key;
            } else {
                realKey = (String)key;
            }
            return realKey.getBytes();
        }else{
            return SerializeUtils.serialize(key);
        }
    }
}
