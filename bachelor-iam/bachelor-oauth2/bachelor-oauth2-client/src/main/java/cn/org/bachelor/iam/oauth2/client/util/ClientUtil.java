package cn.org.bachelor.iam.oauth2.client.util;

import javax.servlet.http.HttpSession;

import cn.org.bachelor.iam.oauth2.client.model.OAuth2ClientCertification;
import cn.org.bachelor.iam.oauth2.client.model.MJsonObject;
import cn.org.bachelor.iam.oauth2.client.OAuth2CientConfig;
import com.alibaba.fastjson.JSONObject;

/**
 * 登录工具类
 * @author team bachelor
 * @version 1.0
 * @since 1.0
 */
public class ClientUtil {
	public static ThreadLocal<HttpSession> sessions = new ThreadLocal<>();//存放session
	
	public static OAuth2CientConfig config;//OAuth2LoginFilter.init的时候初始化
	
	/**
	 * 设置session到ThreadLocal中
	 * @param session
	 */
	public static void setSession(HttpSession session){
		sessions.set(session);
	}

	/**
	 * 清空ThreadLocal中的session
	 */
	public static void clearSession(){
		sessions.set(null);
	}


	/**
	 * 获取当前登录用户OPENID
	 * @return
	 */
	public static String getCurrentOpenId(){
		HttpSession session = sessions.get();
		if(session == null) return null;
		return (String) session.getAttribute(ClientConstant.UP_OPEN_ID);
	}

	/**
	 * 获取当前登录用户ID
	 * @return
	 */
	public static String getCurrentUserId(){
		HttpSession session = sessions.get();
		if(session == null) return null;
		return (String) session.getAttribute(ClientConstant.UP_USER_ID);
	}
	
	/**
	 * 获取当前登录人姓名
	 * @return
	 */
	public static String getCurrentUserName(){
		MJsonObject user = getCurrentUser();
		if(user == null) return "";
		return user.getJsonValue("username");
	}

	/**
	 * 获取当前登录人账号
	 * @return
	 */
	public static String getCurrentAccount(){
		MJsonObject user = getCurrentUser();
		if(user == null) return "";
		return user.getJsonValue("account");
	}

	
	/**
	 * 获取当前登录用户
	 * @return
	 */
	public static MJsonObject getCurrentUser(){
		String personStr = getCurrentUserStr();
		if(personStr == null){
			return null;
		}
		JSONObject personJson = JSONObject.parseObject(personStr);
		return new MJsonObject(personJson);
	}
	
	public static String getCurrentUserStr(){
		HttpSession session = sessions.get();
		if(session == null) return null;
		return String.valueOf(session.getAttribute(ClientConstant.UP_USER));
	}


	/**
	 * 获取当前令牌
	 * @return
	 */
	public static String getCurrentAsToken(){
		OAuth2ClientCertification identity = getCurrentIdentity();
		if(identity == null) return "";
		return identity.getAccessToken();
	}

	/**
	 * 获取当前刷新令牌
	 * @return
	 */
	public static String getCurrentRsToken(){
		OAuth2ClientCertification identity = getCurrentIdentity();
		if(identity == null) return "";
		return identity.getRefreshToken();
	}

	/**
	 * 获取当前令牌到期日期
	 * @return
	 */
	public static String getCurrentAsTokenExpir(){
		OAuth2ClientCertification identity = getCurrentIdentity();
		if(identity == null) return null;
		return identity.getExpiresTime();
	}


	/**
	 * 获取当前OAuth2ClientCertification
	 * @return
	 */
	public static OAuth2ClientCertification getCurrentIdentity(){
		HttpSession session = sessions.get();
		if(session == null) return null;
		return (OAuth2ClientCertification) session.getAttribute(ClientConstant.SESSION_AUTHENTICATION_KEY);
	}

	public static void RemoveSession(){
		HttpSession session = sessions.get();
		session.removeAttribute(ClientConstant.UP_USER);
		session.removeAttribute(ClientConstant.UP_USER_ID);
		session.removeAttribute(ClientConstant.UP_OPEN_ID);
		session.removeAttribute(ClientConstant.SESSION_AUTHENTICATION_KEY);

	}
}
