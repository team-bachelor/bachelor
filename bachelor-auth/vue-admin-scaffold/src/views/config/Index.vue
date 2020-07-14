<template>
  <section>
    <el-form :model="form" :rules="rules" ref="form" label-position="top">
      <el-row :gutter="20">
        <el-col :span="7">
          <el-form-item label="文件名" prop="fileName">
            <el-input v-model="form.fileName" placeholder="请输入文件名"
              @keyup.enter.native="submitForm"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="所属阶段">
            <el-select v-model="form.phase" placeholder="请选择所属阶段"
              filterable @change="submitForm">
              <el-option label="全部" value=""></el-option>
              <el-option label="开发" value="dev"></el-option>
              <el-option label="测试" value="test"></el-option>
              <el-option label="灰度" value="pre"></el-option>
              <el-option label="生产" value="prd"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="3">
          <el-form-item class="handle" label="搜索">
            <el-button type="primary" @click="submitForm" :disabled="loading"
              :loading="loading">搜索</el-button>
          </el-form-item>
        </el-col>
        <el-col :span="3" :offset="6">
          <el-form-item class="handle" label="版本推送">
            <el-button @click="handlePush" :disabled="pushing" :loading="pushing">推送</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-table
      :data="tableData"
      v-loading="loading"
      style="width: 100%">
      <el-table-column
        type="index"
        label="序号">
      </el-table-column>
      <el-table-column
        prop="fileName"
        label="文件名">
      </el-table-column>
      <el-table-column
        prop="phase"
        label="所属阶段">
      </el-table-column>
      <!-- <el-table-column
        prop="phase"
        label="所属应用">
      </el-table-column> -->
      <el-table-column
        label="操作">
        <template slot-scope="scope">
          <!-- <el-button
            disabled
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">推送更新</el-button> -->
          <el-button
            disabled
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">修改</el-button>
          <el-button
            disabled
            type="danger"
            size="mini"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          <el-button
            size="mini"
            @click="handleLog(scope.$index, scope.row)">查看历史</el-button>
        </template>
      </el-table-column>
    </el-table>
  </section>
</template>

<script>
export default {
  name: 'Config',
  data() {
    return {
      form: {
        fileName: '',
        phase: '',
      },
      loading: true,
      pushing: false,
      rules: {},
      tableData: [],
    };
  },
  created() {
    this.fetchList();
  },
  methods: {
    fetchList() {
      this.$http.get('/config/all').then(({ data }) => {
        this.loading = false;
        if (data.status === 'OK') {
          this.tableData = data.data;
          return Promise.resolve();
        }
        return Promise.reject();
      }).catch(() => {
        this.loading = false;
        this.$notify.error({
          title: '错误',
          message: '列表获取失败！',
        });
      });
    },
    fetchListBySearch() {
      this.loading = true;
      this.$http.get('/config/search', {
        params: this.form,
      }).then(({ data }) => {
        this.loading = false;
        if (data.status === 'OK') {
          this.tableData = data.data;
          return Promise.resolve();
        }
        return Promise.reject();
      }).catch(() => {
        this.loading = false;
        this.$notify.error({
          title: '错误',
          message: '列表获取失败！',
        });
      });
    },
    submitForm(e, formName = 'form') {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.fetchListBySearch();
        } else {
          console.log('error submit!!');
        }
      });
    },
    handlePush() {
      this.pushing = true;
      this.$http.put('/config/push').then(({ data }) => {
        this.pushing = false;
        if (data.status === 'OK') {
          this.$notify({
            message: '推送成功',
            type: 'success',
          });
          return Promise.resolve();
        }
        return Promise.reject();
      }).catch(() => {
        this.pushing = false;
        this.$message.error('推送成功');
      });
    },
    handleLog(index, row) {
      console.log(index, row);
      this.$router.push({
        name: 'config/log',
        params: {
          fileName: row.fileName,
        },
        query: {
          phase: row.phase,
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
}
</style>
