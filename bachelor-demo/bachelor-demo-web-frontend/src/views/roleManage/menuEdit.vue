<template>
<section>
  <div class="handler">
    <el-button size="small" type="primary" :loading="loading" @click="onSave">{{id?'保存':'创建'}}</el-button>
	<el-link type="primary" href="https://element.eleme.cn/#/zh-CN/component/icon"
	 target="_blank" style="margin-left: 40px;">
		elementUI图标样式库
	</el-link>
  </div>
  <el-form ref="forms" :model="formData" :rules="formRules" label-width="100px" style="width: 90%;">
	  
	<el-form-item label="排序号" prop="seqOrder">
	  <el-input size="small" v-model="formData.seqOrder" clearable></el-input>
	</el-form-item> 
	  
	<el-form-item label="菜单名称" prop="name">
	  <el-input size="small" v-model="formData.name" clearable></el-input>
	</el-form-item>
	  
	<el-form-item label="路径" prop="uri">
	  <el-input size="small" v-model="formData.uri" placeholder="请输入路径，如：/url" clearable></el-input>
	</el-form-item>
	
	<el-form-item label="路由" prop="component">
	  <el-input size="small" v-model="formData.component" placeholder="请输入路由，如：flieUrl/page，文件目录/页面名，父级路由需命名为‘Layout’" clearable></el-input>
	</el-form-item>
	
	<el-form-item label="上级菜单" prop="parentID">
		<!-- <el-cascader size="small" style="width:100%;margin-top: 6px;"
			clearable
			placeholder="请选择上级菜单 【不选则默认为一级菜单】"
			ref="orgse"
		    v-model="formData.parentID"
		    :options="organizations"
		    :props="{ expandTrigger: 'hover',value:'id',label:'title',checkStrictly:true  }"
		    @change="handleChange">
		</el-cascader> -->
			
		<el-select v-model="formData.parentID" @change="handleChange"
		style="width: 100%;" size="small" clearable
		placeholder="请选择上级菜单 【不选则默认为一级菜单】">
		    <el-option
		      v-for="item in organizations"
		      :key="item.id"
		      :label="item.title"
		      :value="item.id">
		    </el-option>
		</el-select>
			
	</el-form-item>
	
	<el-form-item label="图标" prop="icon">
	  <el-input size="small" v-model="formData.icon" placeholder="elementUI图标" clearable></el-input>
	</el-form-item>
	<!-- <el-form-item label="传真" prop="fax">
	  <el-input size="small" v-model="formData.fax" clearable></el-input>
	</el-form-item>
	<el-form-item label="地址" prop="address">
	  <el-input size="small" v-model="formData.address" clearable></el-input>
	</el-form-item>
	<el-form-item label="描述" prop="description">
	  <el-input size="small" type="textarea" v-model="formData.description" clearable></el-input>
	</el-form-item> -->
	
  </el-form>
  
</section>
</template>

<script>

export default {
  name: 'menuEdit',
  components: {},
  props: {
    organizations: Array,
	pageData: Object,
    id: [Number, String],
  },
  data() {
    return {
		gender:'',
		formData: {
		  "id": "",
		  "component": "",
		  "icon": "",
		  "parentID": "",
		  "uri": "",
		  "name": "",
		  type:'1',
		  comment:'',
		  seqOrder:'1',
		},
		formRules: {
			seqOrder: [
				{ required: true, message: '请输入排序号', trigger: 'blur' },
			],
			name: [
				{ required: true, message: '请输入菜单名称', trigger: 'blur' },
			],
			uri: [
				{ required: true, message: '请输入路径，如：/url', trigger: 'blur' },
			],
			component: [
				{ required: true, message: '请输入路由，如：flieUrl/page，文件目录/页面名，父级路由需命名为‘Layout’', trigger: 'blur' },
			],
		},
      loading: false,
	  orgList:[],
    };
  },
  watch: {
    id: {
      handler(val) {
        if (this.$refs.forms) this.$refs.forms.resetFields();
        if (val) {
          // this.fetchRole(val);
		  // console.log(this.pageData)
		  let data = JSON.parse(JSON.stringify(this.pageData))
		  for(let key in data){
			  for(let k in this.formData){
				  if(key == k){
					  this.formData[k] = data[k];
				  }
			  }
		  }
        }else{
			this.formData = {
			  "id": "",
			  "component": "",
			  "icon": "",
			  "parentID": "",
			  "uri": "",
			  "name": "",
			  type:'1',
			  comment:'',
			  seqOrder:'1',
			};
			// this.$refs.forms.resetFields();
			// this.formData.parentID = this.organizations[0].id;
		}
      },
      immediate: true,
      deep: false,
    },
  },
  created(){
	  // this.formData.parentID = this.organizations[0].id;
  },
  methods: {
	  getParent(data2, nodeId2) {
		  var arrRes = [];
		  if (data2.length == 0) {
			  if (!!nodeId2) {
				  arrRes.unshift(data2.id)//追加指定数据
			  }
			  return arrRes;
		  }
		  let rev = (data, nodeId) => {
			  for (var i = 0, length = data.length; i < length; i++) {
				  let node = data[i];
				  if (node.id == nodeId) {
					  arrRes.unshift(node.id)//追加指定数据
					  rev(data2, node.parentID);
					  break;
				  }
				  else {
					  if (!!node.children) {
						  rev(node.children, nodeId);
					  }
				  }
			  }
			  return arrRes;
		  };
		  arrRes = rev(data2, nodeId2);
		  return arrRes;
	  },
	handleChange(value) {
		// console.log(this.$refs["orgs"].getCheckedNodes()[0].value)
		// console.log(this.$refs["orgs"].getCheckedNodes()[0].label)
		// this.formData.parentID = this.$refs["orgse"].getCheckedNodes()[0].value;
		this.formData.parentID = value;
		// console.log(this.formData,value)
	},
    fetchRole(id) {
		// console.log(id)
		this.$api.getOrg(id).then(({ data }) => {
			// console.log(data)
			this.formData = data.data;
			// console.log(data.data)
			// console.log(this.getParent(this.organizations,data.data.parentID))
			// this.formData.groupIds = this.getParent(this.organizations,data.data.parentID);
		}, () => {
		  this.$message.error('查询失败');
		});
	},
    onSave() {
		this.formData.comment = this.formData.name;
		this.formData.type = !!this.formData.parentID?'2':'1';
		
      this.$refs.forms.validate((valid) => {
        if (!valid) return;
        this.loading = true;
        if (this.id) {
          this.update();
        } else {
          this.add();
        }
      });
    },
    add() {
      this.$api.addMenu(this.formData).then(({ data }) => {
        this.$message.success('新增菜单成功');
        this.$refs.forms.resetFields();
        this.$emit('refresh');
        this.loading = false;
      }, () => {
        this.$message.error('新增菜单失败');
        this.loading = false;
      });
    },
    update() {
      this.$api.upMenu(this.formData).then(() => {
        this.$message.success('编辑菜单成功');
		this.$refs.forms.resetFields();
        this.$emit('refresh');
        this.loading = false;
      }, () => {
        this.$message.error('编辑菜单失败');
        this.loading = false;
      });
    },
  },
};
</script>
<style lang="scss" scoped>
	
	.el-form-item {
	    margin-bottom: 15px;
	}
.handler{
  text-align:left;
  margin-bottom:20px
}
/deep/{
  .el-tabs{
    padding-top: 20px;
  }
}
</style>

