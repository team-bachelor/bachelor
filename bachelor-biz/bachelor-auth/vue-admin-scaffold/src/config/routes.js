import Layout from '@/layout/default/Index.vue';

const routes = [
  {
    path: '/',
    meta: {
      title: '控制台',
      requireAuth: false,
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
        path: '/service',
        name: 'service',
        meta: {
          title: '服务管理',
        },
        component: () => import('../views/Service.vue'),
      },
      {
        path: '/config',
        name: 'config',
        meta: {
          title: '配置中心',
        },
        component: () => import('../views/Star.vue'),
        children: [{
          path: '/config',
          name: 'config/index',
          meta: {
            title: '配置中心',
          },
          component: () => import('../views/config/Index.vue'),
        }, {
          path: '/config/log/:fileName',
          name: 'config/log',
          meta: {
            title: '日志',
          },
          component: () => import('../views/config/Log.vue'),
        }, {
          path: '/config/log/:fileName/content/:commitId',
          name: 'config/log/content',
          meta: {
            title: '查看内容',
          },
          component: () => import('../views/config/Content.vue'),
        }, {
          path: '/config/log/:fileName/diff/:commitId',
          name: 'config/log/diff',
          meta: {
            title: '版本对比',
          },
          component: () => import('../views/config/Diff.vue'),
        }],
      },
      {
        path: '/gateway',
        name: 'gateway',
        meta: {
          title: '统一网关',
          requireAuth: false,
        },
        component: () => import('../views/Star.vue'),
        children: [{
          path: '/gateway',
          name: 'gateway/index',
          meta: {
            title: '统一网关',
            requireAuth: false,
          },
          component: () => import('../views/Gateway.vue'),
        },
        {
          path: '/gateway/limit',
          name: 'gateway/limit',
          meta: {
            title: '限制修改',
            requireAuth: false,
          },
          component: () => import('../views/GatewayLimit.vue'),
        }],
      },
      {
        path: '/monitor',
        name: 'monitor',
        meta: {
          title: '监控中心',
          requireAuth: false,
        },
        component: () => import('../views/Star.vue'),
        children: [{
          path: '/monitor/base',
          name: 'monitor/base',
          meta: {
            title: '基础监控',
          },
          component: () => import('../views/monitor/Base.vue'),
        }, {
          path: '/monitor/log',
          name: 'monitor/log',
          meta: {
            title: '基础监控',
          },
          component: () => import('../views/monitor/Log.vue'),
        }, {
          path: '/monitor/service',
          name: 'monitor/service',
          meta: {
            title: '服务跟踪',
          },
          component: () => import('../views/monitor/Service.vue'),
        }, {
          path: '/monitor/turbine',
          name: 'monitor/turbine',
          meta: {
            title: '容错情况',
          },
          component: () => import('../views/monitor/Turbine.vue'),
        }],
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
