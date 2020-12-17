import createRestful from '../util/createRestful';
/**
 * 配置
 * getUsers: { url: '/users', method: 'get', params: { pageSize: 10 } },
 * getUser: { url: '/user/:id', method: 'get' },
 * addUser: { url: '/user', method: 'post' },
 * deleteUser: { url: '/user/:id', method: 'delete' },
 * putUser: { url: '/user/:id', method: 'put' },
 *
 * 使用范例
 * api.getUsers({ pageNo: 0, pageSize: 10 })
 * api.getUser({ id: 10 })
 * api.addUser({ username: '雄黎明', ... })
 * api.deleteUser({ id: 10 })
 * api.putUser({ id: 10, username: '雄黎明', ... })
 */
export default {
  login: { url: '/login', method: 'post' },
  userinfo: { url: '/user/show', method: 'get' },
  refreshToken: { url: 'user/refreshToken', method: 'post' },
  userPermission: { url: '/user_permission/:userCode', method: 'get' },
  userMenu: { url: '/user_menu/:userCode', method: 'get' },
  userRole: { url: '/roles/:userCode', method: 'get' },
  workbenchUrl: { url: '/user_sys/app/workbench/url', method: 'get' },
  authorizedApp: { url: '/user_sys/user/:userId/apps', method: 'get' },
  message: createRestful('message'),

  getOrganizations: { url: '/user_sys/orgs', method: 'get' },
  getDepts: { url: '/user_sys/depts', method: 'get' },
  // getUsers: { url: '/user_sys/users/:orgId', method: 'get' },
  getUsers: { url: '/users', method: 'get' },
  getRoles: { url: '/roles', method: 'get' },
  getRole: { url: '/role/:id', method: 'get' },
  deleteRole: { url: '/role/:id', method: 'delete' },
  updateRole: { url: '/role', method: 'put' },
  createRole: { url: '/role', method: 'post' },
  getRoleUsers: { url: '/role_user/:roleCode', method: 'get' },
  putRoleUsers: { url: '/role_user/:roleCode', method: 'put' },
  postRoleUsers: { url: '/role_user/:roleCode', method: 'post' },
  getPermissions: { url: '/permissions', method: 'get' },
  getRolePermissions: { url: '/role_permission/:roleCode', method: 'get' },
  updateRolePermissions: { url: '/role_permission/:roleCode', method: 'post' },

  getMenus: { url: '/menus', method: 'get' },
  getRoleMenu: { url: '/role_menu/:roleCode', method: 'get' },
  updateRoleMenu: { url: '/role_menu/:roleCode', method: 'post' },
};
