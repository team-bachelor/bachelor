// 声明该类所在的包
package cn.org.bachelor.cache.redis;

/**
 * Created by 刘卓 on 2015/11/10.
 * 该类用于将 Redis 哈希数据映射到 Java 对象。
 * 它提供了一系列方法来初始化映射、设置映射类、检查属性填充情况以及执行实际的映射操作。
 */

// 引入自定义的无效映射异常类
import cn.org.bachelor.cache.redis.conversion.InvalideMapException;
// 引入自定义的字符串转日期编辑器
import cn.org.bachelor.cache.redis.conversion.StringToDateEditor;
// 引入自定义的字符串转时间戳编辑器
import cn.org.bachelor.cache.redis.conversion.StringToTimestampEditor;
// 引入 Apache Commons 日志框架
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
// 引入 Spring 框架的 Bean 相关类
import org.springframework.beans.*;
// 引入 Spring 框架的断言工具类
import org.springframework.util.Assert;
// 引入 Spring 框架的字符串工具类
import org.springframework.util.StringUtils;

// 引入 Java 反射相关的属性描述符类
import java.beans.PropertyDescriptor;
// 引入 Java SQL 时间戳类
import java.sql.Timestamp;
// 引入 Java 集合框架的相关类
import java.util.*;

/**
 * HashValueMapper 类用于将 Redis 哈希数据映射到指定类型的 Java 对象。
 * 它支持自定义映射类、属性填充检查以及默认值设置等功能。
 *
 * @param <T> 要映射到的 Java 对象类型
 */
public class HashValueMapper<T> {
    // 日志记录器，用于记录调试和错误信息
    protected final Log logger = LogFactory.getLog(this.getClass());
    // 要映射到的 Java 对象的类
    private Class<T> mappedClass;
    // 是否检查对象的所有属性是否都被填充
    private boolean checkFullyPopulated = false;
    // 是否为原始类型的属性设置默认值
    private boolean primitivesDefaultedForNullValue = false;
    // 存储属性名到属性描述符的映射
    private Map<String, PropertyDescriptor> mappedFields;
    // 存储已映射的属性名集合
    private Set<String> mappedProperties;

    /**
     * 默认构造函数，初始化一个空的 HashValueMapper 实例。
     */
    public HashValueMapper() {
    }

    /**
     * 构造函数，使用指定的映射类初始化 HashValueMapper 实例。
     *
     * @param mappedClass 要映射到的 Java 对象的类
     */
    public HashValueMapper(Class<T> mappedClass) {
        // 调用初始化方法
        this.initialize(mappedClass);
    }

    /**
     * 构造函数，使用指定的映射类和属性填充检查标志初始化 HashValueMapper 实例。
     *
     * @param mappedClass           要映射到的 Java 对象的类
     * @param checkFullyPopulated 是否检查对象的所有属性是否都被填充
     */
    public HashValueMapper(Class<T> mappedClass, boolean checkFullyPopulated) {
        // 调用初始化方法
        this.initialize(mappedClass);
        // 设置属性填充检查标志
        this.checkFullyPopulated = checkFullyPopulated;
    }

    /**
     * 设置要映射到的 Java 对象的类。
     * 如果之前没有设置过映射类，则进行初始化；如果已设置且与新的类不同，抛出异常。
     *
     * @param mappedClass 要映射到的 Java 对象的类
     */
    public void setMappedClass(Class<T> mappedClass) {
        // 如果之前没有设置过映射类
        if (this.mappedClass == null) {
            // 调用初始化方法
            this.initialize(mappedClass);
        } else if (!this.mappedClass.equals(mappedClass)) {
            // 如果已设置且与新的类不同，原注释中提到的异常抛出代码被注释掉，可根据实际情况恢复
//            throw new InvalidDataAccessApiUsageException("The mapped class can not be reassigned to map to " + mappedClass + " since it is already providing mapping for " + this.mappedClass);
        }
    }

