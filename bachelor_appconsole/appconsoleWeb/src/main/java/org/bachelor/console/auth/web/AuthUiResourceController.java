package org.bachelor.console.auth.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.auth.domain.AuthUiResource;
import org.bachelor.auth.service.IAuthUiResourceService;
import org.bachelor.auth.vo.AuthUiResourceVo;
import org.bachelor.bpm.service.IBpmRepositoryService;
import org.bachelor.console.auth.ProcessDefinitionVo;
import org.bachelor.console.auth.TaskVo;
import org.bachelor.web.util.ConvertData;

@Controller
@RequestMapping("/auth/ui/")
public class AuthUiResourceController {

	@Autowired
	private IAuthUiResourceService uiService = null;
	
	@Autowired
	private IBpmRepositoryService repositoryService = null;
	
	@RequestMapping("index")
	public ModelAndView directPage(){
		
		return new ModelAndView("/auth/ui/index");
	}
	
	/**
	 * 功能:查询所有信息
	 * 作者 曾强 2013-5-21上午08:14:25
	 * @return
	 */
	@RequestMapping("all")
	@ResponseBody
	public Map<String,Object> findUiInfo(AuthUiResourceVo vo){
		List<AuthUiResourceVo> auth_list = null;
		if("0".equals(vo.getSearchType()) && vo.getAurFuncId()!=null && !StringUtils.isEmpty(vo.getAurFuncId())){
			auth_list = uiService.findByExample(vo);
		}
		if("1".equals(vo.getSearchType())){
			if(vo.getFlowId()!=null && !StringUtils.isEmpty(vo.getFlowId())){
				vo.setFlowId(vo.getFlowId().split(",")[0]);
			}
			auth_list = uiService.findByExample(vo);
		}
		Map<String,Object> ge_map = new HashMap<String,Object>();
		ge_map.put("total", (auth_list!=null?auth_list.size():0));
		ge_map.put("rows", (auth_list!=null?auth_list:new ArrayList<AuthUiResourceVo>()));
		return ge_map;
	}
	
	/**
	 * 功能: 添加修改信息
	 * 作者 曾强 2013-5-21上午08:57:10
	 * @param aur
	 * @return
	 */
	@RequestMapping("add")
	@ResponseBody
	public  Map<String,String> addOrUpdateUiInfo(AuthUiResource aur) throws Exception{
		if(aur!=null){
			if(aur.getId()!=null && aur.getId().trim().equals("")){
				aur.setId(null);
			}
			if(aur.getUiResourceDesc()!=null && !aur.getUiResourceDesc().equals("")){
					String desc = new String(aur.getUiResourceDesc().getBytes("ISO-8859-1"),"UTF-8");
					aur.setUiResourceDesc(desc);
			}
			if(aur.getJoinName()!=null && !aur.getJoinName().equals("")){
				String joinName = new String(aur.getJoinName().getBytes("ISO-8859-1"),"UTF-8");
				aur.setJoinName(joinName);
			}
			if(aur.getFlowName()!=null && !aur.getFlowName().equals("")){
				String flowName = new String(aur.getFlowName().getBytes("ISO-8859-1"),"UTF-8");
				aur.setFlowName(flowName);
			}
			if(aur.getFlowId()!=null && !"".equals(aur.getFlowId())){
				String flows[] = aur.getFlowId().split(",");
				aur.setFlowId(flows[0]);
				aur.setFlowVersion(flows[1]);
			}
			if(StringUtils.isEmpty(aur.getFlowId())){
				aur.setJoinName(null);
				aur.setFlowName(null);
				aur.setJoinId(null);
			}
		}
		uiService.addOrUpdate(aur);
		Map<String,String> ui_map = new HashMap<String,String>();
		ui_map.put("ResultCode", "0");
		return ui_map;
	}
	
	/**
	 * 功能:删除UI元素信息基于ID
	 * 作者 曾强 2013-5-21上午09:46:31
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteById")
	@ResponseBody
	public Map<String,String> deleteUiInfoById(@RequestParam(value="delInfo")String info){
		Map<String,String> ui_map = new HashMap<String,String>();
		try{
			uiService.batchDelete(info);
			ui_map.put("ResultCode", "0");
		}catch(Exception e){
			e.printStackTrace();
			ui_map.put("ResultCode", "1");
		}
		return ui_map;
	}
	
	/**
	 
	 * @return
	 */
	@RequestMapping("allFlow")
	@ResponseBody
	public List<ProcessDefinitionVo> findAllFlow(){
		List<ProcessDefinition> pds = repositoryService.getAllPd();
		return ConvertData.toProcessDefinitionVo(pds);
	}
	
	/**
	 
	 * @return
	 */
	@RequestMapping("findJoinById")
	@ResponseBody
	public List<TaskVo> findJoinById(ProcessDefinitionVo pd){
		if(pd.getId()!=null && !StringUtils.isEmpty(pd.getId()) && !"null".equals(pd.getId())){
			
			return ConvertData.toTaskVo(repositoryService.getAllTaskDefinition(pd.getId()));
		}
		
		return new ArrayList<TaskVo>();
	}
}