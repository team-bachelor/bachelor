package cn.org.bachelor.acm.da.util;

import cn.org.bachelor.exception.SystemException;
import org.apache.ibatis.reflection.MetaObject;

import java.lang.reflect.Method;

public class MetaObjectUtil {
    public static Method method;

    public MetaObjectUtil() {
    }

    public static MetaObject forObject(Object object) {
        try {
            return (MetaObject) method.invoke((Object) null, object);
        } catch (Exception var2) {
            throw new SystemException(var2);
        }
    }

    static {
        try {
            Class<?> metaClass = Class.forName("org.apache.ibatis.reflection.SystemMetaObject");
            method = metaClass.getDeclaredMethod("forObject", Object.class);
        } catch (Exception var4) {
            try {
                Class<?> metaClass = Class.forName("org.apache.ibatis.reflection.MetaObject");
                method = metaClass.getDeclaredMethod("forObject", Object.class);
            } catch (Exception var3) {
                throw new SystemException(var3);
            }
        }


    }
}
