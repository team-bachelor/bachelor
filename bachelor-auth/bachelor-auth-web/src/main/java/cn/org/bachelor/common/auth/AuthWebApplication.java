package cn.org.bachelor.common.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.autoconfigure.ConfigurationPropertiesRebinderAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@EnableCircuitBreaker //开启断路器功能
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = {"cn.org.bachelor.common.auth.dao"})
@SpringBootApplication(scanBasePackages = {"cn.org.bachelor.**.*"},
//        scanBasePackageClasses={cn.org.bachelor.common.auth.interceptor.AuthInterceptorConfig.class},
        exclude = {ConfigurationPropertiesRebinderAutoConfiguration.class})
@EnableHystrix
public class AuthWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthWebApplication.class, args);
    }

}
