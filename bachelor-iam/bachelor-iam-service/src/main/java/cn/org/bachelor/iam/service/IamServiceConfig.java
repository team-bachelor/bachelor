package cn.org.bachelor.iam.service;

import cn.org.bachelor.iam.IamConfig;
import cn.org.bachelor.iam.acm.service.AuthorizeService;
import cn.org.bachelor.iam.acm.service.AuthorizeServiceStub;
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
    public ServiceACInterceptor authInterceptor() {
        return new ServiceACInterceptor();
    }

    /**
     * @return 用户识别拦截器
     */
    @Bean
    public ServiceIDInterceptor userInterceptor() {
        return new ServiceIDInterceptor();
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