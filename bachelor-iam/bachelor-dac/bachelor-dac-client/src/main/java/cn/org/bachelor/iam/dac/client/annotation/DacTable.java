package cn.org.bachelor.iam.dac.client.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 为实体设置的注解，将需要进行权限拦截的实体和对应的表名设置到这里面
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface DacTable {

    /**
     * 设置需要进行拦截的表名
     * @return
     */
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";
}
