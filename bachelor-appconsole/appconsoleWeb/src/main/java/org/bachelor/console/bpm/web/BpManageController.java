package org.bachelor.console.bpm.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.bpm.service.IBpmHistoryService;
import org.bachelor.bpm.service.IBpmRepositoryService;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.vo.ProcessInstanceVo;
import org.bachelor.console.auth.ProcessDefinitionVo;
import org.bachelor.console.auth.TaskVo;
import org.bachelor.facade.service.IBpmContextService;
import org.bachelor.facade.service.ITaskGraphicDataService;
import org.bachelor.org.service.IUserService;
import org.bachelor.util.DateUtil;
import org.bachelor.web.util.ConvertData;

/**
 * 流程监控视图
 * @author user
 *
 */
@Controller
@RequestMapping("bp")
public class BpManageController {

	private Log log = LogFactory.getLog(BpManageController.class);
	
	@Autowired
	private IBpmContextService bpmContextService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IBpmRepositoryService bpmRepositoryService;
	
	@Autowired
	private ITaskGraphicDataService taskGraphicDataService;

	@Autowired
	private IBpmHistoryService bpmHistoryService;

	@Autowired
	private IBpmRuntimeService bpmRuntimeService;
	
	/**
	 *  流程监控主页面
	 * @return
	 */
	@RequestMapping("index")
	public ModelAndView index(){
		
		return new ModelAndView("bp/index");
	}
	
	/**
	 * 查询流程信息
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public List<ProcessInstanceVo> query(ProcessInstanceVo piv) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<HistoricProcessInstance> processInstances = bpmContextService.processQuery(piv.getProcessDefinitionId(), 
				piv.getTaskId(),piv.getStartOrgId(),DateUtil.Str2Date(sdf, piv.getStratTime()),DateUtil.Str2Date(sdf, piv.getEndTime()));
		return ConvertData.toProcessInstanceVo(processInstances,userService,
										bpmContextService,bpmRepositoryService,
										taskGraphicDataService,bpmRuntimeService);
	}
	
	/**
	 * 查询流程名称
	 * @return
	 */
	@RequestMapping("queryPds")
	@ResponseBody
	public List<ProcessDefinitionVo> queryPds(){
		List<ProcessDefinition> process_list = bpmContextService.getAllProcessDefinition();
		return ConvertData.toProcessDefinitionVo(process_list);
	}
	
	/**
	 * 查询节点信息
	 * @return
	 */
	@RequestMapping("queryTasks")
	@ResponseBody
	public List<TaskVo> queryTasks(String pdId){
		List<TaskDefinition> task_list = bpmContextService.getAllTaskDefinition(pdId);
	return ConvertData.toTaskVo(task_list);
	
	}
	
	/**
	 * 保存待办人和执行者
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public Map<String,Object> saveTaskUserAndAssignee(ProcessInstanceVo piVo){
		Map<String,Object> result_map = new HashMap<String,Object>();
		try{
			if(piVo.getSaveType().equals("0")){//保存待办人
				List<String> users = new ArrayList<String>();
				users.add(piVo.getAssignee());
				bpmContextService.setTaskCandidateUserOrAssignee(piVo.getId(), users, null, null);
			}
			if(piVo.getSaveType().equals("1")){//保存执行者
				bpmContextService.setTaskCandidateUserOrAssignee(null, null, null, piVo.getAssignee());
			}
			result_map.put("ResultCode", "0");
		}catch(Exception e){
			result_map.put("ResultCode", "1");
			log.info(e.getMessage(), e);
		}
		return result_map;
	}
	
	/**
	 * 删除流程
	 * @return
	 */
	@RequestMapping("deletePd")
	@ResponseBody
	public Map<String,Object> deletePd(ProcessInstanceVo piVo){
		Map<String,Object> result_map = new HashMap<String,Object>();
		try{
			if(piVo!=null && !StringUtils.isEmpty(piVo.getProcessInstanceId())){
				bpmContextService.deleteProcessInstance(piVo.getProcessInstanceId(), piVo.getReason());
				result_map.put("ResultCode", "0");
			} else {
				result_map.put("ResultCode", "2");
			}
		}catch(Exception e){
			result_map.put("ResultCode", "1");
			log.info(e.getMessage(), e);
		}
		return result_map;
	}
	
	/**
	 * 功能: 查询历史流程信息 主页
	 * 作者 曾强 2013-8-21上午10:20:38
	 * @param piv
	 * @return
	 */
	@RequestMapping("historic")
	public ModelAndView historic(ProcessInstanceVo piv){
		 
		return new ModelAndView("bp/historic");
	}
	
	/**
	 * 功能: 查询历史流程信息
	 * 作者 曾强 2013-8-21上午10:20:38
	 * @param piv
	 * @return
	 */
	@RequestMapping("historicBp")
	@ResponseBody
	public List<ProcessInstanceVo> historicBp(ProcessInstanceVo piv) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<HistoricProcessInstance> hpis = bpmHistoryService.getFinishedProcessInstance(piv.getProcessDefinitionId(), 
				piv.getTaskId(),piv.getStartOrgId(),DateUtil.Str2Date(sdf, piv.getStratTime()),DateUtil.Str2Date(sdf, piv.getEndTime()));
		
		return ConvertData.toHisProcessInstanceVo(hpis,userService,
				bpmContextService ,bpmRepositoryService,bpmRuntimeService); 
	}
	
	/**
	 * 功能: 删除历史流程
	 * 作者 曾强 2013-8-22下午2:14:17
	 * @param piId 流程实例ID
	 * @return
	 */
	@RequestMapping("deleteHisBp")
	@ResponseBody
	public Map<String,Object> deleteHistoricBp(String piId){
		Map<String,Object> result_map = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(piId)){
			bpmHistoryService.deleteHistoricProcessInstance(piId);
			result_map.put("resultCode", "0");
		} else {
			result_map.put("resultCode", "1");
		}
		return result_map;
	}
	
	
	/**
	 * 得到图形数据
	 * @param bizKey
	 * @return
	 */
	@RequestMapping("graphicData")
	@ResponseBody
	public Map<String,Object> getGraphicData(String bizKey){
		
		return taskGraphicDataService.findHistoricByBizKey( bizKey);
	}
	
	/**
	 * 返回流程图
	 * 
	 * @param response
	 */
	@RequestMapping("diagram")
	public void diagram(HttpServletResponse response,String bizKey){
		ProcessInstance pi = bpmRuntimeService.findByBizKey(bizKey);
		byte[] bts = bpmContextService.getProcessDiagram(pi.getProcessDefinitionId());
		try {
			response.getOutputStream().write(bts);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
