package cn.org.bachelor.iam.idm.service;

import cn.org.bachelor.exception.BusinessException;
import cn.org.bachelor.iam.IamConfiguration;
import cn.org.bachelor.iam.oauth2.client.model.OAuth2ClientCertification;
import cn.org.bachelor.iam.oauth2.utils.StringUtils;
import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.iam.vo.UserVo;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class IdmService {
    private static final Logger logger = LoggerFactory.getLogger(IdmService.class);

    @Autowired
    private DefaultImSysService userSysService;

    @Autowired(required = false)
    private List<UserExtendInfoProvider> userExtendInfoProviders;

    public UserVo getUserDetail(String userId) {
        List<UserVo> users = userSysService.findUsersDetail(userId);
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    private String getUserCode(String token) {
        if (StringUtils.isEmpty(token)) return "";
        try {
            JwtToken jwt = JwtToken.decode(token);
            return (String) jwt.getClaims().get(JwtToken.PayloadKey.USER_CODE);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BusinessException(e);
        }
    }

    public void logout(String authorization) {
        logger.info("user logout with token {}", authorization);
        String userCode = getUserCode(authorization);
        logger.info("user logout with code {}", userCode);
        userSysService.logout(userCode);
    }

    public String refreshAccessToken(String authorization) {
        logger.info("user refreshToken with token {}", authorization);
        String account = getUserCode(authorization);
        if (StringUtils.isEmpty(account)) {
            throw new BusinessException("invalid access token");
        }
        return userSysService.getRefreshToken(account);
    }

    public Map<? extends String, ? extends Object> getUserExtInfo(JSONObject user) {
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
