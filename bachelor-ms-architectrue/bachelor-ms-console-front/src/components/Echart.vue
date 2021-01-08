<template>
  <section class="echart">
    <div ref="echart"></div>
  </section>
</template>

<script>
export default {
  name: 'Echart',
  props: {
    option: {
      type: Object,
      default() {
        return {};
      },
    },
    width: 'auto',
    height: 'auto',
  },
  data() {
    return {
      loading: true,
    };
  },
  mounted() {
    this.chart = echarts.init(this.$refs.echart);
    this.chart.setOption(this.option);
  },
};
</script>
