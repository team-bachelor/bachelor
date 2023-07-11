package cn.org.bachelor.iam.acm.vo;

import cn.org.bachelor.iam.acm.permission.PermissionModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/11/9
 */
@Data
public class MenuVo {
    private String id;
    private String code;
    private String name;
    private String uri;
    private String icon;
    private boolean hidden;
    @JsonIgnore
    private MenuVo parent;
    private String parentId;
    private int seqOrder;
    private String comment;
    private boolean has;
    private String groupName;
    /**
     * 权限类型(与哪个实体关联)
     */
    private PermissionModel type;

    /**
     * 权限所有者
     */
    private String owner;
    private List<MenuVo> subMenus;

    public MenuVo() {
    }

    public MenuVo(String id,
                  String code,
                  String uri,
                  String icon,
                  String comment,
                  PermissionModel type,
                  MenuVo parent,
                  List<MenuVo> subMenus) {
        this.id = id;
        this.code = code;
        this.uri = uri;
        this.icon = icon;
        this.comment = comment;
        this.type = type;
        this.parent = parent;
        this.subMenus = subMenus;
    }

    public List<MenuVo> getSubMenus() {
        if (subMenus == null) {
            subMenus = new ArrayList<MenuVo>(5);
        }
        return subMenus;
    }

}
