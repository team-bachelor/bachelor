/*
 * @(#)PageInceptorServiceImpl.java	May 27, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
// 声明该类所在的包路径
package cn.org.bachelor.web.paging;

// 引入阿里巴巴的 FastJSON 库中的 JSONObject 类，用于处理 JSON 数据
import com.alibaba.fastjson.JSONObject;
// 引入 PageHelper 类，用于实现分页功能
import com.github.pagehelper.PageHelper;
// 引入 Apache Commons Lang3 库中的 StringUtils 类，用于字符串操作
import org.apache.commons.lang3.StringUtils;
// 引入 Apache Commons Logging 库中的 Log 接口，用于日志记录
import org.apache.commons.logging.Log;
// 引入 Apache Commons Logging 库中的 LogFactory 类，用于创建日志记录器
import org.apache.commons.logging.LogFactory;
// 引入 Spring Boot 的 ConditionalOnProperty 注解，用于根据配置属性决定是否加载该组件
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
// 引入 Spring 框架中的 HttpMethod 枚举，用于表示 HTTP 请求方法
import org.springframework.http.HttpMethod;
// 引入 Spring 框架中的 Nullable 注解，用于表示参数或返回值可以为 null
import org.springframework.lang.Nullable;
// 引入 Spring MVC 框架中的 ModelAndView 类，用于封装模型数据和视图信息
import org.springframework.web.servlet.ModelAndView;
// 引入 Spring MVC 框架中的 HandlerInterceptorAdapter 类，用于实现拦截器功能
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// 引入 Servlet API 中的 HttpServletRequest 类，用于表示 HTTP 请求
import javax.servlet.http.HttpServletRequest;
// 引入 Servlet API 中的 HttpServletResponse 类，用于表示 HTTP 响应
import javax.servlet.http.HttpServletResponse;
// 引入 Java IO 库中的 BufferedReader 类，用于缓冲读取字符流
import java.io.BufferedReader;
// 引入 Java IO 库中的 InputStreamReader 类，用于将字节流转换为字符流
import java.io.InputStreamReader;

/**
 * 分页拦截器
 * 该拦截器用于在请求处理过程中，根据请求参数判断是否需要进行分页处理，并设置相应的分页信息。
 *
 * @author Team Bachelor
 */
// 当配置属性 bachelor.paging.enabled 为 true 时加载该拦截器，若未配置则默认加载
@ConditionalOnProperty(prefix = "bachelor.paging",
        name = {"enabled"}, havingValue = "true", matchIfMissing = true)
public class PageInterceptor extends HandlerInterceptorAdapter {

    // 定义分页页码参数名的常量
    public static final String PAGE_NUM = "pageNum";
    // 定义分页每页数量参数名的常量
    public static final String PAGE_SIZE = "pageSize";
    // 定义分页标志参数名的常量
    public static final String PAGE_FLAG = "page";
    // 创建日志记录器，使用 final 修饰，确保其不可变
    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * 添加分页处理的拦截器
     * 从请求中解析 start, page 参数，如果这些参数有值，就设置 PageHelper;没有值就跳过
     *
     * @param request  HTTP 请求对象
     * @param response HTTP 响应对象
     * @param obj      处理请求的处理器对象
     * @return 如果需要继续处理请求则返回 true，否则返回 false
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object obj) {
        // 从请求中获取分页标志参数的值
        String flag = request.getParameter(PAGE_FLAG);
        // 如果分页标志为 'n' 或 'N'，表示不进行分页处理
        if("n".equals(flag) || "N".equals(flag)){
            // 清除之前设置的分页信息
            PageHelper.clearPage();
            return true;
        }
        try {
            // 初始化分页页码和每页数量变量
            String pageNum = null;
            String pageSize = null;
            // 判断请求方法是否为 GET
            if (request.getMethod().equals(HttpMethod.GET.name())) {
                // 从 GET 请求参数中获取分页页码和每页数量
                pageNum = request.getParameter(PAGE_NUM);
                pageSize = request.getParameter(PAGE_SIZE);
            } else if (request.getMethod().equals(HttpMethod.POST.name())) {
                // 创建 BufferedReader 用于读取 POST 请求的输入流
                BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                // 创建 StringBuilder 用于存储读取的请求体内容
                StringBuilder body = new StringBuilder();
                // 逐行读取请求体内容并添加到 StringBuilder 中
                reader.lines().forEach(body::append);
                // 将请求体内容解析为 JSONObject
                JSONObject o = JSONObject.parseObject(body.toString());
                // 从 JSONObject 中获取分页页码和每页数量
                pageNum = o.getString(PAGE_NUM);
                pageSize = o.getString(PAGE_SIZE);
            }
            // 判断分页页码和每页数量是否都为空
            if (StringUtils.isEmpty(pageNum) && StringUtils.isEmpty(pageSize)) {
                // 记录调试日志，表明未找到分页标志，不进行分页处理
                log.debug("找不到分页标志，不开始分页处理。");
                return true;
            }
            // 将分页页码字符串转换为基本数据类型 int
            int pageNumInt = Integer.parseInt(pageNum);
            // 将每页数量字符串转换为基本数据类型 int
            int pageSizeInt = Integer.parseInt(pageSize);
            // 记录调试日志，表明找到分页标志，开始进行分页处理
            log.debug("找到分页标志，开始分页处理。");
            // 清除之前设置的分页信息
            PageHelper.clearPage();
            // 设置分页信息，包括页码和每页数量
            PageHelper.startPage(pageNumInt, pageSizeInt);
        } catch (Exception e) {
            // 记录错误日志，打印异常信息
            log.error(e);
        }
        return true;
    }

    /**
     * 请求处理完成后的回调方法
     * 用于清除分页信息，避免对后续请求产生影响
     *
     * @param request      HTTP 请求对象
     * @param response     HTTP 响应对象
     * @param handler      处理请求的处理器对象
     * @param modelAndView 封装模型数据和视图信息的对象，可为 null
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) {
        // 清除分页信息
        PageHelper.clearPage();
    }
}