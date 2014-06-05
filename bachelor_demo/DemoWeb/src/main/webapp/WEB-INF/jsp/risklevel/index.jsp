<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>已完成设备维修流程</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
	<link href="<%=path %>/css/bootstrap.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/css/default/om-default.css" rel="stylesheet" type="text/css">
	<link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">
	<link type="text/css" href="<%=path %>/css/jquery-ui-1.10.0.custom.css" rel="stylesheet" />
	
	<script type="text/javascript"  src="<%=path %>/js/jquery-1.9.0.min.js"></script>
	<script  type="text/javascript"  src="<%=path %>/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script> 
	 <script type="text/javascript" src="<%=path %>/js/ui-auth-commom.js"></script>
    <script type="text/javascript" src="<%=path %>/js/commom-task.js"></script>
    <%
    	String function_id = (String)request.getSession().getAttribute("function_id");
     %>
    <style type="text/css">
	  body {
        padding-top: 20px;
        padding-bottom: 60px;
      }
    </style>
    <script type="text/javascript">
    	/** 如果没有要显示的节点信息，必须创建一个新的数据**/
    	var taskInfoArray = new Array();
    	
    	$(document).ready(function(){
    			 
    			 initUiElement("<%=path %>","<%=function_id %>",null);
    			 
    	});
    </script>
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="<%=path %>/js/html5shiv.js"></script>
    <![endif]--> 
  </head>

   <body>
	<div class="page-header">
		<h3>电网风险定级会签</h3>
	</div>
	
	<div style="border:1px solid red;width:80px; height:60px;display: none;" id="display_id">   
		asdf
		asdfasdf</br>
	</div>
	
	  <div style="padding: 10px;padding-left: 0px;"> 
	  		
	  		<a class="btn btn-info" href="<%=path %>/risklevel/initRiskLevel.htm">新增</a>	
	  		
	  </div>
	 <table class="table table-bordered table-striped table-hover">
		<thead>
		<tr>
			<th>序号</th>
			<th>风险描述</th>
			<th>风险级别</th>
			<th>二级单位风险审核信息</th>
			<th>本部电网风险审核信息</th>
			<th>本部设备风险审核信息</th> 
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
					<c:forEach items="${model}" var="process" varStatus="countNum">
						<tr>
							<td>
									<input type="checkbox" id="riskLevelProcessId" value="${process.id }">
									${countNum.count }
							</td>
							<td>${process.riskDesc }</td>
							<td>${process.riskLevel }(级)</td>
							<td>${process.verifyMsg}</td>
							<td>${process.review1Msg}</td>
							<td>${process.review2Msg }</td>
							<td>
									<a class="btn btn-info" href="<%=path %>/risklevel/initRiskLevel.htm?riskLevelId=${process.id }">操作</a>	
							</td>
						</tr>			
					</c:forEach>
		</tbody>
	</table>
	<div class="pagination pagination-large pagination-centered">
		<ul>
    		<li><a href="#">上一页</a></li>
    		<li><a href="#">1</a></li>
    		<li><a href="#">2</a></li>
    		<li><a href="#">3</a></li>
    		<li><a href="#">4</a></li>
    		<li><a href="#">5</a></li>
    		<li><a href="#">下一页</a></li>
  		</ul>
	</div>  
  </body>
</html>
