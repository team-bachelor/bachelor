package cn.org.bachelor.iam.acm.vo;

import cn.org.bachelor.iam.acm.permission.PermissionModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/11/9
 */
public class MenuVo {
    private String id;
    private String code;
    private String name;
    private String uri;
    private String icon;
    @JsonIgnore
    private MenuVo parent;
    private String parentId;
    private String comment;
    private boolean has;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuVo> getSubMenus() {
        if (subMenus == null) {
            subMenus = new ArrayList<MenuVo>(5);
        }
        return subMenus;
    }

    public void setSubMenus(List<MenuVo> subMenus) {
        this.subMenus = subMenus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public MenuVo getParent() {
        return parent;
    }

    public void setParent(MenuVo parent) {
        this.parent = parent;
    }

    public boolean isHas() {
        return has;
    }

    public void setHas(boolean has) {
        this.has = has;
    }

    public PermissionModel getType() {
        return type;
    }

    public void setType(PermissionModel type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
