package cn.org.bachelor.iam;

import cn.org.bachelor.iam.acm.interceptor.UserAccessControlInterceptor;
import cn.org.bachelor.iam.idm.interceptor.UserIdentifyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;

/**
 * @author liuzhuo
 * @描述
 * @创建时间 2023/5/25
 */
@Configuration
public class IamCommonConfig {

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

    public static final String[] excludePath = {
            "/swagger-ui.html",
            "/**/*swagger*/**",
            "/**/*hystrix*/**",
            "/error",
            "/idm/as/accesstoken",
            "/idm/as/login",
            "/idm/as/logout"
    };

    public boolean isEnableUserIdentify() {
        return config.isEnableUserIdentify();
    }

    public boolean isEnableUserAccessControl() {
        return config.isEnableUserAccessControl();
    }

    public String[] getExcludePathPatterns() {
        return config.getExcludePathPatterns();
    }
}
