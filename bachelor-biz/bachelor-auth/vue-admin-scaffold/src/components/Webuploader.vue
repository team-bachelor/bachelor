<template>
  <div class="webuploader" ref="picker">
    <slot/>
  </div>
</template>

<script>
const defaultOptions = {
  auto: false,
  server: '/',
  pick: {},
  fileNumLimit: 2,
  accept: {
    title: 'Images',
    extensions: 'gif,jpg,jpeg,bmp,png',
    mimeTypes: 'image/*',
  },
  headers: {
    token: '111',
  },
};

export default {
  name: 'Echart',
  props: {
    options: {
      type: Object,
      default() {
        return defaultOptions;
      },
    },
  },
  data() {
    return {
      loading: true,
      fileList: [],
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      /* eslint-disable */
      const options = Object.assign({}, defaultOptions, this.options);
      options.pick = {
        id: this.$refs.picker,
        multiple: options.multiple,
      };
      this.uploader = WebUploader.create(options);
      this.uploader.on('all', (eventName, ...args) => {
        console.log(eventName);
        if (this[eventName]) this[eventName](...args);
        this.$emit(eventName, ...args);
      });
    },
    fileQueued(file) {
      this.fileList.push(file);
    },
    filesQueued(files) {
      if (!files.length) return;
      if (files.length > this.options.fileNumLimit) {
        this.uploader.reset();
        $(this.$refs.picker).find('input').val('');

        // this.uploader.getFiles().forEach((file) => {
        //   this.uploader.removeFile(file, true);
        // })
        this.$emit('error', 'Q_EXCEED_NUM_LIMIT');
        return;
      }
      this.uploader.upload();
    },
    uploadBeforeSend(object, data, headers) {
      Object.assign(headers, this.options.header);
    },
    uploadProgress(file, percentage) {
      file.percentage = percentage;
    },
    uploadFinished() {
      this.fileList = [];
      this.uploader.reset();
      $(this.$refs.picker).find('input').val('');
      // this.uploader.getFiles().forEach((file) => {
      //   this.uploader.removeFile(file, true);
      // })
    },
    error(type) {
      this.uploader.reset();
      $(this.$refs.picker).find('input').val('');
      // this.uploader.getFiles().forEach((file) => {
      //   this.uploader.removeFile(file, true);
      // })
    },
  },
};
</script>
<style>
.webuploader{
  display: inline-block;
  position: relative;
}
.webuploader input{
  opacity: 0;
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  right: 0;
}
</style>
