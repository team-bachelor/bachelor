<template>
    <transition-stagger
      :before-enter-styles="{translateY: -20, opacity: 0}"
      :enter-styles="{translateY: 0, opacity: 1}"
      :leave-styles="{}"
      :delay="80"
      tag="div"
      class="service-list" >
        <a class="service-item" v-for="(item,index) in services"
          :href="item.url"
          :cn.org.bachelor.up.oauth2.key="index"
          :style="{backgroundImage:'url('+item.bg+')'}">
          <h4>{{item.name}}</h4>
        </a>
    </transition-stagger>
</template>
<script>
import TransitionStagger from '@/components/Stagger.vue';

const services = [
  {
    name: '售票系统',
    url: 'http://localhost:8081/#/roles',
    bg: '/examples/1.png',
  },
  {
    name: '检票系统',
    url: 'http://localhost:8081/#/roles',
    bg: '/examples/2.png',
  },
  {
    name: '权限管理系统',
    url: 'http://localhost:8081/#/roles',
    bg: '/examples/4.jpeg',
  },
  {
    name: '用户管理系统',
    url: 'http://localhost:8081/#/roles',
    bg: '/examples/3.png',
  },
  {
    name: '售票系统',
    url: 'http://localhost:8081/#/roles',
    bg: '/examples/5.jpeg',
  },
  {
    name: '售票系统',
    url: 'http://localhost:8081/#/roles',
    bg: '/examples/6.jpeg',
  },
  {
    name: '售票系统',
    url: 'http://localhost:8081/#/roles',
    bg: '/examples/2.png',
  },
];

export default {
  name: 'ServiceList',
  components: {
    TransitionStagger,
  },
  data() {
    return {
      services: [],
    };
  },
  mounted() {
    this.services = services;
  },
  beforeDestroy() {
    this.services = [];
  },
};
</script>
<style lang="scss" scoped>
.service-list{
  display: flex;
  flex: 1;
  justify-content: center;
  align-items: center;
  align-content: center;
  flex-wrap: wrap;
  overflow: auto;
  height: 100%;
  margin: 0 auto;
}
.service-item{
  width: 230px;
  height: 130px;
  background: #FFF url('/examples/1.png') no-repeat;
  background-size: cover;
  margin: 10px;
  color: #FFF;
  border-radius: 4px;
  box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.4), 0 0 1px rgba(56, 56, 56, 0.25);
  cursor: pointer;
  transition: box-shadow 0.218s ease;
  &:hover{
    box-shadow: 5px 5px 10px rgba(56, 56, 56, 0.25);
    h4{
      transform: translateY(-150%);
    }
  }
  h4{
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 2px;
    line-height: 1;
    text-align: center;
    text-shadow: 1px 1px 1px rgba(0,0,0,0.7);
    transform: translateY(0%);
    transition: transform 0.4s;
  }
}
</style>
