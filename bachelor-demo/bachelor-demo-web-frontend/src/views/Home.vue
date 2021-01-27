<template>
  <div class="yj_box">
    <div class="header">
      <div class="weather_box">多云 东北风2级</div>
      <div class="date_box">2020年12月25日 11:21:57</div>
      <div class="title_box">东营港应急指挥中心</div>
      <div class="duty_box">今日值班人员：王贵 祝家辉 黎明 王启超  张 </div>
      <div class="user_box" @click="$router.logout">欢迎您：刘琳 退出</div>
    </div>
    <div class="num_box">
      <ul>
        <li>
          <div class="num_bg"><img src="../assets/images/num1.png" alt=""></div>
          <div class="num_txt">
            <p>2920</p>
            <span>企业建档总数</span>
          </div>
        </li>
        <li>
          <div class="num_bg"><img src="../assets/images/num2.png" alt=""></div>
          <div class="num_txt">
            <p>95311</p>
            <span>重大危险源数量</span>
          </div>
        </li>
        <li>
          <div class="num_bg"><img src="../assets/images/num3.png" alt=""></div>
          <div class="num_txt">
            <p>350</p>
            <span>年度检查次数</span>
          </div>
        </li>
        <li>
          <div class="num_bg"><img src="../assets/images/num4.png" alt=""></div>
          <div class="num_txt">
            <p>65745</p>
            <span>年度隐患建档数</span>
          </div>
        </li>
        <li>
          <div class="num_bg"><img src="../assets/images/num5.png" alt=""></div>
          <div class="num_txt">
            <p>24</p>
            <span>预案总数</span>
          </div>
        </li>
      </ul>
    </div>
    <div class="container_box">
      <div class="con_item">
        <div class="btn_items">
          <div class="btn_box">
            <div class="btn_item"><img src="../assets/images/menu1.png"><p>应急一张图</p></div>
            <div class="btn_item"><img src="../assets/images/menu2.png"><p>应急资源</p></div>
            <div class="btn_item"><img src="../assets/images/menu3.png"><p>应急预案</p></div>
            <div class="btn_item"><img src="../assets/images/menu4.png"><p>应急处置</p></div>
            <div class="btn_item"><img src="../assets/images/menu5.png"><p>应急演练</p></div>
            <div class="btn_item"><img src="../assets/images/menu6.png"><p>应急知识库</p></div>
          </div>
        </div>
      </div>
      <div class="con2_item">
        <div class="map_box">
          <img src="../assets/images/map_box.png">
        </div>
        <div class="tj_item wz_1"><p>322</p><span>应急物资</span></div>
        <div class="tj_item wz_2"><p>2998</p><span>应急队伍</span></div>
        <div class="tj_item wz_3"><p>855</p><span>应急专家</span></div>
        <div class="tj_item wz_4"><p>123</p><span>应急仓库</span></div>
        <div class="tj_item wz_5"><p>5</p><span>避难场所</span></div>
        <div class="bottom_menu">
          <a href="javascript:;">修改密码</a>
          <a href="javascript:;">帮助中心</a>
          <a href="javascript:;">系统设置</a>
          <a href="javascript:;">通知公告</a>
          <a href="javascript:;">待办任务</a>
        </div>
      </div>
      <div class="con_item_r">
        <div class="btn_items">
          <div class="btn_box">
            <div class="btn_item"><img src="../assets/images/menu7.png"><p>舆情检测</p></div>
            <div class="btn_item"><img src="../assets/images/menu8.png"><p>检测监控</p></div>
            <div class="btn_item" @click="openSys('/userRight')"><img src="../assets/images/menu9.png"><p>应急通讯录</p></div>
            <div class="btn_item"><img src="../assets/images/menu10.png"><p>值班值守</p></div>
            <div class="btn_item"><img src="../assets/images/menu11.png"><p>应急通讯与会商</p></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getAsyncRoutes } from '@/config/asyncRouter'
export default {
  data() {
    return {
		
	};
  },
  methods: {
	  openSys(url){
		this.$router.push(url)
	  },
  },
}
</script>

