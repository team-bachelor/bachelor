package cn.org.bachelor.up.oauth2.validator;

import cn.org.bachelor.up.oauth2.common.error.OAuthError;
import cn.org.bachelor.up.oauth2.common.utils.OAuthUtils;
import cn.org.bachelor.up.oauth2.common.OAuth;
import cn.org.bachelor.up.oauth2.common.exception.OAuthProblemException;
import cn.org.bachelor.up.oauth2.response.OAuthClientResponse;

import java.util.*;


/**
 * Created by team bachelor on 15/5/20.
 */
public abstract class OAuthClientValidator {

    protected Map<String, String[]> requiredParams = new HashMap<String, String[]>();
    protected List<String> notAllowedParams = new ArrayList<String>();

    public void validate(OAuthClientResponse response) throws OAuthProblemException {
        validateErrorResponse(response);
        validateParameters(response);
    }

    public void validateParameters(OAuthClientResponse response) throws OAuthProblemException {
        validateRequiredParameters(response);
        validateNotAllowedParameters(response);
    }

    public void validateErrorResponse(OAuthClientResponse response) throws OAuthProblemException {
        String error = response.getParam(OAuthError.OAUTH_ERROR);
        if (!OAuthUtils.isEmpty(error)) {
            String errorDesc = response.getParam(OAuthError.OAUTH_ERROR_DESCRIPTION);
            String errorUri = response.getParam(OAuthError.OAUTH_ERROR_URI);
            String state = response.getParam(OAuth.OAUTH_STATE);
            throw OAuthProblemException.error(error).description(errorDesc).uri(errorUri).state(state);
        }
    }


    public void validateRequiredParameters(OAuthClientResponse response) throws OAuthProblemException {
        Set<String> missingParameters = new HashSet<String>();

        for (Map.Entry<String, String[]> requiredParam : requiredParams.entrySet()) {
            String paramName = requiredParam.getKey();
            String val = response.getParam(paramName);
            if (OAuthUtils.isEmpty(val)) {
                missingParameters.add(paramName);
            } else {
                String[] dependentParams = requiredParam.getValue();
                if (!OAuthUtils.hasEmptyValues(dependentParams)) {
                    for (String dependentParam : dependentParams) {
                        val = response.getParam(dependentParam);
                        if (OAuthUtils.isEmpty(val)) {
                            missingParameters.add(dependentParam);
                        }
                    }
                }
            }
        }

        if (!missingParameters.isEmpty()) {
            throw OAuthUtils.handleMissingParameters(missingParameters);
        }
    }

    public void validateNotAllowedParameters(OAuthClientResponse response) throws OAuthProblemException {
        List<String> notAllowedParameters = new ArrayList<String>();
        for (String requiredParam : notAllowedParams) {
            String val = response.getParam(requiredParam);
            if (!OAuthUtils.isEmpty(val)) {
                notAllowedParameters.add(requiredParam);
            }
        }
        if (!notAllowedParameters.isEmpty()) {
            throw OAuthUtils.handleNotAllowedParametersOAuthException(notAllowedParameters);
        }
    }


}
