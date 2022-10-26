package cn.org.bachelor.iam.acm.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AcmDomain {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String code() default "";
}
