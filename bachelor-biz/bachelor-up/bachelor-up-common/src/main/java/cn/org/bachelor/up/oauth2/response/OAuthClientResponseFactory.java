package cn.org.bachelor.up.oauth2.response;


import cn.org.bachelor.up.oauth2.common.utils.OAuthUtils;
import cn.org.bachelor.up.oauth2.common.exception.OAuthProblemException;
import cn.org.bachelor.up.oauth2.common.exception.OAuthSystemException;

/**
 * Created by team bachelor on 15/5/20.
 */
public class OAuthClientResponseFactory {

    public static OAuthClientResponse createGitHubTokenResponse(String body, String contentType,
                                                                int responseCode)
            throws OAuthProblemException {
        GitHubTokenResponse resp = new GitHubTokenResponse();
        resp.init(body, contentType, responseCode);
        return resp;
    }

    public static OAuthClientResponse createJSONTokenResponse(String body, String contentType,
                                                              int responseCode)
            throws OAuthProblemException {
        OAuthJSONAccessTokenResponse resp = new OAuthJSONAccessTokenResponse();
        resp.init(body, contentType, responseCode);
        return resp;
    }

    public static <T extends OAuthClientResponse> T createCustomResponse(String body, String contentType,
                                                                         int responseCode,
                                                                         Class<T> clazz)
            throws OAuthSystemException, OAuthProblemException {

        OAuthClientResponse resp = (OAuthClientResponse) OAuthUtils
                .instantiateClassWithParameters(clazz, null, null);

        resp.init(body, contentType, responseCode);

        return (T)resp;
    }


}
