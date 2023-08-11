<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
<meta charset="utf-8">
<title>系统角色维护</title>
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
//定义全局的变量，给后台调用前台js留句柄
var ztreeObj; 
$(document).ready(function() {
    //*** 项目树 **//
	$.fn.zTree.init($("#roleTree"), setting);	
	ztreeObj = $.fn.zTree.getZTreeObj("roleTree");
    //*** 项目树 **//  
    $("#addRoleGroup").bind("click",function(ev){
    	$("#messageBoxAlertId").hide();
    	//添加模块
    	addPmClick();
    });
	$("#delRoleGroup").bind("click",function(ev){
		$("#messageBoxAlertId").hide();
		//删除模块
		deletePmClick();
    });
	$("#addRole").bind("click",function(ev){
		$("#messageBoxAlertId").hide();
		//添加功能
		addFuncClick();
	});
	$("#delRole").bind("click",function(ev){
		$("#messageBoxAlertId").hide();
		//删除功能
		deleteFuncClick();
	});
	//加载删除对话框
	loadAppointDelDialog("delRoleDialog",ztreeObj,delFunctionInfo);
	//加载删除对话框
	loadAppointDelDialog("delRoleGroupDialog",ztreeObj,delModelInfo);
  }); 
//*************************下面是Ajax提交*********************
 
function addBdClick(){
	var node = $('#tree').omTree('getSelected');
	if(!node){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("请选择节点."); 
		return;
	}
	if(node.nodeType != "project" && node.nodeType != "bd"){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("请选择项目节点或者业务域节点."); 
		return;
	}
	//显示新建业务域panel
	$( "#createBdDialog").omDialog('open');
}
//添加角色组选项
function addPmClick(){
	var node = ztreeObj.getSelectedNodes();
	var pidType = "";
	if(!node && node.length<=0){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("请选择节点.");  
		return ;
	} 
	if(node[0].nodeType == "role"  ){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("只能在角色组或者项目根节点下添加功能，请选择角色组或者项目根节点."); 
		return ;
	}
	if(node[0].nodeType == "group"){
		pidType = node[0].id;
	}
	//打开添加模块页面
	contentPageRedirect("<%=path %>/auth/role/createSaveRoleGroup.htm?id=redirectPage&type=add&pid="+pidType);
}
//删除模块选项
function deletePmClick(){
	var node = ztreeObj.getSelectedNodes();
	if(!node && node.length<=0){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("请选择节点."); 
		return ;
	}   
	if(node[0].nodeType != "group"){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("选择的节点不是角色组节点.");  
		return ;
	}
	if(node[0].flag=="1"){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("该角色组设置为不可删除,如要删除请更改设置.");  
		return ;
	}
	$("#delRoleGroupDialog").dialog('open');
}
//删除模块
function delModelInfo(modelNodeId){
	//调用服务端，删除菜单
    $.ajax({
	 		url:encodeURI("<%=path %>/auth/role/delRoleGroup.htm?id="+modelNodeId),
	 		contentType:"application/json",
	 		success:function(result){ 
	 			$("#messageBoxAlertId").show();
	 		   	if(result.resultCode == "0"){
	 		   		var node = ztreeObj.getNodeByParam("id",modelNodeId,null);
	 		   		//删除树中的相应位置
	 		   		deletedNodeContent(node);
	 		  		//刷新删除页面
	 		  		refreshModulePage();
	 		  		$("#messageBoxAlert").html("删除成功."); 
	 			}else{
	 				$("#messageBoxAlert").html("删除失败：服务器删除出错.");   
	 			}
	 		   $("#delRoleGroupDialog").dialog("close");
	 		},
    		error:function(result){
    			$("#messageBoxAlertId").show();
    			$("#messageBoxAlert").html("保存失败：不能连接服务器或者服务器处理出错.");   
    		}
	 	});
}
//添加功能
function addFuncClick(){
	var node = ztreeObj.getSelectedNodes();
	if(!node && node.length<=0){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("请选择节点.");   
		return;
	}
	if(node[0].nodeType != "group"){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("只能在角色组下添加角色，请选择角色节点."); 
		return;
	} 
	//显示新建业务域panel
	contentPageRedirect("<%=path %>/auth/role/createRole.htm?id=&type=add&gid="+node[0].id);
}

