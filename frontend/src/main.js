import Vue from 'vue'
import App from './pages/app/App.vue'
import BootstrapVue from 'bootstrap-vue'
import axios from 'axios'
import Vuetify from 'vuetify'
import Vuelidate from 'vuelidate'

import router from './router/router'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false

Vue.use(BootstrapVue)
Vue.use(axios)
Vue.use(Vuetify)
Vue.use(Vuelidate)

/* eslint-disable */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  render: h => h(App)
})
