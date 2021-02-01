package cn.org.bachelor.iam.acm.controller;

import cn.org.bachelor.core.exception.BusinessException;
import cn.org.bachelor.iam.acm.AuthConstant;
import cn.org.bachelor.iam.acm.config.AcmConfig;
import cn.org.bachelor.iam.idm.service.DefaultImSysService;
import cn.org.bachelor.iam.acm.token.JwtToken;
import cn.org.bachelor.iam.acm.vo.UserVo;
import cn.org.bachelor.iam.oauth2.client.OAuth2CientConfig;
import cn.org.bachelor.iam.oauth2.client.OAuth2Client;
import cn.org.bachelor.iam.oauth2.client.model.OAuth2ClientCertification;
import cn.org.bachelor.iam.oauth2.client.util.ClientConstant;
import cn.org.bachelor.iam.oauth2.exception.OAuthBusinessException;
import cn.org.bachelor.iam.oauth2.utils.JSONParser;
import cn.org.bachelor.web.json.JsonResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @描述 认证服务相关接口
 * @创建时间 2019/4/2
 * @author liuzhuo
 */
@RestController
/**
 * 将原auth-login合并，原/user/接口一并归到/dim/
 * /user/accesstoken  -> /idm/as/accesstoken
 * /user/refreshToken -> /idm/as/refreshToken
 * /user/logout       -> /idm/as/logout
 */
@RequestMapping("/idm/as")
public class IdmAsController {

    private static final Logger logger = LoggerFactory.getLogger(IdmAsController.class);

    @Autowired
    private DefaultImSysService userSysService;

    @Autowired
    private AcmConfig authConfig;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private OAuth2CientConfig clientConfig;
    //    @Autowired
//    private RsaSigner signer;

