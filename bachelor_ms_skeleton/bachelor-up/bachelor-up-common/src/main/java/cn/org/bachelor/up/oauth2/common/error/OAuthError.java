package cn.org.bachelor.up.oauth2.common.error;

/**
 * Created by team bachelor on 15/5/20.
 */
public abstract class OAuthError {
    public static final String OAUTH_ERROR = "error";
    public static final String OAUTH_ERROR_DESCRIPTION = "error_description";
    public static final String OAUTH_ERROR_URI = "error_uri";

    public OAuthError() {
    }

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
