package org.bachelor.bpm.service.impl;

import java.util.List;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.bpm.service.IBpmHistoryTaskService;
import org.bachelor.bpm.service.IBpmRejectService;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.service.IBpmRuntimeTaskService;

@Service
public class RejectToStartServiceImpl implements IBpmRejectService{
	
	@Autowired
	IBpmRuntimeTaskService bpmTaskService;
	
	@Autowired
	IBpmRuntimeService bpmRuntimeService; 
	
	@Autowired
	IBpmHistoryTaskService historyTaskService;

//	public void reject(String taskId){
//		TaskEx task = bpmTaskService.getTask(taskId);
//		PvmActivity firstActiviti = bpmTaskService.getFirstTask(task.getTask().getProcessDefinitionId());
//		//退回到初始节点
//		bpmRuntimeService.signal(taskId, firstActiviti.getId());
//		//取得初始节点的历史信息
//		String piId = task.getTask().getProcessInstanceId();
//		List<HistoricTaskInstance> hisTasks = 
//				historyTaskService.getHistoricTasks(piId, firstActiviti.getId());
//		if(hisTasks.size() == 0){
//			return ;
//		}
//		//取得最后一次的历史信息
//		HistoricTaskInstance hisTask =hisTasks.get(hisTasks.size()-1);
//		//取得最后一次受理人
//		String assignee = hisTask.getAssignee();
//		//取得当前节点
//		TaskEx newTask = bpmTaskService.getActiveTask(piId);
//		//设置当前节点受理人
//		bpmRuntimeService.setAssignee(newTask.getTask().getId(), assignee);
//		
//	}

	@Override
	public void reject(String taskId,String userId, BaseBpDataEx bpDataEx) {
		TaskEx task = bpmTaskService.getTask(taskId);
		PvmActivity firstActiviti = bpmTaskService.getFirstTask(task.getTask().getProcessDefinitionId());
		//退回到初始节点
		bpmRuntimeService.signal(taskId, userId, firstActiviti.getId(),bpDataEx);
		//取得初始节点的历史信息
		String piId = task.getTask().getProcessInstanceId();
		List<HistoricTaskInstance> hisTasks = 
				historyTaskService.getHistoricTasks(piId, firstActiviti.getId());
		if(hisTasks.size() == 0){
			return ;
		}
		//取得最后一次的历史信息
		HistoricTaskInstance hisTask =hisTasks.get(hisTasks.size()-1);
		//取得最后一次受理人
		String assignee = hisTask.getAssignee();
		//取得当前节点
//		TaskEx newTask = bpmTaskService.getActiveTask(piId).get(0);
//		//设置当前节点受理人
//		bpmRuntimeService.setAssignee(newTask.getTask().getId(), assignee);
	}
}
