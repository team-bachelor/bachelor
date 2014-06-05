package org.bachelor.demo.risklevel.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.demo.risklevel.biz.IPNRiskLevelBiz;
import org.bachelor.demo.risklevel.bp.PNRiskLevelBpDataEx;
import org.bachelor.demo.risklevel.domain.PNRiskLevel;
import org.bachelor.auth.common.AuthConstant;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.service.IAuthRoleUserService;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.ReviewResult;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.context.service.IVLService;
import org.bachelor.facade.service.IBpmContextService;
import org.bachelor.org.domain.User;
import org.bachelor.ps.domain.Function;
import org.bachelor.ps.service.IFunctionService;

@Controller
@RequestMapping("risklevel")
public class PNRiskLevelController {
	
	@Autowired
	private IPNRiskLevelBiz riskLevelService;
	
	@Autowired
	private IVLService vlService;
	
	@Autowired
	private IBpmContextService bpmCtxService;
	
	@Autowired
	private IBpmRuntimeService bpmRuntimeService;
	
	@Autowired
	private IAuthRoleUserService authRoleService;
	
	@Autowired
	private IFunctionService funcService;
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("index")
	public ModelAndView initPnRiskLevel(@RequestParam(value = "funcId",required = false) String funcId){
		List<PNRiskLevel> riskLevels = riskLevelService.findAll();
		if(StringUtils.isEmpty(funcId)){
			funcId = (String)vlService.getSessionAttribute(AuthConstant.FUNCTION_ID);
		}else {
			vlService.setSessionAttribute(AuthConstant.FUNCTION_ID, funcId);
		} 
		return new ModelAndView("risklevel/index","model",riskLevels);
	}
	
	/**
	 * 查询所有信息风险信息,返回风险集合
	 * @return
	 */
	@RequestMapping("initRiskLevel")
	public ModelAndView newRiskLevelInfo(@RequestParam(value="riskLevelId" , required = false) String riskLevelId){
		Function func = funcService.findByUrl("/risklevel/addOrUpdate.htm");
		vlService.setSessionAttribute(AuthConstant.FUNCTION_ID, func.getId());
		return new ModelAndView("risklevel/pnRiskLevel","riskLevel",(riskLevelId==null?null:riskLevelService.findById(riskLevelId)));
	}
	
