<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色组维护</title>
<link href="<%=path %>/css/main.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/validate/validate.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/default/om-default.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=path %>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/operamasks-ui.js"></script> 
<!-- bootstrap -->
<link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
<link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">
<!-- bootstrap --> 
<style>
    html, body{ width: 100%; height: 100%; padding: 0; margin: 0;overflow: hidden;}
    
    .input_text {
	    border: 1px solid #6D869E;
	    height: 17px;
	    vertical-align: middle;
	    width: 50%;
	}
	label.error{
        background: #fff6bf url(<%=path %>/images/validate/alert.png) center no-repeat;
		background-position: 5px 50%;
		text-align: left;
		padding: 2px 20px 2px 25px;
		border: 1px solid #ffd324;
		display: none;
		width: 200px;
		margin-left: 10px;
   }
     #droplist{
        display:none; 
        position: absolute; 
        width:158px;
        /* 兼容IE6,7 */
        *width:154px;
        /* 兼容IE8 */
        width:162px\0;
        height:100px;
        border:1px solid;
        overflow: auto;
        background-color: #e3e9ef;
     } 
</style>
<script type="text/javascript">
 //定义全局的变量，给后台调用前台js留句柄
$(document).ready(function() {
	 
//*************************下面是Ajax提交*********************
    $('#reset').bind("click",function(ev){
    	$("#messageBoxAlertId").hide();
    });
});  
/** 手动提交表单 **/
function handSubmit(){
	$.ajax({
				url:"<%=path %>/auth/role/addOrUpdateRoleGroup.htm",
				type:"post",
				dataType:"json",
				data:$("#form1").serialize(),
				success:function(result){
					$("#messageBoxAlertId").show();
					if(result.resultCode == "0"){
						$("#messageBoxAlert").html("保存成功.");
						if($('#add_roleGroup_id').val()==null || $('#add_roleGroup_id').val()=="" || $('#add_roleGroup_id').val()=="null"){
							$("#add_roleGroup_id").val(result.roleGroupId);
							refreshParentTreeInfo(result.roleGroupId);
						} else {
							window.parent.updateProjectNode($('#add_roleGroup_id').val(),$('#add_roleGroup_name').val());
						}
					} else if(result.resultCode=="2"){
						$("#messageBoxAlert").html("角色组名称不能重复,请重新输入.");
					} else {
						$("#messageBoxAlert").html("保存失败.");
					}
				}
	});	
}
	
function refreshParentTreeInfo(id){
		if($('#add_roleGroup_id').val()!=null && $('#add_roleGroup_id').val()!="" && $('#add_roleGroup_id').val()!=undefined){
			  var nodeObj = {
	    	 	    			id:id,
	    	 	    			name: $('#add_roleGroup_name').val(), 
	    	 	    			hasChildren: false,
	    	 	    			nodeType: "group"
	    	 	    			};
				window.parent.reloadTreeInfo(nodeObj,$('#roleGroupParentId').val());
	   }
}
</script>

</head>
<body> 
<div class="page-header  text-center">
	<h4>角色组维护</h4>
</div>
<form class="form-horizontal"  id="form1" >
		<form:hidden path="roleGroup.parentRoleGroup.id" id="roleGroupParentId"/>
		<div class="control-group">
  			<label class="control-label" for="add_description">角色组ID:</label>
  			<div class="controls">
    			<form:input path="roleGroup.id" type="text" id="add_roleGroup_id" readonly="true"/>
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_description">角色组名称:</label>
  			<div class="controls">
    			<form:input path="roleGroup.name" type="text"  id="add_roleGroup_name" />
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
  		<div class="control-group">
  			<label class="control-label" for="add_roleGroup_description">角色组描述:</label>
  			<div class="controls">
    			<form:input path="roleGroup.description" type="text"  />
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_value">操作类型:</label>
  			<div class="controls">
    			<form:select path="roleGroup.flag">
    					<form:option value="0">可删除</form:option>
    					<form:option value="1">不可删除</form:option>
    			</form:select>
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