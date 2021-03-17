package cn.org.bachelor.iam;

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
 *
 * @author liuzhuo
 */
@EnableHystrix
@EnableFeignClients
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableScheduling
@MapperScan(basePackages = {"cn.org.bachelor.iam.acm.dao"})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"cn.org.bachelor"})
public class IamWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(IamWebApplication.class, args);
	}

}
