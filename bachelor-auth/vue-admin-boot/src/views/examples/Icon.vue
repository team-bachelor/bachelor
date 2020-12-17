<template>
  <section class="" style="background: #FFF;padding:10px;">
    <!-- <h2>SVG 图标集合</h2>
    <el-alert type="info">
      <span> svgicon 使用方式
        <el-link type="primary" @click="$router.push('/examples/svg-icon')">请看</el-link>
      </span>
    </el-alert>
    <el-row :gutter="10" type="flex" style="flex-wrap: wrap;">
      <el-col class="icon" :span="6" v-for="name in svgicons" :key="name">
        <svgicon style="stroke-width:1" :name="name" :fill="false" color="#1773ff" scale="1">
        </svgicon>
        <br/>
        <span class="icon-name">{{name}}</span>
      </el-col>
    </el-row> -->
    <h2 class="title">内置图标（白色）<span style="font-size:14px;color:#888;font-weight:400">{{pngicons.length}}个</span></h2>
    <div style="margin: 0 15px;">
      <h3 style="font-size:23px;font-weight:300">
        如何使用
      </h3>
      <el-row :gutter="10" type="flex" style="flex-wrap: wrap;">
        <el-col :span="12" style="background: #4492EF;display:flex;justify-content: center;align-items: center;">
          <icon name="anfang"/>
          <icon name="anfang" size="12"/>
          <icon name="anfang" size="24"/>
          <icon name="anfang" size="36"/>
          <icon name="anfang" size="50"/>
          <icon name="anfang" size="100"/>
          <span style="background:#FFF">
            <icon name="anfang" size="100" color="blue"/>
          </span>
        </el-col>
        <el-col :span="12" style="background:#f4f4f4">
          <pre class="html"><code class="html hljs xml">
            <span class="hljs-tag">&lt;<span class="hljs-name">icon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"anfang"</span> <span class="hljs-attr">size</span>=<span class="hljs-string">"12"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">icon</span>&gt;</span>
            <span class="hljs-tag">&lt;<span class="hljs-name">icon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"anfang"</span> <span class="hljs-attr">size</span>=<span class="hljs-string">"24"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">icon</span>&gt;</span>
            <span class="hljs-tag">&lt;<span class="hljs-name">icon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"anfang"</span> <span class="hljs-attr">size</span>=<span class="hljs-string">"36"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">icon</span>&gt;</span>
            <span class="hljs-tag">&lt;<span class="hljs-name">icon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"anfang"</span> <span class="hljs-attr">size</span>=<span class="hljs-string">"50"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">icon</span>&gt;</span>
            <span class="hljs-tag">&lt;<span class="hljs-name">icon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"anfang"</span> <span class="hljs-attr">size</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">color</span>=<span class="hljs-string">"blue"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">icon</span>&gt;</span>
        </code></pre>
        </el-col>
      </el-row>
      <p>
        <el-alert type="info">ps：菜单中使用填入`name`就可以</el-alert>
      </p>
    </div>
    <div style="margin:10px;background:#4492EF;color:#FFF">
      <el-row :gutter="10">
        <el-col class="icon" :lg="6" :xs="24" :sm="12" :md="8" v-for="name in pngicons" :key="name">
          <icon :name="name" size="32"></icon>
          <span class="icon-name" style="color:#eee">{{`&lt;icon name="${name}"/&gt;`}}</span>
        </el-col>
      </el-row>
    </div>
    <h2 class="title">ElementUI 图标<span style="font-size:14px;color:#888;font-weight:400">
      （使用方式<a target="_blank" href="https://element.eleme.io/#/zh-CN/component/alert">请看</a>）</span></h2>
    <el-row :gutter="10">
      <el-col class="icon" :lg="6" :xs="24" :sm="12" :md="8" v-for="name in icons" :key="name">
        <i :class="'icon-i el-icon-' + name"></i> <span class="icon-name">
          {{'el-icon-' + name}}
        </span>
      </el-col>
    </el-row>
    <h2 class="title">font awesome 图标<span style="font-size:14px;color:#888;font-weight:400">
      （更多图标<a target="_blank" href="http://fontawesome.dashgame.com/">请看</a>）</span></h2>
    <el-row :gutter="10">
      <el-col class="icon" :lg="6" :xs="24" :sm="12" :md="8" v-for="name in icons" :key="name">
        <i :class="'icon-i el-icon-' + name"></i> <span class="icon-name">
          {{'el-icon-' + name}}
        </span>
      </el-col>
    </el-row>
  </section>
</template>

<script>
import pngicons from '@/lib/icon/icons';
import icons from './icon.json';
import svgicons from './icon-svg';

export default {
  name: 'ExampleIcon',
  data() {
    return {
      icons,
      svgicons,
      pngicons,
    };
  },
  created() {
  },
  mounted() {
  },
  methods: {
  },
};
</script>
<style lang="scss" scoped>
.title{
  text-align: center;
  font-size: 30px;
  font-weight: 300;
}
.icon {
  padding: 20px 0;
  display: flex;
  justify-content: flex-start;
  flex-direction: column;
  align-items: center;
  text-align: center;
}
.icon-i {
  color: #606266;
  font-size: 20px;
  float: left;
}
.icon-name {
  height: 1em;
  font-size: 12px;
  margin-left: 5px;
  float: left;
}
.hljs{background:#f4f4f4;color:#333;display:block;overflow-x:auto;padding:.5em}.hljs-comment,.hljs-meta{color:#969896}.hljs-emphasis,.hljs-quote,.hljs-string,.hljs-strong,.hljs-template-variable,.hljs-variable{color:#df5000}.hljs-keyword,.hljs-selector-tag,.hljs-type{color:#a71d5d}.hljs-attribute,.hljs-bullet,.hljs-literal,.hljs-symbol{color:#0086b3}.hljs-name,.hljs-section{color:#63a35c}.hljs-tag{color:#333}.hljs-attr,.hljs-selector-attr,.hljs-selector-class,.hljs-selector-id,.hljs-selector-pseudo,.hljs-title{color:#795da3}.hljs-addition{background-color:#eaffea;color:#55a532}.hljs-deletion{background-color:#ffecec;color:#bd2c00}.hljs-link{text-decoration:underline}
</style>
