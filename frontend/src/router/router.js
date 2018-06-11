/* eslint-disable */
import Vue from 'vue'
import Router from 'vue-router'
import Search from '@/components/Search'
import ListView from '@/components/ListView'

Vue.use(Router);

const routes = [
  { path: '/', component: Search },
  { path: '/offers', component: ListView, props: (route) => ({ search: route.query.search }) }
];

export default new Router({
  routes: routes
})
