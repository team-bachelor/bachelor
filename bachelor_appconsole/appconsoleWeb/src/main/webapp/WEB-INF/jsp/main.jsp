<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/header.jsp" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>后台管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
	<link href="<%=path %>/css/default/om-default.css" rel="stylesheet" type="text/css">
    <!-- Le styles -->
    <link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
     /* Sticky footer styles
      -------------------------------------------------- */

      html,
      body {
        height: 100%;
        /* The html and body elements cannot have any padding or margin. */
      }

      /* Wrapper for page content to push down footer */
      #wrap {
        min-height: 100%;
        height: auto !important;
        height: 100%;
        /* Negative indent footer by it's height */
        margin: 0 auto -60px;
      }

      /* Set the fixed height of the footer here */
      /*
      #push,
      #footer {
        height: 60px;
      }
      #footer {
        background-color: #f5f5f5;
      }
	*/
      /* Lastly, apply responsive CSS fixes as necessary */
     /*
      @media (max-width: 767px) {
        #footer {
          margin-left: -20px;
          margin-right: -20px;
          padding-left: 20px;
          padding-right: 20px;
        }
      }
	*/


      /* Custom page CSS
      -------------------------------------------------- */
      /* Not required for template or sticky footer method. */

      #wrap > .container {
        padding-top: 60px;
      }
      .container .credit {
        margin: 20px 0;
      }

      code {
        font-size: 80%;
      }
      
      
      /*Tab页右上角关闭按钮样式 */
      .bchlr-ui-icon-close{
      	float:right;cursor:pointer;margin-top:-38px;margin-right:2px;
      }
    </style>
    <link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="<%=path %>/js/html5shiv.js"></script>
    <![endif]-->
    
   
  </head>

  <body>

    <!-- Part 1: Wrap all page content here -->
    <div id="wrap">
    
    <!-- Fixed navbar -->
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="brand" href="#">Project name</a>
          <div class="nav-collapse collapse">
            <ul class="nav" id="menuNav">
              <li class="active"><a href="#home">主页</a></li>
              <li class="dropdown">
              	<a href="#" class="dropdown-toggle" data-toggle="dropdown">项目结构 <b class="caret"></b></a>
              	<ul class="dropdown-menu">
              		<li><a href="#module" menuURL="ps/index.htm">模块功能维护</a></li>
              		<li><a href="#menu" menuURL="menu/index.htm">菜单管理</a></li>
              	</ul>
              </li>
              <li class="dropdown">
              	<a href="#" class="dropdown-toggle" data-toggle="dropdown">组织人员 <b class="caret"></b></a>
              	<ul class="dropdown-menu">
              		<li><a href="#om" menuURL="org/index.htm">组织管理</a></li>
              		<li><a href="#um" menuURL="user/index.htm">人员管理</a></li>
              	</ul>
              </li> 
              <li class="dropdown">
              	<a href="#" class="dropdown-toggle" data-toggle="dropdown">基础参数 <b class="caret"></b></a>
              	<ul class="dropdown-menu">
              		<li><a href="#enum" menuURL="genum/index.htm">枚举管理</a></li>
              		<li><a href="#gv" menuURL="gv/variable/index.htm">全局变量</a></li>
              	</ul>
              </li>              
              <li class="dropdown">
              	<a href="#" class="dropdown-toggle" data-toggle="dropdown">权限管理 <b class="caret"></b></a>
              	<ul class="dropdown-menu">
              		<li><a href="#role" menuURL="auth/role/index.htm">角色管理</a></li>
              		<li><a href="#roleUser" menuURL="auth/user/index.htm">角色人员管理</a></li>
              		<li><a href="#authFunc" menuURL="auth/psau/index.htm">功能授权管理</a></li>
              		<li><a href="#authUi" menuURL="auth/ui/index.htm">UI控授权限管理</a></li>
              	</ul>
              </li>
              
