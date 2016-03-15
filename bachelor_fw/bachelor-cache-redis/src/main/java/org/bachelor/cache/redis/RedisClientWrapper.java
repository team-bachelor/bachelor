package org.bachelor.cache.redis;

import org.bachelor.cache.NotSupportInClusterException;
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
	
	private String host = "127.0.0.1";
	
	private int port = 6379;
	
	// 0 - never expire
	private int expire = 0;
	
	//timeout for jedis try to connect to redis server, not expire time! In milliseconds
	private int timeout = 0;
	
	private String password = "";
	
    private static HashMap<String, JedisPool> jedisPoolMap = new HashMap<String, JedisPool>(0);
	
    private static HashMap<String, JedisCluster> jedisClusterMap = new HashMap<String, JedisCluster>(0);
		
    private String cacheName = "";

    private Set<String> hosts;

    private boolean isCluster = false;

    public RedisClientWrapper() {

	}
	
	/**
	 * 初始化方法
	 */
//    public void init() {
//        init(host + ":" + port);
//    }
    public void init(String name) {
        cacheName = name;
        Set<HostAndPort> haps = null;
        if (hosts != null) {
//            if (hosts.size() == 1) {
//                HostAndPort hap = parseHostAndPort(hosts.iterator().next());
//                host = hap.getHost();
//                port = hap.getPort();
//                addToJedisPool(cacheName, host, port);
//            } else {
                haps = new HashSet<HostAndPort>(hosts.size());
                for (String hostString : hosts) {
                    HostAndPort hap = parseHostAndPort(hostString);
                    haps.add(hap);
                }
                JedisCluster jc = null;
                if (timeout == 0) {
                    jc = new JedisCluster(haps);
                } else {
                    jc = new JedisCluster(haps, timeout);

                }
                isCluster = true;
                jedisClusterMap.put(name, jc);
//            }
        }
        if (haps == null || haps.size() == 0) {
            addToJedisPool(cacheName, host, port);
        }
    }

    private void addToJedisPool(String name, String _host, int _port) {
        JedisPool jedisPool = jedisPoolMap.get(name);
        if (jedisPool == null) {
            if (password != null && !"".equals(password)) {
                jedisPool = new JedisPool(new JedisPoolConfig(), _host, _port, timeout, password);
            } else if (timeout != 0) {
                jedisPool = new JedisPool(new JedisPoolConfig(), _host, _port, timeout);
            } else {
                jedisPool = new JedisPool(new JedisPoolConfig(), _host, _port);
            }
            jedisPoolMap.put(name, jedisPool);
        }
    }

    private HostAndPort parseHostAndPort(String hostString) {
        StringTokenizer st = new StringTokenizer(hostString, ":");
        if (st.hasMoreElements() & st.countTokens() == 2) {
            return new HostAndPort(st.nextToken(), Integer.valueOf(st.nextToken()));
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
        if(isCluster){
            return hgetAllCluster(key);
			}else{
            return hgetAllSingle(key);
			}
		}

    private Map<String, String> hgetAllCluster(String key) {
        JedisCluster jc = getJedisCluster();
        return jc.hgetAll(key);
	}
	
    private Map<String, String> hgetAllSingle(String key) {
        Map<String, String> value = null;
        JedisPool jedisPool = getJedisPool();
        Jedis jedis = jedisPool.getResource();
        try {
            value = jedis.hgetAll(key);
        } finally {
            jedis.close();
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

    private byte[] getCluster(byte[] key) {
        JedisCluster jc = getJedisCluster();
        return jc.get(key);
    }

    private byte[] getSingle(byte[] key) {
		byte[] value = null;
        JedisPool jedisPool = getJedisPool();
		Jedis jedis = jedisPool.getResource();
        try {
			value = jedis.get(key);
        } finally {
            jedis.close();
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
	 * 将指定value以指定key存入Redis
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

    private byte[] setCluster(byte[] key, byte[] value, int expire) {
        JedisCluster jc = getJedisCluster();
        jc.set(key, value);
        if (expire != 0) {
            jc.expire(key, expire);
        }
        return value;
    }

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

    private void delCluster(byte[] key) {
        JedisCluster jc = getJedisCluster();
        jc.del(key);
    }

    private void delSingle(byte[] key) {
        JedisPool jedisPool = getJedisPool();
		Jedis jedis = jedisPool.getResource();
        try {
			jedis.del(key);
        } finally {
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

    private JedisPool getJedisPool() {
        return jedisPoolMap.get(cacheName);
    }

    private JedisCluster getJedisCluster() {
        return jedisClusterMap.get(cacheName);
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
