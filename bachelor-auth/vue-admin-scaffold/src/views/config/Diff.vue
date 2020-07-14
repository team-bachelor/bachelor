<template>
  <section>
    <h3>文件：{{$route.params.fileName}}</h3>
    <codemirror v-if="initedCodemirror" :merge="true" :options="cmOption"></codemirror>
  </section>
</template>

<script>
import { codemirror } from 'vue-codemirror';
import 'codemirror/lib/codemirror.css';
import 'codemirror/theme/base16-light.css';

// merge js
import 'codemirror/addon/merge/merge';
// merge css
import 'codemirror/addon/merge/merge.css';
// google DiffMatchPatch
import DiffMatchPatch from 'diff-match-patch';
// DiffMatchPatch config with global
window.diff_match_patch = DiffMatchPatch;
window.DIFF_DELETE = -1;
window.DIFF_INSERT = 1;
window.DIFF_EQUAL = 0;
export default {
  components: {
    codemirror,
  },
  data() {
    return {
      initedCodemirror: false,
      cmOption: {
        theme: 'base16-light',
        value: '',
        orig: '',
        connect: 'align',
        mode: 'text/html',
        lineNumbers: true,
        collapseIdentical: false,
        highlightDifferences: true,
        // allowEditingOriginals: false,
      },
    };
  },
  created() {
    this.fetch();
  },
  methods: {
    fetch() {
      this.$http.get(`/config/diff/${this.$route.params.commitId}`, {
        params: {
          fileName: this.$route.params.fileName,
        },
      }).then(({ data }) => {
        if (data.status === 'OK') {
          const [value, orig] = data.data;
          this.cmOption.value = value;
          this.cmOption.orig = orig;
          this.initedCodemirror = true;
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
