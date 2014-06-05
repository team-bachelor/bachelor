//初始枚举数据
/**
 * 
 * @param enumUrl 请求头路径
 * @param enumType 查询类型
 * @param optionId 下拉框ID
 * @param isAllValue 是否有全选择
 * @param optionValue 默认值
 */
function initEnumData(methodUrl,optionId,optionType,isAllValue,enumSearchData,optionValue){
	$.ajax({
	 		url:methodUrl,
	 		contentType:"application/json",
	 		type:"get",
	 		dataType:"json", 
	 		success:function(result){ 
	 			enumSearchData = result;
	 			evaluationSelection(result,optionId,optionType,optionValue,isAllValue,true);
	 		}
 	});
}
//赋值下拉框值
function evaluationSelection(result,optionId,optionType,optionValue,isAllValue,isShowOption){
	var optionObj = document.getElementById(optionId);
	if(isShowOption==true){
		if(optionObj.length<=0){
		    optionObj.options.remove(0,1);
		    if(isAllValue==true){
				optionObj.options.add(new Option("全选",""));
			}
			for (var i=0;i<result.length;i++){
		 		var obj = result[i];
		 		if(optionType == "enum"){
		 			optionObj.options.add(new Option(obj.text,obj.value));
		 		}
		 		if(optionType == "role"){
		 			optionObj.options.add(new Option(obj.name,obj.id));
		 		}
		 		if(optionType == "pd"){
		 			optionObj.options.add(new Option((obj.name+"--"+obj.version),obj.id));
		 		}
		 	}
		}
	}
	if(optionValue!=null && optionObj.length>0){
		for(var i=0;i<optionObj.length;i++){
			if(optionObj[i].value == initVal){
				optionObj[i].selected = true;
				break;
			}
		}
		
	}
}

/**
 * 是否为空
 * @param value 要判断的值
 * @returns
 */
function contentIsEmpty(value){
	if(value==null || value==""){
		return "";
	}
	return value;
}

/**
 * 截取字符串
 * @param content 需要截取的字符内容
 * @param showChar 截取后显示的字符
 * @param len 截取长度
 * @returns
 */
function charsSubstring(content,len,showChar){
	if(contentIsEmpty(content)==""){
		return "";
	}
	if(content.length>=len){
		
		return content.substring(0,len)+showChar;
	}
	return content;
}