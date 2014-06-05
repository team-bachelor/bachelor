<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组织人员维护</title>
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
<style>
    html, body{ width: 100%; height: 100%; padding: 0; margin: 0;overflow: hidden;}
</style>
<script type="text/javascript"><!--
var ztreeObj;
var showFlag = "1";
var initId = "0600000000";
var orgUrl="<%=path %>/org/orglist.htm?id="+initId+"&flag="+showFlag;
$(document).ready(function() {
   $("#isShowCk").bind("click",function(ev){
		   /** 可以操作显示动作**/
		    $("#isShowCk").attr("disabled",true);
   			document.getElementById("content-frame").src = "";
   			if(ev.currentTarget.checked){
	   			orgUrl="<%=path %>/org/orglist.htm?id="+initId+"&flag=";
   			} else {
   				orgUrl="<%=path %>/org/orglist.htm?id="+initId+"&flag="+showFlag;
   			}
   			initZTreeData(orgUrl);
   });
   initZTreeData(orgUrl);
});

function initZTreeData(url){
	$.ajax({
				url:url,
				dataType:"json",
				type:"GET",
				success:function(result){
						/** 可以操作显示动作**/
					    $("#isShowCk").attr("disabled",false);
						//*** 项目树 **//
						$.fn.zTree.init($("#tree"), setting,result);	
						ztreeObj = $.fn.zTree.getZTreeObj("tree");
					    //*** 项目树 **//
					    //** 初始化选中节点 **//
					    searchInitTreeNode();
					    
				}
	});
}

function zTreeOnClick(event, treeId, nodedata){
	var url = encodeURI("<%=path %>/org/detail.htm?id="+nodedata.id);
	document.getElementById("content-frame").src = url;
}

var setting = {
   	async: {
   			enable: true,
			url:orgUrl,
			autoParam:["id"],
			dataFilter:filter
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
		 onClick: zTreeOnClick
	}
};
function filter(treeId, parentNode, childNodes) { 
			if (!childNodes) return null; 
			for (var i=0, l=childNodes.length; i<l; i++) { 
			childNodes[i].name = childNodes[i].name.replace('',''); 
			} 
			return childNodes; 
} 

//查询初始化树节点
function searchInitTreeNode(){
	var treeNodes = ztreeObj.getNodes();
	if(treeNodes==null || treeNodes.length<=0){ 
		 return ;
	}
	for(var i=0;i<treeNodes.length;i++){
		if(treeNodes[i].id != null && treeNodes[i].id != ""){
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
//** 刷新树数据 **//
function refreshZTreeData(){
	initZTreeData(orgUrl);
}
--></script>

</head>
<body>
<div class="row">
	<!--Sidebar content-->
	<div class="span4">
		<div class="page-header">
			<h4>组织机构结构</h4>
			<label class="checkbox  control-label">
		      	<input type="checkbox" id="isShowCk" disabled="disabled"> 显示全部
		    </label>
		</div>
		<div  style="height: 480px;overflow-y: scroll;width: 310px;border:1px solid #86a3c4;" class=" table-bordered">
			<ul id="tree" class="ztree" ></ul>
		</div>
	</div> 
	<!--Body content-->
	<div class="span9" style="margin-top: 26px;">
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