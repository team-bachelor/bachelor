package cn.org.bachelor.cache.redis;

import cn.org.bachelor.cache.NotSupportInClusterException;
import org.springframework.beans.InvalidPropertyException;
import redis.clients.jedis.*;

import java.util.*;

/**
 * 缓存客户端的封装类
 *
 * @author 刘卓
 * @since 2015/8/25
 */
public class RedisClientWrapper {
    // Redis服务的主机名，默认值为 127.0.0.1
    private String host = "127.0.0.1";
    // Redis服务的端口，默认值为 6379
    private int port = 6379;
    // 缓存过期时间，0 表示永不过期
    private int expire = 0;
    // Jedis连接Redis服务器的超时时间，单位为毫秒，非过期时间
    private int timeout = 0;
    // Redis的连接密码，默认为空字符串
    private String password = "";
    // 静态的Jedis连接池映射，键为缓存名称，值为Jedis连接池实例
    private static final HashMap<String, JedisPool> jedisPoolMap = new HashMap<>(0);
    // 静态的Jedis集群映射，键为缓存名称，值为Jedis集群实例
    private static final HashMap<String, JedisCluster> jedisClusterMap = new HashMap<>(0);
    // 缓存名称，默认为空字符串
    private String cacheName = "";
    // 存储Redis主机信息的集合
    private Set<String> hosts;
    // 标记是否为集群模式
    private boolean isCluster = false;

    /**
     * 无参构造函数
     */
    public RedisClientWrapper() {

    }

    /**
     * 初始化方法
     *
     * @param name 缓存名称
     */
    public void init(String name) {
        cacheName = name;
        // 存储主机和端口信息的集合
        Set<HostAndPort> haps = null;
        if (hosts != null) {
            // 初始化集合，容量为 hosts 的大小
            haps = new HashSet<>(hosts.size());
            for (String hostString : hosts) {
                // 解析主机和端口信息
                HostAndPort hap = parseHostAndPort(hostString);
                haps.add(hap);
            }
            // Jedis集群实例
            JedisCluster jc;
            if (timeout == 0) {
                jc = new JedisCluster(haps);
            } else {
                jc = new JedisCluster(haps, timeout);
            }
            isCluster = true;
            // 将集群实例添加到映射中
            jedisClusterMap.put(name, jc);
        }
        // 如果 haps 为空或没有元素，则添加到 Jedis 连接池
        if (haps == null || haps.isEmpty()) {
            addToJedisPool(cacheName, host, port);
        }
    }

    /**
     * 将指定的主机和端口信息添加到 Jedis 连接池
     *
     * @param name 缓存名称
     * @param _host 主机名
     * @param _port 端口号
     */
    private void addToJedisPool(String name, String _host, int _port) {
        // 从映射中获取 Jedis 连接池实例
        JedisPool jedisPool = jedisPoolMap.get(name);
        if (jedisPool == null) {
            if (password != null && !password.isEmpty()) {
                jedisPool = new JedisPool(new JedisPoolConfig(), _host, _port, timeout, password);
            } else if (timeout != 0) {
                jedisPool = new JedisPool(new JedisPoolConfig(), _host, _port, timeout);
            } else {
                jedisPool = new JedisPool(new JedisPoolConfig(), _host, _port);
            }
            // 将连接池实例添加到映射中
            jedisPoolMap.put(name, jedisPool);
        }
    }

    /**
     * 解析主机和端口信息
     *
     * @param hostString 主机和端口信息的字符串，格式为 "host:port"
     * @return 解析后的 HostAndPort 实例
     * @throws InvalidPropertyException 如果主机信息格式不正确
     */
    private HostAndPort parseHostAndPort(String hostString) {
        StringTokenizer st = new StringTokenizer(hostString, ":");
        if (st.hasMoreElements() && st.countTokens() == 2) {
            return new HostAndPort(st.nextToken(), Integer.parseInt(st.nextToken()));
        } else {
            throw new InvalidPropertyException(this.getClass(), "hosts", "invalid host name:" + hostString + ".");
        }
    }

    /**
     * 根据指定key从Redis中获取值
     *
     * @param key 要获得值的key
     * @return 根据key获取的值
     */
    public Map<String, String> hgetAll(String key) {
        if (isCluster) {
            return hgetAllCluster(key);
        } else {
            return hgetAllSingle(key);
        }
    }

