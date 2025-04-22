package cn.org.bachelor.web.exception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注解用于标记异常处理相关的类，被标记的类可能会被用于特定的异常处理逻辑。
 *
 * @author liuzhuo
 * @since 2019/4/1  // 将“@创建时间:” 修正为符合 Javadoc 规范的 “@since”
 */
@Target({ElementType.TYPE})  // 此注解只能应用于类、接口（包括注解类型）或枚举声明
@Retention(RetentionPolicy.RUNTIME)  // 此注解在运行时可见
public @interface ExceptionHandle {
    // 注解定义，暂时为空，可根据实际需求添加注解属性
}