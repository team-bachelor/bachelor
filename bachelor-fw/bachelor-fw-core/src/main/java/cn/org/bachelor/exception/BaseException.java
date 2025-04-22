/*
 * @(#)BaseException.java	Apr 18, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package cn.org.bachelor.exception;

/**
 * 开发平台基础异常类
 * 该类是所有自定义异常的基类，继承自 RuntimeException，用于封装通用的异常信息。
 *
 * @author Team Bachelor
 */
public class BaseException extends RuntimeException {

    /**
     * 序列化版本号，用于在序列化和反序列化过程中验证版本一致性。
     */
    private static final long serialVersionUID = -1345519088905785859L;

    /**
     * 无参构造函数，调用父类的无参构造函数。
     */
    public BaseException() {
        super();
    }

    /**
     * 带有消息和异常原因的构造函数。
     *
     * @param message 异常的详细描述信息
     * @param cause   导致该异常的原始异常
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 带有消息的构造函数。
     *
     * @param message 异常的详细描述信息
     */
    public BaseException(String message) {
        super(message);
    }

    /**
     * 带有异常原因的构造函数。
     *
     * @param cause 导致该异常的原始异常
     */
    public BaseException(Throwable cause) {
        super(cause);
    }
}