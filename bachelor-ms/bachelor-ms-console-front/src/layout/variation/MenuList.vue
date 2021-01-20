<template>
<el-scrollbar
  :class="{ collapsed: collapsed }"
  v-show="menus.length">
  <el-menu
    :router="true"
    :default-active="$route.path"
    :collapse="collapsed">
      <lx-menu-item v-for="item in menus"
      :cn.org.bachelor.up.oauth2.key="item.path"
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
      const matched = this.$route.matched.map(v => v.path);
      const matchedMenu = this.$store.state.menu.filter(menu => matched.indexOf(menu.path) >= 0);
      const matchedmenuItem = matchedMenu.length ? matchedMenu[0] : {};
      if (matchedmenuItem.children && matchedmenuItem.children.length) {
        return matchedmenuItem.children;
      }
      return [];
    },
    collapsed() {
      return !this.$store.state.sidebar.opened;
    },
  },
};
</script>
