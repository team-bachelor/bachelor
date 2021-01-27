package cn.org.bachelor.demo.web.dao;

import cn.org.bachelor.demo.web.domain.BaseMenu;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by gxf on 2021/1/16 15:08
 */
@Repository
public interface BaseMenuMapper extends Mapper<BaseMenu> {

//    @Insert("insert into ")
//    int insert(Menu menu);


    @Select("select *,URI AS path, NAME AS title from cmn_acm_menu")
    List<BaseMenu> selectAll();

}
