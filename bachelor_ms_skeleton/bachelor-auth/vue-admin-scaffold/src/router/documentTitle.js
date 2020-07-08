import { DEFAULT_TITLE } from '@/config';
import { isString } from '../util';

/**
 * 更新文档标题
 * @param {Object|String} route
 */
const updateDocumentTitle = (route) => {
  if (isString(route)) {
    document.title = route;
    return;
  }
  let title = DEFAULT_TITLE;
  if (route.meta && route.meta.title) {
    ({ title } = route.meta);
  } else if (route.matched && route.matched.length) {
    let i = route.matched.length - 1;
    while (i > 0) {
      i -= 1;
      if (route.matched[i].meta && route.matched[i].meta.title) {
        ({ title } = route.matched[i].meta);
        break;
      }
    }
  }
  document.title = title;
};

export default updateDocumentTitle;
