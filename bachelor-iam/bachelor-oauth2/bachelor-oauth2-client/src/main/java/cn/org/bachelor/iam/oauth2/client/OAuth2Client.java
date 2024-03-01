/**
 *
 */
package cn.org.bachelor.iam.oauth2.client;

import cn.org.bachelor.iam.IamConstant;
import cn.org.bachelor.iam.oauth2.OAuthConstant;
import cn.org.bachelor.iam.oauth2.client.exception.GetAccessTokenException;
import cn.org.bachelor.iam.oauth2.client.exception.GetUserInfoException;
import cn.org.bachelor.iam.oauth2.client.model.OAuth2ClientCertification;
import cn.org.bachelor.iam.oauth2.client.util.ClientHelper;
import cn.org.bachelor.iam.oauth2.client.util.ClientRequestInfo;
import cn.org.bachelor.iam.oauth2.client.util.UrlExpProcessor;
import cn.org.bachelor.iam.oauth2.exception.OAuthBusinessException;
import cn.org.bachelor.iam.oauth2.request.DefaultOAuthRequest;
import cn.org.bachelor.iam.oauth2.request.DefaultOAuthRequest.TokenRequestBuilder;
import cn.org.bachelor.iam.oauth2.request.DefaultOAuthResourceRequest;
import cn.org.bachelor.iam.oauth2.request.types.GrantType;
import cn.org.bachelor.iam.oauth2.response.OAuthAccessTokenResponse;
import cn.org.bachelor.iam.oauth2.response.OAuthResourceResponse;
import cn.org.bachelor.iam.oauth2.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;

import static cn.org.bachelor.iam.IamConstant.OAUTH_CB_STATE;


/**
 * @author team bachelor
 *
 */
public class OAuth2Client implements IamClient {
    private static UrlExpProcessor urlExpProcessor;
    private OAuth2ClientConfig config;
    private JSONObject person;
    private ClientRequestInfo info;
    public static final String defaultConfigFileName = "OAuth2-config.properties";
    private static Logger logger = LoggerFactory.getLogger(OAuth2Client.class);

    private SignSecurityOAuthClient oAuthClient;

    public OAuth2Client(OAuth2ClientConfig config, ClientRequestInfo info) {
        this.config = config;
        this.info = info;
        this.oAuthClient = new SignSecurityOAuthClient(new URLConnectionClient());
    }

    /**
     * 获取授权码
     * @return
     */
    @Override
    public String getAuthorizationCode() {
//		logger.info("进入getAuthorizationCode");
        String code = info.getCode();
        boolean isRedirectURL = isRedirectURL();
        logger.info("进入getAuthorizationCode，code=" + code + "，isRedirectURL=" + isRedirectURL);
        if (!isRedirectURL) {
//			return null;
        }
        return code;
    }

    /**
     * 获取phone_id
     * @return
     */
    @Override
    public String getPhoneId() {
        String phoneId = info.getPhoneId();
        logger.info("getPhoneId，phone_id=" + phoneId);
        return phoneId;
    }

    private boolean isRedirectURL() {
        logger.info("isRedirectURL()  url=" + info.getUrl() + ", config.redirecturl=" + config.getLoginRedirectURL());
        if (info.getUrl().equals(config.getLoginRedirectURL())) {
            return true;
        } else if (!config.getLoginRedirectURL().endsWith("/")) {
            return info.getUrl().equals(config.getLoginRedirectURL() + "/");
        }
        return false;
    }

