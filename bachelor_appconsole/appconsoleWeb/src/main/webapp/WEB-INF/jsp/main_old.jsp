<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>平台控制台</title>
<link href="<%=path %>/css.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/default/om-default.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=path %>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/operamasks-ui.js"></script>
<style>
      html, body{ width: 100%; height: 100%; padding: 0; margin: 0;}
      #center-tab .om-panel-body{
        padding: 0;
      }
</style>
    <script type="text/javascript">
         $(document).ready(function() {
            $('body').omBorderLayout({
               panels:[{
                    id:"center-panel",
                  header:false,
                    region:"center"
                },{
                    id:"west-panel",
                    resizable:true,
                    collapsible:true,
                    title:"功能导航",
                    region:"west",width:150
                }]
            });

            var tabElement = $('#center-tab').omTabs({
                height : "fit"
            });

            var navData = [ {id:"n1",text:"工程配置",expanded:true},
                            {id:"n2",text:"授权管理",expanded:true},
                            {id:"n3",text:"系统监控",expanded:true},
                            {id:"n4",text:"人员管理",expanded:true},
                         
                          {id:"n11",pid:"n1",text:"项目结构维护",url:"<%=path %>/ps/index.htm"},
                          {id:"n12",pid:"n1",text:"枚举维护",url:"<%=path %>/genum/index.htm"},
                          {id:"n13",pid:"n1",text:"全局变量维护",url:"<%=path %>/gv/variable/index.htm"},
                          {id:"n14",pid:"n1",text:"组织结构维护",url:"<%=path %>/org/index.htm"},
                          {id:"n15",pid:"n1",text:"菜单维护",url:"<%=path %>/menu/index.htm"},
                         
                          {id:"n21",pid:"n2",text:"角色维护",url:"<%=path %>/auth/role/index.htm"},
                          {id:"n22",pid:"n2",text:"角色人员管理",url:"<%=path %>/auth/user/index.htm"},
                          {id:"n23",pid:"n2",text:"模块功能授权维护",url:"<%=path %>/auth/psau/index.htm"},
                          {id:"n24",pid:"n2",text:"UI元素授权",url:"<%=path %>/auth/ui/index.htm"},
                          
                          {id:"n25",pid:"n4",text:"人员信息维护",url:"<%=path %>/user/index.htm"},
                         
                    	 {id:"n31",pid:"n3",text:"系统异常",url:"<%=path %>/ex/index.htm"}];

            $("#navTree").omTree({
                dataSource : navData,
                simpleDataModel: true,
                onClick : function(nodeData , event){
                  if(nodeData.url){
                    var tabId = tabElement.omTabs('getAlter', 'tab_'+nodeData.id);
                    if(tabId){
                      tabElement.omTabs('activate', tabId);
                    }else{
                      tabElement.omTabs('add',{
                            title : nodeData.text, 
                            tabId : 'tab_'+nodeData.id,
                            content : "<iframe id='"+nodeData.id+"' border=0 frameBorder='no' name='inner-frame' src='"+nodeData.url+"' height='"+ifh+"' width='100%'></iframe>",
                            closable : true
                        });
                    }
                  }
                }
            });

            var ifh = tabElement.height() - tabElement.find(".om-tabs-headers").outerHeight() - 4; //为了照顾apusic皮肤，apusic没有2px的padding，只有边框，所以多减去2px
            $('#main').height(ifh);
        });

    </script>

</head>
<body>
<div id="center-panel">
	<div>
		当前登录用户：<%=loginUser.getUsername() %>,单位：<%=loginUser.getOwnerOrg().getName() %>
	</div>
          <div id="center-tab" >
            <ul>
               <li><a href="#tab1">平台控制台首页</a></li>
            </ul>
            <div id="tab1">
               <iframe id='main' border=0 frameBorder='no' src='../banner.htm' width='100%'></iframe>
            </div>
          </div>
      </div>
<div id="west-panel">
  <ul id="navTree"></ul>
</div>

</body>
</html>