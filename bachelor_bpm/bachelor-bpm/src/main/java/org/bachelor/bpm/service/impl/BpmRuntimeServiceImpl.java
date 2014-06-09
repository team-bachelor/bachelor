package org.bachelor.bpm.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bachelor.bpm.common.BpmConstant;
import org.bachelor.bpm.common.BpmUtils;
import org.bachelor.bpm.dao.IBpmTaskReviewDao;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.BpStartedEvent;
import org.bachelor.bpm.domain.BpmTaskReview;
import org.bachelor.bpm.domain.ReviewResult;
import org.bachelor.bpm.domain.TaskCompletedEvent;
import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.bpm.domain.TaskType;
import org.bachelor.bpm.service.IBpmCandidateService;
import org.bachelor.bpm.service.IBpmEngineService;
import org.bachelor.bpm.service.IBpmRejectService;
import org.bachelor.bpm.service.IBpmRepositoryService;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.service.IBpmRuntimeTaskService;
import org.bachelor.bpm.service.IExpressionResolver;
import org.bachelor.bpm.service.IGroupExpResolveService;
import org.bachelor.bpm.vo.PiStatus;
import org.bachelor.context.service.IVLService;
import org.bachelor.core.entity.IBaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * 
 * @author zhangtao
 * 
 */
