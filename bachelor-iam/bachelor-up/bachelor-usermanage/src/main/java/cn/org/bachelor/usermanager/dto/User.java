package cn.org.bachelor.usermanager.dto;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.sql.Timestamp;


public class User {
  private String id;//id
  private String username;//用户名
  private String sex;//
  private String deptId;
  private String bizDeptId;
  private String positionId;
  private String gwmcId;
  private String gwlxId;
  private String gwsxId;
  private String rankId;
  private Timestamp createtime;
  private String orgId;
  private String usertypeId;
  private String code;
  private String usedname;
  private String idcard;
  private String telphone;
  private String email;
  private String phoneHome;
  private String phoneOffice;
  private String comment1;
  private String educationId;
  private String degreeId;
  private String rank2Id;
  private String rank3Id;
  private String reportercard;
  private Timestamp reportercardBegintime;
  private Timestamp reportercardEndtime;
  private String reportercardBegintimeStr;
  private String reportercardEndtimeStr;
  private String comment3;
  private String secretaryFlag;
  private String leaderId;
  // 该用户的排序
  private int seq;

  public int getSeq() {
    return seq;
  }

  public void setSeq(int seq) {
    this.seq = seq;
  }

  private int source; //1.管理员建立2.账号导入3.用户注册
  private int ad; //是否ad同步  1：是  0：否

  public int getSource() {
    return source;
  }

  public void setSource(int source) {
    this.source = source;
  }

  public int getAd() {
    return ad;
  }

  public void setAd(int ad) {
    this.ad = ad;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public String getDeptId() {
    return deptId;
  }

  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }

  public String getPositionId() {
    return positionId;
  }

  public void setPositionId(String positionId) {
    this.positionId = positionId;
  }

  public String getRankId() {
    return rankId;
  }

  public void setRankId(String rankId) {
    this.rankId = rankId;
  }

  public Timestamp getCreatetime() {
    return createtime;
  }

  public void setCreatetime(Timestamp createtime) {
    this.createtime = createtime;
  }

  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  public String getUsertypeId() {
    return usertypeId;
  }

  public void setUsertypeId(String usertypeId) {
    this.usertypeId = usertypeId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
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

  public String getDegreeId() {
    return degreeId;
  }

  public void setDegreeId(String degreeId) {
    this.degreeId = degreeId;
  }

  public String getRank2Id() {
    return rank2Id;
  }

  public void setRank2Id(String rank2Id) {
    this.rank2Id = rank2Id;
  }

  public String getRank3Id() {
    return rank3Id;
  }

  public void setRank3Id(String rank3Id) {
    this.rank3Id = rank3Id;
  }

  public String getReportercard() {
    return reportercard;
  }

  public void setReportercard(String reportercard) {
    this.reportercard = reportercard;
  }

  public Timestamp getReportercardBegintime() {
    return reportercardBegintime;
  }

  public void setReportercardBegintime(Timestamp reportercardBegintime) {
    this.reportercardBegintime = reportercardBegintime;
  }

  public Timestamp getReportercardEndtime() {
    return reportercardEndtime;
  }

  public void setReportercardEndtime(Timestamp reportercardEndtime) {
    this.reportercardEndtime = reportercardEndtime;
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

  public String getReportercardBegintimeStr() {
    return reportercardBegintimeStr;
  }

  public void setReportercardBegintimeStr(String reportercardBegintimeStr) {
    this.reportercardBegintimeStr = reportercardBegintimeStr;
  }

  public String getReportercardEndtimeStr() {
    return reportercardEndtimeStr;
  }

  public void setReportercardEndtimeStr(String reportercardEndtimeStr) {
    this.reportercardEndtimeStr = reportercardEndtimeStr;
  }

  public String getGwmcId() {
    return gwmcId;
  }

  public void setGwmcId(String gwmcId) {
    this.gwmcId = gwmcId;
  }

  public String getGwsxId() {
    return gwsxId;
  }

  public void setGwsxId(String gwsxId) {
    this.gwsxId = gwsxId;
  }

  public String getGwlxId() {
    return gwlxId;
  }

  public void setGwlxId(String gwlxId) {
    this.gwlxId = gwlxId;
  }

  public String getBizDeptId() {
    return bizDeptId;
  }

  public void setBizDeptId(String bizDeptId) {
    this.bizDeptId = bizDeptId;
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
