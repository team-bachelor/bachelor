package org.bachelor.architecture.ms.console.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2019/1/14
 */
public class HttpUtil {
    private static ObjectMapper jsonMapper = new ObjectMapper();

    private static RestTemplate restTemplate = null;

    static {
        restTemplate = new RestTemplate();
    }

    public static ObjectMapper getJsonMapper() {
        return jsonMapper;
    }

    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }
    public static HttpEntity getJsonRequesBody() {
        return getJsonRequesBody(null);
    }
    public static HttpEntity getJsonRequesBody(Object payload) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", "application/json");
        requestHeaders.add("Content-Type", "application/json");
        return new HttpEntity(payload, requestHeaders);
    }
    public static String encodeURLEncode(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String decodeURL(String str) {
        String result = "";
        if (null == str) {
            return result;
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
