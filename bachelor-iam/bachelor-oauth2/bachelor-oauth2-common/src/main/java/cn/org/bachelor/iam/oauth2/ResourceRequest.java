package cn.org.bachelor.iam.oauth2;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by liuzhuo on 2015/4/29.
 */
public class ResourceRequest {
    private Map<String, String> parameters = new HashMap<String, String>();
    private String uri = null;
    private String method = null;

    public ResourceRequest(String uri, String method) {
        this.uri = uri;
        this.method = method;
    }

    public ResourceRequest setParameter(String paramName, String paramValue) {
        this.parameters.put(paramName, paramValue);
        return this;
    }

    public ResourceRequest setOpentID(String opentID) {
        this.parameters.put(OAuthConstant.HTTP_REQUEST_PARAM_OPEN_ID, opentID);
        return this;
    }

    public String getOpenID() {
        return this.parameters.get(OAuthConstant.HTTP_REQUEST_PARAM_OPEN_ID).toString();
    }

    public ResourceRequest setClientID(String clientID) {
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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

    private static String encode(String src) {
        try {
            return java.net.URLEncoder.encode(src, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return src;
        }
    }
}
