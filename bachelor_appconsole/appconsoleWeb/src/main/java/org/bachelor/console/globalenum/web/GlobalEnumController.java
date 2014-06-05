package org.bachelor.console.globalenum.web;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.gv.domain.GlobalEnum;
import org.bachelor.gv.service.IGlobalEnumService;

@Controller
@RequestMapping("/genum/")
public class GlobalEnumController {

	@Autowired
	private IGlobalEnumService globalEnumService;
	
	/**
	 * 功能:主页面跳转
	 * 作者 曾强 2013-5-17上午10:40:32
	 * @return
	 */
	@RequestMapping("index")
	public ModelAndView redirectIndex(){
		
		return new ModelAndView("/enum/index");
	}
	
	/**
	 * 功能: 保存或者更新枚举
	 * 作者 曾强 2013-5-17上午10:58:46
	 * @param ge
	 * @return
	 */
	@RequestMapping("add")
	@ResponseBody
	public Map<String,String> saveOrUpdateEnumInfo(GlobalEnum ge){
		Map<String,String> ge_map = new HashMap<String,String>();
		try{
			if(ge!=null){
				if(ge.getId()!=null && ge.getId().trim().equals("")){
					ge.setId(null);
				} 
				
				if(ge.getFieldComment()!=null && !ge.getFieldComment().trim().equals("")){//字段注释
					String comment = new String(ge.getFieldComment().getBytes("ISO-8859-1"),"UTF-8");
					ge.setFieldComment(comment);
				}
				ge.setEnumDesc(new String(ge.getEnumDesc().getBytes("ISO-8859-1"),"UTF-8"));
				
				ge.setEnumName(new String(ge.getEnumName().getBytes("ISO-8859-1"),"UTF-8"));
				ge.setFlag(new String(ge.getFlag().getBytes("ISO-8859-1"),"UTF-8"));
				ge.setFieldValue(new String(ge.getFieldValue().getBytes("ISO-8859-1"),"UTF-8"));
				ge.setFieldDesc(new String(ge.getFieldDesc().getBytes("ISO-8859-1"),"UTF-8"));
			}
			globalEnumService.saveOrUpdate(ge);
			ge_map.put("ResultCode", "0");
		}catch(Exception e){
			ge_map.put("ResultCode", "1");
		}
		return ge_map;
	}
	
	/**
	 * 功能: 删除枚举
	 * 作者 曾强 2013-5-17上午11:00:04
	 * @param ge
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,String> deleteEnumInfo(@RequestParam(value="delInfo")String info){
		Map<String,String> ge_map = new HashMap<String,String>();
		try{
			globalEnumService.batchDelete(info);
			ge_map.put("ResultCode", "0");
		}catch(Exception e){
			ge_map.put("ResultCode", "1");
		}
		return ge_map;
	}
	
	/**
	 * 功能: 查询枚举
	 * 作者 曾强 2013-5-17上午11:03:09
	 * @return
	 */
	@RequestMapping("all")
	@ResponseBody
	public Map<String,Object> findAllEnumInfo(GlobalEnum ge){
		List<GlobalEnum> ge_list = globalEnumService.findByExample(ge);
		Map<String,Object> ge_map = new HashMap<String,Object>();
		ge_map.put("total", (ge_list!=null?ge_list.size():0));
		ge_map.put("rows", (ge_list!=null?ge_list:new ArrayList<GlobalEnum>()));
		return ge_map;
	}
}
