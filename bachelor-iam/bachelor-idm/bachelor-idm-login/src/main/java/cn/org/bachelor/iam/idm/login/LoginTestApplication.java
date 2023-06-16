package cn.org.bachelor.iam.idm.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = {"cn.org.bachelor"})
@MapperScan("cn.org.bachelor.iam.idm.login.dao")
public class LoginTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginTestApplication.class,args);
    }
}
