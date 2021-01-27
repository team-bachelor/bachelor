<template>
	<section>
		<section class="lx-container">
			<div class="filter" :class="{opened:!opened}">
				<!-- <div class="hairline"></div> -->
				<div class="btn">
					<el-button size="small" @click="onEditRole(0)">新增组织机构</el-button>
					 <span class="myicon icon-zj"></span>
				</div>

				<!-- <el-button size="small" type="danger" @click="onRemoveRoles" >删除</el-button> -->
				<el-table :data="roles" :loading="loading" @selection-change="onSelectionChange"
				height="640"
				row-key="id"
				:tree-props="{children: 'children', hasChildren: 'hasChildren'}">
					
					<el-table-column type="selection" width="55">
					</el-table-column>
					<el-table-column prop="bname" label="机构名称" show-overflow-tooltip>
					</el-table-column>
					<!-- <el-table-column prop="id" label="ID">
					</el-table-column> -->
					<el-table-column prop="leadingPerson" label="主管负责人" width="120" show-overflow-tooltip>
					</el-table-column>
					<el-table-column prop="leadingPhone" label="负责人电话" show-overflow-tooltip>
					</el-table-column>
					<el-table-column prop="dutyPhone" label="值班电话" show-overflow-tooltip>
					</el-table-column>
					<el-table-column prop="fax" label="传真" show-overflow-tooltip>
					</el-table-column>
					<el-table-column prop="address" label="地址" show-overflow-tooltip>
					</el-table-column>
					<el-table-column prop="description" label="描述" show-overflow-tooltip>
					</el-table-column>
					<el-table-column prop="addTime" label="时间" show-overflow-tooltip>
					</el-table-column>
						
					<el-table-column width="232">
					  <template slot-scope="scope">
					  <el-button
					    size="small"
					    type="primary"
					    @click="onEditRole(scope.row.id)">编辑</el-button>
					  <el-button
					    size="small"
					    type="danger"
					    @click="onRemoveRole(scope.row.id)">删除</el-button>
					  </template>
					</el-table-column>
					<div slot="empty">{{loading?'加载中...':'无数据'}}</div>
				</el-table>
			</div>

			<!-- <div class="list" v-if="opened">
				<el-button circle size="small" class="list-close" icon="el-icon-d-arrow-right" @click="opened=''">
				</el-button>
				<org-edit v-if="opened=='contactsEdit'" :id.sync="checkedRoleId"
				  :organizations="organizations"
				  @refresh="onRefresh"/>
			</div> -->
			
			<el-dialog
			  :title="(checkedRoleId?'编辑':'新增')+'组织'"
			  :visible.sync="dialogVisible"
			  width="40%"
			  :before-close="handleClose">
			  <org-edit :id.sync="checkedRoleId"
			    :organizations="organizations"
			    @refresh="onRefresh"/>
			</el-dialog>

		</section>
		
		<importExcel :types="dataType" :title="fileTit" :showDialogs="showDialog" />
		
		<!-- <el-pagination @current-change="handleCurrentChange" :page-size="pageSize" :pager-count="5" 
		layout="total, prev, pager, next"
		 :total="totalPageNum">
		</el-pagination> -->
	</section>
</template>

<script>
	import orgEdit from '../contacts/orgEdit.vue';
	import importExcel from "@/components/importExcel";
	export default {
		name: 'orgManage',
		components: {
			orgEdit,
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
				},
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
			// this.fetchRoles();
			this.initOrg();
		},
		methods: {
			handleClose(done) {
				done();
			},
			excel(){
				let tHeader = [
				  {key:'id',name:'组织机构名称'},
				  {key:'fromAddress',name:'上级机构'},
				  {key:'toAddress',name:'排序号'},
				  {key:'vehicleNo',name:'地址'},
				  {key:'name',name:'值班电话'},
				  {key:'bcargoOwner',name:'主管负责人'},
				  {key:'cargPrice',name:'负责人联系电话'},
				  {key:'bvehicleOwner',name:'传真'},
				  {key:'vehilePrice',name:'描述'},
				];
				let arr = [
					{
						id:'张明',
						fromAddress:'1',
						toAddress:'2',
						vehicleNo:'山东省德州市开发区',
						name:'5343968521',
						bcargoOwner:'张三',
						cargPrice:'18866669999',
						bvehicleOwner:'123456789-1',
						vehilePrice:'易罐',
					}
				]
				let list = {
				  fileName:'通讯录组织机构',//文件名
				  tHeader:tHeader,//生成Excel表格的头部标题栏
				  excelData:arr,//需要导出Excel的数据集合
				}
				this.$exportExcel.exportExcel(list);
			},
			excelImport(){
			  this.showDialog++;
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
			removeRoles(callback) {
				this.$confirm('此操作将永久删除, 是否继续?', '提示', {
					confirmButtonText: '继续删除',
					cancelButtonText: '取消',
					type: 'warning',
				}).then(() => callback());
			},
			removeRole(roleId) {
				this.$api.delOrg(roleId).then(() => {
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
			initOrg() {
				this.loading = true;
				this.$api.orgTree().then(
					({data}) => {
						// console.log('获取机构',data.data)
						this.organizations = data.data;
						this.roles = data.data;
						this.loading = false;
					},
					() => {
						this.$message.error('获取机构失败！');
					},
				);
			},
			fetchRoles() {
				this.loading = true;
				let par = {
					contact:this.filter,
					pageNum:this.currentPage,
					pageSize:this.pageSize,
				}
				this.$http.post("/organization/params?pageNum="+
				this.currentPage+
				"&pageSize="+this.pageSize,
				this.filter).then((data) => {
				    console.log(data.data.data)
					this.totalPageNum = data.data.data.total;
					// this.roles = data.data.data.list;
					this.loading = false;
				});
			},
			onRefresh() {
				// if (id) this.checkedRoleId = id;
				this.dialogVisible = false;
				this.initOrg();
			},

		},
	};
</script>
<style lang="scss" scoped>
	
	/deep/.el-table__expand-icon--expanded {
	  -webkit-transform: rotate(0deg);
	  transform: rotate(0deg);
	}
	
	/deep/.el-icon-arrow-right:before {
	  content: "\e790";
	  font-size: 18px;
	}
	 
	/deep/.el-table__expand-icon--expanded .el-icon-arrow-right:before {
	  content: "\e791";
	  font-size: 18px;
	}
	
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
