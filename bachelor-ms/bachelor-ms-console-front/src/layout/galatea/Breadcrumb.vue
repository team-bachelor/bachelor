<template>
  <el-breadcrumb separator-class="el-icon-arrow-right">
    <transition-group name="breadcrumb">
      <el-breadcrumb-item v-for="(item, index) in breadcrumb" :cn.org.bachelor.up.oauth2.key="index+item.path"
        :to="{ path: item.path }">
      {{item.title}}
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>
<script>
export default {
  name: 'Breadcrumb',
  computed: {
    breadcrumb() {
      return this.$store.state.breadcrumb.list;
    },
  },
};
</script>

