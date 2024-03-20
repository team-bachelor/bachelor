package cn.org.bachelor.iam.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 *
 * @author liuzhuo
 */
public class DeptDetailVo {


    @ApiModelProperty("id")
    @JsonProperty("id")
    private String id;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("名称")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty("机构id")
    private String orgId;

    @ApiModelProperty("父部门ID")
    private String pid;

    @ApiModelProperty("部门类型ID")
    private String deptTypeId;

    @ApiModelProperty("邮编")
    private String postcode;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("电话")
    private String telephone;

    @ApiModelProperty("传真")
    private String fax;

    @ApiModelProperty("区域ID")
    private String areaId;

    @ApiModelProperty("隶属关系")
    private String relationshipId;

    @ApiModelProperty("单位级别")
    private String levelId;

    @ApiModelProperty("主管单位名称")
    private String chargeDeptName;

    @ApiModelProperty("单位性质")
    private String propertyId;

    @ApiModelProperty("机构id与机构")
    private String economicTypeId;

    @ApiModelProperty("机构id与机构")
    private String industryId;

    @ApiModelProperty("机构id与机构")
    private String sort;

    @ApiModelProperty("更新人")
    private String updateUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("创建时间")
    private Date createTime;

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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDeptTypeId() {
        return deptTypeId;
    }

    public void setDeptTypeId(String deptTypeId) {
        this.deptTypeId = deptTypeId;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(String relationshipId) {
        this.relationshipId = relationshipId;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getChargeDeptName() {
        return chargeDeptName;
    }

    public void setChargeDeptName(String chargeDeptName) {
        this.chargeDeptName = chargeDeptName;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getEconomicTypeId() {
        return economicTypeId;
    }

    public void setEconomicTypeId(String economicTypeId) {
        this.economicTypeId = economicTypeId;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
