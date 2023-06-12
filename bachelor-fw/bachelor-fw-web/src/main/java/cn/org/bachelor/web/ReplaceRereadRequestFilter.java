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
        ServletRequest requestWrapper = new RequestWrapper((HttpServletRequest) request);
        chain.doFilter(requestWrapper, response);
    }
}



