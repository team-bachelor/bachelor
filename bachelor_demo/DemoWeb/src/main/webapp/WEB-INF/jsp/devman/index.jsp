<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>设备维修申请一览</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
	<link href="<%=path %>/css.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/css/default/om-default.css" rel="stylesheet" type="text/css">
    <!-- Le styles -->
    <link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
	  body {
        padding-top: 20px;
        padding-bottom: 60px;
      }

      /* Custom container */
      .container {
        margin: 0 auto;
        max-width: 1000px;
      }
      .container > hr {
        margin: 60px 0;
      }

      /* Main marketing message and sign up button */
      .jumbotron {
        margin: 60px 0;
        text-align: center;
      }
      .jumbotron h1 {
        font-size: 60px;
        line-height: 1;
      }
      .jumbotron .lead {
        font-size: 24px;
        line-height: 1.25;
      }
      .jumbotron .btn {
        font-size: 21px;
        padding: 14px 24px;
      }

      /* Supporting marketing content */
      .marketing {
        margin: 60px 0;
      }
      .marketing p + h4 {
        margin-top: 28px;
      }
	.form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
    </style>
    <link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="<%=path %>/js/html5shiv.js"></script>
    <![endif]-->
    
   
  </head>

   <body>
	<div class="page-header">
		<h3>设备维修申请一览</h3>
	</div>
	<form class="form-inline">
		<div style="padding-top: 20px;height: 40px;">
			<div style="float: left;">
				<a href="start.htm" class="btn btn-large btn-primary ">新建设备维修申请</a>
			</div>
		</div>
	</form>
	队列任务<br/>
	<table class="table table-bordered table-striped table-hover">
		<thead>
		<tr>
			<th>流程ID</th>
			<th>执行ID</th>
			<th>任务ID</th>
			<th>节点名称</th>
			<th>节点描述</th>
			<th>创建时间</th>
			<th>操作</th>
			<th>删除</th>
		</tr>
		</thead>
		<tbody>
			<c:forEach items="${model.waitTasks}" var="task">
			<tr>
				<td>${task.task.processInstanceId }</td>
				<td>${task.task.executionId }</td>
				<td>${task.task.id}</td>
				<td>${task.task.name}</td>
				<td>${task.task.description }</td>
				<td><fmt:formatDate value="${task.task.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>	</td>
				<td><a href="<%=path %>${task.formKey}?taskId=${task.task.id }&funcId=${model.funcId}" class="btn btn-large btn-primary ">操作</a></td>
				<td><a href="<%=path %>/devman/end.htm?taskId=${task.task.id }" class="btn btn-large btn-primary ">删除</a></td>
			</tr>			
			</c:forEach>

		</tbody>
	</table>
	
	签收任务<br/>
	<table class="table table-bordered table-striped table-hover">
		<thead>
		<tr>
			<th>流程ID</th>
			<th>执行ID</th>
			<th>任务ID</th>
			<th>节点名称</th>
			<th>节点描述</th>
			<td>操作</td>
		</tr>
		</thead>
		<tbody>
			<c:forEach items="${model.claimedTasks}" var="task">
			<tr>
				<td>${task.task.processInstanceId }</td>
				<td>${task.task.executionId }</td>
				<td>${task.task.id}</td>
				<td>${task.task.name}</td>
				<td>${task.task.description }</td>
				<td><a href="verify.htm?taskId=${task.task.id }" class="btn btn-primary ">${task.task.name}</a></td>
			</tr>			
			</c:forEach>

		</tbody>
	</table>
	
    <script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
    <script src="<%=path %>/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script>
	<script type="text/javascript">
	</script>

  </body>
</html>
