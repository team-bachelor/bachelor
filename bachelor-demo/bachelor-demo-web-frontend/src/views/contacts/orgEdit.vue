<template>
<section>
  <div class="handler">
    <el-button size="small" type="primary" :loading="loading" @click="onSave">{{id?'保存':'创建'}}</el-button>
  </div>
  <el-form ref="forms" :model="formData" :rules="formRules" label-width="100px" style="width: 90%;">
	  
	<el-form-item label="序号" prop="sortNum">
	  <el-input size="small" v-model="formData.sortNum" clearable></el-input>
	</el-form-item>  
	  
	<el-form-item label="机构名称" prop="bname">
	  <el-input size="small" v-model="formData.bname" clearable></el-input>
	</el-form-item>
	  
	<el-form-item label="上级机构" prop="groupIds">
		<el-cascader size="small" style="width:100%;margin-top: 6px;"
			clearable
			placeholder="请选择上级机构 【不选则默认为父机构】"
			ref="orgse"
		    v-model="formData.groupIds"
		    :options="organizations"
		    :props="{ expandTrigger: 'hover',value:'id',label:'bname',checkStrictly:true  }"
		    @change="handleChange"></el-cascader>
			
	</el-form-item>
	
	<el-form-item label="主管负责人" prop="leadingPerson">
	  <el-input size="small" v-model="formData.leadingPerson" clearable></el-input>
	</el-form-item>
	
	<el-form-item label="负责人电话" prop="leadingPhone">
	  <el-input size="small" v-model="formData.leadingPhone" clearable></el-input>
	</el-form-item>
	<el-form-item label="值班电话" prop="dutyPhone">
	  <el-input size="small" v-model="formData.dutyPhone" clearable></el-input>
	</el-form-item>
	<el-form-item label="传真" prop="fax">
	  <el-input size="small" v-model="formData.fax" clearable></el-input>
	</el-form-item>
	<el-form-item label="地址" prop="address">
	  <el-input size="small" v-model="formData.address" clearable></el-input>
	</el-form-item>
	<el-form-item label="描述" prop="description">
	  <el-input size="small" type="textarea" v-model="formData.description" clearable></el-input>
	</el-form-item>
	
  </el-form>
  
</section>
</template>

<script>

export default {
  name: 'orgEdit',
  components: {},
  props: {
    organizations: Array,
    id: [Number, String],
  },
  data() {
    return {
		genderList:[
			{
				name:'保密',
				val:0
			},
			{
				name:'男',
				val:1
			},
			{
				name:'女',
				val:2
			}
		],
		gender:'',
      formData: {
		  "addId": "",
		  "addTime": "",
		  "address": "",
		  "bname": "",
		  "bstatus": 0,
		  "description": "",
		  "dutyPhone": "",
		  "fax": "",
		  "id": 0,
		  "leadingPerson": "",
		  "leadingPhone": "",
		  "parentId": 0,
		  "sortNum": 0,
		  "updateId": "",
		  "updateTime": "",
		  "groupIds": [],
		},
      formRules: {
        sortNum: [
          { required: true, message: '请输入排序号', trigger: 'blur' },
        ],
        bname: [
          { required: true, message: '请输入机构名称', trigger: 'blur' },
        ],
      },
      loading: false,
	  orgList:[],
    };
  },
  watch: {
    id: {
      handler(val) {
		 // console.log(val)
        if (this.$refs.forms) this.$refs.forms.resetFields();
        if (val) {
          this.fetchRole(val);
        }else{
			this.formData = {
			  "addId": "",
			  "addTime": "",
			  "address": "",
			  "bname": "",
			  "bstatus": 0,
			  "description": "",
			  "dutyPhone": "",
			  "fax": "",
			  "id": 0,
			  "leadingPerson": "",
			  "leadingPhone": "",
			  "parentId": 0,
			  "sortNum": 0,
			  "updateId": "",
			  "updateTime": "",
			  "groupIds": [],
			};
			this.formData.parentId = this.organizations[0].id;
		}
      },
      immediate: true,
      deep: false,
    },
  },
  created(){
	  this.formData.parentId = this.organizations[0].id;
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
					  rev(data2, node.parentId);
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
		this.formData.parentId = this.$refs["orgse"].getCheckedNodes()[0].value;
	},
    fetchRole(id) {
		// console.log(id)
		this.$api.getOrg(id).then(({ data }) => {
			// console.log(data)
			this.formData = data.data;
			// console.log(data.data)
			// console.log(this.getParent(this.organizations,data.data.parentId))
			this.formData.groupIds = this.getParent(this.organizations,data.data.parentId);
		}, () => {
		  this.$message.error('查询失败');
		});
	},
    onSave() {
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
      this.$api.addOrg(this.formData).then(({ data }) => {
        this.$message.success('新增角色成功');
        this.$refs.forms.resetFields();
        this.$emit('refresh');
        this.loading = false;
      }, () => {
        this.$message.error('新增角色失败');
        this.loading = false;
      });
    },
    update() {
      this.$api.updateOrg(this.formData).then(() => {
        this.$message.success('保存角色成功');
        this.$emit('refresh');
        this.loading = false;
      }, () => {
        this.$message.error('保存角色失败');
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

