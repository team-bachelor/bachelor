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
    <script type="text/javascript" src="<%=path %>/js/commom-task.js"></script>
    
    <script type="text/javascript" src="<%=path %>/js/ui-auth-commom.js"></script>
    <style type="text/css">
	  body {
        padding-top: 20px;
        padding-bottom: 60px;
      }
    </style>
    <script type="text/javascript">
    		var taskInfoArray = new Array();
    		$(document).ready(function(){
    			
	    			/** 初始化页面UI控件 **/
	    			var functionId = "<%=request.getSession().getAttribute("function_id") %>";
	    			initUiElement("<%=path %>",functionId,"${riskLevel.id }");
    			
   			});
    	/** 提交信息 **/
    	function addOrUpdateRiskLevel(){
    			if($("#add_chanage_riskLevel").val()==null || $("#add_chanage_riskLevel").val()==""){
    					$("#add_chanage_riskLevel").val(1);
    			}
    			$.ajax({
    						url:"<%=path %>/risklevel/addOrUpdate.htm",
    						type:"get",
    						data:$("#riskLevelFrom").serialize(),
    						success:function(result){
    								if(result.resultCode=="0"){
    									location.href = "<%= path %>/risklevel/index.htm";
    								} else {
    									$("#messageBoxAlertId").show();
    									$("#messageBoxAlert").html("操作失败,请换操作方式.");
    								}
    						}
    			});
    	} 
    	/** 改变值时操 **/
    	function chanageRiskLevel(val){
    		$("#add_chanage_riskLevel").val(val);
    	}
    </script>
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="<%=path %>/js/html5shiv.js"></script>
    <![endif]--> 
  </head>

   <body> 
   <div id="testDivShowId">
   			asdfqwerqwer</br>asdfqwerqwerqwer
   </div>
   
      	<form class="form-horizontal" id="riskLevelFrom" > 
	    		<input type="hidden" id="update_id" name="id" value="${riskLevel.id }">
	    		
	    		<input type="hidden" id="bizKey" name="bizKey" value="${riskLevel.id }">
	    		
	    		
	    		<div class="control-group">
	    			<label class="control-label" for="update_name">风险描述</label>
	    			<div class="controls">
	      				<input type="text" id="add_riskDesc" name="riskDesc"  value="${riskLevel.riskDesc }">
	      				<input type="hidden" id="add_riskUserId" name="riskUserId" value="${riskLevel.riskUserId }">
	    			</div>
	  			</div>
	    		<div class="control-group">
	    			<label class="control-label" for="update_description">风险级别</label>
	    			<div class="controls" >
	    				<input type="hidden" id="add_chanage_riskLevel" name="riskLevel" value="${riskLevel.riskLevel }">
	      				<select id="add_riskLevel" onchange="chanageRiskLevel(this.value)">
	      						<option value="1">一级</option>
	      						<option value="2">二级</option>
	      						<option value="3">三级</option>
	      						<option value="4">四级</option>
	      				</select>
	    			</div>   
	  			</div>
	    		<div class="control-group">
	    			<label class="control-label" for="update_value">二级单位风险审核信息</label>
	    			<div class="controls">
	      				<input type="hidden" id="add_verifyUserId" name="verifyUserId"  value="${riskLevel.verifyUserId }">
	      				<input type="text" id="add_verifyMsg" name="verifyMsg"  value="${riskLevel.verifyMsg }">
	      				<input type="hidden" id="add_verifyDate" name="verifyDateTime"  value="${riskLevel.verifyDateTime }">
	    			</div>
	  			</div>
	  			<div class="control-group">
	    			<label class="control-label" for="update_value">本部电网风险审核信息</label>
	    			<div class="controls">
	      				<input type="hidden" id="add_review1UserId" name="review1UserId"  value="${riskLevel.review1UserId }">
	      				<input type="text" id="add_review1Msg" name="review1Msg"  value="${riskLevel.review1Msg }">
	      				<input type="hidden" id="add_review1Date" name="review1DateTime"  value="${riskLevel.review1DateTime }">
	    			</div>
	  			</div>
	  			<div class="control-group">
	    			<label class="control-label" for="update_value">本部设备风险审核信息</label>
	    			<div class="controls">
	      				<input type="hidden" id="add_review2UserId" name="review2UserId"  value="${riskLevel.review2UserId }">
	      				<input type="text" id="add_review2Msg" name="review2Msg"   value="${riskLevel.review2Msg }">
	      				<input type="hidden" id="add_review2Date" name="review2DateTime"  value="${riskLevel.review2DateTime }">
	    			</div>
	  			</div>
	  			<div class="control-group">
	      				<input type="button" id="submitBtn"  class="btn btn-info" onclick="addOrUpdateRiskLevel()" value="操作"> 
	      				<a href="<%=path %>/risklevel/index.htm"  class="btn btn-primary" >返回</a>
	  			</div>
   	</form>
   	<div  style="float: right;display: none;" id="messageBoxAlertId" >
		<div class="alert alert-error" style="width:280px;" >
	    		<strong id="messageBoxAlert"></strong> 
		</div>
	</div>
  </body>
</html>
