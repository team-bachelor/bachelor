<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>拦截器维护</title>
<!-- bootstrap -->
<link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="<%=path %>/css/jquery-ui-1.10.0.custom.css" rel="stylesheet" />
<link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">
<script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
<script src="<%=path %>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script>
<script src="<%=path %>/js/bootStrapCommon.js"></script>
<script src="<%=path %>/js/bt-validate.js"></script>
<!-- bootstrap --> 
<style>
    html, body{ width: 100%; height: 100%; padding: 0; margin: 0;overflow: hidden;}
</style>
<script type="text/javascript">
//是否启用
var enableArray = ["启用","不启用"];
var type = ["Controller","Biz","Service","Dao"];
var intercepterSearchData;
//定义全局的变量，给后台调用前台js留句柄
$(document).ready(function() {
	intercepterSearchData = new Array();
//*************************下面是Ajax提交*********************
    $('#reset').bind("click",function(ev){
    	$("#messageBoxAlertId").hide();
    });
    $('#update_from').validation();
    $('#save_from').validation();
	
    //显示添加框
  	$('#saveDialog').dialog({
  	    autoOpen: false,
  	    width: 600,
  	    buttons: false  
  	});
  	//显示添加框
  	$('#updateDialog').dialog({
  	    autoOpen: false,
  	    width: 500,
  	    buttons: false  
  	});
    $("#cancel").bind("click",function(ev){
		$("#messageBoxAlertId").hide(); 
    	
    	$('#saveDialog').dialog("close");
    });
    $("#updateCancel").bind("click",function(ev){
		$("#messageBoxAlertId").hide(); 
    	
    	$('#updateDialog').dialog("close");
    });
    $("#addBtn").bind("click",function(ev){
    	$("#messageBoxAlertId").hide(); 
    	
    	$('#update_className_id').val("");
		$('#update_description_id').val("");
		$('#intercepter_id').val("");
		
    	$('#saveDialog').dialog("open");
    });
    $("#deleteBtn").bind("click",function(ev){
    	
    	$("#messageBoxAlertId").hide();
		var roleDeleInfo = getCheckBoxValue("idCheckBox");
		if(roleDeleInfo!=null && roleDeleInfo.length>0){
			$('#delModel').dialog("open");
		} else {
			$("#messageBoxAlertId").show();
			$("#messageBoxAlert").html("请选择要删除拦截器.");
		}
    });
    //加载拦截器信息
    loadInceterpterInfo();
  	//加载删除对话框
	loadDelDialog("delModel","idCheckBox",deleteIntercepterInfo);
});   
//删除信息
function deleteIntercepterInfo(info){
	$.ajax({
			url:"<%=path %>/intercepter/delete.htm?delInfo="+info,
			type:"get",
			dataType:"json",
			success:function(result){
				$("#messageBoxAlertId").show();
				$('#delModel').dialog('close');
				if(result.resultCode == "0"){
					$("#messageBoxAlert").html("删除成功.");
				} else { 
					$("#messageBoxAlert").html("删除失败.");
				}
				loadInceterpterInfo();
			}
	});
}
//加载全局变量信息
function loadInceterpterInfo(){
	$.ajax({
			url:"<%=path %>/intercepter/all.htm",
			type:"get",
			dataType:"json",
			success:function(result){
				intercepterSearchData = result;
				if(result!=null && result.length>0){
					var html = "";
					var tooltipArray = new Array();
					for(var i=0;i<result.length;i++){
						var obj = result[i];
						html+= '<tr class="data" style="cursor: pointer;"';
						html+=' id="stat_className_id_'+i+'"';
						//title="拦截器全类名:'+(obj.className!=null?obj.className:"")+'\n拦截器状态:'+(obj.enable!=null?enableArray[obj.enable-1]:"")+'"';
						html+='ondblclick="evaluationUpdateWin(\''+obj.id+'\')">';
						//html+='<td><input type="checkbox" value="'+obj.id+'" name="idCheckBox"/></td>';
						html+='<td>'+(obj.className!=null?obj.className:"")+'</td>';
						html+='<td>'+(obj.type!=null?type[obj.type-1]:"")+"</td>";
						html+='<td>'+(obj.enable!=null?enableArray[obj.enable-1]:"")+'</td>';
						html += "</tr>";
						tooltipArray.push(i);
					}
					$("#inceterpterTbody").html(html);
					//initTooltip(tooltipArray);
				} else {
					$("#inceterpterTbody").html("<tr class=\"data\"><td colspan=\"4\"><div style=\"text-align:text;\">没有查询到数据，请重试查询条件.</div><td></tr>");
				}				
			}
	});
}
//初始化提示框
function initTooltip(tooltipArray){
	for(var i=0;i<tooltipArray.length;i++){
		//tooltip提示初始化
		showToolTip("stat_className_id_"+i);
	}
}
//赋值更新页面
function evaluationUpdateWin(id,roleId){
		$('#updateDialog').dialog('open');
		
		$('#update_className_id').val("");
		$('#update_description_id').val("");
		$('#intercepter_id').val("");
		
		var obj = searchDataItem(id,intercepterSearchData);
		$('#update_className_id').val(obj.className);
		$('#update_description_id').val(obj.enable);
		$('#intercepter_id').val(obj.id);
}
//功能返回方法
function funReturnMethod(funcResult){ 
	if(funcResult!="{}"){
		funcResult = funcResult.replace(/=/g,":");
		var result = eval("("+funcResult+")");
		$("#messageBoxAlertId").show();
		if(result.resultCode == "0"){
			$("#messageBoxAlert").html("保存成功.");
		} else if(result.resultCode == "2"){
			$("#messageBoxAlert").html("拦截器名称不能重复.");
		} else{
			$("#messageBoxAlert").html("保存失败.");
		}
	} else { 
		$("#messageBoxAlertId").show();
		$("#messageBoxAlert").html("保存失败.");
	}
	//是否成功全都刷新和关闭窗口
	loadInceterpterInfo();
	$('#updateDialog').dialog('close');
	$('#saveDialog').dialog('close');
}
</script>

