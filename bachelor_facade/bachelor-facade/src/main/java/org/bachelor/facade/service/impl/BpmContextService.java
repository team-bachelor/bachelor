package org.bachelor.facade.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.DiagramLayout;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.bpm.common.Constant;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.BpmTaskReview;
import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.bpm.service.IBpmHistoryService;
import org.bachelor.bpm.service.IBpmHistoryTaskService;
import org.bachelor.bpm.service.IBpmRepositoryService;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.service.IBpmRuntimeTaskService;
import org.bachelor.bpm.vo.PiStatus;
import org.bachelor.context.service.IVLService;
import org.bachelor.facade.service.IBpmContextService;
import org.bachelor.facade.service.IContextService;
import org.bachelor.org.domain.User;

@Service
public class BpmContextService implements IBpmContextService {
	@Autowired
	private IBpmRuntimeTaskService bpmTaskService;

	@Autowired
	private IBpmRuntimeTaskService bpmRuntimeTaskService;
	@Autowired
	private IBpmRuntimeService bpmRuntimeService;
	@Autowired
	private IBpmHistoryService bpmHistoryService;

	@Autowired
	private IContextService ctxService;

	@Autowired
	private IVLService vlService;
	@Autowired
	private IBpmHistoryTaskService bpmHistoryTaskService;
	@Autowired
	private IBpmRepositoryService bpmRepositoryService;

	@Override
	public TaskEx getTask(String taskId, String userId, String assignmentType) {
		return bpmTaskService.getTask(taskId, userId, assignmentType);
	}

	@Override
	public ProcessInstance startProcessInstanceByKey(String pdkey,
			BaseBpDataEx bpInfoEx) {
		return bpmRuntimeService.startProcessInstanceByKey(pdkey, bpInfoEx);
	}

	@Override
	public List<TaskEx> getTaskByCandidateUser(String pdkey, String userId) {
		return bpmTaskService.getTaskByCandidateUser(pdkey, userId);
	}

	@Override
	public List<TaskEx> getTaskByAssignee(String pdkey, String userId) {

		return bpmTaskService.getTaskByAssignee(pdkey, userId);
	}

	@Override
	public void setAssignee(String taskId, String userId) {
		bpmRuntimeService.setAssignee(taskId, userId);
	}

	@Override
	public void completeTask() {
		bpmRuntimeService.complete();
	}

	@Override
	public Comment addComment(String taskId, String processInstanceId,
			String message) {
		return bpmTaskService.addComment(taskId, processInstanceId, message);
	}

	@Override
	public BaseBpDataEx getBpDataEx() {
		return (BaseBpDataEx) vlService
				.getRequestAttribute(Constant.BPM_BP_DATA_EX_KEY);
	}

	@Override
	public void complete() {
		bpmRuntimeService.complete();
	}

	@Override
	public TaskEx getPreTask(String taskId) {
		return bpmHistoryTaskService.getPreTask(taskId);
	}

	@Override
	public void deleteProcessInstance(String processInstanceId,
			String deleteReason) {
		bpmRuntimeService
				.deleteProcessInstance(processInstanceId, deleteReason);

	}

	@Override
	public List<HistoricProcessInstance> processQuery(String processId,
			String taskId, String startedOrgId, Date startTime, Date endTime) {
		return bpmRepositoryService.queryProcess(processId, taskId,
				startedOrgId, startTime, endTime);
	}

	@Override
	public void setTaskCandidateUserOrAssignee(String taskId,
			List CandidateUser, List CandidateGroup, String Assignee) {
		if (CandidateUser != null || CandidateGroup != null) {
			bpmRuntimeService.addCandidateUser(taskId, CandidateUser,
					CandidateGroup);
		}
		if (Assignee != null) {
			bpmRuntimeService.setAssignee(taskId, Assignee);
		}
	}

	@Override
	public List<ProcessDefinition> getAllProcessDefinition() {
		return bpmRepositoryService.getAllPd();
	}

	@Override
	public List<ActivityImpl> getProcessActivitImpl(String bizKey) {
		return bpmRepositoryService.getProcessActivitImpl(bizKey);
	}

