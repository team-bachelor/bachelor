package org.bachelor.facade.util;

import java.text.SimpleDateFormat;
import java.util.List;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.task.TaskDefinition;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.service.IRoleService;
import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.bpm.service.IBpmRepositoryService;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.service.IBpmRuntimeTaskService;
import org.bachelor.bpm.vo.GraphicData;
import org.bachelor.core.entity.IBaseEntity;
import org.bachelor.facade.service.IBpmContextService;
import org.bachelor.org.domain.User;
import org.bachelor.org.service.IUserService;

/**
 * 转换数据
 * @author user
 *
 */
public class ConvertToVo {
	
	/**
	 * 返回单个图形节点属性对象,根据当前节点数据
	 * @param te
	 * @param ius
	 * @param bpmCtxService
	 * @return
	 */
	public static GraphicData toGraphicDataBySingleTaskEx(String processType,String type,TaskEx te,IUserService ius,IBpmContextService bpmCtxService,
		    IBpmRuntimeTaskService bpmRuntimeTaskService,
			IBpmRepositoryService bpmRepositoryService,IRoleService roleService,IBpmRuntimeService bpmRuntimeService){
		GraphicData gd = new GraphicData();
		gd.setId(te.getTask().getTaskDefinitionKey());
		gd.setTitle(te.getTask().getName());
		gd.setType(type);
		gd.setAssignee(te.getTask().getAssignee());
		if(gd.getAssignee()!=null && !"".equals(gd.getAssignee())){
			User user  = ius.findById(gd.getAssignee());
			gd.setAssigneeName(user.getUsername());
			gd.setAssigneeUnit(user.getOwnerOrg().getNamePath());
		}
		gd.setOwner(te.getTask().getOwner());
		if(gd.getOwner()!=null && !"".equals(gd.getOwner())){
			gd.setOwnerName(ius.findById(gd.getOwner()).getUsername());
		}
		gd.setTitle(te.getTask().getName());
		/** 得到待办人信息 **/
		getCandidateInfoByPidAndTdkey(te.getTask().getProcessInstanceId(),te.getTask().getTaskDefinitionKey(),gd , bpmCtxService,bpmRuntimeService);
		if(processType!=null && processType.equals("get")){
			/** 得到角色角色信息 **/
			getCandidataRoles(te.getTask().getProcessInstanceId(),te.getTask().getTaskDefinitionKey(),te.getTask().getProcessDefinitionId(),bpmRuntimeTaskService,bpmRepositoryService,roleService,gd);
		}
		return gd;
	} 
	
	/** 
	 * 得到待办人信息
	 * @param pid
	 * @param tdKey
	 */
	public static void getCandidateInfoByPidAndTdkey(String pid,String tdKey,GraphicData gd,IBpmContextService bpmCtxService,IBpmRuntimeService bpmRuntimeService){
		/** 得到待办人信息 **/
//		List<IBaseEntity> users = bpmRuntimeService.getTaskCandidateUserByDefKey2(pid, tdKey);
//		if(users!=null && users.size()>0){
//			StringBuffer candidate = new StringBuffer();
//			StringBuffer candidateName = new StringBuffer();
//			for(IBaseEntity user : users){
//				candidate.append(user.getId()).append(",");
//				candidateName.append(user.getName()).append(",");
//			}
//			gd.setCandidate(candidate.toString().length()>0?candidate.toString().substring(0, candidate.toString().length()-1):candidate.toString());
//			gd.setCandidateName(candidateName.toString().length()>0?candidateName.toString().substring(0, candidateName.toString().length()-1):candidateName.toString());
//		}
	}
	
	/**
	 * 返回单个图形节点属性对象,根据历史节点数据
	 * @param te
	 * @param ius
	 * @param bpmCtxService
	 * @param type 图形数据类型 0已执行1为正在执行2未执行
	 * @return
	 */
	public static GraphicData toGraphicDataByTask(String processType,String type,HistoricTaskInstance hti,IUserService userService,
			IBpmContextService bpmCtxService,IBpmRuntimeTaskService bpmRuntimeTaskService,
			IBpmRepositoryService bpmRepositoryService,IRoleService roleService,IBpmRuntimeService bpmRuntimeService){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GraphicData gd = new GraphicData();
		gd.setId(hti.getTaskDefinitionKey());
		gd.setTitle(hti.getName());
		gd.setType(type);
		gd.setStartTime(hti.getStartTime()!=null?sdf.format(hti.getStartTime()):"");
		gd.setEndTime(hti.getEndTime()!=null?sdf.format(hti.getEndTime()):"");
		
		if(hti.getAssignee()!=null && !"".equals(hti.getAssignee())){
			User user = userService.findById(hti.getAssignee());
			gd.setAssignee(hti.getAssignee());
			gd.setAssigneeName(user.getUsername());
			gd.setAssigneeUnit(user.getOwnerOrg().getNamePath());
		}
		/** 得到待办人信息 **/
		getCandidateInfoByPidAndTdkey(hti.getProcessInstanceId(),hti.getTaskDefinitionKey(),gd,bpmCtxService,bpmRuntimeService);
		if(processType!=null && processType.equals("get")){
			/** 得到待办人角色信息 **/
			getCandidataRoles(hti.getProcessInstanceId(),hti.getTaskDefinitionKey(),hti.getProcessDefinitionId(),bpmRuntimeTaskService,bpmRepositoryService,roleService,gd);
		}
		return gd;
	}
	
	/**
	 * 获取待办角色信息
	 */
	public static void getCandidataRoles(String pid,String definitionKey,String pdid,IBpmRuntimeTaskService bpmRuntimeTaskService,
			IBpmRepositoryService bpmRepositoryService,IRoleService roleService,GraphicData gd){
		List<TaskDefinition> taskDefinitions = bpmRepositoryService.getAllTaskDefinition(pdid);
		if(taskDefinitions!=null && taskDefinitions.size()>0){
			List<String> roles = null;
			for(TaskDefinition td : taskDefinitions){
				if(td.getKey().equals(definitionKey)){
						roles = bpmRuntimeTaskService.getRoleByTaskDefinition(td, pid);
						break;
				}
			}
			if(roles!=null){
				StringBuilder roleNames = new StringBuilder();
				Object[] roleArray =  roles.toArray();
				if(roleArray!=null && roleArray.length>0){
					for(Object tempRoleName : roleArray){
						String convertRole = (String) tempRoleName;
						String splitRoles[] = convertRole.split("#");
						Role role =roleService.findByName(splitRoles[0]);
						roleNames.append(role.getMemo()).append(",");
					}
				}
				gd.setCandidateRoles(roleNames.length()>0?roleNames.toString().substring(0,roleNames.toString().length()-1):"");
			}
		}
	}
}
