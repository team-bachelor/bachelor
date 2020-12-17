<template>
    <el-submenu :index="route.path"
      v-if="route.children && route.children.length">
      <template slot="title" :index="route.path">
        <!-- <i v-if="route.icon" :class="route.icon"></i> -->
        <icon :name="route.icon" size="24" v-if="icons.indexOf(route.icon)>=0"/>
        <i v-else :class="route.icon"></i>
        <span slot='title'>{{route.title}}</span>
      </template>
      <lx-menu-item v-for="item in route.children" :key="item.path" :route="item"></lx-menu-item>
    </el-submenu>
    <lx-link
      v-else
      :route="route">
      <span>
        <!-- <i v-if="route.icon" :class="route.icon"></i> -->
        <template v-if="route.icon">
          <icon :name="route.icon" size="24" v-if="icons.indexOf(route.icon)>=0"/>
          <i v-else :class="route.icon"></i>
        </template>
        <span slot="title">{{route.title}}</span>
      </span>
    </lx-link>
</template>
<script>
// import { isExternal } from '@/util';
import icons from '@/lib/icon/icons';
import LxLink from './Link.vue';

export default {
  name: 'LxMenuItem',
  components: { LxLink },
  props: {
    route: {
      type: Object,
    },
  },
  data() {
    return {
      icons,
    };
  },
};
</script>

