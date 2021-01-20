package cn.org.bachelor.iam.oauth2.response;

import cn.org.bachelor.iam.oauth2.exception.OAuthBusinessException;
import cn.org.bachelor.iam.oauth2.token.OAuthToken;
import cn.org.bachelor.iam.oauth2.validator.TokenValidator;

/**
 * Created by team bachelor on 15/5/20.
 */
public abstract class OAuthAccessTokenResponse extends OAuthResponse {

    public abstract String getAccessToken();

    public abstract Long getExpiresIn();

    public abstract String getExpiration();

    public abstract String getRefreshToken();

    public abstract String getScope();

    public abstract OAuthToken getOAuthToken();

    public String getBody() {
        return body;
    }

    @Override
    protected void init(String body, String contentType, int responseCode) throws OAuthBusinessException {
        validator = new TokenValidator();
        super.init(body, contentType, responseCode);
    }
}
