package cn.org.bachelor.iam.oauth2.validator;


import cn.org.bachelor.iam.oauth2.OAuthConstant;

/**
 * Created by team bachelor on 15/5/20.
 */
public class TokenValidator extends OAuthClientValidator {

    public TokenValidator() {

        requiredParams.put(OAuthConstant.OAUTH_ACCESS_TOKEN, new String[] {});

        notAllowedParams.add(OAuthConstant.OAUTH_CODE);
    }
}
