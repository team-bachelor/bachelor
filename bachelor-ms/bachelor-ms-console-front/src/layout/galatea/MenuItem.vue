<template>
    <el-submenu :index="route.path"
      v-if="route.children && route.children.length">
      <template slot="title" :index="route.path">
        <i v-if="route.icon" :class="route.icon"></i>
        <span slot='title'>{{route.title}}</span>
      </template>
      <lx-menu-item v-for="(item,index) in route.children" :cn.org.bachelor.up.oauth2.key="item.path+index"
        :route="item"></lx-menu-item>
    </el-submenu>
    <el-menu-item
      v-else
      :index="route.path">
      <i v-if="route.icon" :class="route.icon"></i>
      <span slot="title">{{route.title}}</span>
    </el-menu-item>
</template>
<script>
import { isExternal } from '@/util';

export default {
  name: 'LxMenuItem',
  props: {
    route: {
      type: Object,
    },
  },
  methods: {
    to(path) {
      if (isExternal(path)) {
        return path;
      }
      return this.$router.push(path);
    },
  },
};
</script>