<style rel="stylesheet/scss" lang="scss">
html,body{
  height: 100%;
  overflow: hidden;
}
#app{
  height: 100%;
}
.yj_box{
  background: #030542 url(../assets/images/bg.png) center bottom no-repeat;
  background-size: cover;
  height: 100%;
  padding-top: 20px;
  .header{
    background: url(../assets/images/header_bg.png) center no-repeat;
    background-size: cover;
    height: 70px;
    padding: 0 20px;
    display: flex;
    div{
      width: 20%;
      color: #00ddf8;
      line-height: 60px;
      overflow: hidden;
    }
    .date_box,.user_box{
      text-align: right;
	  cursor: pointer;
    }
    .title_box{
      text-align: center;
      line-height: 70px;
      font-size: 34px;
      color: #fff;
      width: 39%;
    }
  }
  .num_box{
    width: 80%;
    margin: 30px auto 0;
    ul{
      width: 100%;
      display: flex;
      li{
        width: 20%;
        list-style: none;
        display: flex;
        .num_bg{
          text-align: center;
          width: 100px;
          height: 95px;
          background: url(../assets/images/num_bg.png) no-repeat center;
          display: inline-block;
          img{
            margin-top: 16px;
          }
        }
        .num_txt{
          display: inline-block;
          margin-left: 10px;
          p{
            color: #00e4ff;
            font-size: 26px;
            font-weight: bold;
            margin: 15px 0 0 0;
          }
          span{
            color: #fff;
            font-size: 13px;
          }
        }
      }
    }
  }
  .container_box{
    position: absolute;
    bottom: 70px;
    left: 20px;
    right: 20px;
    .con_item,.con_item_r{
      display: inline-block;
      width: 441px;
      background: url(../assets/images/left_k.png) top left no-repeat;
      // position: absolute;
      // bottom: 70px;
      .btn_items{
        width: 360px;
        height: 553px;
        background: url(../assets/images/left_kuang.png) top left no-repeat;
        margin: 70px 0 0 30px;
        .btn_box{
          margin: 50px 30px 0;
          display: inline-block;
        }
        .btn_item{
          cursor: pointer;
          width: 137px;
          height: 136px;
          display: inline-block;
          background: url(../assets/images/menu_bg.png) center no-repeat;
          margin-right: 10px;
          margin-bottom: 20px;
          text-align: center;
          color: #fff;
          img{
            margin-top: 14px;
            height: 70px;
            width: 70px;
          }
          p{
            margin: 0;
          }
          &:nth-of-type(2n){
            margin-right: 0;
            margin-left: 10px;
          }
        }
      }
    }
    .con_item_r{
      background: url(../assets/images/right_k.png) bottom right no-repeat;
      float:right;
      position: absolute;
      bottom:-50px;
      right: 0;
      .btn_items{
        margin: 0 30px 70px 0;
        float: right;
        .btn_item{
          &:last-child{
            background: url(../assets/images/menu_all_bg.png) center no-repeat;
            width: 294px;
          }  
        }
      }
    }
    .con2_item{
      display: inline-block;
      // width: calc(100% - 882px);
      width: 840px;
      height: 790px;
      background: url(../assets/images/map.png) center bottom no-repeat;
      position: absolute;
      bottom: -40px;
      left:-420px;
      margin-left:50%;
      .map_box{
        width:410px;
        height: 540px;
        margin: 0 auto;
      }
      .bottom_menu{
        width: 100%;
        text-align: center;
        position: absolute;
        bottom: -10px;
        a{
          border: 1px solid #008aff;
          padding: 3px 8px;
          color: #008aff;
          margin: 0 10px;
          text-decoration: none;
          border-radius: 3px;
        }
      }
      .tj_item{
        width: 127px;
        height: 197px;
        text-align: center;
        display: inline-block;
        position: absolute;
        background: url(../assets/images/num_t_bg.png) no-repeat;
        p{
          color: #00e4ff;
          font-size: 18px;
          font-weight: bold;
          margin-top: 12px;
        }
        span{
          color: #fff;
          position: absolute;
          bottom: 50px;
          left: 0;
          right: 0;
        }
      }
      .wz_1{
        top: 190px;
        left: 0px;
      }
      .wz_2{
        top: 190px;
        right: 0px;
      }
      .wz_3{
        bottom: 160px;
        left: 80px;
      }
      .wz_4{
        bottom: 160px;
        right: 80px;
      }
      .wz_5{
        bottom: 40px;
        left: -64px;
        margin-left:50%;
      }
    }
  }
}
</style>