<template>
<section class="lx-container lx-vertical lx-full">
  <lx-header class="lx-header"/>
  <section class="lx-container" style="overflow: hidden;">
    <aside class="lx-aside">
      <lx-menu-list class="lx-menu"/>
      <div class="lx-status" v-if="$store.state.workbenchUrl">
        <a :href="$store.state.workbenchUrl">
          <i class="fa fa-th"></i>
          <span v-if="$store.state.sidebar.opened"> 返回工作台</span>
        </a>
      </div>
    </aside>
    <section class="lx-container lx-vertical">
      <!-- <tabset/> -->
      <div class="lx-breadcrumb">
        <a href="javascript:;" class="lx-menu-ctrl" @click="onSidebar">
          <i class="el-icon-menu"></i>
        </a>
        <lx-breadcrumb/>
      </div>
      <main class="lx-main" ref="lx-main">
        <transition name="fade-transform" mode="out-in">
          <router-view :key="$route.fullPath"/>
        </transition>
        <!-- <tabset-keep-alive>
          <router-view :key="$route.fullPath"/>
        </tabset-keep-alive> -->
      </main>
    </section>
  </section>
  <lx-common-view/>
</section>
</template>
<script>
import { TOGGLE_SIDEBAR } from '@/store/types';
import { scrollToTop } from '@/util';
import LxHeader from './Header.vue';
import LxMenuList from './MenuList.vue';
import LxBreadcrumb from './Breadcrumb.vue';
import LxCommonView from './CommonView.vue';

export default {
  name: 'Layout',
  components: {
    LxHeader,
    LxMenuList,
    LxBreadcrumb,
    LxCommonView,
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
