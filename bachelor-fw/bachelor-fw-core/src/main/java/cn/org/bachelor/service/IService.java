package cn.org.bachelor.service;

import java.util.List;

public interface IService<T> {
    /**
     * 持久化
     * @param model 模型对象
     * @return 插入成功的数量
     */
    int insert(T model);

    /**
     * 批量持久化
     * @param models 模型对象列表
     * @return 插入成功的数量
     */
    int insert(List<T> models);

    /**
     * 通过主鍵刪除
     * @param key 主鍵
     * @return 刪除成功的数量
     */
    int deleteByPrimaryKey(Object key);

    /**
     * 通过模型刪除
     * @param model 模型
     * @return 刪除成功的数量
     */
    int delete(T model);

    /**
     * 更新模型
     * @param model 模型
     * @return 更新成功的数量
     */
    int update(T model);

    /**
     * 通过ID查找
     * @param key ID
     * @return 查找到的数据
     */
    T selectByPrimaryKey(Object key);

    /**
     * 批量查询，返回结果列表
     * @return 结果列表
     */
    List<T> selectAll();

}
