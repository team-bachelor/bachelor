<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
<script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
<title>异常信息页面</title>
<style type="text/css">
	 /* Base class */
	.bs-docs-example {
	  position: relative;
	  margin: 15px 0;
	  padding: 39px 19px 14px;
	  *padding-top: 19px;
	  background-color: #fff;
	  border: 1px solid #ddd;
	  -webkit-border-radius: 4px;
	     -moz-border-radius: 4px;
	          border-radius: 4px;
	}
	
	/* Echo out a label for the example */
	.bs-docs-example:after {
	  content: "异常信息";
	  position: absolute;
	  top: -1px;
	  left: -1px;
	  padding: 3px 7px;
	  font-size: 12px;
	  font-weight: bold;
	  background-color: #f5f5f5;
	  border: 1px solid #ddd;
	  color: #9da0a4;
	  -webkit-border-radius: 4px 0 4px 0;
	     -moz-border-radius: 4px 0 4px 0;
	          border-radius: 4px 0 4px 0;
	}
	
	/* Remove spacing between an example and it's code */
	.bs-docs-example + .prettyprint {
	  margin-top: -20px;
	  padding-top: 15px;
	}
	.baseProcess{
		cursor: pointer;
		text-align: 12px;
		width: 40px;
		height: 15px;
	}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		$("#goBack").bind("click",function(ev){
			window.history.go(-2);
		});  //exceptionInfoId
		$("#showExceptionInfoId").bind("click",function(ev){
			if(document.getElementById("exceptionInfoId").style.display == "block"){
				document.getElementById("exceptionInfoId").style.display = "none";
			} else {
				document.getElementById("exceptionInfoId").style.display = "block";
			}
		});
	});
</script>
</head> 
<!--main content-->
<body leftmargin="0" topmargin="0">
<div class="bs-docs-example">
    <div align="center" style="font-size: 16;" class="alert alert-error">
    			 ${model.errorMsg }&nbsp;&nbsp;
    			 <!-- <span class="label label-warning baseProcess" id="goBack">返回</span>&nbsp;&nbsp; -->
    </div>
    <div class="btn-group">
	    <button class="btn dropdown-toggle" data-toggle="dropdown" id="showExceptionInfoId">
	      	异常信息
	    <span class="caret"></span>
	    </button>
  </div> 
  <div class="table-bordered" style="margin-top: 10px;display: none;" id="exceptionInfoId">
	     	<span class="label label-important">异常名称:</span>
	     	<span class="label label-info" >${model.exceptionMsg}</span>
	     	<br/>
	     	<br/>
	     	<span class="label label-important">异常详细信息:</span>
	     	<br/>
	     	<span class="label label-info" style="margin-top: 10px;font-size: 13px; ">${model.stackTrace}</span>
	</div>
</div>

</body>
</html>
