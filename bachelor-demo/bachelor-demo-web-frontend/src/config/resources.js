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

const getInterface = () => {
  const inter = {
	  login: { url: '/login', method: 'post' },
	  userinfo: { url: '/user/show', method: 'get' },
	  refreshToken: {url: 'user/refreshToken', method: 'post' },
	  userPermission: { url: '/user_permission/:userCode', method: 'get' },
	  userMenu: { url: '/user_menu/:userCode', method: 'get' },
	  userRole: { url: '/roles/:userCode', method: 'get' },
	  workbenchUrl: { url: '/user_sys/app/workbench/url', method: 'get' },
	  authorizedApp: { url: '/user_sys/user/:userId/apps', method: 'get' },
	  // message: createRestful('message'),

	  getOrganizations: { url: '/user_sys/orgs', method: 'get' },
	  getDepts: { url : '/user_sys/depts', method: 'get' },
	  getUsers: { url: '/user_sys/users', method: 'get' },
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
	  userTestAll: { url: '/userinfo/user', method: 'get' },
	  userTestOne: { url: '/userinfo/user/:id', method: 'get' },
	  userTestDel: { url: '/userinfo/user/:id', method: 'delete' },
	  userTestAdd: { url: '/userinfo/user', method: 'post' },
	  userTestEdit: { url: '/userinfo/user', method: 'put' },
	  
	  // 通讯录
	  contactParams: { url: '/contact/params', method: 'post' },
	  delContacts: { url: '/contact/:id', method: 'delete' },
	  getContacts: { url: '/contact/:id', method: 'get' },
	  addContacts: { url: '/contact', method: 'post' },
	  updateContacts: { url: '/contact', method: 'put' },
	  // 通讯录组织机构
	  orgParams: { url: '/contact/organization/params', method: 'post' },
	  delOrg: { url: '/contact/organization/:id', method: 'delete' },
	  getOrg: { url: '/contact/organization/:id', method: 'get' },
	  addOrg: { url: '/contact/organization', method: 'post' },
	  updateOrg: { url: '/contact/organization', method: 'put' },
		
	  orgTree: { url: '/contact/organization/findOrganizationTree', method: 'get' },
	  
	  //批量导入
	  importBookExcel: { url: '/import/importBookExcel', method: 'post' },
	  
	  // 菜单
	  menuTree: { url: '/menu/menuTree', method: 'get' },
	  addMenu: { url: '/menu', method: 'post' },
	  upMenu: { url: '/menu/update', method: 'put' },
	  delMenu: { url: '/menu/:id', method: 'delete' },
	};
  return inter;
};
const api = getInterface();
export default api
