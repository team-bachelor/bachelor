package cn.org.bachelor.up.oauth2.rsserver;


import cn.org.bachelor.up.oauth2.common.exception.OAuthProblemException;
import cn.org.bachelor.up.oauth2.common.exception.OAuthSystemException;
import cn.org.bachelor.up.oauth2.request.OAuthClientRequest;
import cn.org.bachelor.up.oauth2.response.OAuthClientResponse;

import java.util.Map;

/**
 * Created by team bachelor on 15/5/20.
 */
public interface HttpClient {

    public <T extends OAuthClientResponse> T execute(
            OAuthClientRequest request,
            Map<String, String> headers,
            String requestMethod,
            Class<T> responseClass)
            throws OAuthSystemException, OAuthProblemException;

    /**
     * Shut down the client and release the resources associated with the HttpClient
     */
    public void shutdown();
}
