package cn.org.bachelor.iam.idm.login;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RedisCacheHelper {

    public static void setCaptcha(RedisTemplate template, String index, String captcha) {
        template.opsForValue().set(LoginConstant.CAPTCHA_CACHE_KEY + index, captcha, 300, TimeUnit.SECONDS);
    }

    public static String getCaptcha(RedisTemplate template, String index) {
        Object value = template.opsForValue().get(LoginConstant.CAPTCHA_CACHE_KEY + index);
        return Objects.isNull(value) ? null : value.toString();
    }

    public static boolean verCaptcha(RedisTemplate redisTemplate, String index, String code) {
        if (code != null && index != null) {
            String captcha = getCaptcha(redisTemplate, index);
            return code.trim().toLowerCase().equals(captcha);
        } else {
            return false;
        }
    }
}
