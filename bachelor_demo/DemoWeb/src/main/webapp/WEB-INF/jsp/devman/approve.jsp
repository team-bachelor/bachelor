<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>设备维修</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
	<link href="<%=path %>/css/bootstrap.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/css/default/om-default.css" rel="stylesheet" type="text/css">
	<link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">
	<link type="text/css" href="<%=path %>/css/jquery-ui-1.10.0.custom.css" rel="stylesheet" />
	
	<script type="text/javascript"  src="<%=path %>/js/jquery-1.9.0.min.js"></script>
	<script  type="text/javascript"  src="<%=path %>/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script>
     <script type="text/javascript" src="<%=path %>/js/commom-task.js"></script>
	 <script type="text/javascript" src="<%=path %>/js/ui-auth-commom.js"></script>
    
    <style type="text/css">
	  body {
        padding-top: 20px;
        padding-bottom: 60px;
      }

      /* Custom container */
      .container {
        margin: 0 auto;
        max-width: 1000px;
      }
      .container > hr {
        margin: 60px 0;
      }

      /* Main marketing message and sign up button */
      .jumbotron {
        margin: 60px 0;
        text-align: center;
      }
      .jumbotron h1 {
        font-size: 60px;
        line-height: 1;
      }
      .jumbotron .lead {
        font-size: 24px;
        line-height: 1.25;
      }
      .jumbotron .btn {
        font-size: 21px;
        padding: 14px 24px;
      }

      /* Supporting marketing content */
      .marketing {
        margin: 60px 0;
      }
      .marketing p + h4 {
        margin-top: 28px;
      }
	.form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
      .hightBorder{
			width:250px;height:auto;border:#3AEB3E 1px solid;color:#333;
			filter:progid:DXImageTransform.Microsoft.Shadow(color=#909090,direction=120,strength=4);/*ie*/
			-moz-box-shadow: 2px 2px 10px #909090;/*firefox*/
			-webkit-box-shadow: 2px 2px 10px #909090;/*safariܲchrome*/
			box-shadow:2px 2px 10px #909090;/*operaܲie9*/
		}
    </style>
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="<%=path %>/js/html5shiv.js"></script>
    <![endif]-->
    
   <script type="text/javascript">
   			var textInputArray = ["completeTime","principal"];
   			var taskInfoArray = ["waitManId","waitRoleId","takeManId"];
   			
   			var tasks = {"id":"1","completeTime":"2012-05-12 12:54:01","principal":"北京电力公司/研发部/胡斌"};
   			var tasks1 = {"id":"2","completeTime":"2012-05-05 01:54:01","principal":"北京电力公司/研发部/张涛"};
   			var second = {"x":"94.0","y":"51.0","width":"98.0","height":"80.0","id":"1"};
   			var second1 = {"x":"275.0","y":"51.0","width":"98.0","height":"80.0","id":"2"};
   			var divArray1 = new Array();
   			divArray1.push(tasks);
   			divArray1.push(tasks1);
   			
   			var divArray = new Array();
   			divArray.push(second);
   			divArray.push(second1);
   			
   			$(document).ready(function(){
   					$('#taskPopId').dialog({
						    autoOpen: false,
						    width: 340,
						    buttons: { 
						        "Cancel": function () {
						            $(this).dialog("close");
						        }
						    }
					});
   			
   					/** 获取节点图形及数据 **/
   					initTaskData("${model.id }","<%=path %>/devman/graphicData.htm","pdDiv","showGraphicId","taskPopId",null);
   					/** 获取UI权限及对UI的控制 **/
   					var functionId = "<%=request.getSession().getAttribute("function_id") %>";
   					initUiElement("<%=path %>",functionId,"${model.id}");
   			});
   			
   </script>
  </head>

   <body>
	<div class="page-header">
		<h3>${BpDataEx.taskEx.task.name}</h3>
	</div>
	<!-- 必须要加的UI组件 -->
	<div id="taskPopId"  title="" >
    		 <div class="popover-content">
					<span class="label label-info">待办人:</span>
					<span id="waitManId" class="badge badge-info" ></span>
					<br/>
					<span class="label label-info">待办角色:</span>
					<span id="waitRoleId" class="badge badge-info" ></span>
					<br/>
					<span class="label label-info">负责人:</span>
					<span id="takeManId" class="badge badge-info" ></span>
			</div>
    </div>
	<div id="showGraphicId"></div>
	<!-- 图形 -->
	<img id="pdDiv" src="<%=path %>/devman/diagram.htm?bizKey=${model.id}"/>	
	<!-- 必须要加的UI组件 -->			
	<div id="node1Div"></div>
	<c:if test="${\"申请\" eq BpDataEx.taskEx.task.name}">
		<form class="form-inline" name="devMan" action="doApply.htm" method="post">
	</c:if>
	<c:if test="${\"审批\" eq BpDataEx.taskEx.task.name}">
		<form class="form-inline" name="devMan" action="doVerify.htm" method="post">
	</c:if>	
		<div>
			<input type="hidden" value="${model.id }" name="id">
			<input type="hidden" value="${BpDataEx.taskEx.task.id }" name="taskId">
			<span class="label">理由:</span>
			<input type="text" class="span2" name="reason" value="${model.reason }" id="reasonId">
		</div>
		<div>
			<span class="label">描述:</span>
			<input type="text" class="span3" name="description" value="${model.description }" id="descriptionId">
		</div>
		<div>
			<span class="label">审核意见:</span>
			<input type="text" class="span3" name="verifyContent" value="${model.verifyContent }" id="verifyId">
		</div>
		<div>
			<span class="label">备注</span>
			<input type="text" class="span3" name="memo" value="${model.memo }" id="memoId">
		</div>
		<div style="padding-top: 20px;height: 40px;">
			<div style="float: left;">
				
					<c:if test="${\"申请\" eq BpDataEx.taskEx.task.name}">
					<button id="submit" type="submit" class="btn btn-info">申请</button>	
					</c:if>
					<c:if test="${\"审批\" eq BpDataEx.taskEx.task.name}">
					<button id="submit" type="submit" class="btn btn-info">审核</button>	
					</c:if>
					
				<a href="index.htm" class="btn btn-primary ">返回</a>
			</div>
		</div>
	</form>
	<div id="showXYValue"></div>
	<script type="text/javascript">
	$(document).ready(function(){
		var pdDivObj = $('#pdDiv');
		var i=0;
	});
		
	</script>

  </body>
</html>
