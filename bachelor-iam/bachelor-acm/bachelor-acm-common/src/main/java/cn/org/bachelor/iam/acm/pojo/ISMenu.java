package cn.org.bachelor.iam.acm.pojo;

import cn.org.bachelor.iam.acm.permission.PermissionModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 *  MenuVo for IceStark
 * @author liuzhuo
 * @创建时间: 2018/11/9
 */
@Data
@AllArgsConstructor
public class ISMenu extends Menu {
    private String activePath;//code 仅二级
    private String entry;//code
    private String title;//name
    private String component;//URI
    /**
     * 权限类型(与哪个实体关联)
     */
    private PermissionModel type;

    /**
     * 权限所有者
     */
    private String owner;
    private List<ISMenu> children;

    public ISMenu() {
    }

}
