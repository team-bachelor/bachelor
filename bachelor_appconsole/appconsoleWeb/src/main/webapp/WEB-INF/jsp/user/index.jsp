<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>
<%@ taglib uri="ufp"  prefix="ufp"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员信息维护</title>
<!-- bootstrap -->
<link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="<%=path %>/css/jquery-ui-1.10.0.custom.css" rel="stylesheet" />
<link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">
<script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
<script src="<%=path %>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script>
<script src="<%=path %>/js/bootStrapCommon.js"></script>
<!-- bootstrap -->    
<!-- Ztree导入文件 -->
<link rel="stylesheet" href="<%=path %>/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.ztree.exedit-3.5.js"></script>
<!-- Ztree导入文件 -->
<script type="text/javascript" src="<%=path %>/js/page.js"></script>
<style>
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
    
    .input_text {
	    border: 1px solid #6D869E;
	    height: 17px;
	    vertical-align: middle;
	    width: 50%;
	} 
    #droplist{
        display:none; 
        position: absolute; 
        width:240px;
        /* 兼容IE6,7 */
        *width:154px;
        /* 兼容IE8 */
        width:240px\0;
        height:180px;
        border:1px solid;
        overflow: auto;
        background-color: #e3e9ef;
     } 
	 .overlay {
		  display:none;
		  position:absolute;
		 top:0%;
		  left:0%;
		 <!-- width:100%;
		  height:100%;-->
		  z-index:1001;
		  background-color:#aaaaaa;
		  filter:alpha(opacity=90);
	 }
		
		     