    /**
     * 初始化映射相关的字段，包括映射类、映射字段和映射属性。
     *
     * @param mappedClass 要映射到的 Java 对象的类
     */
    protected void initialize(Class<T> mappedClass) {
        // 设置映射类
        this.mappedClass = mappedClass;
        // 初始化映射字段，原代码使用原始类型的 HashMap，应指定泛型
        this.mappedFields = new HashMap<String, PropertyDescriptor>();
        // 初始化映射属性，原代码使用原始类型的 HashSet，应指定泛型
        this.mappedProperties = new HashSet<String>();
        // 获取映射类的所有属性描述符
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(mappedClass);
        // 原代码中 var3 变量冗余，可直接使用 pds
        for (PropertyDescriptor pd : pds) {
            // 如果属性有写方法
            if (pd.getWriteMethod() != null) {
                // 将属性名的小写形式作为键，属性描述符作为值，存入映射字段
                this.mappedFields.put(pd.getName().toLowerCase(), pd);
                // 将属性名转换为下划线命名法
                String underscoredName = this.underscoreName(pd.getName());
                // 如果下划线命名法与小写属性名不同
                if (!pd.getName().toLowerCase().equals(underscoredName)) {
                    // 将下划线命名法作为键，属性描述符作为值，存入映射字段
                    this.mappedFields.put(underscoredName, pd);
                }
                // 将属性名添加到映射属性集合
                this.mappedProperties.add(pd.getName());
            }
        }
    }

    /**
     * 将驼峰命名法的字符串转换为下划线命名法。
     *
     * @param name 要转换的驼峰命名法字符串
     * @return 转换后的下划线命名法字符串
     */
    private String underscoreName(String name) {
        // 如果字符串为空
        if (!StringUtils.hasLength(name)) {
            // 返回空字符串
            return "";
        } else {
            // 创建一个 StringBuilder 用于构建结果字符串
            StringBuilder result = new StringBuilder();
            // 将字符串的第一个字符转换为小写并添加到结果字符串
            result.append(name.substring(0, 1).toLowerCase());

            // 遍历字符串的其余字符
            for (int i = 1; i < name.length(); ++i) {
                // 获取当前字符
                String s = name.substring(i, i + 1);
                // 获取当前字符的小写形式
                String slc = s.toLowerCase();
                // 如果当前字符与小写形式不同
                if (!s.equals(slc)) {
                    // 添加下划线和小写字符
                    result.append("_").append(slc);
                } else {
                    // 直接添加字符
                    result.append(s);
                }
            }
            // 返回结果字符串
            return result.toString();
        }
    }

    /**
     * 获取当前设置的映射类。
     *
     * @return 映射类的 Class 对象
     */
    public final Class<T> getMappedClass() {
        return this.mappedClass;
    }

    /**
     * 设置是否检查对象的所有属性是否都被填充。
     *
     * @param checkFullyPopulated 是否检查属性填充的标志
     */
    public void setCheckFullyPopulated(boolean checkFullyPopulated) {
        this.checkFullyPopulated = checkFullyPopulated;
    }

    /**
     * 获取是否检查对象的所有属性是否都被填充的标志。
     *
     * @return 是否检查属性填充的标志
     */
    public boolean isCheckFullyPopulated() {
        return this.checkFullyPopulated;
    }

    /**
     * 设置是否为原始类型的属性设置默认值。
     *
     * @param primitivesDefaultedForNullValue 是否为原始类型属性设置默认值的标志
     */
    public void setPrimitivesDefaultedForNullValue(boolean primitivesDefaultedForNullValue) {
        this.primitivesDefaultedForNullValue = primitivesDefaultedForNullValue;
    }

    /**
     * 获取是否为原始类型的属性设置默认值的标志。
     *
     * @return 是否为原始类型属性设置默认值的标志
     */
    public boolean isPrimitivesDefaultedForNullValue() {
        return this.primitivesDefaultedForNullValue;
    }

