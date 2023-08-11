package org.bachelor.auth.common;

import java.lang.annotation.Annotation;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.bachelor.auth.domain.StatisticController;
import org.bachelor.org.domain.User;

import org.aspectj.lang.Signature;

@SuppressWarnings("rawtypes")
public class InterceptorEvaluation {
	
	
	public static StatisticController EvaluationStatisticControllerObj(User user,Object target,Object signature,
			HttpServletRequest request,long startTime,long endTime,
			long costTime,String type){
		StatisticController sc = new StatisticController();
		if(request!=null){
			//得到服务器IP
			sc.setServerIp(request.getLocalAddr());
			
			//没有登录的情�?
			if(user!=null){
				sc.setUserId(user.getId());
				sc.setUserName(user.getUsername());
				sc.setCompanyId(user.getOwnerOrgId());
				sc.setCompanyName(user.getOwnerOrgName());
			}
			
			//服务器基本信�?
			sc.setAccessDate(new Date());
			sc.setCostTime(costTime);
			sc.setAccessDate(new Date(startTime));
			sc.setCompleteDate(new Date(endTime));
			sc.setRequestPath(request.getServletPath());
			sc.setType(type);
			
			Class cl = target.getClass();
			sc.setAccessClass(cl.getName());
			Signature m = (Signature)signature;
			sc.setAccessMethod(m.getName()); 
			
		}
		return sc;
	}
	
	/**
	 * 功能: 判断是否有数组中的注�?
	 * 作�? 曾强 2013-6-13下午04:40:05
	 * @param cl
	 * @return
	 */
	public static boolean judgeAnnotation(Class cl){
		Annotation at[] = cl.getAnnotations();
		boolean flag = false;
		for(Annotation tempAt : at){
			Class<? extends Annotation> oo = tempAt.annotationType();
			for(String auth : AuthConstant.ANNOTATION_NAME){
				if(oo.getCanonicalName().indexOf(auth)!=-1){
					flag = true;
					break;
				}
			}
		}
		if(flag == false){
			for(String icn : AuthConstant.INTERCEPTOR_CLASS_NAME){
				if(cl.getName().indexOf(icn)!=-1){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
}
