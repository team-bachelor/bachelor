<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Demo应用</title>
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

    <div class="container">

      <!-- Jumbotron -->
      <div class="jumbotron">
        <h1>编码测试</h1>
      </div>
      
      <hr/>
      
		<form name="model1" class="form-signin" action="<%=path %>/test/encoding/get.htm" method="get" >
        <h2 class="form-signin-heading">GET方法测试</h2>
        <input name="userId1" type="text" class="input-block-level" placeholder="用户Id" value="${model1.userId1 }">
        <input name="userName1" type="text" class="input-block-level" placeholder="用户名" value="${model1.userName1 }">
        <button class="btn btn-large btn-primary" type="submit">提交</button>
      	</form>
      
      	<form name="model2" class="form-signin" action="<%=path %>/test/encoding/post.htm" method="post">
        <h2 class="form-signin-heading">POST方法测试</h2>
        <input name="userId2" type="text" class="input-block-level" placeholder="用户Id" value="${model2.userId2 }">
        <input name="userName2" type="text" class="input-block-level" placeholder="用户名" value="${model2.userName2 }">
        <button class="btn btn-large btn-primary" type="submit">提交</button>
		</form>
		
		
		<button class="btn btn-large btn-primary" onclick="javascript:ajaxGet();" type="button">AJAX GET请求</button>
		
      <div class="footer">
        <p>&copy; 华商科技信息中心 2013</p>
      </div>

    </div>
 
<script type="text/javascript">
	
	$(document).ready(function(){
	
	});
	
	function ajaxGet(){
		$.ajax({
			url:"<%=path %>/test/encoding/get.htm?userId1=&userName1=胡斌",
			method:"GET",
			dataType:"json",
			success:function(data, textStatus){
				alert(data);
				return false;
				
			},
			error:function(request, textStatus, errorThrown){
				var msg = $.parseJSON(request.responseText).error;
				alert(msg);

				return false;
			}
		});	
	}
		
	</script>


  </body>
</html>
