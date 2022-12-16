package cn.org.bachelor.iam.dac.client.util;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;

public abstract class AopTargetUtils {


    public static Object getTarget(Object obj ){
        if(!AopUtils.isAopProxy(obj)){
            return  obj;
        }
        try {

            //判断是jdk还是cglib代理
            if (AopUtils.isJdkDynamicProxy(obj)) {
                obj = getJdkDynamicProxyTargetObject(obj);
            } else {
                obj = getCglibDynamicProxyTargetObject(obj);
            }

        }catch (Exception e){

        }
        return obj;

    }

    private static Object getCglibDynamicProxyTargetObject(Object obj) throws Exception {
        Field h = obj.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);

        Object dynamicAdvisedInterceptor = h.get(obj);
        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        Object target = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
        return target;
    }

    private static Object getJdkDynamicProxyTargetObject(Object obj) throws Exception {

        Field h = obj.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);

        AopProxy aopProxy = (AopProxy)h.get(obj);
        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        Object target = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
        return target;

    }
}
