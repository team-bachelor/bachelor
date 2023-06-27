package cn.org.bachelor.iam.service;


import cn.org.bachelor.iam.IamConstant;
import cn.org.bachelor.iam.IamContext;
import cn.org.bachelor.iam.idm.service.IamSysService;
import cn.org.bachelor.iam.oauth2.client.model.OAuth2ClientCertification;
import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.iam.vo.UserVo;
import cn.org.bachelor.web.util.RequestUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 访问身份识别拦截器
 * <p>
 * <b>NOTE:</b> 根据已经配置的服务和功能授权信息，控制用户的访问权限
 *
 * @author liuzhuo
 * @version 1.0
 */
public class IdentifyInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(IdentifyInterceptor.class);
    @Autowired
    private IamContext iamContext;
    @Autowired
    private IamSysService userSysService;

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
        Object o = request.getAttribute(ACCESS_BACKEND);
        user.setAccessBackend(!(o != null && "N".equals(o.toString())));

        if (logger.isInfoEnabled()) {
            logger.info("user info assembly: " + JSONObject.toJSONString(user));
        }
        //如果header里面没取到，则尝试从session里面取
        if (user.getAccessToken() == null || "".equals(user.getAccessToken())) {
            OAuth2ClientCertification ucc = (OAuth2ClientCertification) request.getSession().getAttribute(IamConstant.SESSION_AUTHENTICATION_KEY);
            if (ucc != null) {
                user.setAccessToken(ucc.getAccessToken());
                user.setId(ucc.getUserid());
                String personStr = (String) request.getSession().getAttribute(IamConstant.UP_USER);
                UserVo userInSession = JSONObject.parseObject(personStr, UserVo.class);
                user.setName(userInSession.getName());
                user.setOrgId(userInSession.getOrgId());
                user.setOrgName(userInSession.getOrgName());
                user.setDeptId(userInSession.getDeptId());
                user.setDeptName(userInSession.getDeptName());
                if (logger.isInfoEnabled()) {
                    logger.info("user info in session: " + JSONObject.toJSONString(user));
                }
            }
        }
        if (user.isAccessBackend() && user.getAccessToken() != null) {
            user.setAdministrator(userSysService.checkUserIsAdmin(user));
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
