package cn.org.bachelor.iam.oauth2.client.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * url过滤器处理类
 * 
 * @author team bachelor
 * 
 */
public class UrlExpProcessor {
  
	private Pattern pattern;
    public final static String STYLE_EXP = "\\.css$|\\.js$|\\.jpg$|\\.gif$|\\.png$|\\.gzjs$|\\.gzcss";
    private String patterns;
    public UrlExpProcessor(){
        this(null);
    }
    /**
     *
     * @param patterns
     */
    public UrlExpProcessor(String patterns){
    	if(patterns != null && !patterns.equals("")){
    		StringBuffer sb=new StringBuffer();
    		sb.append(STYLE_EXP).append("|").append(patterns);
    		this.patterns=sb.toString();
    	}else{
    		this.patterns =  STYLE_EXP;
    	}
    	pattern = Pattern.compile(this.patterns);
    }  
    
    public Pattern getPattern() {
		return pattern;
	}
	/**
     * 匹配url
     * @param url
     * @return
     */
    public boolean match(String url){
        if(url == null || url.trim().equals("")){
            return false;
        }
        Matcher matcher = pattern.matcher(url);
        return matcher.find();
    }
    
    public String toString(){
    	return "UrlExpProcessor["+patterns+"]";
    }
    
    public static void main(String[] args) {
    	UrlExpProcessor u=new UrlExpProcessor("\\.css$|\\.js$|\\.jpg$|\\.gif$|\\.png$|\\.gzjs$|\\.gzcss|/api/*|/ots/*|/matrix/*");
    	System.out.println(u.match("htt://xxx/ots/LogoCacheFile.jsp"));;
	}
}
