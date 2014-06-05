package org.bachelor.console.user.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.auth.common.AuthConstant;
import org.bachelor.auth.service.IUserManageService;
import org.bachelor.auth.vo.AuthUiResourceVo;
import org.bachelor.context.service.IVLService;
import org.bachelor.dao.DaoConstant;
import org.bachelor.org.domain.User;

@Controller
@RequestMapping("/user/")
public class UserManageController {
	
	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private IVLService vlService;
	
	@Autowired
	private IUserManageService userManageService = null;
	/**
	 * 功能: 人员管理主页面
	 * 作者 曾强 2013-5-30上午09:23:23
	 * @return
	 */
	@RequestMapping("index")
	public ModelAndView index(){

		return new ModelAndView("user/index");
	}
	
	@RequestMapping("add")
	@ResponseBody
	public Map<String,String> addUserInfo(User user){
		Map<String,String> user_info = new HashMap<String,String>();
		if(user!=null){
			try{
				if(user.getMemo()!=null ){
					user.setMemo(new String(user.getMemo().getBytes("ISO-8859-1"),"UTF-8"));
				}
				if(user.getUsername()!=null){
					user.setUsername(new String(user.getUsername().getBytes("ISO-8859-1"),"UTF-8"));
				}
				if(user.getDuty()!=null){
					user.setDuty(new String(user.getDuty().getBytes("ISO-8859-1"),"UTF-8"));
				}
				//初始化密码
				if(user.getPwd()==null || user.getPwd().equals("")){
					user.setPwd(AuthConstant.DEFAULT_PWD);
				}
			}catch(Exception e){
				user_info.put("ResultCode", "2");
			}
			
			
			List<User> user_list = userManageService.validateById(user.getId());
			if(user.getProcessType().equals("false")){//新增
				if(user_list!=null && user_list.size()>0){
					user_info.put("ResultCode", "1");
				} else {
					user.setType("2");
					user.setStatusFlag("1");
					user.setCreateTime(new Date());
					user_info.put("ResultCode", "0");
					userManageService.add(user);
				}
			} else {//修改
				if(user_list!=null && user_list.size()>0){
					User tempUser = user_list.get(0);
					tempUser.setUpdateTime(new Date());
					if(tempUser.equals(user.getId())){
						user_info.put("ResultCode", "1");
					} else {
						user_info.put("ResultCode", "0");
						tempUser.setDuty(user.getDuty().equals("")?tempUser.getDuty():user.getDuty());
						tempUser.setGender(user.getGender().equals("")?tempUser.getGender():user.getGender());
						tempUser.setIdentifyCode(user.getIdentifyCode().equals("")?tempUser.getIdentifyCode():user.getIdentifyCode());
						tempUser.setLoginFlag(user.getLoginFlag().equals("")?tempUser.getLoginFlag():user.getLoginFlag());
						tempUser.setMemo(user.getMemo().equals("")?tempUser.getMemo():user.getMemo());
						tempUser.setOwnerOrgId(user.getOwnerOrgId().equals("")?tempUser.getOwnerOrgId():user.getOwnerOrgId());
						tempUser.setPwd(user.getPwd().equals("")?tempUser.getPwd():user.getPwd());
						tempUser.setUsername(user.getUsername().equals("")?tempUser.getUsername():user.getUsername());
						userManageService.update(tempUser);
					}
				} 
			}
		}
		return user_info;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,String> deleteUserInfo(@RequestParam(value="delInfo") String info){
		Map<String,String> user_info = new HashMap<String,String>();
		try{
			userManageService.batchDelete(info);
			user_info.put("ResultCode", "0");
		}catch(Exception e){
			user_info.put("ResultCode", "1");
		}
		return user_info;
	}
	
	@RequestMapping("all")
	@ResponseBody
	public Map<String,Object> findUserByExample(User user){
		try{
			if(user!=null){
					if(user.getUsername()!=null){
						user.setUsername(new String(user.getUsername().getBytes("ISO-8859-1"),"UTF-8"));
					}
			}
		}catch(Exception e){
			log.info("用户搜索时用户名称转码异常.",e);
		}
		List<User> user_list = userManageService.findByExample(user);
		Map<String,Object> user_map = new HashMap<String,Object>();
		user_map.put("pageResult", vlService.getRequestAttribute(DaoConstant.PAGE_INFO));
		user_map.put("dataResult", (user_list!=null?user_list:new ArrayList<AuthUiResourceVo>()));
		return user_map;
	}
}
