package cn.org.bachelor.iam.idm.login.controller;

import cn.org.bachelor.iam.acm.domain.User;
import cn.org.bachelor.iam.idm.login.service.LoginService;
import cn.org.bachelor.web.json.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user){
        return JsonResponse.createHttpEntity(loginService.login(user));
    }
}
