<template>
<section>
  <div class="handler">
    <el-button type="primary" size="small" :loading="loading" @click="onSave">{{roleId?'保存':'创建'}}</el-button>
  </div>
  <el-form ref="form" :model="formData" :rules="formRules" label-width="80px">
    <el-form-item label="角色名称" prop="name">
      <el-input v-model="formData.name" size="small" clearable></el-input>
    </el-form-item>
    <el-form-item label="角色编码" prop="code">
      <el-input v-model="formData.code" size="small" clearable></el-input>
    </el-form-item>
    <el-form-item label="所属机构" prop="orgCode">
      <el-select v-model="formData.orgCode" size="small" placeholder="请选择所属机构" style="width:100%">
        <el-option v-for="item in organizations" :key="item.code"
          :label="item.name" :value="item.code"></el-option>
      </el-select>
    </el-form-item>
  </el-form>
  <el-tabs value="perm" v-if="roleId">
    <el-tab-pane label="权限配置" name="perm">
      <role-perm :roleCode="this.formData.code" ref="rolePerm"/>
    </el-tab-pane>
    <el-tab-pane label="菜单配置" name="menu">
      <role-menu :roleCode="this.formData.code" ref="roleMenu"/>
    </el-tab-pane>
  </el-tabs>
</section>
</template>

<script>
import RolePerm from './RolePerm.vue';
import RoleMenu from './RoleMenu.vue';

export default {
  name: 'RoleEdit',
  components: { RolePerm, RoleMenu },
  props: {
    organizations: Array,
    roleId: [Number, String],
  },
  data() {
    return {
      formData: {
        name: '',
        code: '',
        orgCode: '',
      },
      formRules: {
        name: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
        ],
        code: [
          { required: true, message: '请输入角色编码', trigger: 'blur' },
        ],
        orgCode: [
          { required: true, message: '请选择所属机构', trigger: 'change' },
        ],
      },
      loading: false,
    };
  },
  watch: {
    roleId: {
      handler(val) {
        if (this.$refs.form) this.$refs.form.resetFields();
        if (val) {
          this.fetchRole();
        }
      },
      immediate: true,
      deep: false,
    },
  },
  methods: {
    fetchRole() {
      this.loading = true;
      this.$api.getRole(this.roleId).then(({ data }) => {
        this.formData = data.data;
        this.loading = false;
      }, () => {
        this.$message.error('获取角色失败！');
        this.loading = false;
      });
    },
    onSave() {
      this.$refs.form.validate((valid) => {
        if (!valid) return;
        this.loading = true;
        if (this.roleId) {
          this.updateRole();
          this.$refs.rolePerm.savePermissions();
          this.$refs.roleMenu.saveMenus();
        } else {
          this.addRole();
        }
      });
    },
    addRole() {
      this.$api.createRole(this.formData).then(({ data }) => {
        this.$message.success('新增角色成功');
        this.$refs.form.resetFields();
        this.$emit('refresh', data.data.id);
        this.loading = false;
      }, () => {
        this.$message.error('新增角色失败');
        this.loading = false;
      });
    },
    updateRole() {
      this.$api.updateRole(this.formData).then(() => {
        this.$message.success('保存角色成功');
        this.$emit('refresh');
        this.loading = false;
      }, () => {
        this.$message.error('保存角色失败');
        this.loading = false;
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.handler{
  text-align:right;
  margin-bottom:20px
}
/deep/{
  .el-tabs{
    padding-top: 20px;
  }
}
</style>