</head>
<body>  
<div class="page-header">
	<h4>拦截器维护</h4>
</div>
<!--<div class="row" style="padding-top: 10px;padding-left:20px;padding-bottom:20px;height: 40px;">
	 <div style="float: left;"> 	
		<button id="addBtn" type="button" class="btn btn-primary">增加</button>
		<button id="deleteBtn" type="button" class="btn btn-danger">删除</button> 
	</div> 
	<div  style="float: right;display: none;" id="messageBoxAlertId" >
		<div class="alert alert-error" style="width:280px;" >
		    <strong id="messageBoxAlert"></strong> 
		</div>
	</div>
</div>-->
<table class="table table-bordered table-striped table-hover">
	<thead>
	<tr>
		<!-- <th><input type = "checkbox" id="mutilSelectItem"  name = "mutilSelectItem" />&nbsp;&nbsp;选择</th> -->
		<th>拦截器全类名</th>
		<th>拦截器层类型</th> 
		<th>是否启用</th> 
	</tr>
	</thead>
	<tbody id="inceterpterTbody"></tbody>
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

<div id="delModel" title="确定删除">
	<div class="control-group">
		<label class="control-label" for="update_name">确定删除拦截器信息?</label>
	</div>
</div>

<div id="saveDialog" title="新增拦截器信息">
 	<form class="form-horizontal" action="<%=path %>/intercepter/saveOrUpdate.htm" 
 			method="post" id="save_from" target="hiddenframe">
 		<div class="control-group">
 			<label class="control-label" for="add_name">拦截器全类名</label>
 			<div class="controls">
   				<input class="input-large" name="className" 
   					btvd-type="required" btvd-err="拦截器全类名不能为空！" btvd-class='btvdclass'
   					id="add_className_id"> 
 			</div>
		</div>
 		<div class="control-group">
 			<label class="control-label" for="add_description">是否启用</label>
 			<div class="controls">
   				<select class="input-large"  
   					btvd-type="required" btvd-err="是否启用不能为空！" btvd-class='btvdclass'
   					id="add_description_id" name="enable">
					<option value="1">启用</option>
					<option value="2">不启用</option>
				</select>
 			</div>
		</div>
		<div class="row">
			<div style="text-align: center;float: left;width: 320px;" >
				<button id="submitForm" type="submit" class="btn btn-info">保存</button>
				&nbsp;&nbsp;		
				<button id="reset" type="reset" class="btn btn-primary">重置</button>
				&nbsp;&nbsp;		
				<button id="cancel" type="button" class="btn btn-danger">取消</button>
			</div> 
		</div>  
 	</form>
 </div>
 <div id="updateDialog" title="修改拦截器信息">
 	<form class="form-horizontal" action="<%=path %>/intercepter/saveOrUpdate.htm" 
 			method="post" id="update_from" target="updateHiddenframe">
 		<input type="hidden" id="intercepter_id" name="id" >
 		<div class="control-group">
 			<label class="control-label" for="update_name" >拦截器全类名</label>
 			<div class="controls">
   				<input class="input-large span4" name="className" readonly="readonly" 
   					id="update_className_id"> 
 			</div>
		</div>
 		<div class="control-group">
 			<label class="control-label" for="update_description">是否启用</label>
 			<div class="controls">
   				<select class="input-large"  
   					btvd-type="required" btvd-err="是否启用不能为空！" btvd-class='btvdclass'
   					id="update_description_id" name="enable">
					<option value="1">启用</option>
					<option value="2">不启用</option>
				</select>
 			</div>
		</div>
		<div class="row">
			<div style="text-align: center;float: left;width: 320px;" >
				<button id="updateSubmitForm" type="submit" class="btn btn-info">保存</button>
				&nbsp;&nbsp;		
				<button id="updateReset" type="reset" class="btn btn-primary">重置</button>
				&nbsp;&nbsp;		
				<button id="updateCancel" type="button" class="btn btn-danger">取消</button>
			</div> 
		</div>  
 	</form>
 </div>
 <iframe name="hiddenframe" style=display:none></iframe>
 <iframe name="updateHiddenframe" style=display:none></iframe>
</body>
</html>