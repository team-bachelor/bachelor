<template>
  <section class="">
    <h2>图标集合</h2>
    <el-row :gutter="10">
      <el-col class="icon" :span="6" v-for="name in icons" :key="name">
  <i :class="'icon-i el-icon-' + name"></i> <span class="icon-name">{{'el-icon-' + name}}</span>
      </el-col>
    </el-row>
    <h2>font awesome 图标集合</h2>
    <el-row :gutter="10">
      <el-col class="icon" :span="6" v-for="name in icons" :key="name">
  <i :class="'icon-i el-icon-' + name"></i> <span class="icon-name">{{'el-icon-' + name}}</span>
      </el-col>
    </el-row>
  </section>
</template>

<script>
import icons from './icon.json';

export default {
  name: 'ExampleIcon',
  data() {
    return {
      icons,
    };
  },
  created() {
  },
  mounted() {
  },
  methods: {
  },
};
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.icon {
  padding: 15px 0;
}
.icon-i {
  color: #606266;
  font-size: 20px;
  float: left;
}
.icon-name {
  height: 1em;
  font-size: 12px;
  margin-left: 5px;
  float: left;
}
</style>
