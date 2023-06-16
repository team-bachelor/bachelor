package cn.org.bachelor.iam.idm.login.controller;

import cn.org.bachelor.web.json.JsonResponse;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CaptchaController {
    
    @GetMapping("/captcha.png")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }

    @ResponseBody
    @RequestMapping("/captcha/ver")
    public ResponseEntity<JsonResponse> verCaptcha(HttpServletRequest request, @RequestParam String code) throws Exception {
        return JsonResponse.createHttpEntity(CaptchaUtil.ver(code, request));
    }
}