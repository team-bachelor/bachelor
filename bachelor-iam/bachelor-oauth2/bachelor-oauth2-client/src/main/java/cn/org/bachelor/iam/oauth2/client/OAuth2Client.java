/**
 * 
 */
package cn.org.bachelor.iam.oauth2.client;

import cn.org.bachelor.iam.oauth2.OAuthConstant;
import cn.org.bachelor.iam.oauth2.client.exception.GetAccessTokenException;
import cn.org.bachelor.iam.oauth2.client.exception.GetUserInfoException;
import cn.org.bachelor.iam.oauth2.client.model.OAuth2ClientCertification;
import cn.org.bachelor.iam.oauth2.client.util.ClientConstant;
import cn.org.bachelor.iam.oauth2.client.util.ClientUtil;
import cn.org.bachelor.iam.oauth2.client.util.StateGenerator;
import cn.org.bachelor.iam.oauth2.client.util.UrlExpProcessor;
import cn.org.bachelor.iam.oauth2.exception.OAuthBusinessException;
import cn.org.bachelor.iam.oauth2.key.Base64;
import cn.org.bachelor.iam.oauth2.request.DefaultOAuthRequest;
import cn.org.bachelor.iam.oauth2.request.DefaultOAuthRequest.TokenRequestBuilder;
import cn.org.bachelor.iam.oauth2.request.DefaultOAuthResourceRequest;
import cn.org.bachelor.iam.oauth2.request.types.GrantType;
import cn.org.bachelor.iam.oauth2.response.OAuthAccessTokenResponse;
import cn.org.bachelor.iam.oauth2.response.OAuthResourceResponse;
import cn.org.bachelor.iam.oauth2.utils.StringUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;


/**
 * @author team bachelor
 *
 */
public class OAuth2Client {
    private static UrlExpProcessor urlExpProcessor;
    private HttpServletResponse response;
    private HttpServletRequest request;
    private OAuth2CientConfig config;
    private JsonObject person;
    private String url;
    public static final String defaultConfigFileName = "OAuth2-config.properties";
    private static Logger logger = LoggerFactory.getLogger(OAuth2Client.class);

	private SignSecurityOAuthClient oAuthClient;

	public OAuth2Client(OAuth2CientConfig config, HttpServletRequest request2,
						HttpServletResponse response2) {
		this.config=config;
		this.request=request2;
		this.response=response2;
		this.url=request.getRequestURL().toString();
		this.oAuthClient = new SignSecurityOAuthClient(new URLConnectionClient());
	}

	/**
	 * 获取授权码
	 * @return
	 */
	public String getAuthrizationCode() {
//		logger.info("进入getAuthrizationCode");
		String code=request.getParameter("code");
		boolean isRedirectURL = isRedirectURL();
		logger.info("进入getAuthrizationCode，code="+code + "，isRedirectURL=" + isRedirectURL);
		if(!isRedirectURL){
//			return null;
		}
		return code;
	}

	/**
	 * 获取phone_id
	 * @return
	 */
	public String getPhoneId() {
//		logger.info("getPhoneId");
		String phoneId=request.getParameter("phone_id");
		logger.info("getPhoneId，phone_id="+phoneId);
		return phoneId;
	}

	private boolean isRedirectURL() {
		logger.info("isRedirectURL()  url=" + url + ", config.redirecturl=" + config.getLoginRedirectURL());
		if(url.equals(config.getLoginRedirectURL())){
			return true;
		}else if(!config.getLoginRedirectURL().endsWith("/")){
			return url.equals(config.getLoginRedirectURL()+"/");
		}
		return false;
	}


	/**
	 * 引导用户授权
	 * @throws IOException 
	 */
	public void toGetAuthrizationCode() throws IOException {
		toGetAuthrizationCode(null);
	}

