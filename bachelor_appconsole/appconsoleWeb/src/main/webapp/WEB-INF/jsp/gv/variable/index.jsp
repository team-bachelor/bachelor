<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>全局变量维护</title>
	<link href="<%=path %>/css.css" rel="stylesheet" type="text/css" />
    <link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="<%=path %>/css/jquery-ui-1.10.0.custom.css" rel="stylesheet" />

    <style type="text/css">
      html,
      body {
        height: 90%;
      }
      .container .credit {
        margin: 0 0;
      }
      code {
        font-size: 80%;
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
		<h3>全局变量维护</h3>
	</div>
	<form class="form-inline">
		<div>
			<span class="label">变量名:</span>
			<input type="text" class="span2" placeholder="变量名" id="searchName">
			<span class="label">变量描述:</span>
			<input type="text" class="span3" placeholder="变量描述" id="searchDescript">
			<span class="label">变量级别:</span>
			<select class="input-small"  id="searchFlag">
				<option></option>
				<option value="1">平台级</option>
				<option value="2">系统级</option>
			</select>
		</div>
		<div style="padding-top: 20px;height: 40px;">
			<div style="float: left;">
				<button id="queryBtn" type="button" class="btn btn-info">查询</button>		
				<button id="addBtn" type="button" class="btn btn-primary">增加</button>
				<button id="updateBtn" type="button" class="btn btn-primary">修改</button>
				<button id="deleteBtn" type="button" class="btn btn-danger">删除</button>
			</div>
			<div  style="float: right;display: none;" id="messageBoxAlertId" >
				<div class="alert" style="width:280px;" >
				    <strong id="messageBoxAlert"></strong> 
				</div>
			</div>
		</div>
	</form>
	<table class="table table-bordered table-striped table-hover">
		<thead>
		<tr>
			<th><input type = "checkbox" id="mutilSelectItem"  name = "mutilSelectItem" />&nbsp;&nbsp;选择</th>
			<th>ID</th>
			<th>变量名</th>
			<th>变量描述</th>
			<th>变量值</th>
		</tr>
		</thead>
		<tbody id="varTbody"></tbody>
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
	
	<div id="addDialog" title="添加全局变量">
    	<form class="form-horizontal" action="add_5" method="post" id="add_from_5">
    		<div class="control-group">
    			<label class="control-label" for="add_name">全局变量名</label>
    			<div class="controls">
      				<input type="text" id="add_name" name="name" placeholder="全局变量名">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="add_description">全局变量描述</label>
    			<div class="controls">
      				<input type="text" id="add_description" name="description" placeholder="全局变量描述">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="add_value">全局变量值</label>
    			<div class="controls">
      				<input type="text" id="add_value" name="value" placeholder="全局变量值">
    			</div>
  			</div>

    	</form>
    </div>
    
    <div id="updateDialog" title="修改全局变量">
    	<form class="form-horizontal" action="update_5" method="post" id="update_from_5">
    		<input type="hidden" id="update_id" name="id" >
    		<div class="control-group">
    			<label class="control-label" for="update_name">全局变量名</label>
    			<div class="controls">
      				<input type="text" id="update_name" name="name" placeholder="全局变量名">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="update_description">全局变量描述</label>
    			<div class="controls">
      				<input type="text" id="update_description" name="description" placeholder="全局变量描述">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="update_value">全局变量值</label>
    			<div class="controls">
      				<input type="text" id="update_value" name="value" placeholder="全局变量值">
    			</div>
  			</div>

    	</form>
    </div>
 
	<div id="delModel" title="确定删除">
    		<div class="control-group">
    			<label class="control-label" for="update_name">确定删除全局变量信息?</label>
  			</div>
    </div>
	
    <script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
    <script src="<%=path %>/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script>
	<script type="text/javascript"><!--
	var varDataArray;
	$(document).ready(function() {
		varDataArray = new Array();
		
		$('#mutilSelectItem').click(function(){
		   var obj=document.getElementsByName('idCheckBox');  //选择所有name="'test'"的对象，返回数组  
		   var s='';  
		   for(var i=0; i<obj.length; i++){  
		   		obj[i].checked = this.checked;
		   }  
			
		});
		 
		$('#addBtn').click(function(e){
			//隐藏上一次信息提示
			$("#messageBoxAlertId").hide();
			
			$("#add_from_5")[0].reset();
			
			$('#addDialog').dialog('open');
		    return false;
		});
		
		$('#updateBtn').click(function(e){
			//隐藏上一次信息提示
			$("#messageBoxAlertId").hide();
			
			$("#update_from_5")[0].reset();
			
			 var var_checkBoxVal = new Array();
			 $('input[name="idCheckBox"]:checked').each(function(){
			 	var_checkBoxVal.push($(this).val());
			 });
			 if(var_checkBoxVal.length == 1){
			 	
				evaluationUpdateWin(var_checkBoxVal[0]);
			} else if(var_checkBoxVal.length>1){
				$("#messageBoxAlertId").show();
				$("#messageBoxAlert").html("修改只能选择一条信息.");
			} else {
				$("#messageBoxAlertId").show();
				$("#messageBoxAlert").html("请选择你要修改的信息.");
			}
		    return false;
		});
		//删除信息框
		$('#delModel').dialog({
		    autoOpen: false,
		    modal: true,
		    buttons: {
		    	 	"Ok": function () {
		        	 	 var var_checkBoxVal = new Array();
						 $('input[name="idCheckBox"]:checked').each(function(){
						 	var_checkBoxVal.push($(this).val());
						 });
						deleteVariableInfo(var_checkBoxVal);
			        },
			        "Cancel": function () {
			            $(this).dialog("close");
			        }
		    }
		});
		//添加信息框
		$('#addDialog').dialog({
		    autoOpen: false,
		    width: 600,
		    buttons: {
		        "Ok": function () {
		        	$.ajax({
		    	 		url:"<%=path %>/gv/variable/update.htm",
		    	 		contentType:"application/json",
		    	 		type:"get",
		    	 		dataType:"json",
		    	 		data:$("#add_from_5").serialize(),
		    	 		success:function(result){ 
		    	 			$("#messageBoxAlertId").show();
		    	 			if(result.ResultCode == "0"){
								$("#messageBoxAlert").html("添加成功.");
		    	 			} else if(result.ResultCode == "2"){
		    	 				$("#messageBoxAlert").html("不能重复添加全局变量名称.");
		    	 			} else {
		    	 				$("#messageBoxAlert").html("添加失败.");
		    	 			}
		    	 			$('#addDialog').dialog('close');
		    	 			//加载全局变量信息
							loadVariableInfo();
		    	 		},
		        		error:function(){
		        			$("#messageBoxAlert").html("服务器连接错误，请重试.");
		        		}
		    	 	});
		        },
		        "Cancel": function () {
		            $(this).dialog("close");
		        }
		    }
		});
		//修改信息框
		$('#updateDialog').dialog({
		    autoOpen: false,
		    width: 600,
		    buttons: {
		        "Ok": function () {
		        	$.ajax({
		    	 		url:"<%=path %>/gv/variable/update.htm",
		    	 		contentType:"application/json",
		    	 		type:"get",
		    	 		dataType:"json",
		    	 		data:$("#update_from_5").serialize(),
		    	 		success:function(result){ 
		    	 			$("#messageBoxAlertId").show();
		    	 			if(result.ResultCode == "0"){
		    	 				$("#messageBoxAlert").html("修改成功.");
		    	 			} else if(result.ResultCode == "2"){
		    	 				$("#messageBoxAlert").html("不能重复添加全局变量名称.");
		    	 			} else {
		    	 				$("#messageBoxAlert").html("修改失败.");
		    	 			}
		    	 			$('#updateDialog').dialog('close');
		    	 			//加载全局变量信息
							loadVariableInfo();
		    	 		},
		        		error:function(){
		        			$("#messageBoxAlert").html("服务器连接错误，请重试.");
		        		}
		    	 	});
		        },
		        "Cancel": function () {
		            $(this).dialog("close");
		        }
		    }
		});
		 
		//删除信息
		$("#deleteBtn").bind("click",function() {
			//隐藏上一次信息提示
			$("#messageBoxAlertId").hide();
			
			 var var_checkBoxVal = new Array();
			 $('input[name="idCheckBox"]:checked').each(function(){
			 	var_checkBoxVal.push($(this).val());
			 });
			 if(var_checkBoxVal.length>0){
			 		$('#delModel').dialog('open');
			} else {
				$("#messageBoxAlertId").show();
				$("#messageBoxAlert").html("请选择要删除的信息.");
			}
		});
		
		//加载全局变量信息
		loadVariableInfo();
	});
	
	//显示修改页面
	function ondblClickShowUpdateWin(id){
			evaluationUpdateWin(id);
	}
	
	//赋值更新页面
	function evaluationUpdateWin(id){
			$('#updateDialog').dialog('open');
			var obj = searchDataItem(id);
			$('#update_id').val(obj.id);
			$('#update_name').val(obj.name);
			$('#update_description').val(obj.description);
			$('#update_value').val(obj.value);
	}

	//查询选项
	function searchDataItem(id){
		var returnObj = new Object();
		if(varDataArray!=null && varDataArray.length>0){
			for(var i=0;i<varDataArray.length;i++){
				var obj = varDataArray[i];
				if(obj.id == id){
					returnObj = obj;
					break;
				}
			}
		}
		return returnObj;
	}
	
	//加载全局变量信息
	function loadVariableInfo(){
		$.ajax({
				url:"<%=path %>/gv/variable/queryAll.htm?name="+$("#searchName").val()+"&description="+$("#searchDescript").val()+"&flag="+$("#searchFlag").val(),
				type:"get",
				dataType:"json",
				success:function(result){
					var var_list = result.rows;
					varDataArray = var_list;
					if(var_list!=null && var_list.length>0){
						var html = "";
						for(var i=0;i<var_list.length;i++){
							var obj = var_list[i];
							html+= '<tr class="data"  ondblclick="ondblClickShowUpdateWin(\''+obj.id+'\')">';
							html+='<td><input type="checkbox" value="'+obj.id+'" name="idCheckBox"/></td>';
							html+='<td>'+obj.id+'</td>';
							html+='<td>'+obj.name+'</td>';
							html+='<td>'+obj.description+'</td>';
							html+='<td>'+obj.value+'</td>';
							html += "</tr>";
						}
						$("#varTbody").html(html);
					} else {
						$("#varTbody").html("<tr class=\"data\"><td colspan=\"4\"><div style=\"text-align:text;\">没有查询到数据，请重试查询条件.</div><td></tr>");
					}				
				}
		});
	}
	
	//查询信息
	$("#queryBtn").bind("click",function() {
		//隐藏上一次信息提示
		$("#messageBoxAlertId").hide();
		//查询
		loadVariableInfo();
	});
	
	//删除信息
	function deleteVariableInfo(info){
		$.ajax({
				url:"<%=path %>/gv/variable/delete.htm?delInfo="+info,
				type:"get",
				dataType:"json",
				success:function(result){
					$("#messageBoxAlertId").show();
					if(result.ResultCode == "0"){
						$("#messageBoxAlert").html("删除成功.");
						$('#delModel').dialog('close');
						loadVariableInfo();
					} else {
						$("#messageBoxAlert").html("删除失败.");
					}
				}
		});
	}
	--></script>

  </body>
</html>
