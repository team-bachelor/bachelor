package cn.org.bachelor.iam.oauth2.utils;


import cn.org.bachelor.iam.oauth2.key.MD5;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by team bachelor on 2015/5/21.
 */
public class SignatureUtil {
    private static final String secret = "bachelor_up"; //加密串


    /**
     * 参数请传入前验证，可以把参数异常抛给客户端程序
     * 生成签名
     *
     * @param query ？后的字符串 没有传入""
     * @return
     */
    public static String createSignature(String query) {
        StringBuffer keys = new StringBuffer();
        keys = keys.append(secret).append(query).append(secret);

        return MD5.getEncryptResult(keys.toString()).toLowerCase();
    }

//    public static void main(String[] args) {
////        String s = SignatureUtil.createSignature("access_token=A2B839FfYFfMYGvnX5wx2B5jX4Q8CQWPcl/LyoJQ2ZSRynyK7KUxSORNW8yg8xGIvB4MtZ/5vujuf5iRoMVHka0C2BtSzImxfFNIB6tqDARqqK2BcCykF1V2Z1CLA8OoryGpEHLB2sr8sbQ4gyMLKbyMUPdJ6I6RgIjsQeY4P2a4lrg4hOtXT5xqcoW9wuTL4sFaAH9EypmD/qBhxjkK1dF23FytJLi9/5cF6Jr8ETh2imQQIyckecHaAnLy1x6HzxrDWale2K/sHtHZFRBEmb912Bx9lSMQ32BW/lrTcSbTYPNNnk152BIADe52DM1hLmqLmk5y/m7AlzFAUpjM3O1Pm82BEQ==");
////        System.out.println(s);
//        List<String> s = new ArrayList<>();
//        s.add("1");
//        s.add("@");
//        s.add("3");
//        System.out.println(String.join("&", s));
//    }

    /**
     * 参数请传入前验证，可以把参数异常抛给客户端程序
     * 生成签名
     *
     * @param query  ？后的字符串 没有传入""
     * @param secret 密钥 必填项
     * @return
     */
    //TODO 什么鬼？这种加密方式绝了，要改掉
    public static String createSignature(String query, String secret) {
        StringBuffer keys = new StringBuffer();
        keys = keys.append(secret).append(query).append(secret);

        return MD5.getEncryptResult(keys.toString()).toLowerCase();
    }

    //改写toString方法
    public static String sortParameters(Map<String, Object> parameters) {
        // 将加入两个id的key集合排序
        List<String> keys = new ArrayList<String>(parameters.keySet());
        Collections.sort(keys);
        // 生成一个新的map将两个id放进去
        Map<String, Object> newParams = new HashMap<String, Object>(parameters);
        // 根据新生成的map和排序后的key集合生成访问字符串并urlencode
        List<String> paires = new ArrayList<>();
        for (String key : keys) {
            String value = newParams.get(key).toString();
            if (value != null && !"".equals(value)) {
                paires.add(key + "=" + value);
            }
        }
        return encode(String.join("&", paires));
    }

    public static String encode(String src) {
        return encode(src, "UTF-8");
    }

    public static String encode(String src, String encodeType) {
        try {
            return java.net.URLEncoder.encode(src, encodeType);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return src;
        }
    }

    public static String decode(String src, String encodeType) {
        try {
            return java.net.URLDecoder.decode(src, encodeType);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return src;
        }
    }

}
