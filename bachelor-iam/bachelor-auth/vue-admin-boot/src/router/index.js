import { MessageBox, Loading } from 'element-ui';
import store from '@/store';
import axios from '@/service';
import { INIT, CLEAR_TOKEN, SET_TOKEN } from '@/store/types';
import { HOME_PATH, LOGIN_URL, LOGOUT_URL, LOGOUT_URL_API, AUTH_PATH, AUTH_URL, CLIENT_ID } from '@/config';
import { isObject, parseJWT } from '@/util';
import LoadingBar from '@/components/LoadingBar';
import router from './instance';
import updateBreadcrumb from './breadcrumb';
import updateDocumentTitle from './documentTitle';

/**
 * 检查当前路由是否需要授权访问
 */
const checkRequireAuth = to => to.matched.some(record => record.meta.requireAuth !== false);
/**
 * 显示隐藏全局加载提示
 */
const loading = (() => {
  let loadingInstance = null;
  return {
    show(text) {
      loadingInstance = Loading.service({
        lock: true,
        text,
      });
    },
    close() {
      loadingInstance.close();
    },
  };
})();
/**
 * 显示错误提示对话框
 */
const showError = (text) => {
  MessageBox.confirm(text || '登录失败!', {
    type: 'error',
    showClose: false,
    closeOnClickModal: false,
    closeOnPressEscape: false,
    confirmButtonText: '重新登录',
    cancelButtonText: '重试',
  }).then(() => {
    router.logout();
  }).catch(() => {
    window.location.reload();
  });
};
/**
 * 凭code获取accesstoken
 */
const fetchAccessToken = async (code) => {
  try {
    const { data } = await axios.get(AUTH_URL, {
      params: { code },
    });
    const { token } = data.data;
    const userinfo = parseJWT(token);
    if (userinfo) {
      store.commit(SET_TOKEN, {
        token,
        userinfo,
      });
      return Promise.resolve(true);
    }
    throw new Error();
  } catch (e) {
    return Promise.resolve(false);
  }
};
/**
 * 重定向去登录
 */
router.login = (state, next) => {
  store.commit(CLEAR_TOKEN);
  const queryData = {
    client_id: CLIENT_ID,
    response_type: 'code',
    state: encodeURIComponent(state),
    redirect_uri: encodeURIComponent(`${window.location.origin}${window.location.pathname}#${AUTH_PATH}`),
  };
  const query = Object.keys(queryData).map(k => `${k}=${queryData[k]}`).join('&');
  const loginUrl = `${LOGIN_URL}${LOGIN_URL.indexOf('?') < 0 ? '?' : '&'}${query}`;
  if (/^http/.test(loginUrl)) window.location = loginUrl;
  else if (next) next(loginUrl);
  else router.push(loginUrl);
};
/**
 * 退出登录
 * 清除用户凭证并重定向去统一用户平台退出
 */
router.logout = () => {
  const href = window.location.href.replace(window.location.hash, '');
  const logoutUri = `${LOGOUT_URL}?${encodeURIComponent(href)}`;
  if (!store.state.token) {
    if (/^http/.test(logoutUri)) window.location = logoutUri;
    else router.push(logoutUri);
  }
  store.commit(CLEAR_TOKEN);
  loading.show('正在退出...');
  axios.put(LOGOUT_URL_API).finally(() => {
    if (/^http/.test(logoutUri)) window.location = logoutUri;
    else router.push(logoutUri);
  });
};
/**
 * 路由临时数据
 */
const { push } = router;
const tempData = {
  breadcrumb: null,
  documentTitle: null,
};
router.push = (location, onComplete, onAbort) => {
  if (isObject(location)) {
    Object.keys(location).forEach((key) => {
      tempData[key] = location[key] || null;
    });
  }
  push.call(router, location, onComplete, onAbort);
};
/**
 * 路由前置守卫
 */
router.beforeEach(async (to, from, next) => {
  /**
   * 检验是否登录回调中
   */
  if (to.path === AUTH_PATH) {
    if (!to.query.code) {
      next(to.query.state || HOME_PATH);
      return;
    }
    loading.show('登录中');
    const done = await fetchAccessToken(to.query.code);
    if (done) {
      // router.replace(to.query.state || HOME_PATH);
      next(to.query.state || HOME_PATH);
    } else {
      loading.close();
      showError('登录失败!');
    }
    return;
  }
  LoadingBar.start();
  if (checkRequireAuth(to)) {
    if (!store.state.token) {
      router.login(to.fullPath, next);
      return;
    }
    if (!store.state.inited) {
      loading.show('加载中');
      try {
        await store.dispatch(INIT);
        loading.close();
      } catch (e) {
        loading.close();
        showError(`服务初始失败！${e.message}`);
        return;
      }
    }
  }
  next();
  if (tempData.breadcrumb) {
    updateBreadcrumb(to, tempData.breadcrumb);
    tempData.breadcrumb = null;
  } else {
    updateBreadcrumb(to);
  }
  if (tempData.documentTitle) {
    updateDocumentTitle(tempData.documentTitle);
    tempData.documentTitle = null;
  } else {
    updateDocumentTitle(to);
  }
});
router.afterEach(() => {
  LoadingBar.finish();
});
export default router;
