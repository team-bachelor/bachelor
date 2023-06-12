package cn.org.bachelor.web.config;

import cn.org.bachelor.web.ReplaceRereadRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class RereadRequestFilterConfig {
    /**
     * 注册过滤器
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(replaceRereadRequestFilter());
        registration.addUrlPatterns("/*");
        registration.setName("streamFilter");
        return registration;
    }

    /**
     * 实例化Filter
     *
     * @return Filter
     */
    @Bean(name = "replaceRereadRequestFilter")
    public Filter replaceRereadRequestFilter() {
        return new ReplaceRereadRequestFilter();
    }
}
