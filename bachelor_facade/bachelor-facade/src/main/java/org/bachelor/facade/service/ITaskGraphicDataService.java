package org.bachelor.facade.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstance;

import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.vo.GraphicData;

/**
 * 获取节点图形数据接口
 * @author user
 *
 */
public interface ITaskGraphicDataService {

	/**
	 * 得到图形数据，根据BaseBpDataEx实例
	 * @param bpDataEx
	 * @return
	 */
	public Map<String,Object> findByBizKey(String bizKey,String userId) ;
	
	/**
	 * 获取节点图形集合，根据历史信息
	 * @param hisTaskExList
	 * @param piId
	 * @return
	 */
	public List<GraphicData> getGraphicDatasByHistoricTaskInstance(String processType,List<HistoricTaskInstance> hisTaskExList,String piId);
	
	/**
	 * 功能: 根据bizKey，查询历史流程信息
	 * 作者 曾强 2013-8-20下午6:36:09
	 * @param bizKey
	 * @return
	 */
	public Map<String,Object> findHistoricByBizKey(String bizKey,String userId);
}
