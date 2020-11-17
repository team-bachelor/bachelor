package cn.org.bachelor.up.oauth2.rsserver.securitycheck;

/**
 * Created by xueyong on 15/6/24.
 */
public interface ICheckOperate {

    public String getFromJedis(String key);

    public void saveToJedis(String key, Integer seconds, String value);

    public void saveToJedis(String key, String value);

    public void removeFromJedis(String key);
}
