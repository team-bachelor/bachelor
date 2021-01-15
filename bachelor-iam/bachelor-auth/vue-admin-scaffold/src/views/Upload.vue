<template>
<section>
  <web-uploader @error="onError">
    <el-button>上传</el-button>
  </web-uploader>

  <LxUploader
    ref="upload"
    :on-error="onError"
    :on-exceed="onExceed"
    :on-change="onChange"
    action="https://jsonplaceholder.typicode.com/posts/"
    multiple
    :file-list="fileList"
  :limit="3">
    <el-button size="small" type="primary">点击上传</el-button>
  </LxUploader>
</section>
</template>
<script>
import WebUploader from '@/components/Webuploader.vue';
import LxUploader from '@/components/Uploader/index.vue';

export default {
  name: 'Upload',
  components: { WebUploader, LxUploader },
  data() {
    return {
      fileList: [],
      uploadFiles: [],
    };
  },
  mounted() {
    console.log(this.$refs.upload)
    this.uploadFiles = this.$refs.upload.uploadFiles;
    this.$watch('uploadFiles', (val) => {
      console.log(10)
    }, {
      deep: true,
    });
  },
  methods: {
    onExceed(type) {
      console.log(type);
    },
    onError(){},
    onChange(file, fileList){
    }
  },
};
</script>
<style>
.el-upload__input {
    display: none;
}
</style>
