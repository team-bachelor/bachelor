<template>
<section>
  <div class="handler">
    <el-button size="small" @click="visibleDialog= true">添加用户</el-button>
    <el-button size="small" type="danger" @click="onRemove">删除用户</el-button>
  </div>
  <el-table
    :data="users"
    :loading="loading"
    @selection-change="onSelectionChange"
    style="width: 100%">
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
      prop="orgName"
      label="机构">
    </el-table-column>
    <div slot="empty">{{loading?'加载中...':'无数据'}}</div>
  </el-table>
  <!-- <el-pagination
    :page-sizes="[15, 30, 50, 100]"
    :page-size="15"
    background
    class="pagination"
    layout="total, sizes, prev, pager, next, jumper"
    :total="40">
  </el-pagination> -->

  <user-add v-model="visibleDialog"
    :roleCode="roleCode"
    :excludeUsers="users"
    @refresh="fetchRoleUsers"
    @close="visibleDialog = $event"/>
</section>
</template>

<script>
import UserAdd from './UserAdd.vue';

export default {
  name: 'RoleUserList',
  components: { UserAdd },
  props: {
    roleCode: [Number, String],
  },
  data() {
    return {
      users: [],
      checkeds: [],
      loading: true,
      visibleDialog: false,
    };
  },
  watch: {
    roleCode: {
      handler() {
        this.fetchRoleUsers();
      },
      immediate: true,
      deep: false,
    },
  },
  created() {
  },
  methods: {
    fetchRoleUsers() {
      this.users = [];
      this.loading = true;
      this.$api.getRoleUsers({
        roleCode: this.roleCode,
      }).then(({ data }) => {
        this.users = data.data;
        this.checkeds = [];
        this.loading = false;
      }, () => {
        this.$message.error('获取角色用户失败！');
        this.loading = false;
      });
    },
    onRemove() {
      if (!this.checkeds.length) {
        this.$message.error('请选择用户');
        return;
      }
      this.$confirm('是否删除关联用户?', '提示', {
        confirmButtonText: '确定删除',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        this.$api.putRoleUsers({
          roleCode: this.roleCode,
          [[]]: this.checkeds,
        }).then(() => {
          this.fetchRoleUsers();
        }).catch(() => this.$message.error('删除关联用户失败！'));
      });
    },
    onSelectionChange(val) {
      this.checkeds = val.map(v => v.account);
    },
  },
};
</script>
<style lang="scss" scoped>
.pagination{
  padding: 20px 0;
  text-align: right
}
</style>

