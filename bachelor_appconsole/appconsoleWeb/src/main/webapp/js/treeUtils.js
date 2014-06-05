function TreeUtils(){
	/** 初始化角色树状态 **/
	/*this.initTreeStatus = function(ztreeObj,ztreeObjData,initData,flag){
		for(index in ztreeObjData){
			if(ztreeObjData[index].nodeType=="role" && (ztreeObjData[index].children==null || ztreeObjData[index].children.length<=0)){
				for(len in initData){
					var roleId = initData[len].split(",")[1];
					if(roleId == ztreeObjData[index].id){
						var ckNode = ztreeObj.getNodeByParam("id",roleId,null);
						ztreeObj.checkNode(ckNode, flag, true);	
					}
				}
			}
			if(ztreeObjData[index].children!=null && ztreeObjData[index].children.length>0){
			
				this.initTreeStatus(ztreeObj,ztreeObjData[index].children,initData,flag);
			}
		}
	}*/
	
	/** 根据角色ID，搜索所有功能ID，返回Array**/
	this.searchArrayByFuncIdOrRoleId = function(id,type,allRoleFuncs){
		var funcs = new Array();
		for(index in allRoleFuncs){
			/** 数据下标1角色ID,0功能ID **/
			var tId = ""; 
			var sId = "";
			if(type=="1"){
					tId = allRoleFuncs[index].split(",")[1];
					sId = allRoleFuncs[index].split(",")[0];
			} else if(type=="0"){
					tId = allRoleFuncs[index].split(",")[0];
					sId = allRoleFuncs[index].split(",")[1];
			}
			if(id==null){
					
					funcs.push(sId);
			} else {
					if(id==tId){
						
						funcs.push(sId);
					}
			}
		}
		return funcs;
	}
	
	/** 根据功能ID，勾选树节点 **/
	this.checkTreeByIds = function(flag,type,ztreeObj,ztreeObjData,sArray){
		var tempProcessArray = new Array();
		for(index in ztreeObjData){
			if(ztreeObjData[index].nodeType==type && (ztreeObjData[index].children==null || ztreeObjData[index].children.length<=0)){
				for(len in sArray){
					if(sArray[len] == ztreeObjData[index].id){
						var ckNode = ztreeObj.getNodeByParam("id",sArray[len],null);
						ztreeObj.checkNode(ckNode, flag, true);	
					}
				}
			}
			if(ztreeObjData[index].children!=null && ztreeObjData[index].children.length>0){
			
				this.checkTreeByIds(flag,type,ztreeObj,ztreeObjData[index].children,sArray);
			}
		}
	}
	
	/** 得到数据ID信息集合 **/
	this.getTreeIds = function(nodeType,ids,ztreeObjData){
		for(index in ztreeObjData){
			if(ztreeObjData[index].nodeType=="role" && (ztreeObjData[index].children==null || ztreeObjData[index].children.length<=0)){
				
				ids.push(ztreeObjData[index].id);
			}
			if(ztreeObjData[index].children!=null && ztreeObjData[index].children.length>0){
				
				this.getTreeIds(nodeType,ids,ztreeObjData[index].children);
			}
		}
		return ids;
	}
};