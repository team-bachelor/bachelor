package cn.org.bachelor.demo.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 开启cglib 代理并增强
 *
 * @author lixiaolong
 * @EnableCircuitBreaker 开启断路器功能
 */
@EnableHystrix
@EnableFeignClients
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableScheduling
@MapperScan(basePackages = {"cn.org.bachelor.demo.web.dao", "cn.org.bachelor.iam.acm.dao"})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"cn.org.bachelor"})
public class WebDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebDemoApplication.class, args);
		System.out.println("启动成功");
	}

}
