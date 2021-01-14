<template>
  <section class="" style="background:#fff;padding:20px;">
    <h1 style="text-align:center;font-size:30px;font-weight:300;">svg icon 图标使用方法</h1>
    <h2 class="title">单色 (defalt: inherit)</h2>
    <el-row :gutter="10" type="flex" style="flex-wrap: wrap;">
      <el-col :span="12" class="flee">
        <svgicon name="arrow" width="50" height="50"></svgicon>
        <svgicon name="arrow" width="50" height="50" color="red"></svgicon>
        <svgicon name="arrow" width="50" height="50" color="green"></svgicon>
        <svgicon name="arrow" width="50" height="50" color="blue"></svgicon>
      </el-col>
      <el-col :span="12">
        <pre class="html"><code class="html hljs xml">
          <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"arrow"</span> <span class="hljs-attr">width</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"50"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
          <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"arrow"</span> <span class="hljs-attr">width</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">color</span>=<span class="hljs-string">"red"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
          <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"arrow"</span> <span class="hljs-attr">width</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">color</span>=<span class="hljs-string">"green"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
          <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"arrow"</span> <span class="hljs-attr">width</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">color</span>=<span class="hljs-string">"blue"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
      </code></pre>
      </el-col>
    </el-row>
    <h2 class="title">多色 (define by path/shape order)</h2>
    <el-row :gutter="10" type="flex" style="flex-wrap: wrap;">
      <el-col :span="12" class="flee">
        <svgicon name="check" :fill="false" width="100" height="100" color="r-#42b983 white"></svgicon>
        <svgicon name="colorwheel" width="200" height="200" :color="colors"></svgicon>
      </el-col>
      <el-col :span="12">
        <div class="html">
          <pre><code class="html hljs xml"><span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"check"</span> <span class="hljs-attr">:fill</span>=<span class="hljs-string">"false"</span> <span class="hljs-attr">width</span>=<span class="hljs-string">"100"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"100"</span> <span class="hljs-attr">color</span>=<span class="hljs-string">"r-#42b983 white"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span></code></pre>
        </div>
        <div class="html">
          <pre><code class="html hljs xml">
      <span class="hljs-tag">&lt;<span class="hljs-name">template</span>&gt;</span>
        <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"colorwheel"</span> <span class="hljs-attr">width</span>=<span class="hljs-string">"200"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"200"</span> <span class="hljs-attr">:color</span>=<span class="hljs-string">"colors"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
      <span class="hljs-tag">&lt;/<span class="hljs-name">template</span>&gt;</span>

      <span class="hljs-tag">&lt;<span class="hljs-name">script</span>&gt;</span><span class="javascript">
        <span class="hljs-keyword">export</span> <span class="hljs-keyword">default</span> {
          data () {
            <span class="hljs-keyword">return</span> {
              <span class="hljs-attr">colors</span>: <span class="hljs-string">'#FBAD20 #F5EB13 #B8D433 #6BC9C6 #058BC5 #34469D #7E4D9F #C63D96 #ED1944'</span>
            }
          }
        }
      </span><span class="hljs-tag">&lt;/<span class="hljs-name">script</span>&gt;</span>
      </code></pre>
        </div>
      </el-col>
    </el-row>

    <h2 class="title">使用原色</h2>
    <el-row :gutter="10" type="flex" style="flex-wrap: wrap;">
      <el-col :span="12" class="flee">
        <svgicon name="colorwheel" width="100" height="100" original></svgicon>
        <svgicon name="colorwheel" width="100" height="100" original color="_ black _ black _"></svgicon>
        <svgicon name="colorwheel" width="100" height="100" original color="_ r-black _ r-red _"></svgicon>
        <svgicon name="gift" width="100" height="100" original></svgicon>
      </el-col>
      <el-col :span="12">
        <div class="html">
<pre><code class="html hljs xml">
        <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"colorwheel"</span> <span class="hljs-attr">width</span>=<span class="hljs-string">"100"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"100"</span> <span class="hljs-attr">original</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
        <span class="hljs-comment">&lt;!-- overwrite original color --&gt;</span>
        <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"colorwheel"</span> <span class="hljs-attr">width</span>=<span class="hljs-string">"100"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"100"</span> <span class="hljs-attr">original</span> <span class="hljs-attr">color</span>=<span class="hljs-string">"_ black _ black _"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
        <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"colorwheel"</span> <span class="hljs-attr">width</span>=<span class="hljs-string">"100"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"100"</span> <span class="hljs-attr">original</span> <span class="hljs-attr">color</span>=<span class="hljs-string">"_ r-black _ r-red _"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
        <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"gift"</span> <span class="hljs-attr">width</span>=<span class="hljs-string">"100"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"100"</span> <span class="hljs-attr">original</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
      </code></pre>
        </div>
      </el-col>
    </el-row>


    <h2 class="title">大小 (defalt unit: px)</h2>
    <el-row :gutter="10" type="flex" style="flex-wrap: wrap;">
      <el-col :span="12" class="flee">
        <svgicon name="arrow"></svgicon>
        <svgicon name="arrow" width="50" height="50"></svgicon>
        <svgicon name="arrow" height="50"></svgicon>
        <svgicon name="arrow" scale="15"></svgicon>
        <svgicon name="arrow" width="10em" height="10em"></svgicon>
      </el-col>
      <el-col :span="12">
        <div class="html">