<!--              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">流程管理 <b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="#queryNotice" menuURL="bpm/notice/index.htm">通知查询</a></li>
                </ul>
              </li>-->
              
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">系统监控 <b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="#statEff" menuURL="stat/index.htm">系统运行效率</a></li>
                  <li><a href="#intercepter" menuURL="intercepter/index.htm">拦截器设置</a></li>
                  <li><a href="#exception" menuURL="ex/index.htm">系统运行异常</a></li>
                </ul>
              </li>
              
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">流程监控 <b class="caret"></b></a>
                <ul class="dropdown-menu">
	                 <li><a href="#bpManager" menuURL="bp/index.htm">流程监控</a></li>
	                 <li><a href="#hisBpManager" menuURL="bp/historic.htm">历史流程</a></li>
                </ul>
              </li>
            </ul>
            <% if(loginUser!=null && loginUser.getId()!=null){ %>
            <ul class="nav pull-right" >
	          		<li class="dropdown" id="userInfoId">
		                <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <%=loginUser.getUsername() %> <b class="caret"></b></a>
		                	 <ul class="dropdown-menu">
			                	 <li><a href="#statEff" >个人信息</a></li>
			                	 <li class="divider"></li>
			                	 <li><a href="<%=path %>/login/loginoff.htm"  menuType="persional">注销</a></li>
	                	    </ul>
	              </li>
            </ul>
            <% } %>
          </div><!--/.nav-collapse   nav pull-right-->
        </div>
      </div>
    </div>

	<!-- Begin page content -->
    <div class="container">
		<ul class="nav nav-tabs" id="myTabs">
  			<li class="active">
    			<a href="#home" data-toggle="tabHome">主页</a>
  			</li>

		</ul>
		<div id="myTabContent" class="tab-content">
			<div class="tab-pane fade in active" id="home">
				主页
			</div>

		</div>
	</div>
      
    <!-- <div id="push"></div> -->
 </div>
 
 <!-- 
 <div id="footer">
    <div class="container">
       <p class="muted credit">Example courtesy <a href="http://martinbean.co.uk">Martin Bean</a> and <a href="http://ryanfait.com/sticky-footer/">Ryan Fait</a>.</p>
    </div>
 </div>
 -->

    <script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
    <script src="<%=path %>/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=path %>/js/operamasks-ui.js"></script>
	
	<script type="text/javascript">
		var showTabs = new Array();
		$(document).ready(function() {
			$('#menuNav .dropdown-menu a').click(function(e){
				var url = $(this).attr('menuURL');
				var tabId = this.hash.replace('#', '');
				var tabName = this.innerText;
				addOrShowTab(tabId, tabName, url);
			}); 
		});
		
		function addOrShowTab(tabId,tabName, url){
			if($('#myTabs a[href="#' + tabId + '"]').length > 0){
				$('#myTabs a[href="#' + tabId + '"]').tab('show');
			}else{
				$('#myTabs').append('<li id="' + tabId + '1"><a href="#' + tabId + '" data-toggle="tab">' + tabName + '</a><button class="close bchlr-ui-icon-close" onclick="closeWin(\''+tabId+'\')">&times;</button></li>');
				var tabContextHtml = '<div id="' + tabId + '" class="tab-pane fade">' 
					+ '<iframe id="main" border=0 frameBorder="no" src="' + url + '" width="100%" height="600"></iframe>' 
					+ '</div>';
				$('#myTabContent').append(tabContextHtml);
				$('#myTabs a[href="#' + tabId + '"]').tab('show');
			}
			//** 值是否有重复添加 **//
			var flag = true;
			for(var i=0;i<showTabs.length;i++){
				if(showTabs[i]==tabId){
					flag = false;
				}
			}
			if(flag){
				//** 保存显示的TabId **//
				showTabs.push(tabId);
			}
			return;
		}
		/** 关闭当前窗口 **/
		function closeWin(tabId){
			$("#"+tabId+"1").remove();
			$("#"+tabId).remove();  
			if(showTabs!=null && showTabs.length>0){
				for(var i=0;i<showTabs.length;i++){
					if(showTabs[i]==tabId){
						showTabs.splice(i,1);
						break;
					}
				}
			}
			$('#myTabs a[href="#'+(showTabs.length>0?showTabs[showTabs.length-1]:"home")+'"]').tab('show');
		}
	</script>

  </body>
</html>
