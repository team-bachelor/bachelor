package cn.org.bachelor.iam.service;

import cn.org.bachelor.iam.IamConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author liuzhuo
 * @描述
 * @创建时间 2018/11/5
 */
@Configuration
public class IamServiceConfig implements WebMvcConfigurer {

    @Resource
    private IamConfig config;

    /**
     * @return 访问控制拦截器
     */
    @Bean
    public AccessControlInterceptor authInterceptor() {
        return new AccessControlInterceptor();
    }

    /**
     * @return 用户识别拦截器
     */
    @Bean
    public IdentifyInterceptor userInterceptor() {
        return new IdentifyInterceptor();
    }

    private static final String[] excludePath = {
            "/swagger-ui.html",
            "/**/*swagger*/**",
            "/**/*hystrix*/**",
            "/error",
            "/idm/as/accesstoken",//与idmascontroller相关
            "/idm/as/logout"//与idmascontroller相关
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
