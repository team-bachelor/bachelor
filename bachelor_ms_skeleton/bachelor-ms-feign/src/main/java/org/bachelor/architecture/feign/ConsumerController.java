package org.bachelor.architecture.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ConsumerController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ServiceStub serviceStub;

    @RequestMapping("/sayHi")
    public String sayHi(@RequestParam String name) {
        logger.info("calling sayHi method");
        return serviceStub.sayHi(name);
    }
}
