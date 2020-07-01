package org.bachelor.common.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@EnableCircuitBreaker //开启断路器功能
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = {"org.bachelor.common.auth.dao"})
@SpringBootApplication(scanBasePackages = {"org.bachelor"})
@EnableHystrix
public class BachelorScaffoldApplication {

    public static void main(String[] args) {
        SpringApplication.run(BachelorScaffoldApplication.class, args);
    }

}
