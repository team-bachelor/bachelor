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
  name: 'MonitorBase',
  components: {
    Ifr,
  },
  data() {
    return {
      // url: 'http://221.2.140.133:8601/kibana/app/infra#/home?_g=()',
      url: iframe.monitorBaseUrl,
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
