package cn.org.bachelor.iam.oauth2.key;

public class Base64 {
    private static org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();

    // 将 s 进行 BASE64 编码
    public static String encode(String s) {
        if (s == null)
            return null;
        return base64.encodeToString(s.getBytes());
    }

    // 将 BASE64 编码的字符串 s 进行解码
    public static String decode(String s) {
        if (s == null)
            return null;
        try {
            byte[] b = base64.decode(s);
            return new String(b);
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
     * @author lishihong
     * @time 20170919 14:39:00
     */
    public static String encodeBase64(byte[] s) {
        return base64.encodeToString(s).replace("+", "2B");
    }

    public static byte[] encode(byte[] s) {
        if (s == null)
            return null;
        return base64.encode(s);
    }

    public static byte[] decode(byte[] s) {
        if (s == null)
            return null;
        return base64.decode(s);
    }
}
