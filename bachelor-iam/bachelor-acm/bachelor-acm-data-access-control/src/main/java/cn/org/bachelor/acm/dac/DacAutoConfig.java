package cn.org.bachelor.acm.dac;

import cn.org.bachelor.context.ILogonUserContext;
import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 *
 *
 * @author liuzhuo
 */
@Configuration
@ConditionalOnProperty(prefix = "bachelor.dac",
        name = {"enabled"}, havingValue = "true", matchIfMissing = true)
//@ConditionalOnBean(SqlSessionFactory.class)
@EnableConfigurationProperties(DacConfiguration.class)
//@AutoConfigureAfter(MybatisAutoConfiguration.class)
//@AutoConfigureBefore(PageHelperAutoConfiguration.class)
@AutoConfigureAfter(PageHelperAutoConfiguration.class)
@Lazy(false)
public class DacAutoConfig implements InitializingBean {

    private final List<SqlSessionFactory> sqlSessionFactoryList;

    private final DacConfiguration properties;

    private ILogonUserContext logonUserContext;

    public DacAutoConfig(List<SqlSessionFactory> sqlSessionFactoryList, DacConfiguration properties, ILogonUserContext logonUserContext) {
        this.sqlSessionFactoryList = sqlSessionFactoryList;
        this.properties = properties;
        this.logonUserContext = logonUserContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DacInterceptor interceptor = new DacInterceptor();
//        interceptor.setProperties(this.properties);
        interceptor.setDacProperties(properties);
        interceptor.setLogonUserContext(logonUserContext);
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
            if (!containsInterceptor(configuration, interceptor)) {
                configuration.addInterceptor(interceptor);
            }
        }
    }

    /**
     * 是否已经存在相同的拦截器
     *
     * @param configuration
     * @param interceptor
     * @return
     */
    private boolean containsInterceptor(org.apache.ibatis.session.Configuration configuration, Interceptor interceptor) {
        try {
            // getInterceptors since 3.2.2
            return configuration.getInterceptors().contains(interceptor);
        } catch (Exception e) {
            return false;
        }
    }

}
