<template>
  <div id="app">
    <router-view/>
  </div>
</template>
<script>
import { getAsyncRoutes } from '@/config/asyncRouter';
export default {
  name: 'App',
  data() {
    return {
    };
  },
  created() {
	let route = this.$store.state.routerList;
	// console.log('routerList',route)
	if(route.length>0){
		// console.log('start app',route);
		let accessRoutes = getAsyncRoutes(route);
		this.$router.addRoutes(accessRoutes);
	}
	
  },
  methods: {
	  initOrg() {
	  	this.$api.menuTree().then(
	  		({data}) => {
	  			console.log('获取',data.data)
	  		},
	  		() => {
	  			this.$message.error('获取菜单失败！');
	  		},
	  	);
	  },
  },
};
</script>
