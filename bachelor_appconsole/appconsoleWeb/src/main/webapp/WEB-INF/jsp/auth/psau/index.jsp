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
<!-- Ztree导入文件 -->
<script type="text/javascript" src="<%=path %>/js/jquery.min.js"></script>
<link rel="stylesheet" href="<%=path %>/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.ztree.exedit-3.5.js"></script>
<!-- Ztree导入文件 -->
<script type="text/javascript" src="<%=path %>/js/treeUtils.js"></script>
<script type="text/javascript"><!--
/** 功能结构 **/
var zTreeFunction; 
/** 角色结构 **/
var zTreeRole;

/** 初始化要删除的权限JSON数据 ****/
var initDelAuthJsonData = "";
/** 初始化要保存权限的JSON数据**/
var initSaveAuthJsonData = "";
/** 所有角色权限信息 **/
var allRoleFuncs = new Array();
/** 声明树工具类 **/
var treeUtilsObj;
/** 选中的角色信息 **/
var checkedRoleId = new Array();
/** 寄存所有功能权限数组 **/
var tempAllAuthFuncs = new Array();
/** 保存操作功能数组 **/
var processArray = new Array();
/** 暂时存储角色功能数组 **/
var tempSaveRoleFuncs = new Array(); 

$(document).ready(function() {
	/** 实例化树工具类 **/
	treeUtilsObj = new TreeUtils();

	 $('#myTab a').click(function (e) {
		if(e.currentTarget.innerText=="功能授权"){
			$("#roleMessageBoxAlertId").hide();
		     
		} 
	});
	
	/** 初始化授权功能信息 **/
	initAuthFunctionsInfo();
    //加载删除对话框
	//loadCommonDelDialog("saveAuthFuncDialog",saveOrUpdateAuthInfo,calcelDelAllAuthFuncs);
});
/** 全部删除时做出提示**/
function saveAllAuthFuncs(){
	
		/** 全部删除时做出提示 **/
		if((allRoleFuncs==null || allRoleFuncs.length<=0) && (tempAllAuthFuncs!=null && tempAllAuthFuncs.length>0)){
				
			$("#saveAuthFuncDialog").dialog("open");
		} else {
			
			saveOrUpdateAuthInfo();
		}
}
/**如果取消时的做法**/
function calcelDelAllAuthFuncs(){
		allRoleFuncs = tempAllAuthFuncs;
		/** 重新加载树 **/
		initZTreeInfo();
}

/** 初始化树信息 **/
function initZTreeInfo(){
	//*** 功能树 **//
	$.fn.zTree.init($("#functionTreeId"), settingFunction);	
	zTreeFunction = $.fn.zTree.getZTreeObj("functionTreeId");
    //*** 功能树 **//  
    //*** 角色树 **//
	$.fn.zTree.init($("#roleTreeId"), settingRole);	
	zTreeRole = $.fn.zTree.getZTreeObj("roleTreeId");
	//*** 角色树 **//
}

/** 初始化授权功能信息 **/
function initAuthFunctionsInfo(){
	$.ajax({
				url:"<%=path %>/auth/assign/authFuncList.htm",
				dataType:"json",
				success:function(result){
					/** 初始化树信息 **/
					if(allRoleFuncs==null || allRoleFuncs.length<=0){
		     				initZTreeInfo();
     				}
					if(result!=null && result.length>0){
						/** 初始信息 **/
						allRoleFuncs = new Array();
						for(index in result){
							allRoleFuncs.push(result[index].func.id+","+result[index].role.id+","+result[index].visible+","+result[index].usage);
						}
						tempAllAuthFuncs = allRoleFuncs;
					}
				}
	});
}

