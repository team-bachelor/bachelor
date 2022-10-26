package cn.org.bachelor.iam.service;


import cn.org.bachelor.iam.IamContext;
import cn.org.bachelor.iam.acm.permission.PermissionOptions;
import cn.org.bachelor.iam.acm.service.AuthorizeServiceStub;
import cn.org.bachelor.iam.vo.UserVo;
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
 * 访问控制拦截器
 * <p>
 * <b>NOTE:</b> 根据已经配置的服务和功能授权信息，控制用户的访问权限
 *
 * @author liuzhuo
 * @version 2.0
 */
public class AccessControlInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AccessControlInterceptor.class);
    @Autowired
    private IamContext valueHolder;

    @Autowired
    private AuthorizeServiceStub authorizeService;

    //private Set<String> urlCache;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getServletPath();
        String permCode = requestMethod + ":" + requestPath;
        logger.info("进入权限拦截器：" + permCode);
//        return true;
        /** 判断请求方式是否符合规范 **/
        PermissionOptions.AccessType accessType = PermissionOptions.AccessType.INTERFACE;
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
            accessType = PermissionOptions.AccessType.RESOURCE;
//            return true;
        } else {
            return true;
        }

        UserVo user = valueHolder.getCurrentUser();
        String usercode = "";
        if (user != null) {
            usercode = user.getCode();
        }
        boolean pass = isPass(permCode, accessType, user, usercode);
        logger.info("access path=[" + requestPath + "], from=[" + valueHolder.getRemoteIP() + "], user=[" + usercode + "], is authorized=[" + pass + "]");
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

    private boolean isPass(String permCode, PermissionOptions.AccessType accessType, UserVo user, String usercode) {
        if (user != null && user.isAdministrator()) {
            return true;
        }
        return authorizeService.isAuthorized(permCode, usercode, accessType);
    }

}
