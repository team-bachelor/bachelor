package cn.org.bachelor.iam.oauth2.token;

/**
 * Created by team bachelor on 15/5/20.
 */
public interface OAuthToken {
    String getAccessToken();

    Long getExpiresIn();

    String getRefreshToken();

    String getScope();
}
