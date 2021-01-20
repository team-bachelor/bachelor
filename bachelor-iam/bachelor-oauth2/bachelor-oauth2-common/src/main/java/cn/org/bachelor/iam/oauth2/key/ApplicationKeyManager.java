package cn.org.bachelor.iam.oauth2.key;

/**
 * Created by liuzhuo on 2015/4/29.
 */
public interface ApplicationKeyManager {
    public ApplicationKey getApplicationKey(String appId);
}
