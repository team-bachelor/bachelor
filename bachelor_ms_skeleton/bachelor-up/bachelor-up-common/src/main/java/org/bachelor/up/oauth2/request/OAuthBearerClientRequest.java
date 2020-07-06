package cn.org.bachelor.up.oauth2.request;


import cn.org.bachelor.up.oauth2.common.OAuth;

/**
 * Created by team bachelor on 15/5/20.
 */
public class OAuthBearerClientRequest extends OAuthClientRequest.OAuthRequestBuilder {

    public OAuthBearerClientRequest(String url) {
        super(url);
    }

    public OAuthBearerClientRequest setAccessToken(String accessToken) {
        this.parameters.put(OAuth.OAUTH_BEARER_TOKEN, accessToken);
        return this;
    }

}
