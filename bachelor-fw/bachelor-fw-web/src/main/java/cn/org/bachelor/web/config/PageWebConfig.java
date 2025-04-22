package cn.org.bachelor.web.config;

import cn.org.bachelor.web.paging.PageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 分页配置类，用于配置分页拦截器。
 *
 * @author liuzhuo
 * @since 2018/11/5
 */
@Configuration
public class PageWebConfig implements WebMvcConfigurer {

    /**
     * 创建一个分页拦截器实例，并将其作为 Spring Bean 管理。
     *
     * @return 分页拦截器实例
     */
    @Bean
    public PageInterceptor pageInterceptor() {
        return new PageInterceptor();
    }

    /**
     * 重写 WebMvcConfigurer 的方法，将分页拦截器添加到拦截器注册表中。
     *
     * @param registry 拦截器注册表，用于注册和管理拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 向拦截器注册表中添加分页拦截器
        registry.addInterceptor(pageInterceptor())
                // 设置拦截器的执行顺序为 -2
                .order(-2)
                // 配置拦截器拦截所有路径
                .addPathPatterns("/**/*");
    }
}