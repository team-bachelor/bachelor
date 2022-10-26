package cn.org.bachelor.iam.oauth2.parameter;


import cn.org.bachelor.iam.oauth2.request.OAuthRequest;
import cn.org.bachelor.iam.oauth2.utils.OAuthUtils;
import cn.org.bachelor.iam.oauth2.exception.OAuthSystemException;

import java.util.Map;

/**
 * Created by team bachelor on 15/5/20.
 */
public class BodyParameterApplier implements OAuthParameterApplier {
    public BodyParameterApplier() {
    }

    public OAuthRequest applyOAuthParameters(OAuthRequest message, Map<String, Object> params) throws OAuthSystemException {
        String body = OAuthUtils.buildQueryString(params.entrySet(), "UTF-8");
        message.setBody(body);
        return message;
    }
}
