package cn.org.bachelor.iam.idm.interceptor;

import cn.org.bachelor.iam.IamConfiguration;
import cn.org.bachelor.iam.IamConstant;
import cn.org.bachelor.iam.IamContext;
import cn.org.bachelor.iam.credential.AbstractIamCredential;
import cn.org.bachelor.iam.idm.service.IamSysService;
import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.iam.vo.UserVo;
import cn.org.bachelor.web.util.RequestUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static cn.org.bachelor.iam.IamConstant.ACCESS_BACKEND;

/**
 * 访问身份识别拦截器
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
    private IamSysService iamSysService;
    @Resource
    private IamConfiguration config;


    //private Set<String> urlCache;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("进入用户信息拦截器，开始组装用户信息：" + request.getServletPath());

        UserVo user = getUserVoFromRequest(request);
        if (logger.isDebugEnabled()) {
            logger.debug("user info assembly: " + JSONObject.toJSONString(user));
        }
        if (user == null) {
            return true;
        }
        //如果header里面没取到，则尝试从session里面取
        if (StringUtils.isNotBlank(user.getAccessToken())) {
            AbstractIamCredential ucc = (AbstractIamCredential) request.getSession().getAttribute(IamConstant.SESSION_AUTHENTICATION_KEY);
            if (ucc != null) {
                user.setAccessToken((String) ucc.getCredential());
                user.setId(ucc.getSubject());
                String ver = request.getHeader(JwtToken.PayloadKey.VER);
                if (StringUtils.isEmpty(ver) || JwtToken.Ver1.equals(ver)) {
                    user.setOrgId(getSessionString(request, IamConstant.UP_ORG_ID));
                    user.setName(getSessionString(request, IamConstant.UP_USER_NAME));
                    user.setOrgName(getSessionString(request, IamConstant.UP_ORG_NAME));
                    user.setDeptId(getSessionString(request, IamConstant.UP_DEPT_ID));
                    user.setDeptName(getSessionString(request, IamConstant.UP_DEPT_NAME));
                } else if (JwtToken.Ver2.equals(ver)) {
                    String personStr = getSessionString(request, IamConstant.UP_USER);
                    UserVo userInSession = JSONObject.parseObject(personStr, UserVo.class);
                    user.setName(userInSession.getName());
                    user.setOrgId(userInSession.getOrgId());
                    user.setOrgName(userInSession.getOrgName());
                    user.setDeptId(userInSession.getDeptId());
                    user.setDeptName(userInSession.getDeptName());
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("user info in session: " + JSONObject.toJSONString(user));
                }
            }
        }
        iamContext.setUser(user);

        iamContext.setRemoteIP(RequestUtil.getIpAddr(request));
        return true;
    }

    private String getSessionString(HttpServletRequest request, String key) {
        return (String) request.getSession().getAttribute(key);
    }

    private UserVo getUserVoFromRequest(HttpServletRequest request) {
        boolean enableGateWay = config.isEnableGateway();

        UserVo user = new UserVo();
        Map<String, Object> claims;
        if (enableGateWay) {
//            if (StringUtils.isBlank(ver) || JwtToken.Ver1.equals(ver)) {
            claims = getHeaderMap(request);
//            } else if (JwtToken.Ver2.equals(ver)) {
//                String claimsJson = request.getHeader(JwtToken.PayloadKey.CLAIMS);
//                claims = JSONObject.parseObject(claimsJson);
//            }
        } else {
            String tokenString = request.getHeader(IamConstant.HTTP_HEADER_TOKEN_KEY);
            if (StringUtils.isEmpty(tokenString)) {
                return null;
            }
            JwtToken jwt = JwtToken.decode(tokenString);
            claims = jwt.getClaims();
        }
        fillUserByClaims(user, claims);
        Object o = request.getAttribute(ACCESS_BACKEND);
        user.setAccessBackend(!(o != null && "N".equals(o.toString())));
        if (user.isAccessBackend() && user.getAccessToken() != null) {
            user.setAdministrator(iamSysService.checkUserIsAdmin(user));
            user.setAccessBackend(false);
        }
        return user;
    }

    private Map<String, Object> getHeaderMap(HttpServletRequest request) {
        Map<String, Object> headers = new HashMap<>();
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            headers.put(name, request.getHeader(name));
        }
        return headers;
    }

    private void fillUserByClaims(UserVo user, Map<String, Object> claims) {
        if (claims == null || claims.size() == 0) {
            return;
        }
        user.setId(getJwtClaim(JwtToken.PayloadKey.USER_ID, claims));
        user.setCode(getJwtClaim(JwtToken.PayloadKey.USER_CODE, claims));
        user.setName(urlDecode(getJwtClaim(JwtToken.PayloadKey.USER_NAME, claims)));
        user.setOrgCode(getJwtClaim(JwtToken.PayloadKey.ORG_CODE, claims));
        user.setOrgName(urlDecode(getJwtClaim(JwtToken.PayloadKey.ORG_NAME, claims)));
        user.setOrgId(getJwtClaim(JwtToken.PayloadKey.ORG_ID, claims));
        user.setDeptId(getJwtClaim(JwtToken.PayloadKey.DEPT_ID, claims));
        user.setDeptName(getJwtClaim(JwtToken.PayloadKey.DEPT_NAME, claims));
        user.setAccessToken(getJwtClaim(JwtToken.PayloadKey.ACCESS_TOKEN, claims));
        user.setTenantId(getJwtClaim(JwtToken.PayloadKey.TENANT_ID, claims));
        user.setAreaId(getJwtClaim(JwtToken.PayloadKey.AREA_ID, claims));
    }

    private String getJwtClaim(String key, Map<String, Object> claims) {
        return claims.containsKey(key) ? claims.get(key).toString() : "";
    }


    private String urlDecode(String param) {
        try {
            return StringUtils.isEmpty(param) ? StringUtils.EMPTY : URLDecoder.decode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
