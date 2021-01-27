import Vue from 'vue';
import axios from 'axios';
import { Message, MessageBox, Notification } from 'element-ui';
import store from '../store';
import router from '../router';
import {
  API_HOST,
  API_BASE_URL,
  API_REQUEST_TIMEOUT,
  AUTH_KEY,
} from '../config';

// import './mock'; // mock 数据

axios.defaults.baseURL = API_HOST + API_BASE_URL;
axios.defaults.timeout = API_REQUEST_TIMEOUT;
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
axios.defaults.headers.common[AUTH_KEY] = store.state.token;

/**
 * request 拦截
 */
axios.interceptors.request.use((config) => {
  if (!config.headers[AUTH_KEY] && store.state.token) {
    axios.defaults.headers.common[AUTH_KEY] = store.state.token;
    config.headers[AUTH_KEY] = store.state.token;
  }
  return config;
}, (error) => {
  Message.error({
    message: '加载超时',
  });
  Promise.reject(error);
});
/**
 * respone 拦截
 */
const responseStatusHandler = {
  // 400: () => {
  //   MessageBox.alert('身份验证过期！', {
  //     type: 'error',
  //     closeOnPressEscape: false,
  //     confirmButtonText: '重新登录',
  //     callback: () => {
  //       router.logout();
  //     },
  //   });
  // },
  401: () => {
    MessageBox.alert('接口没有权限访问', {
      type: 'error',
      closeOnPressEscape: false,
      confirmButtonText: '重新登录',
    }).then(() => {
      router.logout();
    });
  },
  402: () => {
    router.logout();
  },
  403: () => {
    router.logout();
  },
  404: () => {
    Notification.error({
      title: '错误',
      message: '当前应用不支持这个功能',
    });
  },
};
axios.interceptors.response.use(
  response => response,
  (error) => { // 默认除了2XX之外的都是错误的，就会走这里
    if (error.response) {
      const { status } = error.response;
      const handlerFunc = responseStatusHandler[status];
      if (handlerFunc) handlerFunc();
      else {
        Message.error({
          title: error.status,
          message: error.response.data.msg || error.response.data.message || error.message,
        });
      }
    }
    return Promise.reject(error);
  },
);

Vue.prototype.$http = axios;
export default axios;
