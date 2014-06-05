<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>系统运行效率统计</title>
    <link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
    <link href="<%=path %>/css/datetimepicker.css" rel="stylesheet">
    <link href="<%=path %>/css/datetimepicker.less" rel="stylesheet">
    <link type="text/css" href="<%=path %>/css/jquery-ui-1.10.0.custom.css" rel="stylesheet" />
    
	<script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
	<script src="<%=path %>/js/bootstrap.js"></script>
	<script src="<%=path %>/js/bootstrap-datetimepicker.min.js"></script>
	<script src="<%=path %>/js/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="<%=path %>/js/FusionCharts.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script>
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

    </style>
    <link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">
 
  </head>

  <body>
	<div class="page-header">
		<h4>系统运行效率</h4>
	</div>
	<form class="form-inline">
		<div>
			<span class="label">日期范围:</span>
			从&nbsp;&nbsp;
            <div class="controls input-append date form_date" data-link-field="dtp_input1">
                 <input size="16" type="text" value="" readonly id="startTime">
                 <span class="add-on"><i class="icon-remove"></i></span>
				 <span class="add-on"><i class="icon-th"></i></span>
            </div>
			到&nbsp;&nbsp;
			<div class="controls input-append date form_date1" data-link-field="dtp_input1">
                 <input size="16" type="text" value="" readonly id="endTime">
                 <span class="add-on"><i class="icon-remove"></i></span>
				 <span class="add-on"><i class="icon-th"></i></span>
            </div>
		</div>
		<div style="padding-top: 20px;height: 40px;">
			<div style="float: left;">
				<button id="queryBtn" type="button" class="btn btn-info">查询</button>		
			</div>
			<div  style="float: right;display: none;" id="messageBoxAlertId" >
				<div class="alert" style="width:280px;" >
				    <strong id="messageBoxAlert"></strong> 
				</div>
			</div>
		</div>
	</form>
	
	<div id="chartdiv" align="center">chart load...</div>
	<script type="text/javascript"><!--
	$(document).ready(function() {
		//开始时间
		$('.form_date').datetimepicker({
			format:'yyyy-mm-dd',
	        language:  'zh-CN',
	        weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0
	    });
		//结束时间
		$('.form_date1').datetimepicker({
			format:'yyyy-mm-dd',
	        language:  'zh-CN',
	        weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0
	    });
		//查询
		$('#queryBtn').click(function(e){
			loadChartInfo();
			return false;
		});
		//加载信息
		loadChartInfo();
	});
	//加载信息
	function loadChartInfo(){
		$.ajax({
				url:"<%=path %>/stat/systemRun.htm?startTime="+$('#startTime').val()+"&endTime="+$('#endTime').val(),
				type:"get",
				dataType:"JSON",
				success:function(result){
					showChartInfo(result);
				},
				error:function(er){
					$('#loadModel').dialog("close");
				}
		});
		
	}
	
	//显示修改页面
	function showChartInfo(jsonData){
		var chart = new FusionCharts('<%=path %>/FusionChart/Column3D.swf', "ChartId", "650", "300", "0", "0");
		FusionCharts.debugMode.enabled(true);
        chart.setJSONData(jsonData.json);
        chart.render("chartdiv");
	}
	--></script>

  </body>
</html>