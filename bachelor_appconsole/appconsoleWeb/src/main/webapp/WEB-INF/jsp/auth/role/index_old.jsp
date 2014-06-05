<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
<meta charset="utf-8">
<title>系统角色维护</title>

<link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="<%=path %>/css/jquery-ui-1.10.0.custom.css" rel="stylesheet" />
<link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">
<script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
<script src="<%=path %>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script>
<script src="<%=path %>/js/bootStrapCommon.js"></script>
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
<script type="text/javascript">
		var roleSearchData;
        $(document).ready(function() {
        	roleSearchData = new Array();
        	
        	//全选
        	selectAllItem("mutilSelectItem","idCheckBox");
        	
        	$('#addRole').bind("click",function(ev){
        		
        		$("#messageBoxAlertId").hide();
        		
        		$("#addDialog").dialog("open");
        	});
        	
			$('#delRole').bind("click",function(ev){
				
				$("#messageBoxAlertId").hide();
				var roleDeleInfo = getCheckBoxValue("idCheckBox");
				if(roleDeleInfo!=null && roleDeleInfo.length>0){
					$('#delModel').dialog("open");
				} else {
					$("#messageBoxAlertId").show();
					$("#messageBoxAlert").html("请选择要删除角色.");
				}
        	});
			
			$('#reloadRole').bind("click",function(ev){
				
				$("#messageBoxAlertId").hide();
				loadRoleInfo();
        	});
			
			$("#updateRole").bind("click",function(Ev){
				$("#messageBoxAlertId").hide();
				update("update_role_from","idCheckBox","messageBoxAlertId","messageBoxAlert",evaluationUpdateWin);
			});
        	//修改信息框
        	showUpdateDialog("updateDialog","<%=path %>/auth/role/update.htm","update_role_from","messageBoxAlertId","messageBoxAlert",loadRoleInfo);
        	//添加信息框
        	showAddDialog("addDialog","<%=path %>/auth/role/update.htm","add_role_from","messageBoxAlertId","messageBoxAlert",loadRoleInfo);
        	//加载删除对话框
        	loadDelDialog("delModel","idCheckBox",deleteVariableInfo);
        	//加载全局变量信息
        	loadRoleInfo();
        });
        
      	//赋值更新页面
    	function evaluationUpdateWin(id){
    			$('#updateDialog').dialog('open');
    			var obj = searchDataItem(id,roleSearchData);
    			$('#update_id').val(obj.id);
    			$('#update_name').val(obj.name);
    			$('#update_description').val(obj.description);
    			$('#update_memo').val(obj.memo);
    	}
        
      	//删除信息
    	function deleteVariableInfo(info){
    		$.ajax({
    				url:"<%=path %>/auth/role/delete.htm?delInfo="+info,
    				type:"get",
    				dataType:"json",
    				success:function(result){
    					$("#messageBoxAlertId").show();
    					$('#delModel').dialog('close');
    					if(result.ResultCode == "0"){
    						$("#messageBoxAlert").html("删除成功.");
    						loadRoleInfo();
    					} else { 
    						$("#messageBoxAlert").html("删除失败.");
    					}
    				}
    		});
    	}
        
      	//加载全局变量信息
    	function loadRoleInfo(){
    		$.ajax({
    				url:"<%=path %>/auth/role/all.htm",
    				type:"get",
    				dataType:"json",
    				success:function(result){
    					var var_list = result.rows;
    					roleSearchData = var_list;
    					if(var_list!=null && var_list.length>0){
    						var html = "";
    						for(var i=0;i<var_list.length;i++){
    							var obj = var_list[i];
    							html+= '<tr class="data"  ondblclick="evaluationUpdateWin(\''+obj.id+'\')">';
    							html+='<td><input type="checkbox" value="'+obj.id+'" name="idCheckBox"/></td>';
    							html+='<td>'+obj.name+'</td>';
    							html+='<td>'+obj.description+'</td>';
    							html+='<td>'+obj.memo+'</td>';
    							html += "</tr>";
    						}
    						$("#varTbody").html(html);
    					} else {
    						$("#varTbody").html("<tr class=\"data\"><td colspan=\"4\"><div style=\"text-align:text;\">没有查询到数据，请重试查询条件.</div><td></tr>");
    					}				
    				}
    		});
    	}
    	
</script>


</head>
<body> 
	<div class="page-header">
		<h4>系统角色维护</h4>
	</div>
	<form class="form-inline">
		<div style="padding-top: 20px;height: 40px;">
			<div style="float: left;">
				<button id="addRole" type="button" class="btn btn-primary">增加</button>
				<button id="updateRole" type="button" class="btn btn-primary">修改</button>
				<button id="delRole" type="button" class="btn btn-danger">删除</button>
				<button id="reloadRole" type="button" class="btn btn-info">刷新</button>	
			</div>
			<div  style="float: right;display: none;" id="messageBoxAlertId" >
				<div class="alert alert-error" style="width:280px;" id="AlertClassId">
				    <strong id="messageBoxAlert"></strong> 
				</div>
			</div>
		</div>
	</form>
	<table class="table table-bordered table-striped table-hover">
		<thead>
		<tr>
			<th><input type = "checkbox" id="mutilSelectItem"  name = "mutilSelectItem" />&nbsp;&nbsp;选择</th>
			<th>角色名称</th>
			<th>角色描述</th>
			<th>备注</th>
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

	<div id="delModel" title="确定删除">
   		<div class="control-group">
   			<label class="control-label" for="update_name">确定删除角色信息?</label>
 			</div>
   </div>
   
   <div id="updateDialog" title="修改角色信息">
    	<form class="form-horizontal" action="update_5" method="post" id="update_role_from">
    		<input type="hidden" id="update_id" name="id" >
    		<div class="control-group">
    			<label class="control-label" for="update_name">角色名称</label>
    			<div class="controls">
      				<input type="text" id="update_name" name="name" placeholder="角色名称">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="update_description">角色描述</label>
    			<div class="controls">
      				<input type="text" id="update_description" name="description" placeholder="角色描述">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="update_value">备注</label>
    			<div class="controls">
      				<input type="text" id="update_memo" name="memo" placeholder="备注">
    			</div>
  			</div>

    	</form>
    </div>
    
    <div id="addDialog" title="添加角色信息">
    	<form class="form-horizontal" action="add_5" method="post" id="add_role_from">
    		<div class="control-group">
    			<label class="control-label" for="add_name">角色名称</label>
    			<div class="controls">
      				<input type="text" id="add_name" name="name" placeholder="角色名称">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="add_description">角色描述</label>
    			<div class="controls">
      				<input type="text" id="add_description" name="description" placeholder="角色描述">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="add_value">备注</label>
    			<div class="controls">
      				<input type="text" id="add_value" name="memo" placeholder="备注">
    			</div>
  			</div>

    	</form>
    </div>
</body>
</html>