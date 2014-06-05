package org.bachelor.stop.listener;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Service;

import org.bachelor.context.common.ContextConstant;
import org.bachelor.context.listener.BaseContainerStopListener;
import org.bachelor.context.service.IVLService;
import org.bachelor.stat.domain.StatServer;
import org.bachelor.stat.service.IStatServerService;
import org.bachelor.util.IpUtil;

@Service
public abstract class ServerStopStatusListener extends BaseContainerStopListener{

	@Autowired
	private IStatServerService statServerService;
	
	@Autowired
	private IVLService vlService;
	 
	@Override
	public void onEvent(ContextClosedEvent  event) {
		if(!event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")){
			
			return ;
		}
		//只拦截不是/appconsole工程
		if(!vlService.getGloableAttribute(ContextConstant.WEB_CONTEXT_NAME).equals(
				ContextConstant.WEB_PROJECT_NAME)){
			try{
				StatServer ss = new StatServer();
				ss.setIpAddr(IpUtil.getServerIp());
				List<StatServer> ss_list = statServerService.findByExample(ss);
				if(ss_list!=null && ss_list.size()>0){
					ss = (StatServer)ss_list.get(0);
					ss.setShutdownTime(new Date());
					statServerService.saveOrUpdate(ss);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
