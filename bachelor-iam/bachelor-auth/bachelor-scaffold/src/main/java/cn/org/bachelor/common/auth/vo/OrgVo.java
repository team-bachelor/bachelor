package cn.org.bachelor.common.auth.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/11/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgVo {

    @JsonProperty("id")
    private String id;

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    @JsonProperty("pid")
    private String parentId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private OrgVo parent;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean hold;

    @JsonProperty("depttypeId")
    private String depttypeId;

    @JsonProperty("subs")
    private List<OrgVo> subOrgs;


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

    public List<OrgVo> getSubOrgs() {
        return subOrgs;
    }

    public void setSubOrgs(List<OrgVo> subOrgs) {
        this.subOrgs = subOrgs;
    }

    public String getDepttypeId() {
        return depttypeId;
    }

    public void setDepttypeId(String depttypeId) {
        this.depttypeId = depttypeId;
    }

    public OrgVo getParent() {
        return parent;
    }

    public void setParent(OrgVo parent) {
        this.parent = parent;
    }

    public boolean isHold() {
        return hold;
    }

    public void setHold(boolean hold) {
        this.hold = hold;
    }
}
