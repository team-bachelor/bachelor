package cn.org.bachelor.up.oauth2.response;

import cn.org.bachelor.up.oauth2.validator.OAuthClientValidator;
import cn.org.bachelor.up.oauth2.common.exception.OAuthProblemException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by team bachelor on 15/5/20.
 */
public abstract class OAuthClientResponse {

    protected String body;
    protected String contentType;
    protected int responseCode;

    protected OAuthClientValidator validator;
    protected Map<String, Object> parameters = new HashMap<String, Object>();

    public String getParam(String param) {
        Object value = parameters.get(param);
        return value == null ? null : String.valueOf(value);
    }

    protected abstract void setBody(String body) throws OAuthProblemException;

    protected abstract void setContentType(String contentType);

    protected abstract void setResponseCode(int responseCode);

    protected void init(String body, String contentType, int responseCode) throws OAuthProblemException {
        this.setBody(body);
        this.setContentType(contentType);
        this.setResponseCode(responseCode);
        this.validate();

    }

    protected void validate() throws OAuthProblemException {
        validator.validate(this);
    }

}
