import Vue from 'vue';
import VueRouter from 'vue-router';
import routes from '@/config/routes';
import { BASE_URL } from '@/config';

Vue.use(VueRouter);

function recursionRoute(data) {
  return data.map((route) => {
    if (!route.meta) route.meta = {};
    route.meta.original = route.path;
    if (route.children && route.children.length) route.children = recursionRoute(route.children);
    return route;
  });
}

const router = new VueRouter({
  base: BASE_URL,
  routes: recursionRoute(routes),
});

export default router;
