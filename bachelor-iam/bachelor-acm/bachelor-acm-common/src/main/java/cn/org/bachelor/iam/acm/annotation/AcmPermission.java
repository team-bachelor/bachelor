package cn.org.bachelor.iam.acm.annotation;

import cn.org.bachelor.iam.acm.permission.PermissionOptions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AcmPermission {

    String value() default "";

    /**
     * 自定义权限点操作名称
     */
    String opType() default "";

    /**
     * 自定义权限点操作编码（可自动生成）
     */
    String opTypeCode() default "";

    /**
     * 接口类型，默认一般不需要改
     */
    PermissionOptions.AccessType type() default PermissionOptions.AccessType.INTERFACE;

    /**
     * 服务对象描述
     */
    String[] serveFor() default {};

    /**
     * 排序
     */
    int order() default 0;

    /**
     * 校验级别
     */
    PermissionOptions.CheckLevel checkLevel() default PermissionOptions.CheckLevel.Authorized;
}
