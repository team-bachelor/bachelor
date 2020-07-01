package org.bachelor.userlogin;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.jwt.crypto.sign.RsaSigner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
@SpringBootApplication(scanBasePackages = {"org.bachelor"},exclude = {DataSourceAutoConfiguration.class})
public class UserLoginApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserLoginApplication.class, args);
  }


}
