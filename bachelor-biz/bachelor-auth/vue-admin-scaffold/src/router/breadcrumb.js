import store from '@/store';
import { REPLACE } from '@/store/modules/breadcrumb';
import { isString, isObject, isArray } from '../util';
/**
 * 更新面包屑
 * @param {Object} route
 */
const updateBreadcrumb = (route, lastRoute) => {
  let breadcrumb = [];
  route.matched.forEach((record) => {
    const crumb = {
      path: record.path || record.meta.original,
      name: record.name,
      title: record.meta.title || record.name || record.path,
    };
    Object.keys(route.params).forEach((param) => {
      crumb.path = crumb.path.replace(`:${param}`, route.params[param]);
    }, this);
    breadcrumb.push(crumb);
  });
  if (isString(lastRoute)) {
    breadcrumb[breadcrumb.length - 1] = Object.assign(breadcrumb[breadcrumb.length - 1], {
      title: lastRoute,
    });
  } else if (isObject(lastRoute)) {
    breadcrumb[breadcrumb.length - 1] = lastRoute;
  } else if (isArray(lastRoute)) {
    breadcrumb = lastRoute;
  }
  store.commit(REPLACE, breadcrumb);
};

export default updateBreadcrumb;
