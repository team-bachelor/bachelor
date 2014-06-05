
function getCheckBoxValue(checkBoxName){
	var var_checkBoxVal = new Array();
	$('input[name="'+checkBoxName+'"]:checked').each(function(){
	 	var_checkBoxVal.push($(this).val());
	});
	return var_checkBoxVal;
}


function getRaidoValue(raidoName){
	var var_raidoVal =  $('input[name="'+raidoName+'"]:checked').val();
	return var_raidoVal;
}

function loadDelDialog(dialog,checkBoxName,deleteFunction){
	//删除信息框
	$('#'+dialog).dialog({
	    autoOpen: false,
	    modal: true,
	    buttons: {
	    	 	"Ok": function () {
	    	 		var tempDelInfo = getCheckBoxValue(checkBoxName);
	    	 		//确定调用删除方法
	    	 		deleteFunction(tempDelInfo);
		        },
		        "Cancel": function () {
		            $(this).dialog("close");
		        }
	    }
	});
}

function loadCommonDelDialog(dialogId,deleteFunction,cancelFunction){
	//删除信息框
	$('#'+dialogId).dialog({
	    autoOpen: false,
	    modal: true,
	    buttons: {
	    	 	"确定": function () { 
	    	 		//确定调用删除方法
	    	 		deleteFunction();
		        },
		        "取消": function () {
		            $(this).dialog("close");
		            cancelFunction();
		        }
	    }
	});
}

function loadAppointDelDialog(dialog,ztree,deleteFunction){
	//删除信息框
	$('#'+dialog).dialog({
	    autoOpen: false,
	    modal: true,
	    buttons: {
	    	 	"Ok": function () {
	    	 		var node = ztreeObj.getSelectedNodes();
	    	 		deleteFunction(node[0].id);
		        },
		        "Cancel": function () {
		            $(this).dialog("close");
		        }
	    }
	});
}
//全选
function selectAllItem(selectCheckBoxId,checkBoxValueId){
	$('#'+selectCheckBoxId).click(function(){
	   var obj=document.getElementsByName(checkBoxValueId);  //选择所有name="'test'"的对象，返回数组  
	   var s='';  
	   for(var i=0; i<obj.length; i++){  
	   		obj[i].checked = this.checked;
	   }  
		
	});
}
//修改
function update(formId,checkBoxId,messageBoxId,showMessageBoxId,updateInfoFunction){
	$("#"+formId)[0].reset();
	
	 var var_checkBoxVal = getCheckBoxValue(checkBoxId);
	 
	 if(var_checkBoxVal.length == 1){
	 	
		 updateInfoFunction(var_checkBoxVal[0]);
	} else if(var_checkBoxVal.length>1){
		$("#"+messageBoxId).show();
		$("#"+showMessageBoxId).html("修改只能选择一条信息.");
	} else {
		$("#"+messageBoxId).show();
		$("#"+showMessageBoxId).html("请选择你要修改的信息.");
	}
}

//查询选项
function searchDataItem(id,searchDataResult,type){
	var returnObj = new Object();
	if(searchDataResult!=null && searchDataResult.length>0){
		for(var i=0;i<searchDataResult.length;i++){
			var obj = searchDataResult[i];
			if(type=="bizKey"){
				if(obj.bizKey == id){
					returnObj = obj;
					break;
				}
			} else if(type=="processInstanceId"){
				if(obj.processInstanceId == id){
					returnObj = obj;
					break;
				}
			}else {
				if(obj.id == id){
					returnObj = obj;
					break;
				}
			}
		}
	}
	return returnObj;
}

//修改信息框
function showUpdateDialog(id,url,formId,messageBoxId,messageBoxContentId,reloadFunction){
	$('#'+id).dialog({
	    autoOpen: false,
	    width: 600,
	    buttons: {
	        "Ok": function () {
	        	$.ajax({
	    	 		url:url,
	    	 		contentType:"application/json",
	    	 		type:"get",
	    	 		dataType:"json",
	    	 		data:$("#"+formId).serialize(),
	    	 		success:function(result){ 
	    	 			$("#"+messageBoxId).show();
	    	 			if(result.ResultCode == "0"){
	    	 				$("#"+messageBoxContentId).html("修改成功.");
	    	 			} else if(result.ResultCode == "2"){
	    	 				$("#"+messageBoxContentId).html("不能重复添加信息.");
	    	 			} else {
	    	 				$("#"+messageBoxContentId).html("修改失败.");
	    	 			}
	    	 			$('#'+id).dialog('close');
	    	 			//加载全局变量信息
	    	 			reloadFunction();
	    	 		},
	        		error:function(){
	        			$("#"+messageBoxContentId).html("服务器连接错误，请重试.");
	        		}
	    	 	});
	        },
	        "Cancel": function () {
	            $(this).dialog("close");
	        }
	    }
	});
}

