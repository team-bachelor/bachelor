<template>
  <section class="" style="background: #FFF;padding:10px;">
    <h2>SVG 图标集合</h2>
    <el-alert type="info">
      <span> svgicon 使用方式
        <el-link type="primary" @click="$router.push('/examples/svg-icon')">请看</el-link>
    </span>
  </el-alert>
    <el-row :gutter="10" type="flex" style="flex-wrap: wrap;">
      <el-col class="icon" :span="6" v-for="name in svgicons" :key="name">
        <svgicon style="stroke-width:1" :name="name" :fill="false" color="#1773ff" scale="1">
        </svgicon>
        <br/>
        <span class="icon-name">{{name}}</span>
      </el-col>
    </el-row>
    <h2>图标集合</h2>
    <el-row :gutter="10">
      <el-col class="icon" :span="6" v-for="name in icons" :key="name">
        <i :class="'icon-i el-icon-' + name"></i> <span class="icon-name">
          {{'el-icon-' + name}}
        </span>
      </el-col>
    </el-row>
    <h2>font awesome 图标集合</h2>
    <el-row :gutter="10">
      <el-col class="icon" :span="6" v-for="name in icons" :key="name">
        <i :class="'icon-i el-icon-' + name"></i> <span class="icon-name">
          {{'el-icon-' + name}}
        </span>
      </el-col>
    </el-row>
  </section>
</template>

<script>
import icons from './icon.json';
import svgicons from './svg-icon';

export default {
  name: 'ExampleIcon',
  data() {
    return {
      icons,
      svgicons,
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
  display: flex;
  justify-content: flex-start;
  flex-direction: column;
  align-items: center;
  text-align: center;
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
