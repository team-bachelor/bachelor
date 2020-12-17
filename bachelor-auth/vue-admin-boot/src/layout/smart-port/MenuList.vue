<template>
<el-scrollbar>
  <el-menu
    :router="true"
    :default-active="$route.path"
    text-color="#FFF">
      <lx-menu-item v-for="item in menus"
      :key="item.path"
      :route="item"
      ></lx-menu-item>
  </el-menu>
</el-scrollbar>
</template>
<script>
import LxMenuItem from './MenuItem.vue';

export default {
  name: 'LxMenu',
  components: {
    LxMenuItem,
  },
  computed: {
    menus() {
      return this.$store.state.menu;
    },
  },
};
</script>
