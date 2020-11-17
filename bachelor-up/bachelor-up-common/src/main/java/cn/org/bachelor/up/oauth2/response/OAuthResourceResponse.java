package cn.org.bachelor.up.oauth2.response;


import cn.org.bachelor.up.oauth2.common.exception.OAuthProblemException;

/**
 * Created by team bachelor on 15/5/20.
 */
public class OAuthResourceResponse  extends OAuthClientResponse {

    public String getBody() {
        return body;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getContentType(){
        return contentType;
    }

    @Override
    protected void setBody(String body) throws OAuthProblemException {
        this.body = body;
    }

    @Override
    protected void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    protected void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    protected void init(String body, String contentType, int responseCode) throws OAuthProblemException {
        this.setBody(body);
        this.setContentType(contentType);
        this.setResponseCode(responseCode);
    }

}
