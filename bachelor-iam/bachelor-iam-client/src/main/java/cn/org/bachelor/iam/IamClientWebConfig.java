package cn.org.bachelor.iam;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

import static cn.org.bachelor.iam.IamCommonConfig.excludePath;

/**
 * @author liuzhuo
 * @描述
 * @创建时间 2018/11/5
 */
@Configuration
@ConditionalOnMissingClass("cn.org.bachelor.iam.idm.login.config.SecurityConfig")
public class IamClientWebConfig implements WebMvcConfigurer {

    @Resource
    private IamCommonConfig config;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (config.isEnableUserIdentify()) {
            registry.addInterceptor(config.userInterceptor()).order(-2)
                    .excludePathPatterns(excludePath)
                    .addPathPatterns("/**/*");
        }
        if (config.isEnableUserAccessControl()) {
            InterceptorRegistration ir = registry.addInterceptor(config.authInterceptor()).order(-1)
                    .excludePathPatterns(excludePath)
                    .addPathPatterns("/**");
            if (config.getExcludePathPatterns() != null && config.getExcludePathPatterns().length > 0)
                ir.excludePathPatterns(config.getExcludePathPatterns());
        }
    }
}
