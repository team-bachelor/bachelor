package cn.org.bachelor.microservice.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang3.StringUtils;
import cn.org.bachelor.common.auth.AuthConstant;
import cn.org.bachelor.common.auth.token.JwtToken;
import cn.org.bachelor.core.exception.BusinessException;
import cn.org.bachelor.web.json.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Map;

/**
 * @描述: 用于权限拦截的拦截器
 * @创建人: liuzhuo
 * @创建时间: 2018/10/18
 */
@Order(-1)
@Component
public class CheckAuthPreFilter implements GlobalFilter {
    private static final Logger logger = LoggerFactory.getLogger(CheckAuthPreFilter.class);

//    @Autowired
//    private AuthorizeService authorizeService;

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
        String token = headers.get(AuthConstant.HTTP_HEADER_TOKEN_KEY) == null ? "" :
                headers.get(AuthConstant.HTTP_HEADER_TOKEN_KEY).get(0);
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
        ServerHttpRequest host = request;
        boolean pass = true;
        boolean isValidToekn = false;
        if (token != null && !"".equals(token)) {
            JwtToken jwtToken = null;
            try {
                jwtToken = JwtToken.decode(token);
            } catch (IllegalArgumentException e) {
                throw new BusinessException("invalid_authorization");
            }
            isValidToekn = isValidToken(jwtToken);
            if (jwtToken != null && isValidToekn) {
                //TODO 安全性有待提高
                Map<String, Object> tokenClaims = jwtToken.getClaims();
                host = request.mutate()
                        .header(JwtToken.PayloadKey.ORG_ID,
                                getTokenClaim(tokenClaims, JwtToken.PayloadKey.ORG_ID, false))
                        .header(JwtToken.PayloadKey.ORG_CODE,
                                getTokenClaim(tokenClaims, JwtToken.PayloadKey.ORG_CODE, false))
                        .header(JwtToken.PayloadKey.USER_CODE,
                                getTokenClaim(tokenClaims, JwtToken.PayloadKey.USER_CODE, false))
                        .header(JwtToken.PayloadKey.USER_ID,
                                getTokenClaim(tokenClaims, JwtToken.PayloadKey.USER_ID, false))
                        .header(JwtToken.PayloadKey.USER_NAME,
                                getTokenClaim(tokenClaims, JwtToken.PayloadKey.USER_NAME, true))
                        .header(JwtToken.PayloadKey.DEPT_ID,
                                getTokenClaim(tokenClaims, JwtToken.PayloadKey.DEPT_ID, false))
                        .header(JwtToken.PayloadKey.DEPT_NAME,
                                getTokenClaim(tokenClaims, JwtToken.PayloadKey.DEPT_NAME, true))
                        .header(JwtToken.PayloadKey.ORG_NAME,
                                getTokenClaim(tokenClaims, JwtToken.PayloadKey.ORG_NAME, true))
                        .header(JwtToken.PayloadKey.ACCESS_TOKEN,
                                getTokenClaim(tokenClaims, JwtToken.PayloadKey.ACCESS_TOKEN, false))
                        .build();
            } else {
                pass = false;
            }
        }
        //开始验证系统权限
//        String permCode = request.getMethodValue().toLowerCase() + ":" + path.value();
//        boolean pass = authorizeService.isAuthorized(permCode, user);


//        logger.info("access user=[" + user + "], is authorized=[" + pass + "]");
//        //如果有权限,则可以访问
        if (pass) {
            //将现在的request 变成 change对象
            ServerWebExchange build = exchange.mutate().request(host).build();
            return chain.filter(build);

        }
        //如果没权限则返回401
        ServerHttpResponse response = exchange.getResponse();
        JsonResponse<String> jr = null;
        if(isValidToekn){
            response.setStatusCode(HttpStatus.LOCKED);
            //构造返回内容的body
            jr = new JsonResponse();
            jr.setCode(String.valueOf(HttpStatus.LOCKED.value()));
            jr.setData("INVALID_TOKEN");
            jr.setMsg("INVALID_TOKEN");
            jr.setStatus(cn.org.bachelor.web.json.ResponseStatus.BIZ_ERR);
        }else {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //构造返回内容的body
            jr = new JsonResponse();
            jr.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
            jr.setData("SYSTEM_UNAUTHORIZED");
            jr.setMsg("SYSTEM_UNAUTHORIZED");
            jr.setStatus(cn.org.bachelor.web.json.ResponseStatus.BIZ_ERR);
        }
        response.getHeaders().set("Location", path.pathWithinApplication().value());
        ObjectMapper mapper = new ObjectMapper();
        try {
            String responseString = mapper.writeValueAsString(jr);
            byte[] responseBytes = responseString.getBytes(StandardCharsets.UTF_8);
            DataBuffer responseBuffer = response.bufferFactory().wrap(responseBytes);
            return response.writeWith(Flux.just(responseBuffer));
        } catch (JsonProcessingException e) {
            logger.info("JsonProcessingException", e);
        }
        return response.setComplete();

//            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//                logger.info("third post filter");
//            }));
    }

    private String getTokenClaim(Map<String, Object> tokenClaims, String payload, boolean urlencode) {
        String value = tokenClaims.getOrDefault(payload, "").toString();
        if (urlencode) {
            value = urlEncode(value);
        }
        return value;
    }

    private boolean isValidToken(JwtToken jwtToken) {
        return jwtToken.getExp() > new Date().getTime();
    }

    private String urlEncode(String param) {
        try {
            return StringUtils.isBlank(param) ? StringUtils.EMPTY : URLEncoder.encode(param, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String serverExceptionHandler(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return ex.getMessage() + "123";
    }
}
