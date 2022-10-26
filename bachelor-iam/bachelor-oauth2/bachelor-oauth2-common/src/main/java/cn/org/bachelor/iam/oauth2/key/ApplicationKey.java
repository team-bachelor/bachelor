package cn.org.bachelor.iam.oauth2.key;

public class ApplicationKey {
	private String publicKey;
	private String privateKey;
	private String randomKey;

	public ApplicationKey(String pub, String pri, String randomKey) {
		this.publicKey = pub;
		this.privateKey = pri;
		this.randomKey = randomKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public String getRandomKey() {
		return randomKey;
	}

}
