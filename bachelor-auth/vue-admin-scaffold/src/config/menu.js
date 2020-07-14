const mainMenu = [{
  title: '服务管理',
  path: '/service',
  icon: 'el-icon-tickets',
  // code: 'menu.service',
}, {
  title: '配置中心',
  path: '/config',
  icon: 'el-icon-tickets',
}, {
  title: '统一网关',
  path: '/gateway',
  icon: 'el-icon-tickets',
}, {
  title: '角色管理',
  path: '/roles',
  icon: 'fa fa-user-circle-o',
}, {
  title: '监控中心',
  path: '/monitor',
  icon: 'el-icon-menu',
  children: [{
    title: '服务跟踪',
    path: '/monitor/service',
    icon: 'el-icon-tickets',
  }, {
    title: '日志跟踪',
    path: '/monitor/log',
    icon: 'el-icon-tickets',
  }, {
    title: '基础监控',
    path: '/monitor/base',
    icon: 'el-icon-tickets',
  }, {
    title: '容错情况',
    path: '/monitor/turbine',
    icon: 'el-icon-tickets',
  }],
}];

// const exampleMMenu = {
//   title: '范例',
//   path: '/examples',
//   icon: 'el-icon-menu',
//   children: [{
//     title: '图标',
//     path: '/examples/icon',
//     icon: 'el-icon-date',
//   }, {
//     title: '按钮',
//     path: '/examples/button',
//     icon: 'el-icon-date',
//   }, {
//     title: '消息提示',
//     path: '/examples/message',
//     icon: 'el-icon-date',
//   }, {
//     title: '消息通知',
//     path: '/examples/notify',
//     icon: 'el-icon-date',
//   }, {
//     title: '弹窗',
//     path: '/examples/message-box',
//     icon: 'el-icon-date',
//   }, {
//     title: '对话框',
//     path: '/examples/dialog',
//     icon: 'el-icon-date',
//   }, {
//     title: '滚动到顶部',
//     path: '/examples/scrolltotop',
//     icon: 'el-icon-date',
//   }, {
//     title: '可拖动对话框',
//     path: '/examples/drag-dialog',
//     icon: 'el-icon-date',
//   }, {
//     title: '标签页',
//     path: '/examples/tabs',
//     icon: 'el-icon-date',
//   }, {
//     title: '表单',
//     path: '/examples/form',
//     icon: 'el-icon-date',
//   }],
// };
// export default [...mainMenu, exampleMMenu];
export default mainMenu;
