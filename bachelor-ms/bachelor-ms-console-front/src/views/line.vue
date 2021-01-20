<template>
<div>
  <div class="filters">
    <div class="item"
      v-for="(item,index) in items" :cn.org.bachelor.up.oauth2.key="index"
      @click="onItem(item)"
      :class="{active:currentItem.name === item.name}">
      <h4 class="label">{{item.label}}</h4>
      <div class="total">{{item.total}}</div>
    </div>
  </div>
  <div class="chart" ref="chart"></div>
</div>
</template>
<script>
const days = (() => {
  const d = [];
  for (let i = 1; i < 28; i += 1) {
    d.push(i);
  }
  return d;
})();
const option = {
  tooltip: {
    trigger: 'axis',
  },
  legend: {
    data: ['今天', '昨天'],
    bottom: 0,
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '10%',
    containLabel: true,
  },
  toolbox: {
    feature: {
      saveAsImage: {},
    },
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: days,
  },
  yAxis: {
    type: 'value',
  },
  series: [],
  color: ['#1890ff', '#99cdfd'],
};
export default {
  data() {
    return {
      currentItem: {},
      items: [{
        name: 'a',
        total: 1293,
        label: '启动次数',
        data: [
          {
            name: '今天',
            type: 'line',
            smooth: true,
            data: [1, 3, 5, 6, 5, 5, 5, 5, 7, 8, 9, 101, 134, 90, 230, 210,
              120, 132, 101, 134, 90, 230, 210, 132, 101, 134, 90, 230, 210],
          },
          {
            name: '昨天',
            type: 'line',
            smooth: true,
            data: [1, 3, 5, 6, 5, 5, 5, 5, 7, 8, 9, 10, 90, 230, 210, 120, 132,
              210, 132, 101, 134, 90, 230, 210, 230, 210, 230],
          },
        ],
      }, {
        name: 'b',
        total: 1293,
        label: '启动次数',
        data: [
          {
            name: '今天',
            type: 'line',
            smooth: true,
            data: [1, 3, 5, 6, 5, 5, 5, 5, 7, 8, 9, 101, 134, 90, 230, 210,
              120, 132, 101, 134, 90, 230, 210, 132, 101, 134, 90, 230, 210],
          },
          {
            name: '昨天',
            type: 'line',
            smooth: true,
            data: [1, 3, 5, 6, 5, 5, 5, 5, 7, 8, 9, 10, 90, 230, 210, 120, 132,
              210, 132, 101, 134, 90, 230, 210, 230, 210, 230],
          },
        ],
      }, {
        name: 'c',
        total: 1293,
        label: '启动次数',
      }],
    };
  },
  mounted() {
    [this.currentItem] = this.items;
    this.$nextTick(() => this.setChart(this.currentItem.data));
  },
  methods: {
    setChart(data) {
      this.chart = echarts.init(this.$refs.chart);
      this.chart.clear();
      this.chart.setOption(Object.assign(option, {
        series: data,
      }));
    },
    onItem(e) {
      this.currentItem = e;
      this.setChart(e.data);
    },
  },
};
</script>
<style lang="scss" scoped>
.filters{
  display: flex;
  margin: 20px;
  .item{
    width: 140px;
    height: 110px;
    border: 1px solid #eee;
    padding: 10px;
    display: flex;
    flex-direction: column;
    color: #666;
    cursor: pointer;
    &:hover, &.active{
      border-color: #2196F3;
      border-width: 2px;
    }
  }
}
.chart{
  height: 300px;
}
</style>
