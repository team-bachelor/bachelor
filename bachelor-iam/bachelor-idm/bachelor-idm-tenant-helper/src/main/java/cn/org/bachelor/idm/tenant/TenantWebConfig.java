package cn.org.bachelor.idm.tenant;

import cn.org.bachelor.context.ITenantContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.mapper.autoconfigure.ConfigurationCustomizer;

import javax.annotation.Resource;

@Configuration
public class TenantWebConfig {

    @Resource
    private ITenantContext context;

    @Bean
    ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.addInterceptor(new TenantSqlInterceptor(context));
            }
        };
    }
}
