package cn.org.bachelor.up.oauth2.rsserver.util;

import org.apache.commons.lang3.StringUtils;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {

	private static JedisPool jedisPool = null;

	private static String ADDR = "127.0.0.1";
	private static int PORT = 6379;
	private static String AUTH;
	private static int MAX_ACTIVE = 1024;
	private static int MAX_IDLE = 200;
	private static int MAX_WAIT = 10000;
	private static int TIMEOUT = 10000;
	private static boolean TEST_ON_BORROW = true;
	private static int CYCLE = 30 ;

	/**
	 * 初始化连接池
	 */
	static {
		try {
			/**
			 * 获取redis配置
			 */
			ADDR = ApplicationUtil.getValue("addr", ADDR);
			PORT = Integer.parseInt(ApplicationUtil.getValue("port",
					String.valueOf(PORT)));
			AUTH = ApplicationUtil.getValue("auth", AUTH);
			MAX_ACTIVE = Integer.parseInt(ApplicationUtil.getValue(
					"max_active", String.valueOf(MAX_ACTIVE)));
			MAX_IDLE = Integer.parseInt(ApplicationUtil.getValue("max_idle",
					String.valueOf(MAX_IDLE)));
			MAX_WAIT = Integer.parseInt(ApplicationUtil.getValue("max_wait",
					String.valueOf(MAX_WAIT)));
			TIMEOUT = Integer.parseInt(ApplicationUtil.getValue("timeout",
					String.valueOf(TIMEOUT)));
			TEST_ON_BORROW = Boolean.parseBoolean(ApplicationUtil.getValue(
					"test_on_borrow", String.valueOf(TEST_ON_BORROW)));
			CYCLE = Integer.parseInt(ApplicationUtil.getValue("session_life_cycle",
					String.valueOf(CYCLE)));
			/**
			 * 初始化redis配置
			 */
			JedisPoolConfig config = new JedisPoolConfig() ;
			config.setMaxTotal(MAX_ACTIVE);
			config.setMinIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config,ADDR,PORT,TIMEOUT,AUTH) ;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Jedis实例
	 */
	private synchronized static Jedis getJedis(){
		try{
			if(jedisPool!=null){
				Jedis resource = jedisPool.getResource() ;
				return resource ;
			}else{
				return null ;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return null ;
		}
	}

	/**
	 * 释放资源
	 */
	public static void freeResource(final Jedis jedis){
		if(jedis!=null){
			jedisPool.returnResourceObject(jedis);
		}
	}
	
	public static String saveString(String key, String value) {
		return saveString(key, value, CYCLE * 60);
	}
	
	public static String saveString(String key, String value, Integer seconds) {
		String result = "FAIL" ;
		if(StringUtils.isNotBlank(key) && value!=null){
			final Jedis jedis = getJedis() ;
			int cycle = seconds;
			result = jedis.setex(key, cycle, value);
			freeResource(jedis) ;
		}
		return result ;
	}

	/**
	 * redis 存储对象
	 */
	public static String saveObject(String key,Object obj){
		String result = "FAIL" ;
		if(StringUtils.isNotBlank(key) && obj!=null){
			final Jedis jedis = getJedis() ;
			int cycle = CYCLE * 60 ;
			result = jedis.setex(key.getBytes(), cycle,SerializeUtil.serialize(obj)) ;
			freeResource(jedis) ;
		}
		return result ;
	}

	/**
	 * redis 存储对象
	 */
	public static String saveObject(String key,Object obj,Integer seconds){
		String result = "FAIL" ;
		if(StringUtils.isNotBlank(key) && obj!=null){
			final Jedis jedis = getJedis() ;
			int cycle = seconds ;
			result = jedis.setex(key.getBytes(), cycle, SerializeUtil.serialize(obj)) ;
			freeResource(jedis) ;
		}
		return result ;
	}

	/**
	 * redis 获取对象
	 * @return 返回索取对象
	 */
	public static String getString(String key){
		String result = "" ;
		if(StringUtils.isNotBlank(key)){
			final Jedis jedis = getJedis() ;
			result = jedis.get(key) ;
			freeResource(jedis) ;
		}
		return result ;
	}

	/**
	 * redis 获取对象
	 * @return 返回索取对象
	 */
	public static Object getObject(String key){
		Object result = null ;
		if(StringUtils.isNotBlank(key)){
			final Jedis jedis = getJedis() ;
			byte[] bytes = jedis.get(key.getBytes()) ;
			result = SerializeUtil.unserialize(bytes) ;
			freeResource(jedis) ;
		}
		return result ;
	}

	/**
	 * 移除存储对象
	 */
	public static long removeObject(String key){
		long result = 0 ;
		if(StringUtils.isNotBlank(key)){
			final Jedis jedis = getJedis() ;
			result = jedis.del(key);
			freeResource(jedis) ;
		}
		return result ;
	}

	/**
	 * 刷新存储对象
	 */
	public static long refreshObject(String key,Integer seconds){
		long result = 0 ;
		if(StringUtils.isNotBlank(key)){
			final Jedis jedis = getJedis() ;
			result = jedis.expire(key,seconds);
			freeResource(jedis) ;
		}
		return result ;
	}

	public static void main(String[] args){
		final Jedis jedis = JedisUtil.getJedis() ;
//		// 存储简单对象
//		jedis.set("test_key", "本市日前公布2014年度全市职工年平均工资为77560元，月平均工资为6463元，与其相关的社保缴费基数的上下限、工伤保险定期待遇等均会照此标准进行调整") ;
//		// 存储带有生命周期的对象
//		jedis.setex("ex", 25, "setex") ;
//		System.out.println(jedis.get("ex")+","+jedis.ttl("ex")) ;
//		JedisUtil.freeResource(jedis);

//		// 创建对象
//		UserBasicInfo bui = new UserBasicInfo() ;
//		bui.setAccount("zhangsan");
//		bui.setCode("111");
//		jedis.setex(bui.getCode().getBytes(), 100,SerializeUtil.serialize(bui)) ;
//		byte[] person = jedis.get(bui.getCode().getBytes()) ;
//		System.out.println(person);
//		UserBasicInfo user = (UserBasicInfo)SerializeUtil.unserialize(person) ;
//		System.out.println(user.getCode()+","+user.getAccount());
	}
}
