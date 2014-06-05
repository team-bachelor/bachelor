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
<script src="<%=path %>/js/bootStrapCommon.js"></script>
<!-- bootstrap --> 

<!-- Ztree --> 
<script type="text/javascript" src="<%=path %>/js/jquery.min.js"></script>
<link rel="stylesheet" href="<%=path %>/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.ztree.exedit-3.5.js"></script>
<!-- Ztree --> 

<!-- operamasks -->
<script src="<%=path %>/js/operamasks-ui.min.js"></script>
<link href="<%=path %>/css/default/om-default.css" rel="stylesheet">
<!--  operamasks -->
<script type="text/javascript">
var funcZtreeObj;
var menuZtreeObj;
//定义全局的变量，给后台调用前台js留句柄
$(document).ready(function() {
	/** 生成菜单功能树 **/
	$.fn.zTree.init($("#funcTree"), funcSetting);	
	funcZtreeObj = $.fn.zTree.getZTreeObj("funcTree");
	/** 生成菜单功能树 **/
	$.fn.zTree.init($("#menuZtree"), menuSetting);	
	menuZtreeObj = $.fn.zTree.getZTreeObj("menuZtree");
	$("#addMenu").bind("click",function(ev){
    	$("#messageBoxAlertId").hide();
    	addMenuClick();
    });
	$("#delMenu").bind("click",function(ev){
		$("#messageBoxAlertId").hide();
		deleteClick();
    });
     
     $( "#delDialog" ).omDialog({
            autoOpen : false, 
            resizable: false,
            height:140,
            width:230,
            modal: true,
            buttons: [{
                text : "确定", 
                click : function () {
                	 var selectedNode = menuZtreeObj.getSelectedNodes();
                     delMenuInfo(selectedNode[0].id);
                }
            }, {
                text : "取消", 
                click : function () {
                   
                    $("#delDialog" ).omDialog("close");
                }
            }]
        });
     
});

function addMenuClick(){
	var selectedNode = menuZtreeObj.getSelectedNodes();
	//显示新建菜单
	var url;
	if(selectedNode[0]!=null && selectedNode[0].nodeType=="project"){
		url = encodeURI("add.htm");
	}else if(selectedNode[0]!=null && selectedNode[0].nodeType=="menu"){
		url = encodeURI("add.htm?parentId=" + selectedNode[0].id + "");
	}else{ 
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("请选择菜单节点或者项目节点."); 
		return;
	}
	document.getElementById("content-frame").src = url;
}

function deleteClick(){
	var selectedNode = menuZtreeObj.getSelectedNodes();
	if(!selectedNode || selectedNode.length<=0 || selectedNode[0].nodeType != "menu"){
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("请选择要删除的菜单节点.");  
		return;
	}
	$("#delDialog").omDialog('open');
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
					var node = menuZtreeObj.getNodeByParam("id",id,null);
					menuZtreeObj.removeNode(node);
					/**重新选择默认节点 **/
					var nodes = menuZtreeObj.getNodes();
					getInitMenuNode(nodes);
					$("#messageBoxAlert").html("删除成功.");  
	 			}else{
	 				$("#messageBoxAlert").html("删除失败：服务器删除出错.");  
	 			}
	 		    $("#delDialog" ).omDialog("close");
	 		},
    		error:function(result){
    			$("#messageBoxAlertId").show();
    			$("#messageBoxAlert").html("保存失败：不能连接服务器或者服务器处理出错.");   
    		}
	 	});
}

var funcSetting = {
 	   	async: {
 	   			enable: true,
 				url:"<%=path %>/ps/pp/all.htm"
 	   	}, 
 		view:{
 			selectedMulti:false
 		},
 		data: {
 			simpleData: {
 				enable: true
 			}
 		},
 		edit: {
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false
		},
		callback: {
				onAsyncSuccess:function(event, treeId, treeNode, msg){
						funcZtreeObj.expandAll(true);
				},
				beforeDrop:function(treeId, treeNodes, targetNode, moveType) {
					if(targetNode!=null && 
							treeNodes[0].nodeType=="menu" || treeNodes[0].nodeType=="module" || treeNodes[0].nodeType=="func"){
							addNewMenu(treeNodes,targetNode);
					}		
					/* if(treeNodes[0].nodeType!="menu" && treeNodes[0].nodeType!="module"){
						$("#messageBoxAlertId").show();
    					$("#messageBoxAlert").html("功能树根节点不能拖曳,功能或者模块节点进行拖曳.");   
					} */
					return false;
				}
		} 
};

