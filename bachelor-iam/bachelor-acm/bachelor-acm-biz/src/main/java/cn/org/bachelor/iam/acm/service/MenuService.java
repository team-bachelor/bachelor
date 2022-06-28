package cn.org.bachelor.iam.acm.service;

import cn.org.bachelor.core.exception.BusinessException;
import cn.org.bachelor.iam.IamValueHolderService;
import cn.org.bachelor.iam.acm.dao.MenuMapper;
import cn.org.bachelor.iam.acm.dao.OrgMenuMapper;
import cn.org.bachelor.iam.acm.dao.RoleMenuMapper;
import cn.org.bachelor.iam.acm.domain.Menu;
import cn.org.bachelor.iam.acm.domain.OrgMenu;
import cn.org.bachelor.iam.acm.domain.RoleMenu;
import cn.org.bachelor.iam.acm.permission.PermissionModel;
import cn.org.bachelor.iam.acm.vo.ISMenuVo;
import cn.org.bachelor.iam.acm.vo.MenuVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
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

    @Resource
    private MenuMapper menuMapper;

    @Autowired
    private IamValueHolderService valueHolder;


    /**
     * 新增菜单
     *
     * @param m 菜单信息
     */
    public void insert(Menu m) {
        m.setId(UUID.randomUUID().toString());
        m.setUpdateTime(new Date());
        String userCode = valueHolder.getCurrentUserCode();
        m.setUpdateUser(userCode == null ? valueHolder.getRemoteIP() : userCode);
        menuMapper.insert(m);
    }

    /**
     * 更新菜单
     *
     * @param m 菜单信息
     */
    public void update(Menu m) {
        m.setUpdateTime(new Date());
        String userCode = valueHolder.getCurrentUserCode();
        m.setUpdateUser(userCode == null ? valueHolder.getRemoteIP() : userCode);
        menuMapper.updateByPrimaryKey(m);
    }


    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     */
    public void delete(String menuId) {
        menuMapper.deleteByPrimaryKey(menuId);
    }

    /**
     * 计算当前用户的菜单
     *
     * @param userCode 用户编码
     * @return 用户菜单
     */
    public List<MenuVo> calUserMenu(String userCode) {
        return calUserMenu(userCode, null);
    }

    /**
     * 计算当前用户的菜单
     *
     * @param userCode 用户编码
     * @return 用户菜单
     */
    public List<MenuVo> calUserMenu(String userCode, String group) {
        if (userCode == null) {
            throw new BusinessException("user code could not be null");
        }
        boolean isAdmin;
        //如果是管理员则取全部菜单
        isAdmin = userCode.equals(valueHolder.getCurrentUser().getCode()) && valueHolder.getCurrentUser().isAdministrator();
        if (isAdmin) {
            return getAllMenu(group);
        } else {
            List<RoleMenu> rmList = roleMenuMapper.selectViaUserCode(userCode);
            return calRoleMenu(userCode, PermissionModel.USER, rmList, group);
        }

    }

    private List<MenuVo> getAllMenu(String group) {
        return getMenuVoListWithCodes(null, PermissionModel.USER, group, null);
    }

    private List<MenuVo> calRoleMenu(String owner, PermissionModel type, List<RoleMenu> rmList, String groupName) {
        if (rmList.size() == 0) {
            return Collections.emptyList();
        }
        List<String> menuCodes = new ArrayList<String>(rmList.size());
        for (RoleMenu p : rmList) {
            menuCodes.add(p.getMenuCode());
        }
        return getMenuVoListWithCodes(owner, type, groupName, menuCodes);
    }

    public List<ISMenuVo> getUserISMenu(String userCode, String group) {
        List<MenuVo> originMenus = calUserMenu(userCode, group);
        return convert2ISMenu(originMenus, true);
    }

    private List<ISMenuVo> convert2ISMenu(List<MenuVo> originMenus, boolean isSubSys) {
        List<ISMenuVo> menus = new ArrayList<>(originMenus.size());
        originMenus.forEach(m -> {
            menus.add(convert2ISMenu(m, isSubSys));
        });
        return menus;
    }

    private ISMenuVo convert2ISMenu(MenuVo originMenu, boolean isSubSys) {
        if (originMenu == null) {
            return null;
        }
        ISMenuVo m = new ISMenuVo();
        m.setId(originMenu.getId());
        m.setName(originMenu.getName());
        m.setIcon(originMenu.getIcon());
        m.setParentId(originMenu.getParentId());
        m.setSeqOrder(originMenu.getSeqOrder());
        m.setComment(originMenu.getComment());
        m.setHas(originMenu.isHas());
        m.setGroupName(originMenu.getGroupName());
        m.setActivePath(originMenu.getCode());
        m.setEntry(isSubSys ? originMenu.getUri() : null);
        m.setTitle(originMenu.getName());
        m.setComponent(isSubSys ? null : originMenu.getUri());
        m.setType(originMenu.getType());
        m.setOwner(originMenu.getOwner());
        m.setChildren(convert2ISMenu(originMenu.getSubMenus(), false));
        return m;
    }

    /**
     * 获取组织机构的菜单
     *
     * @param orgId 组织机构ID
     * @return 菜单编号
     */
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
     * @param roleCode 角色编码
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
            menu.setUpdateUser(valueHolder.getCurrentUser() == null ? "unknown" : valueHolder.getCurrentUser().getCode());
            orgMenuMapper.insert(menu);
        }
    }

    /**
     * @param roleCode
     * @return
     * @description 取得备选权限列表
     * @author liuzhuo
     * @date 2018/10/27 11:16
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
            rmenu.setUpdateUser(valueHolder.getCurrentUser() == null ? "unknown" : valueHolder.getCurrentUser().getCode());
            roleMenuMapper.insert(rmenu);
        }
    }

    private List<String> sortMenuCode(List<String> menuCode) {
        List<MenuVo> fullMenu = getMenuList(true, null);
        Map<String, MenuVo> mmap = new HashMap<>(fullMenu.size());
        fullMenu.forEach(menu -> {
            mmap.put(menu.getCode(), menu);
        });
        List<String> adds = new ArrayList<>();
        menuCode.forEach(code -> {
            checkForParentMenu(adds, mmap, code);
        });
        adds.forEach(m -> {
            if (!menuCode.contains(m)) {
                menuCode.add(m);
            }
        });
        return menuCode;
    }

    private void checkForParentMenu(List<String> adds, Map<String, MenuVo> mmap, String code) {
        MenuVo m = mmap.get(code);
        if (m == null) return;
        if (m.getParent() == null) {
            return;
        } else {
            if (!adds.contains(m.getParent().getCode())) {
                adds.add(m.getParent().getCode());
            }
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
        return getMenuList(false, null);
    }

    private List<MenuVo> getMenuVoListWithCodes(String owner, PermissionModel type, String group, List<String> menuCodes) {
        Example example = getMenuCriteria(menuCodes, group);
        List<Menu> menus = menuMapper.selectByExample(example);
        return getMenuList(owner, type, menus);
    }

    private Example getMenuCriteria(List<String> menuCodes, String group) {
        Example example = new Example(Menu.class);
        example.orderBy("seqOrder").orderBy("parentId").asc();
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(group)) {
            criteria.andEqualTo("groupName", group);
        }
        if (menuCodes != null && menuCodes.size() != 0) {
            criteria.andIn("code", menuCodes);
        }
        return example;
    }

    private List<MenuVo> getMenuList(String owner, PermissionModel type, List<Menu> menus) {
        return getMenuList(false, menus, owner, type, true);
    }

    public List<MenuVo> getMenuList(boolean isFlat, String groupName) {
        Example example = getMenuCriteria(null, groupName);
        List<Menu> menus = menuMapper.selectByExample(example);
        return getMenuList(isFlat, menus, null, PermissionModel.ROLE, false);
    }

    private List<MenuVo> getMenuList(boolean isFlat, List<Menu> menus, String owner, PermissionModel type, boolean isHas) {
        Map<String, MenuVo> menuVoMap = new LinkedHashMap<>(menus.size());
        for (Menu m : menus) {
            MenuVo mvo = toMenuVo(null, type, isHas, m, null);
            menuVoMap.put(m.getId(), mvo);
        }
        for (Menu m : menus) {
            MenuVo parent = null;
            if (menuVoMap.containsKey(m.getParentId())) {
                parent = menuVoMap.get(m.getParentId());
            }
            //MenuVo mvo = toMenuVo(null, PermissionModel.ROLE, false, m, parent);
            MenuVo mvo = menuVoMap.get(m.getId());
            if (parent != null) {
                mvo.setParentId(parent.getId());
                mvo.setParent(parent);
                parent.getSubMenus().add(mvo);
            }
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

    private MenuVo toMenuVo(String owner, PermissionModel type, boolean has, Menu m, MenuVo parent) {
        MenuVo mv = new MenuVo(m.getId(), m.getCode(), m.getUri(), m.getIcon(), m.getComment(), type, parent, new ArrayList<>());
        mv.setName(m.getName());
        mv.setParentId(m.getParentId());
        mv.setSeqOrder(m.getSeqOrder());
        mv.setOwner(owner);
        mv.setHas(has);
        mv.setGroupName(m.getGroupName());
        return mv;
    }
}
