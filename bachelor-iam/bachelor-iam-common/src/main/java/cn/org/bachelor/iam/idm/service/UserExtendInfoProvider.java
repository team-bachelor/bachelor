package cn.org.bachelor.iam.idm.service;

import java.util.Map;

/**
 * 该接口定义了一个用户扩展信息提供者，用于调用并获取用户的扩展信息。
 *
 * @author 未指定
 */
public interface UserExtendInfoProvider {
    /**
     * 调用该方法以获取用户的扩展信息。
     *
     * @param userInfo 包含用户信息的映射，键为字符串，值为对象类型。
     * @return 包含用户扩展信息的映射，键为字符串，值为对象类型。
     */
    // TODO: 修复泛型使用问题，这里使用 '? extends Object' 可简化为直接使用 'Object'
    Map<String, ? extends Object> invoke(Map<String, ? extends Object> userInfo);
}