    /**
     * 将 Redis 哈希数据映射到指定类型的 Java 对象。
     *
     * @param map 包含 Redis 哈希数据的 Map
     * @return 映射后的 Java 对象
     */
    public T mapRow(Map<String, String> map) {
        // 断言映射类不为空，如果为空则抛出异常
        Assert.state(this.mappedClass != null, "Mapped class was not specified");
        // 使用反射实例化一个映射类的对象
        Object mappedObject = BeanUtils.instantiateClass(this.mappedClass);
        // 创建一个 BeanWrapper 对象，用于访问和操作 mappedObject 的属性
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(mappedObject);
        // 初始化 BeanWrapper，可用于自定义属性编辑器等操作
        this.initBeanWrapper(bw);
        // 如果需要检查属性是否完全填充，则创建一个 HashSet 用于存储已填充的属性名，否则为 null
        HashSet<String> populatedProperties = this.isCheckFullyPopulated() ? new HashSet<String>() : null;
        // 获取 map 中的所有键，即 Redis 哈希数据的列名
        Set<String> columnSet = map.keySet();
        // 遍历所有列名
        for (String column : columnSet) {
            // 去除列名中的空格并转换为小写，然后从映射字段中获取对应的属性描述符
            PropertyDescriptor pd = this.mappedFields.get(column.replaceAll(" ", "").toLowerCase());
            // 如果找到了对应的属性描述符
            if (pd != null) {
                try {
                    // 从 map 中获取该列对应的值
                    String ex = map.get(column);
                    // 如果日志级别为调试，则记录列名和属性名的映射信息
                    if (this.logger.isDebugEnabled()) {
                        this.logger.debug("Mapping column '" + column + "' to property '" + pd.getName() + "' of type " + pd.getPropertyType());
                    }

                    try {
                        // 将获取的值赋给 value 变量
                        Object value = ex;
                        // 获取属性的类型
                        Class<?> clazz = bw.getPropertyType(pd.getName());
                        // 如果值的类型与属性类型不匹配，这里原代码为空，可根据需求添加处理逻辑
                        if (!clazz.isInstance(ex)) {

                        }
                        // 注册自定义的时间戳编辑器
                        bw.registerCustomEditor(Timestamp.class, new StringToTimestampEditor());
                        // 注册自定义的日期编辑器
                        bw.registerCustomEditor(Date.class, new StringToDateEditor());
                        // 设置属性的值
                        bw.setPropertyValue(pd.getName(), value);
                    } catch (TypeMismatchException var13) {
                        // 如果值不为空或者不允许为原始类型设置默认值，则抛出异常
                        if (ex != null || !this.primitivesDefaultedForNullValue) {
                            throw var13;
                        }
                        // 记录类型不匹配的调试信息
                        this.logger.debug("Intercepted TypeMismatchException for column '" + column + "' with value " + ex + " when setting property '" + pd.getName() + "' of type " + pd.getPropertyType() + " on object: " + mappedObject);
                    }

                    // 如果需要检查属性填充情况，则将该属性名添加到已填充属性集合中
                    if (populatedProperties != null) {
                        populatedProperties.add(pd.getName());
                    }
                } catch (NotWritablePropertyException var14) {
                    // 如果属性不可写，则抛出无效映射异常
                    throw new InvalideMapException("Unable to map column " + column + " to property " + pd.getName(), var14);
                }
            }
        }

        // 如果需要检查属性填充情况，并且已填充属性集合与映射属性集合不相等
        if (populatedProperties != null && !populatedProperties.equals(this.mappedProperties)) {
            // 抛出无效映射异常，提示 Map 中不包含填充对象所需的所有字段
            throw new InvalideMapException("Given Map does not contain all fields necessary to populate object of class [" + this.mappedClass + "]: " + this.mappedProperties);
        } else {
            // 将映射后的对象强制转换为泛型类型并返回
            return (T) mappedObject;
        }
    }

    /**
     * 初始化 BeanWrapper，可用于自定义属性编辑器等操作。
     * 目前该方法为空，可根据需求进行实现。
     *
     * @param bw 要初始化的 BeanWrapper 对象
     */
    protected void initBeanWrapper(BeanWrapper bw) {
    }

//    /**
//     * 从结果集中获取指定列的值，并根据属性描述符进行类型转换。
//     * 该方法目前被注释掉，可根据需求进行使用。
//     *
//     * @param rs  结果集对象
//     * @param index 列的索引
//     * @param pd  属性描述符
//     * @return 转换后的值
//     */
//    protected Object getColumnValue(ResultSet rs, int index, PropertyDescriptor pd) {
//        return JdbcUtils.getResultSetValue(rs, index, pd.getPropertyType());
//    }

    /**
     * 创建一个新的 HashValueMapper 实例，并设置映射类。
     *
     * @param mappedClass 要映射到的 Java 对象的类
     * @param <T> 要映射到的 Java 对象类型
     * @return 新的 HashValueMapper 实例
     */
    public static <T> HashValueMapper<T> newInstance(Class<T> mappedClass) {
        // 创建一个新的 HashValueMapper 实例
        HashValueMapper<T> newInstance = new HashValueMapper<T>();
        // 设置映射类
        newInstance.setMappedClass(mappedClass);
        // 返回新实例
        return newInstance;
    }
}

