package cn.org.bachelor.iam.acm.service;

import cn.org.bachelor.iam.IamValueHolderService;
import cn.org.bachelor.iam.vo.UserVo;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2019/3/28
 */
public class MockAuthValueHolderService extends IamValueHolderService {

    private String remoteIP;
    private UserVo user;

    public void setCurrentUser(UserVo user) {
        this.user = user;
    }

    public UserVo getCurrentUser() {
        return this.user;
    }

    public String getRemoteIP() {
        return remoteIP;
    }

    public void setRemoteIP(String remoteIP) {
        this.remoteIP = remoteIP;
    }
}
