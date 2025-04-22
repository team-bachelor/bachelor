package cn.org.bachelor.web;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 替换可以反复读取的request
 * 该过滤器用于将原始的 HttpServletRequest 替换为可重复读取的 RequestWrapper，
 * 以解决在某些场景下请求体只能读取一次的问题。
 */
@Slf4j
public class ReplaceRereadRequestFilter implements Filter {

    /**
     * 过滤器的核心处理方法，在请求处理过程中执行。
     *
     * @param request  Servlet 请求对象，包含客户端请求的信息。
     * @param response Servlet 响应对象，用于向客户端发送响应。
     * @param chain    过滤器链，用于将请求传递给下一个过滤器或目标资源。
     * @throws IOException      如果在输入输出操作中发生错误。
     * @throws ServletException 如果在处理请求时发生 Servlet 相关的错误。
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            // 尝试将原始的 ServletRequest 转换为 HttpServletRequest，并封装为 RequestWrapper
            // 此操作的目的是为了让请求体可以被重复读取
            // 原始代码中声明了一个冗余的局部变量 requestWrapper，可优化为直接赋值给 request
            request = new RequestWrapper((HttpServletRequest) request);
        } catch (Exception e) {
            // 若转换过程中出现异常，记录错误日志并继续处理请求
            log.error("转换失败，继续处理", e);
        }
        // 将修改后的请求和响应传递给过滤器链中的下一个过滤器或目标资源
        chain.doFilter(request, response);
    }
}