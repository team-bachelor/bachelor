package cn.org.bachelor.iam.idm.service;

import cn.org.bachelor.exception.BusinessException;
import cn.org.bachelor.iam.IamConstant;
import cn.org.bachelor.iam.credential.AbstractIamCredential;
import cn.org.bachelor.iam.pojo.IamUser;
import cn.org.bachelor.iam.token.JwtToken;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;

import static cn.org.bachelor.iam.token.JwtToken.PayloadKey.*;

@Service
public class IdmService {
    private static final Logger logger = LoggerFactory.getLogger(IdmService.class);

    @Autowired
    private IamSysService userSysService;


    @Autowired(required = false)
    private List<UserExtendInfoProvider> userExtendInfoProviders;

    public IamUser getUserDetail(String userId) {
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
        if (user == null) {
            user = new HashMap<>(0);
        }
        JwtToken token = getJwtToken(request, user.containsKey(USER_ID) ? user.get(USER_ID).toString() : "");
        token.getClaims().put(USER_CODE, user.get(ACCOUNT));
        token.getClaims().put(USER_NAME, user.get("username"));
        token.getClaims().put(USER_ID, user.get(USER_ID));
        token.getClaims().put(OPEN_ID, user.get(OPEN_ID_CAM));
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
        IamUser userDetail = getUserDetail(userId);
        userDetail.setExtendInfo(getUserExtInfo((JSONObject)JSON.toJSON(userDetail)));
        userDetail.setAccessToken(upCC.getCredential().toString());
        return JwtToken.create(userDetail, upCC.getExpiresTime());
    }

    public Map<String, Object> getUserExtInfo(Map<String, Object> user) {
        Map umMap = Collections.unmodifiableMap(user);
        Map<String, Object> result = new LinkedHashMap();
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