</style>
<script type="text/javascript">
		var genderOptions = ["男","女"];
		var loginFlagOptions = ["可登录","不可登录"];
		var ztreeObj;
		var ztreeSearchObj;
		
		var userSearchData;
		
		var selectFlag = false;
        $(document).ready(function() {
        	userSearchData = new Array();
        	//全选
        	selectAllItem("mutilSelectItem","idCheckBox");
			//新增信息
            $('#add').click(function(){
            	processType = false;
            	$("#add_from_5")[0].reset();
            	$('#addDialog').dialog("open");
            });
            //查询信息
            $('#search').click(function(){
            	var para = "id="+$("#searchName").val()+"&username="+$("#searchDescript").val()+"&ownerOrg.id="+$("#searchOrgId").val();
            	processRequestInfo("pageInfoId","<%=path %>/user/all.htm?"+para,initGrid);
            });

            $('#del').click(function(){
            	$("#messageBoxAlertId").hide();
				var userDeleInfo = getCheckBoxValue("idCheckBox");
				if(userDeleInfo!=null && userDeleInfo.length>0){
					$('#delModel').dialog("open");
				} else {
					$("#messageBoxAlertId").show();
					$("#messageBoxAlert").html("请选择要删除角色.");
				}
            });
  
 			$('#selectOrgModel').dialog({
 				autoOpen: false,
 			    modal: false,
 			    buttons: {
 			    	 	"Ok": function () {
 			    	 		var node = ztreeObj.getSelectedNodes();
 			    	 		if(node!=null && node.length>0){
 			    	 			if(node[0].id!="0600000000"){
 			    	 				if(selectFlag){
 			    	 					$("#update_user_orgId").val(node[0].id);
	 			    	 				$("#update_user_belongOrgName").val(node[0].name);
 			    	 				} else {
	 			    	 				$("#add_user_orgId").val(node[0].id);
	 			    	 				$("#add_user_belongOrgName").val(node[0].name);
 			    	 				}
 			    	 				$("#selectOrgModel").dialog('close');
 			    	 			}
 			    	 		} 
 				        },
 				        "Cancel": function () {
 				            $(this).dialog("close");
 				        }
 			    }
 			});
	    	
	    	//*** 项目树 **//
			$.fn.zTree.init($("#myTree"), settingOrg);	
			ztreeObj = $.fn.zTree.getZTreeObj("myTree");
		    //*** 项目树 **//  
 			$.fn.zTree.init($("#searchTree"),settingSearch);
 			ztreeSearchObj = $.fn.zTree.getZTreeObj("searchTree");
            //初始化表格
            //initGrid();
            //初始化事件
            //点击下拉按钮显示下拉列表
		    $("#choose").click(function(){
		    	showDropList();
		    });
			//点击输入框显示下拉列表
		    $("#position").val("").click(function(){
		    	showDropList();
		    });
		    
		    $('#add_user_belongOrgName').bind('click',function(){
		    	selectFlag = false;
		    	$('#add_user_processType').val(processType);
            	$("#selectOrgModel").dialog('open');
        	});
		    
		    $('#update_user_belongOrgName').bind('click',function(){
		    	selectFlag = true;
            	$("#selectOrgModel").dialog('open');
        	});
			
		  	//修改信息框
        	showUpdateDialog("updateDialog","<%=path %>/user/add.htm","update_from_5","messageBoxAlertId","messageBoxAlert",initGrid);
        	//添加信息框
        	showAddDialog("addDialog","<%=path %>/user/add.htm","add_from_5","messageBoxAlertId","messageBoxAlert",initGrid);
        	//加载删除对话框
        	loadDelDialog("delModel","idCheckBox",delUserInfo);
        	
        	processRequestInfo("pageInfoId","<%=path %>/user/all.htm",initGrid);
        });
	    function showDropList(){
	    	var cityInput = $("#position");
	    	var cityOffset = cityInput.offset();
	    	var topnum = cityOffset.top+cityInput.outerHeight(); 
	    	$("#droplist").css({left: cityOffset.left + "px",top: topnum +"px"})
	    	              .show();
	    	//body绑定mousedown事件，当事件对象非下拉框、下拉按钮等下拉列表隐藏。
	    	$("body").bind("mousedown", onBodyDown);
	    }
	    
		function hideDropList() {
			$("#droplist").hide();
			$("body").unbind("mousedown", onBodyDown);
		}
		
		function onBodyDown(event) {
			if (!(event.target.id == "choose" || event.target.id == "droplist" || $(event.target).parents("#droplist").length>0)) {
				hideDropList();
			}
		}
        //新增状态
        var processType = false;
        function initGrid(users){
			userSearchData = users;
			if(users!=null && users.length>0){
				var html = "";
				for(var i=0;i<users.length;i++){
					var obj = users[i];
					html+= '<tr class="data" ondblclick="evaluationUpdateWin(\''+obj.id+'\')">';
					html+='<td><input type="checkbox" value="'+obj.id+'" name="idCheckBox"/></td>';
					html+='<td>'+obj.id+'</td>';
					html+='<td>'+obj.username+'</td>';
					html+='<td>'+(obj.memo!=null?obj.memo:"")+'</td>';
					html+='<td>'+(obj.duty!=null?obj.duty:"")+'</td>'; 
					html+='<td>'+(obj.ownerOrgName!=null?obj.ownerOrgName:"")+'</td>';
					html+='<td>'+obj.pwd+'</td>';
					html+='<td>'+(obj.identifyCode!=null?obj.identifyCode:"")+'</td>'; 
					html+='<td>'+genderOptions[obj.gender-1]+'</td>';
					html+='<td>'+loginFlagOptions[obj.loginFlag-1]+'</td>';
					html += "</tr>";
				}
				$("#userTbody").html(html);
			} else {
				$("#userTbody").html("<tr class=\"data\"><td colspan=\"10\"><div style=\"text-align:text;\">没有查询到数据，请重试查询条件.</div><td></tr>");
			}				
        }
        
      	//赋值更新页面
    	function evaluationUpdateWin(id){
    			processType = true;
    			$("#update_from_5")[0].reset();
    			$('#updateDialog').dialog('open');
    			$('#update_user_processType').val(processType);
    			var obj = searchDataItem(id,userSearchData);
    			$('#update_user_id').val(obj.id);
    			$('#update_user_username').val(obj.username);
    			$('#update_user_memo').val(obj.memo);
    			$('#update_user_duty').val(obj.duty);
    			$('#update_user_orgId').val(obj.ownerOrgId);
    			$('#update_user_belongOrgName').val(obj.ownerOrgName);
    			$('#update_user_pwd').val(obj.pwd);
    			$('#update_user_identifyCode').val(obj.identifyCode);
    			evaluationSelected("update_user_gender",obj.gender);
    			evaluationSelected("update_user_loginFlag",obj.loginFlag);
    	}
        //下拉框赋值
      	function evaluationSelected(id,initVal){
      		var commonObj = document.getElementById(id);
    		if(initVal!=null && commonObj.length>0){
    			for(var i=0;i<commonObj.length;i++){
    				if(commonObj[i].value == initVal){
    					commonObj[i].selected = true;
    					break;
    				}
    			}
				
			}
        }
         
		var settingOrg = {
		 	   	async: {
		 	   			enable: true,
		 				url:"<%=path %>/org/orglist.htm"
		 	   	},
		 		edit: {
		 			enable: true,
		 			showRemoveBtn: false,
		 			showRenameBtn: false,
		 			isCopy:true,
		 			isMove:true,
		 	        prev:true,
		 			inner:true,
		 			next:true
		 		},
		 		view:{
		 			selectedMulti:false
		 		},
		 		data: {
		 			simpleData: {
		 				enable: true
		 			}
		 		}, 
				callback: {
					onDblClick:zTreeOnDblClick,
					onAsyncSuccess: zTreeOnAsyncSuccess
				} 
		};
		var settingSearch = {
		 	   	async: {
		 	   			enable: true,
		 				url:"<%=path %>/org/showOrgs.htm"
		 	   	},
		 		edit: {
		 			enable: true,
		 			showRemoveBtn: false,
		 			showRenameBtn: false,
		 			isCopy:false,
		 			isMove:false,
		 	        prev:false,
		 			inner:false,
		 			next:false
		 		},
		 		view:{
		 			selectedMulti:false
		 		},
		 		data: {
		 			simpleData: {
		 				enable: true
		 			}
		 		}, 
				callback: {
					onDblClick:searchZTreeOnDblClick,
					onAsyncSuccess: searchZTreeOnAsyncSuccess 
				} 
		};
		//树加载完成
		function searchZTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
		    $("#searchLoadInfo").hide();
		} 
		function searchZTreeOnDblClick(event, treeId, treeNode){
	 		if(treeNode.id!="0600000000"){
			 	 $('#position').val(treeNode.name);
			 	 $('#searchOrgId').val(treeNode.id);
			 	 hideDropList();
	         }
	 	}
		//树加载完成
		function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
		    $("#loadInfo").hide();
		} 
				
	 	function zTreeOnDblClick(event, treeId, treeNode){
	 		if(treeNode.id!="0600000000"){
			 	$('#ownerOrgName').val(treeNode.name);
			 	$('#orgId').val(treeNode.id);
	            $( "#dialog").omDialog('close');
	         }
	 	}
         
        //删除信息
        function delUserInfo(info){
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
		 				$("#delModel").omDialog('close');
		            	initGrid();
	  	 		},
	      		error:function(){
	      			$("#messageBoxAlertId").show();
	      			$("#messageBoxAlert").html("数据库连接失败.");
	      		}
	  	 	});
        }
