package org.bachelor.bpm.service;

import org.bachelor.bpm.domain.BaseBpDataEx;

/**
 * 回退流程服务接口
 * @author 
 *
 */
public  interface IBpmRejectService {

	/**
	 * 回退流程
	 * @param taskId 当前节点Id
	 */
	public void reject(String taskId,BaseBpDataEx bpDataEx);
}
