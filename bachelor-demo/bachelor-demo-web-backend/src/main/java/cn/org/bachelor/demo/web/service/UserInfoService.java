package cn.org.bachelor.demo.web.service;

import com.github.pagehelper.PageInfo;
import cn.org.bachelor.demo.web.dao.UserInfoMapper;
import cn.org.bachelor.demo.web.domain.UserInfo;
import cn.org.bachelor.demo.web.vo.UserInfoVO;
import cn.org.bachelor.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * Description: 用户管理服务
 *
 * @Author Alexhendar
 * @Date: Created in 2018/10/19 17:16
 * @Modified By:
 */

@Service
public class UserInfoService extends BaseService<UserInfo> {

  @Resource
  UserInfoMapper userInfoMapper;

  /**
   * <p>selectAllByPage :  查询用户列表</p>
   *
   * @param
   * @return PageInfo
   * @Auther: Alexhendar
   * @Date: 2018/10/19 17:17
   */

  public PageInfo<UserInfoVO> selectAllByPage() {
    List<UserInfoVO> userInfoList = userInfoMapper.selectAllUserInfoVO();
    PageInfo<UserInfoVO> userInfoPage = new PageInfo<>(userInfoList);
    return userInfoPage;
  }
}