	/**
	 * 新增风险信息
	 * @param riskLevel
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("addOrUpdate")
	@ResponseBody
	public Map<String,Object> addRiskLevelInfo(PNRiskLevel riskLevel) throws ParseException{
		Map<String,Object> result = new HashMap<String,Object>();
		
		if(riskLevel!=null ){
			User user = (User)vlService.getSessionAttribute(AuthConstant.LOGIN_USER);
			List<Role> roles = (List<Role>)vlService.getSessionAttribute(AuthConstant.LOGIN_USER_ROLE);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			/** 二级单位风险定级**/
			if(verfiySecondDeptAuthority(AuthConstant.SECOND_DEPT_RANK,roles)){
					if(!StringUtils.isEmpty(riskLevel.getRiskLevel()) &&
							StringUtils.isEmpty(riskLevel.getId())){
						riskLevel.setRiskUserId(user.getId());
					}
			}
			/**本部电网风险审核 **/
			if(verfiySecondDeptAuthority(AuthConstant.SECOND_CADRE_POWER,roles)){
					if(!StringUtils.isEmpty(riskLevel.getReview1Msg())){
						riskLevel.setReview1UserId(user.getId());
						riskLevel.setReview1Date(new Date());
					} else {
						if(!StringUtils.isEmpty(riskLevel.getReview1DateTime())){
							riskLevel.setReview1Date(sdf.parse(riskLevel.getReview1DateTime()));
						}
					}
					
					/** 更新数据 **/
					riskLevelService.update(riskLevel);
					
					/** 得到当前节点信息**/
					PNRiskLevelBpDataEx bpDataEx = (PNRiskLevelBpDataEx) bpmCtxService.getBpDataExByBizKey(riskLevel.getId());
					

					
					bpmCtxService.completeReview(bpDataEx.getTaskEx().getTask().getId(), riskLevel.getReview1Msg(), "","","0");
			}
			/** 本部设备风险审核 **/
			if(verfiySecondDeptAuthority(AuthConstant.SECOND_CADRE_DEV,roles)){
				if(!StringUtils.isEmpty(riskLevel.getReview2Msg())){
					riskLevel.setReview2UserId(user.getId());
					riskLevel.setReview2Date(new Date());
				} else {
					if(!StringUtils.isEmpty(riskLevel.getReview2DateTime())){
						riskLevel.setReview2Date(sdf.parse(riskLevel.getReview2DateTime()));
					}
				}
				
				/** 更新数据 **/
				riskLevelService.update(riskLevel);
				
				/** 得到当前节点信息**/
				PNRiskLevelBpDataEx bpDataEx = (PNRiskLevelBpDataEx) bpmCtxService.getBpDataExByBizKey(riskLevel.getId());
				
				bpmCtxService.completeReview(bpDataEx.getTaskEx().getTask().getId(), riskLevel.getReview2Msg(), "","","0");
			}
			/** 二级单位风险审核 **/
			if(verfiySecondDeptAuthority(AuthConstant.SECOND_DEPT_VERIFY,roles)){
				if(!StringUtils.isEmpty(riskLevel.getVerifyMsg())){
					riskLevel.setVerifyUserId(user.getId());
					riskLevel.setVerifyDate(new Date());
				} else {
					if(!StringUtils.isEmpty(riskLevel.getVerifyDateTime())){
						riskLevel.setVerifyDate(sdf.parse(riskLevel.getVerifyDateTime()));
					}
				}
				/** 更新数据 **/
				riskLevelService.update(riskLevel);
				
				/** 得到当前节点信息**/
				PNRiskLevelBpDataEx bpDataEx =  (PNRiskLevelBpDataEx) bpmCtxService.getBpDataExByBizKey(riskLevel.getId());
				 
				ReviewResult rr = ReviewResult.pass;
				if(!riskLevel.getVerifyMsg().equals("ok")){
					rr = ReviewResult.reject;
				}
				
				/** 风险等级是一级，二级  ，把needMulti设置为0**/
				if(riskLevel.getRiskLevel()!= null && Integer.valueOf(riskLevel.getRiskLevel())<=2){
					bpDataEx.setNeedMulti("0");
					/** 本部电网风险审核及当前角色的ID **/
					String roleIds[] = new String[2];
					roleIds[0] = AuthConstant.SECOND_CADRE_POWER;
					roleIds[1] = AuthConstant.SECOND_CADRE_DEV;
					/*int index = 1;
					for(Role role:roles){
						roleIds[index] =role.getId();
						index++;
					}*/
					
					/** 本部电网风险审核及当前角色下所有用户ID **/
					List<User> users = authRoleService.findUsersByRoleIds(roleIds);
					
					List<String> userIds = new ArrayList<String>();
					for(User temp_user : users){
						userIds.add(temp_user.getId());
					}
					/** 把所有用户ID，set到候选人 **/
					bpDataEx.setAssigneeList(userIds);
				}
				/** 风险等级是三级，四级  ，把needMulti设置为1**/
				if(riskLevel.getRiskLevel()!= null && Integer.valueOf(riskLevel.getRiskLevel())>2){
					bpDataEx.setNeedMulti("1");
				}
				
				bpmCtxService.completeReview(bpDataEx.getTaskEx().getTask().getId(), riskLevel.getVerifyMsg(), "","",rr.getName());
			}
			/** 新增信息 **/
			if(StringUtils.isEmpty(riskLevel.getId())){
				if(riskLevel.getId()!=null && "".equals(riskLevel.getId().trim())){
					riskLevel.setId(null);
				}
				
				riskLevelService.save(riskLevel);
				
				/** 启动流程 **/
				PNRiskLevelBpDataEx bpDataEx = new PNRiskLevelBpDataEx();
				bpDataEx.setDomainId(riskLevel.getId());
				bpmCtxService.startProcessInstanceByKey("FX1", bpDataEx);
				
				/** 推进节点进程 **/ 
				bpDataEx = (PNRiskLevelBpDataEx) bpmCtxService.getBpDataExByBizKey(riskLevel.getId());
				bpmRuntimeService.complete(bpDataEx.getTaskEx().getTask().getId());
			}   
			
			result.put("resultCode", "0");
		} else {
			result.put("resultCode", "1");
		}
		
		return result;
	}
	
	/**
	 * 审核二级单位电网风险审核 角色
	 * @param riskLevel
	 * @return   
	 * 
	 */
	//fafa39f040b8e5ed0140b8ecf8d3002b
	public boolean verfiySecondDeptAuthority(String authId,List<Role> roles){
		if(roles!=null && roles.size()>0){
			for(Role role : roles){
				 if(role.getId().equals(authId)){
					 
					 return true;
				 }
			}
		}
		return false;
	}
}
