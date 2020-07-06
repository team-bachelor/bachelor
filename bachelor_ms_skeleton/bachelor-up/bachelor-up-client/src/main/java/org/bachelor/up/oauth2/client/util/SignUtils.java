package cn.org.bachelor.up.oauth2.client.util;

import cn.org.bachelor.up.oauth2.key.Base64;
import cn.org.bachelor.up.oauth2.key.RSACoder;
import cn.org.bachelor.up.oauth2.request.OAuthResourceClientRequest;

/**
 * Created by liuzhuo on 2015/4/29.
 */
public class SignUtils {
    /**
     * 根据资源请求生成访问签名
     *
     * @param request   访问请求
     * @param appsecret 应用公钥
     * @return
     */
    public static String createSign(OAuthResourceClientRequest request, String appsecret) throws Exception {
        return new String(Base64.encode(RSACoder.encryptByPublicKey(request.toString().getBytes(), appsecret)));
    }

    /**
     * 根据资源请求生成访问签名
     *
     * @param request   访问请求
     * @return
     */
    public static String createSign(OAuthResourceClientRequest request) throws Exception {
        return new String(Base64.encode(request.toString().getBytes()));
    }

    /**
     * 根据资源请求对象生成网络请求的URL
     *
     * @param request
     * @param appsecret
     * @return
     */
//    public static String getResourceRequestString(ResourceRequest request, String appsecret) {
//        StringBuilder url = new StringBuilder();
//        url.append(request.getUri()).append("?")
//                .append(UpClientConstant.HTTP_REQUEST_PARAM_OPEN_ID).append("=").append(request.getOpenid()).append("&")
//                .append(UpClientConstant.HTTP_REQUEST_PARAM_CLIENT_ID).append("=").append(request.getClientid()).append("&");
//        Map<String, String> params = request.getParams();
//        Set<String> keys = params.keySet();
//        for (String name : keys) {
//            url.append(name).append("=").append(params.get(name)).append("&");
//        }
//        try {
//            url.append(UpClientConstant.HTTP_REQUEST_PARAM_SIGN).append("=").append(createSign(request, appsecret));
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new BusinessException(e);
//        }
//        return url.toString();
//    }
}
