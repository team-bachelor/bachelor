<template>
	<section>
		<section class="lx-container">
			<div class="filter" :class="{opened:!opened}">
				
				<el-form class="form" ref="filter" :model="filter">
				  <el-row :gutter="24">
					  
					<el-col :span="6">
					  <el-form-item label="">
					    <el-cascader style="width:100%;margin-top: 6px;"
					    	clearable
							size="mini"
					    	ref="orgs"
							placeholder="请选择组织"
					        v-model="groupIds"
					        :options="organizations"
					        :props="{ expandTrigger: 'hover',value:'id',label:'bname',checkStrictly:true  }"
					        @change="handleChange"></el-cascader>
					  </el-form-item>
					</el-col>  
					  
				    <el-col :span="6">
				      <el-form-item label="">
				        <el-input v-model="filter.bname" size="mini" placeholder="请输入姓名"
				          ></el-input>
				      </el-form-item>
				    </el-col>
					
					<el-col :span="6">
					  <el-form-item label="">
					    <el-input v-model="filter.phone" size="mini" placeholder="请输入电话号码"
					      ></el-input>
					  </el-form-item>
					</el-col>
					
				    <el-col :span="6">
				      <el-form-item>
				        <el-button type="primary" size="mini" round @click.prevent="fetchRoles" :loading="loading">
				          <i class="el-icon-search"/>
				          <span>查找</span>
				        </el-button>
				      </el-form-item>
				    </el-col>
				  </el-row>
				</el-form>
				
				<div class="hairline"></div>

				<div class="btn">
					<el-button size="mini" @click="onEditRole(0)">新增通讯录</el-button>
					<el-button
					  size="mini"
					  type="primary"
					  @click="excelImport">导入通讯录</el-button>
					  
					<el-button type="warning" icon="el-icon-download" @click="excel" size="mini">下载模板</el-button>
					 
				</div>
				<!-- <el-button size="small" type="danger" @click="onRemoveRoles" >删除</el-button> -->
				<el-table :data="roles" :loading="loading" @selection-change="onSelectionChange" height="620">
					<el-table-column type="selection" width="55">
					</el-table-column>
					<el-table-column prop="groupId" label="ID" show-overflow-tooltip>
					</el-table-column>
					<el-table-column prop="bname" label="姓名" width="100" show-overflow-tooltip>
					</el-table-column>
					<el-table-column prop="phone" label="电话" width="120" show-overflow-tooltip>
					</el-table-column>
					<el-table-column prop="gender" label="性别" width="55" show-overflow-tooltip>
						<template slot-scope="scope">
							{{scope.row.gender==0?'保密':scope.row.gender==1?'男':'女'}}
						</template>
					</el-table-column>
					<el-table-column prop="homeAddress" label="地址" show-overflow-tooltip>
					</el-table-column>
					<el-table-column prop="officeName" label="工作单位" show-overflow-tooltip>
					</el-table-column>
					<el-table-column prop="groupName" label="所属机构" show-overflow-tooltip>
					</el-table-column>
					<el-table-column prop="department" label="部门" width="120" show-overflow-tooltip>
					</el-table-column>
					<el-table-column prop="addTime" label="时间" show-overflow-tooltip>
					</el-table-column>
						
					<el-table-column width="232">
					  <template slot-scope="scope">
					  <el-button
					    size="mini"
					    type="primary"
					    @click="onEditRole(scope.row.id)">编辑</el-button>
					  <el-button
					    size="mini"
					    type="danger"
					    @click="onRemoveRole(scope.row.id)">删除</el-button>
					  </template>
					</el-table-column>
					<div slot="empty">{{loading?'加载中...':'无数据'}}</div>
				</el-table>
			</div>

			<!-- <div class="list" v-if="opened">
				<el-button circle size="mini" class="list-close" icon="el-icon-d-arrow-right" @click="opened=''">
				</el-button>
				<contacts-edit v-if="opened=='contactsEdit'" :id.sync="checkedRoleId"
				  :organizations="organizations"
				  @refresh="onRefresh"/>
			</div> -->
			
			<el-dialog
			  :title="(checkedRoleId?'编辑':'新增')+'通讯录'"
			  :visible.sync="dialogVisible"
			  width="40%"
			  :before-close="handleClose">
			  <contacts-edit :id.sync="checkedRoleId"
			    :organizations="organizations"
			    @refresh="onRefresh"/>
			</el-dialog>

		</section>
		
		<importExcel :types="dataType" :title="fileTit" :showDialogs="showDialog" @uploadFileFun="uploadFileFun" />
		
		<el-pagination @current-change="handleCurrentChange" :page-size="pageSize" :pager-count="5" 
		layout="total, prev, pager, next"
		 :total="totalPageNum">
		</el-pagination>
	</section>
</template>

