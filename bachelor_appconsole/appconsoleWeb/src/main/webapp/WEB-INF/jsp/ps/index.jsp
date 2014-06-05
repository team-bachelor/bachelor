<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目结构维护</title>
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
<script type="text/javascript"><!--
//定义全局的变量，给后台调用前台js留句柄
var validator;
var validatorFunc;
var validatorFp;
var ztreeObj; 
$(document).ready(function() {
    //*** 项目树 **//
	$.fn.zTree.init($("#tree"), setting);	
	ztreeObj = $.fn.zTree.getZTreeObj("tree");
    //*** 项目树 **//  
    $("#addModul").bind("click",function(ev){
    	$("#messageBoxAlertId").hide();
    	//添加模块
    	addPmClick();
    });
	$("#delModul").bind("click",function(ev){
		$("#messageBoxAlertId").hide();
		//删除模块
		deletePmClick();
    });
	$("#addFunction").bind("click",function(ev){
		$("#messageBoxAlertId").hide();
		//添加功能
		addFuncClick();
	});
	$("#delFunction").bind("click",function(ev){
		$("#messageBoxAlertId").hide();
		//删除功能
		deleteFuncClick();
	});
	//加载删除对话框
	loadAppointDelDialog("delFunctionDialog",ztreeObj,delFunctionInfo);
	//加载删除对话框
	loadAppointDelDialog("delModelDialog",ztreeObj,delModelInfo);
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
//添加模块选项
function addPmClick(){
	var node = ztreeObj.getSelectedNodes();
	var pidType = "";
	if(!node && node.length<=0){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("请选择节点.");  
		return ;
	} 
	if(node[0].nodeType != "module" && node[0].nodeType != "project" ){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("只能在项目或者模块下添加功能，请选择项目或者模块节点."); 
		return ;
	}
	if(node[0].nodeType == "module"){
		pidType = node[0].id;
	}
	//打开添加模块页面
	contentPageRedirect("<%=path %>/ps/pm/module.htm?id=redirectPage&type=add&pidType="+pidType);
}
//删除模块选项
function deletePmClick(){
	var node = ztreeObj.getSelectedNodes();
	if(!node && node.length<=0){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("请选择节点."); 
		return ;
	}   
	if(node[0].nodeType != "module"){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("选择的节点不是模块节点.");  
		return ;
	}
	$("#delModelDialog").dialog('open');
}
//删除模块
function delModelInfo(modelNodeId){
	//调用服务端，删除菜单
    $.ajax({
	 		url:encodeURI("<%=path %>/ps/pm/delete.htm?id="+modelNodeId),
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
	 		   $("#delModelDialog").dialog("close");
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
	if(node[0].nodeType != "module"){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("只能在模块下添加功能，请选择模块节点."); 
		return;
	} 
	//显示新建业务域panel
	contentPageRedirect("<%=path %>/ps/func/all.htm?id=&type=add&pidType="+node[0].id);
}

function deleteFuncClick(){
	var selectedNode = ztreeObj.getSelectedNodes();
	if(!selectedNode && selectedNode.length<=0){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("请选择要删除的功能节点."); 
		return;
	}
	if(selectedNode[0].nodeType != "func"){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("选择的节点不是功能节点.");  
		return;
	}
	$("#delFunctionDialog").dialog('open');
}
//删除功能
function delFunctionInfo(delId){
	//调用服务端，删除菜单
    $.ajax({
 		url:encodeURI("<%=path %>/ps/func/delete.htm?id="+delId),
 		contentType:"application/json",
 		success:function(result){ 
 			$("#messageBoxAlertId").show();
 		   	if(result.resultCode == "0"){
 		   		var node = ztreeObj.getNodeByParam("id",delId,null);
 		   		//删除树中的相应位置
 		   		deletedNodeContent(node);
 		  		//刷新页面 
 		  		refreshModulePage();
 		  		$("#messageBoxAlert").html("删除成功.");   
 			}else{ 
 				$("#messageBoxAlert").html("删除失败：服务器删除出错.");   
 			}
 		    $("#delFunctionDialog").dialog('close');
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
	var nodes = ztreeObj.getNodesByParam("id", initExpendId, null);//得到父级NODE
	ztreeObj.addNodes(nodes[0],initExpendNode);
}
//刷新页面
function refreshModulePage(){
	document.getElementById('content-frame').src = "<%=path %>/ps/pp/property.htm";
}

 var setting = {
   	async: {
   			enable: true,
			url:"<%=path %>/ps/pp/all.htm"
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
		beforeDrag: beforeDrag,
		beforeDrop: beforeDrop,
		onClick:zTreeOnClick,
		onAsyncSuccess: zTreeOnAsyncSuccess
	}
};
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
		ztreeObj.expandAll(true);
		
		processTreeStatue("projectStructureTreeId",ztreeObj);
		
	 	var nodes = ztreeObj.getNodes();
		if (nodes.length>0) {
			ztreeObj.selectNode(nodes[0]);
		}
};
function zTreeOnClick(event,treeId,treeNode){
	if(treeNode.nodeType == "project"){
  		contentPageRedirect("<%=path %>/ps/pp/property.htm");
   	}
   	if(treeNode.nodeType == "func"){
   		contentPageRedirect("<%=path %>/ps/func/all.htm?id="+treeNode.id+"&type=update&pidType=");
   	}
   	if(treeNode.nodeType=="module"){
		//打开添加模块页面
		contentPageRedirect("<%=path %>/ps/pm/module.htm?id="+treeNode.id+"&type=update&pidType=");
	}
}

function beforeDrag(treeId, treeNodes) {
	 
	return true;
}
function beforeDrop(treeId, treeNodes, targetNode, moveType) {
	 if(treeNodes == null || treeNodes.length<=0){
	 	
	 	return false;
	 }
	 
	 if(treeNodes[0].nodeType == "func" && targetNode.nodeType == "project"){
	 	$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("功能节点不能加入到项目节点下."); 
	 	return false;
	 }
	 
	 if(treeNodes[0].nodeType == "module" && targetNode.nodeType == "func"){
	 	$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("模块节点不能加入到功能节点下."); 
	 	return false;
	 }
		
	 var currentUrl = "<%=path %>/ps/";
	 var data = "";
	 var url = "";  
	 if(targetNode.nodeType == "project" || targetNode.nodeType == "module"){
	 	data += "id="+treeNodes[0].id;
	 	data += "&parentModule.id=";
	 	if(targetNode.nodeType == "module"){
	 		data += "&parentModule.id="+targetNode.id;
	 	}
	 	url = "pm/createAndUpdate.htm";
	 }
	  
	 if(treeNodes[0].nodeType == "func"){
	 	data = "";
	 	data += "id="+ treeNodes[0].id; 
	 	data += "&module.id="+targetNode.id;
	 	url = "func/createAndUpdate.htm";
	 } 
	 
	 if(data!=""){
	 	udpateNodeRelation(data,(currentUrl+url));
	 }
	return true;
}
//更新节点关系
function udpateNodeRelation(nodeData,nodeUrl){
	$.ajax({
			url:nodeUrl,
			type:"post",
			dataType:"json",
			data:nodeData,
			success:function(json){
				$("#messageBoxAlertId").show();
				if(json.resultCode == "0"){ 
					$("#messageBoxAlert").html("修改成功.");
				} else {
					$("#messageBoxAlert").html("修改失败."); 
				}
			}
	});
}
//更新功能节点名称
function updateProjectNode(nodeId,nodeName){
	var node =  ztreeObj.getNodesByParam("id", nodeId, null);
	node[0].name = nodeName;
	ztreeObj.updateNode(node[0]);
}
--></script>

</head>
<body>

<div style="height: 58px;margin-top: 10px;" class="row">
	<div style="float: left;" class="span6"> 	
		<button id="addModul" type="button" class="btn btn-primary">增加模块</button>
		<button id="delModul" type="button" class="btn btn-danger">删除模块</button>
		<button id="addFunction" type="button" class="btn btn-primary">增加功能</button>
		<button id="delFunction" type="button" class="btn btn-danger">删除功能</button>
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
			<h5>项目结构</h5>
		</div>
		<div id="projectStructureTreeId" style="padding-bottom: 5px;"></div>
		<div class="zTreeDemoBackground left" style="height: 450px;overflow-y:scroll; border:1px solid #86a3c4;">
			<ul id="tree" class="ztree"></ul>
		</div>
	</div> 
	<!--Body content-->
	<div class="span6">
		<div id="demo-frame">
			<iframe id="content-frame" name="content-frame" frameborder="0" src="<%=path %>/ps/pp/property.htm" 
					 style=" border: none;width: 580px;height:800px;"></iframe>
		</div>
	</div> 
</div>
<div id="delModelDialog" title="确定删除">
	<div class="control-group">
		<label class="control-label" for="update_name">确定删除模块信息?</label>
	</div>
</div>
<div id="delFunctionDialog" title="确定删除">
		<div class="control-group">
			<label class="control-label" for="update_name">确定删除功能信息?</label>
	</div>
</div>
</body>
</html>