package cn.org.bachelor.common.auth.interceptor;


import cn.org.bachelor.common.auth.AuthValueHolderService;
import cn.org.bachelor.common.auth.service.AuthorizeService;
import cn.org.bachelor.common.auth.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * 权限拦截器
 * <p>
 * <b>NOTE:</b> 根据已经配置的服务和功能授权信息，控制用户的访问权限
 *
 * @author liuzhuo
 * @Version 1.0
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    @Autowired
    private AuthValueHolderService valueHolder;

    @Autowired
    private AuthorizeService authorizeService;

    //private Set<String> urlCache;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getServletPath();
        String permCode = requestMethod + ":" + requestPath;
        logger.info("进入权限拦截器：" + permCode);
//        return true;
        /** 判断请求方式是否符合规范 **/

        // 获取访问方法
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequestMapping requestAnno = handlerMethod.getMethodAnnotation(RequestMapping.class);
            requestPath = (requestAnno.value())[0];
            RequestMapping[] classRMs = handlerMethod.getBeanType().getAnnotationsByType(RequestMapping.class);
            if (classRMs.length > 0) {
                requestPath = (classRMs[0].value())[0] + requestPath;
            }
            permCode = requestMethod + ":" + requestPath;
        } else if (handler instanceof ResourceHttpRequestHandler) {

        } else {
            return true;
        }

        UserVo user = valueHolder.getCurrentUser();
        boolean pass = user.isAdministrator() ? true : authorizeService.isAuthorized(permCode, user.getCode());
        logger.info("access path=[" + requestPath + "], from=[" + valueHolder.getRemoteIP() + "]user=[" + user.getCode() + "], is authorized=[" + pass + "]");
        if (!pass) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            try {
                PrintWriter pw = response.getWriter();
                pw.append("{\"status\":\"BIZ_ERR\",\"code\":\"UNAUTHORIZED\",\"msg\":\"UNAUTHORIZED\",\"data\":null,\"time\":" + new Date().getTime() + "}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pass;
    }

}
