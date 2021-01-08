<template>
  <section class="">
    <h2>不同状态</h2>
<div>
    <el-button :plain="true" @click="open2">成功</el-button>
  <el-button :plain="true" @click="open3">警告</el-button>
  <el-button :plain="true" @click="open">消息</el-button>
  <el-button :plain="true" @click="open4">错误</el-button>
</div>

<h2>可关闭</h2>
<div>
  <el-button :plain="true" @click="open5">消息</el-button>
  <el-button :plain="true" @click="open6">成功</el-button>
  <el-button :plain="true" @click="open7">警告</el-button>
  <el-button :plain="true" @click="open8">错误</el-button>
</div>
  </section>
</template>

<script>
export default {
  name: 'ExampleMessage',
  data() {
    return {};
  },
  created() {
  },
  mounted() {
  },
  methods: {
    open() {
      this.$message('这是一条消息提示');
    },
    open2() {
      this.$message({
        message: '恭喜你，这是一条成功消息',
        type: 'success',
      });
    },
    open3() {
      this.$message({
        message: '警告哦，这是一条警告消息',
        type: 'warning',
      });
    },
    open4() {
      this.$message.error('错了哦，这是一条错误消息');
    },
    open5() {
      this.$message({
        showClose: true,
        message: '这是一条消息提示',
      });
    },
    open6() {
      this.$message({
        showClose: true,
        message: '恭喜你，这是一条成功消息',
        type: 'success',
      });
    },

    open7() {
      this.$message({
        showClose: true,
        message: '警告哦，这是一条警告消息',
        type: 'warning',
      });
    },

    open8() {
      this.$message({
        showClose: true,
        message: '错了哦，这是一条错误消息',
        type: 'error',
      });
    },
  },
};
</script>
