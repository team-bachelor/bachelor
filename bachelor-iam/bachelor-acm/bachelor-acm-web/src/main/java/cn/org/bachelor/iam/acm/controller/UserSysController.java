package cn.org.bachelor.iam.acm.controller;

import cn.org.bachelor.core.exception.BusinessException;
import cn.org.bachelor.iam.acm.AuthConstant;
import cn.org.bachelor.iam.acm.AuthValueHolderService;
import cn.org.bachelor.iam.acm.config.AuthenticationConfig;
import cn.org.bachelor.iam.acm.service.DefaultUserSysService;
import cn.org.bachelor.iam.acm.token.JwtToken;
import cn.org.bachelor.iam.acm.vo.AppVo;
import cn.org.bachelor.iam.acm.vo.UserSysParam;
import cn.org.bachelor.iam.acm.vo.UserSysResult;
import cn.org.bachelor.iam.acm.vo.UserVo;
import cn.org.bachelor.iam.oauth2.client.OAuth2CientConfig;
import cn.org.bachelor.iam.oauth2.client.OAuth2Client;
import cn.org.bachelor.iam.oauth2.client.model.OAuth2ClientCertification;
import cn.org.bachelor.iam.oauth2.client.util.ClientConstant;
import cn.org.bachelor.iam.oauth2.exception.OAuthBusinessException;
import cn.org.bachelor.iam.oauth2.utils.JSONParser;
import cn.org.bachelor.web.json.JsonResponse;
import cn.org.bachelor.web.json.ResponseStatus;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2019/4/2
 */
@RestController
/**
 * 将原auth-login合并，原/user/接口一并归到/user_sys/
 * /user/accesstoken  -> /user_sys/accesstoken
 * /user/refreshToken -> /user_sys/refreshToken
 * /user/logout       -> /user_sys/logout
 */
@RequestMapping("/idm")
public class UserSysController {

    private static final Logger logger = LoggerFactory.getLogger(UserSysController.class);
    @Autowired
    private DefaultUserSysService userSysService;
    @Autowired
    private AuthValueHolderService valueHolder;
    @Autowired
    private AuthenticationConfig authConfig;

    @ApiOperation(value = "根据当前clientID查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "组织机构编码", paramType = "path", required = true),
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query", required = false),
            @ApiImplicitParam(name = "userName", value = "用户名（模糊匹配）", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页的记录数", paramType = "query", required = false),
            @ApiImplicitParam(name = "page", value = "当前页数", paramType = "query", required = false)
    })
    @RequestMapping(value = "/users/{orgId}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUsers(@PathVariable String orgId, String deptId, String userName, Integer pageSize, Integer page) {
        UserSysParam param = new UserSysParam();
        param.setOrgId(orgId);
        param.setDeptId(deptId);
        param.setUserName(userName);
        param.setPage(String.valueOf(page));
        param.setPageSize(String.valueOf(pageSize));
        UserSysResult<List<UserVo>> result = userSysService.findUsers(param);
        JsonResponse jr = new JsonResponse(result.getRows());
        jr.setStatus(ResponseStatus.OK);
        HttpHeaders headers = new HttpHeaders();
        headers.add("total", String.valueOf(result.getTotal()));

        //ResponseEntity response = JsonResponse.createHttpEntity(result.getRows());

        //response.getHeaders().add("total", String.valueOf(result.getTotal()));
        return new ResponseEntity<JsonResponse>(jr, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "根据当前clientID查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "组织机构编码", paramType = "query", required = true),
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query", required = false),
            @ApiImplicitParam(name = "keyWord", value = "查询关键词，同时用于匹配用户名称和编码", paramType = "query", required = false)
    })
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUsers2(String orgId, String deptId, String keyWord) {
        return JsonResponse.createHttpEntity(userSysService.findUsers(orgId, deptId, keyWord));
    }

