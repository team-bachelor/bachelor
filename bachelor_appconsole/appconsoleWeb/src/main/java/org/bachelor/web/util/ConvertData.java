package org.bachelor.web.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;

import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.bpm.service.IBpmHistoryService;
import org.bachelor.bpm.service.IBpmHistoryTaskService;
import org.bachelor.bpm.service.IBpmRepositoryService;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.vo.GraphicData;
import org.bachelor.bpm.vo.ProcessInstanceVo;
import org.bachelor.console.auth.ProcessDefinitionVo;
import org.bachelor.console.auth.TaskVo;
import org.bachelor.facade.service.IBpmContextService;
import org.bachelor.facade.service.ITaskGraphicDataService;
import org.bachelor.org.domain.User;
import org.bachelor.org.service.IUserService;

/**
 * 转换数据
 * @author user
 *
 */
public class ConvertData {

	/**
	 * 转换流程对象
	 * @param pds
	 * @return
	 */
	public static List<ProcessDefinitionVo> toProcessDefinitionVo(List<ProcessDefinition> pds){
		List<ProcessDefinitionVo> pdvs = new ArrayList<ProcessDefinitionVo>(); 
		if(pds!=null && pds.size()>0){
			for(ProcessDefinition pd:pds){
				ProcessDefinitionVo vo = new ProcessDefinitionVo();
				vo.setId(pd.getId());
				vo.setDescription(pd.getDescription());
				vo.setKey(pd.getKey());
				vo.setName(pd.getName());
				vo.setVersion(pd.getVersion());
				pdvs.add(vo);
			}
		}
		return pdvs;
	}
	
	/**
	 * 转换节点
	 * @param tasks
	 * @return
	 */
	public static List<TaskVo> toTaskVo(List<TaskDefinition> tasks){
		List<TaskVo> taskVos = new ArrayList<TaskVo>();
		if(tasks!=null && tasks.size()>0){
			for(TaskDefinition task : tasks){
				TaskVo vo = new TaskVo();
				vo.setKey(task.getKey());
				vo.setDescription(task.getDescriptionExpression()!=null?task.getDescriptionExpression().getExpressionText():"");
				vo.setName(task.getNameExpression().getExpressionText());
				taskVos.add(vo);
			}
		}
		return taskVos;
	}
	
	/**
	 * 把执行实体转化为流程实例
	 * @param process_list
	 * @return processType get未完成流程，NULL已完成流程
	 */
	public static List<ProcessInstanceVo> toProcessInstanceVo(List<HistoricProcessInstance> processInstances,
				IUserService userService,IBpmContextService bpmContextService,IBpmRepositoryService bpmRepositoryService,
				ITaskGraphicDataService taskGraphicDataService,IBpmRuntimeService bpmRuntimeService){
		List<ProcessInstanceVo> pivs = new ArrayList<ProcessInstanceVo>();
		if(processInstances==null || processInstances.size()<=0){
			
			return pivs;
		}
		
		for(HistoricProcessInstance pi:processInstances){
			
			TaskEx taskEx = bpmContextService.getActiveTask(pi.getId());
			
			/** 节点VO实例 **/
			ProcessInstanceVo piv = new ProcessInstanceVo();
			
			/** 获取流程数据 **/
			//piv.setProcessDatas(bpmRuntimeService.getPiVariableMap(pi.getId()));
			
			//节点详细信息 
			if(!StringUtils.isEmpty(pi.getBusinessKey())){
				List<HistoricTaskInstance> htis = bpmContextService.getProcessHistoricByBizKey(pi.getBusinessKey());
				if(htis!=null){
					List<GraphicData> gds = taskGraphicDataService.getGraphicDatasByHistoricTaskInstance(null,htis, taskEx.getTask().getProcessInstanceId());
					piv.setGds(gds);
				} 
			}
			/** 取得受理人 **/
			if(taskEx!=null && taskEx.getTask()!=null 
					&& taskEx.getTask().getAssignee()!=null && !"".equals(taskEx.getTask().getAssignee())){
				piv.setAssignee(taskEx.getTask().getAssignee());
				User user = userService.findById(piv.getAssignee());
				piv.setAssigneeUnit(user.getOwnerOrg().getNamePath());
				piv.setAssigneeName(user.getUsername());
			}
			/** 取得发起用户信息 **/
			if(pi.getStartUserId()!=null && !"".equals(pi.getStartUserId())){
				User user = userService.findById(pi.getStartUserId());
				if(user!=null){
					piv.setStartOrgId(user.getOwnerOrgId());
					piv.setStartorgName(user.getOwnerOrg().getNamePath());
					piv.setStartUserName(user.getUsername());
				}
			}
			
			/** 得到流程名称 **/
			ProcessDefinition processDefinition = bpmRepositoryService.getPdById(pi.getProcessDefinitionId());
			piv.setProcessName(processDefinition!=null?processDefinition.getName():"");
			
			piv.setProcessInstanceId(taskEx.getTask().getProcessInstanceId());
			piv.setProcessDefinitionId(taskEx.getTask().getProcessDefinitionId());
			piv.setBizKey(pi.getBusinessKey());
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			/** 节点开始开始时间 **/
			piv.setStratTime(taskEx.getTask().getCreateTime()!=null?sdf.format(taskEx.getTask().getCreateTime()):"");
			/** 流程发起开始时间 **/
			piv.setStartDate(pi.getStartTime()!=null?sdf.format(pi.getStartTime()):"");
			piv.setEndTime(pi.getEndTime()!=null?sdf.format(pi.getEndTime()):"");
			
			piv.setTaskName(taskEx.getTask().getName());
			
			piv.setId(pi.getId());
			
			pivs.add(piv);
		} 
		return pivs;
	}
	
