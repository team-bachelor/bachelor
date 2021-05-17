package cn.org.bachelor.up.oauth2.rsclient;

import cn.org.bachelor.up.oauth2.Constant;
import cn.org.bachelor.up.oauth2.common.OAuth;
import cn.org.bachelor.up.oauth2.exception.OAuth2AuthenticationException;
import cn.org.bachelor.up.oauth2.request.OAuthResourceClientRequest;
import cn.org.bachelor.up.oauth2.response.OAuthResourceResponse;
import cn.org.bachelor.up.oauth2.rsclient.model.OAuth2RSConfig;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by team bachelor on 15/5/22.
 */
public class AccessTokenClient {

    private HttpServletResponse response;
    private HttpServletRequest request;
    private OAuth2RSConfig config;
    private String accessToken;
    private JsonObject result;
    private JsonParser parser;

    private static Logger logger= LoggerFactory.getLogger(AccessTokenClient.class);

    private SignSecurityOAuthClient oAuthClient;

    public AccessTokenClient(OAuth2RSConfig config, HttpServletRequest request2,
                             HttpServletResponse response2) {
        this.config=config;
        this.request=request2;
        this.response=response2;
        this.accessToken = request2.getParameter(OAuth.OAUTH_ACCESS_TOKEN);
        this.parser = new JsonParser();

        this.oAuthClient = new SignSecurityOAuthClient(new URLConnectionClient());
    }

    /**
     * 验证accessToken
     * @return
     */
   /* public boolean checkASToken() {
        try {
            boolean flag;
            logger.info("AccessTokenClient 进入checkAsToken方法");
            OAuthResourceClientRequest userInfoRequest = new OAuthResourceClientRequest("check_as_token", config.getCheckASTokenURL(), OAuth.HttpMethod.GET);
            userInfoRequest.setParameter(OAuth.OAUTH_ACCESS_TOKEN,accessToken);
            OAuthResourceResponse resourceResponse = oAuthClient.ckeckAsToken(userInfoRequest, OAuthResourceResponse.class);
            String responseBody = resourceResponse.getBody();
            result = parser.parse(responseBody).getAsJsonObject();
            flag = Boolean.valueOf(this.getJsonValue(result,Constant.HTTP_RESPONSE_PARAM_SUCCESS));
            return flag;
        }catch (Exception e) {
            e.printStackTrace();
            throw new OAuth2AuthenticationException(e);
        }
    }*/

    public boolean checkASToken(String rui) {
        try {
            boolean flag;
            logger.info("AccessTokenClient 进入checkAsToken方法");
            OAuthResourceClientRequest userInfoRequest = new OAuthResourceClientRequest("check_as_token", config.getCheckASTokenURL(), OAuth.HttpMethod.GET);
            userInfoRequest.setParameter(OAuth.OAUTH_ACCESS_TOKEN,accessToken);
            userInfoRequest.setParameter("reqpath", rui);
            OAuthResourceResponse resourceResponse = oAuthClient.ckeckAsToken(userInfoRequest, OAuthResourceResponse.class);
            String responseBody = resourceResponse.getBody();
            result = parser.parse(responseBody).getAsJsonObject();
            flag = Boolean.valueOf(this.getJsonValue(result,Constant.HTTP_RESPONSE_PARAM_SUCCESS));
            return flag;
        }catch (Exception e) {
            e.printStackTrace();
            throw new OAuth2AuthenticationException(e);
        }
    }

    private String getJsonValue(JsonObject json,String key){
        JsonElement ele = json.get(key);
        if(ele==null)return "";
        return ele.isJsonNull()?"":ele.getAsString();
    }
}
