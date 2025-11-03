package cn.org.bachelor.iam.idm.login.config;

import cn.org.bachelor.iam.IamConfiguration;
import cn.org.bachelor.iam.idm.login.security.entryPoint.AuthAuthenticationEntryPoint;
import cn.org.bachelor.iam.idm.login.security.handler.JsonAccessDeniedHandler;
import cn.org.bachelor.iam.idm.login.security.provider.LoginAuthenticationProvider;
import cn.org.bachelor.iam.idm.login.service.UserDetailsService;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.Arrays;
import java.util.List;

/**
 * @Author lz
 */
@Configuration
@ConfigurationProperties(
        prefix = "bachelor.iam.login"
)
public class IamLocalLoginConfig {

    /**
     * 用户拦截器和访问控制拦截器要拦截的地址
     */
    @Setter
    @Getter
    private String[] excludePathPatterns;

    @Resource
    private IamConfiguration config;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private LoginAuthenticationProvider loginAuthenticationProvider;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        if (excludePathPatterns != null) {
            List<String> list = Arrays.asList(excludePathPatterns);
            list.add("/local/login");
            list.add("/captcha.png");
            excludePathPatterns = list.toArray(new String[list.size()]);
        } else {
            excludePathPatterns = new String[]{"/local/login", "/captcha.png", "/verCaptcha"};
        }
        //不通过Session获取SecurityContext
        http
                //关闭csrf
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(excludePathPatterns)
                            .anonymous()
                            .anyRequest().anonymous();
                }).sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                });


        //把token校验过滤器添加到过滤器链中
//        http.addFilterBefore(userIdentifyFilter, JwtAuthenticationTokenFilter.class);
//        http.addFilterBefore(userIdentifyFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling(handle -> {
            handle.accessDeniedHandler(accessDeniedHandler())
                    .authenticationEntryPoint(new AuthAuthenticationEntryPoint());
        });

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new JsonAccessDeniedHandler();
    }

    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authManagerBuilder
                .authenticationProvider(loginAuthenticationProvider)
                .userDetailsService(userDetailsService);
    }

    public String getPrivateKey() {
        return config.getPrivateKey();
    }

}


