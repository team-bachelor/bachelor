package cn.org.bachelor.common.auth.interceptor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/11/5
 */
@Configuration
@ConfigurationProperties(prefix="bachelor.auth")
public class AuthInterceptorConfig implements WebMvcConfigurer {

    private String[] excludePathPatterns;

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }
    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }

    private static final String[] excludePath = {
            "/swagger-ui.html",
            "/**/*swagger*/**",
            "/**/*hystrix*/**",
            "/error",
            "/user/accesstoken",
            "/user/logout"
    };
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor()).order(-2)
                .excludePathPatterns(excludePath)
                .addPathPatterns("/**/*");
        InterceptorRegistration ir = registry.addInterceptor(authInterceptor()).order(-1)
                .excludePathPatterns(excludePath)
                .addPathPatterns("/**");
        if (excludePathPatterns != null && excludePathPatterns.length > 0)
            ir.excludePathPatterns(excludePathPatterns);

    }

    public void setExcludePathPatterns(String[] excludePathPatterns) {
        this.excludePathPatterns = excludePathPatterns;
    }
}
