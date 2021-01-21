package cn.org.bachelor.common.auth.vo;

/**
 * @描述: 权限的DTO
 * @创建人: liuzhuo
 * @创建时间: 2018/10/22
 */
public class Permission {
    /**
     * ID
     */
    private String id;

    /**
     * 对象编码
     */
    private String objCode;

    /**
     * 对象定位
     */
    private String objUri;

    /**
     * 对象操作
     */
    private String objOperate;

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    private String operateName;

    /**
     * 当前实体是否拥有该权限
     */
    private boolean has;

    /**
     * 权限类型(与哪个实体关联)
     */
    private PermissionClass type;

    /**
     * 权限所有者
     */
    private String owner;

    public Permission() {
    }

    public Permission(
            String id, String permCode,
            String objCode,
            String objUri,
            String objOperate) {
        this.objCode = objCode;
        this.objUri = objUri;
        this.objOperate = objOperate;
    }

    public String getObjCode() {
        return objCode;
    }

    public void setObjCode(String objCode) {
        this.objCode = objCode;
    }

    public String getObjUri() {
        return objUri;
    }

    public void setObjUri(String objUri) {
        this.objUri = objUri;
    }

    public String getObjOperate() {
        return objOperate;
    }

    public void setObjOperate(String objOperate) {
        this.objOperate = objOperate;
    }

    public boolean isHas() {
        return has;
    }

    public void setHas(boolean has) {
        this.has = has;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PermissionClass getType() {
        return type;
    }

    public void setType(PermissionClass type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
