package cn.org.bachelor.demo.web.service;

import cn.org.bachelor.iam.token.JwtToken;
import cn.org.bachelor.demo.web.dao.BaseMenuMapper;
import cn.org.bachelor.demo.web.domain.BaseMenu;
import cn.org.bachelor.demo.web.utils.ChildrenListUtil;
import cn.org.bachelor.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gxf on 2021/1/16 15:09
 */
@Service
public class BaseMenuService extends BaseService<BaseMenu> {

    @Resource
    BaseMenuMapper baseMenuMapper;

    public int insert(HttpServletRequest request, BaseMenu baseMenu) {
        String userId = request.getHeader(JwtToken.PayloadKey.USER_ID);
        baseMenu.setUpdateUser(userId).setUpdateTime(new Date());
        return baseMenuMapper.insert(baseMenu);
    }

    public List<BaseMenu> selectAll() {
        List<BaseMenu> list = baseMenuMapper.selectAll();
        for (BaseMenu baseMenu : list) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("title", baseMenu.getTitle());
            baseMenu.setMeta(map);
        }
        List<BaseMenu> baseMenuList = ChildrenListUtil.toTree02(list);
        return baseMenuList;
    }
}
