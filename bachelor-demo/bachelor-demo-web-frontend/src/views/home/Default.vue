<template>
  <section class="home">
    <!-- <div class="items">
      <label>
        <span>项目1</span>
        <input type="text" name="" value="项目名称">
      </label>
      <label>
        <span>项目2</span>
        <select>
          <option value="1">项目名称</option>
        </select>
      </label>
      <label class="label_radio">
        <i :class="chooseState == 1?'radio_check':'radio_uncheck'"></i>
        <input type="radio" hidden="hidden" name="choose" value="1" v-model="chooseState">选中
      </label>
      <label class="label_radio">
        <i :class="chooseState == 0?'radio_check':'radio_uncheck'"></i>
        <input type="radio" hidden="hidden" name="choose" value="0" v-model="chooseState">未选中
      </label>
    </div> -->
    <label>
      <span>查询用户id：</span>
      <input type="text" name="" value="" v-model="query" @blur="fetchUser">
    </label>
    <div class="btns">
      <span @click="showAddModal">添加用户</span>
    </div>
    <div class="table_div">
      <table>
        <thead>
          <tr>
            <th v-for="(item,index) in titles" :key="index">{{item}}</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(user,index) in userList" :key="index">
            <td>{{user.id}}</td>
            <td>{{user.userName}}</td>
            <td>{{user.userType}}</td>
            <td>{{user.enabled}}</td>
            <td>{{user.realName}}</td>
            <td>{{user.qq}}</td>
            <td>{{user.email}}</td>
            <td>{{user.address}}</td>
            <td>{{user.tel}}</td>
            <td>
              <span class="active" @click="editUser(user.id)">编辑</span>
              <span class="active" @click="delUser(user.id)">删除</span>
            </td>
          </tr>
          <tr v-if="!userList">
            <td colspan="10">没有检索到数据！</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="page_num">
      <span class="pre">&lt;</span>
      <span class="num">1</span>
      <span class="num">2</span>
      <span class="num active">3</span>
      <span class="num">4</span>
      <span class="next">&gt;</span>
    </div>
    <div class="myModal_wrap" v-if="addModalFlag">
      <div class="myModal_box">
        <h3>{{h3_tit}}</h3>
        <form>
          <label>
            <span>用户名：</span>
            <input type="text" v-model="addUserName" />
          </label>
          <label v-if="pageFlag == 'edit'">
            <span>角色：</span>
            <select v-model="editUserType">
              <option value="manager">manager</option>
              <option value="user">user</option>
            </select>
          </label>
          <label>
            <span>状态：</span>
            <input type="text" v-model="addEnabled" />
          </label>
          <label>
            <span>真实姓名：</span>
            <input type="text" v-model="addRealName" />
          </label>
          <label>
            <span>QQ：</span>
            <input type="text" v-model="addQQ" />
          </label>
          <label>
            <span>电子邮件：</span>
            <input type="text" v-model="addEmail" />
          </label>
          <label>
            <span>地址：</span>
            <input type="text" v-model="addAddress" />
          </label>
          <label>
            <span>电话：</span>
            <input type="text" v-model="addTel" />
          </label>
        </form>
        <div class="btns myModal_btns">
          <span @click="addUser">确定</span>
          <span @click="hideAddModal">取消</span>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: 'HomeDefault',
  data() {
    return {
      chooseState: 1,
      titles: ['序号', '用户名', '角色', '状态', '真实姓名', 'QQ', '电子邮件', '地址', '电话', '操作'],
      userList: [],
      query: null,
      h3_tit: null,
      editUserType: null,
      editId: null,
      pageFlag: null,
      addModalFlag: false,
      addUserName: null,
      addEnabled: null,
      addRealName: null,
      addQQ: null,
      addEmail: null,
      addAddress: null,
      addTel: null,
    };
  },
  created() {
    this.chooseState = 1;
  },
  mounted() {
    this.fetchUsers();
  },
  methods: {
    // 1.0 默认查询第一页, 共10条数据
    fetchUsers() {
      this.userList = [];
      this.$api.userTestAll({
        pageNum: 1,
        pageSize: 10,
      }).then(({ data }) => {
        this.userList = data.data.list;
      }, () => {
        this.$message.error('查询用户失败！');
      });
    },
    // 2.0 查询用户
    fetchUser() {
      this.userList = [];
      if (this.query !== '') {
        this.$api.userTestOne({
          id: this.query,
        }).then(({ data }) => {
          if (data.status === 'OK') {
            this.userList.push(data.data);
            console.log('this.userList', this.userList);
          } else {
            this.$message.error('没有查询结果');
          }
        }, () => {
          this.$message.error('查询用户失败！');
        });
      } else {
        this.fetchUsers();
      }
    },
    // 3.0 添加用户
    showAddModal() {
      this.h3_tit = '添加用户';
      this.pageFlag = 'add';
      this.addModalFlag = true;
    },
    hideAddModal() {
      this.addModalFlag = false;
    },
    addUser() {
      if (this.pageFlag === 'add') {
        const formData = {
          userName: this.addUserName ? this.addUserName : 'defaultName',
          enabled: this.addEnabled ? this.addEnabled : 0,
          realName: this.addRealName ? this.addRealName : '',
          qq: this.addQQ ? this.addQQ : '',
          email: this.addEmail ? this.addEmail : '',
          address: this.addAddress ? this.addAddress : '',
          tel: this.addTel ? this.addTel : '',
        };
        this.$api.userTestAdd(formData).then(({ data }) => {
          if (data.status === 'OK') {
            this.$message.success('添加用户成功！');
            this.addModalFlag = false;
            this.fetchUsers();
          }
        }, () => {
          this.$message.error('添加用户失败！');
        });
      } else {
        const formDataEdit = {
          id: this.editId,
          userName: this.addUserName ? this.addUserName : 'defaultName',
          userType: this.editUserType ? this.editUserType : '',
          enabled: this.addEnabled ? this.addEnabled : 0,
          realName: this.addRealName ? this.addRealName : '',
          qq: this.addQQ ? this.addQQ : '',
          email: this.addEmail ? this.addEmail : '',
          address: this.addAddress ? this.addAddress : '',
          tel: this.addTel ? this.addTel : '',
        };
        this.$api.userTestEdit(formDataEdit).then(({ data }) => {
          if (data.status === 'OK') {
            this.$message.success('编辑用户成功！');
            this.addModalFlag = false;
            this.fetchUsers();
          }
        }, () => {
          this.$message.error('编辑用户失败！');
        });
      }
    },
    // 4.0 编辑用户
    editUser(id) {
      this.editId = id;
      // 打开模态框
      this.h3_tit = '编辑用户';
      this.pageFlag = 'edit';
      this.addModalFlag = true;
      this.addUserName = this.userList[id].userName;
      this.editUserType = this.userList[id].userType;
      this.addEnabled = this.userList[id].enabled;
      this.addRealName = this.userList[id].realName;
      this.addQQ = this.userList[id].qq;
      this.addEmail = this.userList[id].email;
      this.addAddress = this.userList[id].address;
      this.addTel = this.userList[id].tel;
    },
    // 5.0 删除用户
    delUser(id) {
      this.userList = [];
      this.$api.userTestDel({
        id,
      }).then(({ data }) => {
        if (data.status === 'OK') {
          this.$message.success('删除添加成功！');
          this.fetchUsers();
        }
      }, () => {
        this.$message.error('删除用户失败！');
      });
    },
  },
};
</script>

