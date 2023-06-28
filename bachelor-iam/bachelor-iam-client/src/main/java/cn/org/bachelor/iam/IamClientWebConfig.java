package cn.org.bachelor.iam;

import cn.org.bachelor.iam.acm.interceptor.UserAccessControlInterceptor;
import cn.org.bachelor.iam.idm.interceptor.UserIdentifyInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.annotation.Resource;

/**
 * @author liuzhuo
 * @描述
 * @创建时间 2018/11/5
 */
@Configuration
@ConditionalOnMissingClass("cn.org.bachelor.iam.idm.login.config.SecurityConfig")
public class IamClientWebConfig implements WebMvcConfigurer {

    @Resource
    private IamConfiguration config;
    /**
     * @return 访问控制拦截器
     */
    @Bean
    public HandlerInterceptorAdapter authInterceptor() {
        return new UserAccessControlInterceptor();
    }

    /**
     * @return 用户识别拦截器
     */
    @Bean
    public HandlerInterceptorAdapter userInterceptor() {
        return new UserIdentifyInterceptor();
    }

    private static final String[] excludePath = {
            "/swagger-ui.html",
            "/**/*swagger*/**",
            "/**/*hystrix*/**",
            "/error",
            "/idm/as/accesstoken",
            "/idm/as/login",
            "/idm/as/logout"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (config.isEnableUserIdentify()) {
            registry.addInterceptor(userInterceptor()).order(-2)
                    .excludePathPatterns(excludePath)
                    .addPathPatterns("/**/*");
        }
        if (config.isEnableUserAccessControl()) {
            InterceptorRegistration ir = registry.addInterceptor(authInterceptor()).order(-1)
                    .excludePathPatterns(excludePath)
                    .addPathPatterns("/**");
            if (config.getExcludePathPatterns() != null && config.getExcludePathPatterns().length > 0)
                ir.excludePathPatterns(config.getExcludePathPatterns());
        }
    }
}
