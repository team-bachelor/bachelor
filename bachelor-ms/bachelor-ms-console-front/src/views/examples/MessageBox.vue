<template>
  <section class="">
<h2>提示弹窗</h2>
<div>
  <el-button type="text" @click="open">点击打开 Message Box</el-button>
</div>
<h2>确认弹窗</h2>
<div>
  <el-button type="text" @click="open2">点击打开 Message Box</el-button>
</div>

  </section>
</template>

<script>
export default {
  name: 'ExampleMessageBox',
  data() {
    return {};
  },
  created() {
  },
  mounted() {
  },
  methods: {
    open() {
      this.$alert('这是一段内容', '标题名称', {
        confirmButtonText: '确定',
        callback: action => this.$message({
          type: 'info',
          message: `action: ${action}`,
        }),
      });
    },
    open2() {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!',
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除',
        });
      });
    },
  },
};
</script>
