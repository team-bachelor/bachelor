package cn.org.bachelor.iam.idm.service;

import java.util.Map;

public interface UserExtendInfoProvider {
    Map<? extends String, ? extends Object> invoke(Map<? extends String, ? extends Object> userBaseInfo);
}