<script>
	import contactsEdit from '../contacts/contactsEdit.vue';
	import importExcel from "@/components/importExcel";
	export default {
		name: 'contactsManage',
		components: {
			contactsEdit,
			importExcel
		},
		data() {
			return {
				dialogVisible: false,
				dataType: 2,
				fileTit:'',
				showDialog: 0,
				
				roles: [],
				checkedRoleId: '',
				checkedRoleCode: '',
				organizations: [],
				organizationMaps: {},
				filter: {
					bname:'',
					phone:'',
					groupId:0,
				},
				groupIds:[],
				checkeds: [],
				opened: '',
				loading: true,
				totalPageNum: 0,
				currentPage: 1,
				pageSize: 10,
			};
		},
		computed: {
			
		},
		created() {
			this.fetchRoles();
			this.initOrg();
		},
		methods: {
			handleClose(done) {
				done();
			},
			handleChange(value) {
				// console.log(!!this.$refs["orgs"].getCheckedNodes()[0])
				if(!!this.$refs["orgs"].getCheckedNodes()[0]){
					this.filter.groupId = this.$refs["orgs"].getCheckedNodes()[0].value;
				}else{
					this.filter.groupId = '';
				}
				// console.log(!!this.$refs["orgs"].getCheckedNodes()[0].value)
				this.currentPage = 1;
			},
			excel(){
				let tHeader = [
				  {key:'id',name:'姓名'},
				  {key:'fromAddress',name:'手机号'},
				  {key:'toAddress',name:'邮箱'},
				  {key:'vehicleNo',name:'性别'},
				  {key:'name',name:'办公电话'},
				  {key:'bcargoOwner',name:'家庭电话'},
				  {key:'cargPrice',name:'家庭地址'},
				  {key:'bvehicleOwner',name:'所属机构'},
				  {key:'vehilePrice',name:'工作单位'},
				  {key:'price',name:'部门'},
				  {key:'num',name:'序号'},
				];
				let arr = [
					{
						id:'张明',
						fromAddress:'13111111111',
						toAddress:'az7582@qq.com',
						vehicleNo:'男',
						name:'5343968521',
						bcargoOwner:'18866669999',
						cargPrice:'山东省德州市开发区',
						bvehicleOwner:'北京大学',
						vehilePrice:'易罐',
						price:'技术部',
						num:'1',
					}
				]
				let list = {
				  fileName:'通讯录信息',//文件名
				  tHeader:tHeader,//生成Excel表格的头部标题栏
				  excelData:arr,//需要导出Excel的数据集合
				}
				this.$exportExcel.exportExcel(list);
			},
			excelImport(){
			  this.showDialog++;
			},
			uploadFileFun(e){
			  // console.log(e+"刷新")
			  this.fetchRoles();
			},
			onSelectionChange(val) {
				this.checkeds = val.map(v => v.id);
			},
			onEditRole(id) {
				this.checkedRoleId = id;
				// this.opened = 'contactsEdit';
				this.dialogVisible = true;
				// this.$root.scrollToTop(true);
			},
			onRemoveRole(id) {
				this.removeRoles(() => this.removeRole(id));
			},
			initOrg() {
				this.$api.orgTree().then(
					({data}) => {
						// console.log('获取机构',data.data)
						this.organizations = data.data;
					},
					() => {
						this.$message.error('获取机构失败！');
					},
				);
			},

			removeRoles(callback) {
				this.$confirm('此操作将永久删除, 是否继续?', '提示', {
					confirmButtonText: '继续删除',
					cancelButtonText: '取消',
					type: 'warning',
				}).then(() => callback());
			},
			removeRole(roleId) {
				this.$api.delContacts(roleId).then(() => {
					const index = this.roles.findIndex(role => role.id === roleId);
					this.roles.splice(index, 1);
					if (roleId === this.checkedRoleId) this.opened = '';
					this.$message.success('成功');
				}).catch(() => this.$message.error('删除失败！'));
			},
			handleCurrentChange(e) {
				this.currentPage = e;
				this.fetchRoles();
			},
			fetchRoles() {
				this.loading = true;
				this.$http.post("/contact/params?pageNum="+
				this.currentPage+
				"&pageSize="+this.pageSize,
				this.filter).then((data) => {
				    // console.log(data.data.data)
					this.totalPageNum = data.data.data.total;
					this.roles = data.data.data.list;
					this.loading = false;
				});
			},
			onRefresh() {
				// if (id) this.checkedRoleId = id;
				this.dialogVisible = false;
				this.fetchRoles();
			},

		},
	};
</script>
<style lang="scss" scoped>
	.lx-container {
		flex: 1;
	}
	/deep/.el-dialog__body {
	    padding: 10px 20px;
	}
	.filter {
		border-right: 1px solid #eee;
		width: 50%;
		transition: all 0.4s;
		padding-right: 25px;

		&.opened {
			border: none;
			width: 100%;
		}

		.btn {
			padding-bottom: 15px;
		}
		.el-form{
			height: 40px;
		}
	}

	/deep/ {
		.el-form-item__content {
			justify-content: flex-end;
			display: flex;
		}
	}

	.list {
		padding-left: 25px;
		flex: 1;
		position: relative;
	}

	.list-close {
		position: absolute;
		left: -15px;
		top: 0;
		color: #333;
	}
</style>
