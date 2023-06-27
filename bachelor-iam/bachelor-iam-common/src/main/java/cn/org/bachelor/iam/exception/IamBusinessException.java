package cn.org.bachelor.iam.exception;


import cn.org.bachelor.iam.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by team bachelor on 15/5/20.
 */
public class IamBusinessException extends RuntimeException {
    private String error;
    private String description;
    private String uri;
    private String state;
    private String scope;
    private String redirectUri;
    private int responseStatus;
    private Map<String, String> parameters;

    public IamBusinessException(Throwable cause) {
        super(cause);
    }

    public IamBusinessException(String error) {
        this(error, "");
    }

    public IamBusinessException(String error, String description) {
        super(error + " " + description);
        this.parameters = new HashMap();
        this.description = description;
        this.error = error;
    }

//    public static OAuthBusinessException error(String error) {
//        return new OAuthBusinessException(error);
//    }
//
//    public static OAuthBusinessException error(String error, String description) {
//        return new OAuthBusinessException(error, description);
//    }

    public IamBusinessException description(String description) {
        this.description = description;
        return this;
    }

    public IamBusinessException uri(String uri) {
        this.uri = uri;
        return this;
    }

    public IamBusinessException state(String state) {
        this.state = state;
        return this;
    }

    public IamBusinessException scope(String scope) {
        this.scope = scope;
        return this;
    }

    public IamBusinessException responseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
        return this;
    }

    public IamBusinessException setParameter(String name, String value) {
        this.parameters.put(name, value);
        return this;
    }

    public String getError() {
        return this.error;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUri() {
        return this.uri;
    }

    public String getState() {
        return this.state;
    }

    public String getScope() {
        return this.scope;
    }

    public int getResponseStatus() {
        return this.responseStatus == 0 ? 400 : this.responseStatus;
    }

    public String get(String name) {
        return (String) this.parameters.get(name);
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public String getRedirectUri() {
        return this.redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getMessage() {
        StringBuilder b = new StringBuilder();
        if (!StringUtils.isEmpty(this.error)) {
            b.append(this.error);
        }

        if (!StringUtils.isEmpty(this.description)) {
            b.append(", ").append(this.description);
        }

        if (!StringUtils.isEmpty(this.uri)) {
            b.append(", ").append(this.uri);
        }

        if (!StringUtils.isEmpty(this.state)) {
            b.append(", ").append(this.state);
        }

        if (!StringUtils.isEmpty(this.scope)) {
            b.append(", ").append(this.scope);
        }

        return b.toString();
    }

    public String toString() {
        return this.getClass().getName() + "{error=\'" + this.error + '\'' + ", description=\'" + this.description + '\'' + ", uri=\'" + this.uri + '\'' + ", state=\'" + this.state + '\'' + ", scope=\'" + this.scope + '\'' + ", redirectUri=\'" + this.redirectUri + '\'' + ", responseStatus=" + this.responseStatus + ", parameters=" + this.parameters + '}';
    }
}