</script>
<style type="text/css">
	.testClass{
		font-size: 16px;
		color: red;
	}
</style>

</head>
<body>
	<div id="overlay" class="overlay"></div>
	
	<div class="page-header">
		<h4>人员信息维护</h4>
	</div>
	<form class="form-inline" id="userFormId">
		<div>
			<span class="label">用户ID:</span>
			<input type="text" class="span2" placeholder="用户ID" id="searchName" name="id">
			<span class="label">用户名称:</span>
			<input type="text" class="span3" placeholder="用户名称" id="searchDescript" name="username">
			<span class="label">组织机构:</span>
			<span class="om-combo om-widget om-state-default">
				<input type="hidden" id="searchOrgId" name="ownerOrgId">
                <input type="text" id="position" style="width:220px;">
                <span id="choose" class="om-combo-trigger"></span>
            </span>
            <div id="droplist"  style="z-index: 1002;">
            	<div id="searchLoadInfo" style="font-size: 12px;">正在加载...</div>
			    <ul id="searchTree" style="text-align:left;"  class="ztree"></ul>
			</div>
		</div>
		<div style="padding-top: 20px;height: 40px;">
			<div style="float: left;">
			<ufp:auth notCompany="a" notHasRoles="aa,22">
					<button id="search" type="button" class="btn btn-info">查询</button>		
					<button id="add" type="button" class="btn btn-primary">增加</button>
					<button id="del" type="button" class="btn btn-danger">删除</button>
			</ufp:auth>
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
			<th>用户ID</th>
			<th>用户名称</th>
			<th>用户描述</th>
			<th>职务名称</th>
			<th>直属机构</th>
			<th>密码</th>  
			<th>身份证</th>
			<th>性别</th>
			<th>是否可登录</th> 
		</tr>
		</thead>
		<tbody id="userTbody"></tbody>
	</table>
	<div id="pageInfoId"> </div>  
	
	<div id="selectOrgModel" title="机构选择">
		<div id="loadInfo">正在加载...</div>
   		<div class="control-group"  >
   			<ul id="myTree" class="ztree" style="height: 120px;"></ul>
		</div>
    </div> 


	<div id="addDialog" title="添加人员信息">
    	<form class="form-horizontal" action="add_5" method="post" id="add_from_5">
    		<input type="hidden" id="add_user_processType" name="processType">
    		<div class="control-group">
    			<label class="control-label" for="add_id">用户ID</label>
    			<div class="controls">
      				<input type="text" id="add_user_id" name="id" placeholder="用户ID">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="add_description">用户名称</label>
    			<div class="controls">
      				<input type="text" id="add_description" name="username" placeholder="用户名称">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="add_value">用户描述</label>
    			<div class="controls">
      				<input type="text" id="add_value" name="memo" placeholder="用户描述">
    			</div>
  			</div>
			<div class="control-group">
    			<label class="control-label" for="add_value">职务名称</label>
    			<div class="controls">
      				<input type="text" id="add_value" name="duty" placeholder="职务名称">
    			</div>
  			</div>
  			<div class="control-group">
    			<label class="control-label" for="add_value">直属机构</label>
    			<div class="controls">
    				<input type="hidden" id="add_user_orgId" name="ownerOrgId">
      				<input type="text" id="add_user_belongOrgName" readonly  name="belongOrgName" placeholder="直属机构">
    			</div>
  			</div>
  			<div class="control-group">
    			<label class="control-label" for="add_value">密码</label>
    			<div class="controls">
      				<input type="text" id="add_value" name="pwd" placeholder="密码">
    			</div>
  			</div>
  			<div class="control-group">
    			<label class="control-label" for="add_value">身份证</label>
    			<div class="controls">
      				<input type="text" id="add_value" name="identifyCode" placeholder="身份证">
    			</div>
  			</div>
  			<div class="control-group">
    			<label class="control-label" for="add_value">性别</label>
    			<div class="controls">
      				<select class="input-large" id="add_ui_flag" name="gender">
						<option value="1">男</option>
						<option value="2">女</option>
					</select>
    			</div>
  			</div>
  			<div class="control-group">
    			<label class="control-label" for="add_value">是否可登录</label>
    			<div class="controls">
      				<select class="input-large" id="add_ui_flag" name="loginFlag">
						<option value="1">可登录</option>
						<option value="2">不可登录</option>
					</select>
    			</div>
  			</div>
    	</form>
    </div>
    
    <div id="updateDialog" title="修改人员信息">
    	<form class="form-horizontal" action="update_5" method="post" id="update_from_5">
    		<input type="hidden" id="update_user_processType" name="processType">
    		<div class="control-group">
    			<label class="control-label" for="add_id">用户ID</label>
    			<div class="controls">
      				<input type="text" id="update_user_id" name="id" placeholder="用户ID">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="add_description">用户名称</label>
    			<div class="controls">
      				<input type="text" id="update_user_username" name="username" placeholder="用户名称">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="add_value">用户描述</label>
    			<div class="controls">
      				<input type="text" id="update_user_memo" name="memo" placeholder="用户描述">
    			</div>
  			</div>
			<div class="control-group">
    			<label class="control-label" for="add_value">职务名称</label>
    			<div class="controls">
      				<input type="text" id="update_user_duty" name="duty" placeholder="职务名称">
    			</div>
  			</div>
  			<div class="control-group">
    			<label class="control-label" for="add_value">直属机构</label>
    			<div class="controls">
    				<input type="hidden" id="update_user_orgId" name="ownerOrgId">
      				<input type="text" id="update_user_belongOrgName" readonly name="belongOrgName" placeholder="直属机构">
    			</div>
  			</div>
  			<div class="control-group">
    			<label class="control-label" for="add_value">密码</label>
    			<div class="controls">
      				<input type="text" id="update_user_pwd" name="pwd" placeholder="密码">
    			</div>
  			</div>
  			<div class="control-group">
    			<label class="control-label" for="add_value">身份证</label>
    			<div class="controls">
      				<input type="text" id="update_user_identifyCode" name="identifyCode" placeholder="身份证">
    			</div>
  			</div>
  			<div class="control-group">
    			<label class="control-label" for="add_value">性别</label>
    			<div class="controls">
      				<select class="input-large" id="update_user_gender" name="gender">
						<option value="1">男</option>
						<option value="2">女</option>
					</select>
    			</div>
  			</div>
  			<div class="control-group">
    			<label class="control-label" for="add_value">是否可登录</label>
    			<div class="controls">
      				<select class="input-large" id="update_user_loginFlag" name="loginFlag">
						<option value="1">可登录</option>
						<option value="2">不可登录</option>
					</select>
    			</div>
  			</div>
    	</form>
    </div>
 
	<div id="delModel" title="确定删除">
    		<div class="control-group">
    			<label class="control-label" for="update_name">确定删除人员信息信息?</label>
  			</div>
    </div> 
</body>
</html>