	@Override
	public List<HistoricTaskInstance> processExecuteHistory(
			String processInstanceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getProcessDiagram() {
		BaseBpDataEx bpDataEx = (BaseBpDataEx) vlService
				.getRequestAttribute(Constant.BPM_BP_DATA_EX_KEY);
		if (bpDataEx != null && bpDataEx.getTaskEx() != null
				&& bpDataEx.getTaskEx().getTask() != null) {
			String pdId = bpDataEx.getTaskEx().getTask()
					.getProcessDefinitionId();
			return getProcessDiagram(pdId);
		}
		return null;
	}

	@Override
	public byte[] getProcessDiagram(String pdId) {
		return bpmRepositoryService.getProcessDiagram(pdId);
	}

	@Override
	public BaseBpDataEx getBpDataExByBizKey(String bizKey) {
		return bpmRuntimeService.getBpDataExByBizKey(bizKey);
	}

	@Override
	public List<User> getTaskCandidateUser() {
		return bpmRuntimeService.getTaskCandidateUser();
	}

	@Override
	public List<User> getTaskCandidateUserByBizKey(String bizKey) {
		return bpmRuntimeService.getTaskCandidateUserByBizKey(bizKey);
	}

	// @Override
	// public List<User> getNextTaskCandidateUserByBizKey(String bizKey) {
	// return bpmRuntimeService.getNextTaskCandidateUserByBizKey(bizKey);
	//
	// }

	@Override
	public DiagramLayout getProcessDiagramLayout() {
		BaseBpDataEx bpDataEx = (BaseBpDataEx) vlService
				.getRequestAttribute(Constant.BPM_BP_DATA_EX_KEY);
		if (bpDataEx != null && bpDataEx.getTaskEx() != null
				&& bpDataEx.getTaskEx().getTask() != null) {
			String pdId = bpDataEx.getTaskEx().getTask()
					.getProcessDefinitionId();
			return getProcessDiagramLayout(pdId);
		}
		return null;
	}

	@Override
	public DiagramLayout getProcessDiagramLayout(String pdId) {
		return bpmRepositoryService.getProcessDiagramLayout(pdId);
	}

	@Override
	public void setTaskVariable(String executionId, String variableName,
			String variableValue) {
		bpmTaskService
				.setTaskVariable(executionId, variableName, variableValue);
	}

	@Override
	public Comment getTaskComment(String taskId) {
		return bpmRuntimeService.getTaskComment(taskId);
	}

	@Override
	public List<HistoricTaskInstance> getFinishedTaskByPiid(
			String processInstanceId) {
		return bpmHistoryTaskService.getFinishedTaskByPiid(processInstanceId);
	}

	@Override
	public List<TaskEx> getUnFinishedTaskByPiid(String piid) {
		return bpmRuntimeService.getUnFinishedTaskByPiid(piid);
	}

	@Override
	public List<HistoricTaskInstance> getProcessHistoricByBizKey(String bizKey) {
		return bpmHistoryService.getProcessHistoricByBizKey(bizKey);
	}

	@Override
	public List<User> getTaskCandidateUserByDefKey(String piId,
			String taskDefKey) {
		return bpmRuntimeService.getTaskCandidateUserByDefKey(piId, taskDefKey);
	}

	@Override
	public TaskEx getActiveTask(String piId) {

		return bpmTaskService.getActiveTask(piId);
	}

	@Override
	public <T extends BaseBpDataEx> BpmTaskReview completeReview(String taskId,
			String title, String content, String comment,
			String fallBackReason, String result, String assigneer, T bpDataEx) {
		return bpmRuntimeService.completeReview(taskId, title, content,
				comment, fallBackReason, result, assigneer, bpDataEx);
	}

	@Override
	public List<BpmTaskReview> getTaskReviewsByTaskId(String taskId) {
		return bpmRuntimeService.getTaskReviewsByTaskId(taskId);
	}

	@Override
	public List<BpmTaskReview> getTaskReviewsByBizKey(String bizKey) {
		return bpmRuntimeService.getTaskReviewsByBizKey(bizKey);
	}

	@Override
	public void setPiVariable(String piId, String variableName,
			String variableValue) {
		bpmRuntimeService.setPiVariable(piId, variableName, variableValue);

	}

	@Override
	public Object getPiVariable(String piId, String variableName) {
		return bpmRuntimeService.getPiVariable(piId, variableName);
	}

	@Override
	public PiStatus getPiStatusByBizKey(String bizKey) {
		return bpmRuntimeService.getPiStatusByBizKey(bizKey);
	}

	@Override
	public BaseBpDataEx getFinishedBpDataExByBizKey(String bizKey) {
		return bpmHistoryService.getBpDataExByBizKey(bizKey);
	}

	@Override
	public List<TaskEx> getTaskByCandidateUser(String userId) {
		return bpmTaskService.getTaskByCandidateUser(userId);
	}

	@Override
	public List<TaskEx> getTaskByAssignee(String userId) {
		return bpmTaskService.getTaskByAssignee(userId);
	}

	@Override
	public List<BaseBpDataEx> getBpDataExByCandidateUser(String pdkey,
			String candidateUserId) {

		return bpmTaskService
				.getBpDataExByCandidateUser(pdkey, candidateUserId);
	}

	@Override
	public List<BaseBpDataEx> getBpDataExByAssignee(String pdkey,
			String assignee) {

		return bpmTaskService.getBpDataExByAssignee(pdkey, assignee);
	}

	@Override
	public List<TaskDefinition> getFirstTaskDefByBizKey(String bizKey) {
		return bpmRuntimeTaskService.getFirstTaskDef(bizKey);
	}

	@Override
	public List<TaskDefinition> getLastTaskDefByBizKey(String bizKey) {
		return bpmRuntimeTaskService.getLastTaskDef(bizKey);
	}

	@Override
	public List<User> getNextTaskCandidateUser(String bizKey) {
		return bpmRuntimeService.getNextTaskCandidateUser(bizKey);
	}

	@Override
	public Map<PvmTransition, TaskDefinition> getNextTaskDefinition(
			String bizKey) {
		return bpmRuntimeService.getNextTaskDefinition(bizKey);
	}

	@Override
	public List<User> getNextTaskCandidateUser(String bizKey,
			String outGoingTransValue, BaseBpDataEx bpDataEx) {
		return bpmRuntimeService.getNextTaskCandidateUser(bizKey,
				outGoingTransValue, bpDataEx);
	}

	@Override
	public List<HistoricTaskInstance> getHistoricTasks(String bizKey,
			String taskDefKey) {
		ProcessInstance pi = bpmRuntimeService.findByBizKey(bizKey);
		if (pi == null)
			return null;
		List<HistoricTaskInstance> list = bpmHistoryTaskService
				.getHistoricTasks(pi.getProcessInstanceId(), taskDefKey);
		if (list == null || list.size() == 0) {
			return null;
		} else {
			return list;
		}
	}

}