	/**
	 * 引导用户授权
	 * @throws IOException 
	 */
	public void toGetAuthrizationCode(HttpServletRequest request) throws IOException {
		logger.info("进入toGetAuthrizationCode");
		String phoneId = getPhoneId();
//		StringBuffer originalURL=new StringBuffer(url);
//		String quering=getParamUrl(request,request.getCharacterEncoding(),null);
//		if(quering.length()>0){
//			originalURL.append("?").append(quering);
//		}
//		request.getSession().setAttribute(UpClientConstant.ORIGINALURL, originalURL.toString());
        StringBuilder url = new StringBuilder(config.getAsURL().getAuthorize());
        String m = config.getAsURL().getAuthorize().contains("?") ? "&" : "?";
        url.append(m);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(phoneId)) {
            url.append("phone_id=").append(URLEncoder.encode(phoneId, "utf-8")).append("&");
        }
        url.append("client_id=").append(URLEncoder.encode(config.getId(), "utf-8"));
        url.append("&redirect_uri=").append(URLEncoder.encode(config.getLoginRedirectURL(), "utf-8"));
        url.append("&response_type=code");
        url.append("&state=").append(URLEncoder.encode(getState(request), "utf-8"));
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
        response.sendRedirect(_url);
        logger.info("退出toGetAuthrizationCode，url=" + _url);
    }

	public String refreshAccessToken(String currentRefreshToken){
		String accessToken = "";
		String refreshToken = "";
		String expiration = "";
		logger.info("refresh accesstoken,{}",refreshToken);
		try {
			TokenRequestBuilder builder = DefaultOAuthRequest
							.tokenLocation(config.getAsURL().getAccessToken())
							.setGrantType(GrantType.REFRESH_TOKEN)
							.setClientId(config.getId())
							.setClientSecret(config.getSecret())
							.setRefreshToken(currentRefreshToken)
							.setRedirectURI(config.getLoginRedirectURL());
			DefaultOAuthRequest accessTokenRequest = builder.buildQueryMessage();
			logger.info("call access_token_url to refreshtoken接口" + builder);
			OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(accessTokenRequest, OAuthConstant.HttpMethod.POST);
			accessToken = oAuthResponse.getAccessToken();
			refreshToken = oAuthResponse.getRefreshToken();
			expiration = oAuthResponse.getExpiration();
		}catch (OAuthBusinessException e) {
			logger.error("获取令牌信息错误=======>",e);
			throw new GetAccessTokenException(e.getDescription());
		} catch (Exception e) {
			logger.error("获取令牌信息错误=======>",e);
			throw new GetAccessTokenException(e);
		}

		String openId = "";
		String userId = "";
		String personStr;
		try{
			String userInfoUrl = config.getAsURL().getUserInfo();
			DefaultOAuthResourceRequest userInfoRequest = new DefaultOAuthResourceRequest(userInfoUrl, OAuthConstant.HttpMethod.GET);
			userInfoRequest.setAccessToken(accessToken);
			OAuthResourceResponse resourceResponse = oAuthClient.resource(userInfoRequest, OAuthResourceResponse.class);
			personStr = resourceResponse.getBody();
			person = JsonParser.parseString(personStr).getAsJsonObject();
			logger.info("去调用用户信息接口方法  person:" + person);
			openId = this.getJsonValue(person, "openId");
			userId = this.getJsonValue(person, "userId");
		} catch (Exception e) {
			logger.error("获取用户基本信息错误=======>", e);
			throw new GetUserInfoException(e);
		}
		request.getSession().setAttribute(ClientConstant.UP_USER, personStr);
		request.getSession().setAttribute(ClientConstant.UP_USER_ID, userId);
		request.getSession().setAttribute(ClientConstant.UP_OPEN_ID, openId);
		request.getSession().setAttribute(ClientConstant.SESSION_AUTHENTICATION_KEY, new OAuth2ClientCertification(userId, accessToken, refreshToken, expiration));
		ClientUtil.setSession(request.getSession());

		logger.info("当前登录用户 userId:"+ ClientUtil.getCurrentUserId());
		logger.info("登录后的令牌信息：" + request.getSession().getAttribute(ClientConstant.SESSION_AUTHENTICATION_KEY));
		return person.toString();
	}

	/**
	 * 调用API获取用户信息并将用户信息绑定到会话中
	 * @param authrizationCode
	 * @return
	 * @throws Exception 
	 */
	public String bindUserInfo(String authrizationCode) {
		logger.info("去调用用户信息接口方法");
		String accessToken = "";
		String refreshToken = "";
		String expiration = "";
		try {
			TokenRequestBuilder builder = DefaultOAuthRequest
				.tokenLocation(config.getAsURL().getAccessToken())
				.setGrantType(GrantType.AUTHORIZATION_CODE)
				.setClientId(config.getId())
				.setClientSecret(config.getSecret())
				.setCode(authrizationCode)
				.setRedirectURI(config.getLoginRedirectURL());
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
        String openId;
        String userId;
        String personStr;
        String orgId;
        String userName;
        String orgName;
        String deptId;
        String deptName;
        try {
            logger.info("======去调用user_info_url接口，参数：" + "accessToken=" + accessToken);
            logger.info("如果user_info_url已经赋值，那么直接读取，否则去配置文件中读取");
            String userInfoUrl = config.getAsURL().getUserInfo();
            DefaultOAuthResourceRequest userInfoRequest = new DefaultOAuthResourceRequest(userInfoUrl, OAuthConstant.HttpMethod.GET);
            userInfoRequest.setAccessToken(accessToken);
            OAuthResourceResponse resourceResponse = oAuthClient.resource(userInfoRequest, OAuthResourceResponse.class);
            personStr = resourceResponse.getBody();
            logger.info("======返回的user_info:" + personStr);
            person = JsonParser.parseString(personStr).getAsJsonObject();


			logger.info("去调用用户信息接口方法  person:" + person);

            openId = this.getJsonValue(person, "openId");
            userId = this.getJsonValue(person, "userId");
            orgId = this.getJsonValue(person, "orgId");
            userName = this.getJsonValue(person, "userName");
            orgName = this.getJsonValue(person, "orgName");
            deptId = this.getJsonValue(person, "deptId");
            deptName = this.getJsonValue(person, "deptName");
        } catch (Exception e) {
            logger.error("获取用户基本信息错误=======>", e);
            throw new GetUserInfoException(e);
        }
        //检验email  and  tel   ----------start
		/*try{
			logger.info("======获取用户的邮箱 和 电话");
			OAuthResourceClientRequest userInfoRequest = new OAuthResourceClientRequest("user_info", config.getProperty("asserver_url")+"/emailTel", OAuth.HttpMethod.GET);
			userInfoRequest.setParameter("userId",userId);
			OAuthResourceResponse resourceResponse = oAuthClient.resource(userInfoRequest, OAuthResourceResponse.class);
			String personStr2 = resourceResponse.getBody();
			logger.info("======返回的user_info:" + personStr2);
			JsonObject person2 = parser.parse(personStr2).getAsJsonObject();


			logger.info("去调用用户  email  和 tel接口方法  person2:" + person2);

			String email = this.getJsonValue(person2, "email");
			String tel = this.getJsonValue(person2, "tel");

			if(StringUtils.isEmpty(email) && StringUtils.isEmpty(tel)){
				request.setAttribute("prefect", config.getProperty("asserver_url")+"/prefect?userId="+userId+"&&targetURL="+URLEncoder.encode(request.getParameter("target_url"),"utf-8"));
				return null;
			}

		} catch (Exception e) {
			logger.error("获取用户邮箱 电话 错误=======>",e);
			throw new GetUserInfoException(e);
		}*/
		//检验 email  and  tel   ------------end
//		request.getSession().setAttribute(UpClientConstant.UPUSER, new MJsonObject(person));
        request.getSession().setAttribute(ClientConstant.UP_USER, personStr);
        request.getSession().setAttribute(ClientConstant.UP_USER_ID, userId);
        request.getSession().setAttribute(ClientConstant.UP_OPEN_ID, openId);
        request.getSession().setAttribute(ClientConstant.UP_ORG_ID, orgId);
        request.getSession().setAttribute(ClientConstant.UP_USER_NAME, userName);
        request.getSession().setAttribute(ClientConstant.UP_ORG_NAME, orgName);
        request.getSession().setAttribute(ClientConstant.UP_DEPT_ID, deptId);
        request.getSession().setAttribute(ClientConstant.UP_DEPT_NAME, deptName);
        request.getSession().setAttribute(ClientConstant.SESSION_AUTHENTICATION_KEY, new OAuth2ClientCertification(userId, accessToken, refreshToken, expiration));
        ClientUtil.setSession(request.getSession());

		logger.info("当前登录用户 userId:"+ ClientUtil.getCurrentUserId());
		logger.info("登录后的令牌信息：" + request.getSession().getAttribute(ClientConstant.SESSION_AUTHENTICATION_KEY));
		return person.toString();
	}

	/**
	 * 获取当前用户ID
	 * @return
	 */
	public String getUserId() {
		return (String)request.getSession().getAttribute(ClientConstant.UP_USER_ID);
	}

	/**
	 * 是否从OAuth2认证服务器返回的请求，一般情况为出错了
	 * @return
	 */
	public boolean isCallback() {
		if(isRedirectURL()){
			String error=request.getParameter("error");
			if(error!=null){
				return true;
			}
		}
		return false;
	}

	public HttpServletRequest request() {
		return new OAuth2RequestWrapper(request);
	}

	class OAuth2RequestWrapper extends HttpServletRequestWrapper {
	    public OAuth2RequestWrapper(HttpServletRequest request) {
	        super(request);
	    }

	    @Override
		public String getRemoteUser() {
	        return getUserId();
	    }
	}

	private String getParamUrl(HttpServletRequest request, String encoding, List exceptPramList){
        Enumeration paramNames = request.getParameterNames();
        StringBuffer query = new StringBuffer();
        try {
            while (paramNames.hasMoreElements()) {
                String param = (String) paramNames.nextElement();
                if(exceptPramList!=null && exceptPramList.contains(param)){
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

	public boolean toOriginalURL() throws Exception {
		String originalURL=(String) request.getSession().getAttribute(ClientConstant.ORIGINAL_URL);
		logger.debug("SSOClient toOriginalURL:"+originalURL);
		if(originalURL!=null){
			response.sendRedirect(originalURL);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean toTargetURL() throws Exception {
		if(config.isToLoginRedirectURL()){
			response.sendRedirect(config.getLoginRedirectURL());
			return true;
		}
		String targetURL = (String) request.getParameter("target_url");
		logger.info("去访问targetURL:" + targetURL);
		if (targetURL != null) {
			response.sendRedirect(targetURL);
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


	public boolean isExcluded(String patterns) {
		if(urlExpProcessor==null){
			urlExpProcessor=new UrlExpProcessor(patterns);
		}
		boolean result = urlExpProcessor.match(this.url);
		logger.info("进入isExcluded()========>" + urlExpProcessor.getPattern() + ", " + result);
		return result;
	}
	
	public boolean isExcluded(String except_urlpattern, String except_param, HttpServletRequest request) {
		logger.info("进入isExcluded()========>");
		if(except_param != null && !"".equals(except_param)) {
			int idx = except_param.indexOf("=");
			if(idx > 0) {
				String name = except_param.substring(0,idx);
				String value = except_param.substring(idx+1);
				String value_param = request.getParameter(name);
				logger.info("name=" + name + ",value=" + value + ",request:" + value_param);
				if(value.equals(value_param)) {
					return true;
				}
			}
		}
		
		if(urlExpProcessor==null){
			urlExpProcessor=new UrlExpProcessor(except_urlpattern);
		}
		boolean result = urlExpProcessor.match(this.url);
		logger.info(urlExpProcessor.getPattern() + ", " + result);
		return result;
	}

    public boolean isLogin() {
        boolean flag = false;
        Object obj = request.getSession().getAttribute(ClientConstant.SESSION_AUTHENTICATION_KEY);
        if (obj instanceof OAuth2ClientCertification) {
            OAuth2ClientCertification my = (OAuth2ClientCertification) obj;
            String userid = my.getUserid();
            logger.info("isLogin(), userid:" + userid);
            logger.debug("current user:" + userid);
            if (userid != null && !userid.equals("")) {
                flag = true;
            }
        }
        return flag;
    }

    public boolean isLogin(HttpServletRequest req) {
        boolean flag = false;
        Object obj = request.getSession().getAttribute(ClientConstant.SESSION_AUTHENTICATION_KEY);
        logger.info("进入登录验证========> 登录后的令牌信息：=" + obj);
        //logger.info("当前登录用户 userId:" + ClientUtil.getCurrentUserId());
        if (obj instanceof OAuth2ClientCertification) {
            OAuth2ClientCertification my = (OAuth2ClientCertification) obj;
            String userid = my.getUserid();
            boolean tokenValid = tockenValid(req);
            logger.info("isLogin(), userid:" + userid + ",tokenVlid=:" + tokenValid);
            if (userid != null && !userid.equals("") && tokenValid) {
                ClientUtil.setSession(req.getSession());
                flag = true;

            }
        }
        logger.info("登录验证结果=======>flag:" + flag);
        return flag;
    }

    public String[] isCookie() throws Exception {
        Cookie[] cookies = request.getCookies();
        Cookie my = null;
        if (cookies != null) {
            for (Cookie cookie : request.getCookies()) {
                if (ClientConstant.COOKIE_NAME.equals(cookie.getName())) {
                    my = cookie;
                    break;
                }
            }
            if (my == null) {
				return null;
			}
            byte[] b = my.getValue().getBytes(ClientConstant.DEFAULT_CHARSET);
            b = new Base64().decode(b);
            String access = new String(b, ClientConstant.DEFAULT_CHARSET);
            String[] tokens = StringUtils.delimitedListToStringArray(access, ClientConstant.COOKIE_SEPERATOR);
            return tokens;
        } else {
            return null;
        }
    }

    private boolean tockenValid(HttpServletRequest req) {
        Object obj = request.getSession().getAttribute(ClientConstant.SESSION_AUTHENTICATION_KEY);
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
            	logger.debug("tockens invalid,please login again!");
            }
    	}
    	return false;
    }
    
    private String getJsonValue(JsonObject json,String key){
    	JsonElement ele = json.get(key);
    	if(ele==null) {
			return "";
		}
    	return ele.isJsonNull()?"":ele.getAsString();
    }

	/*
	 * 判断访问令牌是否过期
	 * @param accessToken
	 * @return
	 */
	private boolean accessTokenValid(OAuth2ClientCertification my) {

		String expiration = my.getExpiresTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date expirateionDate = null;
		try {
			expirateionDate = sdf.parse(expiration);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if(my.getAccessToken()!=null && !my.getAccessToken().equals("") && (expirateionDate.getTime()-System.currentTimeMillis()>0)){
			return true;
		}
		return false;
	}

	/**
	 * 获取state
	 * @param request
	 * @return
	 */
	private String getState(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String state = StateGenerator.genStateCode();
		session.setAttribute(ClientConstant.OAUTH_STATE, state);
		return state;
	}

	/**
	 * 验证state是否相同
	 * @param request
	 * @return
	 */
	public boolean isValidState(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Object so = session.getAttribute(ClientConstant.OAUTH_STATE);
		if (StringUtils.isEmpty(so)) {
			return true;
		} else if (so.toString().equals(request.getParameter(ClientConstant.OAUTH_STATE))) {
			session.removeAttribute(ClientConstant.OAUTH_STATE);
			return true;
		}
		return false;
	}

}
