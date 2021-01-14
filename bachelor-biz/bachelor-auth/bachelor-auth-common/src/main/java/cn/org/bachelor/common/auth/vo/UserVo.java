package cn.org.bachelor.common.auth.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/11/1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVo {
    /**
     * 用户ID
     */
    @JsonProperty("id")
    private String id;
    /**
     * 用户名
     */
    @JsonProperty("username")
    private String name;

    /**
     * 用户编码
     */
    @JsonProperty("account")
    private String code;

    /**
     * 用户所在组织
     */
    @JsonProperty("orgId")
    private String orgId;

    @JsonProperty("orgCode")
    private String orgCode;

    @JsonProperty("orgName")
    private String orgName;

    @JsonProperty("usernameCN")
    private String username;

    @JsonProperty("sex")
    private String sex;

    @JsonProperty("sexName")
    //20190328 lz 新增↓
    private String sexName;

    @JsonProperty("usertypeId")
    private String usertypeId;
    //20190328 lz 新增↓
    @JsonProperty("usertype")
    private String usertype;

    @JsonProperty("usedname")
    private String usedname;

    @JsonProperty("idcard")
    private String idcard;

    @JsonProperty("telphone")
    private String telphone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phoneHome")
    private String phoneHome;

    @JsonProperty("phoneOffice")
    private String phoneOffice;

    @JsonProperty("deptId")
    private String deptId;
    /**
     * 用户所在部门
     */
    @JsonProperty("deptName")
    private String deptName;

    @JsonProperty("deptPath")
    private String deptPath;
    /**
     * 用户系统对应的用户token
     */
    private String accessToken;
    private String comment1;
    private String educationId;
    //20190328 lz 新增↓
    private String education;
    private String degreeId;
    //20190328 lz 新增↓
    private String degree;
    private String rankId;
    //20190328 lz 新增↓
    private String rank;
    private String rank2Id;
    //20190328 lz 新增↓
    private String rank2;
    private String rank3Id;
    //20190328 lz 新增↓
    private String rank3;
    private String comment3;
    private String secretaryFlag;
    private String leaderId;
    private String positionId;
    //20190328 lz 新增↓
    private String position;
    private String createtime;

    //20161027新增orgId
    private String qyUserid;
    private String political;
    private String age;
    //base64编码的字符串，需要自己反解。
    private String picture;
    /**
     * 是否是超级管理员
     */
    private boolean isAdministrator = false;

    @JsonProperty(value = "access_backend", access = JsonProperty.Access.WRITE_ONLY)
    private boolean accessBackend = true;

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptPath() {
        return deptPath;
    }

    public void setDeptPath(String deptPath) {
        this.deptPath = deptPath;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUsertypeId() {
        return usertypeId;
    }

    public void setUsertypeId(String usertypeId) {
        this.usertypeId = usertypeId;
    }

    public String getUsedname() {
        return usedname;
    }

    public void setUsedname(String usedname) {
        this.usedname = usedname;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneHome() {
        return phoneHome;
    }

    public void setPhoneHome(String phoneHome) {
        this.phoneHome = phoneHome;
    }

    public String getPhoneOffice() {
        return phoneOffice;
    }

    public void setPhoneOffice(String phoneOffice) {
        this.phoneOffice = phoneOffice;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getComment1() {
        return comment1;
    }

    public void setComment1(String comment1) {
        this.comment1 = comment1;
    }

    public String getEducationId() {
        return educationId;
    }

    public void setEducationId(String educationId) {
        this.educationId = educationId;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(String degreeId) {
        this.degreeId = degreeId;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getRankId() {
        return rankId;
    }

    public void setRankId(String rankId) {
        this.rankId = rankId;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRank2Id() {
        return rank2Id;
    }

    public void setRank2Id(String rank2Id) {
        this.rank2Id = rank2Id;
    }

    public String getRank2() {
        return rank2;
    }

    public void setRank2(String rank2) {
        this.rank2 = rank2;
    }

    public String getRank3Id() {
        return rank3Id;
    }

    public void setRank3Id(String rank3Id) {
        this.rank3Id = rank3Id;
    }

    public String getRank3() {
        return rank3;
    }

    public void setRank3(String rank3) {
        this.rank3 = rank3;
    }

    public String getComment3() {
        return comment3;
    }

    public void setComment3(String comment3) {
        this.comment3 = comment3;
    }

    public String getSecretaryFlag() {
        return secretaryFlag;
    }

    public void setSecretaryFlag(String secretaryFlag) {
        this.secretaryFlag = secretaryFlag;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getQyUserid() {
        return qyUserid;
    }

    public void setQyUserid(String qyUserid) {
        this.qyUserid = qyUserid;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isAccessBackend() {
        return accessBackend;
    }

    public void setAccessBackend(boolean accessBackend) {
        this.accessBackend = accessBackend;
    }
}
