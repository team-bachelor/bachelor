package cn.org.bachelor.microservice.gateway.ratelimit;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2019/1/4
 */
//@Component
public class IpAddressKeyResolver implements KeyResolver {

    public static final String BEAN_NAME = "ipAddressKeyResolver";

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }
}
