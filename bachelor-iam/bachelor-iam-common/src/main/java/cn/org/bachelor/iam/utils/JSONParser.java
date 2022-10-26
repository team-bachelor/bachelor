package cn.org.bachelor.iam.utils;


import cn.org.bachelor.iam.oauth2.OAuthConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.org.bachelor.exception.BusinessException;
import cn.org.bachelor.iam.oauth2.exception.OAuthBusinessException;

import java.io.IOException;
import java.util.Map;

/**
 * Created by team bachelor on 15/5/20.
 */
public final class JSONParser {

    private JSONParser() {
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
            return mapper.readValue(jsonBody, Map.class);
        } catch (IOException e) {
            throw new OAuthBusinessException(OAuthConstant.CodeResponse.UNSUPPORTED_RESPONSE_TYPE,
                    "Invalid response! Response body is not " + OAuthConstant.ContentType.JSON + " encoded");
        }
    }
}
