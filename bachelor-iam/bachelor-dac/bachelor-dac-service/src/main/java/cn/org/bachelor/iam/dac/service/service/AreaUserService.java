package cn.org.bachelor.iam.dac.service.service;

import cn.org.bachelor.iam.IamContext;
import cn.org.bachelor.iam.dac.service.dao.DacAreaUserMapper;
import cn.org.bachelor.iam.dac.service.domain.DacAreaUser;
import cn.org.bachelor.iam.dac.service.pojo.dto.QueryAreaUserDTO;
import cn.org.bachelor.iam.idm.service.UserExtendInfoProvider;
import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.web.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Intellij IDEA.
 * User:  ZhuangJiaHui
 */
@Service
public class AreaUserService implements UserExtendInfoProvider {
    private static final Logger logger = LoggerFactory.getLogger(AreaUserService.class);

    @Autowired
    private DacAreaUserMapper areaUserMapper;
    @Resource
    private IamContext iamContext;

    public PageInfo<DacAreaUser> list(QueryAreaUserDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<DacAreaUser> list = areaUserMapper.selectByParam(dto);
        return new PageInfo<>(list);
    }

    public List<String> add(List<DacAreaUser> users) {
        List<String> msg = new ArrayList<>();
        for (DacAreaUser areaUser : users) {
            Integer count = areaUserMapper.countByUserCode(areaUser.getUserCode());
            if (count != 0) {//此用户已经存在，不可重复添加
                logger.error("UserCode为{}的用户已经存在", areaUser.getUserCode());
                msg.add(areaUser.getUserName());
            }
        }
        if (msg.size() != 0) {
            return msg;
        }
        for (DacAreaUser areaUser : users) {
            String currentUserName = null;
            try {
                currentUserName = iamContext.getCurrentUser().getName();//当前用户
            } catch (Exception e) {//e.printStackTrace();
            }
            areaUser.setId(UuidUtil.getUUID());
            areaUser.setCreateTime(new Date());
            areaUser.setCreateUser(currentUserName);
            areaUserMapper.insertSelective(areaUser);
        }
        return null;
    }


    public void delete(List<String> ids) {
        areaUserMapper.deleteByIds(ids);
    }

    public String getAreaIdByUserCode(String userCode) {
        return areaUserMapper.getAreaIdByUserCode(userCode);
    }

    @Override
    public Map<String, ? extends Object> invoke(Map<String, ? extends Object> userInfo) {
        Map<String, Object> result = new HashMap<>(1);
        Object o = userInfo.get("account");
        if (o != null) {
            result.put(JwtToken.PayloadKey.AREA_ID, areaUserMapper.getAreaCodeByUserCode(o.toString()));
        }
        return result;
    }
}
