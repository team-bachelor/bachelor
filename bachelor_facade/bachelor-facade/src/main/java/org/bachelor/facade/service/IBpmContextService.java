package org.bachelor.facade.service;

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

import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.BpmTaskReview;
import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.bpm.vo.PiStatus;
import org.bachelor.org.domain.User;

public interface IBpmContextService {
	/**
	 * 根据节点id和用户id，返回对应的节点对象
	 * 
	 * @param taskId
	 * @param userId
	 * @param assignmentType
	 *            角色分派类型（可以为null）
	 * @return 指定TaskId和userId的Task
	 */
	public TaskEx getTask(String taskId, String userId, String assignmentType);

	/**
	 * 启动流程
	 * 
	 * @param pdkey
	 *            流程定义Key
	 * @param bpInfoEx
	 *            流程和业务信息交换对象
	 * @return 流程实例
	 */
	public ProcessInstance startProcessInstanceByKey(String pdkey,
			BaseBpDataEx bpInfoEx);

	/**
	 * 根据指定的候选人id，返回对应候选人的节点实例 排序规则：节点开始时间顺序
	 * 
	 * @param pdkey
	 * @param userId
	 * @return 被分派指定候选人的节点对象
	 */
	public List<TaskEx> getTaskByCandidateUser(String pdkey, String userId);

	/**
	 * 根据指定的执行人id，返回对应代理人的节点实例 排序规则：节点开始时间顺序
	 * 
	 * @param pdkey
	 * @param userId
	 * @return 被分派指定代理人的节点对象
	 */
	public List<TaskEx> getTaskByAssignee(String pdkey, String userId);

	/**
	 * 根据指定的候选人id，返回对应候选人的节点实例 排序规则：节点开始时间顺序
	 * 
	 * @param userId
	 *            候选人id
	 * @return 被分派指定候选人的节点对象
	 */
	public List<TaskEx> getTaskByCandidateUser(String userId);

	/**
	 * 根据指定的执行人id，返回对应代理人的节点实例 排序规则：节点开始时间顺序
	 * 
	 * @param userId
	 *            执行人id
	 * @return 被分派指定代理人的节点对象
	 */
	public List<TaskEx> getTaskByAssignee(String userId);

	/**
	 * 为节点分派执行人
	 * 
	 * @param taskId
	 * @param userId
	 */
	public void setAssignee(String taskId, String userId);

	/**
	 * 完成节点并进行流程流转
	 * 
	 */
	public void completeTask();

	/**
	 * 为业务流程或是流程节点添加说明
	 * 
	 * @param taskId
	 * @param processInstanceId
	 * @param message
	 * @return 返回封装业务流程或是流程节点的对象
	 */
	public Comment addComment(String taskId, String processInstanceId,
			String message);

	/**
	 * 取得当前的流程数据交换类(BpDataEx)
	 * 
	 * @return 流程数据交换类
	 */
	public BaseBpDataEx getBpDataEx();

	/**
	 * 完成节点并进行流程流转
	 * 
	 */
	public void complete();

	/**
	 * 取得指定TaskId的上一个Task 只在顺序流程中返回值是有效的
	 * 
	 * @param taskId
	 * 
	 * @return 指定TaskId的上一个Task
	 */
	public TaskEx getPreTask(String taskId);

	/**
	 * 根据流程实例id删除对应流程
	 * 
	 * @param processInstanceId
	 *            流程实例id
	 * @param deleteReason
	 *            删除原因
	 */
	public void deleteProcessInstance(String processInstanceId,
			String deleteReason);

	/**
	 * 流程信息查询方法
	 * 
	 * @param processId
	 *            流程id
	 * @param taskId
	 *            节点id
	 * @param startedOrgId
	 *            发起公司
	 * @param startTime
	 *            发起时间（前区间）
	 * @param endTime
	 *            发起时间（后区间）
	 * @return 流程实例集合
	 */
	public List<HistoricProcessInstance> processQuery(
			String processDefinitionId, String taskId, String startedOrgId,
			Date startTime, Date endTime);

	/**
	 * 设置节点的候选人和执行人
	 * 
	 * @param CandidateUser
	 *            候选人id
	 * @param Assignee
	 *            执行人id
	 */
	public void setTaskCandidateUserOrAssignee(String taskId,
			List CandidateUser, List CandidateGroup, String Assignee);

	/**
	 * 获取全部流程定义
	 * 
	 * @return 流程定义集合
	 */
	public List<ProcessDefinition> getAllProcessDefinition();

	/**
	 * 根据流程定义id,获取对应流程的全部节点对象
	 * 
	 * @return 节点对象集合
	 */
	public List<ActivityImpl> getProcessActivitImpl(String bizKey);