	/**
	 * 把执行实体转化为流程实例
	 * @param process_list
	 * @return processType get未完成流程，NULL已完成流程
	 */
	public static List<ProcessInstanceVo> toHisProcessInstanceVo(List<HistoricProcessInstance> processInstances,
				IUserService userService,IBpmContextService bpmContextService,
				IBpmRepositoryService bpmRepositoryService,IBpmRuntimeService bpmRuntimeService){
		List<ProcessInstanceVo> pivs = new ArrayList<ProcessInstanceVo>();
		if(processInstances==null || processInstances.size()<=0){
			
			return pivs;
		}
		for(HistoricProcessInstance pi:processInstances){
			/** 节点VO实例 **/
			ProcessInstanceVo piv = new ProcessInstanceVo();
			
			ProcessDefinition pd = bpmRepositoryService.getPdById(pi.getProcessDefinitionId());
			
			//节点详细信息 
			List<HistoricTaskInstance> htis = bpmContextService.getProcessHistoricByBizKey(pi.getBusinessKey());
			if(htis!=null){
				List<GraphicData> tempList = new ArrayList<GraphicData>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(htis!=null && htis.size()>0){
					for(HistoricTaskInstance hti : htis){
						GraphicData gd = new GraphicData();
						gd.setId(hti.getTaskDefinitionKey());
						gd.setTitle(hti.getName());
						gd.setStartTime(hti.getStartTime()!=null?sdf.format(hti.getStartTime()):"");
						gd.setEndTime(hti.getEndTime()!=null?sdf.format(hti.getEndTime()):"");
						/** 待办人信息**/
					   acpuireCandideteInfo(
								bpmRuntimeService.getHisTaskCandidateUserByDefKey(pd.getId(),hti.getProcessInstanceId(),hti.getTaskDefinitionKey())//待办人用户集合
								,gd);
						if(hti.getAssignee()!=null && !"".equals(hti.getAssignee())){
							User user = userService.findById(hti.getAssignee());
							gd.setAssignee(hti.getAssignee());
							gd.setAssigneeUnit(user.getOwnerOrg().getNamePath());
							gd.setAssigneeName(user.getUsername());
						}  
						tempList.add(gd);
					}
					
				}
				piv.setGds(tempList);
			} 
		 
			/** 取得发起用户信息 **/
			if(pi.getStartUserId()!=null && !"".equals(pi.getStartUserId())){
				User user = userService.findById(pi.getStartUserId());
				if(user!=null){
					piv.setStartOrgId(user.getOwnerOrgId());
					piv.setStartorgName(user.getOwnerOrg().getNamePath());
					piv.setStartUserName(user.getUsername());
				}
			}
			
			/** 得到流程名称 **/
			ProcessDefinition processDefinition = bpmRepositoryService.getPdById(pi.getProcessDefinitionId());
			piv.setProcessName(processDefinition!=null?processDefinition.getName()+"(V"+pd.getVersion()+")":"");
			
			piv.setProcessInstanceId(pi.getId());
			piv.setProcessDefinitionId(pi.getProcessDefinitionId());
			piv.setBizKey(pi.getBusinessKey());
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 
			/** 流程发起开始时间 **/
			piv.setStartDate(pi.getStartTime()!=null?sdf.format(pi.getStartTime()):"");
			
			piv.setEndTime(pi.getStartTime()!=null?sdf.format(pi.getStartTime()):"");
			
			piv.setId(pi.getId());
			
			pivs.add(piv);
		}
		return pivs;
	}
	
	/**
	 * 功能: 取得候选人信息
	 * 作者 曾强 2013-8-23下午5:07:35
	 * @param users
	 * @param gd
	 */
	public static void acpuireCandideteInfo(List<User> users,GraphicData gd){
		if(users!=null && users.size()>0){
			StringBuffer candidate = new StringBuffer();
			StringBuffer candidateName = new StringBuffer();
			for(User user : users){
				candidate.append(user.getId()).append(",");
				candidateName.append(user.getUsername()).append(",");
			}
			gd.setCandidate(candidate.toString().length()>0?candidate.toString().substring(0, candidate.toString().length()-1):candidate.toString());
			gd.setCandidateName(candidateName.toString().length()>0?candidateName.toString().substring(0, candidateName.toString().length()-1):candidateName.toString());
		}
	}
}
