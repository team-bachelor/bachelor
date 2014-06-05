package org.bachelor.console.auth.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.bachelor.auth.domain.AuthFunction;
import org.bachelor.auth.service.IAuthFunctionService;
import org.bachelor.auth.vo.PostRequestDataVo;
import org.bachelor.gv.vo.GlobalEnumTvVo;

@Controller
@RequestMapping("/auth/assign/")
public class AuthFunctionController {
	@Autowired
	private IAuthFunctionService authFunctionService = null;  
	
	private Log log = LogFactory.getLog(this.getClass());
	/**
	 * 功能: 删除
	 * 作者 曾强 2013-5-14下午05:38:57
	 * @param auth
	 */
	@RequestMapping(value="saveOrUpdate")
	@ResponseBody
	public Map<String,Object> saveOrUpdate(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> auth_map = new HashMap<String,Object>();
		try{
			PostRequestDataVo postRequestDataVo = new PostRequestDataVo();
			postRequestDataVo.setAuthFuncs(request.getParameter("allRoleFuncs"));
			/** 新增所有功能权限信息 **/
			authFunctionService.saveOrUpdate(postRequestDataVo.getAuthFuncs());
			auth_map.put("Msg", "0");
		}catch(Exception e){
			auth_map.put("Msg", "1");
			log.info("保存功能权限信息失败.",e);
		}
		return auth_map;
	}
	
	@RequestMapping("all")
	@ResponseBody
	public List<AuthFunction> findAll(){
	
		return authFunctionService.findAll();
	}
	
	@RequestMapping("single")
	@ResponseBody
	public AuthFunction findAuthFunctionById(String id){
		
		return authFunctionService.findAuthFunctionById(id);
	}
	
	@RequestMapping("delete")
	public void deleteAuthFunctionInfo(AuthFunction auth){
		
		authFunctionService.deleteAuthFunctionInfo(auth);
	}
	
	@RequestMapping("authFuncList")
	@ResponseBody
	public List<AuthFunction> findAuthFunctionByFuncId(){
		List<AuthFunction> authFunctions = authFunctionService.findAuthFunctionByFuncId(null);
		return authFunctions;
	}
	
	/**
	 * 功能: 查询AuthFunction特定信息
	 * 作者 曾强 2013-5-21上午09:42:22
	 * @param funcId
	 * @return
	 */
	@RequestMapping("tv")
	@ResponseBody
	public List<GlobalEnumTvVo> findAuthTvByFuncId(String funcId){
		List<GlobalEnumTvVo> list = authFunctionService.findTvInfo(funcId);
		return list;
	}
}
