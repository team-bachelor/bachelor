<template>
  <section>
      <h4>文件：{{$route.params.fileName}}</h4>
    <codemirror v-model="content" :options="cmOptions"></codemirror>
  </section>
</template>

<script>
import { codemirror } from 'vue-codemirror';
import 'codemirror/lib/codemirror.css';
import 'codemirror/theme/base16-light.css';

export default {
  components: {
    codemirror,
  },
  data() {
    return {
      initedCodemirror: false,
      content: '',
      cmOptions: {
        theme: 'base16-light',
        tabSize: 4,
        mode: 'text/javascript',
        lineNumbers: true,
        line: true,
        readOnly: true,
      },
    };
  },
  created() {
    this.fetch();
  },
  methods: {
    fetch() {
      this.$http.get(`/config/content/${this.$route.params.commitId}`, {
        params: {
          fileName: this.$route.params.fileName,
        },
      }).then(({ data }) => {
        if (data.status === 'OK') {
          this.content = data.data;
          return Promise.resolve();
        }
        return Promise.reject();
      }).catch(() => {
        this.$message.error('接口请求错误！');
      });
    },
  },
};

</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.handle label{
  visibility: hidden;
}
.handle /deep/{
  .el-button{
    display: block;
    width: 100%;
  }
}
</style>
