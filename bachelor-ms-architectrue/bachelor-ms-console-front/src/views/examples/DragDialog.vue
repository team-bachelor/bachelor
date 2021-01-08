<template>
<section>
  <el-button type="primary" @click="dialogVisible = true">打开可拖动对话框</el-button>
  <el-dialog v-drag-dialog :visible.sync="dialogVisible" title="按住头部拖动">
    <h1>你  好 ！</h1>
  </el-dialog>
</section>
</template>

<script>
export default {
  name: 'ExampleDragDialog',
  data() {
    return {
      dialogVisible: false,
    };
  },
  created() {
  },
  mounted() {
  },
  methods: {
  },
};
</script>
