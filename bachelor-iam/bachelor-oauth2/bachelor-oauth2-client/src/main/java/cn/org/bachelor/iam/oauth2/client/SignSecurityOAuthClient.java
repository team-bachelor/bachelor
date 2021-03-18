package cn.org.bachelor.iam.oauth2.client;

import cn.org.bachelor.iam.oauth2.client.exception.SignCreationException;
import cn.org.bachelor.iam.oauth2.ClientConstant;
import cn.org.bachelor.iam.oauth2.client.util.SignatureUtil;
import cn.org.bachelor.iam.oauth2.exception.OAuthBusinessException;
import cn.org.bachelor.iam.oauth2.exception.OAuthSystemException;
import cn.org.bachelor.iam.oauth2.request.DefaultOAuthRequest;
import cn.org.bachelor.iam.oauth2.request.DefaultOAuthResourceRequest;
import cn.org.bachelor.iam.oauth2.response.OAuthAccessTokenResponse;
import cn.org.bachelor.iam.oauth2.response.OAuthResponse;
import cn.org.bachelor.iam.oauth2.response.OAuthJSONAccessTokenResponse;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuzhuo on 2015/5/5.
 */
public class SignSecurityOAuthClient {
    protected HttpClient httpClient;

    public SignSecurityOAuthClient(HttpClient oauthClient) {
        this.httpClient = oauthClient;
    }

    public <T extends OAuthAccessTokenResponse> T accessToken(DefaultOAuthRequest request, Class<T> responseClass) throws OAuthSystemException, OAuthBusinessException {
        return this.accessToken(request, "POST", responseClass);
    }

    public <T extends OAuthAccessTokenResponse> T accessToken(DefaultOAuthRequest request, String requestMethod, Class<T> responseClass) throws OAuthSystemException, OAuthBusinessException {
        HashMap headers = new HashMap();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        return (T) this.httpClient.execute(request, headers, requestMethod, responseClass);
    }

    public OAuthJSONAccessTokenResponse accessToken(DefaultOAuthRequest request) throws OAuthSystemException, OAuthBusinessException {
        return (OAuthJSONAccessTokenResponse) this.accessToken(request, OAuthJSONAccessTokenResponse.class);
    }

    public OAuthJSONAccessTokenResponse accessToken(DefaultOAuthRequest request, String requestMethod) throws OAuthSystemException, OAuthBusinessException {
        return (OAuthJSONAccessTokenResponse) this.accessToken(request, requestMethod, OAuthJSONAccessTokenResponse.class);
    }

    public <T extends OAuthResponse> T resource(DefaultOAuthRequest request, String requestMethod, Class<T> responseClass) throws OAuthSystemException, OAuthBusinessException {
        return (T) this.httpClient.execute(request, (Map) null, requestMethod, responseClass);
    }

    public <T extends OAuthResponse> T resource(DefaultOAuthResourceRequest request, Class<T> responseClass)
            throws OAuthSystemException, OAuthBusinessException, SignCreationException {
        try {
            request.setParameter(ClientConstant.HTTP_REQUEST_PARAM_SIGN, SignatureUtil.createSignature(request.getParamsString()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new SignCreationException(e);
        }
        return (T) this.httpClient.execute(request.buildQueryMessage(), (Map) null, request.getMethod(), responseClass);
    }

    public void shutdown() {
        this.httpClient.shutdown();
    }
}
