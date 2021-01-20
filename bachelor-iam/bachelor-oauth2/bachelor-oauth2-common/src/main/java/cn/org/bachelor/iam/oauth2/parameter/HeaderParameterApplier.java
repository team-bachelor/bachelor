package cn.org.bachelor.iam.oauth2.parameter;


import cn.org.bachelor.iam.oauth2.OAuthConstant;
import cn.org.bachelor.iam.oauth2.exception.OAuthSystemException;
import cn.org.bachelor.iam.oauth2.request.OAuthRequest;
import cn.org.bachelor.iam.oauth2.utils.OAuthUtils;

import java.util.Map;

/**
 * Created by team bachelor on 15/5/20.
 */
public class HeaderParameterApplier implements OAuthParameterApplier {

    public OAuthRequest applyOAuthParameters(OAuthRequest message, Map<String, Object> params)
            throws OAuthSystemException {

        String header = OAuthUtils.encodeAuthorizationBearerHeader(params);
        message.addHeader(OAuthConstant.HeaderType.AUTHORIZATION, header);
        return message;

    }

}
