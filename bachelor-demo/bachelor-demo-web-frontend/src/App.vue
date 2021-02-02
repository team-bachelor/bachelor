<template>
  <div id="app">
    <router-view/>
  </div>
</template>
<script>
export default {
  name: 'App',
  data() {
    return {
    };
  },
  created() {
    console.log('start app');
  },
  methods: {
  },
};
</script>
