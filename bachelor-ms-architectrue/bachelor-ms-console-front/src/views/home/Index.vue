<template>
  <component :is="view"/>
</template>

<script>
export default {
  name: 'Home',
  data() {
    return {
      view: null,
    };
  },
  created() {
    const map = {
      default: () => import('./Default.vue'),
      admin: () => import('./Admin.vue'),
    };
    let key = this.$store.state.userinfo.role;
    key = map[key] ? key : 'default';
    this.view = map[key];
  },
};
</script>
