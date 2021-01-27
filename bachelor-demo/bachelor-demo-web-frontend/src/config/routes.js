import Layout from '@/layout/default/Index.vue';

const routes = [
	{path: '/', redirect: "/home"},
  {
    path: '/home',
    name: 'home',
    component: () => import('../views/Home.vue'),
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
];

export default routes;
