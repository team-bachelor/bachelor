package org.bachelor.bpm.service;

import java.util.List;

import org.bachelor.core.entity.IBaseEntity;

/**
 * 流程办理候选人和办理人服务接口
 * 开发时间：2013-11-29
 * 该接口提供所有查询和设置流程（节点）代办人和办理人的实现
 * @author zhangtao
 *
 */
public interface IBpmCandidateService {
	/**
	 * 根据业务key，获取流程当前节点的 所有出线节点的代办人列表
	 * 
	 * @param bizKey
	 * @return 所有出线节点的代办人集合
	 */
	public List<IBaseEntity> getNextTaskCandidateUser(String bizKey);
	
	/**
	 * 
	 * @param taskId
	 * @return
	 */
	public List<IBaseEntity> getUsersByTaskId(String taskId);
	/**
	 * 根据用户id获取用户对应的权限id
	 * @param userId
	 * @return
	 */
	public List<String> getGroupIdByUser(String userId);
	
	
	public List<String> getTaskCandidateUserByBpDateEX(String candidateArg);
	
	
	
}
