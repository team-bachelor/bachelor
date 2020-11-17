package cn.org.bachelor.architecture.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ClassName SpringBootAdminApplication
 * @Description:
 * @Author Alexhendar
 * @Date 2018/9/21 11:49
 * @Version 1.0
 **/
@SpringBootApplication
@EnableAdminServer
public class SpringBootAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminApplication.class, args);
    }

    @Configuration
    public static class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/assets/**").permitAll()
                    .anyRequest().authenticated().and()
                    .formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll().and()
                    .logout().permitAll().and()
                    .csrf().disable();
//            http.logout().permitAll().and();
        }
    }
}
