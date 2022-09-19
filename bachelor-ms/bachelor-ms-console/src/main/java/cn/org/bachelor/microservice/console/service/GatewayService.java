package cn.org.bachelor.microservice.console.service;

import cn.org.bachelor.microservice.console.utils.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.netflix.appinfo.InstanceInfo;
import cn.org.bachelor.microservice.console.vo.RateLimitVo;
import cn.org.bachelor.microservice.console.vo.RouteVo;
import cn.org.bachelor.microservice.console.vo.ServiceVo;
import cn.org.bachelor.exception.BusinessException;
import cn.org.bachelor.exception.SystemException;
import cn.org.bachelor.web.json.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @描述: 统一网关管理
 * @创建人: liuzhuo
 * @创建时间: 2018/12/29
 */
@Service
public class GatewayService {

    private static final Logger logger = LoggerFactory.getLogger(GatewayService.class);

    @Autowired
    private DiscoveryService discoveryService;
    private Map<String, Map<String, RouteDefinition>> routeCache = new HashMap<>();

    @Autowired
    public GatewayService() {

    }

    public List<InstanceInfo> getGateways() {
        ServiceVo service = discoveryService.getService("gateway");
        return service.getInstances();
    }

    public RateLimitVo getDefaultRateLimit(String url) {

        HttpEntity<String> entity = HttpUtil.getRestTemplate().getForEntity(url + "route/defaultFilter", String.class);
        if (((ResponseEntity<String>) entity).getStatusCode().is2xxSuccessful()) {
            String body = entity.getBody();
            try {
                JsonResponse<List<FilterDefinition>> list = HttpUtil.getJsonMapper().readValue(body, new TypeReference<JsonResponse<List<FilterDefinition>>>() {
                });
                AtomicReference<FilterDefinition> rlFilter = new AtomicReference<>(null);
                list.getData().forEach(r -> {
                    if (r.getName().equals("RequestRateLimiter")) {
                        rlFilter.set(r);
                    }
                });
                if (rlFilter.get() == null) {
                    return null;
                }
                Map<String, String> args = rlFilter.get().getArgs();
                RateLimitVo result = new RateLimitVo();
                args.keySet().forEach(key -> {
                    if (key.endsWith("replenish-rate")) {
                        result.setReplenishRate(Integer.parseInt(args.get(key)));
                    } else if (key.endsWith("burst-capacity")) {
                        result.setBurstCapacity(Integer.parseInt(args.get(key)));
                    } else if (key.equals("keyResolver")) {
                        result.setKeyResolver(args.get(key));
                    }
                });
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private InstanceInfo getGatewayInstance(String instanceId) {
        List<InstanceInfo> ins = getGateways();
        AtomicReference<InstanceInfo> info = new AtomicReference<>(null);
        if (ins != null && ins.size() > 0) {
            ins.forEach(i -> {
                if (i.getInstanceId().equals(instanceId)) {
                    info.set(i);
                }
            });
        }
        return info.get();
    }

    public Collection<RouteDefinition> getRoutes(String url) {
        HttpEntity<String> entity = HttpUtil.getRestTemplate().getForEntity(url + "actuator/gateway/routes", String.class);
        if (((ResponseEntity<String>) entity).getStatusCode().is2xxSuccessful()) {
            String body = entity.getBody();
            try {
                List<RouteVo> list = HttpUtil.getJsonMapper().readValue(body, new TypeReference<List<RouteVo>>() {
                });
                //List<RouteDefinition> result = new ArrayList<>(list.size());
                //cache the gateway
                Map<String, RouteDefinition> rdInGateway = new HashMap<>(list.size());
                list.forEach(r -> {
                    //result.add(r.getRouteDefinition());
                    if(rdInGateway.containsKey(r.getRouteId())){
                        rdInGateway.replace(r.getRouteId(), r.getRouteDefinition());
                    }else {
                        rdInGateway.put(r.getRouteId(), r.getRouteDefinition());
                    }
                });

                routeCache.remove(url);
                routeCache.put(url, rdInGateway);
                return rdInGateway.values();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void updateRateLimit(String url, String routeId, RateLimitVo rateLimit) {
        RouteDefinition def = getRouteDef(url, routeId);
        List<FilterDefinition> filters = def.getFilters();
        AtomicReference<FilterDefinition> fd = new AtomicReference<>();
        filters.forEach(f -> {
            if (RATE_LIMITER_NAME.equals(f.getName())) {
                fd.set(f);
            }
        });
        if (fd.get() != null) {
            filters.remove(fd.get());
        }
        filters.add(makeRateLimitFilter(rateLimit));
        updateRoute(url, def);
    }

    private void updateRoute(String url, RouteDefinition def) {
        try {
            HttpEntity<String> entity = HttpUtil.getRestTemplate().postForEntity(url + "route/update",
                    HttpUtil.getJsonRequesBody(HttpUtil.getJsonMapper().writeValueAsString(def)), String.class);
            HttpStatus status = ((ResponseEntity<String>) entity).getStatusCode();
            if (status.is4xxClientError() || status.is5xxServerError()) {
                throw new BusinessException("ROUTE_UPDATE_FAILURE", entity.getBody());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new SystemException(e);
        }

    }

    private static final String RATE_LIMITER_NAME = "RequestRateLimiter";

    private FilterDefinition makeRateLimitFilter(RateLimitVo rateLimit) {
        FilterDefinition def = new FilterDefinition();
        def.setName(RATE_LIMITER_NAME);
        def.setArgs(new HashMap<>());
        def.getArgs().put("keyResolver", rateLimit.getKeyResolver());
        def.getArgs().put("in-memory-rate-limiter.burst-capacity", String.valueOf(rateLimit.getBurstCapacity()));
        def.getArgs().put("in-memory-rate-limiter.replenish-rate", String.valueOf(rateLimit.getReplenishRate()));
        return def;
    }

    private RouteDefinition getRouteDef(String url, String routeId) {
        // get from cache
        RouteDefinition rd = getRouteDefinitionFromCache(url, routeId);
        if (rd != null) return rd;
        getRoutes(url);
        rd = getRouteDefinitionFromCache(url, routeId);
        return rd;
    }

    private RouteDefinition getRouteDefinitionFromCache(String url, String routeId) {
        Map<String, RouteDefinition> rds = routeCache.get(url);
        if (rds != null) {
            RouteDefinition rd = rds.get(routeId);
            if (rd != null) {
                return rd;
            }
        }
        return null;
    }

}
