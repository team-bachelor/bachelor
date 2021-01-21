package cn.org.bachelor.common.auth.annotation;

import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AcmDomain {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String code() default "";
}
