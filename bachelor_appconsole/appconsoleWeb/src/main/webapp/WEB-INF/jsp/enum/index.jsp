<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>枚举维护</title>

<!-- bootstrap -->
<link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="<%=path %>/css/jquery-ui-1.10.0.custom.css" rel="stylesheet" />
<link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">
<script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
<script src="<%=path %>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script>
<script src="<%=path %>/js/bootStrapCommon.js"></script>
<!-- bootstrap --> 

<script type="text/javascript">
		var genderOptions ;
		var enumSearchData;
		var flagArray = ["平台","系统"];
        $(document).ready(function() {
        	enumSearchData = new Array();
        	
        	//全选
        	selectAllItem("mutilSelectItem","idCheckBox");
        	 
			//新增信息
            $('#add').click(function(){
            	$("#messageBoxAlertId").hide();
        		$('#add_from_5')[0].reset();
        		
        		$("#addDialog").dialog("open");
            });
            //查询信息
            $('#search').click(function(){
            	initGrid();
            });

            $('#del').click(function(){
            	$("#messageBoxAlertId").hide();
				var roleDeleInfo = getCheckBoxValue("idCheckBox");
				if(roleDeleInfo!=null && roleDeleInfo.length>0){
					$('#delModel').dialog("open");
				} else {
					$("#messageBoxAlertId").show();
					$("#messageBoxAlert").html("请选择要删除枚举信息.");
				}
            });
 
          	//修改信息框
        	showUpdateDialog("updateDialog","<%=path %>/genum/add.htm","update_from_5","messageBoxAlertId","messageBoxAlert",initGrid);
        	//添加信息框
        	showAddDialog("addDialog","<%=path %>/genum/add.htm","add_from_5","messageBoxAlertId","messageBoxAlert",initGrid);
        	//加载删除对话框
        	loadDelDialog("delModel","idCheckBox",delUiElement);
            
            //初始化表格
            initGrid();
        });
        
        function initGrid(){
        	$.ajax({
    			url:"<%=path %>/genum/all.htm",
    			type:"get",
    			dataType:"json",
    			data:$('#enumFormId').serialize(),
    			success:function(result){
    				var var_list = result.rows;
    				enumSearchData = var_list;
    				if(var_list!=null && var_list.length>0){
    					var html = "";
    					for(var i=0;i<var_list.length;i++){
    						var obj = var_list[i];
    						html+= '<tr class="data" ondblclick="evaluationUpdateWin(\''+obj.id+'\')">';
    						html+='<td><input type="checkbox" value="'+obj.id+'" name="idCheckBox"/></td>';
    						html+='<td>'+obj.enumName+'</td>';
    						html+='<td>'+obj.enumDesc+'</td>';
    						html+='<td>'+obj.fieldDesc+'</td>';
    						html+='<td>'+obj.fieldValue+'</td>'; 
    						html+='<td>'+obj.fieldComment+'</td>';
    						html+='<td>'+flagArray[obj.flag-1]+'</td>';
    						html += "</tr>";
    					}
    					$("#enumTbody").html(html);
    				} else {
    					$("#enumTbody").html("<tr class=\"data\"><td colspan=\"7\"><div style=\"text-align:text;\">没有查询到数据，请重试查询条件.</div><td></tr>");
    				}				
    			}
    		});
        }
        
     	 //赋值更新页面
    	function evaluationUpdateWin(id){
    			$('#updateDialog').dialog('open');
    			var obj = searchDataItem(id,enumSearchData);
    			$('#update_id').val(obj.id);
    			$('#update_enum_enumName').val(obj.enumName);
    			$('#update_enum_enumDesc').val(obj.enumDesc);
    			$('#update_enum_fieldDesc').val(obj.fieldDesc);
    			$('#update_enum_fieldValue').val(obj.fieldValue);
    			$('#update_enum_fieldComment').val(obj.fieldComment);
    			//$('#update_ui_flag').val(obj.id);
    			var authObj = document.getElementById("update_ui_flag");
    			authObj[0].selected = true;
    	}
        
        //删除信息
        function delUiElement(info){
			 $.ajax({
		   	 		url:"<%=path %>/genum/delete.htm?delInfo="+info,
		   	 		contentType:"application/json",
		   	 		type:"post",
		   	 		dataType:"json",
		   	 		success:function(result){ 
			   	 			$("#messageBoxAlertId").show();
			   	 			if(result.ResultCode=="0"){
			   	 				$("#messageBoxAlert").html("删除成功.");
			   	 			} else {
			   	 				$("#messageBoxAlert").html("删除失败.");
			   	 			}
			   	 			$('#delModel').dialog("close");
		 	            	initGrid();
		   	 		},
		       		error:function(){
		       			$("#messageBoxAlertId").show();
						$("#messageBoxAlert").html("数据库连接错误.");
		       		}
	   	 	}); 
        }

