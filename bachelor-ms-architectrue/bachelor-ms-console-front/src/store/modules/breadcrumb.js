const namespace = 'breadcrumb/';
export const REPLACE = `${namespace}REPLACE`;

export default {
  state: {
    list: [],
  },
  mutations: {
    [REPLACE](state, payload) {
      state.list = payload;
    },
  },
  actions: {},
};
