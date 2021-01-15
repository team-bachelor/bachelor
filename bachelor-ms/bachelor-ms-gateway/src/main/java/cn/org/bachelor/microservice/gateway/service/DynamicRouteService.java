package cn.org.bachelor.microservice.gateway.service;


import cn.org.bachelor.core.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class DynamicRouteService implements ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    @Autowired
    private RouteDefinitionRepository routeDefinitionRepository;
    private ApplicationEventPublisher publisher;

    public List<RouteDefinition> getRouteDefinitions() {
        return routeDefinitionRepository.getRouteDefinitions().collectList().block();
    }

    /**
     * 增加路由
     *
     * @param definition
     * @return
     */
    public void add(RouteDefinition definition) {
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
     * 更新路由
     *
     * @param definition
     * @return
     */
    public void update(RouteDefinition definition) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            throw new BusinessException("update fail,not find route  routeId: " + definition.getId());
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
        } catch (Exception e) {
            throw new BusinessException("update route  fail");
        }
    }

    /**
     * 删除路由
     *
     * @param id
     * @return
     */
    public void delete(String id) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(id));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException( "delete fail");
        }

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