	/**
	 * 根据流程实例id，获取流程的执行历史
	 * 
	 * @param processInstanceId
	 *            流程实例id
	 * @return 流程节点历史对象
	 */
	public List<HistoricTaskInstance> processExecuteHistory(
			String processInstanceId);

	/**
	 * 根据上下文信息，取得当前流程的流程图
	 * 
	 * @return 流程图的二进制数组
	 */
	public byte[] getProcessDiagram();

	/**
	 * 取得指定流程定义Id的流程图
	 * 
	 * @param pdId
	 *            流程定义Id
	 * @return 流程图的二进制数组
	 */
	public byte[] getProcessDiagram(String pdId);

	/**
	 * 根据上下文信息，取得当前流程的节点图形信息
	 * 
	 * @return 当前流程的节点图形信息
	 */
	public DiagramLayout getProcessDiagramLayout();

	/**
	 * 根据流程定义id取得流程图的节点图形信息
	 * 
	 * @param pdId
	 *            流程定义Id
	 * @return 流程图节点图形信息
	 */
	public DiagramLayout getProcessDiagramLayout(String pdId);

	/**
	 * 根据bizKey，取得BpInfoEx对象
	 * 先查询运行中流程，如果有相应的bpdataex则返回，如果没有再查询已经完成的流程，如果没有则返回null。
	 * 
	 * @param bizKey
	 *            流程业务对象key
	 * @return 业务流程封装数据对象
	 */
	public BaseBpDataEx getBpDataExByBizKey(String bizKey);

	/**
	 * 取得当前人工节点的待选人列表
	 * 
	 * @return 当前人工节点的待选人列表
	 */
	public List<User> getTaskCandidateUser();

	/**
	 * 根据bizKey，查询当前节点的候选人
	 * 
	 * @param bizKey
	 * @return 候选人用户集合
	 */
	public List<User> getTaskCandidateUserByBizKey(String bizKey);

	/**
	 * 根据bizKey，查询当前节点下一个节点的候选人
	 * 
	 * @param bizKey
	 * @return 候选人用户集合
	 */
	// public List<User> getNextTaskCandidateUserByBizKey(String bizKey);

	/**
	 * 设置流程变量
	 * 
	 * @param piId
	 *            流程实例id
	 * @param variableName
	 *            参数名称
	 * @param variableValue
	 *            参数值
	 */
	public void setPiVariable(String piId, String variableName,
			String variableValue);

	/**
	 * 取得流程变量
	 * 
	 * @param piId
	 *            流程实例id
	 * @param variableName
	 *            参数名称
	 */
	public Object getPiVariable(String piId, String variableName);

	/**
	 * 为指定节点设置参数
	 * 
	 * @param taskId
	 *            执行中的节点id
	 * @param variableName
	 *            参数名称
	 * @param variableValue
	 *            参数值
	 */
	@Deprecated
	public void setTaskVariable(String taskId, String variableName,
			String variableValue);

	/**
	 * 获取指定流程和指定节点的注释信息
	 * 
	 * @param taskId
	 * @param processInstanceId
	 * @param message
	 * @return 注释信息
	 */
	public Comment getTaskComment(String taskId);

	/**
	 * 获取指定的流程实例的已执行结束的节点对象集合
	 * 
	 * @param processInstanceId
	 *            流程实例id
	 * @return 该流程已执行结束的节点对象
	 */
	public List<HistoricTaskInstance> getFinishedTaskByPiid(
			String processInstanceId);

	/**
	 * 获取指定的流程实例所有未执行节点的对象集合
	 * 
	 * @param piid
	 * @return 未执行节点的对象集合
	 */
	public List<TaskEx> getUnFinishedTaskByPiid(String piid);

	/**
	 * 根据给定的bizKey获取对应的流程的历史数据(只可在流程没有子流程时使用)
	 * 
	 * @param bizKey
	 *            业务实体key
	 * @return 流程节点执行历史
	 */
	public List<HistoricTaskInstance> getProcessHistoricByBizKey(String bizKey);

	/**
	 * 取得指定人工节点的待选人列表
	 * 
	 * @param piId
	 *            流程实例Id
	 * @param taskDefKey
	 *            人工节点定义Key
	 * @return 待选人列表
	 */
	public List<User> getTaskCandidateUserByDefKey(String piId,
			String taskDefKey);

	/**
	 * 获取指定流程实例的活动节点
	 * 
	 * @param piId
	 *            流程实例Id
	 * @return 活动节点
	 */
	public TaskEx getActiveTask(String piId);

