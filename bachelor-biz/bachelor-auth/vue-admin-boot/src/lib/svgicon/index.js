import Vue from 'vue';
import SvgIcon from 'vue-svgicon';
import '@/assets/svg/components';
import './svg.scss';

Vue.use(SvgIcon, {
  tagName: 'svgicon',
  isStroke: false,
});
