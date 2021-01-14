<template>
  <header>
    <div  class="logo" :title="PROJECT_NAME"></div>
    <div>
      <el-dropdown placement="bottom-end" class="userinfo">
        <a href="javascript:;">
          <el-button icon="fa fa-user" circle size="mini"></el-button>
          {{userinfo.username}}
        </a>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="$router.logout">
            <i class="fa fa-sign-out"></i> 退出登录
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <a href="javascript:;" class="startover" @click="openedCommonView('ServiceList')">
        <img src="../../assets/images/startover.png" style="display:inline-block" height="64">
      </a>
    </div>
  </header>
</template>
<script>
import { TOGGLE_COMMON_VIEW } from '@/store/types';
import { PROJECT_NAME } from '@/config';

export default {
  name: 'LxHeader',
  data() {
    return {
      PROJECT_NAME,
    };
  },
  computed: {
    userinfo() {
      return this.$store.state.userinfo;
    },
  },
  methods: {
    openedCommonView(e) {
      this.$store.commit(TOGGLE_COMMON_VIEW, e);
    },
  },
};
</script>
