<template>
  <section>
    <h2>往下滚动 <i class="el-icon-d-arrow-left"></i></h2>
    <h2>往下滚动 <i class="el-icon-d-arrow-left"></i></h2>
    <h2>往下滚动 <i class="el-icon-d-arrow-left"></i></h2>
    <h2>往下滚动 <i class="el-icon-d-arrow-left"></i></h2>
    <h2>往下滚动 <i class="el-icon-d-arrow-left"></i></h2>
    <h2>往下滚动 <i class="el-icon-d-arrow-left"></i></h2>
    <h2>往下滚动 <i class="el-icon-d-arrow-left"></i></h2>
    <h2>往下滚动 <i class="el-icon-d-arrow-left"></i></h2>
    <el-tooltip placement="top" content="滚动到顶部">
      <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
    </el-tooltip>
  </section>
</template>

<script>
import BackToTop from '@/components/BackToTop.vue';

export default {
  name: 'ExampleScrollToTop',
  components: { BackToTop },
  data() {
    return {
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
h2{
  padding: 50px 0;
  text-align: center;
  color: rgb(191, 203, 217);
}
i{
  display: block;
  margin-top: 100px;
  transform: rotate(-90deg);
  text-align: center;
}
</style>
