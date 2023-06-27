package cn.org.bachelor.iam.idm.controller;

import cn.org.bachelor.iam.IamConfiguration;
import cn.org.bachelor.iam.IamConstant;
import cn.org.bachelor.iam.idm.service.IdmService;
import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.iam.utils.StringUtils;
import cn.org.bachelor.web.json.JsonResponse;
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
import java.util.HashMap;
import java.util.Map;

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
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "授权码", paramType = "query", required = true)})
    @RequestMapping(value = "/accesstoken", method = RequestMethod.GET)
    public ResponseEntity<JsonResponse> accesstoken(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(code)) {
            // 返回提示，client重新引导用户登录
            logger.info("code 为空,url: {} {}", request.getRequestURI(), request.getQueryString());
            // TODO 设置统一的状态，包装token
        }
        JwtToken token = idmService.getAccessToken(request, response, code);
        return JsonResponse.createHttpEntity(token.generate(authConfig.getPrivateKey()), HttpStatus.OK);
    }

    public static final String REFRESH_TOKEN = "refresh_token";

    /**
     * @param authorization header中的jwt
     * @param request       request
     * @param response      response
     * @return 刷新后的JWT
     * @描述 刷新JWT
     */
    @ApiOperation(value = "刷新JWT")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
    public ResponseEntity<JsonResponse> refreshToken(@RequestHeader(IamConstant.HTTP_HEADER_TOKEN_KEY) String authorization, HttpServletRequest request, HttpServletResponse response) {
        JwtToken jwt = JwtToken.decode(authorization);
        String refreshToken = jwt.getClaims().containsKey(REFRESH_TOKEN) ? jwt.getClaims().get(REFRESH_TOKEN).toString() : "";
        JwtToken token = idmService.refreshAccessToken(request, response, refreshToken);
        Map<String, String> tokenMap = new HashMap<String, String>(1);
        tokenMap.put("token", token.generate(authConfig.getPrivateKey()));
        return JsonResponse.createHttpEntity(tokenMap, HttpStatus.OK);
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
