<template>
<section>
  <div class="handler">
    <el-button size="mini" type="primary" :loading="loading" @click="onSave">{{id?'保存':'创建'}}</el-button>
  </div>
  <el-form ref="form" :model="formData" :rules="formRules" label-width="100px" style="width: 90%;">
	  
	<el-form-item label="序号" prop="sortNum">
	  <el-input size="mini" v-model="formData.sortNum"></el-input>
	</el-form-item>  
	  
    <el-form-item label="姓名" prop="bname">
      <el-input size="mini" v-model="formData.bname"></el-input>
    </el-form-item>
	
    <el-form-item label="性别" prop="gender">
      <el-select size="mini" v-model="formData.gender" placeholder="请选择性别" style="width:100%">
        <el-option v-for="item in genderList" :key="item.val"
          :label="item.name" :value="item.val"></el-option>
      </el-select>
    </el-form-item>
	
	<el-form-item label="手机号" prop="phone">
	  <el-input size="mini" v-model="formData.phone"></el-input>
	</el-form-item>
	
	<el-form-item label="所属机构" prop="groupIds">
		<el-cascader size="mini" style="width:100%;margin-top: 6px;"
			clearable
			ref="orgs"
		    v-model="formData.groupIds"
		    :options="organizations"
		    :props="{ expandTrigger: 'hover',value:'id',label:'bname',checkStrictly:true  }"
		    @change="handleChange"></el-cascader>
	</el-form-item>
	
	<el-form-item label="办公电话" prop="officePhone">
	  <el-input size="mini" v-model="formData.officePhone"></el-input>
	</el-form-item>
	<el-form-item label="工作单位" prop="officeName">
	  <el-input size="mini" v-model="formData.officeName"></el-input>
	</el-form-item>
	<el-form-item label="部门" prop="department">
	  <el-input size="mini" v-model="formData.department"></el-input>
	</el-form-item>
	<el-form-item label="家庭电话" prop="homePhone">
	  <el-input size="mini" v-model="formData.homePhone"></el-input>
	</el-form-item>
	<el-form-item label="家庭地址" prop="homeAddress">
	  <el-input size="mini" v-model="formData.homeAddress"></el-input>
	</el-form-item>
	<el-form-item label="邮箱" prop="email">
	  <el-input size="mini" v-model="formData.email"></el-input>
	</el-form-item>
	
  </el-form>
  
</section>
</template>

<script>

export default {
  name: 'contactsEdit',
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
		  "bname": "",
		  "bstatus": 0,
		  "department": "",
		  "email": "",
		  "gender": 0,
		  "genders": '',
		  "groupIds": [],
		  "groupId": 0,
		  "groupName": "",
		  "homeAddress": "",
		  "homePhone": "",
		  "id": 0,
		  "officeName": "",
		  "officePhone": "",
		  "phone": "",
		  "sortNum": 0,
		  "updateId": "",
		  "updateTime": ""
		},
      formRules: {
        sortNum: [
          { required: true, message: '请输入排序号', trigger: 'blur' },
        ],
        phone: [
          { required: true, message: '请输入电话号码', trigger: 'blur' },
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'change' },
        ],
		groupIds: [
		  { required: true, message: '请选择所属机构', trigger: 'change',type: 'array' },
		]
      },
      loading: false,
	  orgList:[],
    };
  },
  watch: {
    id: {
      handler(val) {
		 // console.log(val)
        if (this.$refs.form) this.$refs.form.resetFields();
        if (val) {
          this.fetchRole(val);
        }else{
			this.formData = {
			  "addId": "",
			  "addTime": "",
			  "bname": "",
			  "bstatus": 0,
			  "department": "",
			  "email": "",
			  "gender": 0,
			  "genders": '',
			  "groupId": 0,
			  "groupIds": [],
			  "groupName": "",
			  "homeAddress": "",
			  "homePhone": "",
			  "id": 0,
			  "officeName": "",
			  "officePhone": "",
			  "phone": "",
			  "sortNum": 0,
			  "updateId": "",
			  "updateTime": ""
			};
			this.formData.groupId = this.organizations[0].id;
		}
      },
      immediate: true,
      deep: false,
    },
  },
  created(){
	  this.formData.groupId = this.organizations[0].id;
  },
  methods: {
	handleChange(value) {
		// console.log(this.$refs["orgs"].getCheckedNodes()[0].value)
		// console.log(this.$refs["orgs"].getCheckedNodes()[0].label)
		this.formData.groupId = this.$refs["orgs"].getCheckedNodes()[0].value;
		this.formData.groupName = this.$refs["orgs"].getCheckedNodes()[0].label;
	},
    fetchRole(id) {
		// console.log(id)
		this.$api.getContacts(id).then(({ data }) => {
			// console.log(data)
			this.formData = data.data;
			// console.log(this.getParent(this.organizations,data.data.groupId))
			this.formData.groupIds = this.getParent(this.organizations,data.data.groupId);
		}, () => {
		  this.$message.error('查询失败');
		});
	},
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
    onSave() {
      this.$refs.form.validate((valid) => {
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
      this.$api.addContacts(this.formData).then(({ data }) => {
        this.$message.success('新增角色成功');
        this.$refs.form.resetFields();
        this.$emit('refresh');
        this.loading = false;
      }, () => {
        this.$message.error('新增角色失败');
        this.loading = false;
      });
    },
    update() {
      this.$api.updateContacts(this.formData).then(() => {
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

