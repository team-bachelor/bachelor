package cn.org.bachelor.up.oauth2.rsclient.securitycheck;

import cn.org.bachelor.up.oauth2.rsclient.util.ApplicationUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xueyong on 15/6/9.
 */
@Component
public class AccessToknSecurityCheck extends SecurityCheck {

    private static Logger logger= LoggerFactory.getLogger(AccessToknSecurityCheck.class);

    @Autowired
    private ICheckOperate operateJedisImpl;

    @Override
    public boolean isReplyToken(HttpServletRequest request) {
        String remote_ip = getIpAddr(request);
        logger.info("进入拦截过程中...");
        String jedisValue = operateJedisImpl.getFromJedis(remote_ip);
        int count = Integer.valueOf(StringUtils.isBlank(jedisValue) ? "0" : jedisValue);
        String accessTimes = ApplicationUtil.getValue("access_times", ACCESS_TIMES);
        String interceptTime = ApplicationUtil.getValue("intercept_time",INTERCEPT_TIME);
        if(count >= Integer.valueOf(accessTimes)){//如果访问失败次数大于限制数
            operateJedisImpl.saveToJedis(INTERCEPT_PREFIX + remote_ip, Integer.valueOf(interceptTime), String.valueOf(count));
            removekey(request);
            logger.info("IP："+remote_ip +" 次数超过限制");
            return false;
        }else{
            operateJedisImpl.saveToJedis(remote_ip, String.valueOf(++count));
            logger.info("计数器累加："+count);
            return true;
        }
    }

    /**
     * 验证拦截时间是否失效
     * @param request
     * @return
     */

    @Override
    public boolean isFailureByIntercept(HttpServletRequest request) {
        String remote_ip = getIpAddr(request);
        String obj = operateJedisImpl.getFromJedis(INTERCEPT_PREFIX + remote_ip);
        return StringUtils.isNotBlank(obj);//obj true is fail
    }

    /**
     * 清空计数器
     * @param request
     */
    @Override
    public void removekey(HttpServletRequest request) {
        String remote_ip = getIpAddr(request);
        operateJedisImpl.removeFromJedis(remote_ip);
    }

}
