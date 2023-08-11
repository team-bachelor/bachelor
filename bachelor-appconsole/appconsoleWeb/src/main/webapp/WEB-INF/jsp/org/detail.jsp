<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组织人员维护</title>
<!-- bootstrap -->
<link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="<%=path %>/css/jquery-ui-1.10.0.custom.css" rel="stylesheet" />
<link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">
<script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
<script src="<%=path %>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script>
<script src="<%=path %>/js/bootStrapCommon.js"></script>
<!-- bootstrap --> 
<style>
    html, body{ width: 100%; height: 100%; padding: 0; margin: 0;overflow: hidden;}
</style>
<script type="text/javascript">
//定义全局的变量，给后台调用前台js留句柄
$(document).ready(function() {
//*************************下面是Ajax提交*********************
    $('#submit').bind("click",function() {
        $.ajax({
        	url:"<%=path %>/org/info/update.htm",
        	type:"post",
        	dataType:"json",
        	data:$("#form1").serialize(),
        	success:function(result){
        		$("#messageBoxAlertId").show();
        		if(result.resultCode == "0"){
        			$("#messageBoxAlert").html("保存成功.");
        		}else{
        			$("#messageBoxAlert").html("保存失败.");
        		}
        		if(result.showType!=$("#showFlagId").val()){
        			//调用父页面树刷新方法
        			window.parent.refreshZTreeData();
        		} 
        	},
        	error:function(err){
        		$("#messageBoxAlertId").show();
        		$("#messageBoxAlert").html("数据库连接异常，请联系管理员.");
        	}
        });
    });
    $('#reset').bind("click",function(ev){
    	$("#messageBoxAlertId").hide();
    });

});   
</script>

</head>
<body>  
<div class="page-header  text-center">
	<h4>组织机构维护</h4>
</div>
<form class="form-horizontal" action="#" method="post" id="form1">
		<form:hidden path="model.id"/>
  		<div class="control-group">
  			<label class="control-label" for="add_description">组织机构简称:</label>
  			<div class="controls">
    			<form:input path="model.shortName" type="text" id="add_shortName"/>
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_value">组织机构中文名称:</label>
  			<div class="controls">
    			<form:input path="model.name" type="text"/>
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_value">是否需要前台展示:</label>
  			<div class="controls">
    			<form:select path="model.flag" id="showFlagId">
    				<form:option value="1">显示</form:option>
    				<form:option value="2">不显示</form:option>
    			</form:select>
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div> 
		<div class="control-group">
  			<label class="control-label" for="add_value">排序:</label>
  			<div class="controls">
    			<form:input path="model.showOrder" type="text" />
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div> 
		<div class="row">
			<div style="text-align: center;float: left;width: 280px;" >
				<button id="submit" type="button" class="btn btn-info">保存</button>		
				<button id="reset" type="reset" class="btn btn-primary">重置</button>
			</div>
			<div  style="float: right; display: none;" id="messageBoxAlertId" >
				<div class="alert alert-error" style="width:220px;" >
				    <strong id="messageBoxAlert"></strong> 
				</div>
			</div>
		</div>
</form>
</body>
</html>