/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package cn.org.bachelor.acm.dac;

import cn.org.bachelor.acm.dac.annotation.DacEnabled;
import cn.org.bachelor.acm.dac.parser.DacSqlParser;
import cn.org.bachelor.context.ILogonUser;
import cn.org.bachelor.context.ILogonUserContext;
import cn.org.bachelor.exception.SystemException;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * QueryInterceptor 规范
 * <p>
 * 详细说明见文档：https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/Interceptor.md
 *
 * @author liuzh/abel533/isea533
 * @version 1.0.0
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}), @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),})
public class DacInterceptor implements Interceptor {

    private static final Log log = LogFactory.getLog(DacInterceptor.class);

    private DacConfiguration configuration;

    private ILogonUserContext logonUserContext;

    private List<String> dacTables;

    public DacInterceptor() {
        String bannerEnabled = System.getProperty("bachelor.dac.banner");
        if (StringUtil.isEmpty(bannerEnabled)) {
            bannerEnabled = System.getenv("BACHELOR_DAC_BANNER");
        }
        //默认 TRUE
        if (StringUtil.isEmpty(bannerEnabled) || Boolean.parseBoolean(bannerEnabled)) {
            log.debug("\n-------- bachelor data access control is intercepting.--------------\n");
        }
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
        DacField[] dacFields = configuration.getFields();
        //如果配置为不启用，或者方法/类上设置了不启用，或是未设置访问控制的字段，则当前查询不进行处理
        if (skip(invocation) || dacFields.length == 0) {
            return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
        }

        Map<DacField, Object> colAndValue = getFieldValueMap(dacFields);

        if (parameter instanceof Example) {
            Example e = ((Example) parameter);

            colAndValue.keySet().forEach(key -> {
                Object value = colAndValue.get(key);
                String targetFieldName = getTarget(e.getEntityClass(), key.getName());
                if (targetFieldName == null) {
                    throw new SystemException("目标拦截对象：["
                            + e.getEntityClass().getTypeName() +
                            "]不包含属性：[" + key.getName() + "]" +
                            ",建议在类定义中增加对应的属性。");
                }
//                key.setDeep(true);
                if (key.isDeep() && value instanceof String && value != null && key.getPattern() != null && key.getPattern().length() == value.toString().length()) {
                    // TODO 先不用dialect
                    int index = getMatchIndex(key, value);
                    String condition = value.toString().substring(0, index + 1) + "%";
                    e.and(e.createCriteria().andLike(targetFieldName, condition));
                } else {
                    e.and(e.createCriteria().andEqualTo(targetFieldName, value));
                }
            });
            boundSql = ms.getBoundSql(parameter);
        } else {
            DacSqlParser sqlParser = new DacSqlParser(this.dacTables, Arrays.asList(this.configuration.getFields()));
            String dacSql = sqlParser.getSmartDacSql(boundSql.getSql());
            boundSql = new BoundSql(ms.getConfiguration(), dacSql, boundSql.getParameterMappings(), parameter);
        }

        return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
    }

    private String getTarget(Class<?> entityClass, String key) {
        Field[] fs = entityClass.getDeclaredFields();
        key = toUppercaseIgnoreUnderscore(key);
        for (int i = 0; i < fs.length; i++) {
            String fn = toUppercaseIgnoreUnderscore(fs[i].getName());
            if (fn.equals(key)) {
                return fs[i].getName();
            }
        }
        return null;
    }

    private boolean skip(Invocation invocation) {
        DacEnabled enabled = invocation.getMethod().getAnnotation(DacEnabled.class);
        if (enabled != null) {
            return !enabled.enabled();
        } else {
            enabled = invocation.getMethod().getDeclaringClass().getAnnotation(DacEnabled.class);
            if (enabled != null) {
                return !enabled.enabled();
            }
        }
        return !configuration.isEnabled();
    }

    private int getMatchIndex(DacField key, Object value) {
        char[] patternArray = key.getPattern().toCharArray();
        char[] valueArray = value.toString().toCharArray();
        int i = patternArray.length - 1;
        for (; i >= 0; i--) {
            char p = patternArray[i];
            char v = valueArray[i];
            if (p != v) {
                return i;
            }
        }
        return patternArray.length - 1;
    }

    private Map<DacField, Object> getFieldValueMap(DacField[] dacFields) {
        Map<DacField, Object> colAndValue = new HashMap<>(dacFields.length);
        ILogonUser user = logonUserContext.getLogonUser();
        if (user == null) {
            return colAndValue;
        }
        Class<?> clazz = user.getClass();
        Map<String, Field> fields = getClassFieldsMap(clazz.getDeclaredFields());
        Map<String, Method> methods = getClassGetMethodMap(clazz.getDeclaredMethods());
        for (int i = 0; i < dacFields.length; i++) {
            String dfName = dacFields[i].getName();
            if (dfName == null || dfName.isEmpty()) {
                continue;
            }
            Object value = getValue(user, dacFields[i], methods, fields);
            colAndValue.put(dacFields[i], value);
        }
        return colAndValue;
    }

    private Map<String, Method> getClassGetMethodMap(Method[] declaredMethods) {
        Map<String, Method> map = new HashMap<>(declaredMethods.length);
        for (int i = 0; i < declaredMethods.length; i++) {
            String mName = toUppercaseIgnoreUnderscore(declaredMethods[i].getName());
            if (mName.startsWith("GET")) {
                map.put(mName, declaredMethods[i]);
            }
        }
        return map;
    }

    private Map<String, Field> getClassFieldsMap(Field[] declaredFields) {
        Map<String, Field> map = new HashMap<>(declaredFields.length);
        for (int i = 0; i < declaredFields.length; i++) {
            map.put(toUppercaseIgnoreUnderscore(declaredFields[i].getName()), declaredFields[i]);
        }
        return map;
    }

    private Object getValue(ILogonUser user, DacField dacField, Map<String, Method> methods, Map<String, Field> fields) {
        String dfName = dacField.getName();
        dfName = toUppercaseIgnoreUnderscore(dfName);
        String dfGetMethodName = "GET" + dfName;
        Method m = methods.get(dfGetMethodName);
        if (m != null) {
            m.setAccessible(true);
            try {
                return m.invoke(user);
            } catch (IllegalAccessException e) {
                throw new SystemException("方法不能访问：" +
                        m.getDeclaringClass().getTypeName() +
                        ":" + m.getName());
            } catch (InvocationTargetException e) {
                throw new SystemException("方法访问错误：" +
                        m.getDeclaringClass().getTypeName() +
                        ":" + m.getName() +
                        "-" + e);
            }
        }
        Field f = fields.get(dfName);
        if (f != null) {
            f.setAccessible(true);
            try {
                return f.get(user);
            } catch (IllegalAccessException e) {
                throw new SystemException("属性访问错误：" +
                        m.getDeclaringClass().getTypeName() +
                        ":" + m.getName() +
                        "-" + e);
            }
        }
        return null;
    }

    private String toUppercaseIgnoreUnderscore(String dfName) {
        return dfName.replaceAll("_", "").toUpperCase(Locale.ENGLISH);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
//        this.properties = (DacProperties) properties;
    }

    public void setDacProperties(DacConfiguration properties) {
        this.configuration = properties;
    }

    public void setLogonUserContext(ILogonUserContext context) {
        this.logonUserContext = context;
    }

    public void setDacTables(List<String> dacTables) {
        this.dacTables = dacTables;
    }
}
