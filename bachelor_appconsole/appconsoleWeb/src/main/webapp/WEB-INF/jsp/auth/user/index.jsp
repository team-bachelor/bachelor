<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色人员维护</title>
<link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="<%=path %>/css/jquery-ui-1.10.0.custom.css" rel="stylesheet" />
<link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">
<script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
<script src="<%=path %>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script>
<script src="<%=path %>/js/bootStrapCommon.js"></script>

<!-- opermasks组件 -->
<!-- opermasks组件 -->
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
        var secondCompanyArray = new Array();
        var allRoleCompanyArray = new Array();
        
        $(document).ready(function() {
        	roleSearchData = new Array();
        	
        	//全选
        	selectAllItem("mutilSelectItem","idCheckBox");
        	
        	$('#addBtn').bind("click",function(ev){

        		$("#messageBoxAlertId").hide();
        		$('#add_role_from')[0].reset();
        		
        		evaluationSelection(allRoleCompanyArray,"addRoleSelectId",null,"role");
        		$("#addDialog").dialog("open");
        	});
        	
			$('#deleteBtn').bind("click",function(ev){
				
				$("#messageBoxAlertId").hide();
				var roleDeleInfo = getCheckBoxValue("idCheckBox");
				if(roleDeleInfo!=null && roleDeleInfo.length>0){
					$('#delModel').dialog("open");
				} else {
					$("#messageBoxAlertId").show();
					$("#messageBoxAlert").html("请选择要删除角色.");
				}
        	});
			
			$('#reloadBtn').bind("click",function(ev){
				
				$("#messageBoxAlertId").hide();
				loadRoleInfo();
        	});
			
			$('#queryBtn').bind("click",function(ev){
				//加载系统角色信息
	        	loadRoleInfo();
			});

			//查询所有二级单位 
			initSecondUnit();
			//初始化角色下拉菜单
			initRoleData();
        	//修改信息框
        	showUpdateDialog("updateDialog","<%=path %>/auth/user/add.htm","update_role_from","messageBoxAlertId","messageBoxAlert",loadRoleInfo);
        	//添加信息框
        	showAddRoleDialog("addDialog","<%=path %>/auth/user/add.htm","add_role_from","messageBoxAlertId","messageBoxAlert",loadRoleInfo);
        	//加载删除对话框
        	loadDelDialog("delModel","idCheckBox",deleteRolePresonalInfo);
        	//加载系统角色信息
        	loadRoleInfo();
        });
        
        function getAddRoleIds(uiId){
        	var addRoleIds = document.getElementById(uiId);
        	var roleIds = "";
        	for(var i=0;i<addRoleIds.length;i++){
        		if(addRoleIds.options[i].selected){
        			roleIds += addRoleIds.options[i].value+",";
        		}
        	}
        	return roleIds;
        }
        
      //显示添加框
        function showAddRoleDialog(id,url,formId,messageBoxId,messageBoxContentId,reloadFunction){
        	//添加信息框
        	$('#'+id).dialog({
        	    autoOpen: false,
        	    width: 600,
        	    buttons: {
        	        "Ok": function () { 
        	        	/**赋值传入后台值 **/
        	        	$("#add_role_ids").val(getAddRoleIds("addRoleSelectId"));
        	        	 
        	        	$.ajax({
        	    	 		url:url,
        	    	 		contentType:"application/json",
        	    	 		type:"get",
        	    	 		dataType:"json",
        	    	 		data:$("#"+formId).serialize(),
        	    	 		success:function(result){ 
        	    	 			$("#"+messageBoxId).show();
        	    	 			if(result.ResultCode == "0"){
        							$("#"+messageBoxContentId).html("添加成功.");
        	    	 			} else if(result.ResultCode == "2"){
        	    	 				$("#"+messageBoxContentId).html("不能重复添加人员角色名称.");
        	    	 			} else if(result.ResultCode == "1"){
        	    	 				$("#"+messageBoxContentId).html("没有找相关信息,添加失败.");
        	    	 			} else{
        	    	 				$("#"+messageBoxContentId).html("添加失败.");
        	    	 			}
        	    	 			$('#'+id).dialog('close');
        	    	 			//加载全局变量信息
        	    	 			reloadFunction();
        	    	 		},
        	        		error:function(){
        	        			$("#"+messageBoxContentId).html("服务器连接错误，请重试.");
        	        		}
        	    	 	});
        	        },
        	        "Cancel": function () {
        	            $(this).dialog("close");
        	        }
        	    }
        	});
        }
        
      	//赋值更新页面
    	function evaluationUpdateWin(id,roleId){
    			$('#updateDialog').dialog('open');
    			var obj = searchDataItem(id,roleSearchData);
    			$('#update_role_personal').val(obj.authUserName);
    			$('#updateRolePersonalName').val(obj.orgName);
    			$('#update_id').val(obj.id);
    			
    			//得到角色ID 
				evaluationSelection(allRoleCompanyArray,"updateRoleSelectId",roleId,"role");
    	}
        
      	//删除信息
    	function deleteRolePresonalInfo(info){
    		$.ajax({
    				url:"<%=path %>/auth/user/delete.htm?delInfo="+info,
    				type:"get",
    				dataType:"json",
    				success:function(result){
    					$("#messageBoxAlertId").show();
    					$('#delModel').dialog('close');
    					if(result.ResultCode == "0"){
    						$("#messageBoxAlert").html("删除成功.");
    					} else { 
    						$("#messageBoxAlert").html("删除失败.");
    					}
    					loadRoleInfo();
    				}
    		});
    	}
        
      	//加载全局变量信息
    	function loadRoleInfo(){
    		var roleObj = document.getElementById("roleSelectId");
      	    var companyObj = document.getElementById("companySelectId");
      	    var roleVal = getSelectVal(roleObj);
      	    var companyVal = getSelectVal(companyObj);
      	    
    		$.ajax({
    				url:"<%=path %>/auth/user/all.htm?orgId="+companyVal+"&roleId="+roleVal+"&userId="+$("#userName").val(),
    				type:"get",
    				dataType:"json",
    				success:function(result){
    					var var_list = result.rows;
    					roleSearchData = var_list;
    					if(var_list!=null && var_list.length>0){
    						var html = "";
    						for(var i=0;i<var_list.length;i++){
    							var obj = var_list[i];
    							html+= '<tr class="data"  ondblclick="evaluationUpdateWin(\''+obj.id+'\',\''+obj.roleId+'\')" style="cursor:pointer;">';
   								html+='<td><input type="checkbox" value="'+obj.id+'" name="idCheckBox"/></td>';
    							html+='<td>'+(obj.roleMemo+"/"+obj.roleName)+'</td>';
    							html+='<td>'+obj.authUserName+'</td>';
    							html+='<td>'+obj.orgName+'</td>';
    							html += "</tr>";
    						}
    						$("#varTbody").html(html);
    					} else {
    						$("#varTbody").html("<tr class=\"data\"><td colspan=\"5\"><div style=\"text-align:text;\">没有查询到数据，请重试查询条件.</div><td></tr>");
    					}				
    				}
    		});
    	}
        
    	//查询所有二级单位 
        function initSecondUnit(){
        	$.ajax({
	    	 		url:"<%=path %>/org/secondUnit.htm?id=0600000000",
	    	 		type:"get",
	    	 		dataType:"json", 
	    	 		success:function(result){ 
	    	 			 secondCompanyArray = result;
	    	 			 if(result!=null && result.length>0){
	    	 				evaluationSelection(result,"companySelectId",null,"org");
	    	 			 }
	    	 		}
    	 	});
        }
    	
    	function evaluationSelection(result,id,initVal,type){
    		var authObj = document.getElementById(id);
    		if(authObj!=null && authObj.length<=0){
			    authObj.options.remove(0,1);
			    authObj.options.add(new Option("全选",""));
	    		for (var i=0;i<result.length;i++){
			 		var obj = result[i];
			 		if(type=="role"){
			 			authObj.options.add(new Option(obj.memo+"("+obj.name+")",obj.id));
			 		} else {
			 			authObj.options.add(new Option(obj.name,obj.id));
			 		}
			 	}
    		}
    		if(authObj!=null && authObj.length>0 &&
    			initVal!=null && authObj.length>0){
	    			for(var i=0;i<authObj.length;i++){
	    				if(authObj[i].value == initVal){
	    					authObj[i].selected = true;
	    					break;
	    				}
	    			}
			}
    	}
    	
        //初始化角色下拉菜单
        function initRoleData(){
        	$.ajax({
	    	 		url:"<%=path %>/auth/role/queryRole.htm",
	    	 		type:"get",
	    	 		dataType:"json", 
	    	 		success:function(result){ 
	    	 			 allRoleCompanyArray = result; 
	    	 			 if(result!=null && result.length>0){
	    	 				evaluationSelection(result,"roleSelectId",null,"role");
	    	 			 }  
	    	 		}
    	 	});
        }