var settingRole = {
 	   	async: {
 	   			enable: true,
 				url:"<%=path %>/auth/role/tree.htm"
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
		check:{
			enable: true,
			chkStyle: "checkbox",
			chkboxType:  { "Y" : "ps","N" : "ps" } 
		},
 		data: {
 			simpleData: {
 				enable: true
 			}
 		}, 
		callback: {
			onClick:roleZtreeOnClick,
			onCheck:function(){
					/**如果勾选把全局保存ROLEID数组设置为空 **/
				   $('#roleProcessId').hide();
				  // var ptype =  treeNode.checked;
				   
			},
			onAsyncSuccess:onAsyncSuccessRole
		}
 	};
 	function onAsyncSuccessRole(){
 		/**初始化展开功能结构树**/
 		zTreeRole.expandAll(true);
 		/** 初始化功能结构树 **/
 		var troleIds = new Array();
 		//roleIds = treeUtilsObj.getTreeIds("role",roleIds,zTreeRole.getNodes());
 		/** 由于功能权限的值太多，去掉重复值 **/
 		troleIds = treeUtilsObj.getTreeIds("role",troleIds,zTreeRole.getNodes());
 		
 		var roleIds = treeUtilsObj.searchArrayByFuncIdOrRoleId(null,"0",allRoleFuncs);
 		
 		var transitRoleIds = new Array();
 		
 		for(index in troleIds){
 			for(len in roleIds){
 					if(troleIds[index] == roleIds[len]){
 							
 							transitRoleIds.push(troleIds[index]);
 							break;
 					}
 			}
 		}
 		/** 由于功能权限的值太多，去掉重复值 **/
 		
 		treeUtilsObj.checkTreeByIds(true,"role",zTreeRole,zTreeRole.getNodes(),transitRoleIds);
		/** 加入搜索组件 **/ 		
 		processRoleTreeStatue("roleStructureId");
 	} 
	/** 选中角色 **/
	function roleZtreeOnClick(event, treeId, treeNode) {
		$("#roleMessageBoxAlertId").hide();
		$("#roleProcessId").hide();
		/** 初始化暂时存储数组 **/
		tempSaveRoleFuncs = new Array();
		processArray = new Array();
		checkedRoleId = new Array();
		zTreeFunction.checkAllNodes(false);
			    
	    /** 勾选角色下面的功能节点 **/
	    if(treeNode.nodeType=="role"){
	    	var funcs = treeUtilsObj.searchArrayByFuncIdOrRoleId(treeNode.id,"1",allRoleFuncs);
    		treeUtilsObj.checkTreeByIds(true,"func",zTreeFunction,zTreeFunction.getNodes(),funcs);
    		/** 存储勾选角色及功能信息 **/
    		if(allRoleFuncs!=null && allRoleFuncs.length>0){
				for(var i=0;i<allRoleFuncs.length;i++){
					if(allRoleFuncs[i].indexOf(treeNode.id) != -1 ){
						tempSaveRoleFuncs.push(allRoleFuncs[i]);
					}
				}
			}
	    }
	    
	    /** 得到选择中的角色信息 **/
	  	var tNodes = [treeNode]; 
	  	checkedRoleId =  roleIds = treeUtilsObj.getTreeIds("role",checkedRoleId,tNodes);
	}
	 
/**功能授权请求**/
function saveOrUpdateAuthInfo(){
		var authFuncInfos = "";
		/**for(index in allRoleFuncs){
			authFuncInfos += allRoleFuncs[index] + ";";
		}**/
		for(index in processArray){
			authFuncInfos += processArray[index] + ";";
		}
		$.ajax({
				url:"<%=path %>/auth/assign/saveOrUpdate.htm",
				type:"POST",
				dataType:"json",
				data:"allRoleFuncs="+authFuncInfos,				
				success:function(re){
					$("#roleMessageBoxAlertId").show();
					if(re.Msg == "0"){
							$("#roleMessageBoxAlert").html("保存成功.");
							
							/** 初始化数组 **/
							//allRoleFuncs = new Array();
							checkedRoleId = new Array();
 							tempAllAuthFuncs = new Array(); 
 							processArray = new Array();
 							tempSaveRoleFuncs = new Array();
 							/** 初始化数组 **/
 							
							/**  重新加载数据 **/
							initAuthFunctionsInfo();
					} else {
							$("#roleMessageBoxAlert").html("保存失败."); 
					}
				},
          		error:function(error){
          			$("#roleMessageBoxAlertId").show();
          			$("#roleMessageBoxAlert").html("保存失败：不能连接服务器或者服务器处理出错。");
          		}
		});
	}
//** Tab 2 （角色） **/
var settingFunction = {
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
			beforeCheck:function(treeId, treeNode){
					var sNode = zTreeRole.getSelectedNodes();
					 if(sNode[0].nodeType== "group"){
							$("#roleMessageBoxAlertId").show();
  							$("#roleMessageBoxAlert").html("请选择角色,不能对角色组进行操作.");
							return false;
					}
					return true;
			},
			onCheck:zTreeFunctionOnCheck,
			onClick:zTreeRoleOnClick,
			onAsyncSuccess:onAsyncSuccessFunction
		}
};
 	function onAsyncSuccessFunction(){
 		zTreeFunction.expandAll(true);
 		/**添加搜索组件 **/
 		processTreeStatue("funcStructureId",zTreeFunction);
 	}
 	//单击树项
 	function zTreeRoleOnClick(event, treeId, treeNode) {
 		 	var roleObj = null;
 		 	var roleEn = document.getElementsByName('roleEnableId');
			var roleVis = document.getElementsByName('roleVisibleId');
 			if(allRoleFuncs!=null && allRoleFuncs.length>0){
				for(var i=0;i<allRoleFuncs.length;i++){
					if(allRoleFuncs[i].indexOf(treeNode.id+","+checkedRoleId[0]) != -1 ){
						roleObj = allRoleFuncs[i];
					}
				}
			}
			if(roleObj!=null){
				$('#roleProcessId').show();
				var roleArrayObj = new String(roleObj).split(",");
				roleEn[0].checked = (roleArrayObj[3]=="1"?true:false);
				roleVis[0].checked = (roleArrayObj[2]=="1"?true:false);
			} else {
				 $('#roleProcessId').hide();
				roleEn[0].checked = false;
				roleVis[0].checked = false;
			}
	}
	  
 	//单击节点
	function zTreeFunctionOnCheck(event, treeId, treeNode) {
				//初始化
				var roleEn = document.getElementsByName('roleEnableId');
				var roleVis = document.getElementsByName('roleVisibleId');
				if(treeNode.nodeType!="fnuc"){
					roleEn[0].checked = true;
					roleVis[0].checked = true;
				}
				
				/** 如果是勾选功能节点就添加信息，如果勾选掉功能节点就删除信息**/
			   
				/**是否选中**/
				var pType = treeNode.checked?"add":"del";
				var tempArray = [treeNode]; 
				var sNode = zTreeRole.getSelectedNodes();
				
				if(treeNode.nodeType == "project"){
					tempArray = new Array();
					var inputRoleFuncs = new Array();
					if(tempSaveRoleFuncs == null || tempSaveRoleFuncs.length<=0){
						inputRoleFuncs = processArray;
					} else if(processArray == null || processArray.length<=0){
						inputRoleFuncs = tempSaveRoleFuncs;
					}
					getAllFuncIds([treeNode],tempArray,inputRoleFuncs,pType);
				}
				
				/**选中的是单个功能节点**/
		 		if(treeNode.nodeType == "func"){
		 			if(pType == "del"){
		 				delFuncAtProcessArray(treeNode.id);
		 			} else {/** 选中操作 **/
		 				if(tempSaveRoleFuncs==null || tempSaveRoleFuncs.length<=0){
		 					processArray.push(treeNode.id+","+sNode[0].id+","+(roleVis[0]?"1":"0")+","+(roleEn[0]?"1":"0")+","+"add");	
		 				}else {
		 					saveProceeAuthFuncs(tempArray,sNode[0].id,pType); 
		 				} 
		 			}
		 		} else {
		 			
		 			saveProceeAuthFuncs(tempArray,sNode[0].id,pType); 
		 		}
				
		 		/**选中的是单个功能节点
		 		if(treeNode.nodeType == "func"){
		 			if(pType == "del"){
		 				delFuncs(checkedRoleId,treeNode.id);
		 			} else {
		 				allRoleFuncs.push(treeNode.id+","+checkedRoleId+","+(roleVis[0]?"1":"0")+","+(roleEn[0]?"1":"0"));
		 			}
		 		} else {//选中模块
		 			var tempArray = [treeNode]; 
		 			parentNodeRecursion(tempArray,checkedRoleId,pType); 
		 		}**/
	}
 	/** 得到所有功能ID **/
 	function getAllFuncIds(treeNodes,tempArray,inputRoleFuncs,type){
 		for(index in treeNodes){
 			if(treeNodes[index].nodeType == "func"){
 				if(type == "add"){
 					tempArray.push(treeNodes[index]);
 				} else if(type == "del"){
 					for(sindex in inputRoleFuncs){
 						var roleFuncs = inputRoleFuncs[sindex].split(",");
 						if(roleFuncs[0] == treeNodes[index].id){
 							tempArray.push(treeNodes[index]);
 						}
 					}
 				}
 			}
 			if(treeNodes[index].children!=null && treeNodes[index].children.length>0){
 				getAllFuncIds(treeNodes[index].children,tempArray,inputRoleFuncs,type);
			}
 		}
 	}
 	
 	//递归选中的父级节点数据
 	function saveProceeAuthFuncs(treeNode,roleId,type){
 		for(var i=0;i<treeNode.length;i++){
			if(treeNode[i].nodeType == "func"){
				/** 如果有相同就更新，如果没有就新增 **/
				if(type=="add"){
					
					var roleEn = document.getElementsByName('roleEnableId');
					var roleVis = document.getElementsByName('roleVisibleId');
					var roleEnInfo = roleEn[0].checked;
					var roleVisInfo = roleVis[0].checked;
					
					if(tempSaveRoleFuncs==null || tempSaveRoleFuncs.length<=0){
						
	 					processArray.push(treeNode[i].id+","+roleId+","+(roleVisInfo?"1":"0")+","+(roleEnInfo?"1":"0")+","+"add");	
	 				} else {
	 					var flag = false;
	 					/** 如果是勾选中,操作数组删除初始中的值 **/
	 					if(processArray != null && processArray.length>0){
		 					for(index in processArray){
		 						if(processArray[index].indexOf(treeNode[i].id+","+roleId)!=-1){
		 							processArray.splice(index,1);
		 							flag = true;
		 							break;
		 						}
		 					}
	 					}
	 					if(flag==false){
	 						
	 						processArray.push(treeNode[i].id+","+roleId+","+(roleVisInfo?"1":"0")+","+(roleEnInfo?"1":"0")+","+"add");	
	 					}
	 				}
					
					//allRoleFuncs.push(treeNode[i].id+","+roleId+","+(roleVisInfo?"1":"0")+","+(roleEnInfo?"1":"0"));
				} 
				if(type == "del"){
					
					delFuncAtProcessArray(treeNode[i].id);
					//delFuncs(roleId,treeNode[i].id);
				}
			}
			if(treeNode[i].children!=null && treeNode[i].children.length>0){
				saveProceeAuthFuncs(treeNode[i].children,roleId,type);
			}
		}
 	}
 	/** 删除功能ID在操作数组中 **/
 	function delFuncAtProcessArray(funcId){
 			var sNode = zTreeRole.getSelectedNodes();
			if(tempSaveRoleFuncs==null || tempSaveRoleFuncs.length<=0){
				for(index in processArray){
					if(processArray[index].indexOf(funcId+","+sNode[0].id)!=-1){
						processArray.splice(index,1);
						break;
					}
				}
			} else {
				var flag = false;
				var tempProcessObj = null;
				/** 如果在临时数组中存在时就把type改为del,如果不存在时就删除操作数组中的值 **/
				for(rIndex in tempSaveRoleFuncs){
					if(tempSaveRoleFuncs[rIndex].indexOf(funcId+","+sNode[0].id)!=-1){
						flag = true;
						tempProcessObj = tempSaveRoleFuncs[rIndex];
						break;
					}
				}
				if(processArray!=null && processArray.length>0){
					var dFlag = false;
					for(index in processArray){
						if(processArray[index].indexOf(funcId+","+sNode[0].id)!=-1){
							var tempProcessObj = processArray[index];
							processArray.splice(index,1);
							if(flag){
		 						var tempObjs = tempProcessObj.split(",");
		 						tempObjs[4] = "del";
		 						processArray.push(tempObjs[0]+","+tempObjs[1]+","+tempObjs[2]+","+tempObjs[3]+","+tempObjs[4]);
		 					}	
							dFlag = true;
							break;
						}
					}
					if(dFlag == false && tempProcessObj!=null){
						processArray.push(tempProcessObj+",del");
					}
				}else {
					if(tempProcessObj!=null){
						processArray.push(tempProcessObj+",del");
					}
				}
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
					allRoleFuncs.push(treeNode[i].id+","+roleId+","+(roleVisInfo?"1":"0")+","+(roleEnInfo?"1":"0"));
				} 
				if(type == "del"){
					delFuncs(roleId,treeNode[i].id);
				}
			}
			if(treeNode[i].children!=null && treeNode[i].children.length>0){
				parentNodeRecursion(treeNode[i].children,roleId,type);
			}
		}
	}
	
	/**
		*	删除功能节点数据checkedRoleId[0] allRoleFuncs
		*/
	function delFuncs(roleFuncs,funcRoles){
			/** 根据角色ID，查询到所有角色功能权限 **/
			var droleIds = allRoleFuncs;
			for(index in allRoleFuncs){
					if(allRoleFuncs[index].indexOf(funcRoles+","+roleFuncs)!=-1){
							
							droleIds.splice(index,1);
							/**  一次只删除一条功能权限信息 **/
							break;
					}
			}
			/** 重新赋值 **/
			allRoleFuncs = droleIds;
	}
	
	//单击可用按钮
	function roleClick(event,type){
		var treeNodes = zTreeFunction.getSelectedNodes();
		/** 如果没有选择功能节点就返回 **/
		if(treeNodes==null && treeNodes<=0){
		
			return ;
		}		
		
		var roleVis = document.getElementsByName('roleVisibleId');
		var roleEn = document.getElementsByName('roleEnableId');
		if(type=="en"){
			roleVis[0].checked = event.checked;
		}
		
		if(type == "vis" && roleEn[0].checked==true){
			event.checked = true;
		}
		
		/** 单个给功能节点授权 **/
		if(treeNodes!=null && treeNodes.length>0 && treeNodes[0].nodeType=="func"){
			updateRoleArrayItem(treeNodes[0].id,type,event.checked);
		} 
	}
	//更新权限数组选项
	function updateRoleArrayItem(id,type,pType){
		if(allRoleFuncs!=null && allRoleFuncs.length>0){
			for(index in allRoleFuncs){
				if(allRoleFuncs[index].indexOf(id+","+checkedRoleId[0]) !=-1){
					var objArray = new String(allRoleFuncs[index]).split(",");
					if(type == "en"){
						objArray[3] = (pType==true?"1":"0");
						objArray[2] = (pType==true?"1":"0");
					}	
					if(type == "vis"){
						objArray[2] = (pType==true?"1":"0");
					}
					allRoleFuncs.splice(index,1);
					allRoleFuncs.push((objArray[0]+","+objArray[1]+","+objArray[2]+","+objArray[3]));
					/** 修改之后跳出循环 **/
					break;
				}
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
		var nodes = zTreeRole.getNodes();
		if(nodes==null || nodes.length<=0){
			
			return ;
		}
		var snodes = zTreeRole.getNodesByParamFuzzy("name",searchNodeVal,null);
		if(snodes!=null && snodes.length>0){
			for(index in snodes){
				zTreeRole.selectNode(snodes[index]);
			}
		}
	} 
	/**
	 * 清理搜索文本框中数值
	 */
	function clearRoleSearchTextVal(){
		zTreeRole.expandAll(true);
		$("#searchTreeNode_Role").val("");
		$("#searchTreeNode_Role").focus();
	}
	/**
	 * 展开树节点
	 * @param ztreeObjction
	 */
	function extendsRoleTree(){
		zTreeRole.expandAll(true);
	}
	/**
	 * 收起树节点
	 * @param ztreeObjction
	 */
	function contractRoleTree(){
		zTreeRole.expandAll(false);
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
	<li class="active" ><a href="#roleModelTab" data-toggle="tab" >功能授权</a>
	<li ><a href="#importRoleDatas" data-toggle="tab" >导入数据</a>
</ul>
<div class="tab-content ">
	<div class="tab-pane active" id="roleModelTab">
		<div style="margin-top:2px;height: 78px;">
	    	<div>
            		<div style="padding-top: 20px;height: 40px;">
            			<div style="float: left;">
            				<input type="button" value="保  存" style="width: 60px;"  onclick="saveAllAuthFuncs()"  class="btn btn-primary"/>
            			</div>
            			<div  style="float: right;display: none;" id="roleMessageBoxAlertId" >
							<div class="alert alert-error" style="width:280px;" >
							    <strong id="roleMessageBoxAlert"></strong> 
							</div>
						</div>
            		</div>
		    </div>
	    </div>
	    <div style="float: left;padding-left: 20px;">
	    		<div >
						<h4>角色结构</h4>
				</div>
				<div id="roleStructureId" style="width:180px;"></div>
		    	<div class="zTreeDemoBackground left table-bordered" style="height: 550px;overflow-y:scroll; overflow-x:none;border:1px solid #86a3c4;width: 380px;" >
					<ul id="roleTreeId" class="ztree"></ul>
				</div>
	    </div>
	    <div style="padding-left: 420px;">
	    		<div >
						<h4>功能结构</h4>
				</div>
				<div id="funcStructureId" style="width:180px;"></div>
		    	<div class="zTreeDemoBackground left table-bordered" style="float: left;height: 550px;overflow-y:scroll; overflow-x:none;border:1px solid #86a3c4;width: 380px;" >
					<ul id="functionTreeId" class="ztree"></ul>
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
</div>
<!-- <div id="saveAuthFuncDialog" title="确定删除">
	<div class="control-group">
		<label class="control-label" for="update_name">确定删除所有功能权限信息?</label>
	</div>
</div> -->
</body>
</html> 