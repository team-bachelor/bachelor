package cn.org.bachelor.iam.dac.client;

import cn.org.bachelor.context.IUserContext;
import cn.org.bachelor.iam.dac.client.annotation.DacTable;
import cn.org.bachelor.iam.dac.client.util.ClassUtils;
import cn.org.bachelor.iam.dac.client.util.StringUtil;
import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Nullable;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzhuo
 */
@Configuration
@ConditionalOnProperty(prefix = "bachelor.dac",
        name = {"enabled"}, havingValue = "true", matchIfMissing = true)
//@ConditionalOnBean(SqlSessionFactory.class)
@EnableConfigurationProperties(DacConfiguration.class)
@AutoConfigureAfter(MybatisAutoConfiguration.class)
@AutoConfigureBefore(PageHelperAutoConfiguration.class)
@Lazy(false)
public class DacAutoConfig implements InitializingBean, WebMvcConfigurer {

    private final List<SqlSessionFactory> sqlSessionFactoryList;

    private final DacConfiguration configuration;

    private IUserContext logonUserContext;


    public DacAutoConfig(List<SqlSessionFactory> sqlSessionFactoryList, DacConfiguration configuration, @Nullable IUserContext logonUserContext) {
        this.sqlSessionFactoryList = sqlSessionFactoryList;
        this.configuration = configuration;
        this.logonUserContext = logonUserContext;
    }

    /**
     * @return 拦截器
     */
    @Bean
    public DacWebInterceptor dacInterceptor() {
        return new DacWebInterceptor(configuration);
    }

    private static final String[] excludePath = {
            "/swagger-ui.html",
            "/**/*swagger*/**",
            "/**/*hystrix*/**",
            "/error",
            "/idm/as/accesstoken",//与idmascontroller相关
            "/idm/as/logout"//与idmascontroller相关
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (configuration.isEnabled()) {
            registry.addInterceptor(dacInterceptor()).order(10)
                    .excludePathPatterns(excludePath)
                    .addPathPatterns("/**/*");
        }
    }

    @Override
    public void afterPropertiesSet() {
        DacInterceptor interceptor = new DacInterceptor();
        interceptor.setDacProperties(configuration);
        interceptor.setLogonUserContext(logonUserContext);
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
            if (!containsInterceptor(configuration, interceptor)) {
                configuration.addInterceptor(interceptor);
            }
        }
        List<String> dacTables = new ArrayList<>();
        if(configuration.getPackages() != null && configuration.getPackages().length > 0) {
            for (String aPackage : configuration.getPackages()) {
                aPackage = aPackage.endsWith(".") ? aPackage : aPackage + ".";
                dacTables.addAll(getDacTables(aPackage));
            }
        }
        interceptor.setDacTables(dacTables);
    }

    public List<String> getDacTables(String pack) {
        List<Class<?>> classes = ClassUtils.getClasses(pack);
        List<String> dacTables = new ArrayList<>();
        for (Class<?> clazz : classes) {
            DacTable dacTable = clazz.getAnnotation(DacTable.class);
            if (dacTable != null) {
                if (StringUtil.isNotEmpty(dacTable.name())) {
                    dacTables.add(dacTable.name());
                } else {
                    Table table = clazz.getAnnotation(Table.class);
                    if (table != null) {
                        dacTables.add((StringUtil.isEmpty(table.catalog()) ? "" : table.catalog() + ".") + table.name());
                    } else {
                        throw new InvalidDacConfigException("DacTable必须设置name或者设置DacTable注解的同时设置Table注解");
                    }
                }
            }
        }
        return dacTables;
    }

    /**
     * 是否已经存在相同拦截器
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
