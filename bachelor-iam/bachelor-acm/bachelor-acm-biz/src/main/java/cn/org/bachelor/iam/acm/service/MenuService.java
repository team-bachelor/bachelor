package cn.org.bachelor.iam.acm.service;

import org.apache.commons.lang3.StringUtils;
import cn.org.bachelor.iam.acm.AuthValueHolderService;
import cn.org.bachelor.iam.acm.dao.MenuMapper;
import cn.org.bachelor.iam.acm.dao.OrgMenuMapper;
import cn.org.bachelor.iam.acm.dao.RoleMenuMapper;
import cn.org.bachelor.iam.acm.domain.Menu;
import cn.org.bachelor.iam.acm.domain.OrgMenu;
import cn.org.bachelor.iam.acm.domain.RoleMenu;
import cn.org.bachelor.iam.acm.vo.MenuVo;
import cn.org.bachelor.iam.acm.permission.PermissionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/11/9
 */
@Service
public class MenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private OrgMenuMapper orgMenuMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private AuthValueHolderService valueHolder;

    /**
     * 计算当前用户的菜单
     *
     * @param userCode 用户编码
     * @return 用户菜单
     */
    public List<MenuVo> calUserMenu(String userCode) {
        if (userCode.equals(valueHolder.getCurrentUser().getCode())
                && valueHolder.getCurrentUser().isAdministrator()) {
            userCode = null;
        }
        if (StringUtils.isEmpty(userCode)) {
            return getMenuVoList(userCode, PermissionModel.USER, null);
        } else {
            List<RoleMenu> rmList = roleMenuMapper.selectViaUserCode(userCode);
            return calRoleMenu(userCode, PermissionModel.USER, rmList);
        }

    }

    private List<MenuVo> calRoleMenu(String owner, PermissionModel type, List<RoleMenu> rmList) {
        if (rmList.size() == 0) {
            return Collections.emptyList();
        }
        List<String> menuCodes = new ArrayList<String>(rmList.size());
        for (RoleMenu p : rmList) {
            menuCodes.add(p.getMenuCode());
        }
        return getMenuVoList(owner, type, menuCodes);
    }

    private List<MenuVo> getMenuVoList(String owner, PermissionModel type, List<String> menuCodes) {
        Example example = new Example(Menu.class);
        example.setOrderByClause("PARENT_ID, SEQ_ORDER ASC");
        if (menuCodes != null && menuCodes.size() != 0) {
            example.createCriteria().andIn("code", menuCodes);
        }
        List<Menu> menus = menuMapper.selectByExample(example);
        return getMenuVo(owner, type, menus);
    }

    private List<MenuVo> getMenuVo(String owner, PermissionModel type, List<Menu> menus) {
        Map<String, MenuVo> menuMap = new LinkedHashMap<>(menus.size());

        for (Menu m : menus) {
            MenuVo vo = toMenuVo(owner, type, true, m, null);
            if (StringUtils.isNotEmpty(m.getParentId())
                    && menuMap.containsKey(m.getParentId())) {
                MenuVo parent = menuMap.get(m.getParentId());
                vo.setParent(parent);
                parent.getSubMenus().add(vo);
            }
            menuMap.put(m.getId(), vo);
        }
        List<MenuVo> result = new ArrayList<>();
        for (MenuVo m : menuMap.values()) {
            if (m.getParent() == null) {
                result.add(m);
            }
        }
        return result;
    }

    private MenuVo toMenuVo(String owner, PermissionModel type, boolean has, Menu m, MenuVo parent) {
        MenuVo mv = new MenuVo(
                m.getId(),
                m.getCode(),
                m.getUri(),
                m.getIcon(),
                m.getComment(),
                type,
                parent,
                new ArrayList<MenuVo>());
        mv.setOwner(owner);
        mv.setHas(has);
        return mv;
    }

    public List<String> getOrgMenu(String orgId) {
        OrgMenu exrm = new OrgMenu();
        exrm.setOrgCode(orgId);
        List<OrgMenu> omList = orgMenuMapper.select(exrm);
        List<String> menus = new ArrayList<>(omList.size());
        for (OrgMenu rm : omList) {
            menus.add(rm.getMenuCode());
        }
        return menus;
    }

    /**
     * @param roleCode
     * @param menuCode 当前角色拥有的所有菜单列表
     * @author liuzhuo
     */
    public void setOrgMenu(String roleCode, List<String> menuCode) {
        //设置查询的样例
        menuCode = sortMenuCode(menuCode);
        OrgMenu om = new OrgMenu();
        om.setOrgCode(roleCode);
        orgMenuMapper.delete(om);
        for (String m : menuCode) {
            OrgMenu menu = new OrgMenu();
            menu.setId(UUID.randomUUID().toString());
            menu.setOrgCode(roleCode);
            menu.setMenuCode(m);
            menu.setUpdateTime(new Date());
            menu.setUpdateUser(valueHolder.getCurrentUser() == null ?
                    "unknown" : valueHolder.getCurrentUser().getCode());
            orgMenuMapper.insert(menu);
        }
    }

    /**
     * @param roleCode
     * @Description:取得备选权限列表
     * @Author: liuzhuo
     * @Date: 2018/10/27 11:16
     * @Return:
     */
    public List<String> getRoleMenu(String roleCode) {
        RoleMenu exrm = new RoleMenu();
        exrm.setRoleCode(roleCode);
        List<RoleMenu> rmList = roleMenuMapper.select(exrm);

        List<String> roleMenus = new ArrayList<>(rmList.size());
        for (RoleMenu rm : rmList) {
            roleMenus.add(rm.getMenuCode());
        }
        return roleMenus;
    }

    /**
     * @param roleCode
     * @param menuCode 当前角色拥有的所有菜单列表
     * @author liuzhuo
     */
    public void setRoleMenu(String roleCode, List<String> menuCode) {
        //设置查询的样例
        menuCode = sortMenuCode(menuCode);
        RoleMenu rm = new RoleMenu();
        rm.setRoleCode(roleCode);
        roleMenuMapper.delete(rm);
        for (String m : menuCode) {
            RoleMenu rmenu = new RoleMenu();
            rmenu.setId(UUID.randomUUID().toString());
            rmenu.setRoleCode(roleCode);
            rmenu.setMenuCode(m);
            rmenu.setUpdateTime(new Date());
            rmenu.setUpdateUser(valueHolder.getCurrentUser() == null ?
                    "unknown" : valueHolder.getCurrentUser().getCode());
            roleMenuMapper.insert(rmenu);
        }
    }

    private List<String> sortMenuCode(List<String> menuCode) {
        List<MenuVo> fullMenu = getMenuList(true);
        Map<String, MenuVo> mmap = new HashMap<>(fullMenu.size());
        fullMenu.forEach(menu -> {
            mmap.put(menu.getCode(), menu);
        });
        List<String> adds = new ArrayList<>();
        menuCode.forEach(code -> {
            checkForParentMenu(adds, mmap, code);
        });
        menuCode.addAll(adds);
        return menuCode;
    }

    private void checkForParentMenu(List<String> adds, Map<String, MenuVo> mmap, String code) {
        MenuVo m = mmap.get(code);
        if (m == null) return;
        if (m.getParent() == null) {
            return;
        } else {
            adds.add(m.getParent().getCode());
            checkForParentMenu(adds, mmap, m.getParent().getCode());
        }
    }

    /**
     * @Description:取得备选权限列表
     * @Author: liuzhuo
     * @Date: 2018/10/27 11:16
     * @Return:
     */
    //TODO 目前不考虑机构隔离，以后要考虑
    public List<MenuVo> getMenuList() {
        return getMenuList(false);
    }

    public List<MenuVo> getMenuList(boolean isFlat) {
        Example example = new Example(Menu.class);
        example.setOrderByClause("PARENT_ID, SEQ_ORDER ASC");
        List<Menu> menus = menuMapper.selectByExample(example);
        Map<String, MenuVo> menuVoMap = new LinkedHashMap<>(menus.size());
        for (Menu m : menus) {
            MenuVo parent = null;
            if (menuVoMap.containsKey(m.getParentId())) {
                parent = menuVoMap.get(m.getParentId());
            }
            MenuVo mvo = toMenuVo(null, PermissionModel.ROLE, false, m, parent);
            if (parent != null)
                parent.getSubMenus().add(mvo);
            menuVoMap.put(m.getId(), mvo);
        }
        List<MenuVo> result = new ArrayList<>();
        if (isFlat) {
            result.addAll(menuVoMap.values());
        } else {
            for (MenuVo m : menuVoMap.values()) {
                if (m.getParent() == null) {
                    result.add(m);
                }
            }
        }
        return result;
    }

}
