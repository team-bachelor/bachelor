<template>
  <iframe :src="url" frameborder="0" class="iframe" scrolling="yes"
    :style="{minHeight:'100vh', height: iframeHeight}"></iframe>
</template>

<script>
/* eslint-disable */
export default {
  props: ['url'],
  data() {
    return {
      iframeHeight: 'auto',
    };
  },
  mounted() {
    $('.iframe').get(0).onload = () => {
      this.hideIframeAside();
    };
  },
  methods: {
    hideIframeAside() {
      const iframe = $('.iframe').contents();
      const aside = iframe.find('.app-wrapper');
      const body = iframe.find('body');
      const euiNavDrawer = iframe.find('.euiNavDrawer');
      if (euiNavDrawer) euiNavDrawer.remove();
      // const euiHeader = iframe.find('.euiHeader');
      // if (euiHeader) euiHeader.remove();
      const height = body.outerHeight(true);
      if (height) {
        this.iframeHeight = height + 'px'
      }
      if (aside.length) {
        aside.css({ left: 0 });
      } else {
        this.timer = setTimeout(() => this.hideIframeAside(), 200);
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
  // height: 100vh;
}
</style>
