package cn.org.bachelor.acm.da;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Properties;

/**
 * 数据库方言，针对不同数据库进行实现
 *
 * @author liuzh
 */
public interface Dialect {
    /**
     * 跳过 count 和 分页查询
     *
     * @param ms              MappedStatement
     * @param parameterObject 方法参数
     * @param rowBounds       分页参数
     * @return true 跳过，返回默认查询结果，false 执行分页查询
     */
    boolean skip(MappedStatement ms, Object parameterObject, RowBounds rowBounds);

    /**
     * 处理查询参数对象
     *
     * @param ms              MappedStatement
     * @param parameterObject
     * @param boundSql
     * @param pageKey
     * @return
     */
    Object processParameterObject(MappedStatement ms, Object parameterObject, BoundSql boundSql, CacheKey pageKey);

    /**
     * 执行分页前，返回 true 会进行分页查询，false 会返回默认查询结果
     *
     * @param ms              MappedStatement
     * @param parameterObject 方法参数
     * @param rowBounds       分页参数
     * @return
     */
    boolean beforePage(MappedStatement ms, Object parameterObject, RowBounds rowBounds);

    /**
     * 生成分页查询 sql
     *
     * @param ms              MappedStatement
     * @param boundSql        绑定 SQL 对象
     * @param parameterObject 方法参数
     * @param rowBounds       分页参数
     * @param pageKey         分页缓存 key
     * @return
     */
    String getPageSql(MappedStatement ms, BoundSql boundSql, Object parameterObject, RowBounds rowBounds, CacheKey pageKey);

    /**
     * 分页查询后，处理分页结果，拦截器中直接 return 该方法的返回值
     *
     * @param pageList        分页查询结果
     * @param parameterObject 方法参数
     * @param rowBounds       分页参数
     * @return
     */
    Object afterPage(List pageList, Object parameterObject, RowBounds rowBounds);

    /**
     * 完成所有任务后
     */
    void afterAll();

    /**
     * 设置参数
     *
     * @param properties 插件属性
     */
    void setProperties(Properties properties);
}
