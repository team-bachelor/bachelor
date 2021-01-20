package cn.org.bachelor.iam.oauth2.parameter;


import cn.org.bachelor.iam.oauth2.exception.OAuthSystemException;
import cn.org.bachelor.iam.oauth2.request.OAuthRequest;

import java.util.Map;

/**
 * Created by team bachelor on 15/5/20.
 */
public interface OAuthParameterApplier {
    OAuthRequest applyOAuthParameters(OAuthRequest var1, Map<String, Object> var2) throws OAuthSystemException;
}
