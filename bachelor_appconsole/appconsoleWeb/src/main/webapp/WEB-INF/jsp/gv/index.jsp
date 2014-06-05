<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>全局变量维护</title>
<link href="<%=path %>/css.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/default/om-default.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=path %>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/operamasks-ui.js"></script>

<script type="text/javascript">
        $(document).ready(function() {
            $('#grid').omGrid({
                title : '全局变量维护',
                width:800,
                height:400,
                colModel : [
		                      	{header:"名称",name:"name",width:200,editor:{rules:["required",true,"名称是必填的"],type:"text",editable:true,name:"name"}},
							  	{header:"描述",name:"description",width:200,editor:{rules:["required",true,"描述是必填的"],type:"text",name:"description",editable:true}},
							  	{header:"值",name:"value",width:200,editor:{rules:["required",true,"值是必填的"],type:"text",name:"value",editable:true}}
							],
				dataSource : "<%=path %>/gv/queryAll.htm",
                onAfterEdit:function(rowIndex , rowData){
                	updateGv(rowIndex, rowData);
                }
            });

            $('#add').click(function(){
            	$('#grid').omGrid('insertRow',0);
            });

            $('#del').click(function(){
            	var dels = $('#grid').omGrid('getSelections', true);
            	if(dels.length <= 0 ){
            		alert('请选择删除的记录！');
            		return;
            	}
            	//调用后台删除数据
            	del(dels[0]);

            });

            $('#reload').click(function(){
            	$('#grid').omGrid('reload');
            });
        });
        
        function updateGv(rowIndex, rowData){
        	$.ajax({
    	 		url:"update.htm",
    	 		contentType:"application/json",
    	 		type:"post",
    	 		dataType:"json",
    	 		data:JSON.stringify(rowData),
    	 		success:function(result){ 
  	 				$.omMessageBox.alert({
	 					content:"修改成功!"
	 				});
	 				$('#grid').omGrid('saveChanges');
	 				$('#grid').omGrid('reload');
    	 		},
        		error:function(){
        			$.omMessageBox.alert({
	 					content:"修改失败!"
	 				});
        		}
    	 	});
        }
        
        function del(rowData){
        	$.ajax({
    	 		url:"delete.htm",
    	 		contentType:"application/json",
    	 		type:"post",
    	 		dataType:"json",
    	 		data:JSON.stringify(rowData),
    	 		success:function(result){ 
  	 				$.omMessageBox.alert({
	 					content:"删除成功!"
	 				});
  	            	$('#grid').omGrid('reload');
    	 		},
        		error:function(){
        			$.omMessageBox.alert({
	 					content:"删除失败!"
	 				});
        		}
    	 	});
        }

</script>


</head>
<body>
<div style="margin-left:30px;margin-top:20px;">
    <input type="button" id="add" value="新增"/>
    <input type="button" id="del" value="删除"/>
    <input type="button" id="reload" value="刷新"/>
	<table id="grid"></table>
</div>

<br/>
</body>
</html>