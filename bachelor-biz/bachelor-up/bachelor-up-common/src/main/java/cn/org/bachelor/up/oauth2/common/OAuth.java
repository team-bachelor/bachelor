package cn.org.bachelor.up.oauth2.common;

import cn.org.bachelor.up.oauth2.common.message.types.ParameterStyle;
import cn.org.bachelor.up.oauth2.common.message.types.TokenType;

/**
 * Created by team bachelor on 15/5/20.
 */
public final class OAuth {
    public static final String OAUTH_RESPONSE_TYPE = "response_type";
    public static final String OAUTH_CLIENT_ID = "client_id";
    public static final String OAUTH_CLIENT_SECRET = "client_secret";
    public static final String OAUTH_REDIRECT_URI = "redirect_uri";
    public static final String OAUTH_USERNAME = "username";
    public static final String OAUTH_PASSWORD = "password";
    public static final String OAUTH_ASSERTION_TYPE = "assertion_type";
    public static final String OAUTH_ASSERTION = "assertion";
    public static final String OAUTH_SCOPE = "scope";
    public static final String OAUTH_STATE = "state";
    public static final String OAUTH_GRANT_TYPE = "grant_type";
    public static final String OAUTH_HEADER_NAME = "Bearer";
    public static final String OAUTH_CODE = "code";
    public static final String OAUTH_ACCESS_TOKEN = "access_token";
    public static final String OAUTH_EXPIRES_IN = "expires_in";
    public static final String OAUTH_EXPiRATION = "expiration";
    public static final String OAUTH_REFRESH_TOKEN = "refresh_token";
    public static final String OAUTH_TOKEN_TYPE = "token_type";
    public static final String OAUTH_TOKEN = "oauth_token";
    public static final String OAUTH_TOKEN_DRAFT_0 = "access_token";
    public static final String OAUTH_BEARER_TOKEN = "access_token";
    public static final ParameterStyle DEFAULT_PARAMETER_STYLE;
    public static final TokenType DEFAULT_TOKEN_TYPE;
    public static final String OAUTH_VERSION_DIFFER = "oauth_signature_method";

    public OAuth() {
    }

    static {
        DEFAULT_PARAMETER_STYLE = ParameterStyle.HEADER;
        DEFAULT_TOKEN_TYPE = TokenType.BEARER;
    }

    public static final class ContentType {
        public static final String URL_ENCODED = "application/x-www-form-urlencoded";
        public static final String JSON = "application/json";

        public ContentType() {
        }
    }

    public static final class WWWAuthHeader {
        public static final String REALM = "realm";

        public WWWAuthHeader() {
        }
    }

    public static final class HeaderType {
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
        public static final String AUTHORIZATION = "Authorization";

        public HeaderType() {
        }
    }

    public static final class HttpMethod {
        public static final String POST = "POST";
        public static final String GET = "GET";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";

        public HttpMethod() {
        }
    }
}
