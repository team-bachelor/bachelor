package org.bachelor.demo.devman.web;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.demo.devman.biz.IDevManBiz;
import org.bachelor.demo.devman.bp.DevManBpDataEx;
import org.bachelor.demo.devman.domain.DevManDomain;
import org.bachelor.auth.common.AuthConstant;
import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.bpm.service.IBpmHistoryService;
import org.bachelor.bpm.service.IBpmRepositoryService;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.vo.ProcessInstanceVo;
import org.bachelor.context.service.IVLService;
import org.bachelor.core.exception.BusinessException;
import org.bachelor.facade.service.IBpmContextService;
import org.bachelor.facade.service.ITaskGraphicDataService;
import org.bachelor.org.domain.User;
import org.bachelor.org.service.IUserService;

@Controller
@RequestMapping("devman")
public class DevManController {

	@Autowired
	private IDevManBiz devManBiz;
	
	@Autowired
	private IBpmContextService bpmCtxService;
	
	@Autowired
	//测试用，正式开发中不建议直接使用IBpmRuntimeService
	private RepositoryService repositoryService;
	
	@Autowired
	private IVLService vlService;
	
	@Autowired
	private ITaskGraphicDataService taskGraphicDataService;
	
	@Autowired
	//测试用，正式开发中不建议直接使用IBpmRuntimeService
	private IBpmHistoryService bpmHistoryService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	//测试用，正式开发中不建议直接使用IBpmRuntimeService
	private IBpmRepositoryService bpmRepositoryService;
	
	@Autowired
	//测试用，正式开发中不建议直接使用IBpmRuntimeService
	private IBpmRuntimeService bpmRuntimeService;
	

	 
	/**
	 * 显示登录人待办
	 * @return
	 */
	@RequestMapping("index")
	public ModelAndView index(@RequestParam(value = "funcId",required = false) String funcId){
		List<TaskEx> taskList = devManBiz.getWaitTask();
		List<TaskEx> taskList2 = devManBiz.getClaimedTask();
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("waitTasks", taskList);
		map.put("claimedTasks", taskList2);
		
		if(funcId==null || "".equals(funcId)){
			funcId = (String) vlService.getSessionAttribute(AuthConstant.FUNCTION_ID);
		}
		map.put("funcId", funcId);
		
		ModelAndView mav = new ModelAndView("devman/index", "model", map);
		return mav;
	}
	
	@RequestMapping("users")
	@ResponseBody
	public List<User> users(String bk){
		List<User> users = bpmCtxService.getTaskCandidateUserByBizKey(bk);
		
		return users;
	}
	
	@RequestMapping("nextUsers")
	@ResponseBody
	public List<User> nextUsers(String bk){
		List<User> users = bpmCtxService.getNextTaskCandidateUserByBizKey(bk);
		
		return users;
	}
	
	/**
	 * 发起设备维修申请流程
	 * @return
	 */
	@RequestMapping("start")
	public ModelAndView start(){
		devManBiz.startBp();
		ModelAndView mav = new ModelAndView("redirect:index.htm");
		return mav;
	}
	
	/**
	 * 发起设备维修申请流程
	 * @return
	 */
	@RequestMapping("end")
	public ModelAndView end(){
		devManBiz.endBp();
		ModelAndView mav = new ModelAndView("redirect:index.htm");
		return mav;
	}
	
	/**
	 * 显示申请页面
	 * @param devMan
	 * @return
	 */
	@RequestMapping("initApply")
	public ModelAndView initApply(HttpServletRequest request,@RequestParam(value = "funcId",required = false) String funcId){
		DevManBpDataEx bpDataEx = (DevManBpDataEx)bpmCtxService.getBpDataEx();
		String bizKey = bpDataEx.getDomainId();
		DevManDomain devMan = devManBiz.findById(bizKey); 
		vlService.setSessionAttribute(AuthConstant.FUNCTION_ID, funcId);
		ModelAndView mav = new ModelAndView("devman/approve", "model",devMan);
		return mav;
	}
	
	/**
	 * 处理申请信息，流转流程
	 * @return
	 */
	@RequestMapping("doApply")
	public ModelAndView doApply(DevManDomain devMan){
		devManBiz.apply(devMan);
		ModelAndView mav = new ModelAndView("redirect:index.htm","model", devMan);
		return mav;

	}
	
	/**
	 * 显示审核页面
	 * @param devMan
	 * @return
	 */
	@RequestMapping("doVerify")
	public ModelAndView doVerify(DevManDomain devMan){
		devManBiz.verify(devMan);
		ModelAndView mav = new ModelAndView("redirect:index.htm");
		return mav;
	}
	
