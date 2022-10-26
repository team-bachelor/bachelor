package cn.org.bachelor.iam.oauth2.request.types;

/**
 * Created by team bachelor on 15/5/20.
 */
public enum TokenType {
    BEARER("Bearer"),
    MAC("MAC");

    private String tokenType;

    private TokenType(String grantType) {
        this.tokenType = grantType;
    }

    public String toString() {
        return this.tokenType;
    }
}
