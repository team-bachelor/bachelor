<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>功能授权管理</title>
<!-- bootstrap -->
<link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="<%=path %>/css/jquery-ui-1.10.0.custom.css" rel="stylesheet" />
<link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">
<script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
<script src="<%=path %>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script>
<script src="<%=path %>/js/bootStrapCommon.js"></script>
<!-- bootstrap --> 
<!-- Ztree导入文件 -->t
<script type="text/javascript" src="<%=path %>/js/jquery.min.js"></script>
<link rel="stylesheet" href="<%=path %>/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.ztree.exedit-3.5.js"></script>
<!-- Ztree导入文件 -->

<script type="text/javascript"><!--

//单击表格得到ID值
var onclickRoleId = "";

var ztreeObj; 
var ztreeAuth;
var funcArray;
//初始化数组
var roleArray = new Array();
var funcArray = new Array();

var delRoleArray = new Array();
var delFuncArray = new Array();

var selectArray = new Array();

var authInfoArray;
var clickType = "0";//0没有单击加载，1单击加载
$(document).ready(function() {
	 $('#myTab a').click(function (e) {
		if(e.currentTarget.innerText=="功能模块维度"){
			$("#messageBoxAlertId").hide();
			funcArray = new Array();
	 	    delFuncArray = new Array();
		} else if(e.currentTarget.innerText=="角色维度"){
			$("#roleMessageBoxAlertId").hide();
			//初始化数组
			delRoleArray = new Array();
			roleArray = new Array();
	 		//隐藏权限
	 		$('#roleProcessId').hide();
	 		//*** 表格 **//
	 		initRoleGrid();
		    
		     //*** 项目树 **//
			$.fn.zTree.init($("#tree1"), settingRole);	
			ztreeAuth = $.fn.zTree.getZTreeObj("tree1");
		} 
	});
    //*** 项目树 **//
	$.fn.zTree.init($("#tree"), setting);	
	ztreeObj = $.fn.zTree.getZTreeObj("tree");
    //*** 项目树 **//  
 
	function authorityInfo(colValue, rowData, rowIndex){
		if(colValue.indexOf('1') != -1){//0是不可用 1是可用
			$('#authrity_all').val($('#authrity_all').val()+','+rowData.catalogId+'_add'); //设置初始值
			return '<input type="checkbox" id="'+rowData.catalogId+'_add" checked="checked" onclick="check(this)"></input>';
		}
		return '<input type="checkbox" id="'+rowData.catalogId+'_add" onclick="check(this,\'endable\')"></input>';
	}
	//显示表格
	gridShowData();
});

function gridShowData(){
	$.ajax({
		url:"<%=path %>/auth/role/all.htm",
		type:"get",
		dataType:"json",
		success:function(result){
			var var_list = result.rows;
			roleSearchData = var_list;
			if(var_list!=null && var_list.length>0){
				var html = "";
				selectArray = new Array();
				$("#isSelectRoleId").attr("disable",false);
				for(var i=0;i<var_list.length;i++){
					var obj = var_list[i];
					selectArray.push(obj.id);
					html+= '<tr class="data">';
					html+='<td>'+obj.name+'</td>';
					html+='<td>'+obj.description+'</td>';
					html+='<td>'+authorityVisable(obj)+'</td>';
					html+='<td>'+authorityEndable(obj)+'</td>';
					html += "</tr>";
				}
				$("#functionModelTbody").html(html);
			} else {
				$("#isSelectRoleId").attr("disable",true);
				$("#functionModelTbody").html("<tr class=\"data\"><td colspan=\"4\"><div style=\"text-align:text;\">没有查询到数据，请重试查询条件.</div><td></tr>");
			}				
		}
	});
}

function authorityEndable(rowData){
	 //0是不可用 1是可用
	if(initAuthVal(rowData.id,'en')){//0是不可用 1是可用
		return '<input type="checkbox" name="'+rowData.id+'_Enable" checked="checked" onclick="check(this,\'endable\',\''+rowData.id+'\')"></input>';
	}
	
	return '<input type="checkbox" name="'+rowData.id+'_Enable" onclick="check(this,\'endable\',\''+rowData.id+'\')"></input>';
}

