package cn.org.bachelor.web.config;

import cn.org.bachelor.web.ReplaceRereadRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * RereadRequestFilterConfig 类用于配置可重读请求过滤器。
 * 该配置类会创建并注册一个过滤器，用于处理特定的请求。
 *
 * @author [作者姓名]
 * @since [项目开始日期]
 */
@Configuration
public class RereadRequestFilterConfig {
    /**
     * 注册过滤器，将可重读请求过滤器注册到 Spring 容器中。
     *
     * @return FilterRegistrationBean 过滤器注册 Bean，包含过滤器的配置信息
     */
    @Bean
    public FilterRegistrationBean<Filter> filterRegistration() {
        // 创建一个过滤器注册 Bean 实例
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        // 设置过滤器，将自定义的可重读请求过滤器设置到注册 Bean 中
        registration.setFilter(replaceRereadRequestFilter());
        // 添加过滤器的 URL 匹配模式，这里表示匹配所有请求路径
        registration.addUrlPatterns("/*");
        // 设置过滤器的名称，方便在日志或管理中识别
        registration.setName("streamFilter");
        // 返回配置好的过滤器注册 Bean
        return registration;
    }

    /**
     * 实例化 Filter，创建一个可重读请求过滤器的实例。
     *
     * @return Filter 可重读请求过滤器实例
     */
    @Bean(name = "replaceRereadRequestFilter")
    public Filter replaceRereadRequestFilter() {
        // 创建并返回自定义的可重读请求过滤器实例
        return new ReplaceRereadRequestFilter();
    }
}