<style scoped>
  .myModal_wrap{
    position: fixed;
    width:100%;
    height:100%;
    background:rgba(0,0,0,.9);
    left:0;
    top:0;
    z-index:60;
  }
  .myModal_box{
    width:650px;
    height:550px;
    border-radius: 10px;
    background: #fff;
    position: absolute;
    top: 50%;
    left: 50%;
    margin-top: -290px;
    margin-left: -340px;
  }
  h3{
    margin: 0;
    padding: 30px 30px 20px;
    border-bottom: 1px solid #ddd;
  }
  .myModal_box form{
    padding: 20px 30px 30px;
  }
  .home{
    color: #333;
    font-size: 14px;
  }
  /* 项目 */
  .items{
    margin: 29px;
  }
  label{
    display:block;
    font-weight: normal;
    margin: 0 0 15px 0;
    cursor: pointer;
  }
  label span{
    margin: 0 5px 0 0;
    width: 85px;
    display: inline-block;
  }
  input[type=text],
  select{
    width: 240px;
    height: 29px;
    line-height: 28px;
    border-radius: 29px;
    border: 1px solid #bbb;
    padding: 0 15px;
  }
  select{
    appearance:none;
    -moz-appearance:none;
    -webkit-appearance:none;
    background: url('../../assets/images/colormatch/sjx.png') 213px center no-repeat;
  }
  select::-ms-expand{
    display: none;
  }
  .label_radio{
    margin: 0 20px 0 0;
  }
  .label_radio i{
    display: inline-block;
    width: 15px;
    height: 15px;
    vertical-align: -2px;
    margin: 0 5px 0 0;
  }
  .radio_check{
    background: url('../../assets/images/colormatch/radio.png') 0 0 no-repeat;
  }
  .radio_uncheck{
    background: url('../../assets/images/colormatch/radio.png') -15px 0 no-repeat;
  }
  /* 按钮 */
  .btns{
    text-align: right;
  }
  .myModal_btns{
    text-align: center;
  }
  .btns span{
    display: inline-block;
    border: 1px solid #bbb;
    height: 29px;
    line-height: 28px;
    border-radius: 29px;
    margin: 0 0 0 20px;
    padding: 0 20px;
    cursor: pointer;
  }
  .btns span.active,
  .btns span:hover{
    border-color: #1b4aa6;
    background: #558ce4;
    font-weight: bold;
    color: #fff;
  }
  /* 表格 */
  .table_div{
    border-radius: 10px;
    overflow: hidden;
    margin: 25px 0;
  }
  .table_div table{
    width: 100%;
    border-collapse: collapse;
    border: 0 none;
  }
  .table_div tr{
    line-height: 44px;
  }
  .table_div thead{
    background: #558ce4;
    color: #fff;
    border-right: 1px solid #558ce4;
  }
  .table_div th{
    border-right: 1px solid #6da0ef;
    text-align: center;
  }
  .table_div td{
    border-right: 1px solid #eee;
    text-align: center;
  }
  .table_div td:last-child{
    border-right: 0;
  }
  .table_div tbody{
    border: 1px solid #bbb;
    border-top: 0;
    font-size: 12px;
  }
  .table_div tbody tr:nth-child(even){
    background: #f9f9f9;
  }
  .table_div td span{
    display: inline-block;
    color: #2265be;
    cursor: pointer;
  }
  .table_div td span.active{
    font-weight: bold;
    background: #ebf1fe;
    height: 26px;
    line-height: 26px;
    padding: 0 8px;
    border-radius: 26px;
  }
  /* 页码 */
  .page_num{
    float: right;
  }
  .page_num span{
    display: inline-block;
    border: 1px solid #bbb;
    width: 29px;
    height: 29px;
    border-radius: 29px;
    line-height: 28px;
    text-align: center;
    cursor: pointer;
  }
  .page_num span.num{
    margin: 0 5px 0 0;
  }
  .page_num span.num:first-child{
    margin: 0 10px 0 0;
  }
  .page_num span.num:last-child{
    margin: 0 10px 0 0;
  }
  .page_num span.active{
    font-weight: bold;
    background: #558ce4;
    border-color: #1b4aa6;
    color: #fff;
  }
</style>
