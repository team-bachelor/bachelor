package cn.org.bachelor.up.oauth2.common.message.types;

/**
 * Created by team bachelor on 15/5/20.
 */
public enum GrantType {
    AUTHORIZATION_CODE("authorization_code"),
    PASSWORD("password"),
    REFRESH_TOKEN("refresh_token"),
    CLIENT_CREDENTIALS("client_credentials");

    private String grantType;

    private GrantType(String grantType) {
        this.grantType = grantType;
    }

    public String toString() {
        return this.grantType;
    }
}
