<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>

<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单属性维护</title>
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
<style>
    html, body{ width: 100%; height: 100%; padding: 0; margin: 0;overflow: hidden;}
   #droplist{
        display:none; 
        position: absolute; 
        width:215px;
        /* 兼容IE6,7 */
        *width:154px;
        /* 兼容IE8 */
        width:156px\0;
        height:146px;
        border:1px solid;
        overflow: auto;
        background-color: #ffffff;
        border-color:#74b9ef;
   }
    
</style>
<script type="text/javascript">
//定义全局的变量，给后台调用前台js留句柄
var ztreeObj; 

$(document).ready(function() {
//*************************下面是Ajax提交*********************
    $("#submitBtn").bind("click",function(ev){
    	$.ajax({
        	url:"<%=path %>/menu/update.htm",
        	type:"post",
        	dataType:"json",
        	data:$("#form1").serialize(),
        	success:function(result){
        		$("#messageBoxAlertId").show();
        		if(result.resultCode == "0"){
        			updateParentNodeName($("#update_menu_id").val(),$("#update_menu_mame").val());
        			$("#messageBoxAlert").html("保存成功.");
        		}else{
        			$("#messageBoxAlert").html("保存失败.");
        		}
        	},
        	error:function(err){
        		$("#messageBoxAlertId").show();
        		$("#messageBoxAlert").html("数据库连接异常，请联系管理员.");
        	}
        });
    });
    
    /*** 菜单关联模块功能树 **/
	$.fn.zTree.init($("#tree"), setting);	
	ztreeObj = $.fn.zTree.getZTreeObj("tree");
	/*** 菜单关联模块功能树 **/  
    
    //点击下拉按钮显示下拉列表
    $("#choose").click(function(){
    	showDropList();
    });

    //点击输入框显示下拉列表
    $("#position").val("").click(function(){
    	showDropList();
    });
    
});

function showDropList(){
	var cityInput = $("#position");
	var cityOffset = cityInput.offset();
	var topnum = cityOffset.top+cityInput.outerHeight();

	$("#droplist").css({left: cityOffset.left + "px",top: topnum +"px"}).show();
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
		callback: {
   			onAsyncSuccess: zTreeOnAsyncSuccess,
   			beforeClick: zTreeBeforeClick,
			onClick:zTreeOnClick
		}
	};

function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes = treeObj.getNodes();
	var id = document.getElementById("func.id").value;
	if(id != null && id != ""){
		var node = treeObj.getNodeByParam("id", id, null);
		if(node != null){
			treeObj.selectNode(node);
			$("#position").val(node.name);		
		}
	}
}

function zTreeBeforeClick(treeId, treeNode, clickFlag){
	return treeNode.nodeType == "func";
}

function zTreeOnClick(event,treeId,treeNode){
	$("#position").val(treeNode.name);
	var o = document.getElementById("func.id");
	o.value = treeNode.id;
	hideDropList();
}
//** 更新节点名称 **//
function updateParentNodeName(nodeId,nodeName){
	window.parent.updateTreeInfo(nodeId,nodeName);
}
</script>

</head>
<body>
<div class="page-header  text-center">
	<h4>菜单属性维护</h4>
</div>
<form id="form1" method="post" >
<form:hidden path="model.parentMenu.id" id="menu_model_id"/>
<table width="70%" border="0" class="grid_layout" cellspacing="0" align="center" >
	<tr>
		<td align="left">菜单ID:</td>
		<td>
			<form:input path="model.id" id="update_menu_id" cssStyle="input_text" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td align="left">菜单名称:</td>
		<td>
			<form:input path="model.name" id="update_menu_mame" cssStyle="input_text"/>
			<span class="errorImg"></span><span	class="errorMsg"></span>
		</td>
	</tr>
	<tr>
		<td align="left">菜单描述:</td>
		<td>
			<form:input path="model.description" id="description" cssStyle="input_text"/>
			<span class="errorImg"></span><span	class="errorMsg"></span>			
		</td>
	</tr>
	<tr>
		<td align="left">IconUrl:</td>
		<td>
			<form:input path="model.iconUrl" id="iconUrl" cssStyle="input_text"/>
			<span class="errorImg"></span><span	class="errorMsg"></span>			
		</td>
	</tr>
	<tr>
		<td align="left">ImageUrl:</td>
		<td>
			<form:input path="model.imageUrl" id="imageUrl" cssStyle="input_text"/>
			<span class="errorImg"></span><span	class="errorMsg"></span>			
		</td>
	</tr>		
	<tr>
		<td align="left">菜单显示顺序:</td>
		<td>
			<form:input path="model.showOrder" id="showOrder" cssStyle="input_text"/>
			<span class="errorImg"></span><span	class="errorMsg"></span>			
		</td>
	</tr>
	<tr>
			<td align="left">菜单打开方式:</td>
				<td> 
					<form:select path="model.openType" id="openType">
						<form:option value="1">站内</form:option>
						<form:option value="2">站外</form:option>
					</form:select>	
					<span style="color: red;font-size: 13px;">&nbsp;*</span>	
				</td>
			</tr>
	<tr>
		<td align="left">菜单是否可用:</td>
		<td>
			<form:select path="model.flag">
				<form:option value="0">可用</form:option>
				<form:option value="1">不可用</form:option>
				<form:option value="2">不可见</form:option>
			</form:select>
		</td>
	</tr>
	<tr>
		<td>关联功能:</td>
		<td>
			<div id="demo" >
                <span class="om-combo om-widget om-state-default">
                    <input type="text" id="position">
                    <form:hidden path="model.func.id"/>
                    <span id="choose" class="om-combo-trigger"></span>
                </span>
            </div>
		    <div id="droplist" style="background-color:#FFFFFF;">
		    	<div class="zTreeDemoBackground left">
					<ul id="tree" class="ztree"></ul>
				</div>
			</div>		
		</td>
	</tr>	
			
</table>
<div class="row">
	<div style=" float: left; width: 300px;" class="text-center">
		<button id="submitBtn" type="button" class="btn btn-info">保存</button>		
		<button id="reset" type="reset" class="btn btn-primary">重置</button>
	</div>
	<div  style="float: right;display: none;" id="messageBoxAlertId" >
		<div class="alert alert-error" style="width:220px;" >
			    <strong id="messageBoxAlert"></strong> 
		</div>
	</div>
</div>
</form>
</body>
</html>