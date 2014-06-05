<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目属性维护</title>
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
     
    #droplist{
        display:none; 
        position: absolute; 
        width:158px;
        /* 兼容IE6,7 */
        *width:154px;
        /* 兼容IE8 */
        width:162px\0;
        height:140px;
        border:1px solid;
        overflow: auto;
        background-color: #ffffff;
        border-color:#74b9ef;
        z-index: 1002;
     } 
</style>
<script type="text/javascript"><!--
//定义全局的变量，给后台调用前台js留句柄
var ztreeObj;
$(document).ready(function() {
	//*** 项目树 **//
	$.fn.zTree.init($("#projectTree"), setting);	
	ztreeObj = $.fn.zTree.getZTreeObj("projectTree");
    //*** 项目树 **// 
	
	//点击下拉按钮显示下拉列表
    $("#choose").click(function(){
    	showDropList();
    });
	//点击输入框显示下拉列表
    $("#position").val("").click(function(){
    	showDropList();
    });

//*************************下面是Ajax提交*********************
    $('#submit').bind("click",function() {
        $.ajax({
        	url:"<%=path %>/ps/pp/update.htm",
        	type:"post",
        	dataType:"json",
        	data:$("#form1").serialize(),
        	success:function(result){
        		$("#messageBoxAlertId").show();
        		if(result.resultCode == "0"){
        			$("#messageBoxAlert").html("保存成功.");
        			$('#ppId').val(result.ppId);
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
    $('#reset').bind("click",function(ev){
    	$("#messageBoxAlertId").hide();
    }); 
    showToolTip("property_name_id");
    showToolTip("property_context_id");
    showToolTip("property_version_id");
});
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
	 
	var setting = {
		   	async: {
		   			enable: true,
					url:"<%=path %>/ps/bd/all.htm"
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
				 onAsyncSuccess:function(ev){
					 var target = ztreeObj.getNodeByParam("id", $('#ppbdId').val(), null);
					 if(target!=null){
						 var parentObj = target.getParentNode();
			        	 $('#position').val((parentObj==null?target.name:(parentObj.name+'-'+target.name)));
					 }
				 }
			}
	};
	
	function zTreeOnClick(event, treeId, treeNode){
  	   var name = treeNode.name;
	   var pdata = treeNode.getParentNode();//$("#tree").omTree("getParent",ndata);
	   ztreeObj.selectNode(treeNode);
  	   $("#position").val(((pdata!=null?(pdata.name +"-" +name):name)));//保存信息
  	   $('#ppbdId').val(treeNode.id);//保存ID
  	   hideDropList();
  	}
--></script>

</head>
<body>

<div class="page-header text-center">
	<h4>项目属性维护</h4>
</div>
<form class="form-horizontal" action="#" method="post" id="form1">
		<form:hidden path="model.id" id="ppId"/>
  		<div class="control-group">
  			<label class="control-label" for="add_name">项目名称:</label>
  			<div class="input-prepend" style="padding-left: 20px;">
    			<form:input path="model.name" type="text" id="property_name_id"  title="项目名称必须是必须是英文和数字"/>
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_value">上下文名称:</label>
  			<div class="input-prepend" style="padding-left: 20px;">
    			<form:input path="model.context" type="text" id="property_context_id" title="上下文名称必须是必须是英文和数字"/>
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
  		<div class="control-group">
  			<label class="control-label" for="add_description">标题:</label>
  			<div class="controls">
    			<form:input path="model.title" type="text" id="add_description" />
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_description">项目子标题:</label>
  			<div class="controls">
    			<form:input path="model.subTitle" type="text" id="add_subTitle" />
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_description">LOGO URI:</label>
  			<div class="controls">
    			<form:input path="model.logoUrl" type="text" id="add_logoUrl" />
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_value">首页URI:</label>
  			<div class="controls">
    			<form:input path="model.indexPath" type="text" />
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">  
  			<label class="control-label" for="add_value">登陆页URI:</label>
  			<div class="controls">
    			<form:input path="model.loginShowPath" type="text" />
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_value">登录验证URI:</label>
  			<div class="controls">
    			<form:input path="model.loginAuthPath" type="text" />
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_value">错误页URI:</label>
  			<div class="controls">
    			<form:input path="model.errorPath" type="text" />
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_value">主业务域:</label>
  			<div class="controls">
    				<span>
						<form:hidden path="model.ppbd.id" id="ppbdId"/>
		                <input type="text" id="position" >
		                <span id="choose" class="om-combo-trigger"></span>
		            </span>
		            <div id="droplist" style="width: 220px;">
					    <ul id="projectTree" style="text-align:left;width: 210px;" class="ztree"></ul>
					</div>
					<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_value">版权信息:</label>
  			<div class="controls">
    			<form:input path="model.copyright" type="text" />
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="control-group">
  			<label class="control-label" for="add_value">当前版本:</label>
  			<div class="input-prepend" style="padding-left: 20px;">
  				<span class="add-on" >V</span>
    			<form:input path="model.version" type="text" id="property_version_id" title="版本格式:VXX.XX.XX"/>
    			<span style="color: red;font-size: 13px;">&nbsp;*</span>
  			</div>
		</div>
		<div class="row">
			<div style="text-align: center;float: left;width: 320px;" >
				<button id="submit" type="button" class="btn btn-info">保存</button>		
				<button id="reset" type="reset" class="btn btn-primary">重置</button>
			</div>
			<div  style="float: right; display: none;" id="messageBoxAlertId" >
				<div class="alert alert-error" style="width:180px;" >
				    <strong id="messageBoxAlert"></strong> 
				</div>
			</div>
		</div>
</form>
</body>
</html>