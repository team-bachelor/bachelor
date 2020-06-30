package org.bachelor.service;

import java.util.List;

public interface IBaseService<T> {
    /**
     * 持久化
     * @param model
     * @return
     */
    int insert(T model);

    /**
     * 批量持久化
     * @param models
     */
    int insert(List<T> models);

    /**
     * 通过主鍵刪除
     * @param key
     * @return
     */
    int deleteByPrimaryKey(Object key);

    /**
     * 删除
     * @param model
     * @return
     */
    int delete(T model);

    /**
     * 更新
     * @param model
     * @return
     */
    int update(T model);

    /**
     * 通过ID查找
     * @param key
     * @return
     */
    T selectByPrimaryKey(Object key);

    /**
     * 批量查询，返回结果列表
     * @return
     */
    List<T> selectAll();

}
