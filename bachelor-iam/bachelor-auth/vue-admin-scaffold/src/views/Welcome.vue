<template>
<section class="container">
  <div>
    <div class="item">
      你好，{{userinfo.user_name}}
    </div>
    <dl class="item">
      <dt>机构名称：</dt>
      <dd>{{userinfo.org_name}}</dd>
    </dl>
    <dl class="item">
      <dt>部门名称：</dt>
      <dd>{{userinfo.dept_name}}</dd>
    </dl>
    <dl class="item">
      <dt>角色：</dt>
      <dd>{{userinfo.role.map(r=>r.name).join('/')}}</dd>
    </dl>
    <h3>快捷入口</h3>
    <div class="list">
      <span v-for="subitems in menus" :key="subitems.path">
        <el-button type="text" v-for="item in subitems.children" :key="item.path" style="margin-right:15px;">{{item.title}}</el-button>
      </span>
    </div>
  </div>
</section>
</template>
<script>
export default {
  computed: {
    menus() {
      return this.$store.state.menu;
    },
    userinfo() {
      return this.$store.state.userinfo;
    },
  },
};
</script>
<style lang="scss" scoped>
.container{
  font-size: 14px;
  text-align: left;
  color: #333;
}
h3, h4{
  margin: 5px 0;
}
h3{
  margin-top: 20px;
  border-bottom: 1px solid #0da5d344;
  padding-bottom: 5px;
  padding-left: 2px;
  min-width: 420px;
}
.list{
  padding:5px 10px;
}
.item{
  color: #333;
  margin: 10px 0;
  clear: both;
  height: 20px;
  line-height: 20px;
  dt{
    width: 76px;
    float: left;
    color: #888;
    text-align: right;
    margin: 0;
  }
  dd{
    float: left;
    margin: 0;
    padding-left: 5px;
  }
}

</style>