    /**
     * 引导用户授权
     * @throws IOException
     */
    @Override
    public void toGetAuthorizationCode(HttpServletRequest request) throws IOException {
        logger.info("进入toGetAuthorizationCode");
        String phoneId = getPhoneId();
        StringBuilder url = new StringBuilder(config.getAsURL().getAuthorize());
        String m = config.getAsURL().getAuthorize().contains("?") ? "&" : "?";
        url.append(m);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(phoneId)) {
            url.append("phone_id=").append(URLEncoder.encode(phoneId, "utf-8")).append("&");
        }
        url.append("client_id=").append(URLEncoder.encode(config.getId(), "utf-8"));
        url.append("&redirect_uri=").append(URLEncoder.encode(config.getLoginRedirectURL(), "utf-8"));
        url.append("&response_type=code");
        url.append("&").append(OAUTH_CB_STATE).append("=").append(URLEncoder.encode(ClientHelper.getState(request), "utf-8"));
        //目标回调地址
        if (request != null) {
            String targetURL = StringUtils.isEmpty(config.getTargetURL()) ?
                    request.getRequestURL().toString() :
                    config.getTargetURL();
            if (targetURL != null) {
                StringBuffer targetURLBuf = new StringBuffer();
                Enumeration<String> parameterNames = request.getParameterNames();
                if (parameterNames.hasMoreElements()) {
                    if (targetURL.endsWith("/")) {
                        targetURL = targetURL.substring(0, targetURL.length() - 1) + "?";
                    } else {
                        targetURL = targetURL + "?";
                    }
                }
                targetURLBuf.append(targetURL);
                while (parameterNames.hasMoreElements()) {
                    String key = parameterNames.nextElement();
                    URLEncoder.encode(request.getParameter(key), "utf-8");
                    targetURLBuf.append(key).append("=").append(request.getParameter(key));
                    if (parameterNames.hasMoreElements()) {
                        targetURLBuf.append("&");
                    }
                }
                url.append("&target_url=").append(URLEncoder.encode(targetURLBuf.toString(), "utf-8"));
            }
        }
        String _url = url.toString();
        ClientHelper.sendRedirect(_url);
        logger.info("退出toGetAuthorizationCode，url=" + _url);
    }

    private JSONObject bindUser(TokenRequestBuilder builder) {
        String accessToken = "";
        String refreshToken = "";
        String expiration = "";
        try {
            logger.info("调用用户信息接口方法");
            DefaultOAuthRequest accessTokenRequest = builder.buildQueryMessage();
            logger.info("======去调用access_token_url接口，参数：" + builder);
            OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(accessTokenRequest, OAuthConstant.HttpMethod.POST);
            accessToken = oAuthResponse.getAccessToken();
            refreshToken = oAuthResponse.getRefreshToken();
            expiration = oAuthResponse.getExpiration();
        } catch (OAuthBusinessException e) {
            logger.error("获取令牌信息错误=======>", e);
            throw e;
        } catch (Exception e) {
            logger.error("获取令牌信息错误=======>", e);
            throw new GetAccessTokenException(e);
        }
        String userId = "";
        String personStr = "";
        String openId = "";
        try {
            logger.info("======调用user_info_url接口，参数：" + "accessToken=" + accessToken);
            logger.info("如果user_info_url已经赋值，那么直接读取，否则去配置文件中读取");
            String userInfoUrl = config.getAsURL().getUserInfo();
            DefaultOAuthResourceRequest userInfoRequest = new DefaultOAuthResourceRequest(userInfoUrl, OAuthConstant.HttpMethod.GET);
            userInfoRequest.setAccessToken(accessToken);
            OAuthResourceResponse resourceResponse = oAuthClient.resource(userInfoRequest, OAuthResourceResponse.class);
            personStr = resourceResponse.getBody();
            logger.info("======返回的user_info:" + personStr);
            person = JSONObject.parseObject(personStr);
            userId = this.getJsonValue(person, "userId");
            openId = this.getJsonValue(person, "openId");
        } catch (Exception e) {
            logger.error("获取用户基本信息错误=======>", e);
            throw new GetUserInfoException(e);
        }
        ClientHelper.setUserJsonString(personStr);
        ClientHelper.setCredential(
                new OAuth2ClientCertification(userId, accessToken, refreshToken, IamClient.parseExpireTime(expiration)));

        logger.info("当前登录用户 userId:" + ClientHelper.getCurrentUserId());
        logger.info("登录后的令牌信息：" + ClientHelper.getCredential());
        return person;
    }

    @Override
    public JSONObject refreshAccessToken(String currentRefreshToken) {
        TokenRequestBuilder builder = DefaultOAuthRequest
                .tokenLocation(config.getAsURL().getAccessToken())
                .setGrantType(GrantType.REFRESH_TOKEN)
                .setClientId(config.getId())
                .setClientSecret(config.getSecret())
                .setRefreshToken(currentRefreshToken)
                .setRedirectURI(config.getLoginRedirectURL());
        return bindUser(builder);
    }

    /**
     * 调用API获取用户信息并将用户信息绑定到会话中
     * @param authorizationCode
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject bindUser2Session(String authorizationCode) {
        TokenRequestBuilder builder = DefaultOAuthRequest
                .tokenLocation(config.getAsURL().getAccessToken())
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId(config.getId())
                .setClientSecret(config.getSecret())
                .setCode(authorizationCode)
                .setRedirectURI(config.getLoginRedirectURL());
        return bindUser(builder);
    }


    /**
     * 是否从OAuth2认证服务器返回的请求，一般情况为出错了
     * @return
     */
    @Override
    public boolean isCallback() {
        if (isRedirectURL()) {
            String error = info.getError();
            if (error != null) {
                return true;
            }
        }
        return false;
    }

    private String getParamUrl(HttpServletRequest request, String encoding, List exceptPramList) {
        Enumeration paramNames = request.getParameterNames();
        StringBuffer query = new StringBuffer();
        try {
            while (paramNames.hasMoreElements()) {
                String param = (String) paramNames.nextElement();
                if (exceptPramList != null && exceptPramList.contains(param)) {
                    continue;
                }
                String[] values = request.getParameterValues(param);
                for (int i = 0; i < values.length; i++) {
                    query.append('&');
                    try {
                        query.append(URLEncoder.encode(param, encoding));
                    } catch (Exception e) {
                        logger.error("getParamUrl error !", e);
                    }

                    query.append('=');

                    try {
                        query.append(URLEncoder.encode(values[i], encoding));
                    } catch (Exception e) {
                        query.append(URLEncoder.encode(values[i], System.getProperty("file.encoding")));
                    }
                }
            }
            if (query.length() > 0) {
                query.replace(0, 1, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return query.toString();
    }

//    @Override
//    public boolean toOriginalURL() throws Exception {
//        String originalURL = (String) ClientHelper.getFromSession(IamConstant.ORIGINAL_URL);
//        logger.debug("SSOClient toOriginalURL:" + originalURL);
//        if (originalURL != null) {
//            ClientHelper.sendRedirect(originalURL);
//            return true;
//        } else {
//            return false;
//        }
//    }

    @Override
    public boolean toTargetURL() throws Exception {
        if (config.isToLoginRedirectURL()) {
            ClientHelper.sendRedirect(config.getLoginRedirectURL());
            return true;
        }
        String targetURL = info.getTargetUrl();
        logger.info("去访问targetURL:" + targetURL);
        if (targetURL != null) {
            ClientHelper.sendRedirect(targetURL);
            return true;
        }
        return false;
    }

    //新增跳转代码-----------star
	/*public boolean prefect() throws Exception{
		String prefect = (String) request.getAttribute("prefect");
		if(prefect != null){
			response.sendRedirect(prefect);
			return true;
		}
		return false;
	}*/
    //新增跳转代码-----------end


//    @Override
//    public boolean isExcluded(String patterns) {
//        if (urlExpProcessor == null) {
//            urlExpProcessor = new UrlExpProcessor(patterns);
//        }
//        boolean result = urlExpProcessor.match(info.getUrl());
//        logger.info("进入isExcluded()========>" + urlExpProcessor.getPattern() + ", " + result);
//        return result;
//    }

    @Override
    public boolean isExcluded(String except_url_pattern, String except_param, HttpServletRequest request) {
        logger.info("进入isExcluded()========>");
        if (except_param != null && !"".equals(except_param)) {
            int idx = except_param.indexOf("=");
            if (idx > 0) {
                String name = except_param.substring(0, idx);
                String value = except_param.substring(idx + 1);
                String value_param = request.getParameter(name);
                logger.info("name=" + name + ",value=" + value + ",request:" + value_param);
                if (value.equals(value_param)) {
                    return true;
                }
            }
        }

        if (urlExpProcessor == null) {
            urlExpProcessor = new UrlExpProcessor(except_url_pattern);
        }
        boolean result = urlExpProcessor.match(info.getUrl());
        logger.info(urlExpProcessor.getPattern() + ", " + result);
        return result;
    }

//    @Override
//    public boolean isLogin() {
//        boolean flag = false;
//        Object obj = ClientHelper.getCredential();
//        if (obj instanceof OAuth2ClientCertification) {
//            OAuth2ClientCertification my = (OAuth2ClientCertification) obj;
//            String userid = my.getUserid();
//            logger.info("isLogin(), userid:" + userid);
//            logger.debug("current user:" + userid);
//            if (userid != null && !userid.equals("")) {
//                flag = true;
//            }
//        }
//        return flag;
//    }

    @Override
    public boolean isLogin(HttpServletRequest req) {
        boolean flag = false;
        Object obj = ClientHelper.getCredential();
        logger.info("进入登录验证========> 登录后的令牌信息：=" + obj);
        //logger.info("当前登录用户 userId:" + ClientUtil.getCurrentUserId());
        if (obj instanceof OAuth2ClientCertification) {
            OAuth2ClientCertification my = (OAuth2ClientCertification) obj;
            String userid = my.getUserid();
            boolean tokenValid = tokenValid(req);
            logger.info("isLogin(), userid:" + userid + ",tokenVlid=:" + tokenValid);
            if (userid != null && !userid.equals("") && tokenValid) {
                ClientHelper.setSession(req.getSession());
                flag = true;

            }
        }
        logger.info("登录验证结果=======>flag:" + flag);
        return flag;
    }

//    @Override
//    public String[] isCookie() throws Exception {
//        Cookie[] cookies = info.getCookies();
//        Cookie my = null;
//        if (cookies != null) {
//            for (Cookie cookie : info.getCookies()) {
//                if (IamConstant.COOKIE_NAME.equals(cookie.getName())) {
//                    my = cookie;
//                    break;
//                }
//            }
//            if (my == null) {
//                return null;
//            }
//            byte[] b = my.getValue().getBytes(IamConstant.DEFAULT_CHARSET);
//            b = new Base64().decode(b);
//            String access = new String(b, IamConstant.DEFAULT_CHARSET);
//            String[] tokens = StringUtils.delimitedListToStringArray(access, IamConstant.COOKIE_SEPARATOR);
//            return tokens;
//        } else {
//            return null;
//        }
//    }

    private boolean tokenValid(HttpServletRequest req) {
        Object obj = ClientHelper.getCredential();
        if (!(obj instanceof OAuth2ClientCertification)) {
            return false;
        }
        OAuth2ClientCertification my = (OAuth2ClientCertification) obj;
        if (accessTokenValid(my)) {
            return true;
        } else {
            try {
                req.getSession(false).invalidate();
            } catch (IllegalStateException e) {
                logger.debug("tokens invalid,please login again!");
            }
        }
        return false;
    }

//    public static void main(String[] args) {
//        String i = "{\"openId\":\"ZDc5NTQ3MzMzN2Q5NDlmZWIyODA0OWI5MjQxMzQxZTFANmNmZGYwMGI1NGQ0NDFiYmI3ODg5ZGM2MmNkNTA3OWI=\",\"userId\":\"d795473337d949feb28049b9241341e1\",\"username\":\"工业互联网管理员\",\"account\":\"dy_ii\"}";
//        JSONObject person = JSONObject.parseObject(i);
//
//
//        logger.info("去调用用户信息接口方法  person:" + person);
//
//        getJsonValue(person, "openId");
//        getJsonValue(person, "userId");
//        getJsonValue(person, "orgId");
//        getJsonValue(person, "userName");
//        getJsonValue(person, "orgName");
//        getJsonValue(person, "deptId");
//        getJsonValue(person, "deptName");
//    }

    private static String getJsonValue(JSONObject json, String key) {
        String ele = json.getString(key);
        return ele == null ? "" : ele;
    }

    /*
     * 判断访问令牌是否过期
     * @param accessToken
     * @return
     */
    private boolean accessTokenValid(OAuth2ClientCertification my) {
        if (my.getAccessToken() != null && !my.getAccessToken().equals("") && (my.getExpiresTime().getTime() - System.currentTimeMillis() > 0)) {
            return true;
        }
        return false;
    }


    /**
     * 验证state是否相同
     * @param request
     * @return
     */
    @Override
    public boolean isValidState(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Object so = session.getAttribute(IamConstant.OAUTH_STATE);
        if (StringUtils.isEmpty(so)) {
            return true;
        } else if (so.toString().equals(request.getParameter(OAUTH_CB_STATE))) {
            session.removeAttribute(IamConstant.OAUTH_STATE);
            return true;
        }
        return false;
    }

}
