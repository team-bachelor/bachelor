<#import "/spring.ftl" as spring />
<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
  <head>
    <base href="<@spring.url basePath/>">
    <title>Eureka - Last N events</title>
    <link rel="stylesheet" type="text/css" href="eureka/css/wro.css">
  </head>
  <body id="three">

    <!--[if lt IE 7]>
      <p>You are using an <strong>outdated</strong> browser. Please upgrade your browser to improve your experience.</p>
    <![endif]-->

    <#include "header.ftl">

    <div class="container-fluid xd-container">
      <#include "navbar.ftl">

    <div id="xd-jobs" class="tab-pane active col-md-12">
        <ul class="nav nav-tabs" role="tablist" id="myTab">
          <#--<li class="active"><a data-toggle="tab" href="#cancelled">Last 1000 cancelled leases</a></li>-->
          <#--<li><a data-toggle="tab" href="#registered">Last 1000 newly registered leases</a></li>-->
          <li class="active"><a data-toggle="tab" href="#cancelled">最近1000个取消续租服务</a></li>
          <li><a data-toggle="tab" href="#registered">最近1000个新注册续租服务</a></li>
        </ul>
        <div class="tab-content">
          <div class="tab-pane" id="cancelled">
            <table id='lastNCanceled' class="table table-striped table-hover">
              <thead>
                <tr><th>时间戳</th><th>租约</th></tr>
              </thead>
              <tbody>
                <#if lastNCanceled?has_content>
	                <#list lastNCanceled as entry>
	                  <tr><td>${entry.date?datetime}</td><td>${entry.id}</td></tr>
	                </#list>
	            <#else>
	              <tr><td colspan="2">暂无有效数据</td></tr>
	            </#if>
              <tbody>
            </table>
          </div>
          <div class="tab-pane" id="registered">
            <table id='lastNRegistered' class="table table-striped table-hover">
              <thead>
                <tr><th>时间戳</th><th>租约</th></tr>
              </thead>
              <tbody>
                <#if lastNRegistered?has_content>
                  <#list lastNRegistered as entry>
                    <tr><td>${entry.date?datetime}</td><td>${entry.id}</td></tr>
                  </#list>
                <#else>
	              <tr><td colspan="2">暂无有效数据</td></tr>
	            </#if>
              </tbody>
            </table>
          </div>
      </div>
    </div>
    </div>
    <script type="text/javascript" src="eureka/js/wro.js" ></script>
    <script type="text/javascript">
      $(function () {
        $('#myTab a:last').tab('show')
      })
    </script>
  </body>
</html>
