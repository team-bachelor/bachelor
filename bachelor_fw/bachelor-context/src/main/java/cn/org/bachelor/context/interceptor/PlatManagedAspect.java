package cn.org.bachelor.context.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public abstract class PlatManagedAspect extends ManageAspect {
  
	@Around("execution(public * cn.org.bachelor.**.dao..*Dao*.*(..)) or execution(public * cn.org.bachelor.**.service..*Service*.*(..))")
	  public Object intercept(ProceedingJoinPoint pjp) throws Throwable{
		 
		return doIntercept(pjp);
	}
}
