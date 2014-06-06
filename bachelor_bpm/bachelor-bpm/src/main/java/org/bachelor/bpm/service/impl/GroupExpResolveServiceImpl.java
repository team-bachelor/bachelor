package org.bachelor.bpm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.service.IAuthService;
import org.bachelor.bpm.service.IBpmHistoryService;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.service.IGroupExpResolveService;
import org.bachelor.bpm.vo.PiStatus;
import org.bachelor.core.entity.IBaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupExpResolveServiceImpl implements IGroupExpResolveService {

	@Autowired
	private IAuthService authService;
	
	@Autowired
	private IBpmRuntimeService bpmRuntimeService;
	
	@Autowired
	private IBpmHistoryService bpmHistoryService;
	
	private static final String PRE_STR="\"";
	private static final String END_STR="\"";
	
	private Log log = LogFactory.getLog(this.getClass());
	
	@Override
	public List<? extends IBaseEntity> resolve(String groupOrgExp, BaseBpDataEx bpDataEx) {
		List<? extends IBaseEntity> users = null;
		List<String> roleOrgIds = new ArrayList<String>();
		String groupAndOrg = resolveGroupExp(groupOrgExp,bpDataEx);
		if(!StringUtils.isEmpty(groupAndOrg)){
			roleOrgIds.add(groupAndOrg);
			users = authService.findUsersByRoleNameAndOrgId(roleOrgIds.toArray(new String[0]));
			
		}
		return users;
	}

	
	/**
	 * 解析Group表达式
	 * 表达式规则：
	 * 1. 角色和单位都是变量：角色名称变量#单位ID变量
	 * 2. 角色是常量，单位是变量："角色名称常量"#单位ID变量
	 * 3. 角色是变量，单位是常量：角色名称变量#"单位ID变量"
	 * 4. 角色和单位都是常量："角色名称变量"#"单位ID变量"
	 * 
	 * @param groupExp Group表达式
	 * @param bpDataEx BaseBpDataEx
	 * 
	 * @return 角色名称值#单位ID值
	 */
	public String resolveGroupExp(String groupExp, BaseBpDataEx bpDataEx) {
		String resolvedGroupName = null;
		String orgId = "";
		String groupName="";
		
		String groupNameVar = StringUtils.substringBeforeLast(groupExp, "#");
		String orgIdVar = StringUtils.substringAfterLast(groupExp, "#");
		Map bpDataExMap=new HashMap();
		//解析角色名称
		if(isConstant(groupNameVar)){
			String tmp = StringUtils.removeStart(groupNameVar, PRE_STR);
			groupName = StringUtils.removeEnd(tmp, END_STR);
		}else{
			try {
				bpDataExMap=PropertyUtils.describe(bpDataEx);
				if(bpDataExMap.get(groupNameVar)==null){
					groupName=(String) bpDataEx.getBusinessExtMap().get(groupNameVar);
				}else{
					groupName = BeanUtils.getProperty(bpDataEx, groupNameVar);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return null;
			} 
		}
		//解析单位Id
		if(isConstant(orgIdVar)){
			String tmp = StringUtils.removeStart(orgIdVar, PRE_STR);
			orgId = StringUtils.removeEnd(tmp, END_STR);
		}else{
			try {
				bpDataExMap=PropertyUtils.describe(bpDataEx);
				if(bpDataExMap.get(orgIdVar)==null){
					orgId=(String) bpDataEx.getBusinessExtMap().get(orgIdVar);
				}else{
					orgId = BeanUtils.getProperty(bpDataEx, orgIdVar);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return null;
			} 
		}
		
		resolvedGroupName = groupName + "#" + orgId;
		return resolvedGroupName;
	}
	
	/**
	 * 解析Group表达式
	 * 表达式规则：
	 * 1. 角色和单位都是变量：角色名称变量#单位ID变量
	 * 2. 角色是常量，单位是变量："角色名称常量"#单位ID变量
	 * 3. 角色是变量，单位是常量：角色名称变量#"单位ID变量"
	 * 4. 角色和单位都是常量："角色名称变量"#"单位ID变量"
	 * 
	 * @param groupExp Group表达式
	 * @param piId 流程实例Id
	 * 
	 * @return 角色名称值#单位ID值
	 */
	public String resolveGroupExp(String groupExp, String piId) {
		String resolvedGroupName = null;
		String orgId = "";
		String groupName="";
		
		PiStatus piStatus = bpmRuntimeService.getPiStatusByPiId(piId);
		if(piStatus == PiStatus.NotFound){
			return null;
		}
		
		String groupNameVar = StringUtils.substringBeforeLast(groupExp, "#");
		String orgIdVar = StringUtils.substringAfterLast(groupExp, "#");
		//解析角色名称
		if(isConstant(groupNameVar)){
			String tmp = StringUtils.removeStart(groupNameVar, PRE_STR);
			groupName = StringUtils.removeEnd(tmp, END_STR);
		}else{
			try {
				if(piStatus == PiStatus.Running){
					groupName = bpmRuntimeService.getPiVariable(piId, groupNameVar).toString();
				}else{
					groupName = bpmHistoryService.getPiVariable(piId, groupNameVar).toString();
				}
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return null;
			} 
		}
		//解析单位Id
		if(isConstant(orgIdVar)){
			String tmp = StringUtils.removeStart(orgIdVar, PRE_STR);
			orgId = StringUtils.removeEnd(tmp, END_STR);
		}else{
			try {
				if(piStatus == PiStatus.Running){
					Object piVar =  bpmRuntimeService.getPiVariable(piId, orgIdVar);
					if(piVar ==null){
							
						return null;
					} 
					orgId = piVar.toString();
				}else{
					Object piVar =  bpmHistoryService.getPiVariable(piId, orgIdVar);
					if(piVar==null){
						
						return null;
					}
					orgId = piVar.toString();
				}
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return null;
			} 
		}
		
		resolvedGroupName = groupName + "#" + orgId;
		return resolvedGroupName;
	}
	
	public boolean isConstant(String str){
		return StringUtils.startsWith(str, PRE_STR) && StringUtils.endsWith(str, END_STR);
	}
}
