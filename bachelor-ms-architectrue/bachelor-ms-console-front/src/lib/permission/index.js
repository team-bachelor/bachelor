import permission from './permission';


export default function plugin(Vue, store) {
  if (plugin.installed) return;
  plugin.installed = true;

  const Permission = permission(store);
  /**
   * 指令
   */
  Vue.directive('permission', (el, binding) => {
    const strict = !(binding.modifiers && binding.modifiers.some);
    if (!Permission.check(binding.value, strict)) el.style.display = 'none';
    else el.style.display = '';
  });
  /**
   * 注册permission Store
   */
  // store.registerModule('permission', permissionStore);
  /**
   * 请求权限
   */
  // router.beforeResolve((to, from, next) => {
  //   if (!config.url) return;
  //   let actions = [];
  //   // 提取权限code
  //   to.matched.forEach((route) => {
  //     actions.push(...(route.components.default.permission || []));
  //     actions.push(...(route.meta.permission || []));
  //   });
  //   const currentActions = store.state.permission.actions;
  //   actions = actions.filter(action => typeof currentActions[action] === 'undefined');
  //   if (actions.length) {
  //     const obj = {};
  //     actions.forEach((v) => {
  //       obj[v] = null;
  //     });
  //     store.commit(MERGE, obj);
  //     store.commit(LOADING, true);
  //     axios.post(config.url, { actions }).then(({ data }) => {
  //       if (data.code === 0) {
  //         store.commit(MERGE, data.data);
  //       } else {
  //         Message.error('权限加载失败！');
  //       }
  //       store.commit(LOADING, false);
  //     }).catch(() => {
  //       store.commit(LOADING, false);
  //       Message.error('权限加载失败！');
  //     });
  //   }
  //   next();
  // });
  Vue.Permission = Permission;
  Object.defineProperties(Vue.prototype, {
    $permission: {
      get() {
        return Permission;
      },
    },
  });
}
