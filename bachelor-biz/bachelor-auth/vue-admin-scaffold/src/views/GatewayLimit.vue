<template>
  <section>
    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column
        prop="title"
        label="参数名">
      </el-table-column>
      <el-table-column
        prop="defaultValue"
        label="默认值">
      </el-table-column>
      <el-table-column
        label="当前值">
        <template slot-scope="scope">
          <el-input placeholder="请输入数值" v-model="scope.row.value">
            <el-button slot="append" icon="el-icon-check" @click="onChange(scope.row)"></el-button>
          </el-input>
        </template>
      </el-table-column>
    </el-table>
  </section>
</template>

<script>
export default {
  name: 'Gateway',
  data() {
    return {
      data: {
        burstCapacity: {
          title: '每用户每秒访问数',
          defaultValue: 0,
          value: 0,
        },
        replenishRate: {
          title: '总访问量',
          defaultValue: 0,
          value: 0,
        },
      },
    };
  },
  computed: {
    tableData() {
      return Object.keys(this.data).map(k => Object.assign({}, this.data[k], { name: k }));
    },
  },
  watch: {
  },
  created() {
    this.query = this.$route.query;
    console.log(this.query);
    this.data.burstCapacity.defaultValue = this.query.burstCapacity || 0;
    this.data.replenishRate.defaultValue = this.query.replenishRate || 0;
    this.data.burstCapacity.value = this.query.burstCapacity || 0;
    this.data.replenishRate.value = this.query.replenishRate || 0;
    if (this.query['in-memory-rate-limiter.burst-capacity']) {
      this.data.burstCapacity.value = this.query['in-memory-rate-limiter.burst-capacity'];
    }
    if (this.query['in-memory-rate-limiter.replenish-rate']) {
      this.data.replenishRate.value = this.query['in-memory-rate-limiter.replenish-rate'];
    }
  },
  methods: {
    onChange(row) {
      const rateLimit = {
        keyResolver: '#{@tokenKeyResolver}',
        replenishRate: this.data.replenishRate.value,
        burstCapacity: this.data.burstCapacity.value,
      };
      this.data[row.name] = row;
      rateLimit[row.name] = row.value;
      const params = {
        routeId: this.query.id,
        url: this.query.url,
      };
      this.$http.put('/gateway/rate_limit', rateLimit, {
        params,
      }).then(({ data }) => {
        if (data.status === 'OK') {
          this.$notify({
            message: '修改成功',
            type: 'success',
          });
          return Promise.resolve();
        }
        return Promise.reject();
      }).catch(() => {
        this.$message.error('修改失败！');
      });
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss">
</style>
