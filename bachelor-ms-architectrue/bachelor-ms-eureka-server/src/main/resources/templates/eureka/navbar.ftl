<h1>系统状态</h1>
<div class="row">
  <div class="col-md-6">
    <table id='instances' class="table table-condensed table-striped table-hover">
      <#if amazonInfo??>
        <tr>
          <td>EUREKA SERVER</td>
          <td>AMI: ${amiId!}</td>
        </tr>
        <tr>
          <td>区域</td>
          <td>${availabilityZone!}</td>
        </tr>
        <tr>
          <td>实例id</td>
          <td>${instanceId!}</td>
        </tr>
      </#if>
      <tr>
        <td>环境</td>
        <td>${environment!}</td>
      </tr>
      <tr>
        <td>数据中心</td>
        <td>${datacenter!}</td>
      </tr>
    </table>
  </div>
  <div class="col-md-6">
    <table id='instances' class="table table-condensed table-striped table-hover">
      <tr>
        <td>当前时间</td>
        <td>${currentTime}</td>
      </tr>
      <tr>
        <td>启动时长</td>
        <td>${upTime}</td>
      </tr>
      <tr>
        <td>到期剔除实例开启</td>
        <td>${registry.leaseExpirationEnabled?c}</td>
      </tr>
      <tr>
        <td>更新阈值</td>
        <td>${registry.numOfRenewsPerMinThreshold}</td>
      </tr>
      <tr>
        <td>(最近一分钟)续订</td>
        <td>${registry.numOfRenewsInLastMin}</td>
      </tr>
    </table>
  </div>
</div>

<#--<#if isBelowRenewThresold>-->
    <#--<#if !registry.selfPreservationModeEnabled>-->
        <#--<h4 id="uptime"><font size="+1" color="red"><b>RENEWALS ARE LESSER THAN THE THRESHOLD. THE SELF PRESERVATION MODE IS TURNED OFF.THIS MAY NOT PROTECT INSTANCE EXPIRY IN CASE OF NETWORK/OTHER PROBLEMS.</b></font></h4>-->
    <#--<#else>-->
        <#--<h4 id="uptime"><font size="+1" color="red"><b>EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.</b></font></h4>-->
    <#--</#if>-->
<#--<#elseif !registry.selfPreservationModeEnabled>-->
    <#--<h4 id="uptime"><font size="+1" color="red"><b>THE SELF PRESERVATION MODE IS TURNED OFF.THIS MAY NOT PROTECT INSTANCE EXPIRY IN CASE OF NETWORK/OTHER PROBLEMS.</b></font></h4>-->
<#--</#if>-->
<#if isBelowRenewThresold>
    <#if !registry.selfPreservationModeEnabled>
        <h4 id="uptime"><font size="+1" color="red"><b>更新比阈值小。自我保护模式已关闭。在网络/其他问题的情况下，这可能无法保护实例失效。</b></font></h4>
    <#else>
        <h4 id="uptime"><font size="+1" color="red"><b>紧急情况! EUREKA 声明服务实例可能出错, 虽然显示up状态，但实例可能已停止。更新比阈值小，因此，为了安全起见，实例不会过期。</b></font></h4>
    </#if>
<#elseif !registry.selfPreservationModeEnabled>
    <h4 id="uptime"><font size="+1" color="red"><b>自我保护模式已关闭。在网络/其他问题的情况下，这可能无法保护实例失效。</b></font></h4>
</#if>

<h1>Eureka节点列表</h1>
<ul class="list-group">
  <#list replicas as replica>
    <li class="list-group-item"><a href="${replica.value}">${replica.key}</a></li>
  </#list>
</ul>

