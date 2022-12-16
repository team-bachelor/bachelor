package cn.org.bachelor.iam.idm.controller;

import cn.org.bachelor.context.IUser;
import cn.org.bachelor.exception.BusinessException;
import cn.org.bachelor.iam.IamConfiguration;
import cn.org.bachelor.iam.IamConstant;
import cn.org.bachelor.iam.idm.service.DefaultImSysService;
import cn.org.bachelor.iam.idm.service.IdmService;
import cn.org.bachelor.iam.oauth2.client.OAuth2CientConfig;
import cn.org.bachelor.iam.oauth2.client.OAuth2Client;
import cn.org.bachelor.iam.oauth2.client.model.OAuth2ClientCertification;
import cn.org.bachelor.iam.oauth2.client.util.ClientConstant;
import cn.org.bachelor.iam.oauth2.exception.OAuthBusinessException;
import cn.org.bachelor.iam.oauth2.utils.StringUtils;
import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.iam.vo.UserVo;
import cn.org.bachelor.service.ApplicationContextHolder;
import cn.org.bachelor.web.json.JsonResponse;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javafx.application.Application;
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
 * @author liuzhuo
 * @描述 认证服务相关接口
 * @创建时间 2019/4/2
 */
@RestController
/**
 * 将原auth-login合并，原/user/接口一并归到/idm/
 * /user/accesstoken  -> /idm/as/accesstoken
 * /user/refreshToken -> /idm/as/refreshToken
 * /user/logout       -> /idm/as/logout
 */
//@CrossOrigin
@RequestMapping("/idm/as")
public class IdmAsController {

    private static final Logger logger = LoggerFactory.getLogger(IdmAsController.class);

    @Autowired
    private IdmService idmService;

    @Autowired
    private OAuth2CientConfig clientConfig;


    /**
     * @param code     授权码
     * @param request  request
     * @param response response
     * @return 合并了astoken和用户基本信息的JWT
     * @描述 根据code从用户系统获取accesstoken接口，生成的accesstoken和userid等信息，计算返回JWT
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
        JSONObject user = null;
        try {
            user = client.bindUserInfo(code);

        } catch (OAuthBusinessException e) {
            e.printStackTrace();
            return JsonResponse.createHttpEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        if(user != null) {
            user.putAll(idmService.getUserExtInfo(user));
        }
        Map<String, String> token = getToken(request, user);
        return JsonResponse.createHttpEntity(token, HttpStatus.OK);
    }

    /**
     * @param authorization header中的jwt
     * @param request       request
     * @param response      response
     * @return 刷新后的JWT
     * @描述 刷新JWT
     */
    @ApiOperation(value = "刷新JWT")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
    public ResponseEntity<JsonResponse> refreshToken(@RequestHeader(IamConstant.HTTP_HEADER_TOKEN_KEY) String authorization,
                                                     HttpServletRequest request, HttpServletResponse response) {
        String newToken = idmService.refreshAccessToken(authorization);
        OAuth2Client client = new OAuth2Client(clientConfig, request, response);
        String userinfo = client.refreshAccessToken(newToken);
        Map<String, String> token = getToken(request, userinfo);
        return JsonResponse.createHttpEntity(token, HttpStatus.OK);
    }
    private Map<String, String> getToken(HttpServletRequest request, String userinfo){
        return getToken(request, JSONObject.parseObject(userinfo));
    }
    private Map<String, String> getToken(HttpServletRequest request, JSONObject userinfo) {
        OAuth2ClientCertification upCC = (OAuth2ClientCertification) request.getSession()
                .getAttribute(ClientConstant.SESSION_AUTHENTICATION_KEY);
        String token_s = idmService.getJWTString(upCC, userinfo);
        Map<String, String> token = new HashMap<String, String>(1);
        token.put("token", token_s);
        return token;
    }

    /**
     * @param authorization header中的jwt
     * @return 当前系统退出登录处理结果
     * @描述 退出当前系统的应用状态，单点登录的状态由app client处理 解析accesstoken，将退出登录的token存入redis，并设置有效期为token的到期时间
     */
    @ApiOperation(value = "为当前用户登出系统")
    @RequestMapping(value = "/logout", method = RequestMethod.PUT)
    public ResponseEntity<JsonResponse> logout(@RequestHeader(IamConstant.HTTP_HEADER_TOKEN_KEY) String authorization) {
        idmService.logout(authorization);
        return JsonResponse.createHttpEntity("logout success", HttpStatus.OK);
    }
}
