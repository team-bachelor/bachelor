/**
 * 
 */
package cn.org.bachelor.up.oauth2.exception;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * <pre>
<table>
  <tr>
    <td width="25%">错误码error</td>
    <td width="25%">错误编号errorCode</td>
    <td width="50%">错误的描述信息errorDescription</td>
  </tr>
  <tr>
    <td>unsupported_response_type</td>
    <td>400</td>
    <td>不支持的 ResponseType</td>
  </tr>
  <tr>
    <td>invalid_request</td>
    <td>401</td>
    <td>请求不合法,缺少必要的参数</td>
  </tr>
  <tr>
    <td>unknow_client</td>
    <td>402</td>
    <td>未知的客户端</td>
  </tr>
  <tr>
    <td>invalid_redirect_uri</td>
    <td>403</td>
    <td>重定向地址不合法</td>
  </tr>
  <tr>
    <td>redirect_uri_mismatch</td>
    <td>404</td>
    <td>重定向地址不匹配</td>
  </tr>
  <tr>
    <td>scope_not_valid</td>
    <td>405</td>
    <td>SCOPE超出范围</td>
  </tr>
  <tr>
    <td>implicit_grant_not_permitted</td>
    <td>406</td>
    <td>不允许隐式授权</td>
  </tr>
  <tr>
    <td>redirect_rui_fragment_componet</td>
    <td>407</td>
    <td>重定向地址不能包含片段</td>
  </tr>
  <tr>
    <td>access_denied</td>
    <td>407</td>
    <td>用户或管理员禁止访问</td>
  </tr>
  <tr>
    <td>invalid_grant</td>
    <td>409</td>
    <td>提供的Access Grant是无效的、过期的、已撤销的</td>
  </tr>
  <tr>
    <td>unsupported_grant_type</td>
    <td>410</td>
    <td>不支持的 GrantType</td>
  </tr>
  <tr>
    <td>client_unavailable</td>
    <td>411</td>
    <td>客户端不可用，还未颁发secret或被管理员禁用</td>
  </tr>
  <tr>
    <td>invalid_client</td>
    <td>412</td>
    <td>客户端验证失败，client_id或者client_secret不匹配</td>
  </tr>
  <tr>
    <td>authorizationcode_reused</td>
    <td>413</td>
    <td>授权码不能重复使用</td>
  </tr>
  <tr>
    <td>invalid_token</td>
    <td>414</td>
    <td>访问令牌或刷新码无效</td>
  </tr>
  <tr>
    <td>invalid_request_method</td>
    <td>415</td>
    <td>不支持当前请求方法</td>
  </tr>
  <tr>
    <td>expired_token</td>
    <td>416</td>
    <td>令牌已过期</td>
  </tr>
  <tr>
    <td>disabled_token</td>
    <td>417</td>
    <td>令牌已禁用</td>
  </tr>
</table>
 * </pre>
 * @author tangchao
 *
 */
public class OAuth2Exception extends BizException implements Serializable{
	/**
	 * 
	 */

	/**
	 * 不支持的 ResponseType
	 */
	public static final OAuth2Exception UNSUPPORTED_RESPONSE_TYPE = new OAuth2Exception("unsupported_response_type",400,"不支持的 ResponseType");
	/**
	 * 请求不合法，缺少必要的参数
	 */
	public static final OAuth2Exception INVALID_REQUEST = new OAuth2Exception("invalid_request",401,"请求不合法,缺少必要的参数");
	/**
	 * 未知的客户端ID
	 */
	public static final OAuth2Exception UNKNOW_CLIENT = new OAuth2Exception("unknow_client",402,"未知的客户端");
	/**
	 * 重定向地址不合法
	 */
	public static final OAuth2Exception INVALID_REDIRCTURI = new OAuth2Exception("invalid_redirect_uri",403,"重定向地址不合法");
	
