<#import "/spring.ftl" as spring />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${basePath}">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hystrix Dashboard</title>

	<!-- Javascript to monitor and display -->
	<script src="<@spring.url '/webjars/jquery/2.1.1/jquery.min.js'/>" type="text/javascript"></script>
	
	<script>
		function sendToMonitor() {
			
			if($('#stream').val().length > 0) {
				var url = "<@spring.url '/hystrix'/>/monitor?stream=" + encodeURIComponent($('#stream').val()) + "";
				if($('#delay').val().length > 0) {	
					url += "&delay=" + $('#delay').val();
				}
				if($('#title').val().length > 0) {	
					url += "&title=" + encodeURIComponent($('#title').val());
				}
				location.href= url;
			} else {
				$('#message').html("The 'stream' value is required.");
			}
		}
	</script>
</head>
<body>
<div style="width:800px;margin:0 auto;">
	
	<center>
	<#--<img width="264" height="233" src="<@spring.url '/hystrix'/>/images/hystrix-logo.png">-->
	<br>
	<br>
	
	<h2>监控仪表盘</h2>
	<input id="stream" type="textfield" size="120" placeholder="http://hostname:port/turbine/turbine.stream"></input>
	<br><br>
	<i>Turbine集群(默认集群):</i> http://turbine-hostname:port/turbine.stream
	<br>
	<i>Turbine集群(指定集群):</i> http://turbine-hostname:port/turbine.stream?cluster=[clusterName]
	<br>
	<i>单个Hystrix应用:</i> http://hystrix-app:port/hystrix.stream
	<br><br>
	延迟时间: <input id="delay" type="textfield" size="10" placeholder="2000"></input>毫秒
	&nbsp;&nbsp;&nbsp;&nbsp; 
	页面标题: <input id="title" type="textfield" size="60" placeholder="Example Hystrix App"></input><br>
	<br>
	<button onclick="sendToMonitor()">Monitor Stream</button>
	<br><br>
	<div id="message" style="color:red"></div>
	
	</center>
</div>
</body>
</html>