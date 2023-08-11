<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模块属性维护</title>
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
    $('#form1').validation();
});  
//获取功能ID
function refreshParentTreeInfo(id){
	if($('#modelId').val()==null || $('#modelId').val()=="" || $('#modelId').val()==undefined){
		var nodeObj = {
  	 	    			id:id,
  	 	    			name : $('#add_name').val(), 
  	 	    			description:$("#add_description").val,
  	 	    			hasChildren : false,
  	 	    			nodeType: "func",
               	};
	 	window.parent.reloadTreeInfo(nodeObj,$('#modelParentModuleId').val());
	}
} 
//功能返回方法
function funReturnMethod(funcResult){ 
	funcResult = funcResult.replace(/=/g,":");
	var result = eval("("+funcResult+")");
	$("#messageBoxAlertId").show();
	if(result.resultCode == "0"){
		$("#messageBoxAlert").html("保存成功.");
		refreshParentTreeInfo(result.id);
		$('#modelId').val(result.id);
		//** 更新节点名称 **//
		window.parent.updateProjectNode($('#modelId').val(),$('#add_name').val());
	} else if(result.resultCode=="2"){
		$("#messageBoxAlert").html("业务编码不能重复，请更改合理的业务编码.");
	}else{
		$("#messageBoxAlert").html("保存失败.");
	}
}
/** 手动提交表单 **/
function handSubmit(){
	$('#form1').submit();
}

/** URL是否存在 **/
function isExistsUrl(){
	$.ajax({
 		url:"<%=path %>/ps/isExistsUrl.htm",
 		type:"get",
 		data:"url"+$("#add_description").val(),
 		dataType:"json", 
 		success:function(result){ 
 			$("#messageBoxAlertId").show();
 			if(result.resultCode=="0"){
 				$("#messageBoxAlert").html("该URI已经存在,URI不能重复添加.");
 			} else {
 				$("#messageBoxAlert").html("该URI不存在,可以添加.");
 			}
 		}
	});
}
</script>

</head>
<body>  
<div class="page-header  text-center">
	<h4>功能属性维护</h4>
</div>
<form class="form-horizontal" action="<%=path %>/ps/func/createAndUpdate.htm" method="post" id="form1"  target="hiddenframe">
		<form:hidden path="model.module.id" id="modelParentModuleId"/>
		<div class="control-group">
  			<label class="control-label" for="add_description">功能ID:</label>
  			<div class="controls">
    			<form:input path="model.id" type="text" id="modelId" readonly="true"/>
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_description">业务编码:</label>
  			<div class="controls">
    			<form:input path="model.code" type="text"  id="add_code" />
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
  		<div class="control-group">
  			<label class="control-label" for="add_description">功能名称:</label>
  			<div class="controls">
    			<form:input path="model.name" type="text" 
    					btvd-type="required" btvd-err="功能名称不能为空！" btvd-class='btvdclass'
    					id="add_name" />
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_value">功能描述:</label>
  			<div class="controls">
    			<form:input path="model.description" id="add_description"
    			  btvd-type="required" btvd-err="功能描述不能为空！" btvd-class='btvdclass'
    			  type="text" />
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_value">功能访问URI:</label>
  			<div class="controls">
    			<form:input path="model.entryPath" type="text" 
    				btvd-type="required" btvd-err="功能访问路径不能为空！" btvd-class='btvdclass' />
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
    			<c:if test="${model.id==null}">
    				<button type="button" class="btn btn-primary" onclick="isExistsUrl()">检测</button>
    			</c:if>
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
<iframe name="hiddenframe" style=display:none></iframe>
</body>
</html>