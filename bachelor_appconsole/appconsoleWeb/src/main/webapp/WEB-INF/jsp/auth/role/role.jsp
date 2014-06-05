<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色维护</title>
<!-- bootstrap -->
<link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="<%=path %>/css/jquery-ui-1.10.0.custom.css" rel="stylesheet" />
<link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">
<script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
<script src="<%=path %>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script>
<script src="<%=path %>/js/bootStrapCommon.js"></script>
<script src="<%=path %>/js/bt-validate.js"></script>
<!-- bootstrap --> 
<style>
    html, body{ width: 100%; height: 100%; padding: 0; margin: 0;overflow: hidden;}
</style>
<script type="text/javascript">
//定义全局的变量，给后台调用前台js留句柄
$(document).ready(function() {
	 
//*************************下面是Ajax提交*********************
    $('#reset').bind("click",function(ev){
    	$("#messageBoxAlertId").hide();
    });
});  
//获取功能ID
function refreshParentTreeInfo(id){
	if($('#modelGroupId').val()!=null && $('#modelGroupId').val()!="" && $('#modelGroupId').val()!=undefined){
		var nodeObj = {
  	 	    			id:id,
  	 	    			name : $('#add_description').val(), 
  	 	    			hasChildren : false,
  	 	    			nodeType: "role",
               	};
	 	window.parent.reloadTreeInfo(nodeObj,$('#modelGroupId').val());
	}
}  
/** 手动提交表单 **/
function handSubmit(){
	$.ajax({
				url:"<%=path %>/auth/role/update.htm",
				type:"post",
				dataType:"json",
				data:$("#form1").serialize(),
				success:function(result){
					$("#messageBoxAlertId").show();
					if(result.ResultCode == "0"){
							$("#messageBoxAlert").html("保存成功.");
							if($('#modelId').val()==null || $('#modelId').val()=="" || $('#modelId').val()=="null"){
								$("#modelId").val(result.RoleId);
								refreshParentTreeInfo(result.RoleId);
							} else {
								window.parent.updateProjectNode($('#modelId').val(),$('#add_description').val());
							}
					} else if(result.ResultCode=="2"){
						$("#messageBoxAlert").html("角色名称不能重复,请重新输入.");
					} else {
						$("#messageBoxAlert").html("保存失败.");
					}
				}
	});	
}
</script>

</head>
<body>  
<div class="page-header  text-center">
	<h4>角色维护</h4>
</div>
<form class="form-horizontal"  id="form1">
		<form:hidden path="model.group.id" id="modelGroupId"/>
		<div class="control-group">
  			<label class="control-label" for="add_description">角色ID:</label>
  			<div class="controls">
    			<form:input path="model.id" type="text" id="modelId" readonly="true"/>
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_description">角色名称:</label>
  			<div class="controls">
    			<form:input path="model.name" type="text"  id="add_name" />
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
  		<div class="control-group">
  			<label class="control-label" for="add_description">角色描述:</label>
  			<div class="controls">
    			<form:input path="model.description" type="text"  id="add_description"/>
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_value">备注:</label>
  			<div class="controls">
    			<form:input path="model.memo" id="add_memo" type="text" />
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_value">显示顺序:</label>
  			<div class="controls">
    			<form:input path="model.showOrder" id="add_showOrder" type="text" />
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="row">
			<div style="text-align: center;float: left;width: 320px;" >
				<button id="submitForm" type="button" class="btn btn-info" onclick="handSubmit()">保存</button>		
				<button id="reset" type="reset" class="btn btn-primary">重置</button>
			</div>
			<div  style="float: right; display: none;" id="messageBoxAlertId" >
				<div class="alert alert-error" style="width:200px;" >
				    <strong id="messageBoxAlert"></strong> 
				</div>
			</div>
		</div>
</form>
</body>
</html>