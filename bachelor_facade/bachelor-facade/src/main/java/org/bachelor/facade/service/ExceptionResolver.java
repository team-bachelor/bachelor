package org.bachelor.facade.service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import org.bachelor.core.exception.BaseException;
import org.bachelor.core.exception.SystemException;
import org.bachelor.ps.domain.ProjectProperty;
import org.bachelor.ps.service.IProjectPropertyService;

public class ExceptionResolver extends ExceptionHandlerExceptionResolver {

	private Log log = LogFactory.getLog(this.getClass());

	private Properties exceptionMappings;

	@Autowired
	private IProjectPropertyService ppService;

	public Properties getExceptionMappings() {
		return exceptionMappings;
	}

	public void setExceptionMappings(Properties exceptionMappings) {
		this.exceptionMappings = exceptionMappings;
	}

	@Override
//	public ModelAndView resolveException(HttpServletRequest request,
//			HttpServletResponse response, Object handler, Exception e) {
	protected ModelAndView doResolveHandlerMethodException(
			HttpServletRequest request, HttpServletResponse response, HandlerMethod hm, Exception e){
		//记录异常日志
		if (e instanceof SystemException) {
			log.fatal(e.getMessage(), e);
		}else{
			log.error(e.getMessage(), e);
		}

		//取得异常信息、堆栈信息和错误页面
		StackTraceElement[] trace = e.getStackTrace();
		StringBuffer traceInfo = new StringBuffer();
		for (int i = 0; i < trace.length; i++) {
			traceInfo.append(trace[i]).append("\n<br/>");
		}
		ProjectProperty pp = ppService.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("errorMsg", pp.getErrorMsg());
		model.put("exceptionMsg", e.getMessage());
		model.put("stackTrace", traceInfo.toString());
		String errorPath = pp.getErrorPath();
		
		//判断是否返回json对象
		ResponseBody rb = hm.getMethodAnnotation(ResponseBody.class);
		if(rb != null){
			if(e instanceof BaseException){
				try {
					response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
					return new ModelAndView();
				} catch (Exception e1) {
					log.error(e.getMessage(), e);
					return new ModelAndView(errorPath, "model", model);	
				}
//				ModelAndView mav = new ModelAndView();
//				mav.getModelMap().put("textStatus", e.getMessage());
//				return mav;
				//throw (BaseException)e;
			}else{
				RuntimeException re = new RuntimeException(e);
				throw re;
			}
			
		}else{
			ModelAndView mav = new ModelAndView(errorPath,"model", model);
			
			return mav;
			
//			if (e instanceof BusinessException) {
//				//跳转到原页面，并添加异常信息
//				
//			}else{
//				//跳转到错误页面，并添加异常信息
//			}
//			
//			
//			
		}

	}
	


}
