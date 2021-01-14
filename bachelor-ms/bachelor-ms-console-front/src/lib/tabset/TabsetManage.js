import store from '@/store';
import router from '@/router';
import { isString, isArray } from '@/util';
import {
  ADD,
  REMOVE,
  FULL_SCREEN,
} from './store';

function getSiblingKey(keys, currentKey) {
  const index = keys.indexOf(currentKey);
  return index < keys.length - 1 ? keys[index + 1] : keys[index - 1];
}

function pruneCacheEntry(cache, key, current) {
  const cached$$1 = cache[key];
  if (cached$$1 && (!current || cached$$1.tag !== current.tag)) {
    cached$$1.componentInstance.$destroy();
  }
  cache[key] = null;
}

class TabsetBase {
  replaceData = {}
  cache = {}
  add(key, vnode) {
    const { currentRoute } = router;
    store.dispatch(ADD, {
      key,
      item: {
        path: currentRoute.path,
        fullPath: currentRoute.fullPath,
        name: currentRoute.name,
        title: currentRoute.meta.title || currentRoute.name,
      },
    });
    this.cache[key] = vnode;
  }
  remove(key) {
    store.commit(REMOVE, key);
    this.cache[key] = null;
  }
  checkReplace(key) {
    const { replaceData } = this;
    if (replaceData && replaceData.replace) {
      const oldKey = replaceData.key;
      if (oldKey !== key) {
        pruneCacheEntry(this.cache, oldKey);
        this.remove(oldKey);
      }
      this.replaceData = {};
    }
  }
  findKey(key) {
    const { items } = store.state.tabset;
    if (isString(key)) {
      if (items[key]) return key;
      const index = Object.keys(items).find(k => items[k].path === key);
      if (index) return index;
    }
    return null;
  }
  destroyed() {
    Object.keys(this.cache).forEach(key => pruneCacheEntry(this.cache, key));
  }
}
export default class Tabset extends TabsetBase {
  constructor() {
    const { replace } = router;
    router.replace = (location, onComplete, onAbort) => {
      if (router.currentRoute.path !== location.path) {
        this.replaceData = {
          replace: true,
          key: store.state.tabset.key,
        };
      }
      replace.call(router, location, onComplete, onAbort);
    };
    // 当关闭标签同时清除对应的cache
    store.subscribe((mutation) => {
      if (mutation.type === REMOVE) {
        pruneCacheEntry(this.cache, mutation.payload);
      }
    });
    super();
  }
  push(path) {
    router.push(path);
  }
  replace(path) {
    router.replace(path);
  }
  close(path) {
    if (isArray(path)) {
      path.forEach(v => this.close(v));
      return;
    }
    const { items, key: activeKey } = store.state.tabset;
    let removeKey = '';
    const itemKeys = Object.keys(items);
    if (itemKeys.length <= 1) return;
    if (!path) {
      removeKey = activeKey;
    } else {
      removeKey = this.findKey(path);
    }
    if (!removeKey) return;
    if (removeKey === activeKey) {
      const nextKey = getSiblingKey(itemKeys, removeKey);
      router.push(items[nextKey].fullPath);
    }
    this.remove(removeKey);
  }
  closeAll() {
    const { items, key } = store.state.tabset;
    const removeItems = Object.keys(items).filter(k => k !== key);
    this.close(removeItems);
  }
  get(path) {
    const { items, key: activeKey } = store.state.tabset;
    const key = path ? this.findKey(path) : activeKey;
    return key ? items[key] : null;
  }
  getAll() {
    return store.state.tabset.items;
  }
  fullScreen(e) {
    store.commit(FULL_SCREEN, e);
  }
}
