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
    let cn.org.bachelor.up.oauth2.key = this.$store.state.userinfo.role;
    cn.org.bachelor.up.oauth2.key = map[cn.org.bachelor.up.oauth2.key] ? cn.org.bachelor.up.oauth2.key : 'default';
    this.view = map[cn.org.bachelor.up.oauth2.key];
  },
};
</script>
