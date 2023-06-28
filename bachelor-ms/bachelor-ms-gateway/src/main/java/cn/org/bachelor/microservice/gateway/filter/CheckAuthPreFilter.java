package cn.org.bachelor.microservice.gateway.filter;

import cn.org.bachelor.iam.IamConstant;
import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.iam.utils.JwtUtil;
import cn.org.bachelor.microservice.gateway.service.ITenantIdProvider;
import cn.org.bachelor.web.json.JsonResponse;
import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cn.org.bachelor.iam.utils.JwtUtil.getAndRemoveTokenClaim;
import static cn.org.bachelor.iam.utils.JwtUtil.getAndTokenClaim;

/**
 * @描述: 用于权限拦截的拦截器
 * @创建人: liuzhuo
 * @创建时间: 2018/10/18
 */
@Order(-1)
@Component
public class CheckAuthPreFilter implements GlobalFilter {
    private static final Logger logger = LoggerFactory.getLogger(CheckAuthPreFilter.class);

    @Autowired(required = false)
    private ITenantIdProvider tenantIdProvider;


    /**
     * @Description 访问认证中心判断权限
     * @Author liuzhuo
     * @Date 2018/10/18 13:58
     * @Param
     * @Return
     */
    @Override
    @HystrixCommand(commandKey = "authPre")
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("auth checking pre filter");
        ServerHttpRequest request = exchange.getRequest();

        //获取被访问的路径
        RequestPath path = request.getPath();
        logger.info("access path=[" + path.toString() + "]");

        //检查权限
        //获取头部的token
        HttpHeaders headers = request.getHeaders();
        //List<String> tokens = headers.get(AuthConstant.HTTP_HEADER_TOKEN_KEY);
        String token = headers.get(IamConstant.HTTP_HEADER_TOKEN_KEY) == null ? "" :
                headers.get(IamConstant.HTTP_HEADER_TOKEN_KEY).get(0);
//        if (tokens != null)
//            for (String t : tokens) {
//                if (!t.isEmpty()) {
//                    token = t;
//                    break;
//                }
//            }
        logger.info("access with token=[" + token + "]");
        //解析token获取用户信息
//        String user = null;
        if (token == null || "".equals(token)) {
            return chain.filter(exchange);
        }

        ServerHttpRequest host = request;
        boolean pass = true;
        boolean isValidToken = false;
        JwtToken jwtToken = null;
        try {
            jwtToken = JwtToken.decode(token);
        } catch (IllegalArgumentException e) {
//            throw new BusinessException("invalid_authorization");
            logger.warn("invalid_authorization", token);
        }
        if (token != null && !"".equals(token)) {
            isValidToken = JwtUtil.isValidToken(jwtToken);
            if (jwtToken != null && isValidToken) {
                //TODO 安全性有待提高
                Map<String, Object> tokenClaims = new HashMap<>(jwtToken.getClaims().size());
                tokenClaims.putAll(jwtToken.getClaims());
                ServerHttpRequest.Builder builder = request.mutate()
                        .header(JwtToken.PayloadKey.ORG_ID,
                                getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.ORG_ID, false))
                        .header(JwtToken.PayloadKey.ORG_CODE,
                                getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.ORG_CODE, false))
                        .header(JwtToken.PayloadKey.USER_CODE,
                                getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.USER_CODE, false))
                        .header(JwtToken.PayloadKey.USER_ID,
                                getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.USER_ID, false))
                        .header(JwtToken.PayloadKey.USER_NAME,
                                getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.USER_NAME, true))
                        .header(JwtToken.PayloadKey.DEPT_ID,
                                getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.DEPT_ID, false))
                        .header(JwtToken.PayloadKey.DEPT_NAME,
                                getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.DEPT_NAME, true))
                        .header(JwtToken.PayloadKey.ORG_NAME,
                                getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.ORG_NAME, true))
                        .header(JwtToken.PayloadKey.ACCESS_TOKEN,
                                getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.ACCESS_TOKEN, false))
                        .header(JwtToken.PayloadKey.VER,
                                getAndRemoveTokenClaim(tokenClaims, JwtToken.PayloadKey.VER, false));

                tokenClaims.keySet().forEach(key -> {
                    builder.header(key, getAndTokenClaim(tokenClaims, key, false, false));
                });

                host = builder.build();
                logger.info("build header complete: " + JSONObject.toJSONString(request.getHeaders()));
            } else {
                pass = false;
            }
        }
        //开始验证系统权限
//        String permCode = request.getMethodValue().toLowerCase() + ":" + path.value();
//        boolean pass = authorizeService.isAuthorized(permCode, user);


//        logger.info("access user=[" + user + "], is authorized=[" + pass + "]");
//
        if (tenantIdProvider != null) {
            host.mutate().header(JwtToken.PayloadKey.TENANT_ID, tenantIdProvider.getTenantId(request));
        }
        // 如果有权限,则可以访问
        if (pass) {
            //将现在的request 变成 change对象
            ServerWebExchange build = exchange.mutate().request(host).build();
            return chain.filter(build);

        }
        //如果没权限则返回401
        ServerHttpResponse response = exchange.getResponse();
        JsonResponse<String> jr = null;
        if (isValidToken) {
            response.setStatusCode(HttpStatus.LOCKED);
            //构造返回内容的body
            jr = new JsonResponse();
            jr.setCode(String.valueOf(HttpStatus.LOCKED.value()));
            jr.setData("INVALID_TOKEN");
            jr.setMsg("INVALID_TOKEN");
            jr.setStatus(cn.org.bachelor.web.json.ResponseStatus.BIZ_ERR);
        } else {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //构造返回内容的body
            jr = new JsonResponse();
            jr.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
            jr.setData("SYSTEM_UNAUTHORIZED");
            jr.setMsg("SYSTEM_UNAUTHORIZED");
            jr.setStatus(cn.org.bachelor.web.json.ResponseStatus.BIZ_ERR);
        }
        response.getHeaders().set("Location", path.pathWithinApplication().value());
        String responseString = JSONObject.toJSONString(jr);
        byte[] responseBytes = responseString.getBytes(StandardCharsets.UTF_8);
        DataBuffer responseBuffer = response.bufferFactory().wrap(responseBytes);
        return response.writeWith(Flux.just(responseBuffer));
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String serverExceptionHandler(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return ex.getMessage();
    }
}
