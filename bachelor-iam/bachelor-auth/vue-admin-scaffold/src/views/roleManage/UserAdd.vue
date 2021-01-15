<template>
  <el-dialog title="添加角色用户" :visible.sync="value" :before-close="beforeClose" width="60%" class="dialog-wrapper" custom-class="dialog">
    <div class="filter">
      <el-form :inline="true" :model="formData" class="form-inline" size="mini">
        <div>
          <el-form-item label="用户名">
          <el-input v-model="formData.userName" placeholder="用户名" clearable></el-input>
        </el-form-item>
        <el-form-item label="部门">
          <el-input v-model="formData.deptName" placeholder="部门" clearable></el-input>
        </el-form-item>
        </div>
        <el-form-item>
          <el-button type="primary" @click="onSearch" :loading="searching">查询</el-button>
        </el-form-item>
      </el-form>
      <div class="selector" v-if="checkeds.length">
        <div class="selector-header">
          已选择:
        </div>
        <div class="selector-body">
          <el-tag size="small" effect="dark" v-for="(row,index) in checkeds" :key="row.id" closable
            @close="onDelete(index,row)">{{row.account}}</el-tag>
        </div>
      </div>
    </div>
    <el-table
      :data="users"
      ref="table"
      class="table"
      @select="onSelectionChange">
      <el-table-column
        type="selection"
        width="48">
      </el-table-column>
      <el-table-column
        prop="account"
        label="编码">
      </el-table-column>
      <el-table-column
        prop="username"
        label="用户名">
      </el-table-column>
      <el-table-column
        prop="deptName"
        label="部门">
      </el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-pagination
        style="padding:10px;"
        layout="prev, pager, next"
        background
        :page-size="pagination.pageSize"
        :current-page="pagination.currentPage"
        @current-change="(i)=>onCurrentPageChange(i, fetch)"
        @prev-click="(i)=>onCurrentPageChange(i, fetch)"
        @next-click="(i)=>onCurrentPageChange(i, fetch)"
        :total="pagination.total">
      </el-pagination>
      <el-button @click="$emit('close', false)">取 消</el-button>
      <el-button type="primary" @click="onConfirm" :loading="loading">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import some from 'lodash/some';
import pageMixin from './pageMixin';

export default {
  name: 'RoleUserAdd',
  props: {
    value: Boolean,
    roleCode: [String, Number],
    excludeUsers: Array,
  },
  mixins: [pageMixin],
  data() {
    return {
      loading: false,
      searching: false,
      users: [],
      checkeds: [],
      formData: {
        userName: '',
        deptName: '',
      },
      depts: [],
    };
  },
  watch: {
    value(val) {
      this.searching = false;
      this.checkeds = [];
      this.resetPagination();
      this.formData = {
        userName: '',
        deptName: '',
      }
      if (val) this.fetch();
    },
  },
  created() {
    // this.fetchDepts();
  },
  methods: {
    async fetch() {
      this.searching = true;
      const params = {
        start: 0,
        page: this.pagination.currentPage,
        pageSize: this.pagination.pageSize,
        orgId: this.$store.state.userinfo.org_id,
      };
      if (this.formData.userName) params.userName = this.formData.userName;
      if (this.formData.deptName) params.deptName = this.formData.deptName;
      try {
        const { data, headers } = await this.$api.getUsers(params);
        const userCodes = this.excludeUsers.map(user => user.account);
        this.users = data.data.filter(user => userCodes.indexOf(user.account) < 0);
        this.pagination.total = headers.total ? headers.total - 0 : 0;
        if (data.data.length > this.pagination.pageSize) {
          this.pagination.isFinish = true;
        }
        this.renderChecked();
      } catch (e) {
        this.$message.error('获取用户失败！');
      } finally {
        this.searching = false;
      }
    },
    submit() {
      const ids = this.checkeds;
      if (this.loading) return;
      this.loading = true;
      this.$api.postRoleUsers({
        roleCode: this.roleCode,
        [[]]: ids,
      }).then(() => {
        this.$emit('close', false);
        this.$emit('refresh');
        this.checkeds = [];
        this.loading = false;
      }).catch(() => {
        this.$message.error('新增角色角色失败！');
        this.loading = false;
      });
    },
    beforeClose() {
      this.$emit('close', false);
    },
    onConfirm() {
      if (!this.checkeds.length) {
        this.$message.error('请选择用户');
        return;
      }
      this.submit();
    },
    onSelectionChange(checkedUsers, currentUser) {
      this.users.forEach((user) => {
        const selected = some(checkedUsers, {id: user.id});
        const cehcked = some(this.checkeds, {id: user.id});
        if (selected && !cehcked) {
          this.checkeds.push({
            id: user.id,
            account: user.account,
          })
        } else if (!selected && cehcked) {
          this.checkeds = this.checkeds.filter(item => item.id !== user.id);
        }
      });
    },
    renderChecked() {
      this.$nextTick(() => {
        this.checkeds.forEach((row) => {
          this.handleChecked(row.id, true);
        });
      });
    },
    handleChecked(id, val = false){
      const users = this.users.filter(user => user.id === id);
      if (users.length) this.$refs.table.toggleRowSelection(users[0], val);
    },
    onDelete(index, row) {
      this.checkeds.splice(index, 1);
      this.handleChecked(row.id, false);
    },
    onSearch() {
      this.resetPagination();
      this.fetch();
    },
    async fetchDepts() {
      const { data } = await this.$api.getDepts({ tree: false, orgId: this.$store.state.userinfo.org_id });
      this.depts = data.data;
    },
  },
};
</script>
<style lang="scss" scoped>
.dialog-wrapper{
  /deep/{
    .dialog{
      min-width: 600px;
    }
    .form-inline{
      display: flex;
      justify-content: space-between;
    }
    .el-dialog__body{
      padding: 0;
    }
    .el-table{
      thead>tr>th .el-checkbox{
        display: none;
      }
    }
  }
  .filter{
    border-bottom: 1px solid rgba(0,0,0,0.1);
    padding: 10px 15px 0;
    background: rgba(0,0,0,0.03);
  }
  .selector{
    display: flex;
    .selector-header{
      width: 58px;
    }
    .selector-body{
      flex: 1;
    }
    .el-tag{
      margin-bottom: 5px;
      margin-right: 10px;
    }
  }
  .el-table{
    padding: 10px 10px 0;
  }
}
</style>
