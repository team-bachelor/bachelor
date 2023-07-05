package cn.org.bachelor.iam.idm.login;

import cn.org.bachelor.exception.BusinessException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RedisCacheHelper {

    private static final int captchaTimeout = 300;
    private static final int loginTimeout = 300;

    public static void setCaptcha(RedisTemplate template, String index, String captcha) {
        template.opsForValue().set(getCaptchaKey(index), captcha, captchaTimeout, TimeUnit.SECONDS);
    }

    private static String getCaptchaKey(String index) {
        return LoginConstant.CAPTCHA_CACHE_KEY + index;
    }

    public static String getCaptcha(RedisTemplate template, String index) {
        Object value = template.opsForValue().get(getCaptchaKey(index));
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

    public static void setLoginUser(RedisTemplate redisTemplate, LoginUser user) {
        redisTemplate.opsForValue().set(getUserKey(user.getId()), JSONObject.toJSONString(user), loginTimeout, TimeUnit.MINUTES);
    }

    private static String getUserKey(String userId) {
        return LoginConstant.USER_CACHE_KEY + userId;
    }

    public static LoginUser delLoginUser(RedisTemplate redisTemplate, String userId) {
        String userKey = getUserKey(userId);
        if (redisTemplate.hasKey(userKey)) {
            LoginUser user = (LoginUser) redisTemplate.opsForValue().get(userKey);
            if (redisTemplate.delete(userKey)) {
                return user;
            } else {
                throw new BusinessException("登出失败，缓存错误。");
            }
        }
        return null;
    }

    public static void refreshLoginUser(RedisTemplate redisTemplate, String userId) {
        String key = getUserKey(userId);
        if (redisTemplate.hasKey(key))
            redisTemplate.expire(key, loginTimeout, TimeUnit.MINUTES);
    }
}
