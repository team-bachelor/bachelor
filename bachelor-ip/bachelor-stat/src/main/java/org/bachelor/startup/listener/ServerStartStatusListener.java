package org.bachelor.startup.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import org.bachelor.context.common.ContextConstant;
import org.bachelor.context.interceptor.PlatManagedServiceAspect;
import org.bachelor.context.interceptor.PlatManagedDaoAspect;
import org.bachelor.context.interceptor.PlatManagedInterceptor;
import org.bachelor.context.listener.BaseContainerStartupListener;
import org.bachelor.context.service.IVLService;
import org.bachelor.stat.domain.StatAop;
import org.bachelor.stat.domain.StatServer;
import org.bachelor.stat.service.IStatAopService;
import org.bachelor.stat.service.IStatServerService;
import org.bachelor.util.IpUtil;

//@Service
public abstract class ServerStartStatusListener extends BaseContainerStartupListener{

	@Autowired
	private IStatServerService statServerService;
	 
	@Autowired
	private IStatAopService statAopService;
	
	@Autowired
	private IVLService vlService;

	@Override
	public void onEvent(ContextRefreshedEvent event) {
		if(!"Root WebApplicationContext".equals(event.getApplicationContext().getDisplayName())){
			return;
		}
		//只拦截不是/appconsole工程
		if(!vlService.getGloableAttribute(ContextConstant.WEB_CONTEXT_NAME).equals(
				ContextConstant.WEB_PROJECT_NAME)){
			StatServer statServer = new StatServer();
			try{
				statServer.setIpAddr(IpUtil.getServerIp());
				statServer.setStartTime(new Date());
			}catch(Exception e){
				e.printStackTrace();
			}
			statServerService.saveOrUpdate(statServer);
			
			List<StatAop> className_list = processIntercepterInfo((ApplicationContext)event.getSource());
			List<StatAop> stat_list = statAopService.findAll(null);
			List<StatAop> add_list = matchStatAopInfo(className_list,stat_list,"add");
			List<StatAop> del_list = matchStatAopInfo(stat_list,className_list,"del");
			//删除
			if(del_list.size()>0){
				statAopService.deleteByList(del_list);
			}
			//新增
			if(add_list.size()>0){
				statAopService.saveByList(add_list);
			}
		}
	}
	
	/**
	 * 改变拦截器信息
	 * @param class_list 系统检测到的拦截器信息集合
	 * @param stat_list 数据库中查询到的信息集合
	 */
	public List<StatAop> matchStatAopInfo(List<StatAop> class_list,List<StatAop> stat_list,String type){
		if(type.equals("add") && (stat_list==null || stat_list.size()<=0)){
			
			return class_list;
		}
		List<StatAop> temp_list = new ArrayList<StatAop>();
		for(StatAop className:class_list){
			boolean flag = false;
			for(StatAop stat:stat_list){
				if(className.getClassName().equals(stat.getClassName())){
					flag = true;
					break;
				}
			}
			if(flag==false){
				temp_list.add(className);
			}
		}
		return temp_list;
	}  
	
	/**
	 * 操作拦截器信息，返回拦截器类名
	 * @param ac
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<StatAop> processIntercepterInfo(ApplicationContext ac){
		Map managedAspect_map = ac.getBeansOfType(PlatManagedInterceptor.class);
		Map managedBizAspect = ac.getBeansOfType(PlatManagedServiceAspect.class);
		Map managedDaoAspect = ac.getBeansOfType(PlatManagedDaoAspect.class);
		List<StatAop> className_list = new ArrayList<StatAop>();
		getClassName(managedAspect_map,className_list);
		getClassName(managedBizAspect,className_list);
		getClassName(managedDaoAspect,className_list);
		return className_list;
	}
	
	/**
	 * 获取类名
	 * @param obj_map
	 * @param className_list
	 */
	@SuppressWarnings("rawtypes")
	public void getClassName(Map<String,Object> obj_map,List<StatAop> className_list){
		Set<String> key_set = obj_map.keySet();
		for(Iterator it = key_set.iterator();it.hasNext();){
			Object obj = obj_map.get((String)it.next());
			StatAop aop = new StatAop();
			aop.setClassName(obj.getClass().getName());
			if(obj instanceof PlatManagedInterceptor){
				aop.setType("1");
			}
			if(obj instanceof PlatManagedServiceAspect){
				aop.setType("2");
			}
			/** biz类型的切面 **/
			/*if(obj instanceof PfManagedServiceAspect){
				aop.setType("3");
			}*/
			if(obj instanceof PlatManagedDaoAspect){
				aop.setType("4");
			}
			className_list.add(aop);
		}
	}
}