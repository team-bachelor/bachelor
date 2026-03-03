package cn.org.bachelor.log.operate.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="cmn_operate_log")
@Schema(name = "CmnOperateLog", description = "数据操作日志")
public class OperateLog implements Serializable {
    @Id
    private String id;
    @Schema(name = "操作人账号")
    private String opAccount;

    @Schema(name = "操作人账号名")
    private String opAccountName;

    @Schema(name = "操作人所属机构")
    private String opOrgId;

    @Schema(name = "操作人所属机构名")
    private String opOrgName;

    @Schema(name = "操作时间")
    private Date opTime;

    @Schema(name ="操作IP")
    private String opIp;

    @Schema(name = "操作库名称")
    private String dataBase;
    @Schema(name = "操作定语")
    private String attribute;
    @Schema(name = "操作动作")
    private String predicate;
    @Schema(name = "操作主题")
    private String subject;
    @Schema(name = "操作结果")
    private String result;
    @Schema(name = "操作对象标识")
    private String identify;
    @Schema(name = "操作内容")
    private String detail;
    @Transient
    @Schema(name = "操作开始时间")
    private Date operatorTimeStart;
    @Transient
    @Schema(name = "操作结束时间")
    private Date operatorTimeEnd;
    // 流水号
    @Schema(name = "操作对象id")
    private String seriesNumber;
}