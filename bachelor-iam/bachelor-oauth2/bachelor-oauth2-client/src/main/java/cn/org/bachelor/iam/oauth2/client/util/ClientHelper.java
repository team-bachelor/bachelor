package cn.org.bachelor.iam.oauth2.client.util;

import cn.org.bachelor.iam.IamConstant;
import cn.org.bachelor.iam.oauth2.client.OAuth2CientConfig;
import cn.org.bachelor.iam.oauth2.client.model.OAuth2ClientCertification;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录工具类
 *
 * @author team bachelor
 * @version 1.0
 * @since 1.0
 */
public class ClientHelper {
    private static ThreadLocal<HttpSession> session = new ThreadLocal<>();//存放session
    private static ThreadLocal<HttpServletResponse> response = new ThreadLocal<>();//存放session

    public static OAuth2CientConfig config;//OAuth2LoginFilter.init的时候初始化

    /**
     * 设置session到ThreadLocal中
     *
     * @param session
     */
    public static void setSession(HttpSession session) {
        ClientHelper.session.set(session);
    }

    /**
     * 清空ThreadLocal中的session
     */
    public static void clearSession() {
        session.set(null);
    }


    /**
     * 获取当前登录用户OPENID
     *
     * @return
     */
    public static String getCurrentOpenId() {
        HttpSession session = ClientHelper.session.get();
        if (session == null) return null;
        return (String) session.getAttribute(IamConstant.UP_OPEN_ID);
    }

    public static ClientInfo startClient(HttpServletRequest request,
                                         HttpServletResponse response) {
        setSession(request.getSession());
        ClientHelper.response.set(response);
        ClientInfo info = new ClientInfo();
        info.setUrl(request.getRequestURL().toString());
        info.setCode(request.getParameter("code"));
        info.setPhoneId(request.getParameter("phone_id"));
        info.setError(request.getParameter("error"));
        info.setTargetUrl(request.getParameter("target_url"));
        info.setCookies(request.getCookies());
        return info;
    }

    public static void stopClient() {
        session.set(null);
        response.set(null);
    }

    /**
     * 获取state
     *
     * @param request
     * @return
     */
    public static String getState(HttpServletRequest request) {
        String state = StateGenerator.genStateCode();
        session.get().setAttribute(IamConstant.OAUTH_STATE, state);
        return state;
    }

    /**
     * 获取当前用户ID
     *
     * @return
     */
    public static String getUserId() {
        return (String) session.get().getAttribute(IamConstant.UP_USER_ID);
    }

    public static void sendRedirect(String target) throws IOException {
        response.get().sendRedirect(target);
    }

    public static HttpServletRequest request(HttpServletRequest request) {
        return new OAuth2RequestWrapper(request);
    }

    public static OAuth2ClientCertification getCredential() {
        return (OAuth2ClientCertification) session.get().getAttribute(IamConstant.SESSION_AUTHENTICATION_KEY);
    }

    public static Object getFromSession(String key) {
        return session.get().getAttribute(key);
    }

    public static void setUserJsonString(String userJsonString) {
        session.get().setAttribute(IamConstant.UP_USER, userJsonString);
    }

    public static void setCredential(OAuth2ClientCertification c) {
        session.get().setAttribute(
                IamConstant.SESSION_AUTHENTICATION_KEY, c);
    }

    static class OAuth2RequestWrapper extends HttpServletRequestWrapper {
        public OAuth2RequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getRemoteUser() {
            return getUserId();
        }
    }

    /**
     * 获取当前登录用户ID
     *
     * @return
     */
    public static String getCurrentUserId() {
        HttpSession session = ClientHelper.session.get();
        if (session == null) return null;
        return (String) session.getAttribute(IamConstant.UP_USER_ID);
    }

    /**
     * 获取当前登录人姓名
     *
     * @return
     */
    public static String getCurrentUserName() {
        JSONObject user = getCurrentUser();
        if (user == null) return "";
        return user.containsKey("account") ? user.getString("account") : "";
    }

    /**
     * 获取当前登录人账号
     *
     * @return
     */
    public static String getCurrentAccount() {
        JSONObject user = getCurrentUser();
        if (user == null) return "";
        return user.containsKey("account") ? user.getJSONObject("account").toJSONString() : "";
    }


    /**
     * 获取当前登录用户
     *
     * @return
     */
    public static JSONObject getCurrentUser() {
        String personStr = getCurrentUserStr();
        if (personStr == null) {
            return null;
        }
        JSONObject personJson = JSONObject.parseObject(personStr);
        return personJson;
    }

    public static String getCurrentUserStr() {
        HttpSession session = ClientHelper.session.get();
        if (session == null) return null;
        return String.valueOf(session.getAttribute(IamConstant.UP_USER));
    }


    /**
     * 获取当前令牌
     *
     * @return
     */
    public static String getCurrentAsToken() {
        OAuth2ClientCertification identity = getCurrentIdentity();
        if (identity == null) return "";
        return identity.getAccessToken();
    }

    /**
     * 获取当前刷新令牌
     *
     * @return
     */
    public static String getCurrentRsToken() {
        OAuth2ClientCertification identity = getCurrentIdentity();
        if (identity == null) return "";
        return identity.getRefreshToken();
    }

    /**
     * 获取当前令牌到期日期
     * @return
     */
//	public static String getCurrentAsTokenExpir(){
//		OAuth2ClientCertification identity = getCurrentIdentity();
//		if(identity == null) return null;
//		return identity.getExpiresTime();
//	}


    /**
     * 获取当前OAuth2ClientCertification
     *
     * @return
     */
    public static OAuth2ClientCertification getCurrentIdentity() {
        HttpSession session = ClientHelper.session.get();
        if (session == null) return null;
        return (OAuth2ClientCertification) session.getAttribute(IamConstant.SESSION_AUTHENTICATION_KEY);
    }

    public static void RemoveSession() {
        HttpSession session = ClientHelper.session.get();
        session.removeAttribute(IamConstant.UP_USER);
        session.removeAttribute(IamConstant.UP_USER_ID);
        session.removeAttribute(IamConstant.UP_OPEN_ID);
        session.removeAttribute(IamConstant.SESSION_AUTHENTICATION_KEY);

    }
}
