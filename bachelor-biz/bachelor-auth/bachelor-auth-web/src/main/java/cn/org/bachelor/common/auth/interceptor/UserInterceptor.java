package cn.org.bachelor.common.auth.interceptor;


import cn.org.bachelor.web.util.RequestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import cn.org.bachelor.common.auth.AuthValueHolderService;
import cn.org.bachelor.common.auth.service.UserSysService;
import cn.org.bachelor.common.auth.token.JwtToken;
import cn.org.bachelor.common.auth.vo.UserVo;
import cn.org.bachelor.up.oauth2.client.model.UpClientCertification;
import cn.org.bachelor.up.oauth2.client.util.UpClientConstant;
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
 * @Version 1.0
 */
public class UserInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(UserInterceptor.class);
    @Autowired
    private AuthValueHolderService valueHolder;
    @Autowired
    private UserSysService userSysService;

    public static final String ACCESS_BACKEND = "up_access_backend";//是否访问后台获取用户状态，N为不访问，其余为访问

    //private Set<String> urlCache;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("进入用户拦截器：" + request.getServletPath());
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
        Object o = request.getAttribute(ACCESS_BACKEND);
        if (o != null && "N".equals(o.toString())) {
            user.setAccessBackend(false);
        } else {
            user.setAccessBackend(true);
        }

        if (logger.isDebugEnabled()) {
            ObjectMapper m = new ObjectMapper();
            try {
                logger.debug("HEADER用户登录信息：" + m.writeValueAsString(user));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        //如果header里面没取到，则尝试从session里面取
        if (user.getAccessToken() == null || "".equals(user.getAccessToken())) {
            UpClientCertification ucc = (UpClientCertification) request.getSession().getAttribute(UpClientConstant.SESSIONAUTHENTICATIONKEY);
            if (ucc != null) {
                user.setAccessToken(ucc.getAccessToken());
                user.setId(ucc.getUserid());
                String orgId = (String) request.getSession().getAttribute(UpClientConstant.UPORGID);
                String userName = (String) request.getSession().getAttribute(UpClientConstant.UPUSERNAME);
                String orgName = (String) request.getSession().getAttribute(UpClientConstant.UPORGNAME);
                String deptId = (String) request.getSession().getAttribute(UpClientConstant.UPDEPTID);
                String deptName = (String) request.getSession().getAttribute(UpClientConstant.UPDEPTNAME);
                user.setName(userName);
                user.setOrgId(orgId);
                user.setOrgName(orgName);
                user.setDeptId(deptId);
                user.setDeptName(deptName);
                if (logger.isDebugEnabled()) {
                    ObjectMapper m = new ObjectMapper();
                    try {
                        logger.debug("SESSION用户登录信息：" + m.writeValueAsString(user));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (user.isAccessBackend() && user.getAccessToken() != null) {
            user.setAdministrator(userSysService.checkUserIsAdmin(user));
            user.setAccessBackend(false);
        }
        valueHolder.setCurrentUser(user);

        valueHolder.setRemoteIP(RequestUtils.getIpAddr(request));
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