</script>


</head>
<body > 
	<div class="page-header">
		<h4>枚举信息维护</h4>
	</div>
	<form class="form-inline" id="enumFormId">
		<div>
			<span class="label">枚举名称:</span>
			<input type="text" class="span2" placeholder="枚举名称" id="searchName" name="enumName">
			<span class="label">枚举描述:</span>
			<input type="text" class="span3" placeholder="枚举描述" id="searchDescript" name="enumDesc">
			<span class="label">字段值:</span>
			<input type="text" class="span3" placeholder="字段值" id="searchFildeValue" name="fieldValue">
		</div>
		<div style="padding-top: 20px;height: 40px;">
			<div style="float: left;">
				<button id="search" type="button" class="btn btn-info">查询</button>		
				<button id="add" type="button" class="btn btn-primary">增加</button>
				<button id="del" type="button" class="btn btn-danger">删除</button>
			</div>
			<div  style="float: right;display: none;" id="messageBoxAlertId" >
				<div class="alert" style="width:280px;" >
				    <strong id="messageBoxAlert"></strong> 
				</div>
			</div>
		</div>
		<div  style="float: right;display: none;" id="messageBoxAlertId" >
			<div class="alert alert-error" style="width:280px;" >
			    <strong id="messageBoxAlert"></strong> 
			</div>
		</div>
	</form>
	<table class="table table-bordered table-striped table-hover">
		<thead>
		<tr>
			<th><input type = "checkbox" id="mutilSelectItem"  name = "mutilSelectItem" />&nbsp;&nbsp;选择</th>
			<th>枚举名称</th>
			<th>枚举描述</th>
			<th>字段描述</th>
			<th>字段值</th>
			<th>字段注释</th>
			<th>标识</th> 
		</tr>
		</thead>
		<tbody id="enumTbody"></tbody>
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
    			<label class="control-label" for="add_name">枚举名称</label>
    			<div class="controls">
      				<input type="text" id="add_name" name="enumName" placeholder="枚举名称">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="add_description">枚举描述</label>
    			<div class="controls">
      				<input type="text" id="add_description" name="enumDesc" placeholder="枚举描述">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="add_value">字段描述</label>
    			<div class="controls">
      				<input type="text" id="add_value" name="fieldDesc" placeholder="字段描述">
    			</div>
  			</div>
			<div class="control-group">
    			<label class="control-label" for="add_value">字段值</label>
    			<div class="controls">
      				<input type="text" id="add_value" name="fieldValue" placeholder="字段值">
    			</div>
  			</div>
  			<div class="control-group">
    			<label class="control-label" for="add_value">字段注释</label>
    			<div class="controls">
      				<input type="text" id="add_value" name="fieldComment" placeholder="字段注释">
    			</div>
  			</div>
  			<div class="control-group">
    			<label class="control-label" for="add_value">标识</label>
    			<div class="controls">
      				<select class="input-large" id="add_ui_flag" name="flag">
<!-- 						<option value="1">平台</option> -->
						<option value="2">系统</option>
					</select>
    			</div>
  			</div>
    	</form>
    </div>
    
    <div id="updateDialog" title="修改全局变量">
    	<form class="form-horizontal" action="update_5" method="post" id="update_from_5">
    		<input type="hidden" id="update_id" name="id" >
    		<div class="control-group">
    			<label class="control-label" for="update_name">枚举名称</label>
    			<div class="controls">
      				<input type="text" id="update_enum_enumName" name="enumName" placeholder="枚举名称">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="update_description">枚举描述</label>
    			<div class="controls">
      				<input type="text" id="update_enum_enumDesc" name="enumDesc" placeholder="枚举描述">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="update_value">字段描述</label>
    			<div class="controls">
      				<input type="text" id="update_enum_fieldDesc" name="fieldDesc" placeholder="字段描述">
    			</div>
  			</div>
			<div class="control-group">
    			<label class="control-label" for="update_value">字段值</label>
    			<div class="controls">
      				<input type="text" id="update_enum_fieldValue" name="fieldValue" placeholder="字段值">
    			</div>
  			</div>
  			<div class="control-group">
    			<label class="control-label" for="update_value">字段注释</label>
    			<div class="controls">
      				<input type="text" id="update_enum_fieldComment" name="fieldComment" placeholder="字段注释">
    			</div>
  			</div>
  			<div class="control-group">
    			<label class="control-label" for="update_value">标识</label>
    			<div class="controls">
      				<select class="input-large" id="update_ui_flag" name="flag">
<!-- 						<option value="1">平台</option> -->
						<option value="2">系统</option>
					</select>
    			</div>
  			</div>
    	</form>
    </div>
 
	<div id="delModel" title="确定删除">
    		<div class="control-group">
    			<label class="control-label" for="update_name">确定删除全局变量信息?</label>
  			</div>
    </div> 
</body>
</html>