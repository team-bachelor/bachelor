package cn.org.bachelor.iam.oauth2.request;


import cn.org.bachelor.iam.oauth2.OAuthConstant;
import cn.org.bachelor.iam.oauth2.utils.SignatureUtil;

import static cn.org.bachelor.iam.oauth2.utils.SignatureUtil.sortParameters;

/**
 * Created by team bachelor on 15/5/20.
 */
public class DefaultOAuthResourceRequest extends DefaultOAuthRequest.OAuthRequestBuilder {
    private String method = null;

    public DefaultOAuthResourceRequest(String url, String method) {
        super(url);
        this.method = method;
    }

    public DefaultOAuthResourceRequest setAccessToken(String accessToken) {
        this.parameters.put(OAuthConstant.OAUTH_BEARER_TOKEN, accessToken);
        return this;
    }

    public DefaultOAuthResourceRequest setParameter(String paramName, String paramValue) {
        this.parameters.put(paramName, paramValue);
        return this;
    }

    public DefaultOAuthResourceRequest setOpenID(String openID) {
        this.parameters.put(OAuthConstant.HTTP_REQUEST_PARAM_OPEN_ID, openID);
        return this;
    }

    public String getOpenID() {
        return this.parameters.get(OAuthConstant.HTTP_REQUEST_PARAM_OPEN_ID).toString();
    }

    public DefaultOAuthResourceRequest setClientID(String clientID) {
        this.parameters.put(OAuthConstant.HTTP_REQUEST_PARAM_CLIENT_ID, clientID);
        return this;
    }

    public String getClientID() {
        return this.parameters.get(OAuthConstant.HTTP_REQUEST_PARAM_CLIENT_ID).toString();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String toString() {
        String param = getParamsString();
        StringBuilder paramBuilder = new StringBuilder(getMethod())
                .append("&")
                .append(SignatureUtil.encode(this.url))
                .append("&")
                .append(param);
        return paramBuilder.toString();
    }

    //改写toString方法
    public String getParamsString() {
        return sortParameters(this.parameters);
    }
}
