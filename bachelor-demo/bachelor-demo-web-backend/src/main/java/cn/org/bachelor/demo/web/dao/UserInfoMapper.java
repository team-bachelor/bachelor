package cn.org.bachelor.demo.web.dao;

import cn.org.bachelor.demo.web.domain.UserInfo;
import cn.org.bachelor.demo.web.vo.UserInfoVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserInfoMapper extends Mapper<UserInfo> {

  List<UserInfoVO> selectAllUserInfoVO();
}
