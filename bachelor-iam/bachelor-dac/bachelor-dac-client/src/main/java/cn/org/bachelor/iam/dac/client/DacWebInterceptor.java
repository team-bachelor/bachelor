package cn.org.bachelor.iam.dac.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 用于数据访问控制的WEB拦截器
 * <p>
 * <b>NOTE:</b> 为当前访问装载数据访问控制的配置信息
 *
 * @author liuzhuo
 * @version 1.0
 */

public class DacWebInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(DacWebInterceptor.class);


    private DacConfiguration configuration;

    public DacWebInterceptor(DacConfiguration configuration) {
        this.configuration = configuration;
    }

    //private Set<String> urlCache;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("进入数据访问控制拦截器：" + request.getServletPath());
        DacInfo info = new DacInfo();
        info.setEnable(configuration.isEnabled());
        DacHelper.setLocalDacInfo(info);
        return true;
    }
}
