<template>
  <el-dialog title="添加角色用户" :visible.sync="value" :before-close="beforeClose">
    <el-table
      :data="users"
      @selection-change="onSelectionChange">
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
        label="部门">
      </el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button @click="$emit('close', false)">取 消</el-button>
      <el-button type="primary" @click="onConfirm" :loading="loading">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'RoleUserAdd',
  props: {
    value: Boolean,
    roleCode: [String, Number],
    excludeUsers: Array,
  },
  data() {
    return {
      loading: false,
      users: [],
      checkeds: [],
    };
  },
  watch: {
    value(val) {
      if (val) {
        this.checkeds = [];
        this.fetch();
      }
    },
  },
  methods: {
    fetch() {
      const userCodes = this.excludeUsers.map(user => user.account);
      this.$api.getUsers({
        orgId: this.$store.state.userinfo.org_id,
      }).then(({ data }) => {
        this.checkeds = [];
        this.users = data.data.filter(user => userCodes.indexOf(user.account) < 0);
      }).catch(() => {
        this.$message.error('获取用户失败！');
      });
    },
    submit() {
      const ids = [];
      this.users.forEach((user) => {
        if (this.checkeds.indexOf(user.account) >= 0) {
          ids.push({
            id: user.id,
            account: user.account,
          });
        }
      });
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
    onSelectionChange(val) {
      this.checkeds = val.map(v => v.account);
    },
  },
};
</script>
<style lang="scss" scoped>
</style>

