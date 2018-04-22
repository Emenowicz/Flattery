import Vue from 'vue'
import App from './pages/greeting/App.vue'
import BootstrapVue from 'bootstrap-vue'
import RecyclerView from 'vue-recyclerview'
import axios from 'axios'

import router from './router/router'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false

Vue.use(BootstrapVue)
Vue.use(RecyclerView)
Vue.use(axios)

/* eslint-disable */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  render: h => h(App)
})
