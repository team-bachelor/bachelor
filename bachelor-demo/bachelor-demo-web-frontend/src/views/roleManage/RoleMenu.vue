<template>
<section>
  <el-input
    placeholder="输入关键字进行过滤"
    v-model="filterText"
    size="mini">
  </el-input>
  <!-- 父子不关联 -->
  <!-- :check-strictly="true" -->
  <el-tree
    ref="tree"
    :data="menus"
    empty-text="无数据"
    :props="{label: 'comment', children: 'subMenus'}"
    show-checkbox
    node-key="code"
    default-expand-all
    :expand-on-click-node="false"
    :render-content="renderContent"
    :filter-node-method="filterNode">
  </el-tree>
</section>
</template>
<script>
import uniq from 'lodash/fp/uniq';
// import menus from '@/config/menu';

export default {
  name: 'RoleMenu',
  props: {
    roleCode: [Number, String],
  },
  data() {
    return {
      loading: true,
      filterText: '',
      menus: [],
      roleMenus: [],
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
    roleCode: {
      handler(val) {
        if (val) {
          this.filterText = '';
          this.roleMenus = [];
          this.$refs.tree.setCheckedKeys([]);
          this.loading = true;
          Promise.all([this.fetchMenus(), this.fetchRoleMenus()])
            .then(() => {
              this.menus.forEach((menu) => {
                if (menu.subMenus && menu.subMenus.length &&
                  this.roleMenus.indexOf(menu.code) >= 0) {
                  this.roleMenus.splice(this.roleMenus.indexOf(menu.code), 1);
                }
              });
              this.$refs.tree.setCheckedKeys(this.roleMenus);
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
    fetchMenus() {
      // this.menus = menus;
      if (this.menus.length) return Promise.resolve();
      return this.$api.getMenus().then(({ data }) => {
        this.menus = data.data;
      }, () => {
        this.$message.error('获取角色菜单失败！');
      });
    },
    fetchRoleMenus() {
      return this.$api.getRoleMenu({
        roleCode: this.roleCode,
      }).then(({ data }) => {
        this.roleMenus = uniq(data.data);
      }, () => {
        this.$message.error('获取角色菜单失败！');
      });
    },
    saveMenus() {
      const keys = this.$refs.tree.getCheckedKeys();
      this.$api.updateRoleMenu({
        roleCode: this.roleCode,
        [[]]: keys,
      }).then(() => {
        this.$message.success('保存菜单成功');
      }, () => {
        this.$message.error('保存菜单失败');
      });
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.comment.indexOf(value) !== -1;
    },
    renderContent(h, { node }) {
      return (
        <span class="custom-tree-node">
          <span>{node.label}</span>
        </span>
      );
    },
  },
};
</script>

<style lang="scss" scoped>
/deep/ {
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }
  .el-tree{
    padding-top: 20px;
  }
  .el-tree__empty-text{
    font-size: 12px;
    color: #999
  }
}
</style>
