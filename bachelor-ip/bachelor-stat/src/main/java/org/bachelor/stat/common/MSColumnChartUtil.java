package org.bachelor.stat.common;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bachelor.stat.vo.MSColumnVo;

@SuppressWarnings("rawtypes")
public class MSColumnChartUtil {
	
	public static Map<String,Object> builderChartInfo(List list,MSColumnVo cv){
		Map<String,Object> chart_map = new HashMap<String,Object>();
		chart_map.put("chart", cv);
		chart_map.put("data", list);
		return chart_map;
	}
	//json数据格式
	public static String builderJsonString(List<Map> list,MSColumnVo cv){
		StringBuilder json = new StringBuilder();
		json.append(builder2DHeader(cv));
		json.append(builder2DData(list));
		return json.toString();
	}
	//二D柱形图表数据(单柱)
	public static String builder2DData(List<Map> list){
		StringBuilder json = new StringBuilder();
		json.append("\"data\":[");
		int index = 0;
		for(Map map:list){
			json.append("{ \"label\":\""+map.get("label")+"\",  \"value\":\""+map.get("value")+"\"  }");
			if(index<(list.size()-1)){
				json.append(",");
			}
			index++;
		}
		json.append("]");
		json.append("}");
		return json.toString();
	}
	//二D柱形图表的表头
	public static String builder2DHeader(MSColumnVo cv){
		StringBuilder header = new StringBuilder();
		header.append("{");
		header.append("\"chart\":{");
		header.append("\"caption\":").append("\"").append(cv.getCaption()).append("\",");
		header.append("\"numbersuffix\":").append("\"").append(cv.getNumbersuffix()).append("\",");
		header.append("\"canvasbgcolor\":").append("\"").append(cv.getCanvasbgcolor()).append("\",");;
		header.append("\"canvasbasecolor\":").append("\"").append(cv.getCanvasbasecolor()).append("\",");;
		header.append("\"tooltipbgcolor\":").append("\"").append(cv.getTooltipbgcolor()).append("\",");;
		header.append("\"tooltipborder\":").append("\"").append(cv.getTooltipborder()).append("\",");;
		header.append("\"divlinecolor\":").append("\"").append(cv.getDivlinecolor()).append("\"");;
		header.append("},");
		return header.toString();
	}
}
