/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package cn.org.bachelor.microservice.gateway.swagger2;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/***
 *
 * @since:swagger-bootstrap-ui 1.0
 * @author fsl
 * 2019/05/04 12:38
 */
@Slf4j
@Component
@Primary
@AllArgsConstructor
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

    private final RouteLocator routeLocator;
    private final RouteDefinitionRepository routeDefinitionRepository;
    private final GatewayProperties gatewayProperties;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
//        List<String> routes = new ArrayList<>();
//        List<Route> list = new ArrayList<>();
        routeLocator.getRoutes().subscribe(route -> {
//            routes.add(route.getId());
//            list.add(route);
            Object hidden = route.getMetadata().get("swagger-hidden");
            if (hidden != null && Boolean.valueOf(hidden.toString())) {
                return;
            }
            route.getPredicate();
            String fStr = route.getPredicate().toString();
            int arraySurfixIndex = fStr.indexOf("]");
            if (arraySurfixIndex > 0)
                fStr = fStr.substring(0, fStr.indexOf("], m"));
            fStr = fStr.replace("Paths: [", "");
            String[] strArray = fStr.split(",");
            if (strArray.length > 0)
                fStr = strArray[0];
            Object nameObj = route.getMetadata().get("service-name");
            String name = route.getId().replace("ReactiveCompositeDiscoveryClient_", "")
                    .replace("BACHELOR-", "").toLowerCase();
            if (nameObj != null) {
                name = nameObj.toString();
            }
            resources.add(swaggerResource(name,
                    fStr.replace("**", "v2/api-docs")));
        });
        routeDefinitionRepository.getRouteDefinitions();
//        Stream<RouteDefinition> routeDefinitions = routeDefinitionRepository.getRouteDefinitions().toStream();
//        routeDefinitions.filter(routeDefinition -> {
//                    return routes.contains(routeDefinition.getId());
//                })
//                .forEach(route -> {
//                    route.getPredicates().forEach(p ->{
//                        System.out.println(p.getName());
//                    });
//                            .stream()
//                            .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
//                            .forEach(predicateDefinition -> resources.add(swaggerResource(route.getId(),
//                                    predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
//                                            .replace("**", "v2/api-docs"))));
//                });
//        List<SwaggerResource> resources = new ArrayList<>();
//        List<String> routes = new ArrayList<>();
//        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
//        gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId())).forEach(route -> {
//            route.getPredicates().stream()
//                    .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
//                    .forEach(predicateDefinition -> resources.add(swaggerResource(route.getId(),
//                            predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
//                                    .replace("**", "v2/api-docs"))));
//        });
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        log.info("name:{},location:{}", name, location);
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
