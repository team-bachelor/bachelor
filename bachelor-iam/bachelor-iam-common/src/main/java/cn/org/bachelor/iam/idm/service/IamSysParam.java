package cn.org.bachelor.iam.idm.service;

import cn.org.bachelor.iam.utils.StringUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * IAM系统查询参数
 * @author: liuzhuo
 */
@Data
public class IamSysParam {

    /**
     * 组织机构ID
     */
    private String orgId;

    /**
     * 组织机构名称
     */
    private String orgName;

    /**
     * 组织机构编码
     */
    private String orgCode;

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 客户端编码
     */
    private String clientCode;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 查询关键字
     */
    private String keyWord;

    /**
     * 是否期望属性返回（用于机构查询）
     */
    private boolean tree = false;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 一次查询的数据条数
     */
    private Integer pageSize = 10000;

    /**
     * 查询当前页号
     */
    private Integer pageNum = 1;

    /**
     * 将 IamSysParam 对象的属性转换为 Map<String, String> 类型的参数映射。
     * 该方法会检查每个属性是否为空，如果不为空，则将其添加到参数映射中。
     *
     * @return 包含有效查询参数的 Map 对象
     */
    public Map<String, String> toParamMap() {
        // 使用菱形运算符简化泛型类型声明，创建一个空的 HashMap 用于存储参数
        Map<String, String> param = new HashMap<>();
        // 检查组织机构ID是否不为空
        if (StringUtils.isNotEmpty(getOrgId())) {
            // 如果不为空，将组织机构ID添加到参数映射中，键为 "orgId"
            param.put("orgId", getOrgId());
        }
        // 检查用户ID是否不为空
        if (StringUtils.isNotEmpty(getUserId())) {
            // 如果不为空，将用户ID添加到参数映射中，键为 "id"
            param.put("id", getUserId());
        }
        // 检查用户编码是否不为空
        if (StringUtils.isNotEmpty(getUserCode())) {
            // 如果不为空，将用户编码添加到参数映射中，键为 "code"
            param.put("code", getUserCode());
        }
        // 检查用户名称是否不为空
        if (StringUtils.isNotEmpty(getUserName())) {
            // 如果不为空，将用户名称添加到参数映射中，键为 "username"
            param.put("username", getUserName());
        }
        // 检查部门ID是否不为空
        if (StringUtils.isNotEmpty(getDeptId())) {
            // 如果不为空，将部门ID添加到参数映射中，键为 "deptId"
            param.put("deptId", getDeptId());
        }
        // 检查部门名称是否不为空
        if (StringUtils.isNotEmpty(getDeptName())) {
            // 如果不为空，将部门名称添加到参数映射中，键为 "deptName"
            param.put("deptName", getDeptName());
        }
        // 检查客户端ID是否不为空
        if (StringUtils.isNotEmpty(getClientId())) {
            // 如果不为空，将客户端ID添加到参数映射中，键为 "clientId"
            param.put("clientId", getClientId());
        }
        // 检查每页数据条数是否不为 null
        if (getPageSize() != null) {
            // 如果不为 null，将每页数据条数转换为字符串添加到参数映射中，键为 "pageSize"
            param.put("pageSize", String.valueOf(getPageSize()));
        }
        // 检查当前页码是否不为 null
        if (getPageNum() != null) {
            // 如果不为 null，将当前页码转换为字符串添加到参数映射中，键为 "currPage"
            param.put("currPage", String.valueOf(getPageNum()));
        }
        // 返回包含有效查询参数的 Map 对象
        return param;
    }
}
