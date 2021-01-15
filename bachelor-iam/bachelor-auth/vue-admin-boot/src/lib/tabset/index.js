import TabsetManage from './TabsetManage';
import tabsetStore from './store';
import ComponentTabset from './components/Tabset.vue';
import ComponentKeepAlive from './components/KeepAlive';

export default function plugin(Vue, store) {
  if (plugin.installed) return;
  plugin.installed = true;
  if (!store) {
    console.error('You have to install vuex');
    return;
  }

  store.registerModule('tabset', tabsetStore);
  Vue.component('Tabset', ComponentTabset);
  Vue.component('TabsetKeepAlive', ComponentKeepAlive);
  Vue.prototype.$tabset = new TabsetManage();
}
