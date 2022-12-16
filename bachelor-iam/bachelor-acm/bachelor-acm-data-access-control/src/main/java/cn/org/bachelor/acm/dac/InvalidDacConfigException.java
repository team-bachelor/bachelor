package cn.org.bachelor.acm.dac;

import cn.org.bachelor.exception.SystemException;

public class InvalidDacConfigException extends SystemException {
    public InvalidDacConfigException(String msg) {
        super(msg);
    }
}
