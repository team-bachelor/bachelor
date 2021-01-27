<template>
  <el-dialog
    :title="'批量导入'+this.title"
    :visible.sync="dialogVisible"
    width="30%">
    <input type="file" ref="inputer"
           id="avatar" name="avatar" @change="changeFile"
           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
<!--    <el-input type="file" @change="changeFile" v-model="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">-->
<!--      <el-button>批量导入</el-button>-->
<!--    </el-input>-->
    <el-button @click="uploadFile" type="text">上传</el-button>
  </el-dialog>


</template>

<script>
  export default {
    name: "importExcel",
    data(){
      return{
        dialogVisible:false,
        formData:null,
        file:null
      }
    },
    methods:{
      changeFile(file){

        let inputDOM = this.$refs.inputer;
        this.file = inputDOM.files[0];// 通过DOM取文件数据
        let size = Math.floor(this.file.size / 1024);//计算文件的大小　
        this.formData=new FormData();//new一个formData事件
        this.formData.append("file",this.file); //将file属性添加到formData里
        this.formData.append('dataType',this.types)
        //此时formData就是我们要向后台传的参数了
      },
      uploadFile(){
		  if(!!this.file){
			  this.$http({
			    headers: {
			      'Content-Type':'multipart/form-data;'
			    },
			    url: "import",
			    method: "post",
			    data: this.formData
			  })
			    .then(r => {
					// console.log(r.data)
			      if (r.data.status == 'OK'){
			        this.dialogVisible = false;
			        this.$message.success('上传成功');
			        this.$emit("uploadFileFun", this.types);
			      }else {
			        this.$message.error('上传失败')
			      }
			      // this.$emit("input", r.result_data);
			    })
			    .finally(() => {
			      // loading.close();
			    });
		  }else{
			  this.$message.error('请选择文件')
		  }
        
      },
    },
    props:['types','showDialogs','title'],
    mounted() {
    },
    watch:{
      showDialogs:function (n,o) {
        if (n){
          this.dialogVisible = true
        }
      }
    }
  }
</script>

<style scoped>

</style>
