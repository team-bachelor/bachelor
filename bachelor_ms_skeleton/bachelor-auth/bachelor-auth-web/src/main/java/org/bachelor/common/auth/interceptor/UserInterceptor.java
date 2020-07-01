package org.bachelor.common.auth.interceptor;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.bachelor.common.auth.AuthValueHolderService;
import org.bachelor.common.auth.service.UserSysService;
import org.bachelor.common.auth.token.JwtToken;
import org.bachelor.common.auth.vo.UserVo;
import org.bachelor.up.oauth2.client.model.UpClientCertification;
import org.bachelor.up.oauth2.client.util.UpClientConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;

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

        valueHolder.setRemoteIP(getIpAddr(request));
        return true;
    }


    private String urlDecode(String param) {
        try {
            return StringUtils.isBlank(param) ? StringUtils.EMPTY : URLDecoder.decode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }


    private static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if ("0:0:0:0:0:0:0:1".equals(ipAddress)) { //服务端和客户端在一台机器
                ipAddress = null;
            }

            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }
}
