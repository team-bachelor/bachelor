<template>
  <section>
  <iframe :src="url" frameborder="0" class="iframe" scrolling="yes"></iframe>
  </section>
</template>

<script>
/* eslint-disable */
import { iframe } from '@/config';

export default {
  name: 'MonitorTurbine',
  data() {
    return {
      url: iframe.monitorTurbineUrl,
    };
  },
  mounted() {
    // $('.iframe').get(0).onload = () => { 
    //   this.hideIframeAside();
    // };
  },
  methods: {
    hideIframeAside() {
      const aside = $('.iframe').contents().find('.app-wrapper');
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
  height: 100vh;
}
</style>