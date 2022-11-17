package cn.org.bachelor.acm.da;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

public interface Dialect {

    Object processParameterObject(MappedStatement var1, Object var2, BoundSql var3, CacheKey var4);

    void setProperties(Properties var1);

    boolean beforePage(MappedStatement ms, Object parameter, RowBounds rowBounds);

    String getTenantSql(MappedStatement ms, BoundSql boundSql, Object parameter, RowBounds rowBounds, CacheKey cacheKey);
}
