<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通知服务</title>
<!-- bootstrap -->
<link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="<%=path %>/css/jquery-ui-1.10.0.custom.css" rel="stylesheet" />
<link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">
<script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
<script src="<%=path %>/js/bootstrap.js"></script>
<script src="<%=path %>/js/bootstrap-popover.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui-1.10.0.custom.min.js" ></script>
<script src="<%=path %>/js/bootStrapCommon.js"></script>
<script src="<%=path %>/js/common.js"></script>
<!-- bootstrap --> 
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
<script type="text/javascript">
		var enumSearchData; 
		var roleSearchData;
        $(document).ready(function() {
        	enumSearchData = new Array();
        	roleSearchData = new Array();
        	
            //查询信息
            $('#searchNotice').click(function(){
            	initGrid();
            });
            //加载通知状态
            initEnumData("<%=path %>/gv/enum/findByEnumNameTV.htm?enumName=BMP_STATUS","search_notice_status","enum",true,enumSearchData,null);
         	 //加载角色信息
            initEnumData("<%=path %>/auth/role/queryRole.htm","search_notice_role","role",true,roleSearchData,null);
            //初始化表格
            initGrid();
        });
         
        function initGrid(){
        	$.ajax({
    			url:"<%=path %>/bpm/notice/all.htm",
    			type:"get",
    			dataType:"json",
    			data:$('#noticeFormId').serialize(),
    			success:function(result){
    				var var_list = result.rows;
    				enumSearchData = var_list;
    				if(var_list!=null && var_list.length>0){
    					var html = "";
    					for(var i=0;i<var_list.length;i++){
    						var obj = var_list[i];
    						html+= '<tr class="data">';
    						html+='<td>'+isEmpty(obj.roleName)+'</td>';
    						html+='<td>'+isEmpty(obj.recevierUserName)+'</td>';
    						html+='<td>'+obj.title+'</td>';
    						html+='<td>'+charsSubstring(obj.content)+'</td>'; 
    						html+='<td>'+(evaluationSelection(enumSearchData,"search_notice_status",obj.status,false,true))+'</td>';
    						html+='<td>'+obj.createTime+'</td>';
    						html+='<td>'+isEmpty(obj.readName)+'</td>'; 
    						html+='<td>'+isEmpty(obj.doneName)+'</td>';
    						html += "</tr>";
    					}
    					$("#noticeTbody").html(html);
    				} else {
    					$("#noticeTbody").html("<tr class=\"data\"><td colspan=\"8\"><div style=\"text-align:text;\">没有查询到数据，请重试查询条件.</div><td></tr>");
    				}				
    			}
    		});
        } 
</script>


</head>
<body > 
	<div class="page-header">
		<h4>通知服务</h4>
	</div>
	<form class="form-inline" id="noticeFormId">
		<div>
			<span class="label">角色:</span>
			<select class="input-large"  id="search_notice_role" name="roleId"> </select>
			<span class="label">通知接收人:</span>
			<input type="text" class="span3" placeholder="人员" id="search_notice_man" name="recevierUserName">
			<span class="label">通知状态:</span>
			<select class="input-large"  id="search_notice_status" name="status"> </select>
		</div>
		<div style="padding-top: 20px;height: 40px;">
			<div style="float: left;">
				<button id="searchNotice" type="button" class="btn btn-info">查询</button>		 
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
			<th>角色</th>
			<th>通知接收人</th>
			<th>通知标题</th>
			<th>通知内容</th>
			<th>通知状态</th>
			<th>创建时间</th>
			<th>已阅者</th>
			<th>已办者</th>
		</tr>
		</thead>
		<tbody id="noticeTbody"></tbody>
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