@Service
public class BpmRuntimeServiceImpl implements IBpmRuntimeService,
		ApplicationEventPublisherAware {
	@Autowired
	private IBpmEngineService bpmEngineService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private IBpmRepositoryService bpmRepositoryService;

	@Autowired
	private IVLService vlService;

	@Autowired
	private IBpmRuntimeTaskService bpmTaskService;

	@Autowired
	private IBpmTaskReviewDao bpmTaskReviewDao;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private IBpmRejectService rejectService;
	@Autowired
	private IExpressionResolver authService;
	@Autowired
	private IBpmCandidateService bpmCandidateService;

	@Autowired
	private IGroupExpResolveService groupExpResolveService;
	
	@Autowired
	private FormService formService;
	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public ProcessInstance startProcessInstanceByKey(String pdkey,
			BaseBpDataEx bpDataEx, IBaseEntity starter) {
		Map<String, Object> pv = new HashMap<String, Object>();
		bpDataEx.setStartUserId(starter.getId());
		bpDataEx.setStartUserName(starter.getName());
//		bpDataEx.setStartCompanyId(starter.getOrg().getId());
//		bpDataEx.setStartCompanyName(starter.getOrg().getName());
//		bpDataEx.setStartCompanyShortName(starter.getOrg().getShortName());
		// 将bpDataEx的所有属性存入
		addToGvMap(bpDataEx, pv);
		Map<String, Object> businessExtMap = bpDataEx.getBusinessExtMap();
		addBunissMapToGv(businessExtMap, pv);
		identityService.setAuthenticatedUserId(starter.getId());
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(pdkey,
				bpDataEx.getDomainId(), pv);
		bpDataEx.setPiId(pi.getId());
		bpDataEx.setLastOptUserId(starter.getId());
		BpStartedEvent event = new BpStartedEvent(bpDataEx);
		this.publisher.publishEvent(event);
		// FIXME 需要先判断当前节点是不是会签
		// FIXME 根据对应的节点类型，作相应处理
		bpDataEx = this.getBpDataEx(pi.getId(), starter.getId());
		this.saveTaskReview(bpDataEx, starter.getId(), "1");
		return pi;
	}

	@Override
	public ProcessInstance startProcessInstanceByKey(String pdkey,
			BaseBpDataEx bpDataEx) {
		IBaseEntity user = (IBaseEntity) vlService
				.getSessionAttribute(BpmConstant.BPM_LOGON_USER);
		return this.startProcessInstanceByKey(pdkey, bpDataEx, user);
	}

	private Map<String, Object> copyGvInfoToBunissMap(Map<String, Object> pv,
			BaseBpDataEx bpDataEx) {
		Map<String, Object> businessExtMap = new HashMap<String, Object>();
		try {
			Map map = PropertyUtils.describe(bpDataEx);
			for (Object gvkeyObj : pv.keySet()) {
				if (!(gvkeyObj instanceof String)) {
					continue;
				}
				String key = gvkeyObj.toString();
				if (!map.keySet().contains(gvkeyObj)) {
					businessExtMap.put(key, pv.get(key));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return businessExtMap;

	}

	private void addBunissMapToGv(Map<String, Object> businessExtMap,
			Map<String, Object> pv) {
		for (Object keyObj : businessExtMap.keySet()) {
			if (!(keyObj instanceof String)) {
				continue;
			}
			String key = keyObj.toString();
			pv.put(key, businessExtMap.get(key));
		}
	}

	private void addToGvMap(Object bpDataEx, Map<String, Object> pv) {
		try {
			Map map = PropertyUtils.describe(bpDataEx);
			for (Object keyObj : map.keySet()) {
				if (!(keyObj instanceof String)) {
					continue;
				}
				String key = keyObj.toString();
				if (key.equals("taskEx")) {
					continue;
				}
				if (key.equals("class")) {
					continue;
				}
				Object valueObj = map.get(key);

				if (valueObj instanceof Map) {
					continue;
				}
				pv.put(key, valueObj);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public List<? extends IBaseEntity> getTaskCandidateUser() {
		BaseBpDataEx bpinfoEx = (BaseBpDataEx) vlService
				.getRequestAttribute(BpmConstant.BPM_BP_DATA_EX_KEY);

		List<? extends IBaseEntity> userList = bpmTaskService.getUsersByTaskId(bpinfoEx
				.getTaskEx().getTask().getId());
		return userList;
	}

	@Override
	public <T extends BaseBpDataEx> void complete(String taskId, String userId,
			T bpDataEx) {
		completeTask(bpDataEx, userId, taskId);
	}

	@Override
	public <T extends BaseBpDataEx> void completeAndClaim(String taskId,
			String userId, T bpDataEx) {
		taskService.claim(taskId, userId);
		complete(taskId, userId, bpDataEx);
	}

	@Override
	public void complete() {
		BaseBpDataEx bpDataEx = (BaseBpDataEx) vlService
				.getRequestAttribute(BpmConstant.BPM_BP_DATA_EX_KEY);
		IBaseEntity user = (IBaseEntity) vlService
				.getSessionAttribute(BpmConstant.BPM_LOGON_USER);
		// 流转节点
		completeTask(bpDataEx, user.getId(), null);
	}
	private <T extends BaseBpDataEx> void completeTask(T bpDataEx, String userId, String taskId) {
		String tid = taskId;
		if(tid ==null)
			tid = bpDataEx.getTaskEx().getTask().getId();
		//taskService.setAssignee(tid, userId);
		Map<String, Object> gvMap = getGvMap(bpDataEx.getPiId());
		// gvMap.put(Constant.BPM_BP_DATA_EX_KEY, bpDataEx);
		addToGvMap(bpDataEx, gvMap);
		addBunissMapToGv(bpDataEx.getBusinessExtMap(), gvMap);
		taskService.complete(tid,
				gvMap);
		bpDataEx.setLastOptUserId(userId);
		TaskCompletedEvent event = new TaskCompletedEvent(bpDataEx);
		this.publisher.publishEvent(event);
	}

	private Map<String, Object> getGvMap(String piId) {
		Map<String, Object> gvMap = runtimeService.getVariables(piId);
		return gvMap;
	}

	@Override
	public void completeAndClaim(String userId) {
		BaseBpDataEx bpDataEx = (BaseBpDataEx) vlService
				.getRequestAttribute(BpmConstant.BPM_BP_DATA_EX_KEY);
		taskService.claim(bpDataEx.getTaskEx().getTask().getId(), userId);
		completeTask(bpDataEx, userId, null);
	}

	@Override
	public BaseBpDataEx getBpDataEx(String piId, String userId) {
		BaseBpDataEx bpDataEx = (BaseBpDataEx) vlService
				.getRequestAttribute(BpmConstant.BPM_BP_DATA_EX_KEY);
		if (bpDataEx != null && bpDataEx.getPiId().equals(piId)) {
			return bpDataEx;
		}
		long count = runtimeService.createProcessInstanceQuery()
				.processInstanceId(piId).count();
		if (count == 0) {
			// 将GvMap取出，并为BaseBpDataEx对象填充对应的数据
			List<HistoricVariableInstance> variableList = historyService
					.createHistoricVariableInstanceQuery()
					.processInstanceId(piId).list();
			if (variableList == null || variableList.size() == 0)
				return null;
			if (variableList != null) {
				bpDataEx = new BaseBpDataEx();
				Map<String, Object> gvMap = new HashMap<String, Object>();
				Map<String, Object> bMap = new HashMap<String, Object>();
				for (HistoricVariableInstance hvi : variableList) {
					if (hvi.getValue() != null)
						gvMap.put(hvi.getVariableName(), hvi.getValue());
				}
				try {
					BeanUtils.copyProperties(bpDataEx, gvMap);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				bMap = copyGvInfoToBunissMap(gvMap, bpDataEx);
				bpDataEx.setBusinessExtMap(bMap);
				vlService.setRequestAttribute(BpmConstant.BPM_BP_DATA_EX_KEY,
						bpDataEx);
				return bpDataEx;
			} else {
				return null;
			}
		} else {
			// 当前版本BpDataEx不在序列化到数据库中。将通过下面方式返回BpDataEx。
			bpDataEx = new BaseBpDataEx();
			Map<String, Object> gvMap = runtimeService.getVariables(piId);
			Map<String, Object> bMap = new HashMap<String, Object>();
			try {
				BeanUtils.copyProperties(bpDataEx, gvMap);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			bMap = copyGvInfoToBunissMap(gvMap, bpDataEx);
			bpDataEx.setBusinessExtMap(bMap);
			List<TaskEx> tl = bpmTaskService.getActiveTask(piId, userId);
			TaskEx taskEx = null;
			if(!tl.isEmpty())
				taskEx = tl.get(0);
			bpDataEx.setTaskEx(taskEx);
			vlService.setRequestAttribute(BpmConstant.BPM_BP_DATA_EX_KEY,
					bpDataEx);
			return bpDataEx;
		}
	}

	/**
	 * 根据bizkey获取子流程的实例
	 * 
	 * @param bizKey
	 * @return
	 */
	public List<ProcessInstance> getSubProcessByBizKey(String bizKey) {

		return null;
	}

	/**
	 * 根据piid获取该流程的全部子活动流程实例
	 * 
	 * @param piid
	 * @return
	 */
	public List<ProcessInstance> getSubProcessByPiid(String piid) {
		List<ProcessInstance> subProcessList = new ArrayList<ProcessInstance>();
		subProcessList = runtimeService.createProcessInstanceQuery()
				.superProcessInstanceId(piid).list();
		return subProcessList;
	}

	@Override
	public void setAssignee(String taskId, String userId) {
		taskService.setAssignee(taskId, userId);
	}

	@Override
	public void deleteProcessInstance(String processInstanceId,
			String deleteReason) {

		runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
		historyService.deleteHistoricProcessInstance(processInstanceId);
	}

	@Override
	public void addCandidateUser(String taskId, List userId, List groupId) {
		if (userId != null && userId.size() > 0) {
			for (Object object : userId) {
				taskService.addCandidateUser(taskId, (String) object);
			}

		}
		if (groupId != null && groupId.size() > 0) {
			for (Object object2 : groupId) {
				taskService.addCandidateGroup(taskId, (String) object2);
			}
		}
	}

	@Override
	public BaseBpDataEx getBpDataExByBizKey(String bizKey, String userId) {
		BaseBpDataEx bpDataEx = (BaseBpDataEx) vlService
				.getRequestAttribute(BpmConstant.BPM_BP_DATA_EX_KEY);
		if (bpDataEx != null && bpDataEx.getDomainId().equals(bizKey)) {
			return bpDataEx;
		}
		String piId = null;
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceBusinessKey(bizKey).singleResult();
		if (pi == null) {
			HistoricProcessInstance hpi = historyService
					.createHistoricProcessInstanceQuery()
					.processInstanceBusinessKey(bizKey).singleResult();
			if (hpi == null) {
				return null;
			} else {
				piId = hpi.getId();
			}
		} else {
			piId = pi.getProcessInstanceId();
		}
		bpDataEx = getBpDataEx(piId, userId);

		return bpDataEx;
	}

	public List<? extends IBaseEntity> getTaskCandidateUserByBizKey(String bizKey,
			String userId) {
		BaseBpDataEx bpDataEx = this.getBpDataExByBizKey(bizKey, userId);
		if (bpDataEx != null) {
			return bpmTaskService.getUsersByTaskId(bpDataEx.getTaskEx()
					.getTask().getId());
		} else {
			return null;
		}
	}

	@Override
	public void setApplicationEventPublisher(
			ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}

	@Override
	public Comment getTaskComment(String taskId) {
		return taskService.getTaskComments(taskId).get(0);
	}

	@Override
	public List<TaskEx> getUnFinishedTaskByPiid(String piid) {
		List<Task> taskList = taskService.createTaskQuery()
				.processInstanceId(piid).list();
		List<TaskEx> taskExList = new ArrayList<TaskEx>();
		for (Task task : taskList) {
			TaskEx taskEx = new TaskEx();
			taskExList.add(taskEx);
			String formKey = bpmTaskService.getTaskURL(task);
			taskEx.setFormKey(formKey);
			taskEx.setTask(task);
		}
		return taskExList;
	}

	@Override
	public BaseBpDataEx getBpDataExByTaskId(String taskId, String userId) {
		TaskEx task = bpmTaskService.getTask(taskId);
		if(task == null)
			return null;
		BaseBpDataEx bpDataEx = getBpDataEx(task.getTask()
				.getProcessInstanceId(), userId);
		return bpDataEx;
	}

	@Override
	public List<? extends IBaseEntity> getTaskCandidateUserByDefKey(String piId,
			String taskDefKey) {
		String pdId = runtimeService.createProcessInstanceQuery()
				.processInstanceId(piId).singleResult()
				.getProcessDefinitionId();
		TaskDefinition taskDef = bpmTaskService.getTaskDefition(pdId,
				taskDefKey);
		List<? extends IBaseEntity> userList = bpmTaskService.getUserByTaskDefinition(
				taskDef, piId);
		return userList;
	}

	@Override
	public List<? extends IBaseEntity> getTaskCandidateUserByDefKey2(String piId,
			String taskDefKey) {
		String pdId = runtimeService.createProcessInstanceQuery()
				.processInstanceId(piId).singleResult()
				.getProcessDefinitionId();
		TaskDefinition taskDef = bpmTaskService.getTaskDefition(pdId,
				taskDefKey);
		List<? extends IBaseEntity> userList = bpmTaskService.getUserByTaskDefinition2(
				taskDef, piId);
		return userList;
	}

	@Override
	public BpmTaskReview completeReviewForce(
			ProcessInstance pi, String userId, String comment,
			ReviewResult result, String taskCandidate){
		return completeReview(pi, userId, comment,
				result, taskCandidate, true);
	}
	@Override
	public BpmTaskReview completeReview(
			ProcessInstance pi, String userId, String comment,
			ReviewResult result, String taskCandidate) {
		return completeReview(pi, userId, comment,
				result, taskCandidate, false);
	}

	private <T extends BaseBpDataEx> BpmTaskReview completeReview(
			ProcessInstance pi, String userId, String comment,
			ReviewResult result, String taskCandidate, boolean forceCommit) {
		List<Task> tl = taskService.createTaskQuery().processInstanceId(pi.getId())
				.list();
		Task t = null;
		if(forceCommit){
			t = tl.get(0);
		}else{
			for(Task temp : tl){
				if(userId.equals(temp.getAssignee())){
					t = temp;
					break;
				}
			}
			if(t == null){
				throw new IllegalArgumentException("当前用户["+userId+"]不能操作指定任务");
			}
		}
		
		BaseBpDataEx bpDataEx = getBpDataEx(pi.getId(), userId);
		return this.completeReview(t.getId(), userId, "", "", comment, "", result,
				taskCandidate, bpDataEx, true);
	}
	
	@Override
	public <T extends BaseBpDataEx> BpmTaskReview completeReview(String taskId,
			String userId, String comment, ReviewResult result, String taskCandidate,
			T bpData, boolean force) {
		return this.completeReview(taskId, userId, "", "", comment, "", result,
				taskCandidate, bpData, force);
	}
	
	@Override
	public <T extends BaseBpDataEx> BpmTaskReview completeReview(String taskId,
			String userId, String title, String content, String comment,
			String fallBackReason, ReviewResult result, String taskCandidate,
			T bpDataEx, boolean force) {
		// 验证当前用户是否能操作此任务
		String piid = bpDataEx.getPiId();
		if(!hasOperability(userId, piid, taskId, force)){
			throw new IllegalArgumentException("当前用户["+userId+"]不能操作指定任务");
		}
		// 如果当前流程数据不是null则放到session中
		if (bpDataEx != null) {
			vlService.setRequestAttribute(BpmConstant.BPM_BP_DATA_EX_KEY,
					bpDataEx);
		}
		/** 为下一个节点设置待办人 **/
		// 当前是否为会签节点
		Boolean isCounterSignTask = isCountersign(taskId);
		if(ReviewResult.reject == result){
			// TODO 1.找到退回的目标节点
			// TODO 2.为目标节点设置原办理人为待办人
			// TODO 3.如果是会签则终止其他任务
			if(isCounterSignTask){
				rejectService.reject(taskId, userId, bpDataEx);
			}
		}else{
			// 解析强制代办人，传递了taskCandidate参数后，其他与代办人相关的设置均无效
			//FIXME 要改一下
			if (!StringUtils.isEmpty(taskCandidate)) {
				Set<String> forceCandidate = new HashSet<String>();
				// taskCandidate.replaceAll(" ", "");
				// taskCandidate.replaceAll(";;", ";");
				String[] assigneerStr = taskCandidate.split(";");
				forceCandidate = new HashSet(Arrays.asList(assigneerStr));
			}
		}
		// 至此，会签节点的代办人信息已经全部存入assigneerList
		// 获取该节点的前进终点对象
		bpDataEx.setLastOpt(result.toString());
		// 获取当前要提交的task对象的定义id
		String curTaskDefKey = taskService.createTaskQuery().taskId(taskId).singleResult().getTaskDefinitionKey();
		// 流转节点
		complete(taskId, userId, bpDataEx);

		bpDataEx = (T) getBpDataEx(bpDataEx.getPiId(), userId);
		
		// 取得当前活动task
		List<TaskEx> tl = bpmTaskService.getActiveTask(bpDataEx.getPiId(),
				userId);
		String nextTaskId = "";
		// 如果有活动task
		if(!tl.isEmpty()){
			//循环所有活动task
			for(TaskEx task : tl){
				//如果当前提交的task与提交后的下一步task的定义key相同则当前为会签节点
				if(!curTaskDefKey.equals(task.getTask().getTaskDefinitionKey())){
					saveTaskReview4Next(task, bpDataEx);
					nextTaskId = task.getTask().getId();
				}
			}
		}
		
		// 更新当前任务的TaskReview对象
		BpmTaskReview bpTaskReview = findTaskReview(taskId, userId, force);
		updateTaskReview(bpTaskReview, title, content, comment, fallBackReason,
				result.toString(), nextTaskId, userId, "1");
		return bpTaskReview;
	}

	/**
	 * 为下个任务保存TaskReview对象
	 * @param nextTask
	 */
	private void saveTaskReview4Next(TaskEx nextTask, BaseBpDataEx bpDataEx) {
		String nextTaskId = nextTask.getTask().getId();
		boolean isCounterSignTask = isCountersign(nextTaskId);
		nextTaskId = nextTask.getTask().getId();
		// 为了生成会签的taskReview，将使用 assigneerList
		List<String> assigneerList = new ArrayList<String>();
		if (!isCounterSignTask) {
			// 获取流程本身的代办人信息
			// 记录代办人需要用到candidateUserLink
			List<IdentityLink> candidateUserLink = taskService
					.getIdentityLinksForTask(nextTaskId);
			for (IdentityLink idLink : candidateUserLink) {
				if (idLink.getGroupId() != null) {
					List<? extends IBaseEntity> users = groupExpResolveService
							.resolve(idLink.getGroupId(), bpDataEx);
					for (IBaseEntity user : users) {
						assigneerList.add(user.getId());
					}
				}
				if (idLink.getUserId() != null) {
					assigneerList.add(idLink.getGroupId());
				}
			}
			String candUserId = BpmUtils.toTaskReviewCandidate(
					StringUtils.join(assigneerList, '|'));

			// 如果target节点是单签节点，则为target节点生成代办查询用的taskReview数据
			// 如果forceCandidate没有数据，就将节点原本的candidateUserLink赋给
			saveTaskReview(bpDataEx, candUserId, "0");
		} else {
			List<TaskEx> tl = bpmTaskService.getActiveTask(bpDataEx.getPiId(), null);
			for(TaskEx t : tl){
				bpDataEx.setTaskEx(t);
				saveTaskReview(bpDataEx, t.getTask().getAssignee(), "0");
			}
		}
	}

	private boolean hasOperability(String userId, String piid, String taskId, boolean force) {
		boolean result = false;
		BpmTaskReview tr = findTaskReview(taskId, userId, force);
		if(tr != null)
			return true;
		return result;
	}

	private Boolean isCountersign(String nextTaskId) {
		TaskFormData fd = formService.getTaskFormData(nextTaskId);
		List<FormProperty> fps = fd.getFormProperties();
		for(FormProperty fp : fps){
			if("taskType".equals(fp.getId())){
				if(TaskType.会审.toString().equals(fp.getName())){
					return true;
				}
			}
		}
		return false;
	}

	private BpmTaskReview findTaskReview(String taskId, String userId, boolean force) {
		BpmTaskReview result = null;
		List<BpmTaskReview> l = bpmTaskReviewDao
				.getTaskReviewsByTaskId(taskId);
		if(force)
			result = bpmTaskReviewDao.getTaskReviewsByTaskId(taskId).get(0);
		for (BpmTaskReview t : l) {
			if (t.getCandUserId() != null
					&& t.getCandUserId().equals(userId)) {
				result = t;
				break;
			}
		}
		return result;
	}

	// TODO 增加生成会签节点的taskReview代办信息的方法
	private List<BpmTaskReview> saveTaskReviewForCounterTask(
			BaseBpDataEx bpDataEx, List<String> users, String isTaskFinish) {
		List<BpmTaskReview> result = new ArrayList<BpmTaskReview>();
		for (String userId : users) {
			result.add(saveTaskReview(bpDataEx, 
					BpmUtils.toTaskReviewCandidate(userId)
					, isTaskFinish));
		}
		return result;
	}

	private BpmTaskReview updateTaskReview(BpmTaskReview taskReview,
			String title, String content, String comment,
			String fallBackReason, String result, String targetTaskId,
			String reviewUserId, String isTaskFinish) {
		// TODO 记录审核人
		taskReview.setReviewUserId(reviewUserId);
		// 审核相关数据
		taskReview.setReviewComment(comment);
		taskReview.setReviewContent(content);
		taskReview.setReviewDate(new Date());
		taskReview.setReviewResult(result);
		taskReview.setReviewTitle(title);
		// 退回原因
		taskReview.setReviewFallbackReasoin(fallBackReason);
		taskReview.setIsTaskFinish(isTaskFinish);
		TaskEx targetTask = bpmTaskService.getTask(targetTaskId);
		if (targetTask != null) {
			taskReview.setTargetTaskId(targetTask.getTask().getId());
			taskReview.setTargetTaskDefKey(targetTask.getTask()
					.getTaskDefinitionKey());
			taskReview.setTargetTaskName(targetTask.getTask().getName());
		}
		bpmTaskReviewDao.update(taskReview);
		return taskReview;
	}

	private BpmTaskReview saveTaskReview(BaseBpDataEx bpDataEx,
			String candUserId, String isTaskFinish) {
		BpmTaskReview review = new BpmTaskReview();
		// FIXME 这个地方需要你再推敲一下
		// if (bpDataEx == null || user == null
		// || bpDataEx.getTaskEx().getTask() == null) {
		// return null;
		// }
		Task task = bpDataEx.getTaskEx().getTask();
		ProcessDefinition pd = bpmRepositoryService.getPdById(task
				.getProcessDefinitionId());
		if (pd == null) {
			return null;
		}
		// 节点是否执行完毕
		review.setIsTaskFinish(isTaskFinish);
		// 业务key
		review.setBizKey(bpDataEx.getDomainId());
		// 当前task的实例id
		review.setExecId(task.getExecutionId());
		// 流程定义相关字段
		review.setPdId(pd.getId());
		review.setPdKey(pd.getKey());
		review.setPdName(pd.getName());
		review.setPdVersion(pd.getVersion());

		// 流程实例id
		review.setPiId(task.getProcessInstanceId());
		// 审核相关数据
		review.setReviewDate(new Date());
		review.setReviewTaskDefKey(task.getTaskDefinitionKey());
		review.setReviewTaskId(task.getId());
		review.setReviewTaskName(task.getName());
		review.setCandUserId(candUserId);
		if (bpDataEx.getTaskEx().getTaskType() != null) {
			review.setReviewType(String.valueOf(bpDataEx.getTaskEx()
					.getTaskType()));
		}

		bpmTaskReviewDao.save(review);
		return review;
	}

	@Override
	public List<BpmTaskReview> getTaskReviewsByTaskId(String taskId) {
		return bpmTaskReviewDao.getTaskReviewsByTaskId(taskId);
	}

	@Override
	public List<BpmTaskReview> getTaskReviewsByBizKey(String bizKey) {
		return bpmTaskReviewDao.getTaskReviewsByBizKey(bizKey);
	}

	@Override
	public void setPiVariable(String piId, String variableName,
			String variableValue) {
		runtimeService.setVariable(piId, variableName, variableValue);
	}

	@Override
	public Object getPiVariable(String piId, String variableName) {
		Object obj = runtimeService.getVariable(piId, variableName);
		return obj;
	}

	@Override
	public Map<String, Object> getPiVariableMap(String piId) {
		return runtimeService.getVariables(piId);
	}

	@Override
	public PiStatus getPiStatusByBizKey(String bizKey) {
		ProcessInstanceQuery query = runtimeService
				.createProcessInstanceQuery();
		long count = query.processInstanceBusinessKey(bizKey).count();
		if (count > 0) {
			// 流程正在运行中
			return PiStatus.Running;
		} else {
			count = historyService.createHistoricProcessInstanceQuery()
					.finished().processInstanceBusinessKey(bizKey).count();
			if (count > 0) {
				// 流程已经结束
				return PiStatus.Finished;
			} else {
				// 没有流程信息
				return PiStatus.NotFound;
			}
		}
	}

	@Override
	public PiStatus getPiStatusByPiId(String piId) {
		ProcessInstanceQuery query = runtimeService
				.createProcessInstanceQuery();
		long count = query.processInstanceId(piId).count();
		if (count > 0) {
			// 流程正在运行中
			return PiStatus.Running;
		} else {
			count = historyService.createHistoricProcessInstanceQuery()
					.finished().processInstanceId(piId).count();
			if (count > 0) {
				// 流程已经结束
				return PiStatus.Finished;
			} else {
				// 没有流程信息
				return PiStatus.NotFound;
			}
		}
	}

	@Override
	public ProcessInstance findByPiId(String piId) {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(piId).singleResult();
		return pi;
	}

	@Override
	public ProcessInstance findByBizKey(String bizKey) {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceBusinessKey(bizKey).singleResult();
		return pi;
	}

	@Override
	public List<? extends IBaseEntity> getHisTaskCandidateUserByDefKey(String pdid,
			String PiId, String taskDefKey) {
		TaskDefinition taskDef = bpmTaskService.getTaskDefition(pdid,
				taskDefKey);
		List<? extends IBaseEntity> userList = bpmTaskService.getUserByTaskDefinition2(
				taskDef, PiId);
		return userList;
	}

	@Override
	public <T extends BaseBpDataEx> void signal(String taskId, String userId,
			String targetTaskDefKey, T bpDataEx) {
		TaskEx taskEx = bpmTaskService.getTask(taskId);
		String pdId = taskEx.getTask().getProcessDefinitionId();
		ActivityImpl sourceActivity = null;
		ActivityImpl destActivity = null;
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((org.activiti.engine.impl.RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(pdId);
		List<ActivityImpl> activitiList = def.getActivities();
		if (activitiList != null && !activitiList.isEmpty()) {
			for (ActivityImpl activityImpl : activitiList) {
				if (activityImpl.getId().equals(targetTaskDefKey)) {
					destActivity = activityImpl;
				}
				if (activityImpl.getId().equals(
						taskEx.getTask().getTaskDefinitionKey())) {
					sourceActivity = activityImpl;
				}
			}
		}
		if (destActivity == null || sourceActivity == null) {
			return;
		}
		// 清空当前节点的出线
		List<PvmTransition> outTrans = bpmEngineService
				.clearOutTransition(sourceActivity);
		// 创建新流向出线
		TransitionImpl newTrans = sourceActivity.createOutgoingTransition();
		newTrans.setDestination(destActivity);
		// 流转节点
		complete(taskId, userId, bpDataEx);
		// 恢复出线
		bpmEngineService.restoreOutTransition(sourceActivity, outTrans);
	}

	@Override
	public List<IBaseEntity> getNextTaskCandidateUser(String bizKey) {
		ProcessInstance pi = findByBizKey(bizKey);
		Map<PvmTransition, ActivityImpl> actImplMap = bpmEngineService
				.getNextActivityImpl(bizKey);
		Map<PvmTransition, TaskDefinition> taskDefMap = new HashMap<PvmTransition, TaskDefinition>();
		bpmEngineService.warpTaskDefMap(actImplMap, taskDefMap);
		List<IBaseEntity> userList = new ArrayList<IBaseEntity>();
		if (taskDefMap != null) {
			// List<TaskDefinition> lastTaskDefList = bpmTaskService
			// .getLastTaskDef(bizKey);
			// boolean isLastTask = Boolean.FALSE;
			// 返回每个transition对应的task中定义的候选人
			for (TaskDefinition taskDef : taskDefMap.values()) {
				userList.addAll(bpmTaskService.getUserByTaskDefinition2(
						taskDef, pi.getProcessInstanceId()));
			}
		}
		return userList;
	}

	@Override
	public List<IBaseEntity> getNextTaskCandidateUser(String bizKey,
			String outGoingTransValue, BaseBpDataEx bpDataEx) {
		// 判断节点是否是会签节点，是会签节点，则将该节点的流程变量中的
		// 获取流程的流程变量
		ProcessInstance pi = findByBizKey(bizKey);
		if (outGoingTransValue == null || outGoingTransValue.isEmpty())
			return getNextTaskCandidateUser(bizKey);
		// 获取符合条件的出线终点对象
		ActivityImpl outGoingActivityImpl = bpmEngineService
				.getOutGoingActivityImpl(bizKey, outGoingTransValue);
		TaskDefinition outGoingTask = null;
		if (outGoingActivityImpl == null)
			return null;
		outGoingTask = (TaskDefinition) outGoingActivityImpl
				.getProperty("taskDefinition");
		// 判断节点是否是会签节点
		Boolean jointTask = false;
		Boolean autoSetAssigneer = false;
		List<IBaseEntity> userList = new ArrayList<IBaseEntity>();
		List<String> tempUserList = new ArrayList<String>();
		try {
			jointTask = bpmEngineService.isJointTask(outGoingTask);
			autoSetAssigneer = bpmEngineService
					.isAutoSetAssignments(outGoingTask);
			if (autoSetAssigneer)
				return null;
			if (jointTask) {
				String MIAssigneeListName = bpmEngineService
						.getMIAssigneeListName(outGoingActivityImpl);
				if (!StringUtils.isEmpty(MIAssigneeListName))
					tempUserList = (List<String>) bpDataEx.getBusinessExtMap()
							.get(MIAssigneeListName);
				if (tempUserList != null && tempUserList.size() > 0) {
					for (String userId : tempUserList) {
						userList.add(authService.resolveUserByUserExp(userId));
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (userList != null && userList.size() == 0)
			userList = bpmTaskService.getUserByTaskDefinition2(outGoingTask,
					pi.getProcessInstanceId());
		return userList;
	}

	@Override
	public Map<PvmTransition, TaskDefinition> getNextTaskDefinition(
			String bizKey) {
		Map<PvmTransition, ActivityImpl> actImplMap = bpmEngineService
				.getNextActivityImpl(bizKey);
		Map<PvmTransition, TaskDefinition> taskDefMap = new HashMap<PvmTransition, TaskDefinition>();
		bpmEngineService.warpTaskDefMap(actImplMap, taskDefMap);
		return taskDefMap;
	}

	/**
	 * 私有方法:该方法负责更新CurrentOperator流程参与者数据 开发者：张涛 开发时间：2013-11-29
	 * 
	 */
//	private List<String> refreshCurrentOperator(String userId, List copList) {
//		List<String> roleList = bpmCandidateService.getGroupIdByUser(userId);
//		if (roleList != null && copList != null) {
//			for (int i = copList.size() - 1; i > 0; i--) {
//				if (roleList.contains(copList.get(i)))
//					copList.remove(i);
//			}
//		}
//		return roleList;
//	}

}