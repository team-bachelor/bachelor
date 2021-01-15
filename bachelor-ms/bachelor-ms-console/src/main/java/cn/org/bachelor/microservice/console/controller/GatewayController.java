package cn.org.bachelor.microservice.console.controller;

import cn.org.bachelor.microservice.console.service.GatewayService;
import cn.org.bachelor.microservice.console.vo.RateLimitVo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import cn.org.bachelor.web.json.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @描述: 用户角色controller
 * @创建人: liuzhuo
 * @创建时间: 2018/10/22
 */
@RestController
@RequestMapping("gateway")
public class GatewayController {

    @Autowired
    private GatewayService service;

    @ApiOperation(value = "获取所有网关的实例信息")
    @ApiImplicitParams({
    })
    @RequestMapping(method = RequestMethod.GET, value = "list")
    @HystrixCommand(commandKey = "gateway")
    public HttpEntity<JsonResponse> getList() {
        return JsonResponse.createHttpEntity(service.getGateways());
    }

    @ApiOperation(value = "获取指定网关的默认限流信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "网关服务的URL", paramType = "query", required = true)
    })
    @RequestMapping(method = RequestMethod.GET, value = "default_rate_limit")
    @HystrixCommand(commandKey = "gateway")
    public HttpEntity<JsonResponse> getDefaultRateLimit(String url) {
        return JsonResponse.createHttpEntity(service.getDefaultRateLimit(url));
    }

    @ApiOperation(value = "获取全部网关的路由配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "网关服务的URL", paramType = "query", required = true)
    })
    @RequestMapping(method = RequestMethod.GET, value = "routes")
    @HystrixCommand(commandKey = "gateway")
    public HttpEntity<JsonResponse> getRoutes(String url) {
        return JsonResponse.createHttpEntity(service.getRoutes(url));
    }

    @ApiOperation(value = "更新路由限流配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "网关服务的URL", paramType = "query", required = true),
            @ApiImplicitParam(name = "routeId", value = "指定路由的ID", paramType = "query", required = true),
            @ApiImplicitParam(name = "rateLimit", value = "限流器信息", paramType = "body", required = true)
    })
    @RequestMapping(method = RequestMethod.PUT, value = "rate_limit")
    @HystrixCommand(commandKey = "gateway")
    public HttpEntity<JsonResponse> setRateLimit(String url,String routeId, @RequestBody RateLimitVo rateLimit) {
        service.updateRateLimit(url, routeId, rateLimit);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

}
