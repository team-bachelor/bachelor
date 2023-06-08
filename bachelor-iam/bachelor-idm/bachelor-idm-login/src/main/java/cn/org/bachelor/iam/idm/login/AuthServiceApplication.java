package cn.org.bachelor.iam.idm.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = {"cn.org.bachelor"})
@MapperScan("cn.org.bachelor.iam.idm.login.dao")
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class,args);
    }
}
