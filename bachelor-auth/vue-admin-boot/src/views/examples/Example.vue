<template>
<div>
  <section class="mod">
    <el-form :inline="true" >
      <el-form-item>
        <el-input v-model="formdata.name" placeholder="输入违规人姓名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="formdata.idcard" style="width:260px" placeholder="输入违规人身份证号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="formdata.tel" style="width:180px" placeholder="输入违规人电话号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="formdata.company" style="width:320px" placeholder="输入违规人企业名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="formdata.area" placeholder="活动区域">
          <el-option label="区域一" value="shanghai"></el-option>
          <el-option label="区域二" value="beijing"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="formdata.date"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <!-- <el-form-item>
        <el-button type="primary">查询</el-button>
      </el-form-item> -->
      <div style="text-align:right">
        <el-button type="primary">查询</el-button>
      </div>
    </el-form>
  </section>
  <section class="mod">
    <div class="handler">
      <el-link type="primary" :underline="false" style="margin-right:5px;">
        <i class="el-icon-search el-icon--right"></i>
        查询</el-link>
      <el-link type="primary" :underline="false"><i class="el-icon-refresh el-icon--right"></i>
        重置</el-link>
    </div>
    <el-table
      :data="tableData"
      ref="table"
      stripe
      border>
      <el-table-column
        prop="date"
        label="日期"
        width="180">
      </el-table-column>
      <el-table-column
        prop="name"
        label="姓名"
        width="180">
      </el-table-column>
      <el-table-column
        prop="address"
        label="地址">
      </el-table-column>
    </el-table>
  </section>
</div>
</template>

<script>
export default {
  name: 'ExampleTable',
  data() {
    return {
      formdata: {
        name: '',
        idcard: '',
        company: '',
        date: '',
        tel: '',
        area: '',
      },
      tableData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄',
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄',
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1519 弄',
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1516 弄',
      }, {
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄',
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄',
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1519 弄',
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1516 弄',
      }],
    };
  },
  created() {
  },
  mounted() {
    this.$nextTick(() => this.$refs.table.doLayout());
  },
  methods: {
  },
};
</script>
<style lang="scss" scoped>
.mod{
  padding: 20px;
  background: #FFF;
  margin-bottom: 20px;
  box-shadow: 0 2px 5px 0 rgba(0,0,0,0.12);
  border-radius: 4px;
}
.handler{
  text-align: right;
  margin-bottom: 10px;
}
</style>
