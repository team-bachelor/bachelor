<template>
  <header>
    <div class="logo">
      <i class="fa fa-apple"></i> {{PROJECT_NAME}}
    </div>
    <div>
      <a href="javascript:;" class="item circle" @click="openedCommonView('ServiceList')">
        <i class="fa fa-th-large"></i>
      </a>
    </div>
  </header>
</template>
<script>
import { TOGGLE_COMMON_VIEW } from '@/store/types';
import { PROJECT_NAME } from '@/config';

export default {
  name: 'LxHeader',
  data() {
    return {
      PROJECT_NAME,
    };
  },
  methods: {
    openedCommonView(e) {
      this.$store.commit(TOGGLE_COMMON_VIEW, e);
    },
  },
};
</script>

<style lang="scss" scoped>
.logo{
  cursor: pointer;
  a{
    margin-left: 5px;
    color: #AAA;
    transition: all 0.3s;
  }
  &:hover{
    a{
      color: #CCC;
    }
  }
}
.item{
  background: #FFF;
  border-radius: 15px;
  padding: 2px 15px;
  line-height: 1;
  font-size: 14px;
  border: 1px dashed transparent;
  margin-left: 10px;
  &:hover{
    border-color: #aaa;
  }
}
</style>
