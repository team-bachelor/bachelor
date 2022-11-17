package cn.org.bachelor.acm.da;

import cn.org.bachelor.acm.da.util.ExecutorUtil;
import cn.org.bachelor.acm.da.util.MetaObjectUtil;
import cn.org.bachelor.context.ILogonUserContext;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
public class DaSqlInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(DaSqlInterceptor.class);
    private ILogonUserContext context;

    public DaSqlInterceptor(ILogonUserContext context) {
        this.context = context;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];
        Executor executor = (Executor) invocation.getTarget();
        CacheKey cacheKey;
        BoundSql boundSql;
        //由于逻辑关系，只会进入一次
        if (args.length == 4) {
            //4 个参数时
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            //6 个参数时
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }
        //TODO 自己要进行的各种处理
        //注：下面的方法可以根据自己的逻辑调用多次，在分页插件中，count 和 page 各调用了一次
        parameter = processParameterObject(ms, parameter, boundSql, cacheKey);
        return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
    }

    public Object processParameterObject(MappedStatement ms, Object parameterObject, BoundSql boundSql, CacheKey pageKey) {

        Map<String, Object> paramMap = null;
        if (parameterObject == null) {
            paramMap = new HashMap();
        } else if (parameterObject instanceof Map) {
            paramMap = new HashMap();
            paramMap.putAll((Map) parameterObject);
        } else {
            paramMap = new HashMap();
            if (ms.getSqlSource() instanceof ProviderSqlSource) {
                String[] providerMethodArgumentNames = ExecutorUtil.getProviderMethodArgumentNames((ProviderSqlSource) ms.getSqlSource());
                if (providerMethodArgumentNames != null && providerMethodArgumentNames.length == 1) {
                    paramMap.put(providerMethodArgumentNames[0], parameterObject);
                    paramMap.put("param1", parameterObject);
                }
            }

            boolean hasTypeHandler = ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass());
            MetaObject metaObject = MetaObjectUtil.forObject(parameterObject);
            if (!hasTypeHandler) {
                String[] var9 = metaObject.getGetterNames();
                int var10 = var9.length;

                for (int var11 = 0; var11 < var10; ++var11) {
                    String name = var9[var11];
                    paramMap.put(name, metaObject.getValue(name));
                }
            }
            if (boundSql.getParameterMappings() != null && boundSql.getParameterMappings().size() > 0) {
                Iterator var14 = boundSql.getParameterMappings().iterator();

                ParameterMapping parameterMapping;
                String name;
                do {
                    do {
                        parameterMapping = (ParameterMapping) var14.next();
                        name = parameterMapping.getProperty();
                    } while (paramMap.get(name) != null);
                } while (!hasTypeHandler && !parameterMapping.getJavaType().equals(parameterObject.getClass()) && var14.hasNext());
                paramMap.put(name, parameterObject);
            }
        }

        String tenantId = context.getLogonUser().getTenantId();
        setParam(tenantId, "tenantId", parameterObject, paramMap);

        String orgId = context.getLogonUser().getOrgId();
        setParam(orgId, "orgCode", parameterObject, paramMap);
        return paramMap;
    }

    private void setParam(String colValue, String colName, Object parameterObject, Map<String, Object> paramMap) {
        if (!StringUtils.isEmpty(colValue)) {
            if(parameterObject instanceof Example){
                Example e = ((Example)parameterObject);
                try {
                    e.getEntityClass().getField(colName);
                    e.createCriteria().andEqualTo(colName, colValue);
                } catch (NoSuchFieldException ex) {
                    logger.warn("获取字段出错", ex);
                }
            }else {
                paramMap.put(colName, colValue);
            }
        }
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
