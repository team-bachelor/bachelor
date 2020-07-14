package cn.org.bachelor.core.exception;

/**
 * 远程（非本地）异常。
 * Created by guojj on 2016/5/14.
 */
public class RemoteException extends RuntimeException {

    public RemoteException() {
        super();
    }

    public RemoteException(String msg) {
        super(msg);
    }

    public RemoteException(String msg, Throwable ex) {
        super(msg, ex);
    }

}
