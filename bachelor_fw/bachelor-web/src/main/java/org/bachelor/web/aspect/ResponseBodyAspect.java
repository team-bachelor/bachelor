package org.bachelor.web.aspect;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.bachelor.context.service.IVLService;
import org.bachelor.core.exception.BusinessException;
import org.bachelor.core.exception.SystemException;
import org.bachelor.dao.DaoConstant;
import org.bachelor.dao.vo.PageVo;
import org.bachelor.util.ApplicationContextHolder;
import org.bachelor.web.json.JsonResponse;
import org.bachelor.web.json.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResponseBodyAspect {

	private Log log = LogFactory.getLog(this.getClass());
	@Autowired
	private IVLService vlService;

	// 标注该方法体为后置通知，当目标方法执行成功后执行该方法体
	@Around("@annotation(org.springframework.web.bind.annotation.ResponseBody) && execution(public org.bachelor.web.json.JsonResponse *..*(..))")
	public Object jsonDataProceed(ProceedingJoinPoint pjp) {
		// 继续执行接下来的代码
		JsonResponse<?> ret = null;
		Object retVal = null;
		ResponseStatus status = null;
		String msg = null;
		boolean hasErr = false;
		String[] args = null;
		try {
			retVal = pjp.proceed();
			status = ResponseStatus.OK;
		} catch (Throwable e) {
			if (e instanceof BusinessException) {
				status = ResponseStatus.BIZ_ERR;
				args = ((BusinessException) e).getArgs();
			} else if (e instanceof SystemException) {
				status = ResponseStatus.SYS_ERR;
			} else {
				status = ResponseStatus.SYS_ERR;
			}
			hasErr = true;
			msg = e.getMessage();
			log.error(pjp, e);
		}
		if (retVal != null) {
			ret = (JsonResponse<?>) retVal;
		} else {
			ret = new JsonResponse<String>();
		}
		if (msg != null) {
			ret.setMsg(msg);
		} else if (ret.getMsg() != null) {
			msg = ret.getMsg();
		}
		ret.setStatus(status);
		if (msg != null) {
			String transMsg = ApplicationContextHolder.getApplicationContext()
					.getMessage(ret.getMsg(), args, Locale.CHINA);
			if (hasErr && !transMsg.equals(ret.getMsg())) {
				ret.setCode(msg);
			}
			ret.setMsg(transMsg);
		}
		PageVo pageVo = (PageVo) vlService
				.getRequestAttribute(DaoConstant.PAGE_INFO);
		ret.setPage(pageVo);
		return ret;
	}
}
