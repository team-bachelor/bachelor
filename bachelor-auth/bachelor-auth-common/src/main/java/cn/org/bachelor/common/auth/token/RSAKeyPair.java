package cn.org.bachelor.common.auth.token;

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
     * Constructs a key pair from the given public key and private key.
     *
     * <p>Note that this constructor only stores references to the public
     * and private key components in the generated key pair. This is safe,
     * because {@code Key} objects are immutable.
     *
     * @param publicKey  the public key.
     * @param privateKey the private key.
     */
    public RSAKeyPair(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    /**
     * Returns a reference to the public key component of this key pair.
     *
     * @return a reference to the public key.
     */
    public RSAPublicKey getPublic() {
        return publicKey;
    }

    /**
     * Returns a reference to the private key component of this key pair.
     *
     * @return a reference to the private key.
     */
    public RSAPrivateKey getPrivate() {
        return privateKey;
    }
}