    /**
     * 从集群模式下的 Redis 中获取哈希数据
     *
     * @param key 要获取的哈希数据的键
     * @return 哈希数据的键值对映射
     */
    private Map<String, String> hgetAllCluster(String key) {
        // 获取 Jedis 集群实例
        JedisCluster jc = getJedisCluster();
        return jc.hgetAll(key);
    }

    /**
     * 从单节点模式下的 Redis 中获取哈希数据
     *
     * @param key 要获取的哈希数据的键
     * @return 哈希数据的键值对映射
     */
    private Map<String, String> hgetAllSingle(String key) {
        // 存储获取到的哈希数据
        Map<String, String> value;
        // 获取 Jedis 连接池实例
        JedisPool jedisPool = getJedisPool();
        try (Jedis jedis = jedisPool.getResource()) {
            value = jedis.hgetAll(key);
        }
        return value;
    }

    /**
     * 根据指定key从Redis中获取值
     *
     * @param key 要获得值的key
     * @return 根据key获取的值
     */
    public byte[] get(byte[] key) {
        if (isCluster) {
            return getCluster(key);
        } else {
            return getSingle(key);
        }
    }

    /**
     * 从集群模式下的 Redis 中获取二进制数据
     *
     * @param key 要获取的二进制数据的键
     * @return 二进制数据
     */
    private byte[] getCluster(byte[] key) {
        // 获取 Jedis 集群实例
        JedisCluster jc = getJedisCluster();
        return jc.get(key);
    }

    /**
     * 从单节点模式下的 Redis 中获取二进制数据
     *
     * @param key 要获取的二进制数据的键
     * @return 二进制数据
     */
    private byte[] getSingle(byte[] key) {
        // 存储获取到的二进制数据
        byte[] value;
        // 获取 Jedis 连接池实例
        JedisPool jedisPool = getJedisPool();
        try (Jedis jedis = jedisPool.getResource()) {
            value = jedis.get(key);
        }
        return value;
    }

    /**
     * 将指定value以指定key存入Redis
     *
     * @param key   要设置的key
     * @param value 要设置的value
     * @return 存入Redis的值
     */
    public byte[] set(byte[] key, byte[] value) {
        if (isCluster) {
            return setCluster(key, value, expire);
        } else {
            return setSingle(key, value, expire);
        }
    }

    /**
     * 将指定value以指定key存入Redis，并设置过期时间
     *
     * @param key    要设置的key
     * @param value  要设置的value
     * @param expire 过期时间
     * @return 存入Redis的值
     */
    public byte[] set(byte[] key, byte[] value, int expire) {
        if (isCluster) {
            return setCluster(key, value, expire);
        } else {
            return setSingle(key, value, expire);
        }
    }

    /**
     * 在集群模式下将二进制数据存入 Redis，并设置过期时间
     *
     * @param key    要存储的二进制数据的键
     * @param value  要存储的二进制数据
     * @param expire 过期时间
     * @return 存储的二进制数据
     */
    private byte[] setCluster(byte[] key, byte[] value, int expire) {
        JedisCluster jc = getJedisCluster();
        jc.set(key, value);
        if (expire != 0) {
            jc.expire(key, expire);
        }
        return value;
    }

    /**
     * 在单节点模式下将二进制数据存入 Redis，并设置过期时间
     *
     * @param key    要存储的二进制数据的键
     * @param value  要存储的二进制数据
     * @param expire 过期时间
     * @return 存储的二进制数据
     */
    private byte[] setSingle(byte[] key, byte[] value, int expire) {
        JedisPool jedisPool = getJedisPool();
		Jedis jedis = jedisPool.getResource();
        try {
            jedis.set(key, value);
            if (expire != 0) {
				jedis.expire(key, expire);
		 	}
        } finally {
            jedis.close();
		}
		return value;
	}
	
	/**
	 * 根据指定key删除Redis中的值
     *
	 * @param key 要删除的key
	 */
    public void del(byte[] key) {
        if (isCluster) {
            delCluster(key);
        } else {
            delSingle(key);
        }
    }

    /**
     * 在集群模式下根据指定key删除Redis中的值
     *
     * @param key 要删除的键
     */
    private void delCluster(byte[] key) {
        JedisCluster jc = getJedisCluster();
        jc.del(key);
    }

