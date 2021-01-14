<template>
  <i class="lx-icon" v-if="has" :style="{
    backgroundImage: `url(${this.url})`,
    width: `${this.size - 0}px`,
    height: `${this.size - 0}px`
  }"></i>
</template>
<style>
</style>

<script>
/* eslint-disable */
import icons from './icons';

export default {
  name: 'Icon',
  components: {
  },
  props: {
    name: String,
    size: {
      default: 20,
      type: [Number, String],
    },
    color: {
      default: 'white',
      type: String,
    },
  },
  data() {
    return {
    };
  },
  computed: {
    url() {
      return require(`@/assets/icons/${this.color}/${this.name}.png`);
    },
    has() {
      return icons.indexOf(this.name) >= 0;
    },
  },
  created() {
  },
  register() {
    console.warn('inject deprecated since v1.2.0, SVG files can be loaded directly, so just delete the inject line.');
  },
};
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.lx-icon{
  speak: none;
  font-style: normal;
  font-weight: normal;
  font-variant: normal;
  text-transform: none;
  line-height: 1;
  vertical-align: baseline;
  display: inline-block;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background-repeat: no-repeat;
  background-size: contain;
  background-position: center;
}
</style>
