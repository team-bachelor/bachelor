package cn.org.bachelor.acm.da;

import cn.org.bachelor.context.ILogonUserContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.mapper.autoconfigure.ConfigurationCustomizer;

import javax.annotation.Resource;

@Configuration
public class DaWebConfig {

    @Resource
    private ILogonUserContext context;

    @Bean
    ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.addInterceptor(new DaSqlInterceptor(context));
            }
        };
    }
}
