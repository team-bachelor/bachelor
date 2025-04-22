// 声明该枚举类所在的包路径
package cn.org.bachelor.web.json;

// 引入 Swagger 的 ApiModelProperty 注解，用于为枚举常量添加描述信息
import io.swagger.annotations.ApiModelProperty;

/**
 * ResponseStatus 枚举类定义了几种不同的响应状态，用于表示请求处理结果的不同情况。
 * 这些状态可以在 API 响应中使用，配合 Swagger 注解可以清晰地向开发者说明每种状态的含义。
 */
public enum ResponseStatus {
    /**
     * 表示请求处理成功的状态。
     * 使用 @ApiModelProperty 注解为 Swagger 文档提供描述信息，表明该状态代表成功。
     */
    @ApiModelProperty("成功")
    OK,
    /**
     * 表示请求部分成功的状态。
     * 意味着请求处理过程中部分操作完成，但可能存在一些小问题或部分数据未按预期处理。
     * 同样使用 @ApiModelProperty 注解说明该状态代表部分成功。
     */
    @ApiModelProperty("部分成功")
    DEFECT,
    /**
     * 表示业务逻辑层面出现异常的状态。
     * 通常是由于输入数据不符合业务规则、业务流程执行失败等原因导致。
     * @ApiModelProperty 注解描述该状态为业务异常。
     */
    @ApiModelProperty("业务异常")
    BIZ_ERR,
    /**
     * 表示系统层面出现异常的状态。
     * 可能是由于服务器内部错误、数据库连接失败、网络问题等系统级因素导致。
     * 通过 @ApiModelProperty 注解说明该状态为系统异常。
     */
    @ApiModelProperty("系统异常")
    SYS_ERR,
    /**
     * 表示出现未知异常的状态。
     * 当无法明确异常的具体类型或原因时，使用此状态。
     * @ApiModelProperty 注解将其描述为未知异常。
     */
    @ApiModelProperty("未知异常")
    ERR
}