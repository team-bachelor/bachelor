import Vue from 'vue';

const namespace = 'tabset/';
export const REMOVE = `${namespace}REMOVE`;
export const ADD = `${namespace}ADD`;
export const REPLACE = `${namespace}REPLACE`;
export const SET_KEY = `${namespace}SET_KEY`;
export const FULL_SCREEN = `${namespace}FULL_SCREEN`;
export const TOGGLE_FULL_SCREEN = `${namespace}TOGGLE_FULL_SCREEN`;

export default {
  state: {
    items: {},
    key: '',
    fullscreen: false,
  },
  getters: {},
  mutations: {
    [ADD](state, payload) {
      Vue.set(state.items, payload.key, payload.item);
    },
    [REMOVE](state, payload) {
      Vue.delete(state.items, payload);
    },
    [REPLACE](state, payload) {
      Vue.delete(state.items, payload.oldKey);
      Vue.set(state.items, payload.key, payload.item);
    },
    [SET_KEY](state, payload) {
      state.key = payload;
    },
    [FULL_SCREEN](state, payload) {
      state.fullscreen = payload;
    },
  },
  actions: {
    [ADD]({ commit }, payload) {
      commit(ADD, payload);
      commit(SET_KEY, payload.key);
    },
    [TOGGLE_FULL_SCREEN]({ state, commit }) {
      commit(FULL_SCREEN, !state.fullscreen);
    },
  },
};
