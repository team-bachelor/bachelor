package cn.org.bachelor.acm.dac.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface DacTable {
    String value() default "";
}
