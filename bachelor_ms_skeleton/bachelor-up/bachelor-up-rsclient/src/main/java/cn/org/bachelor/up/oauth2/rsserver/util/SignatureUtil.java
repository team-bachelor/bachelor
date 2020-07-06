package cn.org.bachelor.up.oauth2.rsserver.util;

import cn.org.bachelor.up.oauth2.Constant;
import cn.org.bachelor.up.oauth2.ResourceRequest;
import cn.org.bachelor.up.oauth2.utils.MD5;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by team bachelor on 2015/5/21.
 */
public class SignatureUtil {
    private static final String secret = "bachelor_up"; //加密串

    /**
     * 参数请传入前验证，可以把参数异常抛给客户端程序
     * @param query ？后的字符串 没有传入""
     * @param signature 客户端传入的签名 必填项
     * @return
     */
    public static boolean checkSignature(String query,String signature){
        String mySignature = createSignature(query);
        if(mySignature.equals(decode(signature,"UTF-8")))
            return true;
        return false;
    }
    /**参数请传入前验证，可以把参数异常抛给客户端程序
     * 生成签名
     * @param query ？后的字符串 没有传入""
     * @return
     */
    public static String createSignature(String query){
        StringBuffer keys = new StringBuffer();
        keys = keys.append(secret).append(query).append(secret);

        return MD5.getEncryptResult(keys.toString()).toLowerCase();
    }

    /**参数请传入前验证，可以把参数异常抛给客户端程序
     * 生成签名
     * @param query ？后的字符串 没有传入""
     * @param secret 密钥 必填项
     * @return
     */
    public static String createSignature(String query,String secret){
        StringBuffer keys = new StringBuffer();
        keys = keys.append(secret).append(query).append(secret);

        return MD5.getEncryptResult(keys.toString()).toLowerCase();
    }

    /**
     * 根据网络请求生成资源请求对象
     * @param httpRequest
     * @return
     */
    public static ResourceRequest getResourceRequest(HttpServletRequest httpRequest){
        ResourceRequest request = new ResourceRequest(httpRequest.getRequestURI(), httpRequest.getMethod());
        Set<String> keys = httpRequest.getParameterMap().keySet();
        for(String name : keys){
            if(!name.equals(Constant.HTTP_REQUEST_PARAM_SIGN)){
                request.setParameter(name, httpRequest.getParameter(name));
            }
        }
        return request;
    }

    //改写toString方法
    public static String sortParameters(Map<String, Object> parameters){
        // 将加入两个id的key集合排序
        Set<String> keySet = parameters.keySet();
        List<String> paramkeys = new ArrayList<String>(keySet);
        Collections.sort(paramkeys);

        // 生成一个新的map将两个id放进去
        Map<String, Object> newParams = new HashMap<String, Object>(parameters);

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
        paramBuilder = new StringBuilder(encode(param,"UTF-8"));
        return paramBuilder.toString();
    }

    public static String encode(String src,String encodeType) {
        try {
            return java.net.URLEncoder.encode(src, encodeType);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return src;
        }
    }

    public static String decode(String src,String encodeType) {
        try {
            return java.net.URLDecoder.decode(src,encodeType);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return src;
        }
    }

}
