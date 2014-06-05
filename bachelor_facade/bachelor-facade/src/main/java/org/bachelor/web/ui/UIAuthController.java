package org.bachelor.web.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bachelor.auth.common.AuthConstant;
import org.bachelor.auth.dao.IAuthUiResourceDao;
import org.bachelor.auth.domain.AuthUiResource;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.vo.AuthUiResourceVo;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.context.service.IVLService;
import org.bachelor.facade.service.IBpmContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * UIAuth过滤角色
 * @author user
 *
 */

@Service
@RequestMapping("/ui/auth/fd/")
public class UIAuthController {

	@Autowired
	private IAuthUiResourceDao uiDao;
	
	@Autowired
	private  IVLService vlService;
	
	@Autowired
	private IBpmContextService bpmContextService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("findByFuncIdAndRoleIds.htm")
	@ResponseBody
	public List<AuthUiResourceVo> findByFuncIdAndRoleIds(String funcId,
			String bizKey) {
		List<Role> role_list = (ArrayList<Role>)vlService.getSessionAttribute(AuthConstant.LOGIN_USER_ROLE);
		List<AuthUiResourceVo> aurvs = new ArrayList<AuthUiResourceVo>();
		if(role_list!=null && role_list.size()>0){
			aurvs = uiDao.findAuthUiResourceVoInfo("findByFuncIdAndRoleIds", funcId, null, null, null,null, null,  getRoleIdsList(role_list), null);
		}  
		
		/** bizkey为空，直接返回集合，不执行下面逻辑代码**/
		if(StringUtils.isEmpty(bizKey) || "undefined".equals(bizKey)){
			
			return aurvs;
		}
		
		BaseBpDataEx bpDataEx = bpmContextService.getBpDataExByBizKey(bizKey);
		
		/** bpDataEx为空，直接返回集合，不执行下面逻辑代码**/
		if(bpDataEx==null){
			
			return aurvs;
		}
		 
		/** 流程ID **/
		String pid = bpDataEx.getTaskEx().getTask().getProcessDefinitionId();
		
		String pdKey[]=pid.split(":");
		/** 节点ID**/
		String taskId = bpDataEx.getTaskEx().getTask().getTaskDefinitionKey();
		//TODO 将此处改为传入pdKey
//		List<AuthUiResourceVo> uiAuths = uiDao.findAuthUiResourceVoInfo("findUiAuthByFuncIdAndRoleIdsAndFlowInfo", funcId, 
//				null, null,pid,taskId,null, getRoleIdsList(role_list), null);
		
		List<AuthUiResourceVo> uiAuths = uiDao.findAuthUiResourceVoInfo("findUiAuthByFuncIdAndRoleIdsAndFlowInfo", funcId, 
				null, null,pdKey[0],taskId,null, getRoleIdsList(role_list), null);
		
		
		/** uiAuths为空，直接返回集合，不执行下面逻辑代码**/
		if(uiAuths==null || uiAuths.size()<=0){
			
			return aurvs;
		}
		
		/** uiAuths不为空,aurv为空时，直接返回uiAuths集合，不执行下面逻辑代码**/
		if(aurvs==null || aurvs.size()<=0 && (uiAuths!=null && uiAuths.size()>0)){
			
			return uiAuths;
		}
		
		/** 合并集合 **/
		List<AuthUiResourceVo> mergeAurvs = new ArrayList<AuthUiResourceVo>();
		List<AuthUiResourceVo> equalAurvs = new ArrayList<AuthUiResourceVo>();
		for(AuthUiResourceVo aurv : aurvs){
			AuthUiResourceVo tempAurv = aurv;
			for(AuthUiResourceVo uiAuth : uiAuths){
				if(aurv.getUiResourceId().equals(uiAuth.getUiResourceId())){
					tempAurv = uiAuth;
					/** 拿到相同对象 **/
					equalAurvs.add(uiAuth);
					break;
				}
			}
			mergeAurvs.add(tempAurv);
		}
		
		/** 根据相同的对象，获取没有做比较的对象值 **/
		if(equalAurvs!=null && equalAurvs.size()>0){
			/** 获取流程UI权限 **/
			mergeAurvs.addAll(getNotEqualForList(uiAuths,equalAurvs));
			/** 获取角色UI权限 **/
			mergeAurvs.addAll(getNotEqualForList(aurvs,equalAurvs));
		}
		
		return mergeAurvs;
	}
	/**
	 *	 获取不相等集合数据
	 * @param uiAuths
	 * @param equalAurvs
	 * @return
	 */
	public List<AuthUiResourceVo> getNotEqualForList(List<AuthUiResourceVo> uiAuths,List<AuthUiResourceVo> equalAurvs){
		List<AuthUiResourceVo> notEqualAurvs = new ArrayList<AuthUiResourceVo>();
		for(AuthUiResourceVo uiAuth : uiAuths){
			boolean flag = true;
			for(AuthUiResourceVo equalUiAuth : equalAurvs){
				if(uiAuth.getUiResourceId().equals(equalUiAuth.getUiResourceId())){
					flag = false;
					break;
				}
			}
			if(flag){
				notEqualAurvs.add(uiAuth);
			}
		}
		return notEqualAurvs;
	}
	
	public List<String> getRoleIdsList(List<Role> role_list){
		List<String> temp_list = new ArrayList<String>();
		for(Role role : role_list){
			temp_list.add(role.getId());
		}
		return temp_list;
	}
	
	@RequestMapping("existsRole")
	@ResponseBody
	public String isExistsReloInfo(String funcId,String uiId){
		String flag = "0";
		List<AuthUiResource> aurs = uiDao.findByFunctIdAndUiId(funcId, uiId);
		if(aurs!=null && aurs.size()>0){
			flag = "1";
		}
		return flag;
	}
}
