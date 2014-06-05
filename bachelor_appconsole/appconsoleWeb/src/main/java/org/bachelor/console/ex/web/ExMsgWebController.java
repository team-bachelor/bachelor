package org.bachelor.console.ex.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.dao.util.OpermaskPageUtil;
import org.bachelor.ex.domain.ExMsg;
import org.bachelor.ex.service.IExMsgService;

/**
 * 
 *  <code>ExMsgWebController.java</code>
 *  <p>功能: 异常视图类
 *  
 *  <p>Copyright 华商 2013 All right reserved.
 *  @author 曾强 zengqiang_1989@126.com
 *  @version 1.0	
 *  <p>时间 2013-4-23 下午06:42:22
 *  <p>最后修改曾 强
 */
@Controller
@RequestMapping("/ex/")
public class ExMsgWebController {
	
	@Autowired
	private IExMsgService exMsgService;
	/**
	 * 功能: 主页面
	 * 作者 曾强 2013-4-23下午06:43:53
	 * @param ex
	 * @return
	 */
	@RequestMapping("index")
	public ModelAndView module(ExMsg ex){
		
		return new ModelAndView("ex/searchExMsgInfo");
	}
	
	/**
	 * 功能: 查询异常信息
	 * 作者 曾强 2013-4-23下午07:46:51
	 * @param ex
	 * @return
	 */
	@RequestMapping("all")
	@ResponseBody
	public Map<String,Object> findExMsgInfo(ExMsg ex){
		List<ExMsg> ex_list = exMsgService.findExMsgInfo(ex);
//		Map<String,Object> ex_map = new HashMap<String,Object>();
//		ex_map.put("total", (ex_list!=null?ex_list.size():0));
//		ex_map.put("rows", (ex_list!=null?ex_list:new ArrayList<ExMsg>()));
		return OpermaskPageUtil.page(ex_list);
	}
}
