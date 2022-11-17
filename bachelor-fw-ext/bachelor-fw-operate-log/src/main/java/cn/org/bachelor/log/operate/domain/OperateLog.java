package cn.org.bachelor.log.operate.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Table(name="cmn_operate_log")
@ApiModel(value = "CmnOperateLog", description = "数据操作日志")
public class OperateLog implements Serializable {
    @Id
    private String id;
    @ApiModelProperty("操作人账号")
    private String opAccount;
    @ApiModelProperty("操作人所属机构")
    private String opOrgId;
    @ApiModelProperty("操作时间")
    private Date opTime;
    @ApiModelProperty("操作库名称")
    private String dataBase;
    @ApiModelProperty("操作定语")
    private String attribute;
    @ApiModelProperty("操作动作")
    private String predicate;
    @ApiModelProperty("操作主题")
    private String subject;
    @ApiModelProperty("操作结果")
    private String result;
    @ApiModelProperty("操作对象标识")
    private String identify;
    @ApiModelProperty("操作内容")
    private String detail;
    @Transient
    @ApiModelProperty("操作开始时间")
    private Date operatorTimeStart;
    @Transient
    @ApiModelProperty("操作结束时间")
    private Date operatorTimeEnd;
    // 流水号
    @ApiModelProperty("操作对象id")
    private String seriesNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpAccount() {
        return opAccount;
    }

    public void setOpAccount(String opAccount) {
        this.opAccount = opAccount;
    }

    public String getOpOrgId() {
        return opOrgId;
    }

    public void setOpOrgId(String opOrgId) {
        this.opOrgId = opOrgId;
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getOperatorTimeStart() {
        return operatorTimeStart;
    }

    public void setOperatorTimeStart(Date operatorTimeStart) {
        this.operatorTimeStart = operatorTimeStart;
    }

    public Date getOperatorTimeEnd() {
        return operatorTimeEnd;
    }

    public void setOperatorTimeEnd(Date operatorTimeEnd) {
        this.operatorTimeEnd = operatorTimeEnd;
    }

    public String getSeriesNumber() {
        return seriesNumber;
    }

    public void setSeriesNumber(String seriesNumber) {
        this.seriesNumber = seriesNumber;
    }
}