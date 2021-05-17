package cn.org.bachelor.up.oauth2.rsclient.securitycheck;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xueyong on 15/6/9.
 */
public abstract class SecurityCheck {

    static final String ACCESS_TIMES = "5";
    static final String INTERCEPT_TIME = "60";
    static final String INTERCEPT_PREFIX = "INTERCEPT_IP_";

    /**
     * 验证Token失败次数
     * 如果失败$N次，则拦截此IP $M分钟。
     * @param request
     * @return
     */
    public abstract boolean isReplyToken(HttpServletRequest request);

    /**
     * 验证拦截时间是否失效
     * @param request
     * @return
     */
    public abstract boolean isFailureByIntercept(HttpServletRequest request);

    /**
     * 清空计数器
     * @param request
     */
    public abstract void removekey(HttpServletRequest request);

    /**
     * getIP
     * @param request
     * @return ip
     */
    String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        // 如果是多级代理，那么取第一个ip为客户ip
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }
        return ip;
    }
}
