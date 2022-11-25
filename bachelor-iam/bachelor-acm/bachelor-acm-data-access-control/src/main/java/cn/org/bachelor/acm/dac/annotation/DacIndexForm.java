package cn.org.bachelor.acm.dac.annotation;

import java.lang.annotation.*;

/**
 * 在多表联合查询或有子查询时，给指定名字的表施加数据权限控制
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface DacIndexForm {
    String name() default "";
}
