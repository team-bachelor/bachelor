<#import "/spring.ftl" as spring />
<!doctype html>
<html lang="en">
<head>
    <base href="${basePath}">
	<meta charset="utf-8" />
	<title>容错情况监控</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />

	<!-- Setup base for everything -->
	<link rel="stylesheet" type="text/css" href="css/global.css" />
	
	<!-- Our custom CSS -->
	<link rel="stylesheet" type="text/css" href="css/monitor.css" />

	<!-- d3 -->
    <script type="text/javascript" src="js//d3.min.js" ></script>
	
	<!-- Javascript to monitor and display -->
    <script type="text/javascript" src="js/jquery.min.js" ></script>
	<script type="text/javascript" src="js/jquery.tinysort.min.js"></script>
	<script type="text/javascript" src="js/tmpl.js"></script>
	
	<!-- HystrixCommand -->
	<script type="text/javascript" src="components/hystrixCommand/hystrixCommand.js"></script>
	<link rel="stylesheet" type="text/css" href="components/hystrixCommand/hystrixCommand.css" />
	
	<!-- HystrixThreadPool -->
	<script type="text/javascript" src="components/hystrixThreadPool/hystrixThreadPool.js"></script>
	<link rel="stylesheet" type="text/css" href="components/hystrixThreadPool/hystrixThreadPool.css" />

</head>
<body>
	<div id="header">
		<h2><span id="title_name"></span></h2>
	</div>

	<div class="container">
		<div class="row">
			<div class="menubar">
				<div class="title">
				服务回路
				</div>
				<div class="menu_actions">
					Sort: 
					<a href="javascript://" onclick="hystrixMonitor.sortByErrorThenVolume();">出错访问量（Error then Volume）</a> |
					<a href="javascript://" onclick="hystrixMonitor.sortAlphabetically();">名称</a> |
					<a href="javascript://" onclick="hystrixMonitor.sortByVolume();">访问量</a> |
					<a href="javascript://" onclick="hystrixMonitor.sortByError();">错误量（Error）</a> |
					<a href="javascript://" onclick="hystrixMonitor.sortByLatencyMean();">平均值</a> |
					<a href="javascript://" onclick="hystrixMonitor.sortByLatencyMedian();">中值</a> |
					<a href="javascript://" onclick="hystrixMonitor.sortByLatency90();">90</a> | 
					<a href="javascript://" onclick="hystrixMonitor.sortByLatency99();">99</a> | 
					<a href="javascript://" onclick="hystrixMonitor.sortByLatency995();">99.5</a> 
				</div>
				<div class="menu_legend">
					<span class="success">成功</span> | <span class="shortCircuited">短路</span> | <span class="badRequest">错误请求</span> | <span class="timeout">超时</span> | <span class="rejected">拒绝</span> | <span class="failure">失败</span> | <span class="errorPercentage">错误率（%）</span>
				</div>
			</div>
		</div>
		<div id="dependencies" class="row dependencies"><span class="loading">正在加载 ...</span></div>
		
		<div class="spacer"></div>

		<div class="row">
			<div class="menubar">
				<div class="title">
				线程池
				</div>
				<div class="menu_actions">
					Sort: <a href="javascript://" onclick="dependencyThreadPoolMonitor.sortAlphabetically();">名称</a> |
					<a href="javascript://" onclick="dependencyThreadPoolMonitor.sortByVolume();">数量</a> |
				</div>
			</div>
		</div>
		<div id="dependencyThreadPools" class="row dependencyThreadPools"><span class="loading">正在加载 ...</span></div>
	</div>



