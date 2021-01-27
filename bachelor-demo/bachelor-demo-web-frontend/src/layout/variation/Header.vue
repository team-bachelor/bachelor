<template>
  <header>
    <div class="flex-row">
      <div class="logo" @click="openedCommonView('ServiceList')">
        <img src="@/assets/images/logo.svg" height="50">
      </div>
      <div class="title">{{PROJECT_NAME}}</div>
    </div>
    <div class="top-menu">
      <el-menu :default-active="activeIndex" mode="horizontal" @select="onChangeMenu">
        <el-menu-item
        v-for='(item) in menus'
        :key="item.path"
        :index="item.path">{{ item.title }}</el-menu-item>
      </el-menu>
    </div>
    <div class="aside">
      <a href="javascript:;" class="item circle" @click="openedCommonView('MessageList')">
        <i class="fa fa-bell-o"></i>
      </a>
      <el-dropdown placement="bottom-end">
        <a href="javascript:;" class="item circle">
        <i class="fa fa-user"></i> {{userinfo.username}} <i class="fa fa-angle-down"></i>
      </a>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="openedCommonView('Userinfo')">
          <i class="fa fa-user"></i> 用户信息
        </el-dropdown-item>
        <el-dropdown-item @click.native="openedCommonView('Preference')">
          <i class="fa fa-cog"></i> 编好设置
        </el-dropdown-item>
        <el-dropdown-item @click.native="$router.logout">
          <i class="fa fa-sign-out"></i> 退出登录
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
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
    menus() {
      return this.$store.state.menu;
    },
    activeIndex() {
      const matched = this.$route.matched.map(v => v.path);
      const matchedMenu = this.menus.filter(menu => matched.indexOf(menu.path) >= 0);
      if (matchedMenu.length) {
        return matchedMenu[0].path;
      }
      return '';
    },
  },
  methods: {
    openedCommonView(e) {
      this.$store.commit(TOGGLE_COMMON_VIEW, e);
    },
    onChangeMenu(e) {
      this.$router.push(e);
    },
  },
};
</script>

<style lang="scss" scoped>
@import "../../assets/styles/variable.scss";
.logo{
  cursor: pointer;
  padding-top: 5px;
  a{
    margin-left: 5px;
    color: #AAA;
    transition: all 0.3s;
  }
  &:hover{
    a{
      color: #CCC;
    }
  }
}
.title{
  font-size: 20px;
  color: #999;
  font-weight: 700;
  line-height: 60px;
  margin-left: 5px;
}
.aside{
  padding: 18px 0 0;
}
.item{
  background: #FFF;
  padding: 2px 15px;
  line-height: 1;
  font-size: 14px;
  border: 1px dashed transparent;
  margin-left: 10px;
  &:hover{
    border-color: #aaa;
  }
  &.circle{
    border-radius: 15px;
  }
}
.menu-item{
  padding: 2px 10px;
  line-height: 1;
  font-size: 14px;
  margin-left: 10px;
  color: #FFF;
  text-align: right;
  transition: color 0.3s;
  &:hover,&.active{
    color: $--color-primary;
  }
}
</style>
