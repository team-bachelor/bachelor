<template>
<section class="lx-user-card">
  <div class="image"></div>
  <div class="handle">
    <el-dropdown placement="bottom-end">
      <a href="javascript:;" class="item">
        {{userinfo.username}} <i class="fa fa-bars"></i>
      </a>
      <el-dropdown-menu slot="dropdown">
        <!-- <el-dropdown-item @click.native="openedCommonView('Userinfo')">
          <i class="fa fa-user"></i> 用户信息
        </el-dropdown-item>
        <el-dropdown-item @click.native="openedCommonView('Preference')">
          <i class="fa fa-cog"></i> 编好设置
        </el-dropdown-item> -->
        <el-dropdown-item @click.native="$router.logout">
          <i class="fa fa-sign-out"></i> 退出登录
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</section>
</template>
<script>
import { TOGGLE_SIDEBAR, TOGGLE_COMMON_VIEW } from '@/store/types';

export default {
  name: 'UserCard',
  computed: {
    userinfo() {
      return this.$store.state.userinfo;
    },
  },
  methods: {
    openedCommonView(e) {
      this.$store.commit(TOGGLE_COMMON_VIEW, e);
    },
    onSidebar() {
      this.$store.commit(TOGGLE_SIDEBAR);
    },
  },
};
</script>
