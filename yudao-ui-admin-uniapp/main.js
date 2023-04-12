import Vue from 'vue'
import App from './App'
import store from './store' // store
import plugins from './plugins' // plugins
import './permission' // permission
Vue.use(plugins)

import config from '@/config'
Vue.config.productionTip = false
Vue.prototype.$store = store
App.mpType = 'app'
Vue.prototype.$static = config
const app = new Vue({
  ...App
})

app.$mount()
