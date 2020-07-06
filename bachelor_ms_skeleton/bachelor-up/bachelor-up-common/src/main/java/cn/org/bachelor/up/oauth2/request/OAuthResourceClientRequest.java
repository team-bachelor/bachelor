package cn.org.bachelor.up.oauth2.request;

import cn.org.bachelor.up.oauth2.Constant;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by team bachelor on 15/5/20.
 */
public class OAuthResourceClientRequest extends cn.org.bachelor.up.oauth2.request.OAuthBearerClientRequest {
    private String uri = null;
    private String method = null;

    public OAuthResourceClientRequest(String uri, String url, String method) {
        super(url);
        this.uri = uri;
        this.method = method;
    }

    public OAuthResourceClientRequest setParameter(String paramName, String paramValue) {
        this.parameters.put(paramName, paramValue);
        return this;
    }

    public OAuthResourceClientRequest setOpentID(String opentID) {
        this.parameters.put(Constant.HTTP_REQUEST_PARAM_OPEN_ID, opentID);
        return this;
    }

    public String getOpenID() {
        return this.parameters.get(Constant.HTTP_REQUEST_PARAM_OPEN_ID).toString();
    }

    public OAuthResourceClientRequest setClientID(String clientID) {
        this.parameters.put(Constant.HTTP_REQUEST_PARAM_CLIENT_ID, clientID);
        return this;
    }

    public String getClientID() {
        return this.parameters.get(Constant.HTTP_REQUEST_PARAM_CLIENT_ID).toString();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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
                .append(encode(getUri()))
                .append("&")
                .append(encode(param));
        return paramBuilder.toString();
    }

    //改写toString方法
    public String toString1(){
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
        paramBuilder = new StringBuilder(encode(param));
        return paramBuilder.toString();
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
