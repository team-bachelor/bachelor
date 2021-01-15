<template>
  <section class="message"
    v-loading="loading"
    element-loading-background="rgba(255,255,255,0)">
    <aside class="aside">
      <div class="message-item"
        :class="{active: index===currentIndex}"
        v-for="(item,index) in messages"
        @click="open(index)"
        :key="index">
        <h5 class="title">{{item.title}}</h5>
        <div class="text">{{item.content}}</div>
        <div class="date">{{item.datetime}}</div>
      </div>
    </aside>
    <div class="content" v-if="detail">
      <h2 class="content-title">{{detail.title}}</h2>
      <div class="content-date">{{detail.datetime}}</div>
      <div class="content-text" v-html="detail.content"></div>
    </div>
  </section>
</template>
<script>
export default {
  name: 'MessageList',
  data() {
    return {
      messages: [],
      currentIndex: 0,
      pageNo: 0,
      pageSize: 10,
      loading: true,
    };
  },
  computed: {
    detail() {
      if (this.messages.length && this.messages[this.currentIndex]) {
        return this.messages[this.currentIndex];
      }
      return null;
    },
  },
  created() {
    this.fetch();
  },
  methods: {
    fetch() {
      this.loading = true;
      this.$api.message.all({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
      }).then(({ data }) => {
        if (!data.code) {
          this.messages = data.data;
        }
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    open(index) {
      this.currentIndex = index;
    },
  },
};
</script>
<style lang="scss" scoped>
.message{
  margin: 0 auto;
  padding: 40px 20px;
  display: flex;
  max-width: 1000px;
  justify-content: space-between;
  align-items: stretch;
  align-content: stretch;
  box-sizing: border-box;
  height: 100%;
}
.aside{
  width: 260px;
  overflow: auto;
}
.content{
  flex: 1;
  background: #FFF;
  margin-left: 20px;
  transition: all 0.3s;
  overflow: auto;
}
.message-item{
  background: #FFF;
  color: #FFF;
  box-shadow: 0 0 1px rgba(56, 56, 56, 0.25);
  cursor: pointer;
  transition: box-shadow 0.218s ease;
  padding: 10px 10px;
  color: #555;
  overflow: hidden;
  &:hover,&.active{
    box-shadow: inset 0 0 1px rgba(56, 56, 56, 0.15);
    background: #f5f5f5;
  }
  .title{
    padding: 2px;
    margin: 0;
    line-height: 1;
    font-weight: 400;
  }
  .text{
    color: #888888;
    font-size: 13px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    width: 240px;
    margin: 5px 0;
  }
  .date{
    font-size: 12px;
    text-align: right;
  }
}
.content-title{
  text-align: center;
}
.content-date{
  text-align: center;
  font-size: 12px;
}
.content-text{
  padding: 20px;
}
</style>
