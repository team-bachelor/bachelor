package org.bachelor.bpm.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
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
import org.bachelor.bpm.auth.IBpmUser;
import org.bachelor.bpm.common.BpmConstant;
import org.bachelor.bpm.dao.IBpmTaskReviewDao;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.BpStartedEvent;
import org.bachelor.bpm.domain.BpmTaskReview;
import org.bachelor.bpm.domain.ReviewResult;
import org.bachelor.bpm.domain.TaskCompletedEvent;
import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.bpm.domain.TaskType;
import org.bachelor.bpm.service.IAuthService;
import org.bachelor.bpm.service.IBpmCandidateService;
import org.bachelor.bpm.service.IBpmEngineService;
import org.bachelor.bpm.service.IBpmRejectService;
import org.bachelor.bpm.service.IBpmRepositoryService;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.service.IBpmRuntimeTaskService;
import org.bachelor.bpm.vo.PiStatus;
import org.bachelor.context.service.IVLService;
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
	private IAuthService authService;
	@Autowired
	private IBpmCandidateService bpmCandidateService;

	private Log log = LogFactory.getLog(this.getClass());


	@Override
	public ProcessInstance startProcessInstanceByKey(String pdkey,
			BaseBpDataEx bpDataEx, IBpmUser starter) {
		Map<String, Object> pv = new HashMap<String, Object>();
		bpDataEx.setStartUserId(starter.getId());
		bpDataEx.setStartUserName(starter.getName());
		bpDataEx.setStartCompanyId(starter.getOrg().getId());
		bpDataEx.setStartCompanyName(starter.getOrg().getName());
		bpDataEx.setStartCompanyShortName(starter.getOrg()
				.getShortName());
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
		//FIXME 需要先判断当前节点是不是会签
		//FIXME 根据对应的节点类型，作相应处理
		bpDataEx=this.getBpDataEx(pi.getId(), starter.getId());
		this.saveTaskReview(bpDataEx, starter.getId(),"1");
		return pi;
	}
	
	@Override
	public ProcessInstance startProcessInstanceByKey(String pdkey,
			BaseBpDataEx bpDataEx) {
		IBpmUser user = (IBpmUser) vlService.getSessionAttribute(BpmConstant.BPM_LOGON_USER);
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
	public List<IBpmUser> getTaskCandidateUser() {
		BaseBpDataEx bpinfoEx = (BaseBpDataEx) vlService
				.getRequestAttribute(BpmConstant.BPM_BP_DATA_EX_KEY);

		List<IBpmUser> userList = bpmTaskService.getUsersByTaskId(bpinfoEx
				.getTaskEx().getTask().getId());
		return userList;
	}

	@Override
	public <T extends BaseBpDataEx> void complete(String taskId, String userId, T bpDataEx) {
		completeTask(bpDataEx, userId);
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
		IBpmUser user = (IBpmUser) vlService
				.getSessionAttribute(BpmConstant.BPM_LOGON_USER);
		// 流转节点
		completeTask(bpDataEx, user.getId());
	}

	private <T extends BaseBpDataEx> void completeTask(T bpDataEx, String userId) {

		taskService.setAssignee(bpDataEx.getTaskEx().getTask().getId(),
				userId);
		Map<String, Object> gvMap = getGvMap(bpDataEx.getPiId());
		// gvMap.put(Constant.BPM_BP_DATA_EX_KEY, bpDataEx);
		addToGvMap(bpDataEx, gvMap);
		addBunissMapToGv(bpDataEx.getBusinessExtMap(), gvMap);
		taskService.complete(bpDataEx.getTaskEx().getTask().getId().toString(),
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
		completeTask(bpDataEx, userId);
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
			TaskEx taskEx = bpmTaskService.getActiveTask(piId, userId);
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

	public List<IBpmUser> getTaskCandidateUserByBizKey(String bizKey, String userId) {
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
		BaseBpDataEx bpDataEx = getBpDataEx(task.getTask()
				.getProcessInstanceId(), userId);
		return bpDataEx;
	}

	@Override
	public List<IBpmUser> getTaskCandidateUserByDefKey(String piId,
			String taskDefKey) {
		String pdId = runtimeService.createProcessInstanceQuery()
				.processInstanceId(piId).singleResult()
				.getProcessDefinitionId();
		TaskDefinition taskDef = bpmTaskService.getTaskDefition(pdId,
				taskDefKey);
		List<IBpmUser> userList = bpmTaskService.getUserByTaskDefinition(taskDef,
				piId);
		return userList;
	}

	@Override
	public List<IBpmUser> getTaskCandidateUserByDefKey2(String piId,
			String taskDefKey) {
		String pdId = runtimeService.createProcessInstanceQuery()
				.processInstanceId(piId).singleResult()
				.getProcessDefinitionId();
		TaskDefinition taskDef = bpmTaskService.getTaskDefition(pdId,
				taskDefKey);
		List<IBpmUser> userList = bpmTaskService.getUserByTaskDefinition2(taskDef,
				piId);
		return userList;
	}
	
	@Override
	public <T extends BaseBpDataEx> BpmTaskReview completeReview(ProcessInstance pi, String userId,
			String comment, ReviewResult result, String taskCandidate) {
		Task t = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		BaseBpDataEx bpDataEx = getBpDataEx(pi.getId(), userId);
		return this.completeReview(t.getId(), userId, comment, result, taskCandidate, bpDataEx);
	}
	
	@Override
	public <T extends BaseBpDataEx> BpmTaskReview completeReview(String taskId, String userId,
			String comment, ReviewResult result, String taskCandidate, T bpDataEx) {
		return this.completeReview(taskId, userId, "", "", comment, "", result, taskCandidate, bpDataEx);
	}
	
	@Override
	public <T extends BaseBpDataEx> BpmTaskReview completeReview(String taskId, String userId,
			String title, String content, String comment,
			String fallBackReason, ReviewResult result, String taskCandidate, T bpDataEx) {
		
		//如果当前流程数据不是null则放到session中
		if (bpDataEx != null) {
			vlService.setRequestAttribute(BpmConstant.BPM_BP_DATA_EX_KEY,
					bpDataEx);
		}
		
		//将流程数据转换为map
		Map<String, Object> bpDataExMap = null;
		try {
			// 获取节点出线的终点
			bpDataExMap = PropertyUtils.describe(bpDataEx);
		} catch (Exception e) {
			bpDataExMap = new HashMap<String, Object>();
			log.error(e.getMessage(), e);
		}
		
		/*********** 判断流转的终点节点是否是会签节点 ***************/
		//是否为会签节点
		Boolean isCounterSignTask = false;
		String outGoingTransValue = (String) bpDataExMap.get("NEXT_STEP");
		ActivityImpl actImpl = bpmEngineService.getOutGoingActivityImpl(
				bpDataEx.getDomainId(), outGoingTransValue);
		if (actImpl != null) {
			try {
				isCounterSignTask = bpmEngineService
						.isJointTask((TaskDefinition) actImpl
								.getProperty("taskDefinition"));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			} 
		}
		
		//解析强制代办人，传递了taskCandidate参数后，其他与代办人相关的设置均无效
		Set<String> forceCandidate = new HashSet<String>();
		if (!StringUtils.isEmpty(taskCandidate)) {
//			taskCandidate.replaceAll(" ", "");
//			taskCandidate.replaceAll(";;", ";");
			String[] assigneerStr = taskCandidate.split(";");
			forceCandidate = new HashSet(Arrays.asList(assigneerStr));
		}
		//TODO 为了生成会签的taskReview，将使用 assigneerList
		List<String> assigneerList = new ArrayList<String>();
		// 判断节点出线终点的节点是否是会签节点
		if (isCounterSignTask) {
			String MIAssigneeListName = bpmEngineService
					.getMIAssigneeListName(actImpl);
			// 此处修改扩展属性的存储方式
			List<String> paramAssigneerList = (List) bpDataEx.getBusinessExtMap().get(
					MIAssigneeListName);
			if (StringUtils.isEmpty(taskCandidate)
					&& paramAssigneerList != null
					&& paramAssigneerList.size() > 0) {
				assigneerList.addAll(paramAssigneerList);
			} else {
				assigneerList.addAll(forceCandidate);
			}
			Map<String, Object> businessExtMap = bpDataEx.getBusinessExtMap();
			// 此处修改扩展属性的存储方式
			businessExtMap.put(MIAssigneeListName, assigneerList);
			// 获取流程的通用变量
			Map<String, Object> GvMap = new HashMap<String, Object>();
			// 将业务扩展变量转存入流程通用变量
			addBunissMapToGv(businessExtMap, GvMap);
			// 将流程数据封装对象，转存入流程通用变量
			addToGvMap(bpDataEx, GvMap);
			runtimeService.setVariables(bpDataEx.getPiId(), GvMap);
			vlService.setRequestAttribute(BpmConstant.BPM_BP_DATA_EX_KEY,
					bpDataEx);
		}
		//TODO 至此，会签节点的代办人信息已经全部存入assigneerList
		// 获取该节点的前进终点对象
//		Boolean isLastTask = Boolean.FALSE;
//		if (bpDataEx.getDomainId() != null) {
//			List<TaskDefinition> lastTaskList = bpmTaskService
//					.getLastTaskDef(bpDataEx.getDomainId());
//			for (TaskDefinition lastTaskdef : lastTaskList) {
//				if (lastTaskdef.getKey().equals(
//						bpDataEx.getTaskEx().getTask().getTaskDefinitionKey())) {
//					// String keyName = lastTaskdef.getKey();
//					isLastTask = Boolean.TRUE;
//				}
//			}
//		}
		bpDataEx.setLastOpt(result.toString());
		/**
		 * 此处不需要考虑会签，
		 * 调用getBpDataExByBizKey获取bpDataEx时，返回的bpDateEx，就是与当前登录用户关联的那个会签
		 * task实例,应该修改的是getTaskReviewsByTaskId方法，该方法不会返回多个值
		 ****/
		// TODO 保存和获取TaskReview的时候还没有考虑会签的情况
		BpmTaskReview bpTaskReview = bpmTaskReviewDao.getTaskReviewsByTaskId(
				bpDataEx.getTaskEx().getTask().getId()).get(0);
		// 流转节点
		complete(taskId, userId, bpDataEx);
//		if (result.equals(ReviewResult.reject)) {
//			isLastTask = Boolean.FALSE;
//		}
		String nextTaskId = "";
		/**  如果nextTask是单签节点，assigneerList.size将为0**/
		TaskEx nextTask = bpmTaskService.getActiveTask(bpDataEx.getPiId(), userId);
		if (nextTask != null && !isCounterSignTask && assigneerList.size()==0) {
			
			TaskType type = bpmTaskService.getTaskType(nextTask.getTask().getId());
			//TODO 如果是退回，则设置目标节点原来的办理人为待办人
			if(result.equals(ReviewResult.reject)){
				
			}else
			//如果type为null，则需要设置强制待办人。
			if (type == null && forceCandidate != null
						&& forceCandidate.size() > 0) {
			//TODO 记录代办人需要用到candidateUserLink
				List<IdentityLink> candidateUserLink = taskService
						.getIdentityLinksForTask(nextTask.getTask().getId());
				for (IdentityLink idLink : candidateUserLink) {
					if (idLink.getUserId() == null
							&& idLink.getGroupId() != null) {
						taskService.deleteCandidateUser(nextTask.getTask()
								.getId(), idLink.getGroupId());
						continue;
					}
					if (forceCandidate.contains(idLink.getUserId())) {
						taskService.deleteCandidateUser(nextTask.getTask()
								.getId(), idLink.getUserId());
					} else {
						forceCandidate.remove(idLink.getUserId());
						continue;
					}
				}
				for (String fcUserId : forceCandidate) {
					taskService.addCandidateUser(nextTask.getTask().getId(),
							fcUserId);
				}
			}
			
			bpDataEx = (T) getBpDataEx(bpDataEx.getPiId(), userId);
			//TODO 如果target节点是单签节点，则为target节点生成代办查询用的taskReview数据
			//TODO 如果forceCandidate没有数据，就将节点原本的candidateUserLink赋给
			saveTaskReview(bpDataEx, userId, "0");
			nextTaskId = nextTask.getTask().getId();
		}else{
			//TODO 如果target节点是会签节点，那就在此生成对应会签节点所有代办人的taskReview
			this.saveTaskReviewForCounterTask(bpDataEx, userId, "0");
		}
		//FIXME 需要做修改，user处应该传入当前审核人的userId
		updateTaskReview(bpTaskReview, title, content, comment,
				fallBackReason, result.toString(), nextTaskId, userId, "1");
		return bpTaskReview;
	}

	
	//TODO 增加生成会签节点的taskReview代办信息的方法
	private BpmTaskReview saveTaskReviewForCounterTask(BaseBpDataEx bpDataEx, String candUserId,
			String isTaskFinish){
		
		return null;
	}
	
	private BpmTaskReview updateTaskReview(BpmTaskReview taskReview,
			String title, String content, String comment,
			String fallBackReason, String result, String targetTaskId,String reviewUserId,
			String isTaskFinish) {		
		//TODO 记录审核人
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
	if(targetTask!=null){
		taskReview.setTargetTaskId(targetTask.getTask().getId());
		taskReview.setTargetTaskDefKey(targetTask.getTask().getTaskDefinitionKey());
		taskReview.setTargetTaskName(targetTask.getTask().getName());	
		}
		bpmTaskReviewDao.update(taskReview);
		return taskReview;
	}

	private BpmTaskReview saveTaskReview(BaseBpDataEx bpDataEx, String candUserId,
			String isTaskFinish) {
		BpmTaskReview review = new BpmTaskReview();
		//FIXME 这个地方需要你再推敲一下
//		if (bpDataEx == null || user == null
//				|| bpDataEx.getTaskEx().getTask() == null) {
//			return null;
//		}
		Task task = bpDataEx.getTaskEx().getTask();
		ProcessDefinition pd = bpmRepositoryService.getPdById(task
				.getProcessDefinitionId());
		if (pd == null) {
			return null;
		}
		//节点是否执行完毕
		review.setIsTaskFinish(isTaskFinish);
		//业务key
		review.setBizKey(bpDataEx.getDomainId());
		//当前task的实例id
		review.setExecId(task.getExecutionId());
		//流程定义相关字段
		review.setPdId(pd.getId());
		review.setPdKey(pd.getKey());
		review.setPdName(pd.getName());
		review.setPdVersion(pd.getVersion());
		//流程实例id
		review.setPiId(task.getProcessInstanceId());
		//审核相关数据
		review.setReviewDate(new Date());
		review.setReviewTaskDefKey(task.getTaskDefinitionKey());
		review.setReviewTaskId(task.getId());
		review.setReviewTaskName(task.getName());
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
	public List<IBpmUser> getHisTaskCandidateUserByDefKey(String pdid,
			String PiId, String taskDefKey) {
		TaskDefinition taskDef = bpmTaskService.getTaskDefition(pdid,
				taskDefKey);
		List<IBpmUser> userList = bpmTaskService.getUserByTaskDefinition2(
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
	public List<IBpmUser> getNextTaskCandidateUser(String bizKey) {
		ProcessInstance pi = findByBizKey(bizKey);
		Map<PvmTransition, ActivityImpl> actImplMap = bpmEngineService
				.getNextActivityImpl(bizKey);
		Map<PvmTransition, TaskDefinition> taskDefMap = new HashMap<PvmTransition, TaskDefinition>();
		bpmEngineService.warpTaskDefMap(actImplMap, taskDefMap);
		List<IBpmUser> userList = new ArrayList<IBpmUser>();
		if (taskDefMap != null){
	//		List<TaskDefinition> lastTaskDefList = bpmTaskService
	//				.getLastTaskDef(bizKey);
	//		boolean isLastTask = Boolean.FALSE;
			// 返回每个transition对应的task中定义的候选人
			for (TaskDefinition taskDef : taskDefMap.values()) {
				userList.addAll(bpmTaskService.getUserByTaskDefinition2(taskDef,
					pi.getProcessInstanceId()));
			}
		}
		return userList;
	}

	@Override
	public List<IBpmUser> getNextTaskCandidateUser(String bizKey,
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
		List<IBpmUser> userList = new ArrayList<IBpmUser>();
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
						userList.add(authService.findUserById(userId));
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
	private List<String> refreshCurrentOperator(String userId, List copList) {
		List<String> roleList = bpmCandidateService.getGroupIdByUser(userId);
		if (roleList != null && copList != null) {
			for (int i = copList.size() - 1; i > 0; i--) {
				if (roleList.contains(copList.get(i)))
					copList.remove(i);
			}
		}
		return roleList;
	}

}