	/**
	 * 审核维修申请，流转流程
	 * @return
	 */
	@RequestMapping("initVerify")
	public ModelAndView initVerify(String funcId){
		DevManBpDataEx bpDataEx = (DevManBpDataEx)bpmCtxService.getBpDataEx();
		String id = bpDataEx.getDomainId();
		DevManDomain devMan = devManBiz.findById(id);
		ModelAndView mav = new ModelAndView("devman/approve","model",devMan);
		vlService.setSessionAttribute(AuthConstant.FUNCTION_ID, funcId);
		return mav;
	}
	
	/**
	 * 得到图形数据
	 * @param bizKey
	 * @return
	 */
	@RequestMapping("graphicData")
	@ResponseBody
	public Map<String,Object> getGraphicData(String bizKey){
		
		return taskGraphicDataService.findByBizKey( bizKey);
	}
	
	/**
	 * 返回流程图
	 * 
	 * @param response
	 */
	@RequestMapping("diagram")
	public void diagram(HttpServletResponse response){
		DevManBpDataEx bpDataEx = (DevManBpDataEx)bpmCtxService.getBpDataEx();
		String pdId = bpDataEx.getTaskEx().getTask().getProcessDefinitionId();
		InputStream is = repositoryService.getProcessDiagram(pdId);
		/*DiagramLayout layout = repositoryService.getProcessDiagramLayout(pdId);*/
		try {
			byte[] bytes = new byte[is.available()];
			is.read(bytes);
			response.getOutputStream().write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 返回历史流程图
	 * 
	 * @param response
	 */
	@RequestMapping("diagramHistoric")
	public void diagramHistoric(HttpServletResponse response){
		DevManBpDataEx bpDataEx = (DevManBpDataEx)bpmCtxService.getBpDataEx();
		
		HistoricProcessInstance pi = bpmHistoryService.findByPiId(bpDataEx.getPiId());
		InputStream is = repositoryService.getProcessDiagram(pi.getProcessDefinitionId());
		try {
			byte[] bytes = new byte[is.available()];
			is.read(bytes);
			response.getOutputStream().write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到图形数据
	 * @param bizKey
	 * @return
	 */
	@RequestMapping("hisGraphicData")
	@ResponseBody
	public Map<String,Object> getHisGraphicData(String bizKey){
		
		return taskGraphicDataService.findHistoricByBizKey(bizKey);
	}
	
	/**
	 *  得到已经完成的流程
	 * 	
	 * @param response
	 */
	@RequestMapping("initHistorics")
	public ModelAndView initHistorics(){
		List<HistoricProcessInstance> hpis = bpmHistoryService.getFinishedProcessInstance(null, null, null, null, null);
		Map<String,Object> his_map = new HashMap<String,Object>();
		List<ProcessInstanceVo> pivs = new ArrayList<ProcessInstanceVo>();
		if(hpis!=null && hpis.size()>00){
					for(HistoricProcessInstance pi:hpis){
						ProcessDefinition pd = bpmRepositoryService.getPdById(pi.getProcessDefinitionId());
						/** 节点VO实例 **/
						ProcessInstanceVo piv = new ProcessInstanceVo();
						/** 取得发起用户信息 **/
						if(pi.getStartUserId()!=null && !"".equals(pi.getStartUserId())){
							User user = userService.findById(pi.getStartUserId());
							if(user!=null){
								piv.setStartOrgId(user.getOwnerOrgId());
								piv.setStartorgName(user.getOwnerOrgName());
								piv.setStartUserName(user.getUsername());
							}
						}

						piv.setProcessInstanceId(pi.getId());
						piv.setProcessDefinitionId(pi.getProcessDefinitionId());
						
						piv.setBizKey(pi.getBusinessKey());
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						
						/** 节点开始开始时间 
						piv.setStratTime(taskEx.getTask().getCreateTime()!=null?sdf.format(taskEx.getTask().getCreateTime()):"");**/
						/** 流程发起开始时间 **/
						piv.setStartDate(pi.getStartTime()!=null?sdf.format(pi.getStartTime()):"");
						piv.setEndTime(pi.getEndTime()!=null?sdf.format(pi.getEndTime()):"");
						 
						/** 得到流程名称 **/ 
						piv.setProcessName(pd.getName());
						
						piv.setId(pi.getId());
						
						pivs.add(piv);
					} 
		}
		
		his_map.put("historics", pivs); 
		return new ModelAndView("devman/historic","model",his_map);
	}
	
	@RequestMapping("historyBpDataEx")
	@ResponseBody
	public DevManBpDataEx historyBpDataEx(String bizKey){
		return (DevManBpDataEx)bpmCtxService.getFinishedBpDataExByBizKey(bizKey);
	}
	
	@RequestMapping("jsonex")
	@ResponseBody
	public String jsonex(){
		throw new BusinessException("测试异常");
	}
	
	@RequestMapping("htmex")
	public ModelAndView htmex(){
		throw new BusinessException("测试异常");
	}
	
	@RequestMapping("historyPi")
	@ResponseBody
	public String historyPi(String piId, String varName){
		return bpmHistoryService.getPiVariable(piId, varName).toString();
	}
}
