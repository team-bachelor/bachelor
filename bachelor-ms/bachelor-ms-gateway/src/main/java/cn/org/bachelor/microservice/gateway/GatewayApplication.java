package cn.org.bachelor.microservice.gateway;

import cn.org.bachelor.microservice.gateway.ratelimit.InMemoryRateLimiter;
import cn.org.bachelor.microservice.gateway.ratelimit.IpAddressKeyResolver;
import cn.org.bachelor.microservice.gateway.ratelimit.TokenKeyResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName GatewayApplication
 * @Description:
 * @Author Alexhendar
 * @Date 2018/9/21 10:59
 * @Version 1.0
 **/

@EnableDiscoveryClient
@RefreshScope
@ComponentScan("cn.org.bachelor.**.*")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableHystrix
public class GatewayApplication {
    private static Logger logger = LoggerFactory.getLogger(GatewayApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(GatewayApplication.class, args);
        Environment env = application.getEnvironment();
        logger.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:{}\n\t" +
                        "External: \thttp://{}:{}\n\t" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));
    }

    @Bean
    @Primary
    public RateLimiter inMemoryRateLimiter() {
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
