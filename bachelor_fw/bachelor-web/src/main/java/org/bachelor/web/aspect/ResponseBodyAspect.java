package org.bachelor.web.aspect;

import java.util.Locale;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.bachelor.core.exception.BusinessException;
import org.bachelor.core.exception.SystemException;
import org.bachelor.util.ApplicationContextHolder;
import org.bachelor.web.json.JsonResponseData;
import org.bachelor.web.json.ResponseStatus;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResponseBodyAspect {

	// 标注该方法体为后置通知，当目标方法执行成功后执行该方法体
	@Around("@annotation(org.springframework.web.bind.annotation.ResponseBody) && execution(public org.bachelor.web.json.JsonResponseData *..*(..))")
	public Object jsonDataProceed(ProceedingJoinPoint pjp) {
		// 继续执行接下来的代码
		JsonResponseData ret = null;
		Object retVal = null;
		try {
			retVal = pjp.proceed();
			if(retVal == null) return retVal;
			ret = (JsonResponseData) retVal;
			ret.setStatus(ResponseStatus.OK);
		} catch (Throwable e) {
			if (e instanceof BusinessException) {
				ret.setStatus(ResponseStatus.BIZ_ERR);
			} else if (e instanceof SystemException) {
				ret.setStatus(ResponseStatus.SYS_ERR);
			} else {
				ret.setStatus(ResponseStatus.SYS_ERR);
			}
			ret.setMsg(e.getMessage());
		}
		if (ret.getMsg() != null)
			ret.setMsg(ApplicationContextHolder.getApplicationContext()
					.getMessage(ret.getMsg(), null, Locale.CHINA));
		return ret;
	}
}
