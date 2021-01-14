
<template>
  <component v-bind="linkProps(route)">
    <slot/>
  </component>
</template>

<script>
import { isExternal } from '@/util';

export default {
  props: {
    route: {
      type: Object,
      required: true,
    },
  },
  methods: {
    isExternalLink(routePath) {
      return isExternal(routePath);
    },
    linkProps(route) {
      if (this.isExternalLink(route.path)) {
        return {
          is: 'a',
          href: route.path,
          target: '_blank',
          rel: 'noopener',
          class: 'el-menu-item',
        };
      }
      return {
        is: 'el-menu-item',
        index: route.path,
      };
    },
  },
};
</script>