<pre><code class="html hljs xml">
      <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"arrow"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
      <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"arrow"</span> <span class="hljs-attr">width</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"50"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
      <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"arrow"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"50"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
      <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"arrow"</span> <span class="hljs-attr">scale</span>=<span class="hljs-string">"15"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
      <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"arrow"</span> <span class="hljs-attr">width</span>=<span class="hljs-string">"10em"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"10em"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
      </code></pre>
        </div>
      </el-col>
    </el-row>

    <h2 class="title">填充 (default: fill)</h2>
    <el-row :gutter="10" type="flex" style="flex-wrap: wrap;">
      <el-col class="flee" :span="12">
        <svgicon name="arrow" width="50" height="50"></svgicon>
        <svgicon name="arrow" width="50" height="50" :fill="false"></svgicon>
      </el-col>
      <el-col :span="12">
        <div class="html">
<pre><code class="html hljs xml">
      <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"arrow"</span> <span class="hljs-attr">width</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"50"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
      <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"arrow"</span> <span class="hljs-attr">width</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">:fill</span>=<span class="hljs-string">"false"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
      </code></pre>
        </div>
      </el-col>
    </el-row>

    <h2 class="title">方向 (default: right)</h2>
    <el-row :gutter="10" type="flex" style="flex-wrap: wrap;">
      <el-col class="flee" :span="12">
        <svgicon name="arrow" width="50" height="50" dir="left" title="left"></svgicon>
        <svgicon name="arrow" width="50" height="50" dir="up" title="up"></svgicon>
        <svgicon name="arrow" width="50" height="50" title="right"></svgicon>
        <svgicon name="arrow" width="50" height="50" dir="down" title="down"></svgicon>
      </el-col>
      <el-col :span="12">
        <div class="html">
<pre><code class="html hljs xml">
      <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"arrow"</span> <span class="hljs-attr">width</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">dir</span>=<span class="hljs-string">"left"</span> <span class="hljs-attr">title</span>=<span class="hljs-string">"left"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
      <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"arrow"</span> <span class="hljs-attr">width</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">dir</span>=<span class="hljs-string">"up"</span> <span class="hljs-attr">title</span>=<span class="hljs-string">"up"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
      <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"arrow"</span> <span class="hljs-attr">width</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">title</span>=<span class="hljs-string">"right"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
      <span class="hljs-tag">&lt;<span class="hljs-name">svgicon</span> <span class="hljs-attr">name</span>=<span class="hljs-string">"arrow"</span> <span class="hljs-attr">width</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">height</span>=<span class="hljs-string">"50"</span> <span class="hljs-attr">dir</span>=<span class="hljs-string">"down"</span> <span class="hljs-attr">title</span>=<span class="hljs-string">"down"</span>&gt;</span><span class="hljs-tag">&lt;/<span class="hljs-name">svgicon</span>&gt;</span>
      </code></pre>
        </div>
      </el-col>
    </el-row>
  </section>
</template>

<script>
export default {
  name: 'ExampleSvgIcon',
  data() {
    return {
      colors: '#FBAD20 #F5EB13 #B8D433 #6BC9C6 #058BC5 #34469D #7E4D9F #C63D96 #ED1944',
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
<style rel="stylesheet/scss" lang="scss" scoped>
.title{
  color: #2c3e50;
  font-weight: 300;
  text-align: center;
  padding: 20px 0;
  font-size: 24px;
  border-top: 1px solid #dcdcdc;
}
.flee{
  display: flex;
  justify-content: center;
  align-items: center;
}
.hljs{background:#f4f4f4;color:#333;display:block;overflow-x:auto;padding:.5em}.hljs-comment,.hljs-meta{color:#969896}.hljs-emphasis,.hljs-quote,.hljs-string,.hljs-strong,.hljs-template-variable,.hljs-variable{color:#df5000}.hljs-keyword,.hljs-selector-tag,.hljs-type{color:#a71d5d}.hljs-attribute,.hljs-bullet,.hljs-literal,.hljs-symbol{color:#0086b3}.hljs-name,.hljs-section{color:#63a35c}.hljs-tag{color:#333}.hljs-attr,.hljs-selector-attr,.hljs-selector-class,.hljs-selector-id,.hljs-selector-pseudo,.hljs-title{color:#795da3}.hljs-addition{background-color:#eaffea;color:#55a532}.hljs-deletion{background-color:#ffecec;color:#bd2c00}.hljs-link{text-decoration:underline}
</style>
