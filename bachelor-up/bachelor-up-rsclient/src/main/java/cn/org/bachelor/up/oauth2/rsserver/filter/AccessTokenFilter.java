package cn.org.bachelor.up.oauth2.rsserver.filter;

import cn.org.bachelor.up.oauth2.rsserver.AccessTokenClient;
import cn.org.bachelor.up.oauth2.rsserver.model.OAuth2RSConfig;
import cn.org.bachelor.up.oauth2.rsserver.securitycheck.SecurityCheck;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by team bachelor on 15/5/21.
 */
public class AccessTokenFilter implements Filter{
    private OAuth2RSConfig config;
    private static final String defaultConfigFileName="OAuth2-rs-config.properties";
    private static String configFileName=defaultConfigFileName;
    private static Logger logger= LoggerFactory.getLogger(AccessTokenFilter.class);

    private SecurityCheck accessToknSecurityCheck ; //安全检查类

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            configFileName=filterConfig.getInitParameter("configFileName");
            if(configFileName==null || configFileName.equals("")){
                configFileName=defaultConfigFileName;
            }
            config=new OAuth2RSConfig(configFileName);

            ServletContext servletContext = filterConfig.getServletContext();
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            accessToknSecurityCheck = (SecurityCheck)ctx.getBean("accessToknSecurityCheck");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("进入asToken拦截器=========>开始验证asToken");
        AccessTokenClient client=new AccessTokenClient(config,(HttpServletRequest)servletRequest,(HttpServletResponse)servletResponse);
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if(isFailureByIntercept((HttpServletRequest)servletRequest)){
            logger.info("没有超过拦截限制时间");
            callBackMsg(response, "IP访问限制",1007);
            return ;
        }

        HttpServletRequest hsr = (HttpServletRequest) servletRequest;
        String rui = hsr.getRequestURI();
        if(client.checkASToken(rui)){
            logger.info("SignFilter asToken验证成功----url:" + ((HttpServletRequest) servletRequest).getRequestURI());
            //清空IP计数器
            setNormalIpCount((HttpServletRequest)servletRequest);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }else{
            isReplyToken((HttpServletRequest)servletRequest);
            String content = "{\"result\":1001,\"message\":\"token不合法\",\"total\":0,\"rows\":[]}";
            response.getOutputStream().write(content.getBytes());
            return;
        }
    }

    private boolean isFailureByIntercept(HttpServletRequest servletRequest){
        return accessToknSecurityCheck.isFailureByIntercept(servletRequest);
    }

    private void setNormalIpCount(HttpServletRequest servletRequest){
        accessToknSecurityCheck.removekey(servletRequest);
    }

    private boolean isReplyToken(HttpServletRequest servletRequest){
        return accessToknSecurityCheck.isReplyToken(servletRequest);
    }

    private void callBackMsg(HttpServletResponse response,String message,int result){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        JSONObject back = new JSONObject();
        PrintWriter out = null;
        try {
            back.put("result",result);
            back.put("message",message);
            back.put("total",0);
            back.put("rows", new JSONArray());
            out = response.getWriter();
            out.append(back.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (out != null) {
                out.close();
            }
        }
        return;
    }




    @Override
    public void destroy() {

    }
}
