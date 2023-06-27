package cn.org.bachelor.iam.idm.login.controller;

import cn.org.bachelor.iam.idm.login.LoginException;
import cn.org.bachelor.iam.idm.login.credential.UsernamePasswordCredential;
import cn.org.bachelor.iam.idm.login.service.LoginAttemptService;
import cn.org.bachelor.iam.idm.login.service.LoginService;
import cn.org.bachelor.web.json.JsonResponse;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/local")
public class LocalLoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity login(HttpServletRequest request, @RequestBody UsernamePasswordCredential user) {
        if(!CaptchaUtil.ver(user.getCaptcha(), request)){
            throw new LoginException("请输入正确的验证码");
        }
        try {
            Authentication authentication = loginService.login(user);
            String jwt = loginService.getJwt(authentication);
            CaptchaUtil.clear(request);
            return JsonResponse.createHttpEntity(jwt);
        } catch (Exception e) {
            throw e;
        }
    }
}
