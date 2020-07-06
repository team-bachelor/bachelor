package cn.org.bachelor.context.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

public abstract class PrjManagedAspect extends ManageAspect {
  
	@Around("!execution(public * cn.org.bachelor.**.**..**.*(..)) and execution(public * cn.org.bachelor.**.**.biz..*Biz*.*(..)) or !execution(public * cn.org.bachelor.**.**.**..**.*(..)) and execution(public * cn.org.bachelor.**.**.dao..*Dao*.*(..))")
	  public Object intercept(ProceedingJoinPoint pjp) throws Throwable{
			
		return doIntercept(pjp);
	}
	 
}