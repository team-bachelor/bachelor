package cn.org.bachelor.iam.oauth2.validator;

import cn.org.bachelor.iam.oauth2.OAuthConstant;
import cn.org.bachelor.iam.oauth2.exception.OAuthBusinessException;
import cn.org.bachelor.iam.oauth2.exception.OAuthExceptionBuilder;
import cn.org.bachelor.iam.oauth2.utils.OAuthUtils;
import cn.org.bachelor.iam.oauth2.response.OAuthResponse;

import java.util.*;


/**
 * Created by team bachelor on 15/5/20.
 */
public abstract class OAuthClientValidator {

    protected Map<String, String[]> requiredParams = new HashMap<String, String[]>();
    protected List<String> notAllowedParams = new ArrayList<String>();

    public void validate(OAuthResponse response) throws OAuthBusinessException {
        validateErrorResponse(response);
        validateParameters(response);
    }

    public void validateParameters(OAuthResponse response) throws OAuthBusinessException {
        validateRequiredParameters(response);
        validateNotAllowedParameters(response);
    }

    public void validateErrorResponse(OAuthResponse response) throws OAuthBusinessException {
        String error = response.getParam(OAuthConstant.OAUTH_ERROR);
        if (!OAuthUtils.isEmpty(error)) {
            String errorDesc = response.getParam(OAuthConstant.OAUTH_ERROR_DESCRIPTION);
            String errorUri = response.getParam(OAuthConstant.OAUTH_ERROR_URI);
            String state = response.getParam(OAuthConstant.OAUTH_STATE);
            throw new OAuthBusinessException(error).description(errorDesc).uri(errorUri).state(state);
        }
    }


    public void validateRequiredParameters(OAuthResponse response) throws OAuthBusinessException {
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
            throw OAuthExceptionBuilder.buildOnMissingParameters(missingParameters);
        }
    }

    public void validateNotAllowedParameters(OAuthResponse response) throws OAuthBusinessException {
        List<String> notAllowedParameters = new ArrayList<String>();
        for (String requiredParam : notAllowedParams) {
            String val = response.getParam(requiredParam);
            if (!OAuthUtils.isEmpty(val)) {
                notAllowedParameters.add(requiredParam);
            }
        }
        if (!notAllowedParameters.isEmpty()) {
            throw OAuthExceptionBuilder.buildOnNotAllowedParameters(notAllowedParameters);
        }
    }


}