    /**
     * 在单节点模式下根据指定key删除Redis中的值
     *
     * @param key 要删除的键
     */
    private void delSingle(byte[] key) {
        // 获取当前缓存名称对应的 Jedis 连接池
        JedisPool jedisPool = getJedisPool();
        // 从连接池中获取一个 Jedis 连接资源
        Jedis jedis = jedisPool.getResource();
        try {
			jedis.del(key);
        } finally {
            // 无论是否发生异常，最后都要关闭 Jedis 连接，将连接归还给连接池
            jedis.close();
		}
	}
	
	/**
	 * 刷新
	 */
    public void flushDB() {
        if (isCluster) {
            flushDBCluster();
        } else {
            flushDBSingle();
        }

    }

    private void flushDBCluster() {
        throw new NotSupportInClusterException("flushdb");
    }

    private void flushDBSingle() {
        JedisPool jedisPool = getJedisPool();
		Jedis jedis = jedisPool.getResource();
        try {
			jedis.flushDB();
        } finally {
            jedis.close();
		}
	}

	/**
	 * 获取Redis中的记录数
     *
	 * @return 记录数
	 */
    public Long dbSize() {
        if (isCluster) {
            return getDbSizeCluster();
        } else {
            return getDbSizeSingle();
        }
    }

    private Long getDbSizeCluster() {
        throw new NotSupportInClusterException("dbsize");
    }

    private Long getDbSizeSingle() {
		Long dbSize = 0L;
        JedisPool jedisPool = getJedisPool();
		Jedis jedis = jedisPool.getResource();
        try {
			dbSize = jedis.dbSize();
        } finally {
            jedis.close();
		}
		return dbSize;
	}

	/**
	 * 获取符合条件的所有key
     *
	 * @param pattern 可以含有通配符的key模板
	 * @return 符合通配条件的key
	 */
    public Set<byte[]> keys(String pattern) {
        if (isCluster) {
            return getKeysCluster(pattern);
        } else {
            return getKeysSingle(pattern);
        }
    }

    private Set<byte[]> getKeysCluster(String pattern) {
        throw new NotSupportInClusterException("keys");
    }

    private Set<byte[]> getKeysSingle(String pattern) {
		Set<byte[]> keys = null;
        JedisPool jedisPool = getJedisPool();
		Jedis jedis = jedisPool.getResource();
        try {
			keys = jedis.keys(pattern.getBytes());
        } finally {
            jedis.close();
		}
		return keys;
	}

    private JedisPool getJedisPool() {
        return jedisPoolMap.get(cacheName);
    }

    private JedisCluster getJedisCluster() {
        return jedisClusterMap.get(cacheName);
    }

	/**
     * 获取相应key的剩余过期时间
     * 
     * @param key 要查看剩余过期时间的key，不可使用通配符
     * @return 相应key的剩余过期时间，单位为秒
     */
    public long ttl(String key) {
    	long ttl = 0L;
    	JedisPool jedisPool = getJedisPool();
        Jedis jedis = jedisPool.getResource();
        try {
        	ttl = jedis.ttl(key);
        } finally {
        	jedis.close();
        }
        return ttl;
    }

    /**
	 * 获取Redis服务的主机名
     *
	 * @return Redis服务的主机名
	 */
	public String getHost() {
		return host;
	}

	/**
	 * 设置Redis服务的主机名
     *
	 * @param host Redis服务的主机名
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 获取Redis服务的端口
     *
	 * @return Redis服务的端口
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 设置Redis服务的端口
     *
	 * @param port Redis服务的端口
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 获取缓存过期时间
     *
	 * @return 缓存过期时间
	 */
	public int getExpire() {
		return expire;
	}

	/**
	 * 设置缓存过期时间
     *
	 * @param expire 缓存过期时间
	 */
	public void setExpire(int expire) {
		this.expire = expire;
	}

	/**
	 * 获取连接超时时长
     *
	 * @return 连接超时时长
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * 设置连接超时时长
     *
	 * @param timeout 连接超时时长
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * 获取Redis的连接密码
     *
	 * @return Redis的连接密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置Redis的连接密码
     *
	 * @param password Redis的连接密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	

    public Set<String> getHosts() {
        return hosts;
    }
	
    public void setHosts(Set<String> hosts) {
        this.hosts = hosts;
    }
	
    public boolean isCluster() {
        return isCluster;
    }
}
