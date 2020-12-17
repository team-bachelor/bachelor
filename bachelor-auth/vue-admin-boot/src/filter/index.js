import Vue from 'vue';
import { handleRequireContext } from '../util';
/**
 * 动态引入
 */
const requireContext = require.context('./', false, /\.js$/);
handleRequireContext(requireContext, (name, context) => {
  if (name === 'index') return;
  Vue.filter(name, context.default || context);
});