function authorityVisable(rowData){
	if(initAuthVal(rowData.id,'vis')){//0是不可用 1是可用
		return '<input type="checkbox" name="'+rowData.id+'_Visable" checked="checked" onclick="check(this,\'visable\',\''+rowData.id+'\')"></input>';
	}
	return '<input type="checkbox" name="'+rowData.id+'_Visable" onclick="check(this,\'visable\',\''+rowData.id+'\')"></input>';
}
//初始化数值
function initAuthVal(id,type){
	var flag = false;
	if(authInfoArray!=undefined && authInfoArray!=null && authInfoArray.length>0){
		for(var i=0;i<authInfoArray.length;i++){
			var obj = authInfoArray[i];
			if(obj.role.id == id){
				if(type == "vis" && obj.visible=="1"){
					flag = true;
				}
				if(type == "en" && obj.usage=="1"){
					flag = true;
				}
				break;
			}
		}
	}
	return flag; 
}

//选中权限时
function check(obj,type,id){
	var selectNodes = ztreeObj.getSelectedNodes();
 	if(selectNodes!=null && selectNodes.length>0){
		if(selectNodes[0].nodeType == "func"){
			var visObj = document.getElementsByName((id+'_Visable'));//得到可见checkbox的对象 
			var enObj = document.getElementsByName((id+'_Enable'));//得到可用checkbox的对象 
			if(obj.checked == true){
				var pInfo = "";
				var item = searchAtArrayExistsItem(id);
				if(item!=-1){
					funcArray.splice(item,1);
				}
				if(type == "endable") {//选择endable时可见也同时被选中
 					visObj[0].checked = true;
					pInfo = (selectNodes[0].id+","+id+",1,1");
				}
				if(type == "visable"){
					pInfo = (selectNodes[0].id+","+id+",1,0");
				}
				funcArray.push(pInfo);
			} else {
				if(type == "endable") {
					visObj[0].checked = false;			
				}
				if(type == "visable" && enObj[0].checked == true){
					obj.checked = true;
				}  else {
					deleteArrayItem(type,id,selectNodes);
				}
			}
		} else {
			$("#messageBoxAlertId").show();
			$("#messageBoxAlert").html("请选择功能节点."); 
			obj.checked = false;
		}
	} else {
		if(obj.checked == true){
			$("#messageBoxAlertId").show();
			$("#messageBoxAlert").html("请选择功能节点."); 
		}
		obj.checked = false;
	}
}
//数组中的数据项有些数据
function searchAtArrayExistsItem(id){
	var tempId = -1;
	if(funcArray!=null && funcArray.length>0){
		for(var i=0;i<funcArray.length;i++){
			if(funcArray[i].indexOf(id) != -1){
				tempId = i;
			}
		}
	}
	return tempId;
}

