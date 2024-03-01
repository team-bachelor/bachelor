                                                                                                package cn.org.bachelor.iam.oauth2.rs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = {"cn.org.bachelor"})
@MapperScan({"cn.org.bachelor.iam.idm.login.dao","cn.org.bachelor.iam.acm.dao"})
public class RsTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RsTestApplication.class, args);
    }
}
