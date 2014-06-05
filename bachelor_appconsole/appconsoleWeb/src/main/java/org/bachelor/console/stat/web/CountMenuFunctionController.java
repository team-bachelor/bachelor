package org.bachelor.console.stat.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.auth.domain.StatisticController;
import org.bachelor.stat.common.MSColumnChartUtil;
import org.bachelor.stat.service.ICountMenuFunctionService;
import org.bachelor.stat.vo.CountConditionVo;
import org.bachelor.stat.vo.MSColumnVo;

@Controller
@RequestMapping("stat")
public class CountMenuFunctionController {

	@Autowired
	private ICountMenuFunctionService countMenuFunctionService = null;
	
	@RequestMapping("index")
	public ModelAndView countIndexPage(){
		
		return new ModelAndView("/stat/SystemRunEfficiency");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("systemRun")
	@ResponseBody
	public Map<String,Object> countSystemRunEfficiency(CountConditionVo cc){
		
		List list = countMenuFunctionService.countSystemRunEfficiency(cc);
		MSColumnVo cv = new MSColumnVo();
		//显示标题
		if(cc!=null){
			if(cc.getStartTime()!=null && !"".equals(cc.getStartTime()) &&  
					cc.getEndTime()!=null && !"".equals(cc.getEndTime())){
				cv.setCaption((cc.getStartTime())+ " 至 "+(cc.getEndTime())+" 菜单运行效率");
			}else {
				
				cv.setCaption("菜单运行效率");
				
				if(cc.getStartTime()!=null && !"".equals(cc.getStartTime())){
					cv.setCaption(cc.getStartTime()+" 菜单运行效率");
				}
				if(cc.getEndTime()!=null && !"".equals(cc.getEndTime())){
					cv.setCaption(cc.getEndTime()+" 菜单运行效率");
				}
			}
		} else {
			cv.setCaption("系统运行效率");
		}
		//数值单位
		cv.setNumbersuffix("毫秒");
		Map map = new HashMap();
		map.put("json",MSColumnChartUtil.builderJsonString(list, cv));
		return map;
	}
	
	@RequestMapping("systemRun1")
	@ResponseBody
	public Map<String,Object> countSystemRunEfficiency1(CountConditionVo cc){
		
		List list = countMenuFunctionService.countSystemRunEfficiency(cc);
		MSColumnVo cv = new MSColumnVo();
		//显示标题
		if(cc!=null){
			if(cc.getStartTime()!=null && !"".equals(cc.getStartTime()) &&  
					cc.getEndTime()!=null && !"".equals(cc.getEndTime())){
				cv.setCaption((cc.getStartTime())+ " 至 "+(cc.getEndTime())+" 菜单运行效率");
			}else {
				
				cv.setCaption("菜单运行效率");
				
				if(cc.getStartTime()!=null && !"".equals(cc.getStartTime())){
					cv.setCaption(cc.getStartTime()+" 菜单运行效率");
				}
				if(cc.getEndTime()!=null && !"".equals(cc.getEndTime())){
					cv.setCaption(cc.getEndTime()+" 菜单运行效率");
				}
			}
		} else {
			cv.setCaption("菜单运行效率");
		}
		//数值单位
		cv.setNumbersuffix("毫秒");
		return MSColumnChartUtil.builderChartInfo(list, cv);
	}
	
	@RequestMapping("test")
	@ResponseBody
	public Map<String,Object> testSave() throws Exception{
		String path[] = {"/datum/index.htm",
				"/system/addCSPage.htm",
				"/ex/addEMPage.htm",
				"/ex/index.htm",
				"/ex/addEMPage.htm"};
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for(int len=10;len<17;len++){
			Date date = sdf.parse("2013-06-"+len+" 12:23:11");
			for(String tempPath:path){
				System.out.println(tempPath);
				for(int i=0;i<8000;i++){
					Random and = new Random();
					double db = (and.nextDouble()*1500);
					double db1 = (double)Math.rint(db);
					StatisticController sc = new StatisticController();
					sc.setCostTime(db1);
					sc.setRequestPath(tempPath);
					sc.setType("1");
					sc.setAccessDate(date);
					sc.setId(UUID.randomUUID().toString());
					countMenuFunctionService.save(sc);
				}
			}
		}
		return new HashMap();
	}
}
