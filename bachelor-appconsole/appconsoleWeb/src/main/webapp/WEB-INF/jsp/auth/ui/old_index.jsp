<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UI元素授权</title>
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
</style>
<script type="text/javascript">
		var ztreeObj;
		var UIAuthOptions;
		var FuncAuthOptions;
		var UIElementOptions;
		//** UI选择器类型数组 **//
		var SelectorArray;		
		var FuncId="";
		var uiSearchData;
		var flows = new Array();
		var joins = new Array();
		/** 查询类型 **/
		var searchType = "0";
		/** 查询的树对象 **/
		var searchZtreeObj;
        $(document).ready(function() {
        	
        	uiSearchData = new Array();
        	//全选
        	selectAllItem("mutilSelectItem","idCheckBox");
        	
        	//*** 项目树 **//
			$.fn.zTree.init($("#tree"), setting);	
			ztreeObj = $.fn.zTree.getZTreeObj("tree");
		    //*** 项目树 **// 
		    
		    //*** 查询的树对象 **//
			$.fn.zTree.init($("#searchTree"), searchSetting);	
			searchZtreeObj = $.fn.zTree.getZTreeObj("searchTree");
		    //*** 查询的树对象 **// 

			//新增信息
            $('#add').click(function(){
            	$("#messageBoxAlertId").hide();
            	
            	var nodes = ztreeObj.getSelectedNodes();
            	if(nodes!=null && nodes.length>0){
            		if(nodes[0].nodeType == "func"){
            			
            			$("#messageBoxAlertId").hide();
                		$('#add_role_from')[0].reset();
                		
                		evaluationSelection(UIElementOptions,"add_ui_uiTypeId",null);
                		evaluationSelection(FuncAuthOptions,"add_ui_roleId",null);
                		evaluationSelection(UIAuthOptions,"add_ui_uiResourcePermission",null);
                		//** 选择器下拉框 **//
                		evaluationSelection(SelectorArray,"add_ui_uiTypeSelector",null);
                		
                		var nodes = ztreeObj.getSelectedNodes();
                		$('#add_ui_funcId').val(nodes[0].id);
                		
                		//**下拉框赋值 **//
                		drawFlowOption("add_flow_name",null);
                		var flowObj = document.getElementById("add_flow_name");
                		var flowId = flowObj[0].value.split(",")[0];
                		loadJoinData(flowId,null,'add');
                		
                		$("#addDialog").dialog("open");
            		} else {
            			$("#messageBoxAlertId").show();
    					$("#messageBoxAlert").html("请选择功能节点.");
            		}
            	} else {
            		$("#messageBoxAlertId").show();
					$("#messageBoxAlert").html("请选择模块功能节点.");
            	}
            }); 

            $('#del').click(function(){
            	$("#messageBoxAlertId").hide();
				var roleDeleInfo = getCheckBoxValue("idCheckBox");
				if(roleDeleInfo!=null && roleDeleInfo.length>0){
					$('#delModel').dialog("open");
				} else {
					$("#messageBoxAlertId").show();
					$("#messageBoxAlert").html("请选择要删除UI元素.");
				}
            });

			$("#search").click(function(ev){
				searchType = "1";
				var nodes = searchZtreeObj.getSelectedNodes();
				var funcIds = "";
				funcIds = loopSelectNodes(nodes,funcIds);
				$("#searchFuncId").val(funcIds);
				/** 根据查询条件改变表格数据 **/
            	loadAuthInfo();
			});
			
			//点击下拉按钮显示下拉列表
		    $("#choose").click(function(){
		    	showDropList();
		    });
			//点击输入框显示下拉列表
		    $("#position").val("").click(function(){
		    	showDropList();
		    });

            initUiElementData();
            
            initUiAuthData();
            //** 加载选择器数据 **//
         	initSelectorData();
          	//修改信息框
        	showUpdateDialog("updateDialog","<%=path %>/auth/ui/add.htm","update_role_from","messageBoxAlertId","messageBoxAlert",initUIRoleGrid);
        	//添加信息框
        	showAddDialog("addDialog","<%=path %>/auth/ui/add.htm","add_role_from","messageBoxAlertId","messageBoxAlert",initUIRoleGrid);
        	//加载删除对话框
        	loadDelDialog("delModel","idCheckBox",deleteUiRoleInfo);
            //初始化表格
            initUIRoleGrid();
            //**初始化流程数据*/
            initFlowData();
            /** 加载角色数据**/
            loadInitAuthInfo();
        });
        
        //加载角色数据 
		function loadInitAuthInfo(){
				   	$.ajax({
					 		url:"<%=path %>/auth/role/all.htm",
					 		type:"post",
					 		dataType:"json", 
					 		success:function(result){
					 			var roles = new Array(); 
					 			if(result.rows!=null && result.rows.length>0){
					 				for(var i=0;i<result.rows.length;i++){
					 					var obj = new Object();
					 					obj.text = result.rows[i].name;
					 					obj.value = result.rows[i].id;
					 					roles.push(obj);
					 				}
					 			}
					 			/** 初始化选项 **/
	    	 					evaluationSelection(roles,"searchAuthRole",null,"init");
				 			}
			 			});
		}
        
        /** 循环选择的NODES节点 **/
		function loopSelectNodes(nodes,funcIds){
			if(nodes!=null && nodes.length>0){
				for(var i=0;i<nodes.length;i++){
					if(nodes[i].children==null){
						funcIds += nodes[i].id+",";
					} 
					if(nodes[i].children!=null && nodes[i].children.length>0){
						funcIds = loopSelectNodes(nodes[i].children,funcIds);
					}
				}
			} 
			return funcIds;
		}
        // ** 渲染流程下拉框数据 **//
        function drawFlowOption(flowElementId,flowId){
        	var flowSelectObj = document.getElementById(flowElementId);
    		if(flowSelectObj!=null &&  flowSelectObj.length<=0){
		        	if(flows!=null && flows.length>0){
		        		flowSelectObj.options.remove(0,1);
		        		flowSelectObj.options.add(new Option("请选择",""));
		        		for(var i=0;i<flows.length;i++){
		        			var obj = flows[i];
		        			flowSelectObj.options.add(new Option((obj.name+"---"+obj.version),(obj.id+","+obj.version)));
		        		}
					}
			}
			//** 初始化选中 **//
        	if(flowSelectObj!=null && flowSelectObj.length>0 &&
        			flowId!=null && flowId.length>0){
					for(var i=0;i<flowSelectObj.length;i++){
						if(flowSelectObj[i].value == flowId){
							flowSelectObj[i].selected = true;
							break;
						}
					}
			}
        }
         // ** 渲染下拉框数据 **//
        function drawJoinOption(joinElementId,joinId){
        	var joinSelectObj = document.getElementById(joinElementId);
        	
        	if(joinSelectObj!=null && joinSelectObj.length>0){
        			for(var i=0;i<joinSelectObj.length;i++){
        				joinSelectObj.remove(0);
        				i=0;
        			}
        			joinSelectObj.remove(0);
        	}
        	
	       	if(joins!=null && joins.length>0){
	       		joinSelectObj.options.remove(0,1);
	       		joinSelectObj.options.add(new Option("请选择",""));
	       		for(var i=0;i<joins.length;i++){
	       			var obj = joins[i];
	       			joinSelectObj.options.add(new Option((obj.name),(obj.key)));
	       		}
			}
			//** 初始化选中 **//
	       	if(joinSelectObj!=null && joinSelectObj.length>0 &&
	       			 joinId!=null && joinId.length>0){
					for(var i=0;i<joinSelectObj.length;i++){
						if(joinSelectObj[i].value == joinId){
							joinSelectObj[i].selected = true;
							break;
						}
					}
			}
        }
        
        //** 中转到添加框或者更新框
        function toAddOrUpdate(type,joinId){
        	if(type=="add"){
        		drawJoinOption("add_join_name",null);
        	} else if(type=="update") {
        		drawJoinOption("update_join_name",joinId);
        	} else if(type=="search") {
        		drawJoinOption("search_join_name",joinId);
        	}
        }
        
        //**初始化节点数据**/
        function loadJoinData(pdId,joinId,type){
        	var id = pdId.split(",")[0];
        	$.ajax({
        				url:"<%=path %>/auth/ui/findJoinById.htm?id="+id,
        				type:"GET",
        				dataType:"json",
        				success:function(result){
        					joins = result;
        					//** 调用节点下拉数据 **//
        					toAddOrUpdate(type,joinId);
        				}
        	});
        }
        
         //**初始化流程数据*/
        function initFlowData(){
        	$.ajax({
        				url:"<%=path %>/auth/ui/allFlow.htm",
        				type:"GET",
        				dataType:"json",
        				success:function(result){
        					flows = result;
        					 //**下拉框赋值 **//
       						drawFlowOption("search_flow_name",null);
        				}
        	});
        }
        //** 加载选择器数据 **//
         function initSelectorData(){
        	$.ajax({
	    	 		url:"<%=path %>/gv/enum/findByEnumNameTV.htm?enumName=bchlr_AUTH_UI_SELECTOR",
	    	 		contentType:"application/json",
	    	 		type:"get",
	    	 		dataType:"json", 
	    	 		success:function(result){ 
	    	 			SelectorArray  = result;
	    	 		}
    	 	});
        }
        
        function initUiAuthData(){
        	$.ajax({
	    	 		url:"<%=path %>/gv/enum/findByEnumNameTV.htm?enumName=bchlr_AUTH_UI_PERMISSION",
	    	 		contentType:"application/json",
	    	 		type:"get",
	    	 		dataType:"json", 
	    	 		success:function(result){ 
	    	 			UIAuthOptions  = result;
	    	 			evaluationSelection(UIAuthOptions,"searchAuth",null,"init");
	    	 		}
    	 	});
        }
        
        function initUiElementData(){
        	$.ajax({
	    	 		url:"<%=path %>/gv/enum/findByEnumNameTV.htm?enumName=bchlr_AUTH_UI_TYPE",
	    	 		contentType:"application/json",
	    	 		type:"get",
	    	 		dataType:"json", 
	    	 		success:function(result){ 
	    	 			UIElementOptions = result;
	    	 		}
    	 	});
        }
        
      	//初始化表格
    	function initUIRoleGrid(){
    		/** 赋值查询类型 **/
    		$("#searchType").val(searchType);
    		$.ajax({
    			url:"<%=path %>/auth/ui/all.htm",
    			type:"get",
    			dataType:"json",
    			data:$('#uiAuthFormId').serialize(),
    			success:function(result){
    				var var_list = result.rows;
    				uiSearchData = var_list;
    				if(var_list!=null && var_list.length>0){
    					var html = "";
    					for(var i=0;i<var_list.length;i++){
    						var obj = var_list[i];
    						html+= '<tr class="data" ondblclick="evaluationUpdateWin(\''+obj.id+'\')" style="cursor:pointer;">';
    						html+='<td><input type="checkbox" value="'+obj.id+'" name="idCheckBox"/></td>';
    						html+='<td>'+obj.uiTypeName+'</td>';
    						html+='<td>'+obj.uiResourceId+'</td>';
    						html+='<td>'+obj.uiResourceDesc+'</td>';
    						html+='<td>'+isEmpty(obj.roleName,10,"...")+'</td>';
    						html+='<td>'+obj.permissionName+'</td>';
    						html+='<td>'+isEmpty(obj.flowName,15,"...")+'</td>';
    						html+='<td>'+isEmpty(obj.joinName,15,"...")+'</td>';
    						html += "</tr>";
    					}
    					$("#UiRoleTbody").html(html);
    				} else {
    					$("#UiRoleTbody").html("<tr class=\"data\"><td colspan=\"7\"><div style=\"text-align:text;\">没有查询到数据，请重试查询条件.</div><td></tr>");
    				}				
    			}
    		});
    	}
    	//赋值更新页面
    	function evaluationUpdateWin(id){
    			$('#updateDialog').dialog('open');
    			var obj = searchDataItem(id,uiSearchData);
    			$('#update_ui_uiResourceDesc').val(obj.uiResourceDesc);
    			$('#update_ui_uiResourceId').val(obj.uiResourceId);
    			$('#update_id').val(obj.id);
    			$('#update_ui_Join_Name').val(obj.joinName);
    			
    			evaluationSelection(UIElementOptions,"update_ui_uiTypeId",obj.uiTypeId);
        		evaluationSelection(FuncAuthOptions,"update_ui_roleId",obj.roleId);
        		//** UI元素不是OmGrid时才对权限下拉框赋值 **//
        		if(obj.uiTypeId!="4"){
        			evaluationSelection(UIAuthOptions,"update_ui_uiResourcePermission",obj.uiResourcePermission);
        		}
        		//** 选择器下拉框 **//
             	evaluationSelection(SelectorArray,"update_ui_uiTypeSelector",null);
        		
        		var nodes = ztreeObj.getSelectedNodes();
        		$('#update_ui_funcId').val(nodes[0].id);
        		
        		//**下拉框赋值 **//
           		drawFlowOption("update_flow_name",(obj.flowId+","+obj.flowVersion));
           		loadJoinData((obj.flowId+","+obj.flowVersion),obj.joinId,'update');
           		
           		//** UI元素权限下拉框赋值 **//
           		chanageType(obj.uiTypeId,'update_ui_uiResourcePermission',obj.uiResourcePermission);
           		
           		//** UI选择方式赋值 **//
           		evaluationSelection(SelectorArray,"update_ui_uiTypeSelector",obj.uiTypeSelector);
    	}
        //删除信息
        function deleteUiRoleInfo(info){
			$.ajax({
	   	 		url:"<%=path %>/auth/ui/deleteById.htm?delInfo="+info,
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
	   	 			$('#delModel').dialog('close');
	   	 			/**初始化表格数据**/
	   	 			setSearchFuncId();
	   	 			initUIRoleGrid();
	   	 		},
	       		error:function(){
	       			$("#messageBoxAlertId").show();
	       			$("#messageBoxAlert").html("删除失败.");
	       		}
	   	 	});
        }
        
        /** 渲染下拉框 **/
        function evaluationSelection(result,id,initVal,type){
    		var authObj = document.getElementById(id);
    		if(authObj!=null && authObj.length<=0){
			    authObj.options.remove(0,1);
			    if(type!=null){
			    	authObj.options.add(new Option("请选择",""));
			    }
	    		for (var i=0;i<result.length;i++){
			 		var obj = result[i];
			 		authObj.options.add(new Option(obj.text,obj.value));
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

 var setting = {
   	async: {
   			enable: true,
			url:"<%=path %>/ps/pp/all.htm"
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
		 onClick: zTreeOnClick,
		 onAsyncSuccess: function(event, treeId, treeNode, msg) {
			 ztreeObj.expandAll(true);
		 }
	}
};
function zTreeOnClick(event, treeId, treeNode) {
	if(treeNode.nodeType == "func"){
		searchType = "0";
		/** 初始化FUNCID数据 **/
		setSearchFuncId(); 
		/** 初始化FUNCID数据 **/
    	loadAuthInfo();
    } 
}
//加载角色数据 
function loadAuthInfo(){
   	$.ajax({
	 		url:"<%=path %>/auth/role/all.htm",
	 		type:"post",
	 		dataType:"json", 
	 		success:function(result){
	 			var roles = new Array(); 
	 			if(result.rows!=null && result.rows.length>0){
	 				for(var i=0;i<result.rows.length;i++){
	 					var obj = new Object();
	 					obj.text = result.rows[i].name;
	 					obj.value = result.rows[i].id;
	 					roles.push(obj);
	 				}
	 			}
	 			FuncAuthOptions = roles;
				//初始化表格 
   				initUIRoleGrid();
		   }
 	});
}
var omGridAuth = ["3","4"];
//** 改变UI权限选项 **//
function chanageType(type,selectId,initVal){
	if(type=="4"){
		if(UIAuthOptions!=null && UIAuthOptions.length>0){
			var omGridAuthArray = new Array();
			for(var i=0;i<UIAuthOptions.length;i++){
				for(var len=0;len<omGridAuth.length;len++){
					if(UIAuthOptions[i].value==omGridAuth[len]){
						omGridAuthArray.push(UIAuthOptions[i]);
					}
				}
			}
			deleteAllOption(selectId);
			evaluationSelection(omGridAuthArray,selectId,initVal); 
		}
	} else {
		deleteAllOption(selectId);
		evaluationSelection(UIAuthOptions,selectId,initVal); 
	}
}
//** 删除Select中所有的Option元素 **//
function deleteAllOption(selectId){
	var selectObj = document.getElementById(selectId);
	if(selectObj!=null && selectObj.length>0){
		for(var i=0;i<selectObj.length;i++){
			selectObj.remove(0);
			i=0;
		}
		selectObj.remove(0);
	}
}
/** **/
function  chanageJoinSetJoinName(obj,id){
	for(var i=0;i<obj.length;i++){
		if(obj[i].selected == true && obj[i].value!=""){
			$("#"+id).val(obj[i].text);
			break;
		}
	}
}
/** 赋值功能节点ID**/
function setSearchFuncId(){
	var nodes = ztreeObj.getSelectedNodes();
	if(nodes!=null && nodes.length>0){
		$("#searchFuncId").val(nodes[0].id);
	}
}
var searchSetting = {
   	async: {
   			enable: true,
			url:"<%=path %>/ps/pp/all.htm"
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
		 onClick: searchZTreeOnClick ,
		 onAsyncSuccess: function(event, treeId, treeNode, msg) {
			$("#searchLoadInfo").hide();
		}
	}
};
function searchZTreeOnClick(event, treeId, treeNode) {
    	/** 下拉树赋值 **/
    	$('#position').val(treeNode.name);
	 	hideDropList();
}
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
</script>

</head>
<body>
	<div class="page-header">
		<h4>UI控件权限管理</h4>
	</div>
	<form class="form-inline" id="uiAuthFormId">
		<input type="hidden" id="searchFuncId"  name="aurFuncId"/>
		<input type="hidden" id="searchType"   name="searchType"/>
		<div>
			<span class="label">功能结构:</span>
			<span class="om-combo om-widget om-state-default">
                <input type="text" id="position" style="width:220px;">
                <span id="choose" class="om-combo-trigger"></span>
            </span>
            <div id="droplist"  style="z-index: 1002;" class="table-bordered">
            	<div id="searchLoadInfo" style="font-size: 12px;">正在加载...</div>
			    <ul id="searchTree" style="text-align:left;"  class="ztree"></ul>
			</div>
			<span class="label">角色:</span>
				<select id="searchAuthRole" name="roleId"></select>
			<span class="label">权限:</span>
				<select id="searchAuth" name="uiResourcePermission"></select>
			<br/>
			<br/>
			<span class="label">流程名称:</span>
				<select class="input-large"  id="search_flow_name" name="flowId" onchange="loadJoinData(this.value,null,'search')"> </select>
			<span class="label">节点名称:</span>
	            <select class="input-large"  id="search_join_name" name="joinId"> </select>
		</div>
		<div style="padding-top: 20px;height: 40px;">
			<div style="float: left;">
					<button id="search" type="button" class="btn btn-info">查询</button>		
					<button id="add" type="button" class="btn btn-primary">增加</button>
					<button id="del" type="button" class="btn btn-danger">删除</button>
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

	<div>
		<div style="float: left;margin-top: 10.px;">
			<div class="zTreeDemoBackground left table-bordered" style="border:1px solid #86a3c4;width: 180px;height: 380px;">
				<ul id="tree" class="ztree"></ul>
			</div>
		</div>
		<div style="margin-left: 200px;">
			<table class="table table-bordered table-striped table-hover" style="margin-top:20px;">
				<thead>
				<tr>
					<th><input type = "checkbox" id="mutilSelectItem"  name = "mutilSelectItem" />&nbsp;&nbsp;选择</th>
					<th>UI元素类型</th>
					<th>UI元素ID</th>
					<th>UI元素描述</th>
					<th>角色</th>
					<th>权限</th>
					<th>流程名称</th>
					<th>节点名称</th>
				</tr>
				</thead>
				<tbody id="UiRoleTbody"></tbody>
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
	</div>
	
	<div id="delModel" title="确定删除">
   		<div class="control-group">
   			<label class="control-label" for="update_name">确定删除UI元素信息?</label>
 			</div>
   </div>
   
   <div id="updateDialog" title="修改UI权限信息">
    	<form class="form-horizontal" action="update_5" method="post" id="update_role_from">
    		<input type="hidden" id="update_id" name="id" >
    		<input type="hidden" id="update_ui_funcId" name="func.id" >
    		<input type="hidden" id="update_ui_Join_Name" name="joinName" >
    		<input type="hidden" id="update_ui_Flow_Name" name="flowName" >
    		<div class="control-group">
    			<label class="control-label" for="update_name">UI元素类型</label>
    			<div class="controls">
      				<select class="input-large"  id="update_ui_uiTypeId" name="uiTypeId"   onchange="chanageType(this.value,'update_ui_uiResourcePermission',null)"> </select>
    			</div>
  			</div>
  			
  			<div class="control-group">
    			<label class="control-label" for="add_name">UI元素选择方式</label>
    			<div class="controls">
      				<select class="input-large"  id="update_ui_uiTypeSelector" name="uiTypeSelector"> </select>
    			</div>
  			</div>
  			
  			<div class="control-group">
    			<label class="control-label" for="update_name">UI元素值</label>
    			<div class="controls">
      				<input type="text" id="update_ui_uiResourceId" name="uiResourceId" placeholder="UI元素类型">
    			</div>
  			</div>
  			<div class="control-group">
    			<label class="control-label" for="update_name">UI元素描述</label>
    			<div class="controls">
      				<input type="text" id="update_ui_uiResourceDesc" name="uiResourceDesc" placeholder="UI元素描述">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="update_description">角色</label>
    			<div class="controls">
      				<select class="input-large"  id="update_ui_roleId" name="role.id"> </select>
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="update_value">权限</label>
    			<div class="controls">
      				<select class="input-large"  id="update_ui_uiResourcePermission" name="uiResourcePermission"> </select>
    			</div>
  			</div>
  			
			<div class="control-group">
    			<label class="control-label" for="update_value">流程名称</label>
    			<div class="controls">
      				<select class="input-large"  id="update_flow_name" name="flowId" onchange="loadJoinData(this.value,null,'update'),chanageJoinSetJoinName(this,'update_ui_Flow_Name')"> </select>
    			</div>
  			</div>
  			<div class="control-group">
    			<label class="control-label" for="update_value">节点名称</label> 
    			<div class="controls">
      				<select class="input-large"  id="update_join_name" name="joinId"onchange="chanageJoinSetJoinName(this,'update_ui_Join_Name')"> </select>
    			</div>
  			</div>
    	</form>
    </div>
    
    <div id="addDialog" title="添加UI权限信息">
    	<form class="form-horizontal" action="add_5" method="post" id="add_role_from">
    		<input type="hidden" id="add_ui_funcId" name="func.id" >
    		<input type="hidden" id="add_ui_Join_Name" name="joinName" >
    		<input type="hidden" id="add_ui_Flow_Name" name="flowName" >
    		<div class="control-group">
    			<label class="control-label" for="add_name">UI元素类型</label>
    			<div class="controls">
      				<select class="input-large"  id="add_ui_uiTypeId" name="uiTypeId" onchange="chanageType(this.value,'add_ui_uiResourcePermission',null)"> </select>
    			</div>
  			</div>
  			
  			<div class="control-group">
    			<label class="control-label" for="add_name">UI元素选择方式</label>
    			<div class="controls">
      				<select class="input-large"  id="add_ui_uiTypeSelector" name="uiTypeSelector" > </select>
    			</div>
  			</div>
  			
  			<div class="control-group">
    			<label class="control-label" for="add_name" id="add_uiElement_label">UI元素值</label>
    			<div class="controls">
      				<input type="text" id="add_ui_uiResourceId" name="uiResourceId" placeholder="UI元素值">
    			</div>
  			</div>
  			<div class="control-group">
    			<label class="control-label" for="add_name" id="add_uiDescript_label">UI元素描述</label>
    			<div class="controls">
      				<input type="text" id="add_ui_uiResourceDesc" name="uiResourceDesc" placeholder="UI元素描述">
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="add_description">角色</label>
    			<div class="controls">
      				<select class="input-large"  id="add_ui_roleId" name="role.id"> </select>
    			</div>
  			</div>
    		<div class="control-group">
    			<label class="control-label" for="add_value">权限</label>
    			<div class="controls">
      				<select class="input-large"  id="add_ui_uiResourcePermission" name="uiResourcePermission"> </select>
    			</div>
  			</div>
  			
  			<div class="control-group">
    			<label class="control-label" for="add_value">流程名称</label>
    			<div class="controls">
      				<select class="input-large"  id="add_flow_name" name="flowId" onchange="loadJoinData(this.value,null,'add'),chanageJoinSetJoinName(this,'add_ui_Flow_Name')"> </select>
    			</div>
  			</div>
  			<div class="control-group">
    			<label class="control-label" for="add_value">节点名称</label>
    			<div class="controls">
      				<select class="input-large"  id="add_join_name" name="joinId" onchange="chanageJoinSetJoinName(this,'add_ui_Join_Name')"> </select>
    			</div>
  			</div>
    	</form>
    </div>
</body>
</html>