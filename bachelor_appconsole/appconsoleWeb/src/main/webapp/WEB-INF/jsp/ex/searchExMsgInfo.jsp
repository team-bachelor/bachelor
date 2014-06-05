<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
<html>
  <head> 
    <title>系统异常查询</title>
    <!-- bootstrap -->
	<link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
	<link href="<%=path %>/css/datetimepicker.css" rel="stylesheet">
    <link href="<%=path %>/css/datetimepicker.less" rel="stylesheet">
	<link type="text/css" href="<%=path %>/css/jquery-ui-1.10.0.custom.css" rel="stylesheet" />
	<link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">
	
	<script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
	<script src="<%=path %>/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script>
	<script src="<%=path %>/js/bootStrapCommon.js"></script>
	<script src="<%=path %>/js/bootstrap-datetimepicker.min.js"></script>
	<script src="<%=path %>/js/bootstrap-datetimepicker.zh-CN.js"></script>
	
	<!-- bootstrap --> 
	<!-- Ztree导入文件 -->
	<link rel="stylesheet" href="<%=path %>/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=path %>/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery.ztree.exedit-3.5.js"></script>
	<!-- Ztree导入文件 -->
	<script type="text/javascript">
		var ztreeObj; 
		var exArray = new Array();
        $(document).ready(function() {
        	//**** 模块 **//
        	$.fn.zTree.init($("#tree"), setting);	
			ztreeObj = $.fn.zTree.getZTreeObj("tree");
			 
		     //点击下拉按钮显示下拉列表
		    $("#choose").click(function(){
		    	showDropList();
		    });
			//点击输入框显示下拉列表
		    $("#position").val("").click(function(){
		    	showDropList();
		    });
			
		  //开始时间
			$('.exStartTime').datetimepicker({
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
			$('.exEndTime').datetimepicker({
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
			
			//显示添加框
		  	$('#exDescriptDialog').dialog({
		  	    autoOpen: false,
		  	    width: 500,
		  	    buttons: false  
		  	});
			
			//关闭窗口
			$("#exCancel").bind("click",function(ev){
				$("#ex_object_type").val("");
				$("#ex_info").val("");
				$('#exDescriptDialog').dialog("close");
			});
		      
            //初始化异常查询
            searchExMsgInfo();
        });
        //查询异常信息
        function searchExMsgInfo(){
        	var moduleId = ($('#position').val()!=""?$('#exModuleTypeId').val():"");
        	$.ajax({
    			url:"<%=path %>/ex/all.htm?layer="+$('#exTypeId').val()+"&exStartTime="+$('#exStartTime').val()+"&exEndTime="+$('#exEndTime').val()+"&moduleId="+moduleId,
    			type:"get",
    			dataType:"json", 
    			success:function(result){
    				var var_list = result.rows;
    				enumSearchData = var_list;
    				if(var_list!=null && var_list.length>0){
    					exArray = var_list;
    					var html = "";
    					for(var i=0;i<var_list.length;i++){
    						var obj = var_list[i];
    						html+= '<tr class="data" title="双击查看详细信息" ondblclick="evaluationUpdateWin(\''+obj.id+'\')" style="cursor: pointer;">';
    						html+='<td>'+(obj.msg!=null?(obj.msg.length>10?obj.msg.substring(0,10):obj.msg):"")+'</td>';
    						html+='<td>'+(obj.moduleName!=null?obj.moduleName:"")+'</td>';
    						html+='<td>'+(obj.traceName!=null?obj.traceName:"")+'</td>';
    						html+='<td>'+(obj.className!=null?(obj.className.length>10?obj.className.substring(0,10):obj.className):"")+'</td>'; 
    						html+='<td>'+(obj.occurTime!=null?obj.occurTime:"")+'</td>';
    						html+='<td>'+(obj.address!=null?obj.address:"")+'</td>';
    						html += "</tr>";
    					}
    					$("#exceptionTbody").html(html);
    				} else {
    					$("#exceptionTbody").html("<tr class=\"data\"><td colspan=\"7\"><div style=\"text-align:text;\">没有查询到数据，请重试查询条件.</div><td></tr>");
    				}				
    			}
    		});
        }
        
        function evaluationUpdateWin(id){
        	if(exArray!=null && exArray.length>0){
        		for(var i=0;i<exArray.length;i++){
        			if(exArray[i].id==id){
        				$("#ex_object_type").val(exArray[i].className!=null?exArray[i].className:"");
        				$("#ex_info").val(exArray[i].msg!=null?exArray[i].msg:"");
        				$('#exDescriptDialog').dialog("open");
        				break;
        			}
        		}
        	}
        	
        }
        
        function showDropList(){
	    	var cityInput = $("#position");
	    	var cityOffset = cityInput.offset();
	    	var topnum = cityOffset.top+cityInput.outerHeight();
	    	topnum = topnum + 2;
	    	$("#droplist").css({left: cityOffset.left + "px",top: topnum +"px"}) .show();
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
						url:"<%=path %>/ps/pp/all.htm"
			   	},
				edit: {
					enable: true,
					showRemoveBtn: false,
					showRenameBtn: false 
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
					beforeClick: zTreeBeforeClick,
					onAsyncSuccess: zTreeOnAsyncSuccess,
					onClick:zTreeOnClick
				}
			};
			function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
					var nodes = ztreeObj.getNodesByParam("id", $('#exModuleTypeId').val(), null);
		        	$('#position').val(nodes.name);
			};
						
			function zTreeBeforeClick(treeId, treeNode, clickFlag) {
					if(treeNode.nodeType != "project"){
							$("#position").val(treeNode.name);//保存信息
			         	    $('#exModuleTypeId').val(treeNode.id);//保存ID
			         	    hideDropList();
		         	 }
		    	 	//如果选中的是非叶子叶节点不回填
		     	   if(treeNode.children){
		     		   return false;
		     	   }
			};
						
			function zTreeOnClick(event,treeId,treeNode){
         	   $("#position").val(treeNode.name);//保存信息
         	   $('#exModuleTypeId').val(treeNode.id);//保存ID
         	   hideDropList();
			}
    </script>
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
  </head>
  
  <body>
  <div class="page-header">
		<h4>系统运行异常</h4>
	</div>
	<form class="form-inline" id="noticeFormId">
		<div>
			<span class="label" >异常开始时间:</span>
					<div class="controls input-append date exStartTime" data-link-field="dtp_input1">
						 <input size="16" type="text" value="" readonly id="exStartTime">
						 <span class="add-on"><i class="icon-remove"></i></span>
						 <span class="add-on"><i class="icon-th"></i></span>
					</div>
			<span class="label">异常结束时间:</span>
					<div class="controls input-append date exEndTime" data-link-field="dtp_input1">
						 <input size="16" type="text" value="" readonly id="exEndTime">
		                 <span class="add-on"><i class="icon-remove"></i></span>
						 <span class="add-on"><i class="icon-th"></i></span>
					</div>
			<span class="label">异常:</span>
					<select style="width: 80px;" id="exTypeId">
		 				<option value="">全部</option>
		 				<option value="1">web</option>
		 				<option value="2">dao</option>
		 				<option value="3">service</option>
		 				<option value="4">util</option>
		 				<option value="5">timer</option>
		 				<option value="6">webService</option>
		 			</select>
		 	<span class="label"  style="float: left;margin-top: 6px;">模块:</span>
	                <div class="controls" style="float: left;">
		    				<span>
								<input type="hidden"   id="exModuleTypeId"/>
				                <input type="text" id="position" >
				                <span id="choose" class="om-combo-trigger"></span>
				            </span>
				            <div id="droplist" style="width: 220px;">
							    <ul id="tree" style="text-align:left;width: 210px;" class="ztree"></ul>
							</div> 
		  			</div>
		</div>
		<div style="padding-top: 20px;height: 40px;">
			<div style="float: left;">
				<button id="searchNotice" type="button" class="btn btn-info" onclick="searchExMsgInfo()">查询</button>		 
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
			<th>异常信息</th>
			<th>模块</th>
			<th>异常</th>
			<th>异常对象类型</th>
			<th>异常发生时间</th>
			<th>登录人</th>
		</tr>
		</thead>
		<tbody id="exceptionTbody"></tbody>
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
	<div id="exDescriptDialog" title="异常信息">
 		<div class="control-group">
 			<label class="control-label" for="update_name" >异常对象类型</label>
 			<div class="controls">
   				<input class="span6" type="text" name="className"  id="ex_object_type" readonly="readonly">
 			</div>
 			<label class="control-label" for="info" >异常信息</label>
 			<div class="controls">
   				<textarea rows="15"  id="ex_info" style="width: 450px;" readonly="readonly"></textarea> 
 			</div>
		</div>
		<div class="row">
			<div style="margin-left: 20px;" >
				<button id="exCancel" type="button" class="btn btn-danger">关闭</button>
			</div> 
		</div>  
 </div>
  </body>
</html>
