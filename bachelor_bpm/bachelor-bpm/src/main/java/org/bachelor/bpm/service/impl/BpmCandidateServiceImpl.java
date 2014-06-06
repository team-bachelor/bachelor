package org.bachelor.bpm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.bachelor.bpm.service.IAuthService;
import org.bachelor.bpm.service.IBpmCandidateService;
import org.bachelor.bpm.service.IBpmEngineService;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.service.IBpmRuntimeTaskService;
import org.bachelor.core.entity.IBaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BpmCandidateServiceImpl implements IBpmCandidateService{
@Autowired
private IBpmRuntimeService bpmRuntimeService;
@Autowired
private IBpmEngineService bpmEngineService;
@Autowired
private IBpmRuntimeTaskService bpmRuntimeTaskService;
@Autowired
private IAuthService authService;
	
	@Override
	public List<IBaseEntity> getNextTaskCandidateUser(String bizKey) {
		ProcessInstance pi = bpmRuntimeService.findByBizKey(bizKey);
		Map<PvmTransition, ActivityImpl> actImplMap = bpmEngineService.getNextActivityImpl(bizKey);
		Map<PvmTransition, TaskDefinition> taskDefMap = new HashMap<PvmTransition, TaskDefinition>();
		bpmEngineService.warpTaskDefMap(actImplMap, taskDefMap);
		if (taskDefMap == null)
			return null;
		List<IBaseEntity> userList = new ArrayList<IBaseEntity>();
		List<TaskDefinition> lastTaskDefList = bpmRuntimeTaskService
				.getLastTaskDef(bizKey);
		boolean isLastTask = Boolean.FALSE;
		// 返回每个transition对应的task中定义的候选人
		for (TaskDefinition taskDef : taskDefMap.values()) {
			userList.addAll(bpmRuntimeTaskService.getUserByTaskDefinition2(taskDef,
					pi.getProcessInstanceId()));
		}
		return userList;
	}

	@Override
	public List<IBaseEntity> getUsersByTaskId(String taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public List<String> getGroupIdByUser(String userId){
		List<? extends IBaseEntity> roleList= authService.findRolesByUserId(userId);
		if(roleList!=null || roleList.size()==0)
		return null;
		List<String> roleIds=new ArrayList<String>();
		for (IBaseEntity role : roleList) {
			roleIds.add(role.getId());
		}
		return roleIds;
	}

	@Override
	public List<String> getTaskCandidateUserByBpDateEX(String candidateArg) {
        List<String> candidateUserList=new ArrayList<String>();
		return candidateUserList;
	}
	
	
}
