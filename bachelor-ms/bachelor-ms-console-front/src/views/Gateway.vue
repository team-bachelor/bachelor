<template>
  <section v-loading="loading">
    <el-tabs
      v-if="gatewayList.length" v-model="activeName" @tab-click="handleClick" type="border-card">
      <el-tab-pane :label="gateway.instanceId" :name="gateway.homePageUrl"
        v-for="(gateway, index) in gatewayList" :key="index">
        <div class="limit-setting" v-if="rateLimit[activeName]">
          <h4>默认限流设置</h4>
          <div class="flex-row">
            <div>每用户每秒访问数</div>
            <div>{{rateLimit[activeName].burstCapacity}}</div>
          </div>
          <div class="flex-row" style="margin-bottom:20px;">
            <div>总访问量</div>
            <div>{{rateLimit[activeName].replenishRate}}</div>
          </div>
        </div>
        <el-table
          :data="routes[activeName]"
          style="width: 100%">
          <el-table-column
            type="index"
            label="序号">
          </el-table-column>
          <el-table-column
            prop="uri"
            label="路径">
          </el-table-column>
          <el-table-column
            prop="predicates"
            width="360"
            label="匹配器">
            <template slot-scope="scope">
              <li v-for="(predicate,index) in scope.row.predicates"
                :key="index">{{predicate.name}}{{predicate.args}}</li>
            </template>
          </el-table-column>
          <el-table-column
            label="过滤器">
            <template slot-scope="scope">
              <li v-for="(filter,index) in scope.row.filters"
                :key="index">{{filter.name}}</li>
            </template>
          </el-table-column>
          <el-table-column
            label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleRouter(scope.$index, scope.row)">路由管理</el-button>
              <!-- <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)">容错情况</el-button> -->
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
    <div class="no-data center" v-if="!loading&&!gatewayList.length">无可用网关</div>
  </section>
</template>

<script>
export default {
  name: 'Gateway',
  data() {
    return {
      activeName: '',
      gatewayList: [],
      routes: {},
      rateLimit: {},
      loading: true,
    };
  },
  watch: {
    activeName(val) {
      if (!val || val === '0') return;
      this.fetchRateLimit(val);
      this.fetchRoutes(val);
    },
  },
  created() {
    this.fetchGatewayList();
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    fetchGatewayList() {
      this.$http.get('/gateway/list').then(({ data }) => {
        this.gatewayList = data.data;
        if (this.gatewayList.length) this.activeName = this.gatewayList[0].homePageUrl;
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    fetchRoutes(url) {
      this.$http.get('/gateway/routes', {
        params: { url },
      }).then(({ data }) => {
        this.$set(this.routes, url, data.data);
      });
    },
    fetchRateLimit(url) {
      this.$http.get('/gateway/default_rate_limit', {
        params: { url },
      }).then(({ data }) => {
        this.$set(this.rateLimit, url, data.data);
      });
    },
    handleRouter(index, row) {
      console.log(index, row, this.$route.query);
      const query = Object.assign({}, {
        uri: row.uri,
        url: this.activeName,
        id: row.id,
      }, this.rateLimit[this.activeName]);
      row.filters.forEach((filter) => {
        if (filter.name !== 'RequestRateLimiter') return;
        if (filter.args) {
          Object.assign(query, filter.args);
        }
      });
      this.$router.push({
        path: '/gateway/limit',
        query,
      });
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.limit-setting{
  line-height: 30px;
  font-size: 14px;
  .flex-row{
    border-bottom: 1px solid #eee;
    justify-content: space-between;
    width: 240px;
  }
}
</style>
