package cn.org.bachelor.up.oauth2.request;


import cn.org.bachelor.up.oauth2.common.utils.OAuthUtils;
import cn.org.bachelor.up.oauth2.common.OAuth;
import cn.org.bachelor.up.oauth2.common.exception.OAuthSystemException;
import cn.org.bachelor.up.oauth2.common.message.OAuthMessage;
import cn.org.bachelor.up.oauth2.common.parameters.OAuthParametersApplier;

import java.util.Map;

/**
 * Created by team bachelor on 15/5/20.
 */
public class ClientHeaderParametersApplier implements OAuthParametersApplier {

    public OAuthMessage applyOAuthParameters(OAuthMessage message, Map<String, Object> params)
            throws OAuthSystemException {

        String header = OAuthUtils.encodeAuthorizationBearerHeader(params);
        message.addHeader(OAuth.HeaderType.AUTHORIZATION, header);
        return message;

    }

}
