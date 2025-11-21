package cn.org.bachelor.microservice.gateway;

import cn.org.bachelor.microservice.gateway.ratelimit.InMemoryRateLimiter;
import cn.org.bachelor.microservice.gateway.ratelimit.IpAddressKeyResolver;
import cn.org.bachelor.microservice.gateway.ratelimit.TokenKeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.support.ConfigurationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class GateWayConfig {
    @Bean
    @Primary
    public InMemoryRateLimiter inMemoryRateLimiter(ConfigurationService configurationService) {
        // 可根据需求选择构造函数，此处示例使用默认限流参数
        return new InMemoryRateLimiter(2, 4, configurationService); // replenishRate=2, burstCapacity=4
    }

    @Bean(name = IpAddressKeyResolver.BEAN_NAME)
    public KeyResolver ipAddressKeyResolver() {
        return new IpAddressKeyResolver();
    }

    //    @Bean(name = TokenKeyResolver.BEAN_NAME)
    public KeyResolver tokenKeyResolver() {
        return new TokenKeyResolver();
    }
}
