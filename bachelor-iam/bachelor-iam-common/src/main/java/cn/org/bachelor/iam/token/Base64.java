package cn.org.bachelor.iam.token;

/**
 * Base64 工具类，提供了 Base64 编码和解码的相关方法。
 * 这些方法可用于将字符串或字节数组进行 Base64 编码，以及将 Base64 编码的字符串或字节数组进行解码。
 */
public class Base64 {
    /**
     * 将字符串 s 进行 BASE64 编码。
     *
     * @param s 待编码的字符串，如果为 null 则直接返回 null。
     * @return 编码后的字符串
     */
    public static String encode(String s) {
        // 若输入字符串为 null，直接返回 null
        if (s == null)
            return null;
        // 使用 Java 标准库的 Base64 编码器对字符串的字节数组进行编码，并返回编码后的字符串
        return java.util.Base64.getEncoder().encodeToString(s.getBytes());
    }

    /**
     * 将 BASE64 编码的字符串 s 进行解码。
     *
     * @param s 用于解密的字符串，如果为 null 则直接返回 null。
     * @return 解密后的二进制数组，如果解码过程中出现异常则返回 null
     */
    public static byte[] decode(String s) {
        // 若输入字符串为 null，直接返回 null
        if (s == null)
            return null;
        // 获取 Java 标准库的 Base64 解码器
        java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
        try {
            // 对输入字符串进行解码
            byte[] b = decoder.decode(s);
            return b;
        } catch (Exception e) {
            // 若解码过程中出现异常，返回 null
            return null;
        }
    }

    /**
     * 根据 RFC822 规定，BASE64Encoder 编码每 76 个字符，会加上一个回车换行。
     * 为了避免这个问题，可采用 java.util.Base64 进行编码。
     * 由于 http 传输时，会将 + 转成空格，为了解决这个和签名一致问题，将 + 替换成 2B。
     *
     * @param s 用于加密的数组
     * @return 加密后的字符串
     * @author lishihong
     */
    public static String encodeBase64(byte[] s) {
        // 使用 Java 标准库的 Base64 编码器对字节数组进行编码，并将结果中的 + 替换为 2B
        return java.util.Base64.getEncoder().encodeToString(s).replace("+", "2B");
    }

    /**
     * 将字节数组 s 进行 BASE64 编码。
     *
     * @param s 待编码的字节数组，如果为 null 则直接返回 null。
     * @return 编码后的字节数组
     */
    public static byte[] encode(byte[] s) {
        // 若输入字节数组为 null，直接返回 null
        if (s == null)
            return null;
        // 使用 Java 标准库的 Base64 编码器对字节数组进行编码
        return java.util.Base64.getEncoder().encode(s);
    }

    /**
     * 将 BASE64 编码的字节数组 s 进行解码。
     *
     * @param s 用于解密的字节数组，如果为 null 则直接返回 null。
     * @return 解密后的字节数组
     */
    public static byte[] decode(byte[] s) {
        // 若输入字节数组为 null，直接返回 null
        if (s == null)
            return null;
        // 此处原代码使用了编码器进行编码，存在逻辑错误，应使用解码器进行解码
        // 现修正为使用 Java 标准库的 Base64 解码器对字节数组进行解码
        return java.util.Base64.getDecoder().decode(s);
    }
}