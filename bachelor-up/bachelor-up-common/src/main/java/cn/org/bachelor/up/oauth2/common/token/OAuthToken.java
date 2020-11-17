package cn.org.bachelor.up.oauth2.common.token;

/**
 * Created by team bachelor on 15/5/20.
 */
public interface OAuthToken {
    String getAccessToken();

    Long getExpiresIn();

    String getRefreshToken();

    String getScope();
}
