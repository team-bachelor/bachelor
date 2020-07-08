import Vue from 'vue';

import {
  SET_ATTR,
  CLEAR_TOKEN,
  SET_TOKEN,
  TOGGLE_SIDEBAR,
  TOGGLE_COMMON_VIEW,
} from './types';

export default {
  [SET_ATTR](state, payload) {
    Object.keys(payload).forEach((k) => {
      Vue.set(state, k, payload[k]);
    });
  },
  /**
   * 清除用户凭证
   */
  [CLEAR_TOKEN](state) {
    state.token = '';
    state.userinfo = {};
  },
  /**
   * 设置用户凭证
   */
  [SET_TOKEN](state, payload) {
    state.token = payload.token;
    state.userinfo = payload.userinfo;
  },
  /**
   * 切换(关闭)公共视图
   */
  [TOGGLE_COMMON_VIEW](state, e) {
    let key = e.charAt(0).toUpperCase() + e.slice(1);
    key = state.commonView === key ? '' : key;
    state.commonView = key;
  },
  [TOGGLE_SIDEBAR](state) {
    state.sidebar.opened = !state.sidebar.opened;
  },
};