</script>

</head>
<body>
<ul class="nav nav-tabs" id="myTab">
	<li class="active" ><a href="#rolePersonalTab" data-toggle="tab" >角色人员维护</a></li>
	<li ><a href="#importRoleDatas" data-toggle="tab" >导入数据</a>
</ul>
<div class="tab-content">
	<div class="tab-pane active"  id="rolePersonalTab">
		<form class="form-inline">
			<div>
				<span class="label">角色:</span>
				<select class="input-large"  id="roleSelectId" style="width: 420px;" >
				</select>
				<span class="label">公司:</span>
				<select class="input-large"  id="companySelectId">
				</select>
				<span class="label">人员:</span>
				<input type="text" class="span2" placeholder="人员名称" id="userName">
			</div>
			<div style="padding-top: 20px;height: 40px;">
				<div style="float: left;">
					<button id="queryBtn" type="button" class="btn btn-info">查询</button>		
					<button id="addBtn" type="button" class="btn btn-primary">增加</button>
					<button id="deleteBtn" type="button" class="btn btn-danger">删除</button>
					<button id="reloadBtn" type="button" class="btn btn-info">刷新</button>
				</div>
				<div  style="float: right;display: none;" id="messageBoxAlertId" >
					<div class="alert alert-error" style="width:280px;" >
					    <strong id="messageBoxAlert"></strong> 
					</div>
				</div>
			</div>
		</form>
		
		<table class="table table-bordered table-striped table-hover">
			<thead>
			<tr>
				<th><input type = "checkbox" id="mutilSelectItem"  name = "mutilSelectItem" />&nbsp;&nbsp;选择</th>
				<th>角色</th>
				<th>人员</th>
				<th>公司部门</th>
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
	</div>
	
	<div class="tab-pane"  id="importRoleDatas">
		<form id="uploadExcelDocumentId" action="<%=path %>/upload/rolePersonal.htm" method="post" onsubmit="return uploadFile();" 
				enctype="multipart/form-data" target="hiddenIframe">
			<div class="control-group">
				<label class="label label-info" style="float: left;font-size: 20px;height: 22px;padding-top: 5px;">文件路径:</label>
				<div class="controls" style="float: left;">
			     	<input type="file" class="span4 text" id="uploadFilePath" name="uploadFilePath">
			    </div>
				<input type="submit" class="btn btn-info" value="导入数据"></input>
			</div>
			<div  style="float: left;display: none;" id="uploadFileMessageBoxAlertId" >
				<div class="alert alert-error" style="width:280px;" >
				    <strong id="ruploadFileMessageBoxAlert">asdfasdfasdfasdfasdf</strong> 
				</div>
			</div>
		</form>
		<iframe id="hiddenIframe" class="display:none;" hidden="true" width="0" height="0"></iframe>
	</div>
	
	<script type="text/javascript">
		/** 验证上传文件 **/
		function uploadFile(){
			var filePath = $("#uploadFilePath").val();
			if(filePath==null || filePath==""){
				$("#uploadFileMessageBoxAlertId").show();
	  			$("#ruploadFileMessageBoxAlert").html("请选择上传文件.");
	  			return false;
			}
			if(filePath.indexOf(".xlsx")<=-1){
				$("#uploadFileMessageBoxAlertId").show();
	  			$("#ruploadFileMessageBoxAlert").html("请选择Excel文件.");
	  			return false;
			}
			return true;
		}
		/** 返回上传的结果 **/
		function uploadReturnResult(funcResult){
			funcResult = funcResult.replace(/=/g,":");
			var result = eval("("+funcResult+")");
			$("#uploadFileMessageBoxAlertId").show();
			if(result.resultCode == "1"){
				$("#ruploadFileMessageBoxAlert").html("导入成功.");
			} else{
				$("#ruploadFileMessageBoxAlert").html("导入失败,可能导入文档数据格式不规范.");
			}
		}
	</script>
