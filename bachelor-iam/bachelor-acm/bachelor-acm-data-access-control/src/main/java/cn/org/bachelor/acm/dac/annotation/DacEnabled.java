package cn.org.bachelor.acm.dac.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface DacEnabled {
    @AliasFor("enabled")
    boolean value() default true;

    @AliasFor("value")
    boolean enabled() default true;
}
