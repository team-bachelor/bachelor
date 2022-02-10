package cn.org.bachelor.iam.acm.rpc;

import cn.org.bachelor.iam.acm.permission.PermissionGroup;
import cn.org.bachelor.iam.acm.permission.PermissionOptions;
import cn.org.bachelor.iam.acm.permission.PermissionPoint;
import cn.org.bachelor.iam.acm.service.AuthorizeServiceStub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author liuzhuo
 * @描述 用于远程调用的用户角色controller
 * @创建时间 2021/09/29
 */
@RestController
@RequestMapping("/acm/rpc/authorize")
public class RpcAuthorizeController {

    @Resource
    private AuthorizeServiceStub authorizeService;


    /**
     * 根据用户编码判断用户是否能访问当前对象
     *
     * @param objCode  对象编码
     * @param userCode 用户编码
     * @return 是否能访问
     */
    public boolean isAuthorized(String objCode, String userCode) {
        return authorizeService.isAuthorized(objCode, userCode, PermissionOptions.AccessType.INTERFACE);
    }

    public boolean isAuthorized(String objCode, String userCode, PermissionOptions.AccessType accessType) {
        return authorizeService.isAuthorized(objCode, userCode, accessType);
    }

    /**
     * 计算当前用户的权限
     *
     * @param userCode 用户编码
     * @return 用户权限
     */
    public Map<String, PermissionPoint> calUserPermission(String userCode) {
        return authorizeService.calUserPermission(userCode);
    }

    /**
     * @param orgID 组织机构ID
     * @return 权限组列表
     * @描述 取得备选权限列表（按组分开）
     * @author liuzhuo
     * @创建时间 2018/10/27 11:16
     */
    public List<PermissionGroup> getPermissionGroupList(String orgID) {
        return authorizeService.getPermissionGroupList(orgID);
    }

    /**
     * @param roleCode
     * @Description:取得备选权限列表
     * @Author: liuzhuo
     * @Date: 2018/10/27 11:16
     * @Return:
     */
    public List<String> getRolePermission(String roleCode) {
        return authorizeService.getRolePermission(roleCode);
    }

    /**
     * @param roleCode
     * @param perms    当前角色拥有的所有权限列表
     * @author liuzhuo
     */
    public void setRolePermission(String roleCode, List<PermissionPoint> perms) {
        authorizeService.setRolePermission(roleCode, perms);
    }

    /**
     * @param orgId
     * @Description:取得备选权限列表
     * @Author: liuzhuo
     * @Date: 2018/10/27 11:16
     * @Return:
     */
    public List<String> getOrgPermission(String orgId) {
        return authorizeService.getOrgPermission(orgId);
    }

    /**
     * @param orgId
     * @param perms 当前机构拥有的所有权限列表
     * @author liuzhuo
     */
    public void setOrgPermission(String orgId, List<PermissionPoint> perms) {
        authorizeService.setOrgPermission(orgId, perms);
    }

}
