<template>
  <section class="home">
    <el-row>
      <el-col :span="24">
        <div ref="bar" style="height:400px;"></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <div ref="barPolarStack1" style="height:400px;"></div>
      </el-col>
      <el-col :span="12">
        <div ref="barPolarStack2" style="height:400px;"></div>
      </el-col>
    </el-row>
  </section>
</template>

<script>
export default {
  name: 'HomeDefault',
  created() {
  },
  mounted() {
    this.bar('bar');
    this.barPolarStack('barPolarStack1');
    this.barPolarStack('barPolarStack2');
  },
  methods: {
    bar(ref) {
      const myChart = echarts.init(this.$refs[ref]);
      const option = {
        tooltip: {},
        xAxis: {
          data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子'],
        },
        yAxis: {},
        series: [
          {
            name: '销量',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20],
          },
        ],
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
    barPolarStack(ref) {
      const myChart = echarts.init(this.$refs[ref]);
      const option = {
        angleAxis: {},
        radiusAxis: {
          type: 'category',
          data: ['周一', '周二', '周三', '周四'],
          z: 10,
        },
        polar: {},
        series: [
          {
            type: 'bar',
            data: [1, 2, 3, 4],
            coordinateSystem: 'polar',
            name: 'A',
            stack: 'a',
          },
          {
            type: 'bar',
            data: [2, 4, 6, 8],
            coordinateSystem: 'polar',
            name: 'B',
            stack: 'a',
          },
          {
            type: 'bar',
            data: [1, 2, 3, 4],
            coordinateSystem: 'polar',
            name: 'C',
            stack: 'a',
          },
        ],
        legend: {
          show: true,
          data: ['A', 'B', 'C'],
        },
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
  },
};
</script>
