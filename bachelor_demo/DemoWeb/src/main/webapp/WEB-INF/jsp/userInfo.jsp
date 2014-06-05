<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>平台控制台</title>
<link type="text/css" href="<%=path %>/css/bootstrap.css" rel="stylesheet" />
<link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">
<script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
<script src="<%=path %>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script>
<script src="<%=path %>/js/bootStrapCommon.js"></script>
<style>
      html, body{ width: 100%; height: 100%; padding: 0; margin: 0;}
      #center-tab .om-panel-body{
        padding: 0;
      }
</style>
</head>
<body>
 	<div class="page-header">
			<h4>用户基本信息</h4>
	</div>
	 <div class="row-fluid">
	       <ul class="thumbnails">
			         <li class="span4">
			         		<span  class="thumbnail" style="border-bottom: 0px;"><h5>用户基本信息</h5></span>
				           <span  class="thumbnail">
				                <span class="label label-info">用户名称:</span>
				                <span class="label "><%=loginUser.getUsername() %></span>
				                <br/>
				                <br/>
				                <span class="label label-info">OA名称:</span>
				                <span class="label "><%=loginUser.getId() %></span>
				                <br/>
				                <br/>
				                <span class="label label-info">所属单位:</span>
				                <span class="label "><%=loginUser.getOwnerOrg().getNamePath() %></span>
				           </span>
			         </li>
			         <li class="span4">
				           <span  class="thumbnail" style="border-bottom: 0px;"><h5>用户角色信息</h5></span>
				           <span  class="thumbnail">
				             		<c:forEach var="roles" items="${model}" varStatus="roleLen">
					             			<span class="badge badge-info"><c:out value="${roleLen.index+1}"/></span>
					             			<span class="label"><c:out value="${roles.memo}"/>/<c:out value="${roles.name}"/></span>
					             			<br/>
					             			<br/>
				             		</c:forEach>
				          </span>
			         </li>
			         <li class="span4">
				           <span  class="thumbnail" style="border-bottom: 0px;"><h5>用户功能信息</h5></span>
				           <span  class="thumbnail">
				           		<% int index = 1; %>
				            	<c:forEach var="roles" items="${model}" varStatus="afsLen">
				            			<c:if test="${roles.authFunctions!=null}">
				            					<c:forEach var="af" items="${roles.authFunctions}" varStatus="afLen">
				            							<span class="badge badge-info"><%=index %> </span>
								             			<span class="label"><c:out value="${af.func.name}"/></span>
								             			<br/>
								             			<br/> 
								             			<% index++; %>
				            					</c:forEach>
				            			</c:if>
				            	</c:forEach>
				          </span>
			         </li> 
	       </ul>
     </div>
</body>
</html>