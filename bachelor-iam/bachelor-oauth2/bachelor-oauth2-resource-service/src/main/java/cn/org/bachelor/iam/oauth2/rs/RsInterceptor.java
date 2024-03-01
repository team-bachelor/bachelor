package cn.org.bachelor.iam.oauth2.rs;


import cn.org.bachelor.iam.IamConfiguration;
import cn.org.bachelor.iam.IamContext;
import cn.org.bachelor.iam.oauth2.OAuthConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 权限拦截器
 * <p>
 * <b>NOTE:</b> 根据已经配置的服务和功能授权信息，控制用户的访问权限
 *
 * @author liuzhuo
 * @version 2.0
 */
@Slf4j
public class RsInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private IamContext iamContext;

    @Resource
    private IamConfiguration iamConfiguration;

    @Autowired
    private TokenStore tokenStore;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = getRequestPath(handler, request.getServletPath());
        String permCode = requestMethod + ":" + requestPath;
        log.debug("进入权限拦截器：" + permCode);
        /** 判断请求方式是否符合规范 **/
        String accessToken = request.getParameter(OAuthConstant.OAUTH_ACCESS_TOKEN);
        OAuth2AccessToken token = tokenStore.readAccessToken(accessToken);
        OAuth2Authentication authentication = tokenStore.readAuthentication(token);
        return true;
    }

    private String getRequestPath(Object handler, String requestPath) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequestMapping requestAnno = handlerMethod.getMethodAnnotation(RequestMapping.class);
            requestPath = (requestAnno.value())[0];
            RequestMapping[] classRMs = handlerMethod.getBeanType().getAnnotationsByType(RequestMapping.class);
            if (classRMs.length > 0) {
                requestPath = (classRMs[0].value())[0] + requestPath;
            }
        }
        return requestPath;
    }
}
