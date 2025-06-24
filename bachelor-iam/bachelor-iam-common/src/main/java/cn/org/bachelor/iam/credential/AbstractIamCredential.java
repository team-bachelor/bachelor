// 定义包名，表明该类所属的包
package cn.org.bachelor.iam.credential;

// 导入 Lombok 库中的 AllArgsConstructor 注解，用于自动生成全参数构造函数
import lombok.AllArgsConstructor;
// 导入 Lombok 库中的 Data 注解，用于自动生成 getter、setter、toString、equals 和 hashCode 等方法
import lombok.Data;

// 导入 java.util 包中的 Date 类，用于表示日期和时间
import java.util.Date;

/**
 * 抽象类 AbstractIamCredential，用于表示 IAM（身份与访问管理）凭证的抽象概念。
 * 该类使用泛型 T 来表示凭证的具体类型，提供了基本的凭证信息和操作。
 *
 * @param <T> 凭证的具体类型
 */
@Data
@AllArgsConstructor
public abstract class AbstractIamCredential<T> {

    /**
     * 无参构造函数，初始化凭证的创建时间为当前时间。
     */
    public AbstractIamCredential() {
        // 创建一个新的 Date 对象，表示当前时间，并将其赋值给 createTime 字段
        this.createTime = new Date();
    }

    /**
     * 凭证的主体，通常是一个唯一标识，如用户 ID 或服务名称。
     */
    private String subject;

    /**
     * 具体的凭证对象，类型由泛型 T 决定。
     */
    private T credential;

    /**
     * 凭证的创建时间。
     */
    private Date createTime;

    /**
     * 凭证的过期时间。
     */
    private Date expiresTime;

    /**
     * 凭证验证失败的次数，初始值为 0。
     */
    private Integer failCount = 0;
}