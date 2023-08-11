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
<script type="text/javascript">
var createMenuZtreeObj;
//定义全局的变量，给后台调用前台js留句柄
$(document).ready(function() {
	/** 生成菜单功能树 **/
	$.fn.zTree.init($("#createMenuTree"), createMenuSetting);	
	createMenuZtreeObj = $.fn.zTree.getZTreeObj("createMenuTree");
});

var createMenuSetting = {
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
			chkboxType:  { "Y" : "ps","N" : "ps" } 
 		},
		callback: {
				
		} 
};
</script>
</head>
<body> 
 		<div class="control-group"  >
 			<ul id="createMenuTree" class="ztree" style="height: 120px;"></ul>
		</div>
</body>
</html>