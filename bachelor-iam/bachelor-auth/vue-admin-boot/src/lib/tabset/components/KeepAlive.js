import { isDef } from '@/util';

function isAsyncPlaceholder(node) {
  return node.isComment && node.asyncFactory;
}
function getFirstComponentChild(children) {
  if (Array.isArray(children)) {
    for (let i = 0; i < children.length; i += 1) {
      const c = children[i];
      if (isDef(c) && (isDef(c.componentOptions) || isAsyncPlaceholder(c))) {
        return c;
      }
    }
  }
  return null;
}

export default {
  name: 'TabsetKeepAlive',
  abstract: true,
  destroyed() {
    this.$tabset.destroyed();
  },
  render() {
    const slot = this.$slots.default;
    const vnode = getFirstComponentChild(slot);
    const componentOptions = vnode && vnode.componentOptions;
    if (componentOptions) {
      const { $tabset } = this;
      let key = vnode.key == null
        ? componentOptions.Ctor.cid + (componentOptions.tag ? (`::${componentOptions.tag}`) : '')
        : vnode.key;
      key = `_${key}`;

      this.$nextTick(() => $tabset.checkReplace(key));
      this.$nextTick(() => $tabset.add(key, vnode));
      if ($tabset.cache[key]) {
        vnode.componentInstance = $tabset.cache[key].componentInstance;
      }

      vnode.data.keepAlive = true;
    }
    return vnode || (slot && slot[0]);
  },
};
