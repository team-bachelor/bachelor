/** 添加树的配置 **/
 var addSetting = {
  	async: {
  			enable: true,
		url:(path+"/ps/pp/all.htm")
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
		 onClick: addZTreeOnClick,
		 onAsyncSuccess:function(event, treeId, treeNode, msg){
			 $("#searchLoadInfo").hide();
		 }
	}
};
 /** 添加树的节点单击事件 **/
function addZTreeOnClick(event, treeId, treeNode) {
		if(treeNode.nodeType == "func"){
			$("#add_ui_funcId").val(treeNode.id);
			$("#position").val(treeNode.name);
			hideDropList();
		} else {
			$("#messageBoxAlertId").show();
			$("#messageBoxAlert").html("请选择功能节点.");
		}
}

/** 更新树的配置 **/
var updateSetting = {
 	async: {
 			enable: true,
		url:(path+"/ps/pp/all.htm")
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
		 onClick: updateZTreeOnClick,
		 onAsyncSuccess:function(event, treeId, treeNode, msg){
			 $("#delSearchLoadInfo").hide();
		 }
	}
};
/** 添加树的节点单击事件 **/
function updateZTreeOnClick(event, treeId, treeNode) {
		if(treeNode.nodeType == "func"){
			$("#update_ui_funcId").val(treeNode.id);
			$("#delPosition").val(treeNode.name);
			hideDelDropList();
		} else {
			$("#messageBoxAlertId").show();
			$("#messageBoxAlert").html("请选择功能节点.");
		}
}

/** 搜索树的配置 **/
var setting = {
   	async: {
   			enable: true,
			url:(path+"/ps/pp/all.htm")
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
			 processTreeStatue("projectStructureTreeId",ztreeObj);
		 }
	}
};
/** 搜索树的节点单击事件 **/
function zTreeOnClick(event, treeId, treeNode) {
	searchUiData();
}

function searchUiData(){
	var selectNodes = ztreeObj.getSelectedNodes();
	 searchType = "1"; 
	/** 得到选择的FuncId **/
	var funcIds = "";
	funcIds = loopSelectNodes(selectNodes,funcIds);
	$("#searchFuncId").val(funcIds);
	/** 初始化权限数据 **/
	loadAuthInfo();
}

function showDropList(left,top){ 
   	$("#droplist").css({left: ("190px"),top: ("35px")})
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
/** 更新功能树方法 **/
function showDelDropList(left,top){ 
   	$("#delDroplist").css({left: ("190px"),top: ("35px")})
   	              .show();
   	//body绑定mousedown事件，当事件对象非下拉框、下拉按钮等下拉列表隐藏。
   	$("body").bind("mousedown", onDelBodyDown);
   }
   
function hideDelDropList() {
	$("#delDroplist").hide();
	$("body").unbind("mousedown", onDelBodyDown);
}

function onDelBodyDown(event) {
	if (!(event.target.id == "delChoose" || event.target.id == "delDroplist" || $(event.target).parents("#delDroplist").length>0)) {
		hideDelDropList();
	}
}