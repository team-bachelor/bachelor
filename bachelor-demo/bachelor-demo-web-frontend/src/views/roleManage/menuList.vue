<template>
	<section>
		<section class="lx-container">
			<div class="filter" :class="{opened:!opened}">
				<!-- <div class="hairline"></div> -->
				<div class="btn">
					<el-button size="small" @click="onEditRole({},0)">新增菜单</el-button>
					 <span class="myicon icon-zj"></span>
				</div>

				<!-- <el-button size="small" type="danger" @click="onRemoveRoles" >删除</el-button> -->
				<el-table :data="roles" :loading="loading" @selection-change="onSelectionChange"
				height="740"
				row-key="id"
				:tree-props="{children: 'children', hasChildren: 'hasChildren'}">
					
					<el-table-column type="selection" width="55">
					</el-table-column>
					<!-- <el-table-column prop="code" label="标识">
					</el-table-column> -->
					
					<el-table-column prop="title" label="菜单名称" show-overflow-tooltip>
					</el-table-column>
					<el-table-column prop="seqOrder" label="排序" width="80">
					</el-table-column>
					<el-table-column prop="icon" label="图标" show-overflow-tooltip>
					</el-table-column>
					<el-table-column prop="path" label="路径" show-overflow-tooltip>
					</el-table-column>
					<el-table-column prop="component" label="路由" show-overflow-tooltip>
					</el-table-column>
					<el-table-column prop="updateTime" label="时间" show-overflow-tooltip>
					</el-table-column>
						
					<el-table-column width="232">
					  <template slot-scope="scope">
					  <el-button
					    size="small"
					    type="primary"
					    @click="onEditRole(scope.row,scope.row.id)">编辑</el-button>
					  <el-button
					    size="small"
					    type="danger"
					    @click="onRemoveRole(scope.row.id)">删除</el-button>
					  </template>
					</el-table-column>
					<div slot="empty">{{loading?'加载中...':'无数据'}}</div>
				</el-table>
			</div>

			<el-dialog
			  :title="(checkedRoleId?'编辑':'新增')+'菜单'"
			  :visible.sync="dialogVisible"
			  width="40%"
			  :before-close="handleClose">
			  <menu-edit :pageData.sync="checkedData"
			    :id.sync="checkedRoleId"
			    :organizations="roles"
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
	import menuEdit from '../roleManage/menuEdit.vue';
	import importExcel from "@/components/importExcel";
	
	import { ROUTER_LIST } from '@/store/types';
	import { getAsyncRoutes } from '@/config/asyncRouter';
	export default {
		name: 'orgManage',
		components: {
			menuEdit,
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
				checkedData: {},
				checkedRoleCode: '',
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
			onEditRole(data,id) {
				// console.log(data)
				this.checkedData = data;
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
			removeRole(id) {
				this.$api.delMenu(id).then(() => {
					if (id === this.checkedRoleId) this.opened = '';
					this.$message.success('成功');
					this.initOrg();
				}).catch(() => this.$message.error('删除失败！'));
			},
			handleCurrentChange(e) {
				this.currentPage = e;
				this.initOrg();
			},
			initOrg() {
				this.loading = true;
				this.$api.menuTree().then(
					({data}) => {
						// console.log('获取机构',data.data)
						this.roles = data.data;
						
						this.loading = false;
						
						// 刷新列表并保存菜单路由
						let list = JSON.parse(JSON.stringify(this.roles));
						this.addMenuList(list)
					},
					() => {
						this.$message.error('获取机构失败！');
					},
				);
			},
			addMenuList(data){
				let routerList = data;
				//最后添加404页
				routerList.push({
					"path": "*",
					"component": "error/404",
					"name":"404",
					"meta": {
						"title": "404没有找到页面",
						"requireAuth": false,
					},
				})
				this.$store.commit(ROUTER_LIST, {
				  routerList,
				});
				let r = this.$store.state.routerList;
				// console.log('登录保存routerList',r)
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
