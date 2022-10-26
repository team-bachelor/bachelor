package cn.org.bachelor.iam.oauth2.exception;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class OAuthExceptionBuilder {

    public static OAuthBusinessException buildOAuthBusinessException(String message) {
        return new OAuthBusinessException("invalid_request").description(message);
    }

    public static OAuthBusinessException buildOnMissingParameters(Set<String> missingParams) {
        StringBuffer sb = new StringBuffer("Missing parameters: ");
        if(!isEmptySet(missingParams)) {
            Iterator i$ = missingParams.iterator();

            while(i$.hasNext()) {
                String missingParam = (String)i$.next();
                sb.append(missingParam).append(" ");
            }
        }
        return buildOAuthBusinessException(sb.toString().trim());
    }

    public static OAuthBusinessException buildOnBadContentTypeException(String expectedContentType) {
        StringBuilder errorMsg = (new StringBuilder("Bad request content type. Expecting: ")).append(expectedContentType);
        return buildOAuthBusinessException(errorMsg.toString());
    }

    public static OAuthBusinessException buildOnNotAllowedParameters(List<String> notAllowedParams) {
        StringBuffer sb = new StringBuffer("Not allowed parameters: ");
        if(notAllowedParams != null) {
            Iterator i$ = notAllowedParams.iterator();

            while(i$.hasNext()) {
                String notAllowed = (String)i$.next();
                sb.append(notAllowed).append(" ");
            }
        }
        return buildOAuthBusinessException(sb.toString().trim());
    }

    private static boolean isEmptySet(Set<String> missingParams) {
        return missingParams == null || missingParams.size() == 0;
    }
}
