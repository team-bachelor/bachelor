package cn.org.bachelor.acm.dac.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface DacEnabled {
    @AliasFor("enable")
    boolean value();
    
    @AliasFor("value")
    boolean enabled() default true;
}
