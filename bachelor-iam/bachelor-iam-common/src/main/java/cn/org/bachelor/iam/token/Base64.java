package cn.org.bachelor.iam.token;

public class Base64 {
    // 将 s 进行 BASE64 编码
    public static String encode(String s) {
        if (s == null)
            return null;
        return java.util.Base64.getEncoder().encodeToString(s.getBytes());
    }

    /**
     * 将 BASE64 编码的字符串 s 进行解码
     *
     * @param s 用于解密的字符串
     * @return 解密后的二进制数组
     */

    public static byte[] decode(String s) {
        if (s == null)
            return null;
        java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
        try {
            byte[] b = decoder.decode(s);
            return b;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据RFC822规定，BASE64Encoder编码每76个字符，会加上一个回车换行
     * 为了避免这个问题，可采用apache的Base64.encodeBase64String
     * <p>
     * 由于http传输时，会将+转成空格，为了解决这个和签名一致问题，将+替换成2B，而不是%2B
     *
     * @param s 用于加密的数组
     * @return 加密后的字符串
     * @author lishihong
     */
    public static String encodeBase64(byte[] s) {
        return java.util.Base64.getEncoder().encodeToString(s).replace("+", "2B");
    }

    public static byte[] encode(byte[] s) {
        if (s == null)
            return null;
        return java.util.Base64.getEncoder().encode(s);
    }

    public static byte[] decode(byte[] s) {
        if (s == null)
            return null;
        return java.util.Base64.getEncoder().encode(s);
    }
}
