package cn.org.bachelor.microservice.console.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cloud.gateway.route.RouteDefinition;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2019/1/2
 */
public class RouteVo {
    @JsonProperty("route_id")
    private String routeId;

    @JsonProperty("route_definition")
    private RouteDefinition routeDefinition;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("order")
    private int order;

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public RouteDefinition getRouteDefinition() {
        return routeDefinition;
    }

    public void setRouteDefinition(RouteDefinition routeDefinition) {
        this.routeDefinition = routeDefinition;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
