package cn.org.bachelor.iam.idm.service;

import cn.org.bachelor.exception.BusinessException;
import cn.org.bachelor.iam.IamConfiguration;
import cn.org.bachelor.iam.oauth2.client.model.OAuth2ClientCertification;
import cn.org.bachelor.iam.oauth2.utils.StringUtils;
import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.iam.vo.UserVo;
import com.alibaba.fastjson.JSONObject;
import com.sun.javafx.collections.UnmodifiableObservableMap;
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

    @Autowired
    private IamConfiguration authConfig;

    @Autowired(required = false)
    private List<UserExtendInfoProvider> userExtendInfoProviders;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String getJWTString(OAuth2ClientCertification upCC, JSONObject userinfo) {
        String accesstoken = upCC.getAccessToken();
        String refreshToken = upCC.getRefreshToken();
        Date expTime_Date = parseExpireTime(upCC.getExpiresTime());
        // 有效期保持与用户系统一致
        long expTime = expTime_Date.getTime();
        long currentTime = new Date().getTime();
//        Map<String, Object> userObject = JSONParser.parseJSON(userinfo);
        userinfo.put("accesstoken", accesstoken);

        // 存储refreshToken为token有效期的2倍
        userSysService.saveRefreshToken(userinfo.getString("account"), refreshToken, 2 * (expTime - currentTime));
        userinfo.put(JwtToken.PayloadKey.EXP, expTime);
        userinfo.put(JwtToken.PayloadKey.IAT, currentTime);

        userinfo.put(JwtToken.PayloadKey.ISS, ""); // jwt签发者
        userinfo.put(JwtToken.PayloadKey.SUB, userinfo.get("account")); // jwt所面向的用户
        userinfo.put(JwtToken.PayloadKey.AUD, ""); // 接收jwt的一方
        userinfo.put(JwtToken.PayloadKey.NBF, ""); // 接收jwt的一方
        userinfo.put(JwtToken.PayloadKey.JTI, ""); // jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击

        userinfo.put(JwtToken.PayloadKey.USER_NAME, userinfo.get("username"));
        userinfo.put(JwtToken.PayloadKey.USER_CODE, userinfo.get("account"));
        String userId = userinfo.getString("userId");
        userinfo.put(JwtToken.PayloadKey.USER_ID, userId);
        UserVo userDetail = getUserDetail(userId);
        if (userDetail != null) {
            userinfo.put(JwtToken.PayloadKey.ORG_ID, userDetail.getOrgId());
            userinfo.put(JwtToken.PayloadKey.ORG_NAME, userDetail.getOrgName());
            userinfo.put(JwtToken.PayloadKey.DEPT_ID, userDetail.getDeptId());
            userinfo.put(JwtToken.PayloadKey.DEPT_NAME, userDetail.getDeptName());
        }
        logger.info(userinfo.toString());
        String token = JwtToken.create(userinfo.toJSONString(), authConfig.getPrivateKey());
        return token;
    }


    public UserVo getUserDetail(String userId) {
        List<UserVo> users = userSysService.findUsersDetail(userId);
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    public Date parseExpireTime(String expireTime) {
        Date expTime;
        try {
            expTime = sdf.parse(expireTime);
        } catch (ParseException e) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            expTime = calendar.getTime();
        }
        return expTime;
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
//        String userCode = user.getString("account");
//
//        Map m = new HashMap<String, Object>(1);
//        m.put("area_id", "12345");
        Map umMap = Collections.unmodifiableMap(user);
        Map<? extends String, ? extends Object> result = new LinkedHashMap();
        userExtendInfoProviders.forEach( p ->{
            Map r = p.invoke(umMap);
            if(r != null && r.size() > 0){
                result.putAll(r);
            }
        });
        return result;
    }
}
