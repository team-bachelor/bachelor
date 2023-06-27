package cn.org.bachelor.iam.idm.service;

import cn.org.bachelor.exception.BusinessException;
import cn.org.bachelor.iam.IamConstant;
import cn.org.bachelor.iam.credential.AbstractIamCredential;
import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.iam.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static cn.org.bachelor.iam.token.JwtToken.PayloadKey.USER_ID;

@Service
public class IdmService {
    private static final Logger logger = LoggerFactory.getLogger(IdmService.class);

    @Autowired
    private IamSysService userSysService;


    @Autowired(required = false)
    private List<UserExtendInfoProvider> userExtendInfoProviders;

    public UserVo getUserDetail(String userId) {
        return userSysService.findUsersDetail(userId);
    }

    private String getUserCode(JwtToken jwt) {
        try {
            return (String) jwt.getClaims().get(JwtToken.PayloadKey.USER_CODE);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BusinessException(e);
        }
    }

    public void logout(JwtToken authorization) {
        logger.info("user logout with token {}", authorization);
        String userCode = getUserCode(authorization);
        logger.info("user logout with code {}", userCode);
        userSysService.logout(userCode);
    }

    public JwtToken getAccessToken(HttpServletRequest request, HttpServletResponse response, String code) {
        Map<String, Object> user = userSysService.getAccessToken(request, response, code);
        if (user != null) {
            user.putAll(getUserExtInfo(user));
        }
        JwtToken token = getJwtToken(request, user.containsKey(USER_ID) ? user.get(USER_ID).toString() : "");
        return token;
    }

    public JwtToken refreshAccessToken(HttpServletRequest request, HttpServletResponse response, String newToken) {
        Map<String, Object> userinfo = userSysService
                .refreshToken(request, response, newToken);
        return getJwtToken(request,
                userinfo.containsKey(USER_ID) ?
                        userinfo.get(USER_ID).toString()
                        : "");
    }

    private JwtToken getJwtToken(HttpServletRequest request, String userId) {
        AbstractIamCredential upCC = (AbstractIamCredential) request.getSession()
                .getAttribute(IamConstant.SESSION_AUTHENTICATION_KEY);
        UserVo userDetail = getUserDetail(userId);
        return JwtToken.create(userDetail, upCC);
    }

    public Map<? extends String, ? extends Object> getUserExtInfo(Map<String, Object> user) {
        Map umMap = Collections.unmodifiableMap(user);
        Map<? extends String, ? extends Object> result = new LinkedHashMap();
        if (userExtendInfoProviders == null) {
            return result;
        }
        userExtendInfoProviders.forEach(p -> {
            Map r = p.invoke(umMap);
            if (r != null && r.size() > 0) {
                result.putAll(r);
            }
        });
        return result;
    }
}
