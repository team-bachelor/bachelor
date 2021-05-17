/**
 * 
 */
package cn.org.bachelor.up.oauth2.rsclient.model;

import java.io.IOException;
import java.util.Properties;

/**
 * @author team bachelor
 *
 */
public class OAuth2RSConfig {


	private String checkASTokenURL;

	Properties p=new Properties();

	public OAuth2RSConfig() {
		super();
	}

	public String getCheckASTokenURL() {
		return checkASTokenURL;
	}

	public void setCheckASTokenURL(String checkASTokenURL) {
		this.checkASTokenURL = checkASTokenURL;
	}

	public String getProperty(String key){
		return p.getProperty(key);
	}

	public OAuth2RSConfig(String configFileName) throws IOException{
		p.load(getClass().getResourceAsStream("/"+configFileName));
		this.checkASTokenURL=p.getProperty("check_astoken_url");
	}
}
