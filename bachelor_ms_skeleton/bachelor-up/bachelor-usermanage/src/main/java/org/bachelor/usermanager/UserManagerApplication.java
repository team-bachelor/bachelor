package cn.org.bachelor.usermanager;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCircuitBreaker // 开启断路器功能
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"cn.org.bachelor"})
public class UserManagerApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserManagerApplication.class, args);
  }

}
