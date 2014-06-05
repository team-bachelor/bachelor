var attributeArray = ["display","display","readonly","readonly","disabled","disabled"];
var attributeValArray = ["none","block",true,false,true,false];
/** 获取UI权限数据 **/ //1不可见 2可见 3不可编辑 4可编辑 5不可执行 6可执行
function initUiElement(url,functionId,bizKey){
	$.ajax({
		url:(url+"/ui/auth/fd/findByFuncIdAndRoleIds.htm"),
		type:"POST",
		data:"funcId="+functionId+"&bizKey="+bizKey,
		dataType:"json",
		success:function(ev){ 
			if(ev!=null && ev.length>0){
				for(var i=0;i<ev.length;i++){
					/** 如果选择的类型是OmGrid 就跳过该循环 **/
					if(ev[i].uiTypeId == "4"){
						
						break;
					}
					for(index in attributeArray){
						var authIndex = Number(index)+1;
						if(ev[i].uiResourcePermission == String(authIndex)){
							/** 如果权限为不可见或者是可见时，对css样式进行操作 **/
							if(ev[i].uiResourcePermission==1 || ev[i].uiResourcePermission==2){
								$('#'+ev[i].uiResourceId).css({"display":attributeValArray[index]});
							} else {
								$('#'+ev[i].uiResourceId).attr(attributeArray[index],attributeValArray[index]);
							}
						}
					}
				}
			}
		}
	});
}
 