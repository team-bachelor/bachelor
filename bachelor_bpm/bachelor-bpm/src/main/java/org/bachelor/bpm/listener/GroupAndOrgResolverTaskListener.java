package org.bachelor.bpm.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.task.IdentityLink;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.bachelor.bpm.auth.IBpmUser;
import org.bachelor.bpm.common.BpmUtils;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.service.IGroupExpResolveService;
import org.bachelor.context.service.IVLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupAndOrgResolverTaskListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IVLService vlService;
	@Autowired
	private IGroupExpResolveService groupExpResolveService;
	@Autowired
	private IBpmRuntimeService bpmRuntimeService;

	@Override
	public void notify(DelegateTask delegateTask) {
		BaseBpDataEx bpDataEx = new BaseBpDataEx();
		Set<String> nameSet = delegateTask.getVariableNames();
		Map busMap = new HashMap();
		try {
			//创建并填充bpDataEx
			Map map = BeanUtils.describe(bpDataEx);
			for (Object keyObj : map.keySet()) {
				if (!(keyObj instanceof String)) {
					continue;
				}
				String key = keyObj.toString();
				if (key.equals("taskEx")) {
					continue;
				}
				if(key.equals("class")){
					continue;
				}
				//将流程变量中的值设置到bpDataEx中
				Object valueObj = delegateTask.getVariable(key);
				PropertyUtils.setProperty(bpDataEx, key, valueObj);
			}
			//得到所有的流程变量名字和值
			for (String valName : nameSet) {
				if (!map.keySet().contains(valName)) {
					busMap.put(valName, delegateTask.getVariable(valName));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		//设置流程变量map到bpDataEx中
		bpDataEx.setBusinessExtMap(busMap);
		//得到当前任务的候选人或组织
		Set<IdentityLink> ilList = delegateTask.getCandidates();
		for (IdentityLink il : ilList) {
			if (BpmUtils.isCandidateGroup(il)
					&& BpmUtils.haveOrgInfo(il.getGroupId())) {
				List<? extends IBpmUser> users = groupExpResolveService.resolve(
						il.getGroupId(), bpDataEx);
				if (users != null && !users.isEmpty()) {
					for (IBpmUser user : users) {
						taskService.addCandidateUser(delegateTask.getId(),
								user.getId());
					}
				}

			}
		}

	}

}
