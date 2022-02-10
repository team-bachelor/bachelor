package cn.org.bachelor.iam.acm.service;


import cn.org.bachelor.iam.acm.permission.PermissionGroup;
import cn.org.bachelor.iam.acm.permission.PermissionOptions;
import cn.org.bachelor.iam.acm.permission.PermissionPoint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @描述 鉴权相关的服务
 * @创建时间 2018/10/22
 * @author liuzhuo
 */
@Service
public class AuthorizeServiceClientFallback implements AuthorizeServiceClient {

//    @Override
//    public boolean isAuthorized(String objCode, String userCode) {
//        return false;
//    }

    @Override
    public boolean isAuthorized(String objCode, String userCode, PermissionOptions.AccessType accessType) {
        return false;
    }

    @Override
    public Map<String, PermissionPoint> calUserPermission(String userCode) {
        return null;
    }

    @Override
    public List<PermissionGroup> getPermissionGroupList(String orgID) {
        return null;
    }

    @Override
    public List<String> getRolePermission(String roleCode) {
        return null;
    }

    @Override
    public void setRolePermission(String roleCode, List<PermissionPoint> perms) {

    }

    @Override
    public List<String> getOrgPermission(String orgId) {
        return null;
    }

    @Override
    public void setOrgPermission(String orgId, List<PermissionPoint> perms) {

    }
}