<script>
		/**
		 * Queue up the monitor to start once the page has finished loading.
		 * 
		 * This is an inline script and expects to execute once on page load.
		 */ 
		 
		 // commands
		var hystrixMonitor = new HystrixCommandMonitor('dependencies', {includeDetailIcon:false});
		var basePath = '';
		var stream = getUrlVars()["stream"];
		
		console.log("Stream: " + stream)
		
		if(stream != undefined) {
			if(getUrlVars()["delay"] != undefined) {
				stream = stream + "&delay=" + getUrlVars()["delay"];
			}
			
			var commandStream = basePath + "${contextPath}/proxy.stream?origin=" + stream;
			var poolStream = basePath + "${contextPath}/proxy.stream?origin=" + stream;
			
			if(getUrlVars()["title"] != undefined) {
				$('#title_name').text("数据流来源: " + decodeURIComponent(getUrlVars()["title"]))
			} else {
				$('#title_name').text("数据流来源: " + decodeURIComponent(stream))
			}
		}
		console.log("命令数据流: " + commandStream)
				
		$(window).load(function() { // within load with a setTimeout to prevent the infinite spinner
			setTimeout(function() {
				if(commandStream == undefined) {
						console.log("命令流未定义")
						$("#dependencies .loading").html("未指定'stream'参数。");
						$("#dependencies .loading").addClass("failed");
				} else {
					// sort by error+volume by default
					hystrixMonitor.sortByErrorThenVolume();
					
					// start the EventSource which will open a streaming connection to the server
					var source = new EventSource(commandStream);
					
					// add the listener that will process incoming events
					source.addEventListener('message', hystrixMonitor.eventSourceMessageListener, false);

					//	source.addEventListener('open', function(e) {
					//		console.console.log(">>> opened connection, phase: " + e.eventPhase);
					//	    // Connection was opened.
					//	}, false);

					source.addEventListener('error', function(e) {
						$("#dependencies .loading").html("连接不到被监控数据流");
						$("#dependencies .loading").addClass("failed");
					  if (e.eventPhase == EventSource.CLOSED) {
					    // Connection was closed.
						  console.log("连接错误关闭: " + JSON.stringify(e));
					  } else {
						  console.log("数据流中出现错误: " + JSON.stringify(e));
					  }
					}, false);
				}
			},0);
		});
		
		// thread pool
		var dependencyThreadPoolMonitor = new HystrixThreadPoolMonitor('dependencyThreadPools');

		$(window).load(function() { // within load with a setTimeout to prevent the infinite spinner
			setTimeout(function() {
				if(poolStream == undefined) {
						console.log("poolStream is undefined")
						$("#dependencyThreadPools .loading").html("未指定'stream'参数。");
						$("#dependencyThreadPools .loading").addClass("failed");
				} else {
					dependencyThreadPoolMonitor.sortByVolume();
					
					// start the EventSource which will open a streaming connection to the server
					var source = new EventSource(poolStream);
					
					// add the listener that will process incoming events
					source.addEventListener('message', dependencyThreadPoolMonitor.eventSourceMessageListener, false);
	
					//	source.addEventListener('open', function(e) {
					//		console.console.log(">>> opened connection, phase: " + e.eventPhase);
					//	    // Connection was opened.
					//	}, false);
	
					source.addEventListener('error', function(e) {
                        $("#dependencies .loading").html("连接不到被监控数据流");
                        $("#dependencies .loading").addClass("failed");
					  if (e.eventPhase == EventSource.CLOSED) {
					    // Connection was closed.
						  console.log("Connection was closed on error: " + e);
					  } else {
						  console.log("Error occurred while streaming: " + e);
					  }
					}, false);
				}
			},0);
		});
		
		//Read a page's GET URL variables and return them as an associative array.
		// from: http://jquery-howto.blogspot.com/2009/09/get-url-parameters-values-with-jquery.html
		function getUrlVars()
		{
		    var vars = [], hash;
		    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
		    for(var i = 0; i < hashes.length; i++)
		    {
		        hash = hashes[i].split('=');
		        vars.push(hash[0]);
		        vars[hash[0]] = hash[1];
		    }
		    return vars;
		}
		
	</script>


</body>
</html>
