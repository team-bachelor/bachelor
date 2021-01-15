package cn.org.bachelor.up.oauth2.common.token;

/**
 * Created by team bachelor on 15/5/20.
 */
public class BasicOAuthToken implements OAuthToken {
    protected String accessToken;
    protected Long expiresIn;
    protected String refreshToken;
    protected String scope;

    public BasicOAuthToken() {
    }

    public BasicOAuthToken(String accessToken, Long expiresIn, String refreshToken, String scope) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.scope = scope;
    }

    public BasicOAuthToken(String accessToken) {
        this(accessToken, (Long)null, (String)null, (String)null);
    }

    public BasicOAuthToken(String accessToken, Long expiresIn) {
        this(accessToken, expiresIn, (String)null, (String)null);
    }

    public BasicOAuthToken(String accessToken, Long expiresIn, String scope) {
        this(accessToken, expiresIn, (String)null, scope);
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public Long getExpiresIn() {
        return this.expiresIn;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public String getScope() {
        return this.scope;
    }
}
