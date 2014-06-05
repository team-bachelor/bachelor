<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../WEB-INF/jsp/inc/header.jsp" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Demo应用</title>
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
      .ufp-ui-icon-close{
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
              <li class="active"><a href="#">主页</a></li>
	                
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

	<!-- Begin page content -->
    <div class="container">
		<ul class="nav nav-tabs" id="myTabs">
  			<li class="active">
    			<a href="#home" data-toggle="tab">主页</a>
  			</li>

		</ul>
		<div id="myTabContent" class="tab-content">
			<div class="tab-pane fade in active" id="home">
				主页
			</div>

		</div>
	</div>
 </div>
    <script src="<%=path %>/js/jquery-1.9.0.min.js"></script>
    <script src="<%=path %>/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=path %>/js/operamasks-ui.js"></script>
	
	<script type="text/javascript">
		var username =  "<%=loginUser.getUsername() %>";
		$(document).ready(function() {
			/** 初始化数据 **/
			initMenuData();
		});
		
		function initMenuData(){
			$.ajax({
					url:"<%=path %>/menu/all.htm?isCompress=false",
					dataType:"json",
					success:function(re){
						var html = "";
						html = circulateMenu(re,html);
						if(username!=null && username!=""){
							$(".nav-collapse").append(appendUserInfo());
						}
 						$('#menuNav').append(html);
						//操作菜单
						initMenuEvent();
						/** 操作个人信息菜单**/
						persionalMenuEvent();
					},
					error:function(e){
						alert("error" + e);
					}
			});
		}
		/** 追加用户信息 **/
		function appendUserInfo(){
			var html = "";
			html += '<ul class="nav pull-right" id="persionalNav">';
	        html += '<li class="dropdown" id="userInfoId">';
		    html += '<a href="#" class="dropdown-toggle" data-toggle="dropdown">'+username+' <b class="caret"></b></a>';
		     html += '<ul class="dropdown-menu">';
			html += ' <li><a href="#personalInfo" menuURL="/persionalInfo.htm" menuType="persional">个人信息</a></li>';
			html += '<li class="divider"></li>';
			html += '<li><a href="<%=path %>/login/logoff.htm"  menuType="persional">注销</a></li>';
	         html += '</ul>';
	        html += '</li>';
            html += '</ul>';
            return html;
		}
		
		//循环菜单数据
		function circulateMenu(re,html){
			if(re==null || re.length<=0){
				return html;
			}
			for(var i=0;i<re.length;i++){
				var menuObj = re[i];
				if(menuObj!=null){
					html += '<li class="dropdown">';
				}
				
				if(menuObj.children==null || menuObj.children.length<=0){
					html += '<a href="#'+menuObj.funcId+'" executable="'+menuObj.executable+'" funcId="'+menuObj.funcId+'" menuId="'+menuObj.id+'"  menuURL="'+menuObj.funcUrl+'" >'+menuObj.name+'</a>';
				} else {
					html += '<a href="#" class="dropdown-toggle" data-toggle="dropdown"  executable="'+menuObj.executable+'">'+menuObj.name+' <b class="caret"></b></a>';
				} 
				 
				if(menuObj.children!=null && menuObj.children.length>0){
					html += '<ul class="dropdown-menu">';
					html = circulateMenu(menuObj.children,html);
					html+= '</ul></li>';
				}  else {
					html += '</li>';
				}
			}
			return html;
		}
		
		function initMenuEvent(){
			$('#menuNav .dropdown-menu a').click(showDropDownMenu);
		}
		
		function persionalMenuEvent(){
			$('#persionalNav .dropdown-menu a').click(showDropDownMenu);
		}
		
		/** 显示点击菜单页面 **/
		function showDropDownMenu(){
			var persional = $(this).attr('menuType');
			/** 点击个人信息菜单 **/
			if(persional!=null && persional!=""){
					var url = ($(this).attr('menuURL'));
					if(url!=null && url!=""){
							var tabId = this.hash.replace('#', '');
							var tabName = this.innerText;
							addOrShowTab(tabId, tabName, url);
					}
			} else { 
					var executable = $(this).attr('executable');	
					if(executable){
						var url = ($(this).attr('menuURL')+"?funcId="+$(this).attr('funcId')+"&menuId="+$(this).attr('menuId'));
						var tabId = this.hash.replace('#', '');
						var tabName = this.innerText;
						addOrShowTab(tabId, tabName, url);
					} else {
						alert("当前用户没有此菜单的使用权限!");
					}
			}
		}
		
		function addOrShowTab(tabId,tabName, url){
			if($('#myTabs a[href="#' + tabId + '"]').length > 0){
				$('#myTabs a[href="#' + tabId + '"]').tab('show');
			}else{
				$('#myTabs').append('<li id="' + tabId + '1"><a href="#' + tabId + '" data-toggle="tab">' + tabName + '</a><button class="close ufp-ui-icon-close" onclick="closeWin(\''+tabId+'\')">&times;</button></li>');
				var tabContextHtml = '<div id="' + tabId + '" class="tab-pane fade">' 
					+ '<iframe id="main" border=0 frameBorder="no" src="<%=path %>' + url + '" width="100%" height="600"></iframe>' 
					+ '</div>';
				$('#myTabContent').append(tabContextHtml);
				$('#myTabs a[href="#' + tabId + '"]').tab('show');
			}
			
			return;
		
		}
		
		function closeWin(tabId){
			//$("#"+tabId+"1").remove();
			//$("#"+tabId).remove(); 
		}
	</script>
            
  </body>
</html>