package cn.org.bachelor.microservice.console.controller;

import cn.org.bachelor.microservice.console.service.DiscoveryService;
import cn.org.bachelor.microservice.console.utils.HttpUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import cn.org.bachelor.web.json.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @描述: 用户角色controller
 * @创建人: liuzhuo
 * @创建时间: 2018/10/22
 */
@RestController
@RequestMapping("discovery/")
public class DiscoveryController {

    @Autowired
    private DiscoveryService service;

    @ApiOperation(value = "获取所有服务的列表")
    @ApiImplicitParams({
    })
    @RequestMapping(method = RequestMethod.GET, value = "services")
    @HystrixCommand(commandKey = "discovery")
    public HttpEntity<JsonResponse> getServices() {
        return JsonResponse.createHttpEntity(service.getServices());
    }

    @ApiOperation(value = "获取所有服务列表的数量")
    @ApiImplicitParams({
    })
    @RequestMapping(method = RequestMethod.GET, value = "serviceCount")
    @HystrixCommand(commandKey = "discovery")
    public HttpEntity<JsonResponse> getServiceCount() {
        return JsonResponse.createHttpEntity(service.getServices().size());
    }

    @ApiOperation(value = "停止指定的服务实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appID", value = "服务ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "instanceID", value = "服务实例的ID", paramType = "path", required = true)
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "service/{appID}/{instanceID}")
    @HystrixCommand(commandKey = "discovery")
    public HttpEntity<JsonResponse> setInstanceOut(@PathVariable String appID, @PathVariable String instanceID) {
        service.setInstanceOut(HttpUtil.decodeURL(appID), HttpUtil.decodeURL(instanceID));
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "启动指定的服务实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appID", value = "服务ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "instanceID", value = "服务实例的ID", paramType = "path", required = true)
    })
    @RequestMapping(method = RequestMethod.POST, value = "service/{appID}/{instanceID}")
    @HystrixCommand(commandKey = "discovery")
    public HttpEntity<JsonResponse> setInstanceUp(@PathVariable String appID, @PathVariable String instanceID) {
        service.setInstanceUp(HttpUtil.decodeURL(appID), HttpUtil.decodeURL(instanceID));
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "停止指定的服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appID", value = "服务ID", paramType = "path", required = true)
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "service/{appID}")
    @HystrixCommand(commandKey = "discovery")
    public HttpEntity<JsonResponse> setServiceDown(@PathVariable String appID) {
        service.setServiceDown(appID);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "启动指定的服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appID", value = "服务ID", paramType = "path", required = true)
    })
    @RequestMapping(method = RequestMethod.POST, value = "service/{appID}")
    @HystrixCommand(commandKey = "discovery")
    public HttpEntity<JsonResponse> setServiceUp(@PathVariable String appID) {
        service.setServiceUp(appID);
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "获取eureka服务的列表")
    @ApiImplicitParams({
    })
    @RequestMapping(method = RequestMethod.GET, value = "eureka")
    @HystrixCommand(commandKey = "discovery")
    public HttpEntity<JsonResponse> getEurekaService() {
        return JsonResponse.createHttpEntity(service.getEurekaService(), HttpStatus.OK);
    }

    @ApiOperation(value = "获取指定的eureka服务的实例信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "服务URL", paramType = "query", required = true)
    })
    @RequestMapping(method = RequestMethod.GET, value = "eureka/info")
    @HystrixCommand(commandKey = "discovery")
    public HttpEntity<JsonResponse> getEurekaInstanceInfo(String url) {
        return JsonResponse.createHttpEntity(service.getEurekaInstance(url), HttpStatus.OK);
    }
}
