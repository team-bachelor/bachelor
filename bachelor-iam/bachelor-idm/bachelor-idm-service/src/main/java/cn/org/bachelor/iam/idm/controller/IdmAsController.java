package cn.org.bachelor.iam.idm.controller;

import cn.org.bachelor.iam.IamConfiguration;
import cn.org.bachelor.iam.IamConstant;
import cn.org.bachelor.iam.credential.AbstractIamCredential;
import cn.org.bachelor.iam.idm.service.IdmService;
import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.iam.utils.StringUtils;
import cn.org.bachelor.iam.vo.UserVo;
import cn.org.bachelor.web.json.JsonResponse;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static cn.org.bachelor.iam.token.JwtToken.PayloadKey.USER_ID;

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
    private IamConfiguration authConfig;

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
            user = client.bindUser2Session(code);

        } catch (OAuthBusinessException e) {
            e.printStackTrace();
            return JsonResponse.createHttpEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        if(user != null) {
            user.putAll(idmService.getUserExtInfo(user));
        }
        Map<String, String> token = getJwtToken(request, user);
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
        String newToken = idmService.refreshAccessToken(JwtToken.decode(authorization));
        OAuth2Client client = new OAuth2Client(clientConfig, request, response);
        JSONObject userinfo = client.refreshAccessToken(newToken);
        String token = getJwtToken(request, userinfo);
        Map<String, String> tokenMap = new HashMap<String, String>(1);
        tokenMap.put("token", token);
        return JsonResponse.createHttpEntity(tokenMap, HttpStatus.OK);
    }

    private String getJwtToken(HttpServletRequest request, JSONObject userinfo) {
        AbstractIamCredential upCC = (AbstractIamCredential) request.getSession()
                .getAttribute(IamConstant.SESSION_AUTHENTICATION_KEY);
        String userId = userinfo.getString(USER_ID);
        UserVo userDetail = idmService.getUserDetail(userId);
        return JwtToken.generate(upCC, userinfo, authConfig.getPrivateKey(), userDetail);
    }

    /**
     * @param authorization header中的jwt
     * @return 当前系统退出登录处理结果
     * @描述 退出当前系统的应用状态，单点登录的状态由app client处理 解析accesstoken，将退出登录的token存入redis，并设置有效期为token的到期时间
     */
    @ApiOperation(value = "为当前用户登出系统")
    @RequestMapping(value = "/logout", method = RequestMethod.PUT)
    public ResponseEntity<JsonResponse> logout(@RequestHeader(IamConstant.HTTP_HEADER_TOKEN_KEY) String authorization) {
        JwtToken jwt = JwtToken.decode(authorization);
        idmService.logout(jwt);
        return JsonResponse.createHttpEntity("logout success", HttpStatus.OK);
    }
}
