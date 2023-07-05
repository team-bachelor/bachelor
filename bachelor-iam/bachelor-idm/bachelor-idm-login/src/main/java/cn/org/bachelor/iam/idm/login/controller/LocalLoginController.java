package cn.org.bachelor.iam.idm.login.controller;

import cn.org.bachelor.iam.idm.login.LoginException;
import cn.org.bachelor.iam.idm.login.RedisCacheHelper;
import cn.org.bachelor.iam.idm.login.credential.UsernamePasswordCredential;
import cn.org.bachelor.iam.idm.login.service.LoginService;
import cn.org.bachelor.iam.utils.StringUtils;
import cn.org.bachelor.web.json.JsonResponse;
import com.wf.captcha.utils.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/local")
public class LocalLoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    public ResponseEntity login(HttpServletRequest request, @RequestBody UsernamePasswordCredential user, @RequestParam(value = "i", required = false) String index) {
        String captcha = (String) request.getSession().getAttribute("captcha");
        log.debug("ver captcha:" + user.getCaptcha() + ",session=" + captcha + ",cache=" + RedisCacheHelper.getCaptcha(redisTemplate, index));
        index = StringUtils.isEmpty(user.getIndex()) ? index : user.getIndex();
        if ( //!CaptchaUtil.ver(user.getCaptcha(), request) &&
                !RedisCacheHelper.verCaptcha(redisTemplate, index, user.getCaptcha())) {
//        if ( !RedisCacheHelper.verCaptcha(redisTemplate, index, captcha)) {
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

    @GetMapping("/logout")
    public ResponseEntity logout() {
        loginService.logout();
        return JsonResponse.createHttpEntity(HttpStatus.OK);
    }
}
