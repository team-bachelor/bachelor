package org.bachelor.facade.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.repository.DiagramLayout;
import org.activiti.engine.repository.DiagramNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.auth.service.IRoleService;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.bpm.service.IBpmHistoryService;
import org.bachelor.bpm.service.IBpmRepositoryService;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.service.IBpmRuntimeTaskService;
import org.bachelor.bpm.vo.GraphicData;
import org.bachelor.facade.service.IBpmContextService;
import org.bachelor.facade.service.ITaskGraphicDataService;
import org.bachelor.facade.util.ConvertToVo;
import org.bachelor.org.domain.User;
import org.bachelor.org.service.IUserService;

@Service
public class TaskGraphicDataServiceImpl implements ITaskGraphicDataService{

	@Autowired
	private IUserService userService;

	@Autowired
	private IBpmRuntimeTaskService bpmRuntimeTaskService;
	
	@Autowired
	private IBpmRepositoryService bpmRepositoryService;
	
	@Autowired
	private IBpmContextService bpmCtxService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IBpmRuntimeService bpmRuntimeService;
	
	@Autowired
	private IBpmHistoryService bpmHistoryService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public Map<String,Object> findByBizKey(String bizKey) {
			/** 得到 BaseBpDataEx(流程和业务数据交换)对象 **/	
			BaseBpDataEx bpDataEx = bpmCtxService.getBpDataExByBizKey(bizKey);
		
			Map<String,Object> gd_map = new HashMap<String,Object>();
			//流程尚未执行的节点
			List<TaskEx> taskEXList=bpmCtxService.getUnFinishedTaskByPiid(bpDataEx.getPiId());
			
			//流程已执行的节点
			List<HistoricTaskInstance> hisTaskExList=bpmCtxService.getFinishedTaskByPiid(bpDataEx.getPiId());
			
			List<GraphicData> graphicDatas= getGraphicDatasByHistoricTaskInstance("get",hisTaskExList,bpDataEx.getPiId());
			
			gd_map.put("taskBorderData", getDiagramPropertiesInfo(null,bpDataEx.getTaskEx().getTask().getProcessDefinitionId(),
					graphicDatas));
			gd_map.put("taskDatas", graphicDatas);
 
			return gd_map;
	}

	/**
	 * 获取节点图形集合，根据历史信息
	 * @param hisTaskExList
	 * @param piId
	 * @return
	 */
	public List<GraphicData> getGraphicDatasByHistoricTaskInstance(String processType,List<HistoricTaskInstance> hisTaskExList,String piId){
		/**当前活动节点 **/
		TaskEx taskEx = bpmCtxService.getActiveTask(piId);
		
		List<GraphicData> temp_gds = new ArrayList<GraphicData>();
		List<HistoricTaskInstance> removeHtis = new ArrayList<HistoricTaskInstance>();
		for(HistoricTaskInstance hti : hisTaskExList){
			if(hti.getTaskDefinitionKey().equals(taskEx.getTask().getTaskDefinitionKey())){
				/** 把图形数据添加到集合中**/
				temp_gds.add(ConvertToVo.toGraphicDataByTask(processType,"1",hti, userService, bpmCtxService,bpmRuntimeTaskService,bpmRepositoryService,roleService,bpmRuntimeService));
				/** 删除当前节点所有对象 **/
				removeHtis.add(hti);
			}
		}
		hisTaskExList.removeAll(removeHtis);
		/** 当前活动节点 **/
		GraphicData activitGraphicData = ConvertToVo.toGraphicDataBySingleTaskEx(processType,"1",taskEx, userService, bpmCtxService,bpmRuntimeTaskService,
				bpmRepositoryService,roleService,bpmRuntimeService);
		activitGraphicData.setGds(temp_gds);
		/**当前活动节点 **/
		
		/** 获取历史节点图形数据的defindKey **/
		List<String> defindKeys = new ArrayList<String>();
		List<GraphicData> graphicDatas= new ArrayList<GraphicData>();
		for(HistoricTaskInstance hti : hisTaskExList){
				boolean flag = false;
				for(String defindKey : defindKeys){
					if(defindKey.equals(hti.getTaskDefinitionKey())){
						flag = true;
					}
				}
				if(flag == false){
					defindKeys.add(hti.getTaskDefinitionKey());
					graphicDatas.add(ConvertToVo.toGraphicDataByTask(processType,"0",hti, userService, bpmCtxService,
							bpmRuntimeTaskService,bpmRepositoryService,roleService,bpmRuntimeService));
				}
		}
		/** 通过历史的节点图形数据的key,得到节点图形属性及数据**/
		if(defindKeys!=null && defindKeys.size()>0){
			for(String defindKey : defindKeys){
				List<GraphicData> hisGraphicDatas = new ArrayList<GraphicData>();
				for(HistoricTaskInstance hti : hisTaskExList){
					if(defindKey.equals(hti.getTaskDefinitionKey())){
							GraphicData gd = new GraphicData();
							gd.setId(hti.getTaskDefinitionKey());
							gd.setStartTime(hti.getStartTime()!=null?sdf.format(hti.getStartTime()):"");
							gd.setEndTime(hti.getEndTime()!=null?sdf.format(hti.getEndTime()):"");
							/** 得到待办人信息 **/
							ConvertToVo.getCandidateInfoByPidAndTdkey(hti.getProcessInstanceId(), hti.getTaskDefinitionKey(), gd, bpmCtxService, bpmRuntimeService);
							if(hti.getAssignee()!=null && !"".equals(hti.getAssignee())){
								User user = userService.findById(hti.getAssignee());
								gd.setAssignee(hti.getAssignee());
								gd.setAssigneeUnit(user.getOwnerOrg().getNamePath());
								gd.setAssigneeName(user.getUsername());
							}
							hisGraphicDatas.add(gd);
					}
				}
				/** 把历史信息添加入VO中**/
				for(GraphicData gd : graphicDatas){
					if(gd.getId().equals(defindKey)){
						gd.setGds(hisGraphicDatas);
					}
				}
			}
		}
		
		/** 把当前节点数据和历史数据整合起来 **/
		graphicDatas.add(activitGraphicData);
		
		return graphicDatas;
	}

