<template>
<section class="lx-container lx-full theme-galatea">
  <aside class="lx-aside" :class="{ collapsed: collapsed }">
    <lx-user-card/>
    <lx-menu-list class="lx-menu"/>
    <div class="lx-aside-shadow"></div>
  </aside>
  <section class="lx-container lx-vertical" style="position: relative;">
    <lx-header class="lx-header"/>
    <section class="lx-container lx-vertical">
      <div class="lx-breadcrumb">
        <a href="javascript:;" class="lx-menu-ctrl" @click="onSidebar">
          <i class="el-icon-menu"></i>
        </a>
        <lx-breadcrumb/>
      </div>
      <!-- <tabset/> -->
      <main class="lx-main" ref="lx-main">
        <transition name="fade-transform" mode="out-in">
          <router-view :cn.org.bachelor.up.oauth2.key="$route.fullPath"/>
        </transition>
        <!-- <tabset-keep-alive>
          <router-view :cn.org.bachelor.up.oauth2.key="$route.fullPath"/>
        </tabset-keep-alive> -->
      </main>
    </section>
    <lx-common-view/>
  </section>
</section>
</template>
<script>
import { TOGGLE_SIDEBAR } from '@/store/types';
import { scrollToTop } from '@/util';
import LxHeader from './Header.vue';
import LxMenuList from './MenuList.vue';
import LxBreadcrumb from './Breadcrumb.vue';
import LxCommonView from './CommonView.vue';
import LxUserCard from './UserCard.vue';

export default {
  name: 'Layout',
  components: {
    LxHeader,
    LxMenuList,
    LxBreadcrumb,
    LxCommonView,
    LxUserCard,
  },
  computed: {
    collapsed() {
      return !this.$store.state.sidebar.opened;
    },
  },
  mounted() {
    this.$root.scrollToTop = animation => scrollToTop(this.$refs['lx-main'], animation);
  },
  methods: {
    onSidebar() {
      this.$store.commit(TOGGLE_SIDEBAR);
    },
  },
};
</script>
<style lang="scss" src="./layout.scss"></style>
