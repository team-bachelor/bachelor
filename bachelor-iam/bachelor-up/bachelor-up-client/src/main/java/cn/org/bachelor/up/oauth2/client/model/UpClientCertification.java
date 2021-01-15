package cn.org.bachelor.up.oauth2.client.model;

import java.io.Serializable;

public class UpClientCertification implements Serializable {

	private static final long serialVersionUID = -2225295839721811001L;
	/**
	 * 客户端会话认证凭证
	 * @author lidonghui
	 *
	 */
	
	private String userid=null;
	
	private String accessToken=null;

	private String refreshToken=null;
	
	private String expiresTime = null;

	public UpClientCertification(){
	}
	
	public UpClientCertification(String userid, String accessToken, String refreshToken, String expiresTime){
		this.userid = userid;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiresTime = expiresTime;
	}
	
    public UpClientCertification(String userid, String accessToken, String refreshToken){
		this.userid = userid;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
    
    public UpClientCertification(String userid){
		this.userid = userid;
	}
    
    public boolean equals(Object obj){
    	if ((obj instanceof UpClientCertification)){
    		UpClientCertification certification = (UpClientCertification)obj;
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
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getExpiresTime() {
		return expiresTime;
	}

	public void setExpiresTime(String expiresTime) {
		this.expiresTime = expiresTime;
	}
}
