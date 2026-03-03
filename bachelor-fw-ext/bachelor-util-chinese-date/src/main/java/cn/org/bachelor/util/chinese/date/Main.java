package cn.org.bachelor.util.chinese.date;

import cn.org.bachelor.epms.common.utils.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.HijrahChronology;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        String fileName = "D:\\work\\myzy\\02.project\\epms\\enclosed_area\\enclosed_area_service\\bachelor-epms-whiteblack-list\\src\\main\\java\\1.json";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray array = JSONArray.parseArray(sb.toString());
        array.forEach(a -> {
            if (a instanceof JSONObject) {
                JSONObject o = ((JSONObject) a);
                System.out.println(o.getString("date") +"="+lunarCalendar(o.getString("date")));
//                o.fluentRemove("detailsLink");
//                o.fluentRemove("videoLink");
//                o.fluentRemove("sales");
//                o.fluentRemove("poolmoney");
//                o.fluentRemove("content");
//                o.fluentRemove("addmoney");
//                o.fluentRemove("addmoney2");
//                o.fluentRemove("poolmoney");
//                o.fluentRemove("prizegrades");
//                o.fluentRemove("msg");
//                o.fluentRemove("m2add");
//                o.fluentRemove("z2add");
                // 获取当前日期的阳历信息
            }
        });
//        try {
//            FileWriter writer = new FileWriter(fileName);
//            writer.write(array.toJSONString());
//            writer.close();
//            System.out.println("文件写入成功！");
//        } catch (IOException e) {
//            System.out.println("文件写入失败：" + e.getMessage());
//        }
    }
    public static String lunarCalendar(String dateString){
        String[] sArray = dateString.split("-");
        int year = Integer.valueOf(sArray[0]);
        int month = Integer.valueOf(sArray[1]);
        int day = Integer.valueOf(sArray[2]);
        // 创建阳历日期
//        Calendar solarDate = new GregorianCalendar(year, month - 1, day);
        // 转换为阴历日期
        LocalDate date = LocalDate.of(year, month, day);
        Chronology hijriChronology = HijrahChronology.INSTANCE;
        ChronoLocalDate hijriDate = hijriChronology.date(date);
        System.out.println(hijriDate);

        // 输出阴历日期
        System.out.println("Lunar date: " + hijriDate);
        return "";
    }
}
