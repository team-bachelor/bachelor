package cn.org.bachelor.iam.idm.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import cn.org.bachelor.iam.acm.service.AuthorizeService;
import cn.org.bachelor.core.exception.BusinessException;
import cn.org.bachelor.core.exception.SystemException;
import cn.org.bachelor.web.exception.ExceptionHandle;
import cn.org.bachelor.web.json.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @描述: 用户角色controller
 * @创建人: liuzhuo
 * @创建时间: 2018/10/22
 */
@RestController
@ExceptionHandle
public class TestController {

    @Autowired
    private AuthorizeService authorizeService;

    /**
     * 下面都是测试用的
     * @param user
     * @return
     */

    @HystrixCommand(//fallbackMethod = "hiError",
            threadPoolProperties = {
                    //10个核心线程池,超过20个的队列外的请求被拒绝;
                    // 当一切都是正常的时候，线程池一般仅会有1到2个线程激活来提供服务
                    @HystrixProperty(name = "coreSize", value = "1"),
                    @HystrixProperty(name = "maxQueueSize", value = "1"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "1")},
            commandProperties = {
                    //命令执行超时时间
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"),
                    //若干10s一个窗口内失败三次, 则达到触发熔断的最少请求量
                    //@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
                    //断路30s后尝试执行, 默认为5s
                    //@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000"),
                    //@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
                    @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value = "10")
            })
    @RequestMapping(value = "/user_permission", method = RequestMethod.GET)
    public HttpEntity<JsonResponse> getUserPermission(String user) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Map permMap = authorizeService.calUserPermission(user);
        List permList = new ArrayList(permMap.values());
        return JsonResponse.createHttpEntity(permList);
    }

    /**
     * @param user
     * @return
     */
    public HttpEntity<JsonResponse> hiError(String user) {
        return JsonResponse.createHttpEntity("hi," + user + ",sorry,error!");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/exception/{type}/{msg}")

    public void except(@PathVariable String type, @PathVariable String msg) {
        if ("sys".equals(type)) {
            throw new SystemException(msg);
        } else if ("biz".equals(type)) {
            throw new BusinessException(msg, "123");
        } else if ("rmt".equals(type)) {
            throw new BusinessException(msg);
        } else if ("un".equals(type)) {
            throw new RuntimeException(msg);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test_perm")
    public boolean testPerm(String permCode, String user) {
        return authorizeService.isAuthorized(permCode, user);
    }

    @RequestMapping(method = RequestMethod.POST, value = "manage/user")
    public String createUser() {
        return "createUser";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "user/chagePw")
    public String userChagePw() {
        return "userChagePw";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "manage/user")
    public String deleteUser() {
        return "deleteUser";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "manage/user")
    public String updateUser() {
        return "updateUser";
    }

    @RequestMapping(method = RequestMethod.GET, value = "manage/user")
    public String getUser() {
        return "getUser";
    }
}
