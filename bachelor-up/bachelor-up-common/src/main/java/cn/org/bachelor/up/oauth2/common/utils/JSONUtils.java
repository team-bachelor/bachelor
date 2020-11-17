package cn.org.bachelor.up.oauth2.common.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.org.bachelor.core.exception.BusinessException;
import cn.org.bachelor.up.oauth2.common.OAuth;
import cn.org.bachelor.up.oauth2.common.error.OAuthError;
import cn.org.bachelor.up.oauth2.common.exception.OAuthProblemException;

import java.io.IOException;
import java.util.Map;

/**
 * Created by team bachelor on 15/5/20.
 */
public final class JSONUtils {
    public JSONUtils() {
    }

    public static String buildJSON(Map<String, Object> params) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new BusinessException(e);
        }
    }

    public static Map<String, Object> parseJSON(String jsonBody) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> tokenMap = mapper.readValue(jsonBody, Map.class);
            return tokenMap;
        } catch (IOException e) {
            throw OAuthProblemException.error(OAuthError.CodeResponse.UNSUPPORTED_RESPONSE_TYPE,
                    "Invalid response! Response body is not " + OAuth.ContentType.JSON + " encoded");
        }
    }
}