	/**
	 * 流转当前节点并记录审核信息。 只能在当前节点是审核节点时调用此方法。 此方法会进行节点流转并记录审核信息。
	 * 
	 * @param taskId
	 *            审核节点Id
	 * @param title
	 *            审核标题
	 * @param content
	 *            审核内容
	 * @param comment
	 *            审核备注
	 * @param result
	 *            审核结果
	 * 
	 * @return 审核信息实体类
	 */
	public <T extends BaseBpDataEx> BpmTaskReview completeReview(String taskId,
			String title, String content, String comment,
			String fallBackReason, String result, String assigneer, T bpDataEx);

	/**
	 * 查询指定节点相关的审核信息。 此节点必须是审核节点。 审核信息按照审核时间顺序排列。
	 * 
	 * @param taskId
	 *            审核节点ID
	 * @return 节点相关的审核信息
	 */
	public List<BpmTaskReview> getTaskReviewsByTaskId(String taskId);

	/**
	 * 查询指定业务Key相关的审核信息。 如果流程中有多个审核节点，那么审核节点的审核信息将全部返回。 审核信息按照审核时间顺序排列。
	 * 
	 * 
	 * @param bizKey
	 *            业务Key
	 * @return 业务Key相关的审核信息
	 */
	public List<BpmTaskReview> getTaskReviewsByBizKey(String bizKey);

	/**
	 * 取得指定BizKey对应的流程状态
	 * 
	 * @param bizKey
	 *            BizKey
	 * @return 流程状态详细信息
	 */
	public PiStatus getPiStatusByBizKey(String bizKey);

	/**
	 * 根据业务Key取得已结束流程对应的BpDataEx
	 * 
	 * @param bizKey
	 *            业务Key
	 * @return BpDataEx
	 */
	public BaseBpDataEx getFinishedBpDataExByBizKey(String bizKey);

	/**
	 * 根据当前节点候选人和流程定义Key查询BaseBpDataEx 查询Task排序原则，对Task创建时间正序排序，对Task名称正序排序
	 * 
	 * @param pdkey
	 *            流程定义Key
	 * @param userId
	 *            当前节点的候选人
	 * @return BaseBpDataEx的集合
	 */
	public List<BaseBpDataEx> getBpDataExByCandidateUser(String pdkey,
			String candidateUserId);

	/**
	 * 根据当前节点受理人和流程定义Key查询BaseBpDataEx 查询Task排序原则，对Task创建时间正序排序，对Task名称正序排序
	 * 
	 * @param pdkey
	 *            流程定义Key
	 * @param userId
	 *            当前节点的候选人
	 * @return BaseBpDataEx的集合
	 */
	public List<BaseBpDataEx> getBpDataExByAssignee(String pdkey,
			String assignee);

	/**
	 * 根据指定的业务Key，获取该流程所有的第一步节点定义
	 * 
	 * @param bizKey
	 *            业务Key
	 * @return 流程第一步的用户节点定义
	 */
	public List<TaskDefinition> getFirstTaskDefByBizKey(String bizKey);

	/**
	 * 根据指定的业务Key，获取该流程所有的最后一步的节点定义
	 * 
	 * @param bizKey
	 *            业务Key
	 * @return 流程最后一步的的用户节点定义
	 */
	public List<TaskDefinition> getLastTaskDefByBizKey(String bizKey);

	/**
	 * 根据业务key，获取流程当前活动节点 的全部出线节点的代办人集合
	 * 
	 * @param bizKey
	 *            业务key
	 * @return 出线节点的代办人集合
	 */
	public List<User> getNextTaskCandidateUser(String bizKey);

	/**
	 * 根据业务key，获取流程当前活动节点的全部出线节点定义
	 * 
	 * @param bizKey
	 *            业务key
	 * @return 出线节点的定义集合
	 */
	public Map<PvmTransition, TaskDefinition> getNextTaskDefinition(
			String bizKey);

	/**
	 * 指定业务key和流程定义对象，获取节点的代办人集合
	 * 
	 * @param bizKey
	 *            业务key
	 * @param taskDef
	 *            流程定义对象
	 * @return 指定节点的代办人集合
	 */
	public List<User> getNextTaskCandidateUser(String bizKey,
			String outGoingTransValue, BaseBpDataEx bpDataEx);
	
	
	/**
	 * 根据指定业务key和流程定义key，获取对应节点的历史数据
	 * 
	 * @param bizKey
	 * @param taskDefKey
	 * @return
	 */
	public List<HistoricTaskInstance> getHistoricTasks(String bizKey,
			String taskDefKey);

}
