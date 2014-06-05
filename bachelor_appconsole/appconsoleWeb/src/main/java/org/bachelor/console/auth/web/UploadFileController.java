package org.bachelor.console.auth.web;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.bachelor.auth.domain.AuthFunction;
import org.bachelor.auth.domain.AuthRoleUser;
import org.bachelor.auth.service.IAuthFunctionService;
import org.bachelor.auth.service.IAuthRoleUserService;
import org.bachelor.console.common.Constant;
import org.bachelor.web.util.ParseBigDatasExcel;
import org.bachelor.web.util.ParseExcel;

@Controller
@RequestMapping("/upload/")
public class UploadFileController {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private IAuthFunctionService authFunctionService;
	
	@Autowired
	private IAuthRoleUserService authRoleUserService;
	
	/**
	 * 导入功能角色信息
	 * @param response
	 * @param file
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "roleFunc", method = RequestMethod.POST)
	public void uploadRole(HttpServletResponse response,
			@RequestParam("uploadFilePath") MultipartFile file){
		PrintWriter printWriter = null;
		Map<String,Object> maps = new HashMap<String,Object>();
		try{
			printWriter = response.getWriter();
			if(!file.isEmpty()){
				/** 获取功能权限对象集合 **/
				InputStream  input = file.getInputStream();
				ParseExcel pe = ParseExcel.getParseExcel();
				List<AuthFunction> afs = (List<AuthFunction>) pe.getEnityLists(input,Constant.ENITY_TYPE_AUTHFUNCTION);
				/** 批量保存功能权限信息 **/
				authFunctionService.batchSaveAuthFunction(afs);
			}
			maps.put("\"resultCode\"", "\"1\"");
		}catch(Exception e){
			maps.put("\"resultCode\"", "\"0\"");
			e.printStackTrace();
			log.info("上传功能权限异常",e);
		}
		printWriter.write("<script>parent.uploadReturnResult('"+maps+"');</script>");
	}
	
	/**
	 * 导入角色人员数据
	 * @param response
	 * @param file
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "rolePersonal", method = RequestMethod.POST)
	public void uploadRolePersional(HttpServletResponse response,
			@RequestParam("uploadFilePath") MultipartFile file){
		PrintWriter printWriter = null;
		Map<String,Object> maps = new HashMap<String,Object>();
		try{
			printWriter = response.getWriter();
			if(!file.isEmpty()){
				/** 获取角色人员对象集合 **/
				InputStream  input = file.getInputStream();
				ParseBigDatasExcel pbde = new ParseBigDatasExcel();
				List<AuthRoleUser> arus = (List<AuthRoleUser>) pbde.readOneSheet(input,Constant.ENITY_TYPE_ROLEPERSIONAL);
				/** 批量角色人员权限信息 **/
				authRoleUserService.batchSaveRolesPersonal(arus);
			}
			maps.put("\"resultCode\"", "\"1\"");
		}catch(Exception e){
			maps.put("\"resultCode\"", "\"0\"");
			e.printStackTrace();
			log.info("上传角色人员异常",e);
		}
		printWriter.write("<script>parent.uploadReturnResult('"+maps+"');</script>");
	}
}
