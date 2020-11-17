import { isFunction, isArray, isObject } from '@/util';
import store from '@/store';
import router from './instance';

const checkRequireAuth = to => to.matched.some(record => record.meta.requireAuth === true);
const recuFind = (data, key, val) => {
  if (isArray(data)) {
    const final = data.some(item => recuFind(item, key, val));
    if (final) return true;
  } else if (isObject(data)) {
    if (data[key] && data[key] === val) return true;
    else if (data.children && data.children.length) {
      return recuFind(data.children, key, val);
    }
  }
  return false;
};

/**
 * 检查当前路由是否需要授权访问
 */
export default function checkPermission(to) {
  if (!checkRequireAuth(to)) return true;
  const { menu, permission } = store.state;
  if (recuFind(menu, 'path', to.path)) return true; // 判断是否menu里有该项值
  if (to.meta && to.meta.code) { // 判断路由是否有配置code
    if (isFunction(to.meta.code)) {
      if (to.meta.code(to)) return true;
    } else if (permission.some(to.meta.code)) return true;
  }
  router.push('/401');
  return false;
}
