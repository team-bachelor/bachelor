package cn.org.bachelor.iam.idm.login.controller;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello/{user}")
    public String hello(@PathVariable String user) {
        return "hello " + user;
    }
}

