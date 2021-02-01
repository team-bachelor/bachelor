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
	  refreshToken: {url: 'idm/as/refreshToken', method: 'post' },
	  userPermission: { url: '/acm/permission/user/:userCode', method: 'get' },
	  userMenu: { url: '/acm/user/menu/:userCode', method: 'get' },
	  userRole: { url: '/acm/role/roles/:userCode', method: 'get' },
	  workbenchUrl: { url: '/idm/rs/app/workbench/url', method: 'get' },
	  authorizedApp: { url: '/idm/rs/user/:userId/apps', method: 'get' },
	  // message: createRestful('message'),

	  getOrganizations: { url: '/idm/rs/orgs', method: 'get' },
	  getDepts: { url : '/idm/rs/depts', method: 'get' },
	  getUsers: { url: '/idm/rs/users', method: 'get' },
	  getRoles: { url: '/acm/role/roles', method: 'get' },
	  getRole: { url: '/acm/role/:id', method: 'get' },
	  deleteRole: { url: '/acm/role/:id', method: 'delete' },
	  updateRole: { url: '/acm/role', method: 'put' },
	  createRole: { url: '/acm/role', method: 'post' },
	  getRoleUsers: { url: '/acm/role/users/:roleCode', method: 'get' },
	  putRoleUsers: { url: '/acm/role/users/:roleCode', method: 'put' },
	  postRoleUsers: { url: '/acm/role/users/:roleCode', method: 'post' },
	  getPermissions: { url: '/acm/permission/grouped', method: 'get' },
	  getRolePermissions: { url: '/acm/permission/role/:roleCode', method: 'get' },
	  updateRolePermissions: { url: '/acm/permission/role/:roleCode', method: 'post' },

	  getMenus: { url: '/acm/menus', method: 'get' },
	  getRoleMenu: { url: '/acm/role/menu/:roleCode', method: 'get' },
	  updateRoleMenu: { url: '/acm/role/menu/:roleCode', method: 'post' },
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
