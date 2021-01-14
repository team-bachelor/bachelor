<template>
  <iframe :src="url" frameborder="0" class="iframe" scrolling="yes"></iframe>
</template>

<script>
/* eslint-disable */
export default {
  props: ['url'],
  mounted() {
    $('.iframe').get(0).onload = () => { 
      this.hideIframeAside();
    };
  },
  methods: {
    hideIframeAside() {
      const aside = $('.iframe').contents().find('.lx-aside');
      if (aside.length) {
        aside.hide();
      } else {
        this.timer = setTimeout(() => this.hideIframeAside(), 400);
      }
    },
  },
  destroyed() {
    clearTimeout(this.timer);
  },
};
</script>

<style rel="stylesheet/scss" lang="scss">
.iframe{
  width: 100%;
  height: 100vh;
}
</style>
