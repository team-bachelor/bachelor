import { isString, isArray, isFunction } from '@/util';

export default store => ({
  check(action, strict = true) {
    if (isString(action)) {
      return store.state.permission.indexOf(action) >= 0;
    } if (isFunction(action)) {
      return this.check(action(), strict);
    } else if (isArray(action)) {
      return action[strict ? 'every' : 'some'](code => store.state.permission.indexOf(code) >= 0);
    }
    return false;
  },
  every(actions) {
    return this.check(actions, true);
  },
  some(actions) {
    return this.check(actions, false);
  },
  getAll() {
    return store.state.permission;
  },
});
