package cn.org.bachelor.iam.oauth2.client.model;

import cn.org.bachelor.iam.credential.AbstractIamCredential;

import java.io.Serializable;
import java.util.Date;

public class OAuth2ClientCertification extends AbstractIamCredential<String> implements Serializable {

	private static final long serialVersionUID = -2225295839721811001L;
	/**
	 * 客户端会话认证凭证
	 * @author lidonghui
	 *
	 */
	
	private String userid=null;
	
	private String accessToken=null;

	private String refreshToken=null;
	
	public OAuth2ClientCertification(){
	}
	
	public OAuth2ClientCertification(String userid, String accessToken, String refreshToken, Date expiresTime){
		this.setUserid(userid);
		this.setAccessToken(accessToken);
		this.refreshToken = refreshToken;
		super.setExpiresTime(expiresTime);
	}
	
    public OAuth2ClientCertification(String userid, String accessToken, String refreshToken){
		this.setUserid(userid);
		this.setAccessToken(accessToken);
		this.refreshToken = refreshToken;
	}
    
    public OAuth2ClientCertification(String userid){
		this.setUserid(userid);
	}
    
    public boolean equals(Object obj){
    	if ((obj instanceof OAuth2ClientCertification)){
    		OAuth2ClientCertification certification = (OAuth2ClientCertification)obj;
    		String guestUser = certification.getUserid();
    		if(userid==null&&guestUser==null){
    			return true;
    		}
    		if(userid!=null&&guestUser!=null&&userid.equals(guestUser)){
    			return true;
    		}
    	}
    	return false;
    }
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
        sb.append(super.toString());

        if (this.userid == null)
          sb.append(": Null authentication");
        else {
          sb.append(": Authentication: ").append(this.userid);
        }
        return sb.toString();
    }

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {

		this.userid = userid;
		super.setSubject(userid);
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		super.setCredential(accessToken);
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
