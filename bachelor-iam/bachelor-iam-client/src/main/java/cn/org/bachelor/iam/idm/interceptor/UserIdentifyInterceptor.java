package cn.org.bachelor.iam.idm.interceptor;

import cn.org.bachelor.iam.IamConfiguration;
import cn.org.bachelor.iam.IamConstant;
import cn.org.bachelor.iam.IamContext;
import cn.org.bachelor.iam.credential.AbstractIamCredential;
import cn.org.bachelor.iam.pojo.IamUser;
import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.web.util.RequestUtil;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

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
@Slf4j
public class UserIdentifyInterceptor implements HandlerInterceptor {
    @Resource
    private IamContext iamContext;
    //    @Autowired
//    private IamSysService iamSysService;
    @Resource
    private IamConfiguration config;


    //private Set<String> urlCache;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("进入用户信息拦截器，开始组装用户信息：" + request.getServletPath());

        IamUser user = getUserInfoFromRequest(request);
        if (log.isDebugEnabled()) {
            log.debug("user info assembly: " + JSONObject.toJSONString(user));
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
                    IamUser userInSession = JSONObject.parseObject(personStr, IamUser.class);
                    user.setName(userInSession.getName());
                    user.setOrgId(userInSession.getOrgId());
                    user.setOrgName(userInSession.getOrgName());
                    user.setDeptId(userInSession.getDeptId());
                    user.setDeptName(userInSession.getDeptName());
                }
                if (log.isDebugEnabled()) {
                    log.debug("user info in session: " + JSONObject.toJSONString(user));
                }
            }
        }
        iamContext.setUser(user);
        log.debug("---------------↓↓↓↓↓setting user info↓↓↓↓↓---------------");
        log.debug(JSONObject.toJSONString(user));
        log.debug("---------------↑↑↑↑↑setting user info↑↑↑↑↑---------------");
//        iamSysService.refreshToken(request, response, user);
        iamContext.setRemoteIP(RequestUtil.getIpAddr(request));
        return true;
    }

    private String getSessionString(HttpServletRequest request, String key) {
        return (String) request.getSession().getAttribute(key);
    }

    private IamUser getUserInfoFromRequest(HttpServletRequest request) {
        boolean enableGateWay = config.isEnableGateway();

        IamUser user = new IamUser();
        Map<String, Object> claims;
        if (enableGateWay) {
            claims = getHeaderMap(request);
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
//            user.setAdministrator(iamSysService.assertIsAdmin(user));
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

    private void fillUserByClaims(IamUser user, Map<String, Object> claims) {
        if (claims == null || claims.size() == 0) {
            return;
        }
        user.setId(getAndDelJwtClaim(JwtToken.PayloadKey.USER_ID, claims));
        user.setCode(getAndDelJwtClaim(JwtToken.PayloadKey.USER_CODE, claims));
        user.setName(urlDecode(getAndDelJwtClaim(JwtToken.PayloadKey.USER_NAME, claims)));
        user.setOrgCode(getAndDelJwtClaim(JwtToken.PayloadKey.ORG_CODE, claims));
        user.setOrgName(urlDecode(getAndDelJwtClaim(JwtToken.PayloadKey.ORG_NAME, claims)));
        user.setOrgId(getAndDelJwtClaim(JwtToken.PayloadKey.ORG_ID, claims));
        user.setDeptId(getAndDelJwtClaim(JwtToken.PayloadKey.DEPT_ID, claims));
        user.setDeptName(getAndDelJwtClaim(JwtToken.PayloadKey.DEPT_NAME, claims));
        user.setAccessToken(getAndDelJwtClaim(JwtToken.PayloadKey.ACCESS_TOKEN, claims));
        user.setTenantId(getAndDelJwtClaim(JwtToken.PayloadKey.TENANT_ID, claims));
        user.setAreaId(getAndDelJwtClaim(JwtToken.PayloadKey.AREA_ID, claims));
        user.setExtendInfo(claims);
    }

    private String getAndDelJwtClaim(String key, Map<String, Object> claims) {
        key = key.toLowerCase();
        if (claims.containsKey(key)) {
            String result = claims.get(key).toString();
            claims.remove(key);
            return result;
        } else {
            return "";
        }
    }


    private String urlDecode(String param) {
        try {
            return StringUtils.isEmpty(param) ? StringUtils.EMPTY : URLDecoder.decode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
