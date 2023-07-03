package cn.org.bachelor.iam.idm.login.controller;

import cn.org.bachelor.iam.idm.login.LoginConstant;
import cn.org.bachelor.iam.idm.login.RedisCacheHelper;
import cn.org.bachelor.web.json.JsonResponse;
import com.wf.captcha.utils.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
public class CaptchaController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/captcha.png")
    public void captcha(HttpServletRequest request, HttpServletResponse response, @RequestParam("i")String index) throws Exception {
        CaptchaUtil.out(request, response);
        String captcha = (String) request.getSession().getAttribute("captcha");
        RedisCacheHelper.setCaptcha(redisTemplate, index, captcha);
        log.debug("captcha:" + captcha);
    }

    @ResponseBody
    @RequestMapping("/captcha/ver")
    public ResponseEntity<JsonResponse> verCaptcha(HttpServletRequest request, @RequestParam String code) throws Exception {
        return JsonResponse.createHttpEntity(CaptchaUtil.ver(code, request));
    }
}