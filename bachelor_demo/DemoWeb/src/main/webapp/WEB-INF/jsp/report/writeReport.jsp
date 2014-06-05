<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>填写请假申请</title>
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
		<h3>填写请假申请</h3>
	</div>
	<form class="form-inline" name="report" action="doWrite.htm" method="post">
		<div>
			<input type="hidden" value="${model.domainId }" name="id">
			<input type="hidden" value="${model.taskId }" name="taskId">
			<span class="label">标题:</span>
			<input type="text" class="span2" name="title">
		</div>
		<div>
			<span class="label">内容:</span>
			<input type="text" class="span3" name="content">
		</div>
		<div style="padding-top: 20px;height: 40px;">
			<div style="float: left;">
				<button id="doWrite" type="submit" class="btn btn-info">保存</button>		
				<a href="index.htm" class="btn btn-primary ">返回</a>
			</div>
		</div>
	</form>
	
    <script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
    <script src="<%=path %>/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script>
	<script type="text/javascript">
	</script>

  </body>
</html>
