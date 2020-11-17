package cn.org.bachelor.up.oauth2.rsserver.securitycheck;

import cn.org.bachelor.up.oauth2.rsserver.util.JedisUtil;
import org.springframework.stereotype.Component;

/**
 * Created by xueyong on 15/6/24.
 */

@Component
public class OperateJedisImpl implements ICheckOperate {

    @Override
    public String getFromJedis(String key) {
        return JedisUtil.getString(key);
    }

    @Override
    public void saveToJedis(String key, Integer seconds, String value) {
        JedisUtil.saveString(key, value, seconds);
    }

    @Override
    public void saveToJedis(String key, String value) {
        JedisUtil.saveString(key,value);
    }

    @Override
    public void removeFromJedis(String key) {
        JedisUtil.removeObject(key);

    }
}
