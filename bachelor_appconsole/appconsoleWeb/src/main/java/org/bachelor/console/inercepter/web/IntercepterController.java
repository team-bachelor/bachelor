package org.bachelor.console.inercepter.web;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.stat.domain.StatAop;
import org.bachelor.stat.service.IStatAopService;

@Controller
@RequestMapping("intercepter")
public class IntercepterController {
/*
	private Log log = LogFactory.getLog(IntercepterController.class);
	
	@Autowired
	private IStatAopService statAopService;
	
	@Autowired
	private IIntercepterWs intercepterWs;
	*//**
	 * 跳转主页面
	 * @return
	 *//*
	@RequestMapping("index")
	public ModelAndView indexPage(){
		
		return new ModelAndView("intercepter/index");
	}
	
	*//**
	 * 查询所有拦截器信息
	 * @return
	 *//*
	@RequestMapping("all")
	@ResponseBody
	public List<StatAop> findAll(){
		
		return statAopService.findAll(null);
	}
	
	*//**
	 * 保存或者更新信息
	 * @param sa
	 * @return
	 *//*
	@RequestMapping("saveOrUpdate")
	public void saveInfo(HttpServletResponse response , StatAop sa){
		Map<String,Object> sa_map = new HashMap<String,Object>();
		PrintWriter print = null;
		try{
			print = response.getWriter();
			if(sa!=null){
				if(sa.getClassName()!=null && !"".equals(sa.getClassName())){
					//更新服务器端拦截器信息
					String setFlag = intercepterWs.callSetIntercepter(sa.getClassName(), sa.getEnable());
					sa_map.put("\"resultCode\"", "\""+setFlag+"\"");
				}
			}
			
			if(sa!=null){
				if(sa.getId()==null || "".equals(sa.getId())){
					sa.setId(null);
				}
			}
			StatAop tempSa = statAopService.findByName(sa.getClassName());
			if(tempSa==null){
				statAopService.saveOrUpdate(sa);
				sa_map.put("\"resultCode\"", "\"0\"");
			} else {
				if(sa.getClassName().equals(tempSa.getClassName())){
					if(sa.getId()==null){
						sa_map.put("\"resultCode\"", "\"2\"");
					} else {
						if(sa.getId().equals(tempSa.getId())){
							sa_map.put("\"resultCode\"", "\"2\"");
						} else {
							//更新服务器端拦截器信息
							String setFlag = intercepterWs.callSetIntercepter(sa.getClassName(), Boolean.valueOf(sa.getEnable()));
							//更新拦截器信息
							statAopService.saveOrUpdate(sa);
							sa_map.put("\"resultCode\"", "\""+setFlag+"\"");
						}
					}
				}  
			}
		}catch(Exception e){
			log.debug("拦截器信息保存错误："+e.getMessage());
			sa_map.put("\"resultCode\"", "\"1\"");
		}
		print.write("<script>parent.funReturnMethod('"+sa_map+"');</script>");
	}
	
	*//**
	 * 删除拦截器信息
	 * @param sa
	 * @return
	 *//*
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delInfo(@RequestParam(value="delInfo")String delInfo){
		Map<String,Object> sa_map = new HashMap<String,Object>();
		try{
			statAopService.batchDelete(delInfo);
			sa_map.put("\"resultCode\"", "\"0\"");
		}catch(Exception e){
			log.debug("拦截器信息删除错误："+e.getMessage());
			sa_map.put("\"resultCode\"", "\"1\"");
		}
		return sa_map;
	}*/
}
