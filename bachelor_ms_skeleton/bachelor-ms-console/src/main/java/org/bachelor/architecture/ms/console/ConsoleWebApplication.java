package org.bachelor.architecture.ms.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.autoconfigure.ConfigurationPropertiesRebinderAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

@EnableCircuitBreaker //开启断路器功能
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = {"org.bachelor.architecture.ms.console.cache"})
@SpringBootApplication(scanBasePackages = {"org.bachelor"},scanBasePackageClasses={org.bachelor.common.auth.interceptor.AuthInterceptorConfig.class},exclude = {DataSourceAutoConfiguration.class, ConfigurationPropertiesRebinderAutoConfiguration.class})
@EnableHystrix
@Configuration
@EnableHystrixDashboard
public class ConsoleWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsoleWebApplication.class, args);
    }
}
