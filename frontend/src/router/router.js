import Vue from 'vue'
import Router from 'vue-router'
import Search from '@/components/Search'

Vue.use(Router)

const routes = [
  { path: '/', component: Search }
  // { path: '/recyclerview', component: RecyclerView, props: (route) => ({ search: route.query.search }) }
]

export default new Router({
  routes: routes
})
