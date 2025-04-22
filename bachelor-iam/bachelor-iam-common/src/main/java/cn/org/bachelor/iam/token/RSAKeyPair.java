package cn.org.bachelor.iam.token;

import java.io.Serializable;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * RSA加密时用到的键值对
 * @author liuzhuo
 */
public class RSAKeyPair implements Serializable {
    // 定义一个私钥对象，使用final修饰，确保在对象创建后不能被重新赋值
    private RSAPrivateKey privateKey;
    // 定义一个公钥对象，使用final修饰，确保在对象创建后不能被重新赋值
    private RSAPublicKey publicKey;

    /**
     * 从给定的公钥和私钥构造一个RSA密钥对对象。
     *
     * <p>注意，此构造函数仅存储对生成的密钥对中公钥和私钥组件的引用。
     * 这是安全的，因为{@code Key}对象是不可变的。
     *
     * @param publicKey  公钥对象
     * @param privateKey 私钥对象
     */
    public RSAKeyPair(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        // 将传入的公钥赋值给当前对象的公钥属性
        this.publicKey = publicKey;
        // 将传入的私钥赋值给当前对象的私钥属性
        this.privateKey = privateKey;
    }

    /**
     * 返回此密钥对中公钥组件的引用。
     *
     * @return 公钥的引用
     */
    public RSAPublicKey getPublic() {
        // 返回当前对象的公钥属性
        return publicKey;
    }

    /**
     * 返回此密钥对中私钥组件的引用。
     *
     * @return 私钥的引用
     */
    public RSAPrivateKey getPrivate() {
        // 返回当前对象的私钥属性
        return privateKey;
    }
}