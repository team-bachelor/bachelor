package org.bachelor.web.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import org.bachelor.auth.domain.AuthRoleUser;
import org.bachelor.auth.domain.Role;
import org.bachelor.console.common.Constant;
import org.bachelor.org.domain.User;

/** 
 * 解析大数据量的EXCEL
 * @author user
 *
 */
public class ParseBigDatasExcel extends DefaultHandler {

	private SharedStringsTable sst;  
    private String lastContents;  
    private boolean nextIsString;  
  
    private int sheetIndex = -1;  
    private List<String> rowlist = new ArrayList<String>();  
    private int curRow = 0;  
    private int curCol = 0;  
    
    private List<AuthRoleUser> lists;
      
    /** 
     * 读取第一个工作簿的入口方法 
     * @param path 
     */  
    public List<?> readOneSheet(InputStream input,String excelType) throws Exception {  
        OPCPackage pkg = OPCPackage.open(input);    
        XSSFReader r = new XSSFReader(pkg);  
        SharedStringsTable sst = r.getSharedStringsTable();  
        
        if(excelType.equals(Constant.ENITY_TYPE_ROLEPERSIONAL)){
        	
        	lists = new ArrayList<AuthRoleUser>();
        }
        
        XMLReader parser = fetchSheetParser(sst);  
              
        InputStream sheet = r.getSheet("rId1");  
  
        InputSource sheetSource = new InputSource(sheet);  
        parser.parse(sheetSource);  
        
        
        if(excelType.equals(Constant.ENITY_TYPE_ROLEPERSIONAL)){

        	return lists;
        }
        sheet.close();        
        return lists;
    }  
      
      
    /** 
     * 读取所有工作簿的入口方法 
     * @param path 
     * @throws Exception 
     */  
    public void process(String path) throws Exception {  
        OPCPackage pkg = OPCPackage.open(path);  
        XSSFReader r = new XSSFReader(pkg);  
        SharedStringsTable sst = r.getSharedStringsTable();  
  
        XMLReader parser = fetchSheetParser(sst);  
  
        Iterator<InputStream> sheets = r.getSheetsData();  
        while (sheets.hasNext()) {  
            curRow = 0;  
            sheetIndex++;  
            InputStream sheet = sheets.next();  
            InputSource sheetSource = new InputSource(sheet);  
            parser.parse(sheetSource);  
            sheet.close();  
        }  
    }  
    
    private int index = 0;
    private String roles[] = new String[3]; 
    private Role role;
    private User user;
    private AuthRoleUser aru;
    
    /** 
     * 该方法自动被调用，每读一行调用一次，在方法中写自己的业务逻辑即可 
     * @param sheetIndex 工作簿序号 
     * @param curRow 处理到第几行 
     * @param rowList 当前数据行的数据集合 
     */  
    public void optRow(int sheetIndex, int curRow, List<String> rowList) {  
        String rowInfo = "";  
        index = 0;
        for(String str : rowList) {  
        	
        	rowInfo += str + "@";
        	index ++;
        }  
        if(index==2){
        	
        	roles = rowInfo.split("@");
        }
        if(index<2){
        	
        	getRolePersonals(rowInfo);
        }
    }  
    
    /**
     * 得到角色人员对象
     * @param rowInfo
     */
    public void getRolePersonals(String rowInfo){
    	String rowInfos[] = rowInfo.split("@");
    	aru = new AuthRoleUser();
    	role = new Role();
    	user = new User();
    	role.setId(roles[0]);
    	user.setId(rowInfos[0]);
    	aru.setRole(role);
    	aru.setUser(user);
    	lists.add(aru);
    }
      
    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {  
        XMLReader parser = XMLReaderFactory  
                .createXMLReader("com.sun.org.apache.xerces.internal.parsers.SAXParser");  
        this.sst = sst;  
        parser.setContentHandler(this);  
        return parser;  
    }  
      
    public void startElement(String uri, String localName, String name,  
            Attributes attributes) throws SAXException {  
        // c => 单元格  
        if (name.equals("c")) {  
            // 如果下一个元素是 SST 的索引，则将nextIsString标记为true  
            String cellType = attributes.getValue("t");  
            if (cellType != null && cellType.equals("s")) {  
                nextIsString = true;  
            } else {  
                nextIsString = false;  
            }  
        }  
        // 置空  
        lastContents = "";  
    }  
      
      
    public void endElement(String uri, String localName, String name)  
            throws SAXException {  
        // 根据SST的索引值的到单元格的真正要存储的字符串  
        // 这时characters()方法可能会被调用多次  
        if (nextIsString) {  
            try {  
                int idx = Integer.parseInt(lastContents);  
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx))  
                        .toString();  
            } catch (Exception e) {  
  
            }  
        }  
  
        // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引  
        // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符  
        if (name.equals("v")) {  
            String value = lastContents.trim();  
            value = value.equals("") ? " " : value;  
            rowlist.add(curCol, value);  
            curCol++;  
        } else {  
            // 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法  
            if (name.equals("row") && curCol>0) {  
                optRow(sheetIndex, curRow, rowlist);  
                rowlist.clear();  
                curRow++;  
                curCol = 0;  
            }  
        }  
    }  
  
    public void characters(char[] ch, int start, int length)  
            throws SAXException {  
        // 得到单元格内容的值  
        lastContents += new String(ch, start, length);  
    }  
}
