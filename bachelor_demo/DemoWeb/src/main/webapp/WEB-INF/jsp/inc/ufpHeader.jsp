<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.bachelor.org.domain.User"%> 
<%@page import="java.util.List"%>
<%@page import="org.bachelor.facade.service.IContextService" %>
<%@page import="org.bachelor.facade.service.impl.FacadeHolderServiceImpl"%>
<%@page import="org.bachelor.auth.domain.Role"%>
<%@page import="org.bachelor.ps.domain.ProjectProperty"%>
<%@page import="org.bachelor.auth.domain.AuthFunction" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//当前登录用户信息
IContextService ctxService = FacadeHolderServiceImpl.getContextService();

User loginUser = ctxService.getLoginUser();
//当前登录用户角色权限集合
List<Role> loginUserRoles = ctxService.getLoginRoles();
//项目基本信息
ProjectProperty projectProperty = ctxService.getProjectProperty();
%>    

