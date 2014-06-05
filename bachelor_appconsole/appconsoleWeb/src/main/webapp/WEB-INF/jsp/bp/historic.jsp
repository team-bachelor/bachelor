<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>流程监控</title>
   <!-- bootstrap -->
	<link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
	<link type="text/css" href="<%=path %>/css/jquery-ui-1.10.0.custom.css" rel="stylesheet" />
	<link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">
	<script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
	<script src="<%=path %>/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script>
	<script src="<%=path %>/js/bootStrapCommon.js"></script>
	
	<script src="<%=path %>/js/common.js"></script>
	<!-- bootstrap -->    
	<link href="<%=path %>/css/datetimepicker.css" rel="stylesheet">
	<script src="<%=path %>/js/bootstrap-datetimepicker.min.js"></script>
	<script src="<%=path %>/js/bootstrap-datetimepicker.zh-CN.js"></script>
  </head>
  <style>
	   	 html,
		 body {
		   height: 90%;
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
		var processInstanceArray = new Array();
		var processType = "0";//0是增加候选人，1是指定执行者
		$(document).ready(function() {
				//开始时间
				$('.form_date').datetimepicker({
					format:'yyyy-mm-dd hh:ii:ss',
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
					format:'yyyy-mm-dd hh:ii:ss',
			        language:  'zh-CN',
			        weekStart: 1,
			        todayBtn:  1,
					autoclose: 1,
					todayHighlight: 1,
					startView: 2,
					minView: 2,
					forceParse: 0
			    });
			    //全选
        		selectAllItem("mutilSelectItem","idCheckBox");
			     
			    //删除流程
			     $("#delPiBtn").bind("click",function(ev){
			    	var piObj = showSelectInfoDialog();
			    	if(piObj==null || piObj==""){
			    		
			    		return ;
			    	}
			    	$('#DeletePiDialog').dialog("open");
			    });
			    //初始化删除窗口
			    initDelDialog();
			    //初始化数据 
			    loadProcessInstanceData();
			    //*** 初始化详细页面 **/
			    initDialogInfo("ProcessInstanceDescriptDialog");
			  	 //*** 初始化详细页面 **/
			  	initDialogInfo("UserInfoDescriptDialog");
			  	//关闭详细窗口
			  	closeDialog("exCancel","ProcessInstanceDescriptDialog");
			  	//关闭候选人窗口
			  	closeDialog("UICancel","UserInfoDescriptDialog");
			  	//初始化流程名称，下拉框
			  	initPdNameSelect();
			  	//查询所有二级单位 
			  	initSecondUnit();
	    });
	    
	     //初始化删除窗口
	    function initDelDialog(){
	    	$('#DeletePiDialog').dialog({
				    autoOpen: false,
				    modal: true,
				    buttons: {
				    	 	"Ok": function () {
				    	 		var processInstanceId = showSelectInfoDialog();
				    	 		 
				    	 		if( processInstanceId==null || processInstanceId==""){
				    	 			$("#messageBoxAlertId").show();
									$("#messageBoxAlert").html("请选择要操作流程信息.");
									return ;
				    	 		}
				    	 		/** 关闭理由窗口 **/
				    	 		$('#DeletePiDialog').dialog("close");
				    	 		//*** 删除流程信息 ***//
				    	 		delProcessInfo(processInstanceId);
					        },
					        "Cancel": function () {
					            $(this).dialog("close");
					        }
				    }
				});
	    }
	    
	    //删除流程信息
	    function delProcessInfo(piId){
	    	$.ajax({
   				url:"<%=path %>/bp/deleteHisBp.htm",
	    				type:"get",
	    				dataType:"json",
	    				data:"piId="+piId,
	    				success:function(result){
	    					$("#messageBoxAlertId").show();
	    					 if(result.ResultCode=="0"){
								$("#messageBoxAlert").html("流程信息删除成功.");
	    					 } else {
	    					 	$("#messageBoxAlert").html("流程信息删除失败.");
	    					 }
	    					  //初始化数据 
		    				loadProcessInstanceData();
	    					 $("DeletePiDialog").dialog("close");
	    				}
   			});
	    }
	    
	    //查询所有二级单位 
        function initSecondUnit(){
        	$.ajax({
	    	 		url:"<%=path %>/org/secondUnit.htm?id=0600000000",
	    	 		type:"get",
	    	 		dataType:"json", 
	    	 		success:function(result){ 
	    	 			 secondCompanyArray = result;
	    	 			 if(result!=null && result.length>0){
	    	 				evaluationSelectionForPd(result,"companySelectId",null,"org");
	    	 			 }
	    	 		}
    	 	});
        }
        
       	function evaluationSelectionForPd(result,id,initVal,type){
    		var authObj = document.getElementById(id);
    		if(authObj!=null && authObj.length<=0){
			    authObj.options.remove(0,1);
			    authObj.options.add(new Option("全选",""));
	    		for (var i=0;i<result.length;i++){
			 		var obj = result[i];
			 		if(type=="role"){
			 			authObj.options.add(new Option(obj.memo+"("+obj.name+")",obj.id));
			 		} else if(type == "task"){ 
			 			authObj.options.add(new Option(obj.name,obj.key));
			 		} else {
			 			authObj.options.add(new Option(obj.name,obj.id));
			 		}
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
	    
	    //初始化流程名称，下拉框
	    function initPdNameSelect(){
	    	$.ajax({
   				url:"<%=path %>/bp/queryPds.htm",
	    				type:"get",
	    				dataType:"json",
	    				success:function(result){
	    					 //渲染流程名称下拉框
							evaluationSelection(result,"searchPdId","pd",null,true,true);
	    				}
   			});
	    }
	    
	    //初始化流程节点，下拉框
	    function initTaskSelect(pdid){
	    	if(pdid==null || pdid==""){
	    		removeDefindedNode();
	    		return ;
	    	}
	    	removeDefindedNode();
	    	$.ajax({
   				url:"<%=path %>/bp/queryTasks.htm",
	    				type:"get",
	    				dataType:"json",
	    				data:"pdId="+pdid,
	    				success:function(result){
	    					//渲染流程名称下拉框
							evaluationSelectionForPd(result,"searchTaskId",null,"task");
	    				}
   			});
	    }
	    
	    function removeDefindedNode(){
	    		/** 初始化流程节点 **/
	    		var taskOptions = document.getElementById("searchTaskId");
	    		if(taskOptions!=null && taskOptions.length>0){
	    			for(var i=0;i<taskOptions.length;i++){
	    				taskOptions.remove(0);
	    				i=0;
	    			}
	    			taskOptions.remove(0);
	    		}
	    }
	     
	    //查询用户信息
	    function searchUserInfo(userId,tbodyId){
	    	$.ajax({
	    				url:"<%=path %>/user/all.htm",
			    				type:"get",
			    				dataType:"json",
			    				data:"id="+$("#"+userId).val(),
			    				success:function(result){
			    					if(result.total>0){
				    						var html = "";
				    						for(var i=0;i<result.rows.length;i++){
				    							var obj = result.rows[i];
				    							html+= '<tr class="data">';
					    						html+='<td><input type="radio" value="'+obj.id+'" name="idRadio"/>&nbsp;'+obj.id+'</td>';
					    						html+='<td> '+ isEmpty(obj.username,10,"...")+'</td>';
					    						html+='<td title="'+obj.ownerOrg.namePath+'"> '+ isEmpty(obj.ownerOrg.namePath,22,"...")+'</td>';
					    						html += "</tr>";
				    						}
		  										$("#"+tbodyId).html(html);
				    				} else {
				    					$("#"+tbodyId).html("<tr class=\"data\"><td colspan=\"4\"><div style=\"text-align:text;\">没有查询到数据，请重试查询条件.</div><td></tr>");
				    				}	
			    				}
	    			});
	    }
	    
	    function closeDialog(btId,dialogId){
	    	$("#"+btId).bind("click",function(ev){
		    	$("#messageBoxAlertId").hide();
		    	$('#'+dialogId).dialog("close");
		    });
	    }
	    
	    function initDialogInfo(dialogId){
	    	$('#'+dialogId).dialog({
		  	    autoOpen: false,
		  	    width: 560,
		  	    buttons: false  
		  	});
	    }
	    
	    function showSelectInfoDialog(){
	    	var selectPiVals = getCheckBoxValue("idCheckBox");
	    	if(selectPiVals==null || selectPiVals.length<=0){
	    		$("#messageBoxAlertId").show();
				$("#messageBoxAlert").html("请选择要操作流程信息.");
				return ;
	    	}  
	    	
	    	if(selectPiVals.length>1){
	    		$("#messageBoxAlertId").show();
				$("#messageBoxAlert").html("操作流程信息时，不能同时选择多条.");
				return ;
	    	}
	    	
	    	return selectPiVals[0];
	    }
	    
	    //** 加载表格数据 **///
	    function loadProcessInstanceData(){
	    	/** 隐藏操作提示信息 **/
	    	$("#messageBoxAlertId").hide();
	    	$.ajax({
	    				url:"<%=path %>/bp/historicBp.htm",
	    				type:"get",
	    				dataType:"json",
	    				data:$("#searchBpForm").serialize(),
	    				success:function(result){
	    					var process_list = result;
	    					processInstanceArray = process_list;
		    				if(process_list!=null && process_list.length>0){
		    					var html = "";
		    					for(var i=0;i<process_list.length;i++){
		    						var obj = process_list[i];
		    						html+= '<tr class="data" ondblclick="evaluationUpdateWin(\''+obj.processInstanceId+'\')" style="cursor: pointer;">';
		    						html+='<td><input type="checkbox" value="'+obj.processInstanceId+'" name="idCheckBox"/></td>';
		    						html+='<td>'+isEmpty(obj.processName,12,"...")+'</td>'; 
		    						html+='<td>'+isEmpty(obj.startorgName,24,"...")+'</td>';
		    						html+='<td>'+isEmpty(obj.startUserName,24,"...")+'</td>';
		    						html+='<td>'+isEmpty(obj.startDate,24,"...")+'</td>';
		    						html += "</tr>";
		    					}
		    					$("#enumTbody").html(html);
		    				} else {
		    					$("#enumTbody").html("<tr class=\"data\"><td colspan=\"7\"><div style=\"text-align:text;\">没有查询到数据，请重试查询条件.</div><td></tr>");
		    				}	
	    				},
	    				error:function(){
	    						$("#enumTbody").html("<tr class=\"data\"><td colspan=\"7\"><div style=\"text-align:text;\">历史流程查询异常，请与管理员联系.</div><td></tr>");
	    				}
	    	});
	    }
	    
	     /** 赋值更新页面 **/
    	function evaluationUpdateWin(id){
    			var obj = searchDataItem(id,processInstanceArray,"processInstanceId");
    			$("#processInstanceName").html(obj.processName);
    			var html = "";
   				if(obj.gds!=null && obj.gds.length>0){
   					for(index in obj.gds){
   						html += "<tr class=\"data\">";
	   						html+="<td>"+isEmpty(obj.gds[index].startTime,30,"...")+"</td>";
	   						html+="<td>"+isEmpty(obj.gds[index].title,24,"")+"</td>";
	   						html+="<td>"+isEmpty(obj.gds[index].assigneeUnit,24,"")+"</td>";
	   						html+="<td>"+isEmpty(obj.gds[index].assigneeName,24,"")+"</td>";
	   						html+="<td>"+isEmpty(obj.gds[index].candidateName,24,"")+"</td>";
   						html+="</tr>";
   					}
   				}else {
   					html+="<td colspan=\"6\"><div style=\"text-align:text;\">没有查询到数据，请重试查询条件.</div><td>";
   				}
    			$("#piExectionTbody").html(html);
		    	$('#ProcessInstanceDescriptDialog').dialog("open");
    	}
    	
    	/** 得到下拉框值，当下拉框改变选项时 **/
    	function getValueBychangeItem(inputId,value){
    		$("#"+inputId).val(value);
    	}
	</script>
  <body>
     <div class="page-header" >
		<h4>历史流程</h4>
	</div>
	<form class="form-inline" id="searchBpForm">
		<input type="hidden" id="searchProcessDefinitionId" name="processDefinitionId"/>
		<input type="hidden" id="searchTaskExId" name="taskId"/>
		<input type="hidden" id="searchStartOrgId" name="startOrgId"/>
		<div>
			<span class="label">流程名称:</span>
			<select class="input-large"   id="searchPdId" onchange="initTaskSelect(this.value),getValueBychangeItem('searchProcessDefinitionId',this.value)"> </select>
			<span class="label">当前节点:</span>
			<select class="input-large"  id="searchTaskId" onchange="getValueBychangeItem('searchTaskExId',this.value)"> </select>
			<span class="label">发起单位:</span>
			<select id="companySelectId" onchange="getValueBychangeItem('searchStartOrgId',this.value)"> </select>
			<br/><br/>
			<span class="label">启动日期:</span>
			<span class="label" style="text-align: center;">从</span>
            <div class="controls input-append date form_date" data-link-field="dtp_input1">
                 <input size="16" type="text" value="" readonly id="stratTime" name="stratTime">
                 <span class="add-on"><i class="icon-remove"></i></span>
				 <span class="add-on"><i class="icon-th"></i></span>
            </div>
            <span class="label" style="width: 52px;text-align: center;">&nbsp;&nbsp;到&nbsp;&nbsp;</span>
			<div class="controls input-append date form_date1" data-link-field="dtp_input1">
                 <input size="16" type="text" value="" readonly id="endTime" name="endTime">
                 <span class="add-on"><i class="icon-remove"></i></span>
				 <span class="add-on"><i class="icon-th"></i></span>
            </div>
		</div>
		<div style="padding-top: 20px;height: 40px;">
			<div style="float: left;">
				<button id="del" type="button" class="btn btn-primary" 
								onclick="loadProcessInstanceData()">查&nbsp;&nbsp;询</button>
				<button id="delPiBtn" type="button" class="btn btn-danger">删除流程</button>
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
	<table class="table table-bordered table-striped table-hover">
		<thead>
		<tr>
			<th><input type = "checkbox" id="mutilSelectItem"  name = "mutilSelectItem" />&nbsp;&nbsp;选择</th>
			<th>流程名称</th> 
			<th>发起单位</th>
			<th>发起人</th>
			<th>发起时间</th> 
		</tr>
		</thead>
		<tbody id="enumTbody"></tbody>
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
	
	<div id="ProcessInstanceDescriptDialog" title="流程详细信息">
	 		<div >
 					<label class="label" for="update_name"  >流程名称：</label>
 					<label class="label label-info " id="processInstanceName" ></label>
			</div>
			<div>
				<h6>流程执行信息</h6>
				<table class="table table-bordered table-striped table-hover">
					<thead>
						<tr>
							<th>开始时间</th>
							<th>执行节点名称</th>
							<th>执行单位</th>
							<th>执行人</th>
							<th>受理人</th>
						</tr>
					</thead>
					<tbody id="piExectionTbody"></tbody>
				</table>
			</div>
			<div  class="page-header">
				<h6>流程数据信息</h6>
				<table class="table table-bordered table-striped table-hover">
					<thead>
					<tr>
						<th>ID</th>
						<th>名称</th>
						<th>值</th>
					</tr>
					</thead>
					<tbody id="piDataTbody"></tbody>
				</table>
			</div>
			<div class="row">
				<div style="margin-left: 20px;" >
					<button id="exCancel" type="button" class="btn btn-danger">关闭</button>
				</div> 
			</div>  
 </div>
 
 <div id="DeletePiDialog" title="删除流程" >
			确定删除流程信息.
 </div>
  </body>
</html>
