<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模块属性维护</title>
<link href="<%=path %>/css/main.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/validate/validate.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/default/om-default.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=path %>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/operamasks-ui.js"></script> 
<!-- bootstrap -->
<link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
<link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">
<!-- bootstrap --> 
<style>
    html, body{ width: 100%; height: 100%; padding: 0; margin: 0;overflow: hidden;}
    
    .input_text {
	    border: 1px solid #6D869E;
	    height: 17px;
	    vertical-align: middle;
	    width: 50%;
	}
	label.error{
        background: #fff6bf url(<%=path %>/images/validate/alert.png) center no-repeat;
		background-position: 5px 50%;
		text-align: left;
		padding: 2px 20px 2px 25px;
		border: 1px solid #ffd324;
		display: none;
		width: 200px;
		margin-left: 10px;
   }
     #droplist{
        display:none; 
        position: absolute; 
        width:158px;
        /* 兼容IE6,7 */
        *width:154px;
        /* 兼容IE8 */
        width:162px\0;
        height:100px;
        border:1px solid;
        overflow: auto;
        background-color: #e3e9ef;
     } 
</style>
<script type="text/javascript">
//定义全局的变量，给后台调用前台js留句柄
var validator;

$(document).ready(function() {
	//*** 选项卡 **//
	 $('#make-tab').omTabs({
            width: 550,
            height: 400,
            tabWidth : 65,
            onActivate : function(n,event) {
            		var indexName = ("grid"+(n+1));
                   	createGridObject((n+1),indexName,"<%=path %>/ps/pme/all.htm?flag="+(n+1)+"&moduleId="+$('#modelId').val());
                }
        });
    /** 下拉菜单 **/
    //****项目主业务数据 **//
	$("#tree").omTree({
	         dataSource : "<%=path %>/ps/bd/all.htm",
	         onSuccess:function(data){
	         	  //var ndata = cyclingPdInfo(data,$('#ppbdId').val()), text = cyclingPdInfo(data,$('#ppbdId').val());
		        	//$('#position').val(text);
		        	if($('#ppbdId').val()!=null && $('#ppbdId').val()!=""){
			        	var target = $('#tree').omTree('findNode', "id", $('#ppbdId').val(), "",true);
			        	var parentObj = $('#tree').omTree('getParent',target); 
			        	$('#position').val((parentObj==null?target.text:(parentObj.text+'-'+target.text)));
			        }
		      },
	         onClick:function(nodedata,event){
	         	   var ndata = nodedata, text = ndata.text;
	     		   ndata = $("#tree").omTree("getParent",ndata);
	         	   while(ndata){
	         		   text = ndata.text +"-" +text;
	         		   ndata = $("#tree").omTree("getParent",ndata);
	         	   }
	         	   $("#position").val(text);//保存信息
	         	   $('#ppbdId').val(nodedata.id);//保存ID
	         	   hideDropList();
	         },
	        onBeforeSelect: function(nodedata){
	     	   //如果选中的是非叶子叶节点不回填
	     	   if(nodedata.children){
	     		   return false;
	     	   }
	        }
     });
	//点击下拉按钮显示下拉列表
    $("#choose").click(function(){
    	showDropList();
    });
	//点击输入框显示下拉列表
    $("#position").val("").click(function(){
    	showDropList();
    });
        
    //** 初始化表格 **//
    createGridObject("1","grid1","<%=path %>/ps/pme/all.htm?flag=1&moduleId="+$('#modelId').val()); 
	//*************************下面是校验代码主体***********************************************
	validator = $("#form1").validate({
		rules : {
			name : {
	            required : true,
	            minlength : 2,
	            maxlength : 128
	        },
	        description : {
	            required : true,
	            minlength : 2,
	            maxlength : 128
	        },
	        type : {
	            required : true 
	        }        
		},
		messages : {
			name : {
	            required : "请输入模块名称",
	            minlength : "模块名称至少是2为",
	            maxlength : "模块名称最多是128位"
	        },
	        description : {
	            required : "请输入模块描述",
	            minlength : "模块描述至少是2为",
	            maxlength : "模块描述最多是128位"
	        },
	        type : {
	            required : "请输入模块类型" 
	        } 
		}
	});
//*************************下面是Ajax提交*********************
	$("#resetBtn").bind("click",function(ev){
		//重置表单
		$("#form1")[0].reset();
		$("#messageBoxAlertId").hide();
	});
	var options = {
		beforeSubmit : beforeSubmitHandler,
    	success : successHandler
	};
	function beforeSubmitHandler(formData, jqForm, options) {
         return true;
    }

    function successHandler(responseText, statusText, xhr, $form) {
    	if(responseText.resultCode == "0"){
			alert("保存成功.");
			refreshParentTreeInfo(responseText.resultId);
			//切换到修改状态
			$('#modelId').val(responseText.resultId);
		}else{
			alert("保存失败.");
		}

    }
 	
    $("#submitBtn").bind("click",function(ev){
    	$("#messageBoxAlertId").hide();
    	$.ajax({
        	url:"<%=path %>/ps/pm/createAndUpdate.htm",
        	type:"post",
        	dataType:"json",
        	data:$("#form1").serialize(),
        	success:function(result){
        		$("#messageBoxAlertId").show();
        		if(result.resultCode == "0"){
        			$("#messageBoxAlert").html("保存成功.");
        			refreshParentTreeInfo(result.resultId);
        			//切换到修改状态
        			$('#modelId').val(result.resultId);
        			//** 更新节点名称 **//
					window.parent.updateProjectNode($('#modelId').val(),$('#modelName').val());
        		}else if(result.resultCode == "2"){
        			$("#messageBoxAlert").html("业务编码不能重复，请更改合理的业务编码.");
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

});
//加载表格
function createGridObject(type,name,url){
	//** 判断表格是否存在 **//
	var store = $('#'+name).omGrid('getData');
	if(store.total == undefined || store.total == "undefined"){
			//*****表格信息 ***//
		 	$('#'+name).omGrid({
				             limit:16, 
				             width:500,
				             height : 300,
				             colModel : [
				                     {header:"扩展信息",name:"value",width:180,editor:{rules:["required",true,"扩展信息"],type:"text",editable:true,name:"value"}},//虽然提供了editor属性，但提供了editable的话(没有则可以编辑)，只有editable=true或者
				                         //当  editable为一个函数并返回非false时，才可以进行编辑。
								  {header:"扩展信息描述",name:"description",width:250,editor:{rules:["required",true,"扩展信息描述"],type:"text", name:"description" }}],
						dataSource : url,
						onAfterEdit:function(rowIndex , rowData){
							savePmeInfo(rowData,type);
						} 
		     });	
    } else {
    	$('#'+name).omGrid('reload');
   }
}
//新增信息
function insertGridInfo(index){
	if($('#modelId').val()!=null && $('#modelId').val()!=""){
		$('#'+index).omGrid('insertRow',0);
	} else {
		alert("请先添加模块信息");
	}
}

//保存信息
function savePmeInfo(rowData,index){
	$.ajax({
			url:"<%=path %>/ps/pme/update.htm?moduleId="+$('#modelId').val()+"&value="+escape(encodeURIComponent(rowData.value))+"&description="+escape(encodeURIComponent(rowData.description))+"&flag="+index+"&id="+rowData.id,
			type:"post",
			dataType:"json",
			success:function(json){
				if(json.result=="success"){
					alert("修改成功.");
				} else {
					alert("修改失败.");
				}
				$('#grid'+index).omGrid('reload');//刷新当前页
			}
	});
}

//删除信息
function deletePmeInfo(index){
	var dels = $('#grid'+index).omGrid('getSelections', true);
  	if(dels.length <= 0 ){
  		alert('请选择删除的记录！');
  		return;
  	}
  	$.omMessageBox.confirm({
        title:'确认删除',
        content:'该模块扩展信息都将被删除，确认删除？',
        onClose:function(result){
            if(result){
					$.ajax({
							url:"<%=path %>/ps/pme/delete.htm?id="+dels[0].id,
							type:"post",
							dataType:"json",
							success:function(json){
								if(json.result=="success"){
									alert("删除成功.");
								} else {
									alert("删除失败.");
								}
								$('#grid'+index).omGrid('reload');//刷新当前页
							}
					});
			}
	 }});
}
function showDropList(){
	    	var cityInput = $("#position");
	    	var cityOffset = cityInput.offset();
	    	var topnum = cityOffset.top+cityInput.outerHeight();
	    	if($.browser.msie&&($.browser.version == "6.0"||$.browser.version == "7.0")){
	    		topnum = topnum + 2;
	    	}
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
	
	function refreshParentTreeInfo(id){
		if($('#modelId').val()==null || $('#modelId').val()=="" || $('#modelId').val()==undefined){
			  var nodeObj = {
	    	 	    			id:id,
	    	 	    			name: $('#modelName').val(), 
	    	 	    			description:$("#modelDescription").val,
	    	 	    			hasChildren: false,
	    	 	    			nodeType: "module"
	    	 	    			};
				window.parent.reloadTreeInfo(nodeObj,$('#modelParentModuleId').val());
	   }
	}
</script>

</head>
<body> 
<div class="page-header  text-center">
	<h4>模块属性维护</h4>
</div>
<div>
	<div style="margin-left: 10px;">
		<div>
			<h6>模块基本信息</h6>
		</div>
		<div>
			<form id="form1" method="post" >
				<form:hidden path="model.parentModule.id" id="modelParentModuleId"/>
				<table width="70%" border="0" class="grid_layout" cellspacing="0" align="center" >
					<tr>
						<td align="left">模块ID:</td>
						<td>
							<form:input path="model.id"  cssStyle="input_text" id = "modelId" readonly="true"/>
							<span style="color: red;font-size: 13px;">&nbsp;*</span>
						</td>
					</tr>
					<tr>
						<td align="left">业务编码:</td>
						<td>
							<form:input path="model.code"  cssStyle="input_text" id = "modelId" />
						</td>
					</tr>
					<tr>
						<td align="left">模块名称:</td>
						<td>
							<form:input path="model.name"  cssStyle="input_text" id = "modelName"/>
							<span style="color: red;font-size: 13px;">&nbsp;*</span>
						</td>
					</tr>
					<tr>
						<td align="left">模块描述:</td>
						<td>
							 <form:input path="model.description"  cssStyle="input_text" id="modelDescription"/><span style="color: red;font-size: 13px;">&nbsp;*</span>
							<span class="errorImg"></span><span	class="errorMsg"></span>			
						</td>
					</tr>
					<tr>
						<td align="left">模块类型:</td>
						<td> 
							 <form:select path="model.type" cssStyle="input_text">
							 	<form:option value="1">服务</form:option>
							 	<form:option value="2">定时任务</form:option>
							 	<form:option value="3">接口</form:option>
							 </form:select>
							 <span style="color: red;font-size: 13px;">&nbsp;*</span>
							<span class="errorImg"></span><span	class="errorMsg"></span>		
						</td>
					</tr> 		
				</table>
				<div class="row"> 
					<div class="text-center " style="width:280px;float: left;"> 
						<button id="submitBtn" type="button" class="btn btn-info">保  存</button>		
						<button id="resetBtn" type="button" class="btn btn-primary">重  置</button>
					</div> 
					<div  style="width:280px;float: right;display: none;" id="messageBoxAlertId" >
						<div class="alert alert-error" style="width:180px;" >
						    <strong id="messageBoxAlert"></strong> 
						</div>
					</div>
				</div>
			</form>
			</div>
		</div>
	<div style="margin-left: 10px;">
			<div>
				<div>
					<h6>模块扩展信息</h6>
				</div>
			</div>
			<div id="make-tab" style="margin-top:  10px;">
		         <ul>
		             <li> <a href="#tab1">表信息</a> </li>
		             <li> <a href="#tab2">视图信息</a> </li>
		             <li>  <a href="#tab3">序列信息</a> </li>
		             <li> <a href="#tab4">java包名</a> </li>
		             <li> <a href="#tab5">jsp路径信息</a></li>
		             <li>  <a href="#tab6">flash路径信息</a></li>
		         </ul>
		         <div id="tab1">
		             <div style="margin-left:10px;margin-top:2px">
		             	<div>
		             		<div>
		             			<div style="float: left;"><input type="button" value="新  增" style="width: 60px;"  onclick="insertGridInfo('grid1')"class="button_u" /></div>
		             			<div><input type="button" value="删   除" style="width: 60px;margin-left: 5px;"class="button_u"  onclick="deletePmeInfo('1')"/></div> 
		             		</div>
		             		<div style="margin-top: 5px;">
		             			<table id="grid1"></table>
		             		</div>
		             	</div>
				    </div>
		         </div>
		         <div id="tab2">
		             	<div style="margin-left:10px;margin-top:2px">
			             	<div>
			             		<div>
			             			<div style="float: left;"><input type="button" value="新  增" style="width: 60px;"  onclick="insertGridInfo('grid2')"class="button_u" /></div>
			             			<div><input type="button" value="删   除" style="width: 60px;margin-left: 5px;"class="button_u"  onclick="deletePmeInfo('2')"/></div> 
			             		</div>
			             		<div style="margin-top: 5px;">
			             			<table id="grid2"></table>
			             		</div>
						    </div>
					    </div>
		         </div>
		         <div id="tab3">
		             	<div style="margin-left:10px;margin-top:2px">
					    	<div>
			             		<div>
			             			<div style="float: left;"><input type="button" value="新  增" style="width: 60px;"  onclick="insertGridInfo('grid3')"class="button_u" /></div>
			             			<div><input type="button" value="删   除" style="width: 60px;margin-left: 5px;"class="button_u"  onclick="deletePmeInfo('3')"/></div> 
			             		</div>
			             		<div style="margin-top: 5px;">
			             			<table id="grid3"></table>
			             		</div>
						    </div>
					    </div>
		         </div>
		         <div id="tab4">
		             <div style="margin-left:10px;margin-top:2px">
				    	<div>
			             		<div>
			             			<div style="float: left;"><input type="button" value="新  增" style="width: 60px;"  onclick="insertGridInfo('grid4')"class="button_u" /></div>
			             			<div><input type="button" value="删   除" style="width: 60px;margin-left: 5px;"class="button_u"  onclick="deletePmeInfo('4')"/></div> 
			             		</div>
			             		<div style="margin-top: 5px;">
			             			<table id="grid4"></table>
			             		</div>
						    </div>
				    </div>
		         </div>
		         <div id="tab5">
		             	<div style="margin-left:10px;margin-top:2px">
					    	<div>
			             		<div>
			             			<div style="float: left;"><input type="button" value="新  增" style="width: 60px;"  onclick="insertGridInfo('grid5')"class="button_u" /></div>
			             			<div><input type="button" value="删   除" style="width: 60px;margin-left: 5px;"class="button_u"  onclick="deletePmeInfo('5')"/></div> 
			             		</div>
			             		<div style="margin-top: 5px;">
			             			<table id="grid5"></table>
			             		</div>
						    </div>
					    </div>
		         </div>
		         <div id="tab6">
		             	<div style="margin-left:10px;margin-top:2px">
					    	<div>
			             		<div>
			             			<div style="float: left;"><input type="button" value="新  增" style="width: 60px;"  onclick="insertGridInfo('grid6')"class="button_u" /></div>
			             			<div><input type="button" value="删   除" style="width: 60px;margin-left: 5px;"class="button_u"  onclick="deletePmeInfo('6')"/></div> 
			             		</div>
			             		<div style="margin-top: 5px;">
			             			<table id="grid6"></table>
			             		</div>
						    </div>
					    </div>
		         </div>
		     </div>
	</div>
</div>
</body>
</html>