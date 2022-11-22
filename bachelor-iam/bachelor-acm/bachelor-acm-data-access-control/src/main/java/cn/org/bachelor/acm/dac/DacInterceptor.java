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

import cn.org.bachelor.acm.dac.parser.DacSqlParser;
import cn.org.bachelor.context.ILogonUserContext;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.util.StringUtil;

import java.util.Properties;

/**
 * QueryInterceptor 规范
 * <p>
 * 详细说明见文档：https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/Interceptor.md
 *
 * @author liuzh/abel533/isea533
 * @version 1.0.0
 */
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
public class DacInterceptor implements Interceptor {

    private static final Log log = LogFactory.getLog(DacInterceptor.class);

    private DacConfiguration properties;

    private ILogonUserContext logonUserContext;

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
        DacSqlParser sqlParser = new DacSqlParser();
        String dacSql = sqlParser.getSmartDacSql(boundSql.getSql());
        BoundSql dacBoundSql = new BoundSql(ms.getConfiguration(), dacSql, boundSql.getParameterMappings(), parameter);
        return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, dacBoundSql);
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
        this.properties = properties;
    }

    public void setLogonUserContext(ILogonUserContext context) {
        this.logonUserContext = context;
    }

}
