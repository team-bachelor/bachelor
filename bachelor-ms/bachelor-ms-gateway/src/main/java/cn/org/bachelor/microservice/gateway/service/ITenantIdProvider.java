package cn.org.bachelor.microservice.gateway.service;

import org.springframework.http.server.reactive.ServerHttpRequest;

public interface ITenantIdProvider {
    String getTenantId(ServerHttpRequest request);
}
