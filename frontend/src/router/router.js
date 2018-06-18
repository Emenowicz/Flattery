/* eslint-disable */
import Vue from 'vue'
import Router from 'vue-router'
import Search from '@/components/Search'
import ListView from '@/components/ListView'
import UserAccount from '@/components/UserAccount'
import FavOffers from '@/components/FavOffers'

Vue.use(Router);

const routes = [
  { path: '/', component: Search },
  { path: '/offers', component: ListView, props: (route) => ({
      search: route.query.search,
      city: route.query.city,
      minPrice: route.query.minPrice,
      maxPrice: route.query.maxPrice,
      radius: route.query.radiusValue,
      daysOld: route.query.daysOld,
      offerType: route.query.offerType,
      roomType: route.query.roomType,}) },
  { path: '/account', component: UserAccount},
  { path: '/favourites', component: FavOffers}
];

export default new Router({
  routes: routes
})
