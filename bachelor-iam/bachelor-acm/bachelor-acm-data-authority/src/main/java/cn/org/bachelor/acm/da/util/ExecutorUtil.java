package cn.org.bachelor.acm.da.util;

import cn.org.bachelor.exception.SystemException;
import cn.org.bachelor.acm.da.Dialect;
//import com.github.pagehelper.BoundSqlInterceptor.Chain;
//import com.github.pagehelper.BoundSqlInterceptor.Type;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

public abstract class ExecutorUtil {
    private static Field additionalParametersField;
    private static Field providerMethodArgumentNamesField;

    public ExecutorUtil() {
    }

    public static Map<String, Object> getAdditionalParameter(BoundSql boundSql) {
        try {
            return (Map)additionalParametersField.get(boundSql);
        } catch (IllegalAccessException var2) {
            throw new SystemException("获取 BoundSql 属性值 additionalParameters 失败: " + var2, var2);
        }
    }

    public static String[] getProviderMethodArgumentNames(ProviderSqlSource providerSqlSource) {
        try {
            return providerMethodArgumentNamesField != null ? (String[])((String[])providerMethodArgumentNamesField.get(providerSqlSource)) : null;
        } catch (IllegalAccessException var2) {
            throw new SystemException("获取 ProviderSqlSource 属性值 providerMethodArgumentNames: " + var2, var2);
        }
    }

    public static MappedStatement getExistedMappedStatement(Configuration configuration, String msId) {
        MappedStatement mappedStatement = null;

        try {
            mappedStatement = configuration.getMappedStatement(msId, false);
        } catch (Throwable var4) {
        }

        return mappedStatement;
    }

    public static <E> List<E> tenantQuery(Dialect dialect, Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql, CacheKey cacheKey) throws SQLException {
        if (!dialect.beforePage(ms, parameter, rowBounds)) {
            return executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, boundSql);
        } else {
            parameter = dialect.processParameterObject(ms, parameter, boundSql, cacheKey);
            String pageSql = dialect.getTenantSql(ms, boundSql, parameter, rowBounds, cacheKey);
            BoundSql pageBoundSql = new BoundSql(ms.getConfiguration(), pageSql, boundSql.getParameterMappings(), parameter);
            Map<String, Object> additionalParameters = getAdditionalParameter(boundSql);
            Iterator var12 = additionalParameters.keySet().iterator();

            while(var12.hasNext()) {
                String key = (String)var12.next();
                pageBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
            }

//            if (dialect instanceof Chain) {
//                pageBoundSql = ((Chain)dialect).doBoundSql(Type.PAGE_SQL, pageBoundSql, cacheKey);
//            }

            return executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, pageBoundSql);
        }
    }

    static {
        try {
            additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
            additionalParametersField.setAccessible(true);
        } catch (NoSuchFieldException var2) {
            throw new SystemException("获取 BoundSql 属性 additionalParameters 失败: " + var2, var2);
        }

        try {
            providerMethodArgumentNamesField = ProviderSqlSource.class.getDeclaredField("providerMethodArgumentNames");
            providerMethodArgumentNamesField.setAccessible(true);
        } catch (NoSuchFieldException var1) {
        }

    }
}