    /**
     * @描述 根据code从用户系统获取accesstoken接口，生成的accesstoken和userid等信息，计算返回JWT
     *
     * @param code 授权码
     * @param request request
     * @param response response
     * @return 合并了astoken和用户基本信息的JWT
     */
    @ApiOperation(value = "根据授权码获取访问令牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "授权码", paramType = "query", required = true)
    })
    @RequestMapping(value = "/accesstoken", method = RequestMethod.GET)
    public ResponseEntity<JsonResponse> accesstoken(@RequestParam String code, HttpServletRequest request,
                                                    HttpServletResponse response) {
        if (StringUtils.isEmpty(code)) {
            // 返回提示，client重新引导用户登录
            logger.info("code 为空,url: {} {}", request.getRequestURI(), request.getQueryString());
            // TODO 设置统一的状态，包装token
        }
        OAuth2Client client = new OAuth2Client(clientConfig, request, response);
        String userinfo = "";
        try {
            userinfo = client.bindUserInfo(code);
        } catch (OAuthBusinessException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        OAuth2ClientCertification upCC = (OAuth2ClientCertification) request.getSession()
                .getAttribute(ClientConstant.SESSIONAUTHENTICATIONKEY);
        String token_s = getJWTString(upCC, userinfo);
        Map<String, String> token = new HashMap<String, String>(1);
        token.put("token", token_s);
        return JsonResponse.createHttpEntity(token, HttpStatus.OK);
    }

    /**
     * @描述 刷新JWT
     * @param authorization header中的jwt
     * @param request request
     * @param response response
     * @return 刷新后的JWT
     */
    @ApiOperation(value = "刷新JWT")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
    public ResponseEntity<JsonResponse> refreshToken(@RequestHeader(AuthConstant.HTTP_HEADER_TOKEN_KEY) String authorization,
                                                     HttpServletRequest request, HttpServletResponse response) {
        logger.info("user refreshToken with token {}", authorization);
        String account = getUserCode(authorization);
        if (StringUtils.isBlank(account)) {
            throw new BusinessException("invalid accesstoken");
        }
        OAuth2Client client = new OAuth2Client(clientConfig, request, response);
        String userinfo = client.refreshAccessToken(userSysService.getRefreshToken(account));
        OAuth2ClientCertification upCC = (OAuth2ClientCertification) request.getSession()
                .getAttribute(ClientConstant.SESSIONAUTHENTICATIONKEY);
        String token_s = getJWTString(upCC, userinfo);
        Map<String, String> token = new HashMap<String, String>(1);
        token.put("token", token_s);
        return JsonResponse.createHttpEntity(token, HttpStatus.OK);
    }

    /**
     * @描述 退出当前系统的应用状态，单点登录的状态由app client处理 解析accesstoken，将退出登录的token存入redis，并设置有效期为token的到期时间
     *
     * @param authorization header中的jwt
     * @return 当前系统退出登录处理结果
     */
    @ApiOperation(value = "为当前用户登出系统")
    @RequestMapping(value = "/logout", method = RequestMethod.PUT)
    public ResponseEntity<JsonResponse> logout(@RequestHeader(AuthConstant.HTTP_HEADER_TOKEN_KEY) String authorization) {
        logger.info("user logout with token {}", authorization);
        String userCode = getUserCode(authorization);
        logger.info("user logout with code {}", userCode);
        userSysService.logout(userCode);
        return JsonResponse.createHttpEntity("logout success", HttpStatus.OK);
    }

    private String getJWTString(OAuth2ClientCertification upCC, String userinfo) {
        String accesstoken = upCC.getAccessToken();
        String refreshToken = upCC.getRefreshToken();
        Date expTime_Date = parseExpireTime(upCC.getExpiresTime());
        // 有效期保持与用户系统一致
        long expTime = expTime_Date.getTime();
        long currentTime = new Date().getTime();
        Map<String, Object> userObject = JSONParser.parseJSON(userinfo);
        userObject.put("accesstoken", accesstoken);

        // 存储refreshToken为token有效期的2倍
        userSysService.saveRefreshToken((String) userObject.get("account"), refreshToken, 2 * (expTime - currentTime));
        userObject.put(JwtToken.PayloadKey.EXP, expTime);
        userObject.put(JwtToken.PayloadKey.IAT, currentTime);

        userObject.put(JwtToken.PayloadKey.ISS, ""); // jwt签发者
        userObject.put(JwtToken.PayloadKey.SUB, userObject.get("account")); // jwt所面向的用户
        userObject.put(JwtToken.PayloadKey.AUD, ""); // 接收jwt的一方
        userObject.put(JwtToken.PayloadKey.NBF, ""); // 接收jwt的一方
        userObject.put(JwtToken.PayloadKey.JTI, ""); // jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击

        userObject.put(JwtToken.PayloadKey.USER_NAME, userObject.get("username"));
        userObject.put(JwtToken.PayloadKey.USER_CODE, userObject.get("account"));
        String userId = userObject.get("userId").toString();
        userObject.put(JwtToken.PayloadKey.USER_ID, userId);
        UserVo userDetail = getUserDetail(userId);
        if (userDetail != null) {
            userObject.put(JwtToken.PayloadKey.ORG_ID, userDetail.getOrgId());
            userObject.put(JwtToken.PayloadKey.ORG_NAME, userDetail.getOrgName());
            userObject.put(JwtToken.PayloadKey.DEPT_ID, userDetail.getDeptId());
            userObject.put(JwtToken.PayloadKey.DEPT_NAME, userDetail.getDeptName());
        }
        logger.info(userObject.toString());
        String token = JwtToken.create(JSONParser.buildJSON(userObject), authConfig.getPrivateKey());
        return token;
    }


    private UserVo getUserDetail(String userId) {
        List<UserVo> users = userSysService.findUsersDetail(userId);
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    private Date parseExpireTime(String expireTime) {
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
        if(StringUtils.isEmpty(token)) return "";
        try {
            JwtToken jwt = JwtToken.decode(token);
            return (String) jwt.getClaims().get(JwtToken.PayloadKey.USER_CODE);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BusinessException(e);
        }
    }
}
