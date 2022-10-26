package cn.org.bachelor.iam.oauth2.request;

import cn.org.bachelor.iam.oauth2.OAuthConstant;
import cn.org.bachelor.iam.oauth2.OAuthConstant;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by team bachelor on 15/5/20.
 */
public class DefaultOAuthResourceRequest extends DefaultOAuthRequest.OAuthRequestBuilder {
    private String method = null;

    public DefaultOAuthResourceRequest(String url, String method) {
        super(url);
        this.method = method;
    }

    public DefaultOAuthResourceRequest setAccessToken(String accessToken) {
        this.parameters.put(OAuthConstant.OAUTH_BEARER_TOKEN, accessToken);
        return this;
    }

    public DefaultOAuthResourceRequest setParameter(String paramName, String paramValue) {
        this.parameters.put(paramName, paramValue);
        return this;
    }

    public DefaultOAuthResourceRequest setOpentID(String opentID) {
        this.parameters.put(OAuthConstant.HTTP_REQUEST_PARAM_OPEN_ID, opentID);
        return this;
    }

    public String getOpenID() {
        return this.parameters.get(OAuthConstant.HTTP_REQUEST_PARAM_OPEN_ID).toString();
    }

    public DefaultOAuthResourceRequest setClientID(String clientID) {
        this.parameters.put(OAuthConstant.HTTP_REQUEST_PARAM_CLIENT_ID, clientID);
        return this;
    }

    public String getClientID() {
        return this.parameters.get(OAuthConstant.HTTP_REQUEST_PARAM_CLIENT_ID).toString();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String toString() {
        // 将加入两个id的key集合排序
        Set<String> keySet = this.parameters.keySet();
        List<String> paramkeys = new ArrayList<String>(keySet);
        Collections.sort(paramkeys);

        // 生成一个新的map将两个id放进去
        Map<String, Object> newParams = new HashMap<String, Object>(this.parameters);

        // 根据新生成的map和排序后的key集合生成访问字符串并urlencode
        StringBuilder paramBuilder = new StringBuilder();
        for (String key : paramkeys) {
            String value = newParams.get(key).toString();
            if (value != null && !"".equals(value)) {
                paramBuilder.append(key).append("=").append(value).append("&");
            }
        }
        paramBuilder.deleteCharAt(paramBuilder.length() - 1);
        String param = paramBuilder.toString();
        paramBuilder = new StringBuilder(getMethod())
                .append("&")
                .append(encode(this.url))
                .append("&")
                .append(encode(param));
        return paramBuilder.toString();
    }

    //改写toString方法
    public String getParamsString(){
        // 将加入两个id的key集合排序
        Set<String> keySet = this.parameters.keySet();
        List<String> paramkeys = new ArrayList<String>(keySet);
        Collections.sort(paramkeys);

        // 生成一个新的map将两个id放进去
        Map<String, Object> newParams = new HashMap<String, Object>(this.parameters);

        // 根据新生成的map和排序后的key集合生成访问字符串并urlencode
        StringBuilder paramBuilder = new StringBuilder();
        for (String key : paramkeys) {
            String value = newParams.get(key).toString();
            if (value != null && !"".equals(value)) {
                paramBuilder.append(key).append("=").append(value).append("&");
            }
        }
        paramBuilder.deleteCharAt(paramBuilder.length() - 1);
        return encode(paramBuilder.toString());
    }

    private static String encode(String src) {
        try {
            return java.net.URLEncoder.encode(src, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return src;
        }
    }
}
