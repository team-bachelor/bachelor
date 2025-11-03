package cn.org.bachelor.iam.acm.service;

import cn.org.bachelor.exception.BusinessException;
import cn.org.bachelor.iam.IamContext;
import cn.org.bachelor.iam.acm.dao.MenuMapper;
import cn.org.bachelor.iam.acm.dao.OrgMenuMapper;
import cn.org.bachelor.iam.acm.dao.RoleMenuMapper;
import cn.org.bachelor.iam.acm.domain.OrgMenu;
import cn.org.bachelor.iam.acm.domain.RoleMenu;
import cn.org.bachelor.iam.acm.permission.PermissionModel;
import cn.org.bachelor.iam.acm.pojo.ISMenu;
import cn.org.bachelor.iam.acm.pojo.Menu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import jakarta.annotation.Resource;
import java.util.*;

/**
 *
 * @author liuzhuo
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
    private IamContext iamContext;


    /**
     * 新增菜单
     *
     * @param m 菜单信息
     */
    public void insert(cn.org.bachelor.iam.acm.domain.Menu m) {
        m.setId(UUID.randomUUID().toString());
        m.setUpdateTime(new Date());
        String userCode = iamContext.getUserCode();
        m.setUpdateUser(userCode == null ? iamContext.getRemoteIP() : userCode);
        menuMapper.insert(m);
    }

    /**
     * 更新菜单
     *
     * @param m 菜单信息
     */
    public void update(cn.org.bachelor.iam.acm.domain.Menu m) {
        m.setUpdateTime(new Date());
        String userCode = iamContext.getUserCode();
        m.setUpdateUser(userCode == null ? iamContext.getRemoteIP() : userCode);
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
    public List<Menu> calUserMenu(String userCode) {
        return calUserMenu(userCode, null, null);
    }

    /**
     * 计算当前用户的菜单
     *
     * @param userCode 用户编码
     * @return 用户菜单
     */
    public List<Menu> calUserMenu(String userCode, String group, String parentId) {
        if (userCode == null) {
            throw new BusinessException("user code could not be null");
        }
        boolean isAdmin;
        //如果是管理员则取全部菜单
        isAdmin = userCode.equals(iamContext.getUser().getCode()) && iamContext.getUser().isAdministrator();
        if (isAdmin) {
            return getAllMenu(group, parentId);
        } else {
            List<RoleMenu> rmList = roleMenuMapper.selectViaUserCode(userCode);
            return calRoleMenu(userCode, PermissionModel.USER, rmList, group, parentId);
        }

    }

    private List<Menu> getAllMenu(String group, String parentId) {
        return getMenuListWithCodes(null, PermissionModel.USER, group, null, parentId);
    }

    private List<Menu> calRoleMenu(String owner, PermissionModel type, List<RoleMenu> rmList, String groupName, String parentId) {
        if (rmList.size() == 0) {
            return Collections.emptyList();
        }
        List<String> menuCodes = new ArrayList<>(rmList.size());
        for (RoleMenu p : rmList) {
            menuCodes.add(p.getMenuCode());
        }
        return getMenuListWithCodes(owner, type, groupName, menuCodes, parentId);
    }

    private List<Menu> filterMenuByParent(List<Menu> originMenus, String parentId) {
        return null;
    }

    public List<ISMenu> getUserISMenu(String userCode, String group, String parentId) {
        List<Menu> originMenus = calUserMenu(userCode, group, parentId);
        return convert2ISMenu(originMenus, true);
    }

    private List<ISMenu> convert2ISMenu(List<Menu> originMenus, boolean isSubSys) {
        List<ISMenu> menus = new ArrayList<>();
        if (originMenus == null) {
            return menus;
        }
        originMenus.forEach(m -> menus.add(convert2ISMenu(m, isSubSys)));
        return menus;
    }

    private ISMenu convert2ISMenu(Menu originMenu, boolean isSubSys) {
        if (originMenu == null) {
            return null;
        }
        ISMenu m = new ISMenu();
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
        m.setHidden(originMenu.isHidden());
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
     * @param orgCode  组织编码
     * @param menuCode 当前角色拥有的所有菜单列表
     * @author liuzhuo
     */
    public void setOrgMenu(String orgCode, List<String> menuCode) {
        //设置查询的样例
        OrgMenu om = new OrgMenu();
        om.setOrgCode(orgCode);
        orgMenuMapper.delete(om);
        for (String m : menuCode) {
            OrgMenu menu = new OrgMenu();
            menu.setId(UUID.randomUUID().toString());
            menu.setOrgCode(orgCode);
            menu.setMenuCode(m);
            menu.setUpdateTime(new Date());
            menu.setUpdateUser(iamContext.getUser() == null ? "unknown" : iamContext.getUser().getCode());
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
        sortMenuCode(menuCode);
        RoleMenu rm = new RoleMenu();
        rm.setRoleCode(roleCode);
        roleMenuMapper.delete(rm);
        for (String m : menuCode) {
            RoleMenu rmenu = new RoleMenu();
            rmenu.setId(UUID.randomUUID().toString());
            rmenu.setRoleCode(roleCode);
            rmenu.setMenuCode(m);
            rmenu.setUpdateTime(new Date());
            rmenu.setUpdateUser(iamContext.getUser() == null ? "unknown" : iamContext.getUser().getCode());
            roleMenuMapper.insert(rmenu);
        }
    }

    private List<String> sortMenuCode(List<String> menuCode) {
        List<Menu> fullMenu = getMenuList(true, null);
        Map<String, Menu> mmap = new HashMap<>(fullMenu.size());
        fullMenu.forEach(menu -> mmap.put(menu.getCode(), menu));
        List<String> adds = new ArrayList<>();
        menuCode.forEach(code -> checkForParentMenu(adds, mmap, code));
        adds.forEach(m -> {
            if (!menuCode.contains(m)) {
                menuCode.add(m);
            }
        });
        return menuCode;
    }

    private void checkForParentMenu(List<String> adds, Map<String, Menu> mmap, String code) {
        Menu m = mmap.get(code);
        if (m == null) return;
        if (m.getParent() != null) {
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
    public List<Menu> getMenuList() {
        return getMenuList(false, null);
    }

    private List<Menu> getMenuListWithCodes(String owner, PermissionModel type, String group, List<String> menuCodes, String parentId) {
        Example example = getMenuCriteria(menuCodes, group);
        List<cn.org.bachelor.iam.acm.domain.Menu> menus = menuMapper.selectByExample(example);
        return getMenuList(owner, type, menus, parentId);
    }

    private Example getMenuCriteria(List<String> menuCodes, String group) {
        Example example = new Example(cn.org.bachelor.iam.acm.domain.Menu.class);
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

    private List<Menu> getMenuList(String owner, PermissionModel type, List<cn.org.bachelor.iam.acm.domain.Menu> menus, String parentId) {
        return getMenuList(false, menus, owner, type, true, parentId);
    }

    public List<Menu> getMenuList(boolean isFlat, String groupName) {
        Example example = getMenuCriteria(null, groupName);
        List<cn.org.bachelor.iam.acm.domain.Menu> menus = menuMapper.selectByExample(example);
        return getMenuList(isFlat, menus, null, PermissionModel.ROLE, false, null);
    }

    private List<Menu> getMenuList(boolean isFlat, List<cn.org.bachelor.iam.acm.domain.Menu> menus,
                                   String owner, PermissionModel type,
                                   boolean isHas, String parentId) {
        Map<String, Menu> menuMap = new LinkedHashMap<>(menus.size());
        for (cn.org.bachelor.iam.acm.domain.Menu m : menus) {
            Menu menu = toMenu(null, type, isHas, m, null);
            menuMap.put(m.getId(), menu);
        }
        for (cn.org.bachelor.iam.acm.domain.Menu m : menus) {
            Menu parent = null;
            if (menuMap.containsKey(m.getParentId())) {
                parent = menuMap.get(m.getParentId());
            }
            Menu mvo = menuMap.get(m.getId());
            if (parent != null) {
                mvo.setParentId(parent.getId());
                mvo.setParent(parent);
                parent.getSubMenus().add(mvo);
            }
            menuMap.put(m.getId(), mvo);
        }
        List<Menu> result = new ArrayList<>();
        if (isFlat) {
            result.addAll(menuMap.values());
        } else {
            if (parentId != null && !"".equals(parentId)) {
                result.add(menuMap.get(parentId));
            } else {
                for (Menu m : menuMap.values()) {
                    if (m.getParent() == null) {
                        result.add(m);
                    }
                }
            }
        }
        return result;
    }

    private Menu toMenu(String owner, PermissionModel type, boolean has, cn.org.bachelor.iam.acm.domain.Menu m, Menu parent) {
        Menu mv = new Menu(m.getId(), m.getCode(), m.getUri(), m.getIcon(), m.getComment(), type, parent, new ArrayList<>());
        mv.setName(m.getName());
        mv.setParentId(m.getParentId());
        mv.setSeqOrder(m.getSeqOrder());
        mv.setOwner(owner);
        mv.setHidden(m.getHidden());
        mv.setHas(has);
        mv.setGroupName(m.getGroupName());
        return mv;
    }
}
