package cn.org.bachelor.iam.acm.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "cmn_acm_obj_permission")
public class ObjPermission {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 对象名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 对象编码
     */
    @Column(name = "CODE")
    private String code;

    /**
     * 对象定位
     */
    @Column(name = "URI")
    private String uri;

    /**
     * 对象操作
     */
    @Column(name = "OPERATE")
    private String operate;

    /**
     * 对象类型
     */
    @Column(name = "TYPE")
    private String type;

    /**
     * 所属域编码
     */
    @Column(name = "DOMAIN_CODE")
    private String domainCode;

    /**
     * 是否系统默认
     */
    @Column(name = "IS_SYS")
    private Boolean isSys;

    /**
     * 排序
     */
    @Column(name = "SEQ_ORDER")
    private Integer seqOrder;

    /**
     * 默认权限行为
     */
    @Column(name = "DEF_AUTH_OP")
    private String defAuthOp;

    /**
     * 说明
     */
    @Column(name = "COMMENT")
    private String comment;

    /**
     * 更新时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 更新人
     */
    @Column(name = "UPDATE_USER")
    private String updateUser;

    /**
     * 获取ID
     *
     * @return ID - ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取对象名称
     *
     * @return NAME - 对象名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置对象名称
     *
     * @param name 对象名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取对象编码
     *
     * @return CODE - 对象编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置对象编码
     *
     * @param code 对象编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取对象定位
     *
     * @return URI - 对象定位
     */
    public String getUri() {
        return uri;
    }

    /**
     * 设置对象定位
     *
     * @param uri 对象定位
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * 获取对象操作
     *
     * @return OPERATE - 对象操作
     */
    public String getOperate() {
        return operate;
    }

    /**
     * 设置对象操作
     *
     * @param operate 对象操作
     */
    public void setOperate(String operate) {
        this.operate = operate;
    }

    /**
     * 获取对象类型
     *
     * @return TYPE - 对象类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置对象类型
     *
     * @param type 对象类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取所属域编码
     *
     * @return DOMAIN_CODE - 所属域编码
     */
    public String getDomainCode() {
        return domainCode;
    }

    /**
     * 设置所属域编码
     *
     * @param domainCode 所属域编码
     */
    public void setDomainCode(String domainCode) {
        this.domainCode = domainCode;
    }

    /**
     * 获取排序
     *
     * @return ORDER - 排序
     */
    public Integer getSeqOrder() {
        return seqOrder;
    }

    /**
     * 设置排序
     *
     * @param seqOrder 排序
     */
    public void setSeqOrder(Integer seqOrder) {
        this.seqOrder = seqOrder;
    }

    /**
     * 获取默认权限行为
     *
     * @return DEF_AUTH_OP - 默认权限行为
     */
    public String getDefAuthOp() {
        return defAuthOp;
    }

    /**
     * 设置默认权限行为
     *
     * @param defAuthOp 默认权限行为
     */
    public void setDefAuthOp(String defAuthOp) {
        this.defAuthOp = defAuthOp;
    }

    /**
     * 获取更新时间
     *
     * @return UPDATE_TIME - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取更新人
     *
     * @return UPDATE_USER - 更新人
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 设置更新人
     *
     * @param updateUser 更新人
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isSys() {
        return isSys;
    }

    public void setIsSys(Boolean isSys) {
        isSys = isSys;
    }
}