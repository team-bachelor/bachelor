package org.bachelor.auth.interceptor;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.auth.common.AuthConstant;
import org.bachelor.auth.domain.LoginResult;
import org.bachelor.auth.service.ILoginService;
import org.bachelor.context.common.ContextConstant;
import org.bachelor.context.interceptor.PrjManagedInterceptor;
import org.bachelor.context.service.IVLService;
import org.bachelor.org.domain.User;
import org.bachelor.ps.domain.ProjectProperty;

public class PrjAuthServiceInteterceptor extends PrjManagedInterceptor {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private ILoginService loginService;
	
	@Autowired
	private IVLService vlService;
	
	private List<String> excludeUrlList = null;
	
	private void redirectToLogin(HttpServletResponse response){
		String loginUrl = excludeUrlList.get(1);
		if(!StringUtils.isEmpty(loginUrl)){
			try {
				response.sendRedirect(ContextConstant.WEB_PROJECT_NAME+loginUrl);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}else{
			log.error("没有配置LoginPath!");
		}
		
	}

	/**
	 * @return the excludeUrlList
	 */
	public List<String> getExcludeUrlList() {
		return excludeUrlList;
	}

	/**
	 * @param excludeUrlList the excludeUrlList to set
	 */
	public void setExcludeUrlList(List<String> excludeUrlList) {
		this.excludeUrlList = excludeUrlList;
	}

	@Override
	protected void doAfterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception e) {
		 
	}

	@Override
	protected void doPostHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav) {
		 
	}

	@SuppressWarnings("unchecked")
	@Override
	protected boolean doPreHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) {
		
		/** 在平台管理里把不要登录过滤的URL存到位Session中**/
		List<String> urls = (List<String>) vlService.getSessionAttribute(AuthConstant.NO_LOGIN_AUTH_LIST);
		if(urls==null || urls.size()<=0){
			vlService.setSessionAttribute(AuthConstant.NO_LOGIN_AUTH_LIST,excludeUrlList);
		}
		
		log.info("判断当前资源是否需要验证");
		String servletPath = request.getServletPath();
		//如果是登陆页面，不需要验证
		if(excludeUrlList != null && excludeUrlList.size() > 0){
			for(String excludeUrl: excludeUrlList){
				if(servletPath.equals(excludeUrl)){
					log.info(servletPath + "不需要进行 登录验证。");
					return true;
				}
			}			
		}
		

		log.info(servletPath + " 需要进行 登录验证。");
		
		String userName = request.getHeader(AuthConstant.AUTH_NAME);
		log.info("auth_name:" + userName);
		//判断请求是否来自信息中心
		if(userName != null){
			log.info("请求来自信息中心，进行单点登录验证");
			if(userName.equals(AuthConstant.AUTH_FAILED_VALUE)){
				log.info("用户(" + userName + ")验证失败");
				log.info("转发到登录页面。");
				redirectToLogin(response);
				return false;
			}else{
				log.info("认证用户");
				LoginResult loginResult = loginService.login(userName, null, true);
				if(loginResult.getResult() == AuthConstant.RESULT_SUCCESS){
					return true;
				}else{
					log.error("单点用户(" + userName + ")在本地库不存在。");
					redirectToLogin(response);
					return false;
				}
				
			}
		}else{
			log.info("请求不是来自信息中心。");
			log.info("判断用户是否已经登录。如果没有登录则跳转到登录页面。");
			if(request.getSession(false) == null ){
				log.info("用户没有登录或者session超时。重新登录");
				redirectToLogin(response);
				return false;
			}
			Object attributeObj = request.getSession(false).getAttribute(AuthConstant.LOGIN_USER);
			if(attributeObj != null && attributeObj instanceof User){
				User user = (User)attributeObj;
				if(user.getId() != null && !user.getId().trim().equals("")){
					//用户已经登录
					log.info("用户(" + user.getId() + ")已经登录");
					return true;
				}else{
					log.info("用户没有登录或者session超时。");
					redirectToLogin(response);
					return false;
				}
			}else{
				redirectToLogin(response);
				return false;
			}
		}
	}

	@Override
	protected int initOrder() {
		return 0;
	}

}
