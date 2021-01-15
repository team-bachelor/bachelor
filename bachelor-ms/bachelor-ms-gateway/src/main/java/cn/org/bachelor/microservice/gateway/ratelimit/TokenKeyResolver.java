package cn.org.bachelor.microservice.gateway.ratelimit;

import cn.org.bachelor.common.auth.token.JwtToken;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2019/1/4
 */
public class TokenKeyResolver implements KeyResolver {

    public static final String BEAN_NAME = "tokenKeyResolver";
    public static final String DEFAULT_TOKEN = "default_token";

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        List<String> tokens = exchange.getRequest().getHeaders().get(JwtToken.PayloadKey.ACCESS_TOKEN);
        String token = DEFAULT_TOKEN;
        if (tokens != null && tokens.size() > 0){
            token = tokens.get(0);
        }
        return Mono.just(token);
    }
}
