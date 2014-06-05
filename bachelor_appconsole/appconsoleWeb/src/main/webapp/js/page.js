var showPageNum = 15;//显示行数
var startLineNum = 0;//开始行数
var currentPageNum = 0;//当前页数

var gloabPageDiv = "";
var gloabPageUrl = "";
var gloabDataSearchResult = function(){};

/** 生成分页组件 **/
function createPageComponent(pageResult,pageDiv){
	if(pageResult!=null){
		var html = '<div class="pagination pagination-large pagination-centered">';
		html += "<ul>";
		
		/** 判断上一页 **/
		if((Number(pageResult.page))>=0){
			html+='<li><a href="javascript:void(0);" onclick="processPageInfo('+(Number(pageResult.page)-1)+','+pageResult.pageRowNum+')">上一页</a></li>';
		}
		
		/** 循环页数 **/
		var pageCount = pageResult.pageCount;
		for(var index=0;index<pageCount;index++){
			if(index<8){
				html+='<li><a href="javascript:void(0);" onclick="processPageInfo('+(index)+','+pageResult.pageRowNum+')">'+(index+1)+'</a></li>';
			} else {
				break;
			}
		}
		 
		/** 判断下一页 **/
		if((Number(pageResult.page))<pageResult.pageCount) {
			html+='<li><a href="javascript:void(0);" onclick="processPageInfo('+(Number(pageResult.page)+1)+','+pageResult.pageRowNum+')">下一页</a></li>';
		}
		html+='<li><a href="javascript:void(0);">'+(pageResult.pageCount)+'/'+(Number(pageResult.page)+1)+'</a></li>';
		html += "</ul>";
		html += "</div>";
		$("#"+pageDiv).html(html);
	} else {
		$("#"+pageDiv).html("");
	}
} 
/** 操作分页信息 **/
function processPageInfo(tempCurrentPageNum,tempPageRowNum){
	currentPageNum = tempCurrentPageNum;
	startLineNum = currentPageNum*Number(tempPageRowNum);
	/** 分页请求函数 **/
	pageRequestDatas();
}

/** 操作分页请求 **/
function processRequestInfo(pageDiv,pageUrl,dataSearchResult){
	gloabPageDiv = pageDiv;
	gloabPageUrl = pageUrl;
	gloabDataSearchResult = dataSearchResult;
	/** 分页请求函数 **/
	pageRequestDatas();
}

/** 分页请求函数 **/
function pageRequestDatas(){
	$.ajax({
		url:gloabPageUrl,
		type:"get",
		data:"start="+startLineNum+"&limit="+showPageNum+"&page="+currentPageNum,
		dataType:"json",
		success:function(result){
			/** 生成分页组件 **/
			createPageComponent(result.pageResult,gloabPageDiv);
			/** 返回结果集 **/
			gloabDataSearchResult(result.dataResult);
		}
	});
}