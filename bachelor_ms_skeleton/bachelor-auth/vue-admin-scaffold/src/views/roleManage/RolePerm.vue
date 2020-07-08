<template>
<el-collapse v-model="collapseActives" class="collapse" v-loading="loading">
  <el-collapse-item v-for="(permission, index) in permissions"
    :key="permission.groupCode" :name="index">
    <template slot="title">
      <el-checkbox
        :indeterminate="permission.isIndeterminate"
        v-model="permission.checkedAll"
        @change="onCheckedAll($event, permission)">《{{permission.groupName}}》</el-checkbox>
    </template>
    <el-checkbox-group v-model="permission.checkeds"
      @change="onChecked($event, permission)">
      <el-checkbox v-for="item in permission.perms"
        :label="item.objCode"
        :key="`${permission.groupCode}_${item.objCode}`">{{item.operateName}}</el-checkbox>
    </el-checkbox-group>
  </el-collapse-item>
</el-collapse>
</template>

<script>
export default {
  name: 'RolePerm',
  props: {
    roleCode: [Number, String],
  },
  data() {
    return {
      collapseActives: [],
      permissions: [],
      rolePermissions: [],
      loading: true,
    };
  },
  watch: {
    roleCode: {
      handler(val) {
        this.rolePermissions = [];
        if (val) {
          this.loading = true;
          Promise.all([this.fetchRolePermissions(), this.fetchPermissions()])
            .then(() => {
              this.merge();
              this.loading = false;
            })
            .catch(() => {
              this.loading = false;
            });
        }
      },
      immediate: true,
      deep: false,
    },
  },
  methods: {
    async fetchRolePermissions() {
      try {
        const { data } = await this.$api.getRolePermissions({
          roleCode: this.roleCode,
        });
        if (data && data.status === 'OK') {
          this.rolePermissions = data.data;
          return Promise.resolve();
        }
        throw new Error('invlid');
      } catch (e) {
        this.$message.error('获取角色权限失败！');
        return Promise.reject(e);
      }
    },
    async fetchPermissions() {
      try {
        if (this.permissions.length) return Promise.resolve();
        const { data } = await this.$api.getPermissions();
        if (data && data.status === 'OK') {
          this.collapseActives = data.data.map((v, i) => i);
          this.permissions = data.data;
          return Promise.resolve();
        }
        throw new Error('invlid');
      } catch (e) {
        this.$message.error('获取权限列表失败！');
        return Promise.reject(e);
      }
    },
    merge() {
      this.permissions = this.permissions.map((group) => {
        let checkeds = [];
        if (group.perms) {
          checkeds = group.perms
            .filter(perm => this.rolePermissions.indexOf(perm.objCode) >= 0)
            .map(perm => perm.objCode);
        } else {
          group.perms = [];
        }
        return Object.assign({}, group, {
          checkeds,
          checkedAll: checkeds.length && checkeds.length >= group.perms.length,
          isIndeterminate: false,
        });
      });
    },
    savePermissions() {
      const perms = [];
      this.permissions.forEach((group) => {
        group.perms.forEach((perm) => {
          if (group.checkeds.indexOf(perm.objCode) >= 0) {
            perms.push({
              objCode: perm.objCode,
              objOperate: perm.objOperate,
              objUri: perm.objUri,
              owner: perm.owner,
            });
          }
        });
      });
      this.$api.updateRolePermissions({
        roleCode: this.roleCode,
        [[]]: perms,
      }).then(() => {
        this.$message.success('保存权限成功');
      }, () => {
        this.$message.error('保存权限失败');
      });
    },
    onCheckedAll(val, permission) {
      permission.checkeds = val ? permission.perms.map(v => v.objCode) : [];
      permission.isIndeterminate = false;
    },
    onChecked(ids, permission) {
      const checkedCount = ids.length;
      permission.checkedAll = checkedCount === permission.perms.length;
      permission.isIndeterminate = checkedCount > 0 && checkedCount < permission.perms.length;
    },
  },
};
</script>
<style lang="scss" scoped>
.collapse{
  border-top: none;
  border-bottom: none;
}
</style>
