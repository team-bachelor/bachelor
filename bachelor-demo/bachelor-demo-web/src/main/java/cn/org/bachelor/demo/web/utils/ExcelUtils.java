package cn.org.bachelor.demo.web.utils;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.org.bachelor.demo.web.common.annotation.ExcelColumn;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lfxa
 * @Description: Excel 导入导出工具类
 * @date 2020/6/24 12:51
 */
public class ExcelUtils {

    private final static Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    private final static String EXCEL2003 = "xls";
    private final static String EXCEL2007 = "xlsx";
    private final static String REGEX_XLS = "^.+\\.(?i)(xls)$";
    private final static String REGEX_XLSX = "^.+\\.(?i)(xlsx)$";

    /**
     * 读取file，并将读取到的数据映射到cls对应的实体类上，通过注解 反射
     *
     * @param path
     * @param cls
     * @param file
     * @param <T>
     * @return
     */
    public static <T> List<T> readExcel(String path, Class<T> cls, MultipartFile file) {

        String fileName = file.getOriginalFilename();
        List<T> dataList = new ArrayList<>();
        if (fileName == null) {
            return dataList;
        }
        if (!fileName.matches(REGEX_XLS) && !fileName.matches(REGEX_XLSX)) {
            log.error("上传文件格式不正确");
        }
        Workbook workbook = null;
        try {
            InputStream is = file.getInputStream();
            if (fileName.endsWith(EXCEL2007)) {
                workbook = new XSSFWorkbook(is);
            }
            if (fileName.endsWith(EXCEL2003)) {
                workbook = new HSSFWorkbook(is);
            }
            if (workbook != null) {
                Map<String, List<Field>> classMap = getClassMap(cls);

                //索引-->columns 读起excel
                Map<Integer, List<Field>> reflectionMap = new HashMap<>(16);
                //默认读取第一个sheet
                Sheet sheet = workbook.getSheetAt(0);
                boolean firstRow = true;
                for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    //首行  提取注解
                    if (firstRow) {
                        for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                            Cell cell = row.getCell(j);
                            String cellValue = getCellValue(cell);
                            if (classMap.containsKey(cellValue)) {
                                reflectionMap.put(j, classMap.get(cellValue));
                            }
                        }
                        firstRow = false;
                    } else {
                        //忽略空白行
                        if (row == null) {
                            continue;
                        }
                        try {
                            T t = cls.newInstance();
                            //判断是否为空白行
                            boolean allBlank = true;
                            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                                if (reflectionMap.containsKey(j)) {
                                    Cell cell = row.getCell(j);
                                    String cellValue = getCellValue(cell);
                                    if (StringUtils.isNotBlank(cellValue)) {
                                        allBlank = false;
                                    }
                                    List<Field> fieldList = reflectionMap.get(j);
                                    fieldList.forEach(
                                            field -> {
                                                try {
                                                    handleField(t, cellValue, field);
                                                } catch (Exception e) {
                                                    log.error(String.format("reflect field:%s value:%s exception!", field.getName(), cellValue), e);
                                                }
                                            }
                                    );
                                }
                            }
                            if (!allBlank) {
                                dataList.add(t);
                            } else {
                                log.warn(String.format("row:%s is blank ignore!", i));
                            }
                        } catch (Exception e) {
                            log.error(String.format("parseToDate row:%s exception!", i), e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(String.format("parseToDate excel exception!"), e);
        } finally {
            workbookClose(workbook);
        }
        return dataList;
    }

    /**
     * 类映射  注解 value-->bean columns<br/>
     * 将类中 有@ExcelColumn注解的并且value有值的字段存到classMap中 键=@ExcelColumn注解的value,值=List<Field><br/>
     * 获取cls类中声明的所有字段 并通过`流` 返回List<Field> 数组<br/>
     *
     * @param cls 目标类
     * @param <T> List<Field>
     * @return
     * @author lixiaolong
     * @date 2020/12/29 8:49
     */
    private static <T> Map<String, List<Field>> getClassMap(Class<T> cls) {
        //类映射  注解 value-->bean columns
        //将类中 有@ExcelColumn注解的并且value有值的字段存到classMap中 键=@ExcelColumn注解的value,值=List<Field>
        Map<String, List<Field>> classMap = new HashMap<>();
        // 获取cls类中声明的所有字段 并通过`流` 返回List<Field> 数组
        List<Field> fields = Stream.of(cls.getDeclaredFields()).collect(Collectors.toList());
        fields.forEach(
                field -> {
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    if (annotation != null) {
                        String value = annotation.value();
                        if (!StringUtils.isEmpty(value)) {
                            if (!classMap.containsKey(value)) {
                                classMap.put(value, new ArrayList<>());
                            }
                            field.setAccessible(true);
                            classMap.get(value).add(field);
                        }
                    }
                }
        );
        return classMap;
    }

    private static void workbookClose(Workbook workbook) {
        if (workbook != null) {
            try {
                workbook.close();
            } catch (Exception e) {
                log.error(String.format("parseToDate excel exception!"), e);
            }
        }
    }

    private static <T> void handleField(T t, String value, Field field) throws Exception {
        Class<?> type = field.getType();
        if (type == null || type == void.class || StringUtils.isBlank(value)) {
            return;
        }
        if (type == Object.class) {
            field.set(t, value);
            //数字类型
        } else if (type.getSuperclass() == null || type.getSuperclass() == Number.class) {
            if (type == int.class || type == Integer.class) {
                field.set(t, NumberUtils.toInt(value));
            } else if (type == long.class || type == Long.class) {
                field.set(t, NumberUtils.toLong(value));
            } else if (type == byte.class || type == Byte.class) {
                field.set(t, NumberUtils.toByte(value));
            } else if (type == short.class || type == Short.class) {
                field.set(t, NumberUtils.toShort(value));
            } else if (type == double.class || type == Double.class) {
                field.set(t, NumberUtils.toDouble(value));
            } else if (type == float.class || type == Float.class) {
                field.set(t, NumberUtils.toFloat(value));
            } else if (type == char.class || type == Character.class) {
                field.set(t, CharUtils.toChar(value));
            } else if (type == boolean.class) {
                field.set(t, BooleanUtils.toBoolean(value));
            } else if (type == BigDecimal.class) {
                field.set(t, new BigDecimal(value));
            }
        } else if (type == Boolean.class) {
            field.set(t, BooleanUtils.toBoolean(value));
        } else if (type == Date.class) {
            field.set(t, DateUtil.parse(value, DatePattern.NORM_DATETIME_PATTERN));
        } else if (type == String.class) {
            field.set(t, value);
        } else {
            Constructor<?> constructor = type.getConstructor(String.class);
            field.set(t, constructor.newInstance(value));
        }
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                return DateUtil.formatDateTime(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
            } else {
                return new BigDecimal(cell.getNumericCellValue()).toString();
            }
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return StringUtils.trimToEmpty(cell.getStringCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return StringUtils.trimToEmpty(cell.getCellFormula());
        } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return "";
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
            return "ERROR";
        } else {
            return cell.toString().trim();
        }

    }

}
