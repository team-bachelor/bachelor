package cn.org.bachelor.iam.oauth2.client;


import cn.org.bachelor.iam.oauth2.exception.OAuthBusinessException;
import cn.org.bachelor.iam.oauth2.exception.OAuthSystemException;
import cn.org.bachelor.iam.oauth2.request.DefaultOAuthRequest;
import cn.org.bachelor.iam.oauth2.response.OAuthResponse;

import java.util.Map;

/**
 * Created by team bachelor on 15/5/20.
 */
public interface HttpClient {

    public <T extends OAuthResponse> T execute(
            DefaultOAuthRequest request,
            Map<String, String> headers,
            String requestMethod,
            Class<T> responseClass)
            throws OAuthSystemException, OAuthBusinessException;

    /**
     * Shut down the client and release the resources associated with the HttpClient
     */
    public void shutdown();
}