    /**
     * 查询用户
     *
     * @param userID 用户ID
     * @return 返回用户列表
     */
    @ApiOperation(value = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userID", value = "用户ID", paramType = "path", required = true)
    })
    @RequestMapping(value = "/user/{userID}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUser(@PathVariable String userID) {
        return JsonResponse.createHttpEntity(userSysService.findUser(null, userID, null));
    }

    /**
     * 查询用户
     *
     * @param userCode 用户编码
     * @param orgId    机构ID
     * @return 返回用户列表
     */
    @ApiOperation(value = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode", value = "用户编码", paramType = "query", required = true),
            @ApiImplicitParam(name = "orgId", value = "机构ID", paramType = "query", required = true)
    })
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUserByCode(String userCode, String orgId) {
        return JsonResponse.createHttpEntity(userSysService.findUser(orgId, null, userCode));
    }

    @ApiOperation(value = "获得机构下部门列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "父机构ID", paramType = "query", required = true),
            @ApiImplicitParam(name = "tree", value = "是否要树状结构", paramType = "query", required = false, defaultValue = "false"),
            @ApiImplicitParam(name = "deptId", value = "父部门ID", paramType = "query", required = false)
    })
    @RequestMapping(value = "/depts", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getDepts(String orgId, String deptId, boolean tree) {
        List permg = userSysService.findDepts(orgId, deptId, tree);
        return JsonResponse.createHttpEntity(permg);
    }

    @ApiOperation(value = "获得机构列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "机构ID", paramType = "query", required = true),
            @ApiImplicitParam(name = "code", value = "机构编码", paramType = "query", required = true)
    })
    @RequestMapping(value = "/orgs", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getOrgs() {
        List permg = userSysService.findAllOrgs();
        return JsonResponse.createHttpEntity(permg);
    }

    @ApiOperation(value = "根据用户ID获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIds", value = "用户ID（逗号分隔）", paramType = "query", required = true)
    })
    @RequestMapping(value = "/users/ids", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUserByIds(String userIds) {
        List<UserVo> userVoList = userSysService.findUserByIds(userIds);
        return JsonResponse.createHttpEntity(userVoList);
    }


    @Value("${spring.application.portal-code}")
    private String portalCode;
    @ApiOperation(value = "获取跳转会portal的地址")
    @RequestMapping(value = "/app/portal/url", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getPortalURL() {
        AppVo app = userSysService.findAppsByCode(portalCode);
        return JsonResponse.createHttpEntity(app == null ? "" : app.getUrl());
    }

    @ApiOperation(value = "根据app的编码获取app详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appCode", value = "app的编码", paramType = "path", required = true)
    })
    @RequestMapping(value = "/app/{appCode}", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getAppByCode(@PathVariable String appCode) {
        AppVo app = userSysService.findAppsByCode(appCode);
        return JsonResponse.createHttpEntity(app);
    }

    @ApiOperation(value = "根据用户ID获取该用户可以登录的app")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "path", required = true)
    })
    @RequestMapping(value = "/user/{userId}/apps", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getAppByUserId(@PathVariable String userId) {
        List<AppVo> apps = userSysService.findAppsByUserId(userId);
        return JsonResponse.createHttpEntity(apps);
    }

    /**
     * 查询用户
     *
     * @return 返回用户列表
     */
    @ApiOperation(value = "根据当前clientID查询用户")
    @RequestMapping(value = "/usersInClient", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query", required = false),
            @ApiImplicitParam(name = "deptName", value = "部门名（模糊匹配）", paramType = "query", required = false),
            @ApiImplicitParam(name = "userName", value = "用户名（模糊匹配）", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页的记录数", paramType = "query", required = false),
            @ApiImplicitParam(name = "page", value = "当前页数", paramType = "query", required = false)
    })
    public HttpEntity<JsonResponse> getUsersByClient(String deptId, String deptName, String userName, Integer pageSize, Integer page) {
        UserSysParam param = new UserSysParam();
        param.setDeptId(deptId);
        param.setUserName(userName);
        param.setDeptName(deptName);
        param.setPage(page == null ? null : String.valueOf(page));
        param.setPageSize(page == null ? null : String.valueOf(pageSize));
        param.setClientId(valueHolder.getClientID());
        return JsonResponse.createHttpEntity(userSysService.findUserByClientID(param));
    }


    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private OAuth2CientConfig clientConfig;
    //    @Autowired
//    private RsaSigner signer;

    /**
     * 根据code从用户系统获取accesstoken接口，生成的accesstoken和userid等信息，计算返回JWT
     *
     * @param code
     * @return
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
     * 退出当前系统的应用状态，单点登录的状态由app client处理 解析accesstoken，将退出登录的token存入redis，并设置有效期为token的到期时间
     *
     * @return 当前系统退出登录处理结果
     */
    @ApiOperation(value = "为当前用户登出系统")
    @RequestMapping(value = "/logout", method = RequestMethod.PUT)
    public ResponseEntity<JsonResponse> logout(@RequestHeader(AuthConstant.HTTP_HEADER_TOKEN_KEY) String token) {
        logger.info("user logout with token {}", token);
        String userCode = getUserCode(token);
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
//        Jwt jwt = JwtHelper.encode(userObject.toString(), signer);
//        return jwt.getEncoded();
    }
    /**
     * <p>getOrgInfo : 获取指定用户所在机构信息</p>
     *
     * @return
     */
    private UserVo getUserDetail(String userId) {
        List<UserVo> users = userSysService.findUserDetail(userId);
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
