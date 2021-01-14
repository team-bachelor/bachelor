package cn.org.bachelor.up.oauth2.common.parameters;


import cn.org.bachelor.up.oauth2.common.exception.OAuthSystemException;
import cn.org.bachelor.up.oauth2.common.message.OAuthMessage;

import java.util.Map;

/**
 * Created by team bachelor on 15/5/20.
 */
public interface OAuthParametersApplier {
    OAuthMessage applyOAuthParameters(OAuthMessage var1, Map<String, Object> var2) throws OAuthSystemException;
}
