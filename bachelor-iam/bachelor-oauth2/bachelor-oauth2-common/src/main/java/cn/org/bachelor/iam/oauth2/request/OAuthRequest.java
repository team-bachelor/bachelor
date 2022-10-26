package cn.org.bachelor.iam.oauth2.request;

import java.util.Map;

/**
 * Created by team bachelor on 15/5/20.
 */
public interface OAuthRequest {

    String getLocationUri();

    void setLocationUri(String var1);

    String getBody();

    void setBody(String var1);

    String getHeader(String var1);

    void addHeader(String var1, String var2);

    Map<String, String> getHeaders();

    void setHeaders(Map<String, String> var1);
}
