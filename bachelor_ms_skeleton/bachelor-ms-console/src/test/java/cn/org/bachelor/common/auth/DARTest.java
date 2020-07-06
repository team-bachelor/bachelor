//package cn.org.bachelor.common.auth;
//
//import ConsoleWebApplication;
//import DiscoveryService;
//import GatewayService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
///**
// * @描述:
// * @创建人: liuzhuo
// * @创建时间: 2018/12/23
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = ConsoleWebApplication.class)
//@WebAppConfiguration
//public class DARTest {
//    @Autowired
//    private DiscoveryService darService;
//    @Autowired
//    private GatewayService gateWayService;
//    @Test
//    public void getServiceList(){
//        darService.getServices();
//    }
//
//    @Test
//    public void getEurekaServiceList(){
////        Map<String, List<String>> zones =  darService.getEurekaService();
////        zones.values().forEach(urls ->{
////            urls.forEach(url->{
////                System.out.println(darService.getEurekaInstance(url));
////            });
////        });
//    }
//
//    @Test
//    public void getGateway(){
////        gateWayService.getRoutes("http://localhost:8080/");
//    }
//}
