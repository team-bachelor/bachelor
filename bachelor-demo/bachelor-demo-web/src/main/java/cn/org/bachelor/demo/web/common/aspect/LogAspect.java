package cn.org.bachelor.demo.web.common.aspect;

import cn.org.bachelor.demo.web.service.LogService;
import cn.org.bachelor.demo.web.common.annotation.AppointLog;
import cn.org.bachelor.demo.web.domain.Logs;
import cn.org.bachelor.demo.web.utils.HttpContextUtils;
import cn.org.bachelor.demo.web.utils.IPUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    @Autowired
    private LogService logService;


    @Pointcut("@annotation(cn.org.bachelor.demo.web.common.annotation.AppointLog)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //异步保存日志
        saveLog(point, time);
        return result;
    }

    void saveLog(ProceedingJoinPoint joinPoint, long time) throws InterruptedException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Logs sysLog = new Logs();
        AppointLog appointLog = method.getAnnotation(AppointLog.class);
        if (appointLog != null) {
            // 注解上的描述
            sysLog.setOperation(appointLog.method());//用户操作了啥
            sysLog.setModules(appointLog.module());//模块名称
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSON.toJSONString(args);
            sysLog.setParams(params);
        } catch (Exception e) {

        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));
        // 用户名
        String userId = request.getHeader("userId");
        String userName = request.getHeader("user_name");
        if (StringUtils.isEmpty(userName)) {
            if (null != sysLog.getParams()) {
                sysLog.setUserId(userId);
                sysLog.setUsername(sysLog.getParams());
            } else {
                sysLog.setUserId(userId);
                sysLog.setUsername("获取用户信息为空");
            }
        } else {
            sysLog.setUserId(userId);
            sysLog.setUsername(userName);
        }
        sysLog.setTime((int) time);
        // 保存系统日志
        logService.save(sysLog);
    }
}
