package org.bachelor.userlogin.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.bachelor.common.auth.AuthConstant;
import org.bachelor.core.exception.BusinessException;
import org.bachelor.core.exception.SystemException;
import org.bachelor.up.oauth2.client.UpClient;
import org.bachelor.up.oauth2.client.SignSecurityOAuthClient;
import org.bachelor.up.oauth2.client.URLConnectionClient;
import org.bachelor.up.oauth2.client.model.UpClientCertification;
import org.bachelor.up.oauth2.client.util.UpClientConstant;
import org.bachelor.up.oauth2.common.OAuth;
import org.bachelor.up.oauth2.exception.OAuth2Exception;
import org.bachelor.up.oauth2.request.OAuthResourceClientRequest;
import org.bachelor.up.oauth2.response.OAuthResourceResponse;
import org.bachelor.userlogin.config.UserSysConfig;
import org.bachelor.userlogin.service.impl.UserServiceImpl;
import org.bachelor.userlogin.vo.JWTToken;
import org.bachelor.web.json.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  @Autowired
  private UserServiceImpl userService;

  @Autowired
  private UserSysConfig userSysConfig;
  @Autowired
  private RsaSigner signer;

  /**
   * 根据code从用户系统获取accesstoken接口，生成的accesstoken和userid等信息，计算返回JWT
   * 
   * @param code
   * @return
   */
  @RequestMapping(value = "/accesstoken",method = RequestMethod.GET)
  public ResponseEntity<JsonResponse> accesstoken(@RequestParam String code, HttpServletRequest request,
      HttpServletResponse response) {
    String userinfo = "";
    if (StringUtils.isEmpty(code)) {
      // 返回提示，client重新引导用户登录
      logger.info("code 为空,url: {} {}", request.getRequestURI(), request.getQueryString());
      // TODO 设置统一的状态，包装token
    }
    UpClient client = new UpClient(userSysConfig, request, response);
    try {
      userinfo = client.bindUserInfo(code);
    } catch (OAuth2Exception e) {
      e.printStackTrace();
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
    UpClientCertification upCC = (UpClientCertification) request.getSession()
        .getAttribute(UpClientConstant.SESSIONAUTHENTICATIONKEY);
    String accesstoken = upCC.getAccessToken();
    String refreshToken = upCC.getRefreshToken();


    JSONObject userObject = JSONObject.fromObject(userinfo);
    userObject.put("accesstoken", accesstoken);

    // 有效期保持与用户系统一致
    Date expTime = parseExpireTime(upCC.getExpiresTime());
    long currentTime = new Date().getTime();
    // 存储refreshToken为token有效期的2倍
    userService.saveRefreshToken((String)userObject.get("account"), refreshToken, 2 * (expTime.getTime() - currentTime));
    userObject.put("exp", expTime.getTime());
    userObject.put("iat", currentTime);

    userObject.put("iss", ""); // jwt签发者
    userObject.put("sub", userObject.get("account")); // jwt所面向的用户
    userObject.put("aud", ""); // 接收jwt的一方
    userObject.put("nbf", ""); // 接收jwt的一方
    userObject.put("jti", ""); // jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击

    userObject.put("user_name", userObject.get("username"));
    userObject.put("user_code", userObject.get("account"));

    JSONObject orgInfo = getOrgInfo(accesstoken,userObject.get("userId").toString());
    if(orgInfo != null){
      userObject.put("org_id", orgInfo.get("orgId"));
      userObject.put("org_name", orgInfo.get("orgName"));
      userObject.put("dept_id", orgInfo.get("deptId"));
      userObject.put("dept_name", orgInfo.get("deptName"));
    }


    logger.info(userObject.toString());
    Jwt jwt = JwtHelper.encode(userObject.toString(), signer);

    JWTToken token = new JWTToken();
    token.setToken(jwt.getEncoded());
    return JsonResponse.createHttpEntity(token,HttpStatus.OK);
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

  @RequestMapping(value = "/refreshToken",method = RequestMethod.POST)
  public ResponseEntity<JsonResponse> refreshToken(@RequestHeader(AuthConstant.HTTP_HEADER_TOKEN_KEY) String authorization,
                                                   HttpServletRequest request, HttpServletResponse response) {
    logger.info("user refreshToken with token {}",authorization);
    String account = getUserCode(authorization);
    if(StringUtils.isBlank(account)){
      throw new BusinessException("invalid accesstoken");
    }
    UpClient client = new UpClient(userSysConfig, request, response);


    String userinfo = "";
    userinfo = client.refreshAccessToken(userService.getCurrentRefreshToken(account));
    UpClientCertification upCC = (UpClientCertification) request.getSession()
            .getAttribute(UpClientConstant.SESSIONAUTHENTICATIONKEY);
    String accesstoken = upCC.getAccessToken();
    String refreshToken = upCC.getRefreshToken();
    JSONObject userObject = JSONObject.fromObject(userinfo);
    userObject.put("accesstoken", accesstoken);

    // 有效期保持与用户系统一致
    Date expTime = parseExpireTime(upCC.getExpiresTime());
    long currentTime = new Date().getTime();
    // 存储refreshToken为token有效期的2倍
    userService.saveRefreshToken((String)userObject.get("account"), refreshToken, 2 * (expTime.getTime() - currentTime));
    userObject.put("exp", expTime.getTime());
    userObject.put("iat", currentTime);

    userObject.put("iss", ""); // jwt签发者
    userObject.put("sub", userObject.get("account")); // jwt所面向的用户
    userObject.put("aud", ""); // 接收jwt的一方
    userObject.put("nbf", ""); // 接收jwt的一方
    userObject.put("jti", ""); // jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击

    userObject.put("user_name", userObject.get("username"));
    userObject.put("user_code", userObject.get("account"));

    JSONObject orgInfo = getOrgInfo(accesstoken,userObject.get("userId").toString());
    if(orgInfo != null){
      userObject.put("org_id", orgInfo.get("orgId"));
      userObject.put("org_name", orgInfo.get("orgName"));
      userObject.put("dept_id", orgInfo.get("deptId"));
      userObject.put("dept_name", orgInfo.get("deptName"));
    }
    logger.info(userObject.toString());
    Jwt jwt = JwtHelper.encode(userObject.toString(), signer);
    JWTToken token = new JWTToken();
    token.setToken(jwt.getEncoded());
    return JsonResponse.createHttpEntity(token,HttpStatus.OK);
  }
  /**
   * <p>getOrgInfo : 获取指定用户所在机构信息</p>
   * @param accessToken 用户token
   * @return
   */
  private JSONObject getOrgInfo(String accessToken,String userId) {
    SignSecurityOAuthClient oAuthClient = new SignSecurityOAuthClient(new URLConnectionClient());
    OAuthResourceClientRequest orgInfoRequest =
            new OAuthResourceClientRequest("userInfoDetail", userSysConfig.getUserInfoDetail() , OAuth.HttpMethod.GET);
    orgInfoRequest.setAccessToken(accessToken);
    orgInfoRequest.setParameter("clientId", userSysConfig.getClientId());
    orgInfoRequest.setParameter("userId",userId);

    OAuthResourceResponse resourceResponse = null;
    try {
      resourceResponse = oAuthClient.resource(orgInfoRequest, OAuthResourceResponse.class);
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new SystemException(e.getCause());
    }
    String userInfoDetailInfo = resourceResponse.getBody();
    // logger.info(userInfoDetailInfo);
    JSONObject userDetailObject = JSONObject.fromObject(userInfoDetailInfo);

    JSONArray orgArray = userDetailObject.getJSONArray("rows");
    if(orgArray != null && orgArray.size()> 0){
      return (JSONObject) orgArray.get(0);
    }
    return null;
  }


  /**
   * 退出当前系统的应用状态，单点登录的状态由app client处理 解析accesstoken，将退出登录的token存入redis，并设置有效期为token的到期时间
   * 
   * @return 当前系统退出登录处理结果
   */
  @RequestMapping(value = "/logout",method = RequestMethod.PUT)
  public ResponseEntity<JsonResponse> logout(@RequestHeader(AuthConstant.HTTP_HEADER_TOKEN_KEY) String token) {
    logger.info("user logout with token {}",token);
    String userCode = getUserCode(token);
    logger.info("user logout with code {}",userCode);
    userService.logout(userCode);
    return JsonResponse.createHttpEntity("logout success",HttpStatus.OK);
  }

  private String getUserCode(String token) {
    String userCode;
    try{
      Jwt jwt = JwtHelper.decode(token);
      JSONObject userInfo = JSONObject.fromObject(jwt.getClaims());
      userCode = (String)userInfo.get("user_code");
    }catch (Exception e){
      logger.error(e.getMessage());
      throw new BusinessException(e);
    }
    return userCode;
  }

}