</div>


	<div id="delModel" title="确定删除">
   		<div class="control-group">
   			<label class="control-label" for="update_name">确定删除角色人员信息?</label>
 			</div>
   </div>
   
   <div id="updateDialog" title="修改角色人员信息">
    	<form class="form-horizontal" action="update_5" method="post" id="update_role_from">
    		<input type="hidden" id="update_id" name="id" >
    		<div class="control-group">
    			<label class="control-label" for="update_name">角色</label>
    			<div class="controls">
      				<select class="input-large"  id="updateRoleSelectId" name="role.id" style="width: 420px;"> </select>
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="update_description">人员</label>
    			<div class="controls">
      				<input type="text" id="update_role_personal" name="user.id" placeholder="人员">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="update_value">公司部门</label>
    			<div class="controls">
      				<input class="input-large"  id="updateRolePersonalName" readonly="readonly"> 
    			</div>
  			</div>

    	</form>
    </div>
    
    <div id="addDialog" title="添加角色人员信息">
    	<form class="form-horizontal" action="add_5" method="post" id="add_role_from">
    		<input id="add_role_ids" name="roleIds" type="hidden">
    		<div class="control-group">
    			<label class="control-label" for="add_name">角色</label>
    			<div class="controls">
      				<select class="input-large"  id="addRoleSelectId" multiple="multiple" style="width:400px;height:200px;"> </select>
      				<font size="2" color="red" style="font-size:7px;">按住Ctrl或者Shift进行多选.</font>
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="add_description">人员</label>
    			<div class="controls">
      				<input type="text" id="add_role_personal" name="user.id" placeholder="人员">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="add_value">公司部门</label>
    			<div class="controls">
      				<input class="input-large"  id="addRolePersonalName" readonly="readonly"> </input>
    			</div>
  			</div>

    	</form>
    </div>
</body>
</html>