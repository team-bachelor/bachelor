<template>
  <section class="">
    <h2>带有倾向性</h2>
<div>
  <el-button
    plain
    @click="open3">
    成功
  </el-button>
  <el-button
    plain
    @click="open4">
    警告
  </el-button>
  <el-button
    plain
    @click="open5">
    消息
  </el-button>
  <el-button
    plain
    @click="open6">
    错误
  </el-button>
</div>

  </section>
</template>

<script>
export default {
  name: 'ExampleNotifcation',
  data() {
    return {};
  },
  created() {
  },
  mounted() {
  },
  methods: {
    open3() {
      this.$notify({
        title: '成功',
        message: '这是一条成功的提示消息',
        type: 'success',
      });
    },
    open4() {
      this.$notify({
        title: '警告',
        message: '这是一条警告的提示消息',
        type: 'warning',
      });
    },
    open5() {
      this.$notify.info({
        title: '消息',
        message: '这是一条消息的提示消息',
      });
    },
    open6() {
      this.$notify.error({
        title: '错误',
        message: '这是一条错误的提示消息',
      });
    },
  },
};
</script>
