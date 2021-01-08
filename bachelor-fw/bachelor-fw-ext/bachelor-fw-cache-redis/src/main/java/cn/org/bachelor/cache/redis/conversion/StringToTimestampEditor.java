package cn.org.bachelor.cache.redis.conversion;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by liuzhuo on 2015/11/11.
 */
public class StringToTimestampEditor extends PropertyEditorSupport {
    public void setAsText(String text) throws java.lang.IllegalArgumentException {
        if(!StringUtils.isEmpty(text) && text != null){
            long time = Long.valueOf(text);

            setValue(new Timestamp(time));
        }
        return;
    }

    public String getAsText() {
        if(this.getValue() == null) return null;
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            //方法一
            tsStr = sdf.format(getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

}
