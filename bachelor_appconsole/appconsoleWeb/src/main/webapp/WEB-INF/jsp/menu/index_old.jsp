<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单维护</title> 
<!-- bootstrap -->
<link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="<%=path %>/css/jquery-ui-1.10.0.custom.css" rel="stylesheet" />
<link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">
<script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
<script src="<%=path %>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script>
<script src="<%=path %>/js/bootStrapCommon.js"></script>
<!-- bootstrap --> 

<!-- Ztree -->
<link rel="stylesheet" href="<%=path %>/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.ztree.exedit-3.5.js"></script>
<!-- Ztree -->
<script type="text/javascript">
var ztreeObj;
//定义全局的变量，给后台调用前台js留句柄
$(document).ready(function() {
	/*** 菜单关联模块功能树 **/
	$.fn.zTree.init($("#tree"), setting);	
	ztreeObj = $.fn.zTree.getZTreeObj("tree");
	
    $("#addMenu").bind("click",function(ev){
    	$("#messageBoxAlertId").hide();
    	addClick();
    });
	$("#delMenu").bind("click",function(ev){
		$("#messageBoxAlertId").hide();
		deleteClick();
    });
	//加载删除对话框
	loadAppointDelDialog("delDialog",ztreeObj,delMenuInfo);
});
 
var setting = {
	   	async: {
	   			enable: true,
				url:"<%=path %>/menu/tree.htm"
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
		callback: {
			onClick:zTreeOnClick,
			onAsyncSuccess:function(event, treeId, treeNode, msg){
				ztreeObj.expandAll(true);
				processTreeStatue("projectStructureTreeId",ztreeObj);
				var nodes = ztreeObj.getNodes();
				if(nodes!=null && nodes.length>0){ 
					searchInitTreeNode(nodes);
				}
			},
			onDrop:function(event, treeId, treeNodes, targetNode, moveType){
				if(targetNode!=null){
					var targetId = targetNode.id;
					var nodeId = treeNodes[0].id;
					moveMenuItem(nodeId,targetId);
				} else {
					return false;
				}
				return true;
			}
		}
	};
 
/** 保存菜单项目 **/
function moveMenuItem(id,parentId){
	$.ajax({
    	url:"<%=path %>/menu/move.htm",
    	type:"post",
    	dataType:"json",
    	data:"id="+id+"&parentMenu.id="+parentId,
    	success:function(result){
    		$("#messageBoxAlertId").show();
    		if(result.resultCode == "0"){
    			$("#messageBoxAlert").html("移动成功.");
    		}else{
    			$("#messageBoxAlert").html("移动失败.");
    		}
    	},
    	error:function(err){
    		$("#messageBoxAlertId").show();
    		$("#messageBoxAlert").html("数据库连接异常，请联系管理员.");
    	}
    });
}
//查询初始化树节点
function searchInitTreeNode(treeNodes){
	for(var i=0;i<treeNodes.length;i++){
		if(treeNodes[i].nodeType != "project" && treeNodes[i].nodeType=="menu"){
			var url = encodeURI("detail.htm?id="+treeNodes[i].id);
	 		document.getElementById("content-frame").src = url;
	 		ztreeObj.selectNode(treeNodes[i]);
	 		break;
		}
		if(treeNodes[i].children!=null && treeNodes[i].children.length>0){
			searchInitTreeNode(treeNodes[i].children);
		}
	}
}
function zTreeOnClick(event,treeId,treeNode){
	if(treeNode.nodeType == "menu"){
     	var url = encodeURI("detail.htm?id="+treeNode.id);
 		document.getElementById("content-frame").src = url;
 	}
}

function addClick(){
	var selectedNode = ztreeObj.getSelectedNodes();
//  	if(!selectedNode || selectedNode.length<=0 || selectedNode[0].nodeType != "menu"){
// 		$("#messageBoxAlertId").show();
// 		$("#messageBoxAlert").html("请选择节点.");  
// 		return;
// 	}
	//显示新建菜单
	var url;
	if(selectedNode[0].nodeType=="project"){
		url = encodeURI("add.htm");
	}else if(selectedNode[0].nodeType=="menu"){
		url = encodeURI("add.htm?parentId=" + selectedNode[0].id + "");
	}else{ 
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("请选择菜单节点或者项目节点."); 
		return;
	}
	document.getElementById("content-frame").src = url;
}

function deleteClick(){
	var selectedNode = ztreeObj.getSelectedNodes();
	if(!selectedNode || selectedNode.length<=0 || selectedNode[0].nodeType != "menu"){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("请选择要删除的菜单节点.");  
		return;
	}
	$("#delDialog").dialog('open');
}

function delMenuInfo(id){
	//调用服务端，删除菜单
    $.ajax({
	 		url:encodeURI("<%=path %>/menu/delete.htm?id="+id),
	 		contentType:"application/json",
	 		success:function(result){ 
	 			$("#messageBoxAlertId").show();
	 		   	if(result.resultCode == "0"){
					//删除树中的相应位置
					var node = ztreeObj.getNodeByParam("id",id,null);
					ztreeObj.removeNode(node);
					$("#messageBoxAlert").html("删除成功.");  
					var nodes = ztreeObj.getNodes();
					if(nodes!=null && nodes.length>0){ 
						searchInitTreeNode(nodes);
					}
	 			}else{
	 				$("#messageBoxAlert").html("删除失败：服务器删除出错.");  
	 			}
	 		    $("#delDialog").dialog('close');
	 		},
    		error:function(result){
    			$("#messageBoxAlertId").show();
    			$("#messageBoxAlert").html("保存失败：不能连接服务器或者服务器处理出错.");   
    		}
	 	});
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
//更新树的数据
function updateTreeInfo(nodeId,nodeName){
	if(nodeId==""){
		var nodes = ztreeObj.getNodes();
		nodeId = nodes[0].id;
	}
	var nodes = ztreeObj.getNodesByParam("id", nodeId, null);//得到NODE
	nodes[0].name = nodeName;
	ztreeObj.updateNode(nodes[0]);
}
</script>

</head>
<body> 
<div style="height: 58px;margin-top: 10px;" class="row">
	<div style="float: left;" class="span6"> 	
		<button id="addMenu" type="button" class="btn btn-primary">增加菜单</button>
		<button id="delMenu" type="button" class="btn btn-danger">删除菜单</button>
		<a href="<%=path %>/menu/cmIndex.htm" class="btn  btn-primary ">自动生成菜单</a>
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
		<div  style="height: 450px;overflow-y:scroll; border:1px solid #86a3c4;" class="zTreeDemoBackground left" > 
			<ul id="tree" class="ztree"></ul>
		</div>
	</div> 
	<!--Body content-->
	<div class="span6">
		<div id="demo-frame">
			<iframe id="content-frame" name="content-frame" frameborder="0" 
					 style=" border: none;width: 580px;height:800px;"></iframe>
		</div>
	</div> 
</div>
<div id="delDialog" title="确定删除">
	<div class="control-group">
		<label class="control-label" for="update_name">确定删除模块信息?</label>
	</div>
</div> 
</body>
</html>