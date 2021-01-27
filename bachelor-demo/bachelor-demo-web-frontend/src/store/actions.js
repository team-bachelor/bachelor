import $api from '../service/api';
import defaultMenu from '../config/menu';
import {
  SET_ATTR,
  INIT,
  FETCH_USERINFO,
  FETCH_PERMISSION,
  FETCH_USERROLE,
  FETCH_MENU,
  SETTING_MENU,
} from './types';
import { MENU_GET_MODE } from '../config';

export default {
  [INIT]({ commit, dispatch }) {
    return Promise.all([
      // 已从jwt中读取
      // dispatch(FETCH_USERINFO),
      // 加载权限
      dispatch(FETCH_PERMISSION),
      // 加载角色信息
      dispatch(FETCH_USERROLE),
      // 加载并设置主菜单
      dispatch(FETCH_MENU),
    ]).then(() => {
      commit(SET_ATTR, {
        inited: true,
      });
    });
  },
  /**
   * 获取用户信息
   */
  [FETCH_USERINFO]({ commit }) {
    return new Promise((resolve, reject) => {
      $api.userinfo().then(({ data }) => {
        if (data.status !== 'OK') {
          reject(data);
          return;
        }
        commit(SET_ATTR, {
          userinfo: data.data,
        });
        resolve();
      }, reject);
    });
  },
  /**
   * 获取用户权限
   */
  [FETCH_PERMISSION]({ state, commit }) {
    return new Promise((resolve, reject) => {
      $api.userPermission({
        userCode: state.userinfo.user_code,
      }).then(({ data }) => {
        if (data.status !== 'OK') {
          reject(data);
          return;
        }
        const permission = data.data.map(v => v.objCode);
        commit(SET_ATTR, {
          permission: [...state.permission, ...permission],
        });
        resolve();
      }, reject);
    });
  },
  /**
   * 获取用户角色信息
   */
  async [FETCH_USERROLE]({ state, commit }) {
    try {
      const { data } = await $api.userRole({
        userCode: state.userinfo.user_code,
      });
      if (data && data.status === 'OK') {
        commit(SET_ATTR, {
          userinfo: Object.assign({}, state.userinfo, {
            role: data.data,
          }),
          permission: [...state.permission, ...data.data],
        });
        return Promise.resolve();
      }
      throw new Error('invlid');
    } catch (e) {
      return Promise.reject(e);
    }
  },
  /**
   * 获取用户菜单
   * 其方法和[SETTING_MENU]选其一
   */
  [FETCH_MENU]({ commit, state }) {
    return new Promise((resolve, reject) => {
      $api.userMenu({
        userCode: state.userinfo.user_code,
      }).then(({ data }) => {
        if (data.status !== 'OK') {
          reject(data);
          return;
        }
        const recursionMenu = menus => menus.map((item) => {
          if (item.subMenus && item.subMenus.length) {
            item.children = recursionMenu(item.subMenus);
          }
          return Object.assign(item, {
            title: item.title || item.comment,
            path: item.path || item.uri,
          });
        });
        const menu = recursionMenu(data.data);
        commit(SET_ATTR, {
          menu,
        });
        resolve();
      }, reject);
    });
  },
  /**
   * 设置用户菜单
   * 其方法和[FETCH_MENU]选其一
   */
  [SETTING_MENU]({ commit, state, dispatch }) {
    if (MENU_GET_MODE === 'remote') {
      return dispatch(FETCH_MENU);
    }
    const recursionMenu = (data) => {
      let some = false;
      const newData = [];
      data.forEach((item) => {
        if (item.children && item.children.length) {
          const childrens = recursionMenu(item.children);
          if (childrens) {
            item.children = childrens;
            some = true;
            newData.push(item);
          }
        } else if (!item.code || state.permission.indexOf(item.code) >= 0) {
          some = true;
          newData.push(item);
        }
      });
      if (some) return newData;
      return null;
    };
    const menu = recursionMenu(defaultMenu);
    commit(SET_ATTR, {
      menu,
    });
    return Promise.resolve();
  },
};
