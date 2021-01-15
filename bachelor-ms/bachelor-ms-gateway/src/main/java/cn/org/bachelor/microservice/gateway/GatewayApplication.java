package cn.org.bachelor.microservice.gateway;

import cn.org.bachelor.microservice.gateway.ratelimit.InMemoryRateLimiter;
import cn.org.bachelor.microservice.gateway.ratelimit.IpAddressKeyResolver;
import cn.org.bachelor.microservice.gateway.ratelimit.TokenKeyResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName GatewayApplication
 * @Description:
 * @Author Alexhendar
 * @Date 2018/9/21 10:59
 * @Version 1.0
 **/

@EnableEurekaClient
@ComponentScan("cn.org.bachelor")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableHystrix
public class GatewayApplication {
    private static Logger log = LoggerFactory.getLogger(GatewayApplication.class);

    public static void main(String[] args) {
        log.info("gateway starting");
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RateLimiter inMemoryRateLimiter(){
        return new InMemoryRateLimiter();
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
