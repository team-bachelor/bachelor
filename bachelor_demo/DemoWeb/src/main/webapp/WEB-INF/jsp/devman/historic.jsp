<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>已完成设备维修流程</title>
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
	 <script type="text/javascript" src="<%=path %>/js/ui-auth-commom.js"></script>
    <script type="text/javascript" src="<%=path %>/js/commom-task.js"></script>
    
    <style type="text/css">
	  body {
        padding-top: 20px;
        padding-bottom: 60px;
      }
    </style>
    <script type="text/javascript">
    	/** 如果没有要显示的节点信息，必须创建一个新的数据**/
    	var taskInfoArray = new Array();
    	
    	$(document).ready(function(){
    			
    			$("#showProcessImage").dialog({
						    autoOpen: false,
						    width: 800,
						    height:400,
						    buttons: { 
						        "Cancel": function () {
						            $(this).dialog("close");
						        }
						    }
					});
    				
    				
    				$("#taskPopId").dialog({
						    autoOpen: false,
						    width: 340,
						    buttons: { 
						        "Cancel": function () {
						            $(this).dialog("close");
						        }
						    }
					});
    			
    	});
    	
    	/** 显示流程图 **/
		function showProcessImage(bizKey){
				$("#pdDiv").attr("src",("<%=path %>/devman/diagramHistoric.htm?bizKey="+bizKey));
				$("#showProcessImage").dialog("open");
				getGraphicData(bizKey);
		}
		
		/** 得到流程各节点数据 **/
		function getGraphicData(bizKey){
				/** 获取节点图形及数据 **/
 				initTaskData(bizKey,"<%=path %>/devman/hisGraphicData.htm","pdDiv","showGraphicId","taskPopId","showProcessImage");
		}
    </script>
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="<%=path %>/js/html5shiv.js"></script>
    <![endif]--> 
  </head>

   <body>
	<div class="page-header">
		<h3>已完成设备维修流程</h3>
	</div>
	
	<!-- 必须要加的UI组件 -->
	<div id="taskPopId"  title="" >
    </div>

	<div id="showGraphicId"></div>
	<!-- 图形 -->
	
	<div id="showProcessImage"  title="浏览流程图片" >
    		 <img id="pdDiv"  />	
    </div>
	
	 <table class="table table-bordered table-striped table-hover">
		<thead>
		<tr>
			<th>流程名称</th>
			<th>发起时间</th>
			<th>发起单位</th>
			<th>发起人</th>
			<th>结束时间</th> 
		</tr>
		</thead>
		<tbody>
					<c:forEach items="${model.historics}" var="process">
						<tr ondblclick="showProcessImage('${process.bizKey}')" style="cursor: pointer;">
							<td>${process.processName }</td>
							<td>${process.startDate }</td>
							<td>${process.startorgName}</td>
							<td>${process.startUserName}</td>
							<td>${process.endTime }</td>
						</tr>			
					</c:forEach>
		</tbody>
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
  </body>
</html>
