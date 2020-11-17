<template>
  <section>
    <h3>注册与发现服务列表</h3>
    <el-tabs v-model="activeName" type="border-card"
      v-if="Object.keys(eurekaList).length">
      <el-tab-pane :label="name" :name="name"
        v-for="(ips, name) in eurekaList" :key="name">
        <ul>
          <li v-for="(ip,ipIndex) in ips"
            :key="ipIndex">{{ip}} -
            <span v-if="eurekaInstanceList[ip]&&eurekaInstanceList[ip].status==STATUS.UP"
              class="health">启动中</span>
            <span v-else class="unhealth">停止中</span>
          </li>
        </ul>
      </el-tab-pane>
    </el-tabs>
    <div v-else class="no-data">
      无可用服务
    </div>
    <h3>业务服务列表</h3>
    <el-table
      :data="serviceList"
      style="width: 100%">
      <el-table-column
        type="index"
        label="序号">
      </el-table-column>
      <el-table-column
        prop="id"
        label="服务名">
      </el-table-column>
      <el-table-column
        label="服务状态">
        <template slot-scope="scope">
          <div v-if="scope.row.instances&&scope.row.instances.length">
            <div v-for="(item,index) in scope.row.instances"
              :key="index">
              {{item.instanceId}}
              <span v-if="item.status==STATUS.UP" class="health">（启动中）</span>
              <span v-else class="unhealth">（停止中）</span>
              <el-tooltip effect="dark" placement="top"
                :content="item.status==STATUS.UP?'停止该服务':'启动此服务'">
                <a href="javascript:;" @click="runInstance(item)">
                  <i class="fa" :class="{
                      'fa-stop': item.status==STATUS.UP,
                      'fa-play': item.status!=STATUS.UP}"></i>
                </a>
              </el-tooltip>
            </div>
          </div>
          <div v-else>无可用服务</div>
        </template>
      </el-table-column>
      <el-table-column
        label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            :disabled="scope.row.status===STATUS.UP"
            @click="playService(scope.row)">全部启动</el-button>
          <el-button
            size="mini"
            type="danger"
            :disabled="scope.row.status===STATUS.HALT"
            @click="stopService(scope.row)">全部停止</el-button>
        </template>
      </el-table-column>
    </el-table>
  </section>
</template>

<script>
const STATUS = {
  HALT: 'HALT',
  UP: 'UP',
  UNCERTAIN: 'UNCERTAIN',
};

export default {
  name: 'Service',
  data() {
    return {
      STATUS,
      activeName: '',
      eurekaList: {},
      eurekaInstanceList: {},
      serviceList: [],
    };
  },
  mounted() {
    this.fetchDiscoveryEureka();
    this.fetchDiscoveryServices();
  },
  methods: {
    fetchDiscoveryEureka() {
      this.$http.get('/discovery/eureka').then(({ data }) => {
        Object.keys(data.data).forEach((name) => {
          if (!this.activeName) this.activeName = name;
          if (data.data[name].length) {
            data.data[name].forEach((url => this.fetchDiscoveryEurekaInfo(url)));
          }
        });
        this.eurekaList = data.data;
      }).catch(() => {
        this.$notify.warning({
          title: '警告',
          message: '未发现服务列表！',
        });
      });
    },
    fetchDiscoveryEurekaInfo(url) {
      this.$http.get('/discovery/eureka/info', {
        params: { url },
      }).then(({ data }) => {
        this.$set(this.eurekaInstanceList, url, data.data);
      });
    },
    fetchDiscoveryServices() {
      this.$http.get('/discovery/services').then(({ data }) => {
        if (data.data && data.data.length) {
          // data.data[0].instances.push(Object.assign({}, data.data[0].instances[0], {
          //   status: 'HALT',
          // }));
          this.resetServiceStatus(data.data);
          return Promise.resolve();
        }
        return Promise.reject();
      }).catch(() => {
        this.$notify.error({
          title: '错误',
          message: '服务列表获取失败！',
        });
      });
    },
    resetServiceStatus(data) {
      data.map((item) => {
        const len = item.instances.filter(instance => instance.status === 'UP').length;
        if (len === item.instances.length) {
          item.status = STATUS.UP;
        } else if (len < 1) {
          item.status = STATUS.HALT;
        } else {
          item.status = STATUS.UNCERTAIN;
        }
        return item;
      });
      this.serviceList = data;
    },
    runInstance(instance) {
      if (instance.status === STATUS.UP) this.stopInstance(instance);
      else this.playInstance(instance);
    },
    playInstance(instance) {
      this.$http.post(`/discovery/service/${instance.app}/${instance.instanceId}`).then(({ data }) => {
        if (data.status === 'OK') {
          instance.status = STATUS.UP;
          this.resetServiceStatus(this.serviceList);
          return Promise.resolve();
        }
        return Promise.reject();
      }).catch(() => this.notifyError(true));
    },
    stopInstance(instance) {
      this.$http.delete(`/discovery/service/${instance.app}/${instance.instanceId}`).then(({ data }) => {
        if (data.status === 'OK') {
          instance.status = STATUS.HALT;
          this.resetServiceStatus(this.serviceList);
          return Promise.resolve();
        }
        return Promise.reject();
      }).catch(() => this.notifyError(false));
    },
    playService(service) {
      this.$http.post(`/discovery/service/${service.id}`).then(({ data }) => {
        if (data.status === 'OK') {
          service.status = STATUS.UP;
          this.fetchDiscoveryServices();
          // this.resetServiceStatus(this.serviceList);
          return Promise.resolve();
        }
        return Promise.reject();
      }).catch(() => this.notifyError(true));
    },
    stopService(service) {
      this.$http.delete(`/discovery/service/${service.id}`).then(({ data }) => {
        if (data.status === 'OK') {
          service.status = STATUS.HALT;
          this.fetchDiscoveryServices();
          // this.resetServiceStatus(this.serviceList);
          return Promise.resolve();
        }
        return Promise.reject();
      }).catch(() => this.notifyError(false));
    },
    notifyError(is) {
      this.$notify.error({
        title: '错误',
        message: `服务${is ? '启动' : '停止'}失败！`,
      });
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss">
.health{
  color: #67C23A;
}
.unhealth{
  color: #E6A23C;
}
a{
  color: #333
}
</style>
