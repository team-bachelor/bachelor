package cn.org.bachelor.iam.dac.client;

import cn.org.bachelor.exception.SystemException;

public class InvalidDacConfigException extends SystemException {
    public InvalidDacConfigException(String msg) {
        super(msg);
    }
}
