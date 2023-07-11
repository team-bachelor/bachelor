package cn.org.bachelor.iam.acm.vo;

import cn.org.bachelor.iam.acm.permission.PermissionModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: MenuVo for IceStark
 * @创建人: liuzhuo
 * @创建时间: 2018/11/9
 */
@Data
@AllArgsConstructor
public class ISMenuVo extends MenuVo{
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
    private List<ISMenuVo> children;

    public ISMenuVo() {
    }

}