//显示添加框
function showAddDialog(id,url,formId,messageBoxId,messageBoxContentId,reloadFunction){
	//添加信息框
	$('#'+id).dialog({
	    autoOpen: false,
	    width: 600,
	    buttons: {
	        "Ok": function () {
	        	$.ajax({
	    	 		url:url,
	    	 		contentType:"application/json",
	    	 		type:"get",
	    	 		dataType:"json",
	    	 		data:$("#"+formId).serialize(),
	    	 		success:function(result){ 
	    	 			$("#"+messageBoxId).show();
	    	 			if(result.ResultCode == "0"){
							$("#"+messageBoxContentId).html("添加成功.");
	    	 			} else if(result.ResultCode == "2"){
	    	 				$("#"+messageBoxContentId).html("不能重复添加全局变量名称.");
	    	 			} else if(result.ResultCode == "1"){
	    	 				$("#"+messageBoxContentId).html("没有找相关信息,添加失败.");
	    	 			} else{
	    	 				$("#"+messageBoxContentId).html("添加失败.");
	    	 			}
	    	 			$('#'+id).dialog('close');
	    	 			//加载全局变量信息
	    	 			reloadFunction();
	    	 		},
	        		error:function(){
	        			$("#"+messageBoxContentId).html("服务器连接错误，请重试.");
	        		}
	    	 	});
	        },
	        "Cancel": function () {
	            $(this).dialog("close");
	        }
	    }
	});
}

//得到选中值
function getSelectVal(sObj){
	var val = "";
	for(var i=0;i<sObj.length;i++){
		if(sObj[i].selected == true){
			val = sObj[i].value;
			break;
		}
	}
	return val;
}
//显示提示信息
function showToolTip(id){
	$('#'+id).tooltip({
    	trigger:"focus",
    	placement:"right",
    	animation:true 
    });
}

//** 不能为空判断 **//
function isEmpty(val,len,str){
	if(val==null || val=="" || val.length<=0){
		
		return "";
	}
	if(len!=null && val.length >= len){
		
		return val.substring(0,len)+str;
	}
	return val;
}
/** 打开锁屏 DIV层**/
function processScreen(flag){
	 var overlay = document.getElementById('overlay');
     var dd = document.documentElement;
                var db = document.body;
	  if (flag == 'show') {
	   overlay.style.display = "block";
	   overlay.style.top=Math.max(dd.scrollTop, db.scrollTop);//当页面出现滚动条时下拉后获得起始位置
	   overlay.style.left=Math.max(dd.scrollLeft, db.scrollLeft);
	   overlay.style.width=Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth) + "px";
	   overlay.style.height=Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight) + "px";
	   document.body.style.overflow="hidden";//隐藏滚动条
	   
			return true;
	 }
	 else {
	  	overlay.style.display = "none";
	 }
}

var ztreeProcessObject;

/**
 * 操作树状态，展开，关闭
 */
function processTreeStatue(showDivId,ztreeObject){
	ztreeProcessObject = ztreeObject;
	var html = '<div class="btn-group">';
	html += '<li class="icon-minus-sign" onclick="extendsTree()" style="cursor:pointer;">  </li>';
	html += '<li class="icon-plus-sign" onclick="contractTree()" style="cursor:pointer;"> </li>';
	html+='<input type="text" class="input-medium search-query span2" id="searchTreeNodeId"/>';
	html += '<li class="icon-search" onclick="searchTreeNodes()" style="cursor:pointer;">   </li>';
	html += '<li class="icon-remove" onclick="clearSearchTextVal()" style="cursor:pointer;">  </li>';
	html += '</div>';
	$("#"+showDivId).html(html);
}

/** 
 * 搜索节点信息
 * @returns
 */
function searchTreeNodes(searcherTxtId){
	var searchNodeVal = $("#searchTreeNodeId").val();
	if(searchNodeVal==null || searchNodeVal=="" || searchNodeVal.length<=0){
		
		return ;
	}
	var nodes = ztreeProcessObject.getNodes();
	if(nodes==null || nodes.length<=0){
		
		return ;
	}
	var snodes = ztreeProcessObject.getNodesByParamFuzzy("name",searchNodeVal,null);
	if(snodes!=null && snodes.length>0){
		for(index in snodes){
			ztreeProcessObject.selectNode(snodes[index]);
		}
	}
} 
/**
 * 清理搜索文本框中数值
 */
function clearSearchTextVal(){
	ztreeProcessObject.expandAll(true);
	$("#searchTreeNodeId").val("");
	$("#searchTreeNodeId").focus();
}
/**
 * 展开树节点
 * @param ztreeObjction
 */
function extendsTree(){
	ztreeProcessObject.expandAll(true);
}
/**
 * 收起树节点
 * @param ztreeObjction
 */
function contractTree(){
	ztreeProcessObject.expandAll(false);
}