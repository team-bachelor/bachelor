package cn.org.bachelor.iam.token;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA键值创建器，用于生成RSA密钥对，获取公钥和私钥的字符串表示，以及根据字符串还原公钥和私钥对象。
 * @author liuzhuo
 */
public class RSAKeyCreator {

    /**
     * 定义RSA算法名称，用于密钥生成和转换。
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 从RSAKeyPair对象中获取公钥的Base64编码字符串。
     *
     * @param keyPair 包含公钥和私钥的RSAKeyPair对象
     * @return 公钥的Base64编码字符串
     * @throws Exception 若在获取公钥过程中出现异常
     */
    public static String getPublicKey(RSAKeyPair keyPair) throws Exception {
        // 获得map中的公钥对象 转为key对象
        // 编码返回字符串
        return Base64.encodeBase64(keyPair.getPublic().getEncoded());
    }

    /**
     * 从RSAKeyPair对象中获取私钥的Base64编码字符串。
     *
     * @param keyPair 包含公钥和私钥的RSAKeyPair对象
     * @return 私钥的Base64编码字符串
     * @throws Exception 若在获取私钥过程中出现异常
     */
    public static String getPrivateKey(RSAKeyPair keyPair) throws Exception {
        // 获得map中的私钥对象 转为key对象
        // 编码返回字符串
        return Base64.encodeBase64(keyPair.getPrivate().getEncoded());
    }

    /**
     * 根据Base64编码的公钥字符串还原RSAPublicKey对象。
     *
     * @param key Base64编码的公钥字符串
     * @return 还原后的RSAPublicKey对象
     * @throws Exception 若在还原公钥过程中出现异常
     */
    public static RSAPublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        // 将Base64编码的公钥字符串解码为字节数组
        keyBytes = Base64.decode(key);
        // 创建X509EncodedKeySpec对象，用于存储公钥的编码信息
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // 获取RSA算法的KeyFactory实例
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 根据KeySpec生成RSAPublicKey对象
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 根据Base64编码的私钥字符串还原RSAPrivateKey对象。
     *
     * @param key Base64编码的私钥字符串
     * @return 还原后的RSAPrivateKey对象
     * @throws NoSuchAlgorithmException 若指定的算法不可用
     * @throws InvalidKeySpecException 若密钥规范无效
     */
    public static RSAPrivateKey getPrivateKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes;
        // 将Base64编码的私钥字符串解码为字节数组
        keyBytes = Base64.decode(key);
        // 创建PKCS8EncodedKeySpec对象，用于存储私钥的编码信息
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        // 获取RSA算法的KeyFactory实例
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 根据KeySpec生成RSAPrivateKey对象
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 初始化并生成一个新的RSA密钥对。
     *
     * @return 包含公钥和私钥的RSAKeyPair对象
     * @throws Exception 若在生成密钥对过程中出现异常
     */
    public static RSAKeyPair initKey() throws Exception {
        // 获得对象 KeyPairGenerator 参数 RSA 1024个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        // 初始化密钥对生成器，指定密钥长度为1024位
        keyPairGen.initialize(1024);
        // 通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKeyPair(publicKey, privateKey);
    }

}