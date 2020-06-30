package org.bachelor.service.impl;

import org.bachelor.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @ClassName BaseService
 * @Description:
 * @Author Alexhendar
 * @Date 2018/10/10 9:20
 * @Version 1.0
 **/
public abstract class BaseService<T> implements IBaseService<T> {


    @Autowired
    BaseMapper<T> mapper;

    @Override
    public int insert(T model) {
        return mapper.insertSelective(model);
    }

    @Override
    public int insert(List<T> models) {
        int count = 0;
        for(T t:models){
           int res = mapper.insertSelective(t);
           count += res;
        }
        return count;
    }

    @Override
    public int deleteByPrimaryKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public int delete(T model) {
        return mapper.delete(model);
    }

    @Override
    public int update(T model) {
        return mapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public T selectByPrimaryKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }
}
