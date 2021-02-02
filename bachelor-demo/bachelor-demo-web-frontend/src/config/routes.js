import Layout from '@/layout/default/Index.vue';

const routes = [
	{path: '/', redirect: "/parkPhoneBook"},
  {
    path: '/home',
    name: '一张图',
    component: () => import('../views/Home.vue'),
  },
  {
    path: '/parkPhoneBook',
	alias: '/',
    meta: {
      title: '通讯录管理',
    },
    component: Layout,
    children: [
      {
        path: '/userRight',
        meta: {
          title: '角色管理',
        },
        component: () => import('../views/roleManage/Index.vue'),
      },
	  {
	    path: '/parkPhoneBook',
	    meta: {
	      title: '园区通讯录',
	    },
	    component: () => import('../views/contacts/contactsList.vue'),
	  },
	  {
	    path: '/orgPhoneBook',
	    meta: {
	      title: '组织机构',
	    },
	    component: () => import('../views/contacts/orgList.vue'),
	  },
      // {
      //   path: '/mydemo',
      //   name: '我的demo',
      //   meta: {
      //     title: '我的demo',
      //   },
      //   component: () => import('../views/home/Index.vue'),
      // },
      // {
      //   path: '/a',
      //   name: '一级菜单2',
      //   meta: {
      //     title: '一级菜单2',
      //   },
      //   component: () => import('../views/home/Index.vue'),
      //   children: [{
      //     path: '/a/a',
      //     name: '二级菜单1',
      //     meta: {
      //       title: '二级菜单1',
      //     },
      //     component: () => import('../views/home/Index.vue'),
      //   }],
      // },
      // {
      //   path: '/b',
      //   name: '一级菜单3',
      //   meta: {
      //     title: '一级菜单3',
      //   },
      //   component: () => import('../views/home/Index.vue'),
      //   children: [{
      //     path: '/b/a',
      //     name: '二级菜单1',
      //     meta: {
      //       title: '二级菜单1',
      //     },
      //     component: () => import('../views/home/Index.vue'),
      //     children: [{
      //       path: '/b/a/a',
      //       name: '三级菜单',
      //       meta: {
      //         title: '三级菜单',
      //       },
      //       component: () => import('../views/home/Index.vue'),
      //     }],
      //   }, {
      //     path: '/b/b',
      //     name: '二级菜单2',
      //     meta: {
      //       title: '二级菜单2',
      //     },
      //     component: () => import('../views/home/Index.vue'),
      //     children: [{
      //       path: '/b/b/a',
      //       name: '三级菜单',
      //       meta: {
      //         title: '三级菜单',
      //       },
      //       component: () => import('../views/home/Index.vue'),
      //     }],
      //   }, {
      //     path: '/b/c',
      //     name: '二级菜单3',
      //     meta: {
      //       title: '二级菜单3',
      //     },
      //     component: () => import('../views/home/Index.vue'),
      //   }],
      // },
    ],
  },
  /**
   * 此/login路由用于模拟登录
   */
  {
    path: '/login',
    name: 'login',
    meta: {
      title: '登录',
      requireAuth: false,
    },
    component: () => import('../views/Login.vue'),
  },
  {
    path: '/401',
    name: '401',
    meta: {
      title: '401没有权限',
      requireAuth: false,
    },
    component: () => import('../views/error/401.vue'),
  },
  {
    path: '*',
    name: '404',
    meta: {
      title: '404没有找到页面',
      requireAuth: false,
    },
    component: () => import('../views/error/404.vue'),
  },
];

export default routes;
