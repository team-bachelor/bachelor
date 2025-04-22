package cn.org.bachelor.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

/**
 * 可读取热点数据的缓存管理器.
 * @author 刘卓
 * @since 2015/8/25
 */
public class HotspotRedisCacheManager extends RedisCacheManager {

    private String dbname = "";
    private static final Logger logger = LoggerFactory
            .getLogger(HotspotRedisCacheManager.class);

    /**
     * 获取缓存对象.
     * @param table  数据库中的表名
     * @param column 数据库中的列名
     * @return 缓存对象
     */
    public Cache getCache(String table, String column) {
        // 若开启调试日志，记录获取缓存实例的信息
        if (logger.isDebugEnabled()) {
            logger.debug("获取表名为: [" + table + "]，列名为: [" + column + "] 的热区RedisCache实例");
        }
        // 检查 dbname 是否为空或 null，若为空则抛出异常
        if(dbname == null || "".equals(dbname)){
            throw new IllegalArgumentException("dbname can not be null or empty.");
        }
        // 根据表名、列名和 dbname 生成缓存名称，然后获取缓存实例
        return this.getCache("pdmi_" + dbname + "." + table + "_" + column + "_");
//        return this.getCache("pdmi_userplatform." + table + "_");
    }

    /**
     * 获取数据库名称
     * @return 数据库名称
     */
    public String getDbname() {
        return dbname;
    }

    /**
     * 设置数据库名称
     * @param dbname 数据库名称
     */
    public void setDbname(String dbname) {
        this.dbname = dbname;
    }
}