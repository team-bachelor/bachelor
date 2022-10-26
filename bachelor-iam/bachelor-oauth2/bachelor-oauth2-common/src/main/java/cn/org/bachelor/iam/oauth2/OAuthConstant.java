package cn.org.bachelor.iam.oauth2;

import cn.org.bachelor.iam.oauth2.request.types.ParameterStyle;
import cn.org.bachelor.iam.oauth2.request.types.TokenType;

/**
 * Created by team bachelor on 15/5/20.
 */
public final class OAuthConstant {
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
    public static final ParameterStyle DEFAULT_PARAMETER_STYLE = ParameterStyle.HEADER;
    public static final TokenType DEFAULT_TOKEN_TYPE = TokenType.BEARER;
    public static final String OAUTH_VERSION_DIFFER = "oauth_signature_method";

    public static final String OAUTH_ERROR = "error";
    public static final String OAUTH_ERROR_DESCRIPTION = "error_description";
    public static final String OAUTH_ERROR_URI = "error_uri";

    public static final String TEMPLATE_REPLACE_STRING ="${error}";

    public OAuthConstant() {
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

    public static String HTTP_REQUEST_PARAM_SIGN = "sign";
    public static String HTTP_REQUEST_PARAM_PHONE_ID = "phone_id";
    public static String HTTP_REQUEST_PARAM_CLIENT_ID = "client_id";
    public static String HTTP_REQUEST_PARAM_OPEN_ID = "open_id";
    public static String HTTP_RESPONSE_PARAM_SUCCESS = "success";

    public static final class ResourceResponse {
        public static final String INVALID_REQUEST = "invalid_request";
        public static final String EXPIRED_TOKEN = "expired_token";
        public static final String INSUFFICIENT_SCOPE = "insufficient_scope";
        public static final String INVALID_TOKEN = "invalid_token";

        public ResourceResponse() {
        }
    }

    public static final class TokenResponse {
        public static final String INVALID_REQUEST = "invalid_request";
        public static final String INVALID_CLIENT = "invalid_client";
        public static final String INVALID_GRANT = "invalid_grant";
        public static final String UNAUTHORIZED_CLIENT = "unauthorized_client";
        public static final String UNSUPPORTED_GRANT_TYPE = "unsupported_grant_type";
        public static final String INVALID_SCOPE = "invalid_scope";

        public TokenResponse() {
        }
    }

    public static final class CodeResponse {
        public static final String INVALID_REQUEST = "invalid_request";
        public static final String UNAUTHORIZED_CLIENT = "unauthorized_client";
        public static final String ACCESS_DENIED = "access_denied";
        public static final String UNSUPPORTED_RESPONSE_TYPE = "unsupported_response_type";
        public static final String INVALID_SCOPE = "invalid_scope";
        public static final String SERVER_ERROR = "server_error";
        public static final String TEMPORARILY_UNAVAILABLE = "temporarily_unavailable";
        public CodeResponse() {
        }
    }
}
