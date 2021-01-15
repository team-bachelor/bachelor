import axios from 'axios';

const namespace = 'permission/';
export const LOADING = `${namespace}LOADING`;
export const MERGE = `${namespace}MERGE`;
export const FETCH = `${namespace}FETCH`;

export default {
  state: {
    actions: {},
    loading: false,
  },
  getters: {},
  mutations: {
    [LOADING](state, payload) {
      state.loading = payload;
    },
    [MERGE](state, payload) {
      state.actions = Object.assign(state.actions, payload);
    },
  },
  actions: {
    [FETCH]({ commit }) {
      return new Promise((resolve, reject) => {
        axios.get('/user/permission').then(({ data }) => {
          if (data.code) {
            reject(data);
            return;
          }
          commit(MERGE, data.data);
          resolve();
        }, () => {});
      });
    },
  },
};
