package org.bachelor.cache.redis.conversion;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by liuzhuo on 2015/11/11.
 */
public class StringToDateEditor extends PropertyEditorSupport {
    public void setAsText(String text) throws IllegalArgumentException {
        if(!StringUtils.isEmpty(text) && text != null){
            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            try {
                setValue(sdf.parse(text));
            } catch (ParseException e) {
                e.printStackTrace();
            }
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
