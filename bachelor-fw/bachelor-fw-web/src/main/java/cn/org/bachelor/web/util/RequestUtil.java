/*
 * @(#)ContextHelper.java	May 9, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package cn.org.bachelor.web.util;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Team Bachelor
 * liuzhuo 2021.1.11 last updated
 */
public class RequestUtil {
	
	private static ServletContext servletContext = null;
	private static ServletConfig servletConfig = null;

	public static HttpServletRequest getRequest(){
		ServletRequestAttributes ra = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes());
		if(ra == null){
			return null;
		}
		return ra.getRequest();
	}
	
	
	public static ServletContext getServletContext(){
		return servletContext;
	}

	
	public static void setServletContext(ServletContext sc){
		servletContext = sc;
	}


	public static ServletConfig getServletConfig() {
		return servletConfig;
	}


	public static void setServletConfig(ServletConfig sc) {
		servletConfig = sc;
	}

	public static String getIpAddr() {
		return getIpAddr(getRequest());
	}
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress;
		try {
			ipAddress = request.getHeader("x-forwarded-for");
			if ("0:0:0:0:0:0:0:1".equals(ipAddress)) { //服务端和客户端在一台机器
				ipAddress = null;
			}

			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if (ipAddress.equals("127.0.0.1")) {
					// 根据网卡取本机配置的IP
					try {
						InetAddress inet = InetAddress.getLocalHost();
						ipAddress = inet.getHostAddress();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
				}
			}
			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
				// = 15
				if (ipAddress.indexOf(",") > 0) {
					ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
				}
			}
		} catch (Exception e) {
			ipAddress = "";
		}
		// ipAddress = this.getRequest().getRemoteAddr();

		return ipAddress;
	}
	
}
