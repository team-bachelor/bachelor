<template>
  <section>
    <el-card class="box-card">
      <strong slot="header">配置信息</strong>
      <el-row>
        <el-col :span="3">
          文件名：
        </el-col>
        <el-col :span="6">
          {{$route.params.fileName}}
        </el-col>
        <el-col :span="3">
          所属系统：
        </el-col>
        <el-col :span="6">
          -
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="3">所属阶段：</el-col>
        <el-col :span="6">{{$route.query.phase}}</el-col>
        <el-col :span="3">更新时间：</el-col>
        <el-col :span="6">-</el-col>
      </el-row>
      <el-row>
        <el-col :span="3">存储路径：</el-col>
        <el-col :span="6">-</el-col>
      </el-row>
    </el-card>

    <!-- <h3>业务服务列表</h3> -->
    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column
        type="index"
        label="序号">
      </el-table-column>
      <el-table-column
        prop="commitTime"
        label="更新时间">
      </el-table-column>
      <el-table-column
        prop="message"
        label="更新信息">
      </el-table-column>
      <el-table-column
        label="操作"
        width="360px">
        <template slot-scope="scope">
          <el-button
            type="danger"
            disabled
            size="mini">回滚到此版本</el-button>
          <el-button
            size="mini"
            @click="handleContent(scope.$index, scope.row)">查看内容</el-button>
          <el-button
            size="mini"
            @click="handleMerge(scope.$index, scope.row)">与最新版本比较</el-button>
        </template>
      </el-table-column>
    </el-table>
  </section>
</template>

<script>
export default {
  name: 'ConfigLog',
  data() {
    return {
      tableData: [],
    };
  },
  created() {
    this.fetchList();
  },
  methods: {
    fetchList() {
      this.$http.get('/config/log', {
        params: {
          fileName: this.$route.params.fileName,
        },
      }).then(({ data }) => {
        this.tableData = data.data;
      });
    },
    handleRevert(index, row) {
      this.$router.push({
        name: 'config/log/content',
        params: {
          fileName: this.$route.params.fileName,
          commitId: row.commitId,
        },
      });
    },
    handleContent(index, row) {
      this.$router.push({
        name: 'config/log/content',
        params: {
          fileName: this.$route.params.fileName,
          commitId: row.commitId,
        },
      });
    },
    handleMerge(index, row) {
      console.log(index, row);
      this.$router.push({
        name: 'config/log/diff',
        params: {
          fileName: this.$route.params.fileName,
          commitId: row.commitId,
        },
      });
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.handle label{
  visibility: hidden;
}
.handle /deep/{
  .el-button{
    display: block;
    width: 100%;
  }
  .el-card{
    margin-top: 20px;
  }
}
/deep/{
  .el-card{
    margin-top: 20px;
    margin-bottom: 20px;
  }
}
.dl-horizontal dt{
  width: 100px;
}
.dl-horizontal dd{
  margin-left: 120px;
}
</style>
