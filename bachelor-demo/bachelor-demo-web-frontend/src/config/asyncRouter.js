import { constantRoutes } from '@/config/routes.js';
import Layout from '@/layout/default/Index.vue';

export function getAsyncRoutes(routes) {
    const res = []
	// console.log(routes)
    const keys = ['path', 'name', 'children', 'redirect', 'meta', 'hidden']
    routes.forEach(item => {
        const newItem = {};
        if (item.component) {
            if (item.component === 'Layout') {
                newItem.component = Layout
            } else {
                newItem.component = resolve => require([`@/views/${item.component}`],resolve)
            }
        }
        for (const key in item) {
            if (keys.includes(key)) {
                newItem[key] = item[key]
            }
        }
        if (newItem.children && newItem.children.length) {
            newItem.children = getAsyncRoutes(item.children)
        }
        res.push(newItem)
    })
    return res
}
