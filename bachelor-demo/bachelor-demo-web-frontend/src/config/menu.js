export default [{
  title: '控制台1',
  path: '/roles',
  icon: 'el-icon-mobile-phone',
  // code: 'menu.home', // 权限code
}, {
  title: '角色管理',
  path: '/roles',
  icon: 'fa fa-user-circle-o',
  // code: 'menu.role', // 权限code
}, {
  title: '范例',
  path: '/examples',
  icon: 'el-icon-menu',
  children: [{
    title: '我的demo',
    path: '/mydemo',
  }, {
    title: '一级菜单2',
    path: '/a',
    children: [{
      title: '二级菜单1',
      path: '/a/a',
    }],
  }, {
    title: '一级菜单3',
    path: '/b',
    children: [{
      title: '二级菜单1',
      path: '/b/a',
      children: [{
        title: '三级菜单',
        path: '/b/a/a',
      }],
    }, {
      title: '二级菜单2',
      path: '/b/b',
      children: [{
        title: '三级菜单',
        path: '/b/b/a',
      }],
    }, {
      title: '二级菜单3',
      path: '/b/c',
    }],
  }],
}];
