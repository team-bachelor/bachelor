package org.bachelor.console.auth.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.auth.domain.AuthRoleUser;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.service.IAuthRoleUserService;
import org.bachelor.auth.vo.AuthRoleUserVo;
import org.bachelor.org.domain.User;
import org.bachelor.org.service.IUserService;
import org.bachelor.stat.domain.StatUserTrack;
import org.bachelor.stat.service.IStatUserTrackService;

@Controller
@RequestMapping("/auth/user/")
public class AuthReloUserController {

	@Autowired
	private IAuthRoleUserService aruService = null;
	
	@Autowired
	private IUserService userService = null;
	 
	@Autowired
	private IAuthRoleUserService arus;
	
	/**
	 * 功能: 跳转到主页
	 * 作者 曾强 2013-5-22上午10:02:58
	 * @return
	 */
	@RequestMapping("index")
	public ModelAndView redriectIndex(){
		/*String ids[] = {"8a1c53cc3fe78447013fe78cbb220000#0619250000","8a1c528b3ff14ccb013ff14df41b0000#0619250000"};
		String names[] = {"verifyGroup#0664010500","guest","user#0632040000"};
		Set<User> users = aruService.findUsersByRoleIdAndOrgId(ids);
		Set<User> users1 = aruService.findUsersByRoleNameAndOrgId(names);
		StatUserTrack sut = statUserTrackService.getLasted();*/
		return new ModelAndView("/auth/user/index");
	}
	
	/**
	 * 功能:保存或者更新信息
	 * 作者 曾强 2013-5-22上午10:04:25
	 * @param aru
	 * @return
	 */
	@RequestMapping("add")
	@ResponseBody
	public Map<String,String> addOrUpdate(AuthRoleUser aru) throws UnsupportedEncodingException{
		Map<String,String> aru_map = new HashMap<String,String>();
		//得到前端人员信息
		aru.getUser().setId(URLDecoder.decode(aru.getUser().getId(),"UTF-8"));
		if(aru.getUser()!=null && aru.getUser().getId()!=null && !aru.getUser().getId().equals("")){
			String tempUserName[] = aru.getUser().getId().split("/");
			if(tempUserName.length>=2 && tempUserName[1]!=null && !tempUserName[1].trim().equals("")){
				aru.getUser().setId(tempUserName[1]);
			}
		}
		User user = userService.findById(aru.getUser().getId());
		
		if(user!=null){
			//添加用户信息时用户ID设置为NULL
			if(aru.getId()!=null && aru.getId().trim().equals("")){
				aru.setId(null);
			} 
			//判断角色和人员是否重复添加 
			List<AuthRoleUser> aruVo_list  = aruService.findListByExample(aru);
			/** 用户名及角色ID 查询 角色人员集合信息 **/
			if(aruVo_list!=null && aruVo_list.size()==1
					&& StringUtils.isEmpty(aru.getId())){
				aru_map.put("ResultCode", "2");
			} else {
				aru_map.put("ResultCode", "0");
				aruService.addOrUpdate(aru);
			}
			/** 以前代码,现已经修改 **/
			/*
			boolean flag = true;
			if(aruVo_list!=null && aruVo_list.size()==1){
				if(aru.getId()!=null){
					AuthRoleUser tempAru = aruVo_list.get(0);
					if(tempAru.getId().equals(aru.getId())){
						aruService.addOrUpdate(aru);
					} else {
						flag = false;
					}
				} else {
					flag = false;
				}
			} else {
				aruService.addOrUpdate(aru);
			}
			//如果成功更新时ResultCode值为0 重复添加为2
			if(flag){
				aru_map.put("ResultCode", "0");
			} else {
				aru_map.put("ResultCode", "2");
			}*/
		} else {
			aru_map.put("ResultCode", "1");
		}
		return aru_map;
	}
	
	/**
	 * 功能: 删除信息
	 * 作者 曾强 2013-5-22上午10:14:45
	 * @param id
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,String> deleteById(@RequestParam(value="delInfo") String info){
		Map<String,String> aru_map = new HashMap<String,String>();
		try{
			aruService.batchDelete(info);
			aru_map.put("ResultCode", "0");
		}catch(Exception e){
			aru_map.put("ResultCode", "1"); 
		}
		return aru_map;
	}
	
	/**
	 * 功能: 查询信息
	 * 作者 曾强 2013-5-22上午10:47:56
	 * @param aru
	 * @return
	 */
	@RequestMapping("all")
	@ResponseBody
	public Map<String,Object> findByExample(AuthRoleUserVo aruVo){
		if(aruVo!=null){
			if(aruVo.getUserId()!=null && !aruVo.getUserId().equals("")){
				Pattern pt = Pattern.compile("\\w");
				Matcher match = pt.matcher(aruVo.getUserId());
				if(!match.find()){
					aruVo.setUserName(aruVo.getUserId());
					aruVo.setUserId(null);
				}
			}
		}
		List<AuthRoleUserVo> role_list = aruService.findByExample(aruVo);
		Map<String,Object> ge_map = new HashMap<String,Object>();
		ge_map.put("total", (role_list!=null?role_list.size():0));
		ge_map.put("rows", (role_list!=null?role_list:new ArrayList<AuthRoleUser>()));
		return ge_map;
	}
}
