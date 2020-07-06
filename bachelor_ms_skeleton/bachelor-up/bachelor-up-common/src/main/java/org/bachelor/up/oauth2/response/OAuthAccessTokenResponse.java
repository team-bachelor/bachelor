package cn.org.bachelor.up.oauth2.response;

import cn.org.bachelor.up.oauth2.common.token.OAuthToken;
import cn.org.bachelor.up.oauth2.validator.TokenValidator;
import cn.org.bachelor.up.oauth2.common.exception.OAuthProblemException;

/**
 * Created by team bachelor on 15/5/20.
 */
public abstract class OAuthAccessTokenResponse extends OAuthClientResponse {

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
    protected void init(String body, String contentType, int responseCode) throws OAuthProblemException {
        validator = new TokenValidator();
        super.init(body, contentType, responseCode);
    }
}
