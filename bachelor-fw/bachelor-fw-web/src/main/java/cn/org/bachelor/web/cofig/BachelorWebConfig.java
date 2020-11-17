package cn.org.bachelor.web.cofig;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import cn.org.bachelor.web.aspect.PageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebConfig
 * @Description:
 * @Author Alexhendar
 * @Date 2018/10/9 18:46
 * @Version 1.0
 **/
@Configuration
public class BachelorWebConfig implements WebMvcConfigurer {

    @Autowired
    private PageInterceptor pageInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pageInterceptor)
                .addPathPatterns("/**/*").excludePathPatterns("/login/**");
    }


    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
