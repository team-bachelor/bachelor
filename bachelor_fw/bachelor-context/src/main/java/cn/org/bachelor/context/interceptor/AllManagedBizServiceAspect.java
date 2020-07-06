package cn.org.bachelor.context.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public abstract class AllManagedBizServiceAspect extends ManageAspect {
  
	@Around("execution(public * cn.org.bachelor.**.**.biz..*Biz*.*(..)) or execution(public * cn.org.bachelor.**.**.service..*Service*.*(..))")
	  public Object intercept(ProceedingJoinPoint pjp) throws Throwable{

		return doIntercept(pjp);
	}
}
