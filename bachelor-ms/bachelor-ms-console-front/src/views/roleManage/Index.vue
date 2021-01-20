<template>
<section class="lx-container">
  <div class="filter" :class="{opened:!opened}">
    <el-form class="form" ref="filter" :model="filter" @submit.prevent="fetchRoles">
      <el-row :gutter="20">
        <el-col :span="opened?24:8">
          <el-form-item label="">
            <el-input v-model="filter.keyWord" placeholder="请输入角色名称或编码"
              @keyup.enter.native="fetchRoles"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="opened?12:8">
          <multilevel-dropdown :data="allOrganizations" @command="onCommand"
            :props="{ label: 'name', cn.org.bachelor.up.oauth2.key: 'code', children: 'subOrgs' }">
            <el-button type="primary">
              {{filter.orgName ? `${filter.orgName}` : '所属机构'}}
                <i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
          </multilevel-dropdown>
        </el-col>
        <el-col :span="opened?12:8">
          <el-form-item>
            <el-button type="primary" round @click.prevent="fetchRoles" :loading="loading">
              <i class="el-icon-search"/>
              <span>查找</span>
            </el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="hairline"></div>

    <el-button size="small" @click="onEditRole(0)">新增角色</el-button>
    <el-button size="small" type="danger" @click="onRemoveRoles" >批量删除</el-button>
    <el-table
      ref="table"
      :data="roles"
      :loading="loading"
      :fit="opened==''"
      :highlight-current-row="true"
      @selection-change="onSelectionChange">
      <el-table-column
          type="selection"
          width="35">
      </el-table-column>
      <el-table-column
        prop="code"
        label="角色编码"
        v-if="!opened">
      </el-table-column>
      <el-table-column
        prop="name"
        label="角色名称"
        :show-overflow-tooltip="true">
      </el-table-column>
      <el-table-column
        v-if="!opened"
        label="所属机构">
        <template slot-scope="scope">
        <span>{{organizationMaps[scope.row.orgCode] || scope.row.orgCode}}</span>
        </template>
      </el-table-column>
      <el-table-column width="232">
        <template slot-scope="scope">
        <el-button
          size="mini"
          @click="onEditUser(scope.row.code)">关联用户</el-button>
        <el-button
          size="mini"
          type="primary"
          @click="onEditRole(scope.row.id)">编辑</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="onRemoveRole(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
      <div slot="empty">{{loading?'加载中...':'无数据'}}</div>
    </el-table>
  </div>
  <div class="list" v-if="opened">
    <el-button circle size="mini" class="list-close"
      icon="el-icon-d-arrow-right"
      @click="opened=''">
    </el-button>
    <user-list v-if="opened=='roleUser'" :roleCode.sync="checkedRoleCode"/>
    <role-edit v-if="opened=='roleEdit'" :roleId.sync="checkedRoleId"
      :organizations="organizations"
      @refresh="onRefresh"/>
  </div>
</section>
</template>

<script>
import RoleEdit from './RoleEdit.vue';
import UserList from './UserList.vue';
import MultilevelDropdown from '../../components/MultilevelDropdown.vue';

export default {
  name: 'roleManage',
  components: { RoleEdit, UserList, MultilevelDropdown },
  data() {
    return {
      roles: [],
      checkedRoleId: '',
      checkedRoleCode: '',
      organizations: [],
      organizationMaps: {},
      filter: {
        keyWord: '',
        orgCode: null,
        orgName: null,
      },
      checkeds: [],
      opened: '',
      loading: true,
    };
  },
  computed: {
    allOrganizations() {
      return [{
        code: null,
        id: '',
        name: '全部',
        pid: '0',
      }, ...this.organizations];
    },
  },
  created() {
    this.fetchRoles();
    this.fetchOrganizations();
  },
  methods: {
    fetchRoles() {
      this.loading = true;
      this.$api.getRoles(this.filter).then(
        ({ data }) => {
          this.roles = data.data;
          this.loading = false;
        },
        () => {
          this.$message.error('获取角色失败！');
          this.loading = false;
        },
      );
    },
    fetchOrganizations() {
      this.$api.getOrganizations({
        code: this.$store.state.userinfo.org_code,
        id: this.$store.state.userinfo.org_id,
      }).then(
        ({ data }) => {
          this.organizations = [...this.organizations, ...data.data];
          const organizationMaps = {};
          this.eachOrganizations(this.organizations, organizationMaps);
          this.$set(this, 'organizationMaps', organizationMaps);
        },
        () => {
          this.$message.error('获取机构失败！');
        },
      );
    },
    eachOrganizations(data, map) {
      data.forEach((n) => {
        map[n.code] = n.name;
        if (n.subOrgs && n.subOrgs.length) {
          this.eachOrganizations(n.subOrgs);
        }
      });
    },
    onRemoveRoles() {
      if (!this.checkeds.length) {
        this.$message.error('请选择角色');
        return;
      }
      this.removeRoles(() => this.checkeds.forEach(id => this.removeRole(id)));
    },
    onRemoveRole(id) {
      this.removeRoles(() => this.removeRole(id));
    },
    removeRoles(callback) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '继续删除',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => callback());
    },
    removeRole(roleId) {
      this.$api.deleteRole(roleId).then(() => {
        const index = this.roles.findIndex(role => role.id === roleId);
        this.roles.splice(index, 1);
        if (roleId === this.checkedRoleId) this.opened = '';
      }).catch(() => this.$message.error('删除失败！'));
    },
    onEditUser(code) {
      this.checkedRoleCode = code;
      this.opened = 'roleUser';
      this.$root.scrollToTop(true);
    },
    onEditRole(id) {
      this.checkedRoleId = id;
      this.opened = 'roleEdit';
      this.$root.scrollToTop(true);
      if (!id) this.$refs.table.setCurrentRow();
    },
    onSelectionChange(val) {
      this.checkeds = val.map(v => v.id);
    },
    onRefresh(id) {
      if (id) this.checkedRoleId = id;
      this.fetchRoles();
    },
    onCommand(e) {
      this.filter.orgCode = e.code;
      this.filter.orgName = e.name;
      this.fetchRoles();
    },
  },
};
</script>
<style lang="scss" scoped>
.lx-container {
  flex: 1;
}
.filter {
  border-right: 1px solid #eee;
  width: 350px;
  transition: all 0.4s;
  padding-right: 25px;
  &.opened {
    border: none;
    width: 100%;
  }
}
.form{
  /deep/ {
    .el-form-item__content {
      justify-content: flex-end;
      display: flex;
    }
  }
}

.list {
  padding-left: 25px;
  flex: 1;
  position: relative;
}
.list-close{
  position: absolute;
  left: -15px;
  top: 0;
  color: #333;
}
</style>

