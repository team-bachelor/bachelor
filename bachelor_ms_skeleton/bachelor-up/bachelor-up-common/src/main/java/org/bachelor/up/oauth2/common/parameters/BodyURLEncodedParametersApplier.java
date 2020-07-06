package cn.org.bachelor.up.oauth2.common.parameters;


import cn.org.bachelor.up.oauth2.common.utils.OAuthUtils;
import cn.org.bachelor.up.oauth2.common.exception.OAuthSystemException;
import cn.org.bachelor.up.oauth2.common.message.OAuthMessage;

import java.util.Map;

/**
 * Created by team bachelor on 15/5/20.
 */
public class BodyURLEncodedParametersApplier implements OAuthParametersApplier {
    public BodyURLEncodedParametersApplier() {
    }

    public OAuthMessage applyOAuthParameters(OAuthMessage message, Map<String, Object> params) throws OAuthSystemException {
        String body = OAuthUtils.format(params.entrySet(), "UTF-8");
        message.setBody(body);
        return message;
    }
}
