import Layout from '@/layout/smart-port/Index.vue';

const routes = [
  {
    path: '/',
    meta: {
      title: '首页',
    },
    component: Layout,
    children: [
      {
        path: '/',
        name: '',
        meta: {
          title: '欢迎',
        },
        component: () => import('../views/Welcome.vue'),
      },
      {
        path: '/roles',
        name: 'roles',
        meta: {
          title: '角色管理',
        },
        component: () => import('../views/roleManage/Index.vue'),
      },
      {
        path: '/error/401',
        name: '401',
        meta: {
          title: '401没有权限',
          requireAuth: false,
        },
        component: () => import('../views/error/401.vue'),
      },
      {
        path: '/error/404',
        name: '404',
        meta: {
          title: '404没有找到页面',
          requireAuth: false,
        },
        component: () => import('../views/error/404.vue'),
      },
      {
        path: '/subsystem',
        name: 'subsystem',
        meta: {
          title: '子系统列表',
        },
        component: () => import('../views/SubSystemList.vue'),
      },
      {
        path: '/examples',
        name: 'examples',
        requireAuth: false,
        meta: {
          title: '范例',
        },
        component: () => import('../views/examples/Index.vue'),
        children: [{
          path: '/examples/example',
          name: 'examples/example',
          meta: {
            title: 'Example',
          },
          component: () => import('../views/examples/Example.vue'),
        }, {
          path: '/examples/icon',
          name: 'examples/icon',
          meta: {
            title: 'ICON',
          },
          component: () => import('../views/examples/Icon.vue'),
        }, {
          path: '/examples/svg-icon',
          name: 'examples/svg-icon',
          meta: {
            title: 'SVG-ICON',
          },
          component: () => import('../views/examples/SvgIcon.vue'),
        }, {
          path: '/examples/button',
          name: 'examples/button',
          meta: {
            title: 'BUTTON',
          },
          component: () => import('../views/examples/Button.vue'),
        }, {
          path: '/examples/table',
          name: 'examples/table',
          meta: {
            title: 'TABLE',
          },
          component: () => import('../views/examples/Table.vue'),
        }, {
          path: '/examples/message',
          name: 'examples/message',
          meta: {
            title: 'message',
          },
          component: () => import('../views/examples/Message.vue'),
        }, {
          path: '/examples/notify',
          name: 'examples/notify',
          meta: {
            title: 'notify',
          },
          component: () => import('../views/examples/Notification.vue'),
        }, {
          path: '/examples/message-box',
          name: 'examples/message-box',
          meta: {
            title: 'message-box',
          },
          component: () => import('../views/examples/MessageBox.vue'),
        }, {
          path: '/examples/dialog',
          name: 'examples/dialog',
          meta: {
            title: 'dialog',
          },
          component: () => import('../views/examples/Dialog.vue'),
        }, {
          path: '/examples/tabs',
          name: 'examples/tabs',
          meta: {
            title: 'tabs',
          },
          component: () => import('../views/examples/Tabs.vue'),
        }, {
          path: '/examples/scrolltotop',
          name: 'examples/scrolltotop',
          meta: {
            title: 'ScrollToTop',
          },
          component: () => import('../views/examples/ScrollToTop.vue'),
        }, {
          path: '/examples/drag-dialog',
          name: 'examples/drag-dialog',
          meta: {
            title: 'DragDialog',
          },
          component: () => import('../views/examples/DragDialog.vue'),
        }, {
          path: '/examples/form',
          name: 'examples/form',
          meta: {
            title: '表单',
          },
          component: () => import('../views/examples/Form.vue'),
        }],
      },
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
