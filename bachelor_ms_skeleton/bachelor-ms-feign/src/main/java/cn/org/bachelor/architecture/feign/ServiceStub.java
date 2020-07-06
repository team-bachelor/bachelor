package cn.org.bachelor.architecture.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("service-hi")
public interface ServiceStub {
    @RequestMapping(method = RequestMethod.GET, value = "/hi")
    String sayHi(@RequestParam String name);
}