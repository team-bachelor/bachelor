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
 * RSA键值创建器
 * @author liuzhuo
 */
public class RSAKeyCreator {

    public static final String KEY_ALGORITHM = "RSA";

    //获得公钥
    public static String getPublicKey(RSAKeyPair keyPair) throws Exception {
        //获得map中的公钥对象 转为key对象
        //编码返回字符串
        return Base64.encodeBase64(keyPair.getPublic().getEncoded());
    }

    //获得私钥
    public static String getPrivateKey(RSAKeyPair keyPair) throws Exception {
        //获得map中的私钥对象 转为key对象
        //编码返回字符串
        return Base64.encodeBase64(keyPair.getPrivate().getEncoded());
    }

    public static RSAPublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static RSAPrivateKey getPrivateKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes;
        keyBytes = Base64.decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    //map对象中存放公私钥
    public static RSAKeyPair initKey() throws Exception {
        //获得对象 KeyPairGenerator 参数 RSA 1024个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();

        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKeyPair(publicKey, privateKey);
    }

//    public static void main(String[] args) {
//        RSAKeyPair keyPair;
//        try {
//            keyPair = initKey();
//            String publicKey = getPublicKey(keyPair);
//            System.out.println(publicKey);
//            String privateKey = getPrivateKey(keyPair);
//            System.out.println(privateKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}