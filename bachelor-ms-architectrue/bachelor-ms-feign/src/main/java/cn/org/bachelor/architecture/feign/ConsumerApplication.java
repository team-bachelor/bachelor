package cn.org.bachelor.architecture.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@SpringBootApplication
////@EnableDiscoveryClient
//@EnableEurekaClient
////@EnableFeignClients
//public class SimpleServiceConsumer {
////    @Autowired
////    private SimpleAppStub simpleAppStub;
//
//    public static void main(String[] args) {
//        SpringApplication.run(SimpleServiceConsumer.class, args);
//    }
//
////    @RequestMapping("/hi")
////    public String home(@RequestParam String name) {
////        return simpleAppStub.sayHi(name);
////    }
//
//
//}
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }


}
