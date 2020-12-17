<template>
  <vue-svg-icon v-bind="props">
  </vue-svg-icon>
</template>
<script>
  import VueSvgIcon from 'vue-svgicon';

  export default {
    components: {
      VueSvgIcon,
    },
    props: {
    },
    computed: {
    },
    created() {
      console.log(this);
    },
    register: function () {
      console.warn("inject deprecated since v1.2.0, SVG files can be loaded directly, so just delete the inject line.")
    },
  }
</script>