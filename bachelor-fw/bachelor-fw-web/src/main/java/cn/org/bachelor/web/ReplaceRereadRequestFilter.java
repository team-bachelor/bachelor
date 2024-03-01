package cn.org.bachelor.web;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 替换可以反复读取的request
 */
@Slf4j
public class ReplaceRereadRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            ServletRequest requestWrapper = new RequestWrapper((HttpServletRequest) request);
            request = requestWrapper;
        }catch (Exception e){
            log.error("转换失败，继续处理", e);
        }
        chain.doFilter(request, response);
    }
}



