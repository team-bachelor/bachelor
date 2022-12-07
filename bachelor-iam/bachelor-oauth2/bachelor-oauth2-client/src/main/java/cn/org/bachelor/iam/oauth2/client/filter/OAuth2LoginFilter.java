/**
 *
 */
package cn.org.bachelor.iam.oauth2.client.filter;

import cn.org.bachelor.iam.oauth2.client.OAuth2CientConfig;
import cn.org.bachelor.iam.oauth2.client.OAuth2Client;
import cn.org.bachelor.iam.oauth2.client.exception.GetAccessTokenException;
import cn.org.bachelor.iam.oauth2.client.exception.GetUserInfoException;
import cn.org.bachelor.iam.oauth2.client.util.ClientConstant;
import cn.org.bachelor.iam.oauth2.client.util.ClientUtil;
import cn.org.bachelor.iam.oauth2.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 基于OAuth2协议实现单点登录功能，使用配置方法：
 * <pre>
 * 1.在web.xml中注册此Filter,对需要获取到当前用户的url进行拦截，注意:filter-mapping的拦截要在转码Filter的后面，其他Filter的前面
 * 2.编辑WEB-INF/class/sso.properties文件(或通过增加Filter参数configFileName指定配置文件名称),内容如下：
 #应用ID
 client_id=TEST-APP
 #应用凭证
 client_secret=8d42ee5e-d1ab-4d60-abf0-499457a11579
 #应用注册的回调地址
 redirect_url=http://localhost:8080/R1AuthricationServer/client.jsp
 #授权请求的URL
 authorize_url=http://localhost:8080/R1AuthricationServer/oauth2/authorize
 #获取令牌的URL
 access_token_url=http://localhost:8080/R1AuthricationServer/oauth2/access_token
 #获取用户信息的API调用地址
 api_url=http://localhost:8080/R1AuthricationServer/api/user
 *3.获取当前用户的方式：
 *	1)通过request当前用户id：request.getRemoterUser()
 *	2)当前用户id：SSO.getUserId()
 *	3)当前用户JSONObject对象：SSO.getUser()
 * </pre>
 * @author team bachelor
 *
 */
//@Component
//@ServletComponentScan
@WebFilter(urlPatterns = "/*", filterName = "oauth2LoginFilter")
public class OAuth2LoginFilter implements Filter {

    //    private OAuth2CientConfig config;
    //    private static final String defaultConfigFileName = UpClient.defaultConfigFileName;
    private static final Logger logger = LoggerFactory.getLogger(OAuth2LoginFilter.class);
    //    private static String configFileName = defaultConfigFileName;
    private static String EXCEPT_PATTERNS = "";
    private static String EXCEPT_PARAMS = "";
//    private static String serviceString = "serviceAuthenticate";
//    private static String upString = "UpAuthenticate";

//    public static String getConfigFileName() {
//        return configFileName;
//    }

    @Override
    public void destroy() {

    }

    private OAuth2CientConfig getConfig(ServletContext context) {
        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);

        return applicationContext.getBean(OAuth2CientConfig.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        OAuth2CientConfig config = getConfig(request.getServletContext());
        if (!config.isLoginFilterEnable()) {
            chain.doFilter(request, response);
            return;
        }
        response.setCharacterEncoding("UTF-8");
        OAuth2Client client = new OAuth2Client(config, (HttpServletRequest) request, (HttpServletResponse) response);
        if (logger.isDebugEnabled()) {
            logger.debug("session hashcode:" + ((HttpServletRequest) request).getSession());
        }
        try {
            String requestURI = ((HttpServletRequest) request).getRequestURI();
            if (client.isLogin((HttpServletRequest) request)) {
                logger.info("进入登录方法isLogin()----url:" + requestURI);
                setLogon((HttpServletResponse) response, true);
                chain.doFilter(client.request(), response);
                logger.info("isLogin方法通过");
                return;
            } else if (client.isExcluded(EXCEPT_PATTERNS, EXCEPT_PARAMS, (HttpServletRequest) request)) {
                logger.info("url匹配----url:" + requestURI);
                logger.debug("isExcluded");
                chain.doFilter(request, response);
                logger.info("url匹配成功通过");
                return;
            } else if (client.isCallback()) {
                logger.info("验证是否服务端返回的地址----url:" + requestURI);
                logger.debug("isCallback");
                setLogon((HttpServletResponse) response, false);
                chain.doFilter(request, response);
                logger.info("验证是否服务端返回的地址，成功通过");
                return;
            }
            setLogon((HttpServletResponse) response, false);
            String code = client.getAuthorizationCode();
            logger.info("code:" + code);
            if (code == null) {
                logger.info("code为空时 从服务端获取code----url:" + requestURI);
//				client.toGetAuthrizationCode();
                client.toGetAuthorizationCode((HttpServletRequest) request);
                return;
            }

            if (!client.isValidState((HttpServletRequest) request)) {
                logger.info("验证state失败");
                String result = returnResourceFile(ClientConstant.TEMPLATE_NAME, ClientConstant.STATE_ERROR);
                response.getWriter().write(result);
                return;
            }
            logger.info("去调用用户信息接口方法----url:" + requestURI);
            client.bindUserInfo(code);

//				if(!client.toOriginalURL()){//如果直接敲有code的url地址
//					chain.doFilter(client.request(), response);
//				}
			/*if(client.prefect()){
				return;
			}*/


            if (client.toTargetURL()) {
                return;
            }
            logger.info("====执行完毕，接着执行其他的filter！");

            chain.doFilter(request, response);
        } catch (GetAccessTokenException gte) {
            logger.error("客户端拦截器获取令牌失败===========>", gte);
            String result = returnResourceFile(ClientConstant.TEMPLATE_NAME, ClientConstant.GET_ACCESSTOKEN_ERROR + "," + gte.getMessage());
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(result);
        } catch (GetUserInfoException gue) {
            logger.error("客户端拦截器获取用户信息失败===========>", gue);
            String result = returnResourceFile(ClientConstant.TEMPLATE_NAME, ClientConstant.GET_USERINFO_ERROR);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(result);
        } catch (Exception e) {
            logger.error("客户端拦截器执行异常===========>", e);
//			throw new ServletException(e.getMessage());
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("执行异常，请联系管理员");
        }
    }

    private void setLogon(HttpServletResponse response, boolean logon) {
        Cookie cookie = new Cookie("logon_flag", logon ? "1" : "0");//创建新cookie
        //cookie.setMaxAge(5 * 60);// 设置存在时间为5分钟
        cookie.setPath("/");//设置作用域
        response.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户端
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        OAuth2CientConfig config = getConfig(filterConfig.getServletContext());
        if (!config.isLoginFilterEnable()) return;
        try {
            ClientUtil.config = config;// 放到全局变量里面
            EXCEPT_PATTERNS = filterConfig.getInitParameter("_except_urlpattern");
            EXCEPT_PARAMS = filterConfig.getInitParameter("_except_param");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    //TODO 错误模板定义方式要改一下
    private String returnResourceFile(String fileName, String info) throws ServletException, IOException {
        String text = IOUtils.readFromResource("support/http/resources/" + fileName);
        if (text == null) {
            return "未定义错误信息模版:" + info;
        } else {
            return StringUtils.replace(text, ClientConstant.TEMPLATE_REPLACE_STRING, info);
        }

    }

}
