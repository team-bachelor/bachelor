/*
 * @(#)ContextHelper.java	May 9, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
// 声明当前类所在的包路径
package cn.org.bachelor.web.util;

// 引入 ServletConfig 类，用于获取 Servlet 的配置信息
import jakarta.servlet.ServletConfig;
// 引入 ServletContext 类，用于获取 Servlet 上下文信息
import jakarta.servlet.ServletContext;
// 引入 HttpServletRequest 类，用于处理 HTTP 请求
import jakarta.servlet.http.HttpServletRequest;

// 引入 Spring 的 RequestContextHolder 类，用于获取当前请求的上下文信息
import org.springframework.web.context.request.RequestContextHolder;
// 引入 Spring 的 ServletRequestAttributes 类，用于获取 Servlet 请求的属性
import org.springframework.web.context.request.ServletRequestAttributes;

// 引入 InetAddress 类，用于表示 IP 地址
import java.net.InetAddress;
// 引入 UnknownHostException 类，用于处理无法解析主机名的异常
import java.net.UnknownHostException;

/**
 * RequestUtil 类提供了一系列静态方法，用于获取请求、Servlet 上下文、Servlet 配置和客户端 IP 地址等信息。
 *
 * @author Team Bachelor
 * @since 2021.1.11  // 最后更新时间
 */
public class RequestUtil {
	// 静态的 ServletContext 实例，用于存储 Servlet 上下文信息
	private static ServletContext servletContext = null;
	// 静态的 ServletConfig 实例，用于存储 Servlet 配置信息
	private static ServletConfig servletConfig = null;

	/**
	 * 获取当前的 HttpServletRequest 对象。
	 *
	 * @return 当前的 HttpServletRequest 对象，如果不存在则返回 null
	 */
	public static HttpServletRequest getRequest() {
		// 通过 RequestContextHolder 获取当前请求的 ServletRequestAttributes
		ServletRequestAttributes ra = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
		// 如果 ServletRequestAttributes 为空，则返回 null
		if (ra == null) {
			return null;
		}
		// 返回当前的 HttpServletRequest 对象
		return ra.getRequest();
	}

	/**
	 * 获取 Servlet 上下文信息。
	 *
	 * @return 静态的 ServletContext 实例
	 */
	public static ServletContext getServletContext() {
		// 返回存储的 ServletContext 实例
		return servletContext;
	}

	/**
	 * 设置 Servlet 上下文信息。
	 *
	 * @param sc 要设置的 ServletContext 实例
	 */
	public static void setServletContext(ServletContext sc) {
		// 将传入的 ServletContext 实例赋值给静态变量
		servletContext = sc;
	}

	/**
	 * 获取 Servlet 配置信息。
	 *
	 * @return 静态的 ServletConfig 实例
	 */
	public static ServletConfig getServletConfig() {
		// 返回存储的 ServletConfig 实例
		return servletConfig;
	}

	/**
	 * 设置 Servlet 配置信息。
	 *
	 * @param sc 要设置的 ServletConfig 实例
	 */
	public static void setServletConfig(ServletConfig sc) {
		// 将传入的 ServletConfig 实例赋值给静态变量
		servletConfig = sc;
	}

	/**
	 * 获取客户端的 IP 地址，调用 getIpAddr(HttpServletRequest request) 方法。
	 *
	 * @return 客户端的 IP 地址
	 */
	public static String getIpAddr() {
		// 调用重载的 getIpAddr 方法，传入当前的 HttpServletRequest 对象
		return getIpAddr(getRequest());
	}

	/**
	 * 根据 HttpServletRequest 对象获取客户端的 IP 地址。
	 *
	 * @param request HttpServletRequest 对象
	 * @return 客户端的 IP 地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
		// 用于存储客户端的 IP 地址
		String ipAddress;
		try {
			// 尝试从请求头中获取 x-forwarded-for 字段的值
			ipAddress = request.getHeader("x-forwarded-for");
			// 如果 IP 地址为 "0:0:0:0:0:0:0:1"，表示服务端和客户端在同一台机器，将 IP 地址置为 null
			if ("0:0:0:0:0:0:0:1".equals(ipAddress)) {
				ipAddress = null;
			}

			// 如果 IP 地址为空或为 "unknown"，尝试从 Proxy-Client-IP 请求头中获取 IP 地址
			if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			// 如果 IP 地址仍然为空或为 "unknown"，尝试从 WL-Proxy-Client-IP 请求头中获取 IP 地址
			if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			// 如果 IP 地址还是为空或为 "unknown"，从请求的远程地址中获取 IP 地址
			if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				// 如果 IP 地址为 "127.0.0.1"，表示是本地地址，尝试根据网卡获取本机配置的 IP 地址
				if (ipAddress.equals("127.0.0.1")) {
					try {
						// 获取本地主机的 InetAddress 实例
						InetAddress inet = InetAddress.getLocalHost();
						// 获取本地主机的 IP 地址
						ipAddress = inet.getHostAddress();
					} catch (UnknownHostException e) {
						// 记录异常信息，可考虑使用日志框架替代 e.printStackTrace()
						e.printStackTrace();
					}
				}
			}
			// 对于通过多个代理的情况，第一个 IP 为客户端真实 IP，多个 IP 按照 ',' 分割
			if (ipAddress != null && ipAddress.length() > 15) {
				// 如果 IP 地址中包含逗号，截取第一个 IP 地址
				if (ipAddress.indexOf(",") > 0) {
					ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
				}
			}
		} catch (Exception e) {
			// 发生异常时，将 IP 地址置为空字符串
			ipAddress = "";
		}
		// 返回获取到的 IP 地址
		return ipAddress;
	}
}