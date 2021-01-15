<template>
  <section>
    <ifr :url="url"></ifr>
  </section>
</template>

<script>
/* eslint-disable */
import Ifr from '@/components/IframeKibana.vue'
// import Ifr from '@/components/IframeLocal.vue'
import { iframe } from '@/config';

export default {
  name: 'MonitorService',
  components: {
    Ifr,
  },
  data() {
    return {
      url: iframe.monitorServiceUrl,
    };
  },
  mounted() {
  },
  methods: {
  },
};
</script>

<style rel="stylesheet/scss" lang="scss">
</style>
