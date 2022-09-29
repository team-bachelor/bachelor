package cn.org.bachelor.microservice.gateway.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.server.reactive.ServerHttpRequest;

@ConditionalOnProperty(prefix = "bachelor.gateway",
        name = {"tenantProvider"}, havingValue = "db")
public class DBTenantProvider implements ITenantIdProvider{
    @Override
    public String getTenantId(ServerHttpRequest request) {
        return "0";
    }
}
