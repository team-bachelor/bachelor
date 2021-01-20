package cn.org.bachelor.iam.oauth2.response;

import cn.org.bachelor.iam.oauth2.exception.OAuthBusinessException;
import cn.org.bachelor.iam.oauth2.validator.OAuthClientValidator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by team bachelor on 15/5/20.
 */
public abstract class OAuthResponse {

    protected String body;
    protected String contentType;
    protected int responseCode;

    protected OAuthClientValidator validator;
    protected Map<String, Object> parameters = new HashMap<String, Object>();

    public String getParam(String param) {
        Object value = parameters.get(param);
        return value == null ? null : String.valueOf(value);
    }

    protected abstract void setBody(String body) throws OAuthBusinessException;

    protected abstract void setContentType(String contentType);

    protected abstract void setResponseCode(int responseCode);

    protected void init(String body, String contentType, int responseCode) throws OAuthBusinessException {
        this.setBody(body);
        this.setContentType(contentType);
        this.setResponseCode(responseCode);
        this.validate();

    }

    protected void validate() throws OAuthBusinessException {
        validator.validate(this);
    }

}
