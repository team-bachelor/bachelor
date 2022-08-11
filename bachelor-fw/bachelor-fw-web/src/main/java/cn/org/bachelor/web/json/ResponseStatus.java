package cn.org.bachelor.web.json;

import io.swagger.annotations.ApiModelProperty;

public enum ResponseStatus {
    /**
     *
     */
    @ApiModelProperty("成功")
    OK,
    /**
     *
     */
    @ApiModelProperty("部分成功")
    DEFECT,
    /**
     *
     */
    @ApiModelProperty("业务异常")
    BIZ_ERR,
    /**
     *
     */
    @ApiModelProperty("系统异常")
    SYS_ERR,
    /**
     *
     */
    @ApiModelProperty("未知异常")
    ERR
}