	/**
	 * 重定向地址不匹配
	 */
	public static final OAuth2Exception REDIRECT_URI_MISMATCH = new OAuth2Exception("redirect_uri_mismatch",404,"重定向地址不匹配");
	/**
	 * SCOPE超出范围
	 */
	public static final OAuth2Exception SCOPE_NOT_VALID = new OAuth2Exception("scope_not_valid",405,"SCOPE超出范围");
	/**
	 * 不允许隐式授权
	 */
	public static final OAuth2Exception IMPLICIT_GRANT_NOT_PERMITTED = new OAuth2Exception("implicit_grant_not_permitted",406,"不允许隐式授权");
	/**
	 * 重定向地址不能包含片段
	 */
	public static final OAuth2Exception REDIRECT_URI_FRAGMENT_COMPONENT = new OAuth2Exception("redirect_rui_fragment_componet",407,"重定向地址不能包含片段");
	/**
	 * 用户拒绝授权
	 */
	public static final OAuth2Exception ACESS_DENIED = new OAuth2Exception("access_denied",408,"用户或管理员禁止访问");
	/**
	 * 提供的Access Grant是无效的、过期的或已撤销的
	 */
	public static final OAuth2Exception INVALID_GRANT = new OAuth2Exception("invalid_grant",409,"提供的Access Grant是无效的、过期的、已使用或已撤销的");
	/**
	 * 不支持的 GrantType
	 */
	public static final OAuth2Exception UNSUPPORTED_GRANT_TYPE = new OAuth2Exception("unsupported_grant_type",410,"不支持的 GrantType");
	/**
	 * 客户端不可用，还未颁发secret或者被管理员禁用
	 */
	public static final OAuth2Exception CLIENT_UNAVAILABLE = new OAuth2Exception("client_unavailable",411,"客户端不可用，还未颁发secret或被管理员禁用");
	/**
	 * client_secret参数无效
	 */
	public static final OAuth2Exception INVALID_CLIENT = new OAuth2Exception("invalid_client",412,"客户端验证失败，client_id或者client_secret不匹配");
	/**
	 * 授权码不能重复使用
	 */
	public static final OAuth2Exception AUTHORIZATIONCODE_REUSED = new OAuth2Exception("authorizationcode_reused",413,"授权码不能重复使用");
	
	/**
	 * 令牌无效
	 */
	public static final OAuth2Exception INVALID_TOKEN = new OAuth2Exception("invalid_token",414,"访问令牌或刷新码无效");
	/**
	 * 不支持当前请求方法
	 */
	public static final OAuth2Exception INVALID_REQUEST_METHOD = new OAuth2Exception("invalid_request_method",415,"不支持当前请求方法");
	/**
	 * 令牌过期
	 */
	public static final OAuth2Exception EXPIRED_TOKEN = new OAuth2Exception("expired_token",416,"令牌已过期");
	/**
	 * 已禁用的令牌
	 */
	public static final OAuth2Exception DISABLED_TOKEN = new OAuth2Exception("disabled_token",417,"令牌已禁用");
	/**
	 * 客户端应用已停用
	 */
	public static final OAuth2Exception DISABLED_APP = new OAuth2Exception("disabled_app",418,"客户端应用已停用");
	
	
	/**
	 * 没有符合要求的数据
	 */
	public static final OAuth2Exception NO_DATA = new OAuth2Exception("no_data",419,"没有符合要求的数据");
	
	/**
	 * 用户验证失败
	 */
	public static final OAuth2Exception INVALID_USER = new OAuth2Exception("invalid_user",420,"用户验证失败");
	
	/**
	 *  OAuth2-config.properties配置文件相关参数的值为空
	 */
	public static final OAuth2Exception PARSE_PARAMETER_FAILURE = new OAuth2Exception("parse_parameter_failuer",422," OAuth2-config.properties配置文件相关参数的值为空");
	
	
	/**
	 * 参数不能为“”或 null
	 */
	public static final OAuth2Exception PARAMETER_NO_NULL = new OAuth2Exception("iparameter_no_null",423,"当前值不能为“”或 null");
	
	
	
	
	public OAuth2Exception(String error,int errorCode,String errorDescription){
		super(errorDescription);
		this.error=error;
		this.errorCode=String.valueOf(errorCode);
		this.errorDescription=errorDescription;
	}
	
	public OAuth2Exception(String errorDescription){
		super(errorDescription);
		this.errorDescription=errorDescription;
	}
	
	public OAuth2Exception(Exception e) {
		super(e.getMessage());
		if(e instanceof OAuth2Exception){
			OAuth2Exception e2=(OAuth2Exception)e;
			this.error=e2.getError();
			this.errorCode=e2.getErrorCode();
			this.errorDescription=e2.getErrorDescription();
		}else{
			this.error="system_error";
			this.errorCode="500";
			this.errorDescription=e.getMessage();
		}
	}

	/**
	 * 将错误信息已参数的形式添加到URL中
	 * @param url
	 * @return
	 */
	public String appendToURL(String url){
		String m=url.contains("?") ? "&" : "?";
		StringBuilder sb=new StringBuilder();
		sb.append(url).append(m).append("error=").append(error);
		sb.append("&").append("errorCode=").append(errorCode);
		try {
			sb.append("&").append("errorDescription=").append(URLEncoder.encode(this.errorDescription, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
}
