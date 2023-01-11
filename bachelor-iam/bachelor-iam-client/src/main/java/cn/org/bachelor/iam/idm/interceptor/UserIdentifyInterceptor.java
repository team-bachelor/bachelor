package cn.org.bachelor.iam.idm.interceptor;

import cn.org.bachelor.iam.IamContext;
import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.iam.vo.UserVo;
import cn.org.bachelor.iam.idm.service.ImSysService;
import cn.org.bachelor.iam.oauth2.client.model.OAuth2ClientCertification;
import cn.org.bachelor.iam.oauth2.client.util.ClientConstant;
import cn.org.bachelor.service.ApplicationContextHolder;
import cn.org.bachelor.web.util.RequestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 权限拦截器
 * <p>
 * <b>NOTE:</b> 根据已经配置的服务和功能授权信息，控制用户的访问权限
 *
 * @author liuzhuo
 * @version 1.0
 */

public class UserIdentifyInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(UserIdentifyInterceptor.class);
    @Autowired
    private IamContext iamContext;
    @Autowired
    private ImSysService imSysService;

    private static final String ACCESS_BACKEND = "up_access_backend";//是否访问后台获取用户状态，N为不访问，其余为访问

    //private Set<String> urlCache;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("进入用户信息拦截器，开始组装用户信息：" + request.getServletPath());
        UserVo user = new UserVo();
        user.setId(request.getHeader(JwtToken.PayloadKey.USER_ID));
        user.setCode(request.getHeader(JwtToken.PayloadKey.USER_CODE));
        user.setName(urlDecode(request.getHeader(JwtToken.PayloadKey.USER_NAME)));
        user.setOrgCode(request.getHeader(JwtToken.PayloadKey.ORG_CODE));
        user.setOrgName(urlDecode(request.getHeader(JwtToken.PayloadKey.ORG_NAME)));
        user.setOrgId(request.getHeader(JwtToken.PayloadKey.ORG_ID));
        user.setDeptId(request.getHeader(JwtToken.PayloadKey.DEPT_ID));
        user.setDeptName(request.getHeader(JwtToken.PayloadKey.DEPT_NAME));
        user.setAccessToken(request.getHeader(JwtToken.PayloadKey.ACCESS_TOKEN));
        user.setTenantId(request.getHeader(JwtToken.PayloadKey.TENANT_ID));
        user.setAreaId(request.getHeader(JwtToken.PayloadKey.AREA_ID));
        user.setAreaName(request.getHeader(JwtToken.PayloadKey.AREA_NAME));
        Object o = request.getAttribute(ACCESS_BACKEND);
        if (o != null && "N".equals(o.toString())) {
            user.setAccessBackend(false);
        } else {
            user.setAccessBackend(true);
        }

        if (logger.isDebugEnabled()) {
            ObjectMapper m = new ObjectMapper();
            try {
                logger.debug("user info assembly: " + m.writeValueAsString(user));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        //如果header里面没取到，则尝试从session里面取
        if (user.getAccessToken() == null || "".equals(user.getAccessToken())) {
            OAuth2ClientCertification ucc = (OAuth2ClientCertification) request.getSession().getAttribute(ClientConstant.SESSION_AUTHENTICATION_KEY);
            if (ucc != null) {
                user.setAccessToken(ucc.getAccessToken());
                user.setId(ucc.getUserid());
                String orgId = (String) request.getSession().getAttribute(ClientConstant.UP_ORG_ID);
                String userName = (String) request.getSession().getAttribute(ClientConstant.UP_USER_NAME);
                String orgName = (String) request.getSession().getAttribute(ClientConstant.UP_ORG_NAME);
                String deptId = (String) request.getSession().getAttribute(ClientConstant.UP_DEPT_ID);
                String deptName = (String) request.getSession().getAttribute(ClientConstant.UP_DEPT_NAME);
                user.setName(userName);
                user.setOrgId(orgId);
                user.setOrgName(orgName);
                user.setDeptId(deptId);
                user.setDeptName(deptName);
                if (logger.isDebugEnabled()) {
                    ObjectMapper m = new ObjectMapper();
                    try {
                        logger.debug("user info in session: " + m.writeValueAsString(user));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (user.isAccessBackend() && user.getAccessToken() != null) {
            user.setAdministrator(imSysService.checkUserIsAdmin(user));
            user.setAccessBackend(false);
        }
        iamContext.setLogonUser(user);

        iamContext.setRemoteIP(RequestUtil.getIpAddr(request));
        return true;
    }


    private String urlDecode(String param) {
        try {
            return StringUtils.isBlank(param) ? StringUtils.EMPTY : URLDecoder.decode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
