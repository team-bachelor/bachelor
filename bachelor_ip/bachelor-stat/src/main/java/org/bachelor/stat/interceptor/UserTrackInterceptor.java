package org.bachelor.stat.interceptor;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.auth.common.AuthConstant;
import org.bachelor.auth.domain.Role;
import org.bachelor.context.interceptor.AllManagedIntercepter;
import org.bachelor.context.service.IVLService;
import org.bachelor.org.domain.Org;
import org.bachelor.org.domain.User;
import org.bachelor.org.service.IOrgService;
import org.bachelor.ps.domain.ProjectProperty;
import org.bachelor.ps.service.IProjectPropertyService;
import org.bachelor.stat.domain.StatUserTrack;
import org.bachelor.stat.service.IStatUserTrackService;

/**
 * 记录用户访问过的URL。
 * 
 * @author user
 *
 */
public class UserTrackInterceptor extends AllManagedIntercepter{

	@Autowired
	private IVLService vlService;
	
	@Autowired
	private IStatUserTrackService statUserTrackService; 
	
	@Autowired
	private IOrgService orgService;
	 
	@Override
	protected void doAfterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception e) {
		 
	}

	@Override
	protected void doPostHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav) {
	 
	}

	@Override
	protected boolean doPreHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) {
		/** 功能信息实体 **/
		StatUserTrack sut = new StatUserTrack();
		/** 请求实体 **/
		HandlerMethod hm = (HandlerMethod)obj;
		
		/** 服务器地址信息 **/
		sut.setServerIp(request.getLocalAddr());
		
		/** 请求参数信息 **/
		String funcId = (String)request.getParameter("funcId");
		String menuId = (String)request.getParameter("menuId");
		
		/**参数值**/
		StringBuffer paramValues = new StringBuffer();
		/**得到参数值**/
		getRequestParamValues(request,paramValues);
		
		sut.setAccessUrl(request.getRequestURL().toString());
		sut.setAccessParam(paramValues.toString());
		
		sut.setType("1");
		if(funcId!=null && !"".equals(funcId)){
			sut.setType("0");
		}
		sut.setFuncId(funcId);
		sut.setMenuId(menuId);
		
		/** 请求信息信息 **/
		sut.setClientIp(request.getRemoteAddr());
		sut.setAccessTime(new Date());
		sut.setAccessUri(request.getServletPath());
		
		User user = (User) vlService.getSessionAttribute(AuthConstant.LOGIN_USER);
		/** 会产生没有登录的记录 **/
		if(user!=null){
				/** 当前登录用户信息 **/
				sut.setUserId(user.getId());
				sut.setUserName(user.getUsername());
				
				/** 用户单位信息 **/
				Org org = orgService.findOrgByUserId(user.getId());
				if(org!=null){
					sut.setOrgId(org.getCompanyId());
					sut.setOrgName(org.getCompanyName());
				}
		}
		
		/** 拦截的类信息 **/
		sut.setClassName(hm.getBean().getClass().getName());
		sut.setMethod(hm.getMethod().getName());
		/** 存储信息 **/
		statUserTrackService.save(sut);
		
		/** 只存储单击菜单的实体 **/
		if(sut.getType().equals("0")){
			/**把实体存储至Session**/
			statUserTrackService.saveUserTrackFuncEntity(sut);
		}
		
		return true;
	}

	/**
	 * 得到参数值
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void getRequestParamValues(HttpServletRequest request,StringBuffer paramValues){
		//**得到参数及参数值**//
		Map<String,Object> paramMap = request.getParameterMap();
		if(paramMap!=null && paramMap.size()>0){
			//**得到参数的key**//
			Enumeration<Object> params =  request.getParameterNames();
			//**保存所有key**//
			String keys[] = new String[paramMap.size()];
			if(params!=null && params.hasMoreElements()){
				int index = 0;
				while(params.hasMoreElements()){
					keys[index] = (String) params.nextElement();
					index++;
				}
				//**初始化标识**//
				index = 0;
				//**循环key得到参数值**//
				for(String key:keys){
					//**拼合参数值**//
					paramValues.append(key).append("=").append(((String[])paramMap.get(key))[0]);
					if(index<(paramMap.size()-1)){
						paramValues.append("&");
					}
					index++;
				}
			}
		}
	}
	
	@Override
	protected int initOrder() {
		
		return 4;
	}
	
}