//删除数组数据项
function deleteArrayItem(type,id,selectNodes){
	if(funcArray!=null && funcArray.length>0){
		for(var i=0;i<funcArray.length;i++){
			if(funcArray[i].indexOf(id) != -1){
				funcArray.splice(i,1);
				if(type == "visable"){
					 var visObj = document.getElementsByName((id+'_Enable'));//得到可见checkbox的对象 
					 if(visObj[0].checked == true){
					 	funcArray.push(selectNodes[0].id+","+id+",0,1");//可用，可见
					 } 
				}
				break;
			}
		}
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
 		data: {
 			simpleData: {
 				enable: true
 			}
 		}, 
		callback: {
			onClick:zTreeOnClick,
			onAsyncSuccess:onAsyncSuccessFunction
		}
 	};
 	function onAsyncSuccessFunction(){
 		ztreeObj.expandAll(true);
 		processTreeStatue("funcModelId",ztreeObj);
 	}
 	function zTreeOnClick(event, treeId, treeNode) {
 		//初始化数组
 		funcArray = new Array();
 		authInfoArray = new Array();
 		if(treeNode.nodeType == "func"){
 			showAuthInfo(treeNode.id,"");
 		} else {
 			//重新加载表格
			gridShowData();
 		}
	}
	
	//显示权限信息
	function showAuthInfo(funcid,roleid){
		$.ajax({
				url:"<%=path %>/auth/assign/authFuncList.htm?funcId="+funcid+"&roleId="+roleid,
				type:"get",
				dataType:"json",
				success:function(rs){
					authInfoArray = rs;
					delFuncArray = new Array();
					
					initRoleInsertInfo(rs,funcArray);
					clickType = "1";
					//重新加载表格
					gridShowData();
				}
		});
	}
	
	function initRoleInsertInfo(rs,tempArray){
		if(rs!=null && rs.length>0){
			for(var i=0;i<rs.length;i++){
				var obj = rs[i];
				tempArray.push(obj.func.id+","+obj.role.id+","+obj.visible+","+obj.usage);
				
				delFuncArray.push(obj.func.id+","+obj.role.id);
			}
		}
	}
	
	//保存功能模块
	function saveAuthInfo(){
		var delInfo = repeatDelArrayInfo(delFuncArray);
		if(funcArray!=null && funcArray.length>0){
			var authInfo = "";
			for(var i=0;i<funcArray.length;i++){
				authInfo += funcArray[i] + ";";
			}
			saveOrUpdateAuthInfo(authInfo,delInfo,'messageBoxAlertId','messageBoxAlert');
		}  else {
			if(delFuncArray!=null && delFuncArray.length>0){
				saveOrUpdateAuthInfo("",delInfo,'messageBoxAlertId','messageBoxAlert');
			} else {
				$("#messageBoxAlertId").show();
				$("#messageBoxAlert").html("没有可保存数据.");  
			}
		}
	}
	//保存函数
	function saveOrUpdateAuthInfo(addAuthInfo,delInfo,messageBoxId,messageBoxContentId){
		/** 赋值授权功能信息及要删除授权功能信息**/
		//$("#add_authIds").val(authInfo);
		//$("#add_deleteAuthIds").val(delInfo);
		var model = new Object();
		model.authIds = addAuthInfo;
		model.deleteAuthIds = delInfo;
		/**功能授权请求**/
		$.ajax({
				url:"<%=path %>/auth/assign/saveOrUpdate.htm",
				type:"POST",
				dataType:"json",
				data:model,				
				success:function(re){
					$("#"+messageBoxId).show();
					if(re.Msg == "0"){
						$("#"+messageBoxContentId).html("保存成功."); 
					} else {
						$("#"+messageBoxContentId).html("保存失败."); 
					}
				},
          		error:function(error){
          			$("#"+messageBoxId).show();
          			$("#"+messageBoxContentId).html("保存失败：不能连接服务器或者服务器处理出错。");
          		}
		});
	}
	//** Tab 2 （角色） **/
	var settingRole = {
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
 		check:{
 			enable: true,
			chkStyle: "checkbox",
			chkboxType:  { "Y" : "ps","N" : "ps" } 
 		},
		callback: {
			beforeCheck: zTreeBeforeCheck,
			onCheck:zTreeRoleOnCheck,
			onClick:zTreeRoleOnClick,
			onAsyncSuccess:onRoleAsyncSuccessFunction
		}
 	};
 	function onRoleAsyncSuccessFunction(){
 		ztreeAuth.expandAll(true);
 		processRoleTreeStatue("roleDimensionId");
 	}
 	//单击树项
 	function zTreeRoleOnClick(event, treeId, treeNode) {
 		if(onclickRoleId!=null && onclickRoleId!=""){
 			onClickTreeUpdateRoleInfo(treeNode);
 		} 
	}
 	//单击树对象修改权限数组值
 	function onClickTreeUpdateRoleInfo(treeNode){
 		 	var roleObj = null;
 		 	var roleEn = document.getElementsByName('roleEnableId');
			var roleVis = document.getElementsByName('roleVisibleId');
 			if(roleArray!=null && roleArray.length>0){
				for(var i=0;i<roleArray.length;i++){
					if(roleArray[i].indexOf(treeNode.id) != -1){
						roleObj = roleArray[i];
					}
				}
			}
			if(roleObj!=null){
				$('#roleProcessId').show();
				var roleArrayObj = new String(roleObj).split(",");
				roleEn[0].checked = (roleArrayObj[3]=="1"?true:false);
				roleVis[0].checked = (roleArrayObj[2]=="1"?true:false);
			} else {
				roleEn[0].checked = false;
				roleVis[0].checked = false;
			}
 	}
 	
 	//树选中之前
 	function zTreeBeforeCheck(treeId, treeNode) {
 		if(onclickRoleId!=null && onclickRoleId!=""){
 			return true;
 		} else {
 			$("#roleMessageBoxAlertId").show();
  			$("#roleMessageBoxAlert").html("请选择角色.");
		    return false;
		 }
	    return true;
	}
 	
 	//单击节点
	function zTreeRoleOnCheck(event, treeId, treeNode) {
		var checkNodes = ztreeAuth.getCheckedNodes(true);
		//初始化
		if(checkNodes!=null && checkNodes.length>0){
			$('#roleProcessId').show();
		} else {
			$('#roleProcessId').hide();
		}
		
		var roleEn = document.getElementsByName('roleEnableId');
		var roleVis = document.getElementsByName('roleVisibleId');
		if(treeNode.nodeType!="fnuc"){
			roleEn[0].checked = true;
			roleVis[0].checked = true;
		}
		//是否选中
		var pType = treeNode.checked?"add":"del";
 		//初始化数组 
 		if(treeNode.nodeType == "func"){
 			if(pType == "del"){
 				deleteRoleArrayItem(treeNode.id);
 			} else {
 				roleArray.push(treeNode.id+","+onclickRoleId+","+(roleVis[0]?"1":"0")+","+(roleEn[0]?"1":"0"));
 			}
 		} else {
 			var tempArray = [treeNode]; 
 			parentNodeRecursion(tempArray,onclickRoleId,pType); 
 		}
	}
	//递归选中的父级树对象
	function parentNodeRecursion(treeNode,roleId,type){
		for(var i=0;i<treeNode.length;i++){
			if(treeNode[i].nodeType == "func"){
				if(type=="add"){
					var roleEn = document.getElementsByName('roleEnableId');
					var roleVis = document.getElementsByName('roleVisibleId');
					var roleEnInfo = roleEn[0].checked;
					var roleVisInfo = roleVis[0].checked;
					roleArray.push(treeNode[i].id+","+roleId+","+(roleVisInfo?"1":"0")+","+(roleEnInfo?"1":"0"));
				} 
				if(type == "del"){
					deleteRoleArrayItem(treeNode[i].id);
				}
			}
			if(treeNode[i].children!=null && treeNode[i].children.length>0){
				parentNodeRecursion(treeNode[i].children,roleId,type);
			}
		}
	}
	//删除角色数组选项
	function deleteRoleArrayItem(id){
		if(roleArray!=null && roleArray.length>0){
			for(var i=0;i<roleArray.length;i++){
				if(roleArray[i].indexOf(id) != -1){
					roleArray.splice(i,1);
					break;
				}
			}
		}
	}
	
	//单击可用按钮
	function roleClick(event,type){
		var treeNodes = ztreeAuth.getSelectedNodes();
		var roleVis = document.getElementsByName('roleVisibleId');
		var roleEn = document.getElementsByName('roleEnableId');
		if(type=="en"){
			roleVis[0].checked = event.checked;
		}
		
		if(type == "vis" && roleEn[0].checked==true){
			event.checked = true;
		}
		
		if(treeNodes!=null && treeNodes.length>0){
			updateRoleArrayItem(treeNodes[0].id,type,event.checked);
		} else {
			if(roleArray!=null && roleArray.length>0){
				for(var i=0;i<roleArray.length;i++){
					var tempObj = new String(roleArray[i]).split(",");
					updateRoleArrayItem(tempObj[0],type,event.checked);
				}
			}
		}
	}
	//更新权限数组选项
	function updateRoleArrayItem(id,type,pType){
		if(roleArray!=null && roleArray.length>0){
			for(var i=0;i<roleArray.length;i++){
				if(roleArray[i].indexOf(id) != -1){
					var objArray = new String(roleArray[i]).split(",");
					if(type == "en"){
						objArray[3] = (pType==true?"1":"0");
						objArray[2] = (pType==true?"1":"0");
					}	
					if(type == "vis"){
						objArray[2] = (pType==true?"1":"0");
					}
					roleArray.splice(i,1);
					roleArray.push((objArray[0]+","+objArray[1]+","+objArray[2]+","+objArray[3]));
				}
			}
		}
	}
	//初始化表格
	function initRoleGrid(){
		$.ajax({
			url:"<%=path %>/auth/role/all.htm",
			type:"get",
			dataType:"json",
			success:function(result){
				var var_list = result.rows;
				roleSearchData = var_list;
				if(var_list!=null && var_list.length>0){
					var html = "";
					/** 设置背景颜色数组 **/
					backgroupColorArray = var_list;
					for(var i=0;i<var_list.length;i++){
						var obj = var_list[i];
						html+= '<tr class="data" onclick="onClickRoleTable(\''+obj.id+'\'),chanageBackgroupColor(\''+obj.id+'\')" id="'+obj.id+'">';
						html+='<td>'+obj.name+'</td>';
						html+='<td>'+obj.description+'</td>';
						html += "</tr>";
					}
					$("#roleModelTbody").html(html);
				} else {
					$("#roleModelTbody").html("<tr class=\"data\"><td colspan=\"2\"><div style=\"text-align:text;\">没有查询到数据，请重试查询条件.</div><td></tr>");
				}				
			}
		});
	}
	
	function onClickRoleTable(roleId){
		onclickRoleId = roleId;
		//初始化数组
		delRoleArray = new Array();
		roleArray = new Array();
		
		var nodes = ztreeAuth.getNodes();
		if(nodes!=null && nodes.length>0){
			initCheckFalse(nodes);
		}
		$('#roleProcessId').hide();
		showRoleInfo("",roleId);
	}
	
	function initCheckFalse(nodes){
		for(var i=0;i<nodes.length;i++){
			ztreeAuth.checkNode(nodes[i], false, true);
			if(nodes[i].children!=null && nodes[i].children.length>0){
				initCheckFalse(nodes[i].children);
			}
		}
	}
	//显示权限信息
	function showRoleInfo(funcid,roleid){
		$.ajax({
				url:"<%=path %>/auth/assign/authFuncList.htm?funcId="+funcid+"&roleId="+roleid,
				type:"get",
				dataType:"json",
				success:function(rs){
					//初始化选中选项
					initTreeCheckedItem(rs);
					//初始化数据
					initRoleData(rs);
				}
		});
	}
	//初始化树选项
	function initTreeCheckedItem(rs){
		var nodes = ztreeAuth.getNodes();
		if(rs!=null && rs.length>0){
			recursionRoleTree(rs,nodes);
		}
	}
	//递归权限树
	function recursionRoleTree(rs,nodes){
		for(var i=0;i<nodes.length;i++){
			if(nodes[i].nodeType == "func"){
				for(var len=0;len<rs.length;len++){
					if(nodes[i].id == rs[len].func.id){
						ztreeAuth.checkNode(nodes[i], true, true);
					}
				}
			}
			if(nodes[i].children!=null && nodes[i].children.length>0){
				recursionRoleTree(rs,nodes[i].children);
			}
		}
	}
	//初始化权限数据
	function initRoleData(rs){
		if(rs!=null && rs.length>0){
			for(var i=0;i<rs.length;i++){
				roleArray.push((rs[i].func.id+","+rs[i].role.id+","+rs[i].visible+","+rs[i].usage));
				//添加删除数据
				delRoleArray.push(rs[i].func.id+","+rs[i].role.id);
			}
		}
	}
	//保存数值
	function saveRoleInfo(){
		var delInfo = repeatDelArrayInfo(delRoleArray);
		if(roleArray!=null && roleArray.length>0){
			var roleInfo = "";
			for(var i=0;i<roleArray.length;i++){
				roleInfo += roleArray[i] + ";";
			}
			saveOrUpdateAuthInfo(roleInfo,delInfo,'roleMessageBoxAlertId','roleMessageBoxAlert');
		}  else {
			if(delRoleArray!=null && delRoleArray.length>0){
				saveOrUpdateAuthInfo("",delInfo,'roleMessageBoxAlertId','roleMessageBoxAlert');
			} else {
				$("#roleMessageBoxAlertId").show();
	  			$("#roleMessageBoxAlert").html("没有可保存数据."); 
			}
		}
	}
	
	//遍历删除数组
	function repeatDelArrayInfo(delArray){
		var delInfo = "";
		if(delArray!=null && delArray.length>0){
			for(var i=0;i<delArray.length;i++){
				delInfo += delArray[i] + ";";
			}
		}
		return delInfo;
	} 
	var backgroupColorArray = new Array();
	/** 单击TR时底色变色 **/
	function chanageBackgroupColor(disId){
		processColor();
		$("#"+disId).addClass("clickSelectColor");
	}
	/** 设置颜色 **/
	function processColor(){
		for(index in backgroupColorArray){
			$("#"+backgroupColorArray[index].id).removeClass("clickSelectColor");	
		}
	}
	
	/** 全选功能权限 **/
	function selectFuncRoles(ev){
		var selectNodes = ztreeObj.getSelectedNodes();
	 	if(selectNodes!=null && selectNodes.length>0){
			if(selectNodes[0].nodeType == "func"){
				
				funcArray = new Array();
				/**全选 **/ 
				if(ev.currentTarget.checked){
					
					changeSelectStatus(true,"all",selectNodes);
				} else {//反选
					
					changeSelectStatus(false,"unall",selectNodes);
				}
				
			} else {
				$("#messageBoxAlertId").show();
				$("#messageBoxAlert").html("请选择功能节点."); 
				return ;
			}
	 	} else {
			$("#messageBoxAlertId").show();
			$("#messageBoxAlert").html("请选择功能节点."); 
			return ;
		}
		
	}
	/** 改变选择状态 **/
	function changeSelectStatus(flag,type,selectNodes){
		for(index in selectArray){
			$("input[name='"+selectArray[index]+"_Visable']").attr("checked",flag);
			$("input[name='"+selectArray[index]+"_Enable']").attr("checked",flag);
			if(type=="all"){
				var pInfo = (selectNodes[0].id+","+selectArray[index]+",1,1");
				funcArray.push(pInfo);
			}
		}
	}
	
	/**
	 * 操作树状态，展开，关闭
	 */
	function processRoleTreeStatue(showDivId){
		var html = '<div class="btn-group">';
		html += '<li class="icon-minus-sign" onclick="extendsRoleTree()" style="cursor:pointer;">  </li>';
		html += '<li class="icon-plus-sign" onclick="contractRoleTree()" style="cursor:pointer;"> </li>';
		html+='<input type="text" class="input-medium search-query span2" id="searchTreeNode_Role"/>';
		html += '<li class="icon-search" onclick="searchRoleTreeNodes()" style="cursor:pointer;">   </li>';
		html += '<li class="icon-remove" onclick="clearRoleSearchTextVal()" style="cursor:pointer;">  </li>';
		html += '</div>';
		$("#"+showDivId).html(html);
	}

	/** 
	 * 搜索节点信息
	 * @returns
	 */
	function searchRoleTreeNodes(){
		var searchNodeVal = $("#searchTreeNode_Role").val();
		if(searchNodeVal==null || searchNodeVal=="" || searchNodeVal.length<=0){
			
			return ;
		}
		var nodes = ztreeAuth.getNodes();
		if(nodes==null || nodes.length<=0){
			
			return ;
		}
		var snodes = ztreeAuth.getNodesByParamFuzzy("name",searchNodeVal,null);
		if(snodes!=null && snodes.length>0){
			for(index in snodes){
				ztreeAuth.selectNode(snodes[index]);
			}
		}
	} 
	/**
	 * 清理搜索文本框中数值
	 */
	function clearRoleSearchTextVal(){
		ztreeAuth.expandAll(true);
		$("#searchTreeNode_Role").val("");
		$("#searchTreeNode_Role").focus();
	}
	/**
	 * 展开树节点
	 * @param ztreeObjction
	 */
	function extendsRoleTree(){
		ztreeAuth.expandAll(true);
	}
	/**
	 * 收起树节点
	 * @param ztreeObjction
	 */
	function contractRoleTree(){
		ztreeAuth.expandAll(false);
	}
--></script>
<style type="text/css">
	.myTable,.myTable td {   
	    border:1px solid #86a3c4;   
	    border-collapse:collapse;  
	} 
	.table tr{
		cursor: pointer;
	}
	.clickSelectColor{
		background-color:#cccccc;
	}
</style>

</head>
<body>
<ul class="nav nav-tabs" id="myTab">
	<li class="active" ><a href="#functionModelTab" data-toggle="tab" >功能模块维度</a></li>
	<li ><a href="#roleModelTab" data-toggle="tab" >角色维度</a>
	<li ><a href="#importRoleDatas" data-toggle="tab" >导入数据</a>
</ul>
<div class="tab-content">
	<div class="tab-pane active"  id="functionModelTab">
		<div style="margin-top:2px;height: 78px;">
	    	<div>
	           		<div style="padding-top: 20px;height: 40px;">
	           			<div style="float: left;">
	           				<input type="button" value="保  存" style="width: 60px;"  onclick="saveAuthInfo();"  class="btn btn-primary"/>
	           			</div>
		           		<div  style="float: right;display: none;" id="messageBoxAlertId" >
							<div class="alert alert-error" style="width:280px;" >
							    <strong id="messageBoxAlert"></strong> 
							</div>
						</div>
	           		</div>
		    </div>
	    </div>
    	<div id="funcModelId" style="width:180px;"></div>
     	<div id="center-panel" style="float: left;border:1px solid #86a3c4;width: 220px;margin-top: 2px;">
	    	<div class="zTreeDemoBackground left" style="height: 450px;overflow-y:scroll; ">
				<ul id="tree" class="ztree" ></ul>
			</div>
	   	</div>
	   	<div id="west-panel" style="margin-left: 240px;">
	    	    <table class="table table-bordered table-striped table-hover">
					<thead>
					<tr>
						<th>角色名称</th>
						<th>角色描述</th>
						<th>可见权限</th>
						<th>可用权限<input type="checkbox" class="checkbox" style="margin-left: 8px;" onclick="selectFuncRoles(event)" id="isSelectRoleId">全选权限</th>
					</tr>
					</thead>
					<tbody id="functionModelTbody"></tbody>
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
	   	<input type="hidden" id="authrity_all" />
	</div>
	<div class="tab-pane" id="roleModelTab">
		<div style="margin-top:2px;height: 78px;">
	    	<div>
            		<div style="padding-top: 20px;height: 40px;">
            			<div style="float: left;">
            				<input type="button" value="保  存" style="width: 60px;"  onclick="saveRoleInfo()"  class="btn btn-primary"/>
            			</div>
            			<div  style="float: right;display: none;" id="roleMessageBoxAlertId" >
							<div class="alert alert-error" style="width:280px;" >
							    <strong id="roleMessageBoxAlert"></strong> 
							</div>
						</div>
            		</div>
		    </div>
	    </div>
	    <div id="west-panel"  style="float: left;margin-top:2px;" class="span9">
	    	    <table class="table table-bordered table-hover" >
					<thead>
					<tr>
						<th>角色名称</th>
						<th>角色描述</th>
					</tr>
					</thead>
					<tbody id="roleModelTbody"></tbody>
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
	   	<div id="roleDimensionId" style="margin-left: 10px;padding-left: 10px;"></div>
	    <div id="center-panel" style="float: left;border:1px solid #86a3c4;width: 240px;margin-left: 10px;">
	    	<div class="zTreeDemoBackground left" style="height: 450px;overflow-y:scroll; overflow-x:none;" >
				<ul id="tree1" class="ztree"></ul>
			</div>
	   	</div>
	   	<div style="float: left;margin-left: 10px;width: 140px;font-size: 13px;">
				<table class="myTable" id="roleProcessId" style="display: none;">
						<tr>
							<td style="width: 60px;height: 22px;">可用权限</td>
							<td style="width: 50px;height: 22px;">
								<input type="checkbox"   checked="checked"  name="roleEnableId" onclick="roleClick(this,'en')">可用</input>
							</td>
						</tr>
						<tr>
							<td>可见权限</td>
							<td>
								<input type="checkbox"   checked="checked"  name="roleVisibleId" onclick="roleClick(this,'vis')">可见</input>
							</td>
						</tr>
				</table>
	   	</div>
	</div>
	<div class="tab-pane"  id="importRoleDatas">
		<form id="uploadExcelDocumentId" action="<%=path %>/upload/roleFunc.htm" method="post" onsubmit="return uploadFile();" 
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
	
	<form id="roleDimensionalityForm" >
			<input type="hidden" id="add_authIds" name="authIds"/>
			<input type="hidden" id="add_deleteAuthIds" name="deleteAuthIds"/>
	</form>
</div>
</body>
</html> 