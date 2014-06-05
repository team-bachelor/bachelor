package org.bachelor.web.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.bachelor.auth.domain.AuthFunction;
import org.bachelor.auth.domain.Role;
import org.bachelor.console.common.Constant;
import org.bachelor.ps.domain.Function;

/**
 * 解析Excel文件
 * @author user
 *
 */
public class ParseExcel {
	
	/**
	 * 得到ParseExcel对象
	 * @return
	 */
	public static ParseExcel getParseExcel(){
		
		return new ParseExcel();
	}
	
	private Log log = LogFactory.getLog(this.getClass());
	
	/** 
	 * 得到功能权限集合
	 * @return
	 */
	public List<AuthFunction> getAuthFunctionObjectList(Iterator<?> its){
		List<AuthFunction> afs = new ArrayList<AuthFunction>();
		for(Iterator<?> it = its;it.hasNext();){
			/** 获取工作薄对象 **/
			XSSFSheet sheet = (XSSFSheet)it.next();
			/** 获取行对象 **/
			XSSFRow row ;
			/** 声明单元格 **/
			String cell;
			/** 角色对象 **/
			Role role;
			/** 功能对象 **/
			Function func;
			/** 功能角色对象 **/
			AuthFunction af;
			/** 角色数组 **/
			String roleIds[] = sheet.getRow(1).getCell(0).toString().split(",");
			/** 角色ID值 **/
			for(String roleId:roleIds){
				/** 表头前两行不写入到功能权限对象中去 **/
				for(int i = (sheet.getFirstRowNum() + Constant.EXCEL_HEADER_START_NUMBER);i < sheet.getPhysicalNumberOfRows();i++){
					row = sheet.getRow(i);  
					if(row!=null && row.getCell(Constant.EXCEL_CELL_START_NUMBER)!=null){
						cell = row.getCell(Constant.EXCEL_CELL_START_NUMBER).toString();   
						/** 功能角色对象 **/
						 af = new AuthFunction();
						 role = new Role();
						 func = new Function();
						 role.setId(roleId);
						 func.setId(cell);
						 af.setRole(role);
						 af.setFunc(func);
						 afs.add(af);
					}
				 }
			}
		}
		return afs; 
	}
	
	/**
	 * 得到所有工作薄数据迭代
	 * @return
	 */
	public Iterator<?> getAllSheetDatas(InputStream input){
		Iterator<?> its = null;
		try{
			XSSFWorkbook xwb = new XSSFWorkbook(input);
			its = xwb.iterator();
		}catch(Exception e){
			e.printStackTrace();
			log.info("加载EXCEL错误.",e);
		}
		return its;
	}
	
	/** 
	 * 得到实体集合
	 * @param input
	 * @return
	 */
	public List<?> getEnityLists(InputStream input,String enityType){
		/** 导入功能权限数据流 **/
		if(enityType.equals(Constant.ENITY_TYPE_AUTHFUNCTION)){
			
			return getAuthFunctionObjectList(getAllSheetDatas(input));
		}
		return null;
	}
	
	
}