var menuSetting = {
 	   	async: {
 	   			enable: true,
 				url:"<%=path %>/menu/tree.htm"
 	   	}, 
 		view:{
 			selectedMulti:false
 		},
 		data: {
 			simpleData: {
 				enable: true
 			}
 		},
		edit: {
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false
		}, 		
		callback: {
				onAsyncSuccess:function(event, treeId, treeNode, msg){
						menuZtreeObj.expandAll(true);
						var nodes = menuZtreeObj.getNodes();
						getInitMenuNode(nodes);
						
				},
				beforeDrag:function(){
					
					return false;
				},
				onClick:addClick
		} 
};
/** 拖曳后的入库操作 **/
function addNewMenu(nodes,targetNode ){
	$.ajax({
				url:"<%=path %>/menu/authCreate.htm",
				data:"nodeId="+nodes[0].id+"&targetId="+targetNode.id,
				dataType:"json",
				success:function(result){
   					
   					menuZtreeObj.reAsyncChildNodes(null, "refresh");	
				}
	});
}

/** 查询默认节点 **/
function getInitMenuNode(nodes){
	for(var index=0;index<nodes.length;index++){
			if(nodes[index] != null){
					if(nodes[index].nodeType == "menu"){
						
						menuZtreeObj.selectNode(nodes[index]);
						redirectMenuManagePage(nodes[index]);
						return ;
					}
					if(nodes[index].children!=null && nodes[index].children.length>0){
						
						getInitMenuNode(nodes[index].children);
					}
			}
	}
}

function addClick(){
	var selectedNode = menuZtreeObj.getSelectedNodes();
	redirectMenuManagePage(selectedNode[0]);
}

function redirectMenuManagePage(node){
	if(node.nodeType == "menu"){
     	var url = encodeURI("detail.htm?id="+node.id);
 		document.getElementById("content-frame").src = url;
 	}
}

//更新树的数据
function updateTreeInfo(nodeId,nodeName){
	if(nodeId==""){
		var nodes = menuZtreeObj.getNodes();
		nodeId = nodes[0].id;
	}
	var nodes = menuZtreeObj.getNodesByParam("id", nodeId, null);//得到NODE
	nodes[0].name = nodeName;
	menuZtreeObj.updateNode(nodes[0]);
}
//刷新树数据
function reloadTreeInfo(initExpendNode,initExpendId){
	if(initExpendId==""){
		var nodes = menuZtreeObj.getNodes();
		initExpendId = nodes[0].id;
	}
	var nodes = menuZtreeObj.getNodesByParam("id", initExpendId, null);//得到父级NODE
	menuZtreeObj.addNodes(nodes[0],initExpendNode);
}
</script>
</head>
<body> 
		<div  style="float: left;width: 320px;height: 500px;">
				<div style="float: left;" > 	
						<div class="  text-center">
								<h4>功能结构树</h4>
						</div>
				</div>
				<div class="zTreeDemoBackground left table-bordered" style="border:1px solid #86a3c4;width: 320px;
						height: 500px;overflow-y: scroll;overflow-x:none;float: left;">
							<ul id="funcTree" class="ztree" style="height: 220px;"></ul>
				</div>
		</div>
		<div  style="float: left;width: 300px;height: 500px;margin-top: 10px;">
				<div style="float: left;" > 	
					<button id="addMenu" type="button" class="btn btn-primary">增加菜单</button>
					<button id="delMenu" type="button" class="btn btn-danger">删除菜单</button>
				</div>
				<div class="zTreeDemoBackground left table-bordered" style="border:1px solid #86a3c4;width: 300px;
						height: 500px;overflow-y: scroll;overflow-x:none;">
							<ul id="menuZtree" class="ztree" style="height: 220px;"></ul>
				</div>
		</div>
		
		
		<!--Body content-->
		<div class="span6" ">
			<div id="demo-frame">
				<iframe id="content-frame" name="content-frame"  frameborder="0" 
						 style=" border: none;width: 500px;height:540px;"></iframe>
			</div>
		</div> 
		
		<div  style="float: left;display: none; " id="messageBoxAlertId" >
				<div class="alert alert-error" style="width:480px;" >
				    		<strong id="messageBoxAlert"></strong> 
				</div>
		</div>
		
		<div id="delDialog" title="确定删除" >
				<p>确定删除菜单信息?</p>
		</div>
</body>
</html>