function deleteFuncClick(){
	var selectedNode = ztreeObj.getSelectedNodes();
	if(!selectedNode && selectedNode.length<=0){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("请选择要删除的角色节点."); 
		return;
	}
	if(selectedNode[0].nodeType != "role"){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("选择的节点不是角色节点.");  
		return;
	}
	$("#delRoleDialog").dialog('open');
}
//删除功能
function delFunctionInfo(delId){
	//调用服务端，删除菜单
    $.ajax({
 		url:"<%=path %>/auth/role/delete.htm?id="+delId,
 		contentType:"application/json",
 		type:"get",
 		success:function(result){ 
 			$("#messageBoxAlertId").show();
 		   	if(result.ResultCode == "0"){
 		   		var node = ztreeObj.getNodeByParam("id",delId,null);
 		   		//删除树中的相应位置
 		   		deletedNodeContent(node);
 		  		//刷新页面 
 		  		refreshModulePage();
 		  		$("#messageBoxAlert").html("删除成功.");   
 			}else{ 
 				$("#messageBoxAlert").html("删除失败：服务器删除出错.");   
 			}
 		    $("#delRoleDialog").dialog('close');
 		},
   		error:function(result){
   			$("#messageBoxAlertId").show();
   			$("#messageBoxAlert").html("保存失败：不能连接服务器或者服务器处理出错.");   
   		}
 	});
}
//删除节点刷新信息
function deletedNodeContent(sNode){
	ztreeObj.removeNode(sNode);
	$("#messageBoxAlertId").show();
	$("#messageBoxAlert").html("删除成功.");   
	var nodes = ztreeObj.getNodes();
	if (nodes.length>0) {
		ztreeObj.selectNode(nodes[0]);
	}
}
//主页面跳转
function contentPageRedirect(url){
	document.getElementById("content-frame").src = url;
} 
//刷新树数据
function reloadTreeInfo(initExpendNode,initExpendId){
	if(initExpendId==""){
		var nodes = ztreeObj.getNodes();
		initExpendId = nodes[0].id;
	}
	var nodes = ztreeObj.getNodesByParam("id", initExpendId, null);//得到父级NODE
	ztreeObj.addNodes(nodes[0],initExpendNode);
}
//刷新页面
function refreshModulePage(){
	var nodes = ztreeObj.getNodes();
	if (nodes.length>0 && nodes[0].children.length>0) {
		ztreeObj.selectNode(nodes[0].children[0]);
		contentPageRedirect("<%=path %>/auth/role/createSaveRoleGroup.htm?id="+nodes[0].children[0].id+"&type=update&pid=");
	}
}

 var setting = {
   	async: {
   			enable: true,
			url:"<%=path %>/auth/role/tree.htm"
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
		onClick:zTreeOnClick,
		onAsyncSuccess: zTreeOnAsyncSuccess
	}
};
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
		ztreeObj.expandAll(true);
		
		processTreeStatue("projectStructureTreeId",ztreeObj);
		
	 	var nodes = ztreeObj.getNodes();
		if (nodes.length>0 && nodes[0].children.length>0) {
			ztreeObj.selectNode(nodes[0].children[0]);
			contentPageRedirect("<%=path %>/auth/role/createSaveRoleGroup.htm?id="+nodes[0].children[0].id+"&type=update&pid=");
		}
};
function zTreeOnClick(event,treeId,treeNode){
   	if(treeNode.nodeType == "role"){
   		contentPageRedirect("<%=path %>/auth/role/createRole.htm?id="+treeNode.id+"&type=update&gid=");
   	}
   	if(treeNode.nodeType=="group"){
		//打开添加模块页面
		contentPageRedirect("<%=path %>/auth/role/createSaveRoleGroup.htm?id="+treeNode.id+"&type=update&pid=");
	}
}
//更新功能节点名称
function updateProjectNode(nodeId,nodeName){
	var node =  ztreeObj.getNodesByParam("id", nodeId, null);
	node[0].name = nodeName;
	ztreeObj.updateNode(node[0]);
}
 
</script>
</head>
<body> 
<div style="height: 58px;margin-top: 10px;" class="row">
	<div style="float: left;" class="span6"> 	
		<button id="addRoleGroup" type="button" class="btn btn-primary">增加角色组</button>
		<button id="delRoleGroup" type="button" class="btn btn-danger">删除角色组</button>
		<button id="addRole" type="button" class="btn btn-primary">增加角色</button>
		<button id="delRole" type="button" class="btn btn-danger">删除角色</button>
	</div>
	<div  style="float: right;display: none;" id="messageBoxAlertId" >
		<div class="alert alert-error" style="width:480px;" >
		    <strong id="messageBoxAlert"></strong> 
		</div>
	</div>
</div>
<div class="row">
	<!--Sidebar content-->
	<div class="span3">
		<div class="page-header">
			<h4>角色结构</h4>
		</div>
		<div id="projectStructureTreeId" style="padding-bottom: 5px;"></div>
		<div class="zTreeDemoBackground left table-bordered" style="height: 405px;overflow-y:scroll; border:1px solid #86a3c4;width: 240px;">
			<ul id="roleTree" class="ztree"></ul>
		</div>
	</div> 
	<!--Body content-->
	<div class="span6">
		<div id="demo-frame">
			<iframe id="content-frame" name="content-frame" frameborder="0" src="" 
					 style=" border: none;width: 580px;height:400px;"></iframe>
		</div>
	</div> 
</div>
<div id="delRoleGroupDialog" title="确定删除">
	<div class="control-group">
		<label class="control-label" for="update_name">确定删除角色组信息?</label>
	</div>
</div>
<div id="delRoleDialog" title="确定删除">
		<div class="control-group">
			<label class="control-label" for="update_name">确定删除角色信息?</label>
	</div>
</div>
</body>
</html>