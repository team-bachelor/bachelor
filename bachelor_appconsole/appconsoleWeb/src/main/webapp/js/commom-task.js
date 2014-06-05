var processedColor = "ff3030";//已执行 
var processColor = "3AEB3E";//执行
/** 删除上一次显示节点数据集合 **/
var removeArray = new Array();
/** 设置节点值 **/
function setTaskInfo(array,valArray){
	for(var i=0;i<array.length;i++){
		if(valArray!=null){
			$("#"+array[i]).html(valArray[i]);
		} else {
			$("#"+array[i]).html("");
		}
	}
}
   			
/** 计算图片热点位置 **/
function calculatePoint(x,y,gds){
	var index = -1;
	var flag = false;
	if(gds!=null && gds.length>0){
		for(i in gds){
			var gd = gds[i];
			/** 鼠标是否移动到节点上  **/
			if(x<=(Number(gd.x)+Number(gd.width)) && x>=gd.x &&
					y<=(Number(gd.y)+Number(gd.height)) && y>=gd.y){
				flag = true;
			}
			if(flag==true){
				index = i;
				break;
			}	 
		}
	}
	return index;
}
/** 节点信息 **/
var taskDatas = new Array();
var graphicDatas = new Array();
/** 请求节点数据  dialogId只有在弹出显示流程图时会有,其他情况可传NULL**/
function initTaskData(bizKey,path,divId,appDivId,popDivId,dialogId){
		$.ajax({
					url:(path),
					dataType:"json",
					type:"get",
					data:"bizKey="+bizKey,
					success:function(result){
							if(result!=null){
									/** 循环动态创建DIV **/
									taskDatas = result.taskDatas;//历史数据
									dynamicCreateDiv(result.taskDatas,result.taskBorderData,divId,appDivId,popDivId,dialogId);
									graphicDatas = result.taskBorderData;
							}
					}
		});
}

/** 循环动态创建DIV **/
function dynamicCreateDiv(taskPropertyDatas,taskBorderData,divId,appDivId,popDivId,dialogId){
	
	var html = "";
	if(taskBorderData!=null && taskBorderData.length>0){
		var bTop = document.getElementById(divId).offsetTop;
		var bLeft = document.getElementById(divId).offsetLeft; 
		var borderColor = processColor;
		for(index in taskBorderData){
				var obj = taskBorderData[index];
				/** 判断是否是当前活动节点，加以颜色 **/
				if(taskPropertyDatas!=null && taskPropertyDatas.length>0){
					for(pdIndex in taskPropertyDatas){
						if(taskPropertyDatas[pdIndex].id ==obj.id){
							/** 颜色赋值为已执行 **/
							if(taskPropertyDatas[pdIndex].type == "0"){
								borderColor = processColor;
								break;
							} else if(taskPropertyDatas[pdIndex].type == "1"){
								borderColor = processedColor;
								break;
							}
						}
					}
				}
				html+='<div id="showClientImageId_'+index+'" style="position:absolute;cursor:pointer;'; 
				html+='top:'+((Number(obj.y)+(Number(bTop)))+"px;");
				html+='left:'+((Number(obj.x)+(Number(bLeft)))+"px;");
				html+='border-color:#'+borderColor+';';
				html+='width:'+obj.width+"px;";
				html+='height:'+obj.height+"px;";
				html+='z-index: 1010;" class="hightBorder table-bordered"';
				html+=' onclick="showTaskInfo(\''+divId+'\',\'showClientImageId_'+index+'\',\''+popDivId+'\')" bizKey="'+obj.id+'"> </div>';
		}
	}
	if(taskInfoArray.length>0){
		$("#"+appDivId).append(html); 
	} else {
		/** 删除上一次显示节点的DIV **/
		for(index in removeArray){
			if($("#showClientImageId_"+index)){
				$("#showClientImageId_"+index).remove();
			}
		}
		removeArray = taskBorderData;
		$("#"+dialogId).append(html); 
	} 
}

/** 显示节点数据的POP框 **/
function showTaskInfo(divId,popId,popDivId){
	if(taskDatas!=null && taskDatas.length>0){
			/** 得到鼠标移动到的节点下标 **/
			var popObj = $("#"+popId);
			var bizKey = popObj.attr("bizKey"); 
			for(index in taskDatas){
				var obj = taskDatas[index];
				if(obj.id == bizKey){
					/** 标题名称 **/
					$("#"+popDivId).attr("title",obj.title);
					//$("#popTitle").html(obj.title);
					/** 赋值当前节点  如果是历史信息的话，就不需要对taskInfoArray里面的元素赋值**/
					if(taskInfoArray.length>0){
						var htmlArray = new Array();
						htmlArray.push(obj.candidateName);
						htmlArray.push(obj.candidateRoles);//待办角色名称
						htmlArray.push(obj.ownerName);
						setTaskInfo(taskInfoArray,htmlArray);
					}	
					
					/** 显示已执行节点的数据 **/ //obj数据没有实现
					setProcessedData(obj.gds);
					
					/** 得到图形节点数据 **/
					var graphicDataObj = getGraphicDataObj(graphicDatas,bizKey);
					
					/** 显示图形节点 **/
					$("#"+popDivId).dialog("open");
				}
			}
	}
}
/**  得到流程图形属性 **/
function getGraphicDataObj(gds,id){
	if(gds!=null && gds.length>0){
		for(index in gds){
			var flag = false;
			var gd = gds[index];
			/** 鼠标是否移动到节点上  **/
			if(gd.id == id){
				flag = true;
			}
			if(flag==true){
				return gd;
			}	 
		}
	}
	return null;
}
/** 显示已执行节点的数据 **/
function setProcessedData(datas){
	if(datas!=null && datas.length>0){
		var html = '<div class="row-fluid" style="height:200px;margin-top: 5px;overflow-y: scroll;">';
		html+='<ul class="thumbnails">';
		html+='<li>';
		html+='<span  class="thumbnail" style="border-bottom: 0px;"><b>节点历史操作信息</b>';
		html+='<span class="badge badge-info" style="float: right;">'+(datas.length)+'</span>';
		html+='</span>';
		for(index in datas){
			var hisData = datas[index];
			html+='<span  class="thumbnail">';
			html+='<span class="badge badge-info"  >'+(Number(index)+1)+'</span>';
			html+='<span class="label label-info">节点开始时间:</span>';
			if(hisData.startTime!=null && hisData.startTime!="null" && hisData.startTime!=""){
				html+=' <span class="label "> '+hisData.startTime+'</span>';
			}
			html+='<br/>';
			html+='<span class="label label-info" style="margin-left: 28px;">节点完成时间:</span>';
			if(hisData.endTime!=null && hisData.endTime!="null" && hisData.endTime!=""){
				html+='<span class="label "> '+hisData.endTime+' </span>';
			}
			html+='<br/>';
			html+='<span class="label label-info" style="margin-left: 28px;">执行人:</span>';
			if(hisData.assigneeName!=null && hisData.assigneeName!="null" && hisData.assigneeName!=""){
				html+='<span class="label ">  '+hisData.assigneeName+'</span>';
			}
			html+='</span>';
		}
		html+='</li>';
		html+='</ul>';
		html+='</div>';
		/** 删除上一次信息 **/
		if($(".row-fluid")){
			$(".row-fluid").remove();
		}
		$("#taskPopId").append(html);
	} else {
		/** 删除上一次信息 **/
		if($(".row-fluid")){
			$(".row-fluid").remove();
		}
	}
}