package cn.org.bachelor.exception;

/**
 * 远程（非本地）异常。
 * 该异常类继承自 RuntimeException，用于表示在远程操作中出现的异常情况。
 * Created by guojj on 2016/5/14.
 */
public class RemoteException extends RuntimeException {

    /**
     * 无参构造函数。
     * 调用父类 RuntimeException 的无参构造函数，创建一个没有详细描述信息的远程异常对象。
     */
    public RemoteException() {
        super();
    }

    /**
     * 带消息的构造函数。
     * 调用父类 RuntimeException 的带消息构造函数，创建一个包含指定详细描述信息的远程异常对象。
     *
     * @param msg 异常的详细描述信息
     */
    public RemoteException(String msg) {
        super(msg);
    }

    /**
     * 带消息和异常原因的构造函数。
     * 调用父类 RuntimeException 的带消息和异常原因构造函数，创建一个包含指定详细描述信息和异常原因的远程异常对象。
     *
     * @param msg 异常的详细描述信息
     * @param ex 导致该异常的原始异常
     */
    public RemoteException(String msg, Throwable ex) {
        super(msg, ex);
    }

}