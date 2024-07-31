package cn.org.bachelor.log.operate;

public interface OperateLogObject {
    String getIdentify();
    String getAttribute();
    default String getSerialNumber() {return String.valueOf(System.currentTimeMillis());}
}
