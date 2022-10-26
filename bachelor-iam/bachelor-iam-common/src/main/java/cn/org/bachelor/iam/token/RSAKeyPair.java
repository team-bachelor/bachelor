package cn.org.bachelor.iam.token;

import java.io.Serializable;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/10/25
 */
public class RSAKeyPair implements Serializable {
    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    /**
     * Constructs a cn.org.bachelor.iam.oauth2.key pair from the given public cn.org.bachelor.iam.oauth2.key and private cn.org.bachelor.iam.oauth2.key.
     *
     * <p>Note that this constructor only stores references to the public
     * and private cn.org.bachelor.iam.oauth2.key components in the generated cn.org.bachelor.iam.oauth2.key pair. This is safe,
     * because {@code Key} objects are immutable.
     *
     * @param publicKey  the public cn.org.bachelor.iam.oauth2.key.
     * @param privateKey the private cn.org.bachelor.iam.oauth2.key.
     */
    public RSAKeyPair(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    /**
     * Returns a reference to the public cn.org.bachelor.iam.oauth2.key component of this cn.org.bachelor.iam.oauth2.key pair.
     *
     * @return a reference to the public cn.org.bachelor.iam.oauth2.key.
     */
    public RSAPublicKey getPublic() {
        return publicKey;
    }

    /**
     * Returns a reference to the private cn.org.bachelor.iam.oauth2.key component of this cn.org.bachelor.iam.oauth2.key pair.
     *
     * @return a reference to the private cn.org.bachelor.iam.oauth2.key.
     */
    public RSAPrivateKey getPrivate() {
        return privateKey;
    }
}
