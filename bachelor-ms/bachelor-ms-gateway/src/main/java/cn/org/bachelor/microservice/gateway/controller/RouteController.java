package cn.org.bachelor.microservice.gateway.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import cn.org.bachelor.microservice.gateway.service.DynamicRouteService;
import cn.org.bachelor.microservice.gateway.vo.GatewayFilterDefinition;
import cn.org.bachelor.microservice.gateway.vo.GatewayPredicateDefinition;
import cn.org.bachelor.microservice.gateway.vo.GatewayRouteDefinition;
import cn.org.bachelor.web.json.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private DynamicRouteService dynamicRouteService;

    @Autowired
    private GatewayProperties gatewayProperties;

    /**
     * 增加路由
     *
     * @param gwdefinition
     * @return
     */
    @PostMapping("/add")
    @HystrixCommand(commandKey = "route")
    public ResponseEntity<JsonResponse> add(@RequestBody GatewayRouteDefinition gwdefinition) {
        RouteDefinition definition = assembleRouteDefinition(gwdefinition);
        this.dynamicRouteService.add(definition);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    @GetMapping("/defaultFilter")
    @HystrixCommand(commandKey = "route")
    public ResponseEntity<JsonResponse> getDefaultFilter() {
        return JsonResponse.createHttpEntity(gatewayProperties.getDefaultFilters());
    }

    @GetMapping("/delete/{id}")
    @HystrixCommand(commandKey = "route")
    public ResponseEntity<JsonResponse> delete(@PathVariable String id) {
        this.dynamicRouteService.delete(id);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    @PostMapping("/update")
    @HystrixCommand(commandKey = "route")
    public ResponseEntity<JsonResponse> update(@RequestBody GatewayRouteDefinition gwdefinition) {
        RouteDefinition definition = assembleRouteDefinition(gwdefinition);
        this.dynamicRouteService.update(definition);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    private RouteDefinition assembleRouteDefinition(GatewayRouteDefinition gwdefinition) {
        RouteDefinition definition = new RouteDefinition();
        List<PredicateDefinition> pdList = new ArrayList<>();
        definition.setId(gwdefinition.getId());
        List<GatewayPredicateDefinition> gatewayPredicateDefinitionList = gwdefinition.getPredicates();
        for (GatewayPredicateDefinition gpDefinition : gatewayPredicateDefinitionList) {
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setArgs(gpDefinition.getArgs());
            predicate.setName(gpDefinition.getName());
            pdList.add(predicate);
        }
        definition.setPredicates(pdList);
        List<FilterDefinition> pfList = new ArrayList<>();
        List<GatewayFilterDefinition> gatewayFilterDefinitionList = gwdefinition.getFilters();
        for (GatewayFilterDefinition gfDefinition : gatewayFilterDefinitionList) {
            FilterDefinition filter = new FilterDefinition();
            filter.setArgs(gfDefinition.getArgs());
            filter.setName(gfDefinition.getName());
            pfList.add(filter);
        }
        definition.setFilters(pfList);
        URI uri = null;
        if (gwdefinition.getUri().startsWith("lb://")) {
            uri = UriComponentsBuilder.fromUriString(gwdefinition.getUri()).build().toUri();
        } else {
            uri = UriComponentsBuilder.fromHttpUrl(gwdefinition.getUri()).build().toUri();
        }
        definition.setUri(uri);
        return definition;
    }

}
