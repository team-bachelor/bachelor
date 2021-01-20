package cn.org.bachelor.iam.oauth2.response;


import cn.org.bachelor.iam.oauth2.OAuthConstant;
import cn.org.bachelor.iam.oauth2.token.BasicOAuthToken;
import cn.org.bachelor.iam.oauth2.utils.OAuthUtils;
import cn.org.bachelor.iam.oauth2.token.OAuthToken;


/**
 * Created by team bachelor on 15/5/20.
 */
public class GitHubTokenResponse extends OAuthAccessTokenResponse {


    public String getAccessToken() {
        return getParam(OAuthConstant.OAUTH_ACCESS_TOKEN);
    }

    public Long getExpiresIn() {
        String value = getParam(OAuthConstant.OAUTH_EXPIRES_IN);
        return value == null? null: Long.valueOf(value);
    }
    @Override
    public String getExpiration() {
        String value = getParam(OAuthConstant.OAUTH_EXPiRATION);
        return value == null? null: value;
    }

    public String getRefreshToken() {
        return getParam(OAuthConstant.OAUTH_EXPIRES_IN);
    }

    public String getScope() {
        return getParam(OAuthConstant.OAUTH_SCOPE);
    }

    public OAuthToken getOAuthToken() {
        return new BasicOAuthToken(getAccessToken(), getExpiresIn(), getRefreshToken(), getScope());
    }

    protected void setBody(String body) {
        this.body = body;
        parameters = OAuthUtils.decodeForm(body);
    }

    protected void setContentType(String contentType) {
        this.contentType = contentType;
    }

    protected void setResponseCode(int code) {
        this.responseCode = code;
    }


}
