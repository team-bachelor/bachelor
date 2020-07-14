<template>
<el-scrollbar>
  <el-menu
    :router="true"
    :default-active="$route.path"
    :collapse="collapsed">
      <lx-menu-item v-for="(item,index) in menus"
      :key="item.path+index"
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
    collapsed() {
      return !this.$store.state.sidebar.opened;
    },
  },
};
</script>
