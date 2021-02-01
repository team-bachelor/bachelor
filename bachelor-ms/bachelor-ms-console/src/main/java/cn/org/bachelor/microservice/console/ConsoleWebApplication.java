package cn.org.bachelor.microservice.console;

import cn.org.bachelor.iam.acm.config.AcmConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@MapperScan(basePackages = {"cn.org.bachelor.**.dao.*"})
@SpringBootApplication(scanBasePackages = {"cn.org.bachelor.**.*"},
        scanBasePackageClasses={AcmConfig.class},
        exclude = { ConfigurationPropertiesRebinderAutoConfiguration.class})
@EnableHystrix
@Configuration
@EnableHystrixDashboard
public class ConsoleWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsoleWebApplication.class, args);
    }
}
