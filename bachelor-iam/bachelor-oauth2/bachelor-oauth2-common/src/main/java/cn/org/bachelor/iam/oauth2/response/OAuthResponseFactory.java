package cn.org.bachelor.iam.oauth2.response;


import cn.org.bachelor.iam.oauth2.exception.OAuthBusinessException;
import cn.org.bachelor.iam.oauth2.exception.OAuthSystemException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by team bachelor on 15/5/20.
 */
public class OAuthResponseFactory {

    public static OAuthResponse createGitHubTokenResponse(String body, String contentType,
                                                          int responseCode)
            throws OAuthBusinessException {
        GitHubTokenResponse resp = new GitHubTokenResponse();
        resp.init(body, contentType, responseCode);
        return resp;
    }

    public static OAuthResponse createJSONTokenResponse(String body, String contentType,
                                                        int responseCode)
            throws OAuthBusinessException {
        OAuthJSONAccessTokenResponse resp = new OAuthJSONAccessTokenResponse();
        resp.init(body, contentType, responseCode);
        return resp;
    }

    public static <T extends OAuthResponse> T createCustomResponse(String body, String contentType,
                                                                   int responseCode,
                                                                   Class<T> clazz)
            throws OAuthSystemException, OAuthBusinessException {

        OAuthResponse resp = (OAuthResponse) getBean(clazz, null, null);
        resp.init(body, contentType, responseCode);
        return (T)resp;
    }

    private static <T> T getBean(Class<T> clazz, Class<?>[] paramsTypes, Object[] paramValues) throws OAuthSystemException {
        try {
            if(paramsTypes != null && paramValues != null) {
                if(paramsTypes.length != paramValues.length) {
                    throw new IllegalArgumentException("Number of types and values must be equal");
                } else if(paramsTypes.length == 0 && paramValues.length == 0) {
                    return clazz.newInstance();
                } else {
                    Constructor e = clazz.getConstructor(paramsTypes);
                    return (T) e.newInstance(paramValues);
                }
            } else {
                return clazz.newInstance();
            }
        } catch (NoSuchMethodException var4) {
            throw new OAuthSystemException(var4);
        } catch (InstantiationException var5) {
            throw new OAuthSystemException(var5);
        } catch (IllegalAccessException var6) {
            throw new OAuthSystemException(var6);
        } catch (InvocationTargetException var7) {
            throw new OAuthSystemException(var7);
        }
    }

}
