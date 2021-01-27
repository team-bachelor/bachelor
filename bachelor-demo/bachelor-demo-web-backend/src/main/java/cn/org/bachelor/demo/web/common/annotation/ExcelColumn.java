package cn.org.bachelor.demo.web.common.annotation;

import java.lang.annotation.*;

/**
 * @author 李小龙
 * @Description: ${todo}
 * @date 2020/12/07 10:49
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {
    /**
     * Excel标题
     *
     * @return
     * @author Lynch
     */
    String value() default "";

    /**
     * Excel从左往右排列位置
     *
     * @return
     * @author Lynch
     */
    int col() default 0;
}
