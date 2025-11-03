package cn.org.bachelor.iam.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <p>数据权限信息</p>
 * 创建时间: 2019/6/26
 *
 * @author liuzhuo
 */
@Data
public class DataPermission {

    @Schema(name = "部门列表")
    private List<IamOrg> depts;

    @Schema(name = "机构id与机构")
    private Map<String, IamOrg> deptMap;

    @Schema(name = "机构id与用户列表")
    private Map<String, List<IamUser>> orgUserMap;

    public DataPermission(List<IamOrg> treeOrgs, Map<String, IamOrg> deptMap, Map<String, List<IamUser>> userMap) {
        this.depts = treeOrgs;
        this.deptMap = deptMap;
        this.orgUserMap = userMap;
    }
}
