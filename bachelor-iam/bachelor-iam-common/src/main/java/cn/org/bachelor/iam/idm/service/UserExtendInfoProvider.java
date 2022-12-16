package cn.org.bachelor.iam.idm.service;

import java.util.Map;

public interface UserExtendInfoProvider {
    Map<String, ? extends Object> invoke(Map<String, ? extends Object> userInfo);
}