	/**
	 * 功能: 根据bizKey，查询历史流程信息
	 * 作者 曾强 2013-8-20下午6:36:09
	 * @param bizKey
	 * @return
	 */
	@Override
	public Map<String, Object> findHistoricByBizKey(String bizKey) {
		Map<String,Object> gd_map = new HashMap<String,Object>();
		List<HistoricTaskInstance> historics = bpmHistoryService.getProcessHistoricByBizKey(bizKey);
		HistoricProcessInstance hpi = bpmHistoryService.findByBizKey(bizKey);
		 
		TaskEx taskEx = bpmRuntimeTaskService.getActiveTask(hpi.getId());
		if(taskEx!=null){
				HistoricTaskInstanceEntity htie = new HistoricTaskInstanceEntity();
				htie.setAssignee(taskEx.getTask().getAssignee());
				htie.setId(taskEx.getTask().getId());
				htie.setStartTime(taskEx.getTask().getCreateTime());
				htie.setProcessInstanceId(taskEx.getTask().getProcessInstanceId());
				htie.setProcessDefinitionId(taskEx.getTask().getProcessDefinitionId());
				htie.setTaskDefinitionKey(taskEx.getTask().getTaskDefinitionKey());
				historics.add(htie);
		}
		
		List<GraphicData> graphicDatas = new ArrayList<GraphicData>();
		List<GraphicData> tempList = new ArrayList<GraphicData>();
		if(historics!=null && historics.size()>0){
			for(HistoricTaskInstance hti : historics){
				GraphicData gd = new GraphicData();
				gd.setId(hti.getTaskDefinitionKey());
				gd.setStartTime(hti.getStartTime()!=null?sdf.format(hti.getStartTime()):"");
				gd.setEndTime(hti.getEndTime()!=null?sdf.format(hti.getEndTime()):"");
				/** 得到待办人信息 **/
				ConvertToVo.getCandidateInfoByPidAndTdkey(hti.getProcessInstanceId(), hti.getTaskDefinitionKey(), gd, bpmCtxService, bpmRuntimeService);
				if(hti.getAssignee()!=null && !"".equals(hti.getAssignee())){
					User user = userService.findById(hti.getAssignee());
					gd.setAssignee(hti.getAssignee());
					gd.setAssigneeUnit(user.getOwnerOrg().getNamePath());
					gd.setAssigneeName(user.getUsername());
				}  
				tempList.add(gd);
			}
			
		}
		/** 把历史信息添加入VO中**/
		/*for(GraphicData gd : tempList){
			if(gd.getId().equals(hpi defindKey)){
				gd.setGds(hisGraphicDatas);
			}
		}*/
		
		/** 节点属性对象 **/
		List<DiagramNode> dns = getDiagramPropertiesInfo("type",hpi.getProcessDefinitionId(),
				tempList);
		if(dns!=null && dns.size()>0){
			for(DiagramNode dn : dns){
				List<GraphicData> clist = new ArrayList<GraphicData>();
				GraphicData tempGd = new GraphicData();
				String tempType = null;
				for(GraphicData gd : tempList){
					if(gd.getId().equals(dn.getId())){
						clist.add(gd);
						if(StringUtils.isEmpty(gd.getEndTime())){
							
							tempType = "1";
						}
					}
				}
				tempGd.setId(dn.getId());
				tempGd.setGds(clist);
				if(!StringUtils.isEmpty(tempType)){
					tempGd.setType(tempType);
				} else {
					tempGd.setType("0");
				}
				graphicDatas.add(tempGd);
			}
		}
		gd_map.put("taskBorderData", dns);
		gd_map.put("taskDatas", graphicDatas);
		return gd_map;
	}
	

	/**
	 * 功能: 得到流程属性数据
	 * 作者 曾强 2013-8-20下午6:45:52
	 * @param pdId
	 * @param graphicDatas
	 * @return
	 */
	public List<DiagramNode> getDiagramPropertiesInfo(String processType,String pdId,List<GraphicData> graphicDatas){
			/**获取所有节点位置数据**/
			DiagramLayout dl = null;
			if(processType==null || "".equals(processType)){
				dl = bpmCtxService.getProcessDiagramLayout();
			} else {
				dl = bpmCtxService.getProcessDiagramLayout(pdId);
			}
			List<DiagramNode> dns = new ArrayList<DiagramNode>();
			/** 循环出节点位置数据**/
			for(GraphicData te : graphicDatas){
				dns.add(dl.getNode(te.getId()));
			}
			return dns;
	}
	
}
